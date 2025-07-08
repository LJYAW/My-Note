// export default service
import axios from 'axios'
import { Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
// import generateSignString from './setsign'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 50000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  (config) => {
    // do something before request is sent

    // 判断 接口不同域名
    // config.baseURL = "/";

    // 设置默认请求头
    var methodArr = ['post', 'delete', 'put']
    var addSign = methodArr.includes(config.method)
    if (addSign) {
      // 说明有特殊 sign
      if (!config.headers.sign) {
        // config.headers.sign = generateSignString(config.url, config.data)
      }
    }

    if (store.getters.token) {
      config.headers.Token = getToken()
    }
    return config
  },
  (error) => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  (response) => {
    const res = response
    // if the custom code is not 20000, it is judged as an error.
    // if (res.code !== 20000) {
    //   Message({
    //     message: res.message || 'Error',
    //     type: 'error',
    //     duration: 5 * 1000
    //   })

    //   // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
    //   if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
    //     // to re-login
    //     MessageBox.confirm(
    //       'You have been logged out, you can cancel to stay on this page, or log in again',
    //       'Confirm logout',
    //       {
    //         confirmButtonText: 'Re-Login',
    //         cancelButtonText: 'Cancel',
    //         type: 'warning'
    //       }
    //     ).then(() => {
    //       store.dispatch('user/resetToken').then(() => {
    //         location.reload()
    //       })
    //     })
    //   }
    //   return Promise.reject(new Error(res.message || 'Error'))
    // } else {
    //   return res
    // }
    return res
  },
  (error) => {
    console.log(error)
    if (error && error.response) {
      switch (error.response.status) {
        case 401:
          store.dispatch('user/logout')
          error.message = '登录信息已过期，请重新登录!'
          break
        case 422:
          error.message = getErrorMsg(error.response.data.message)
          break
        case 404:
          error.message = '哎呀~没有找到该地址'
          // router.push('*')
          break
        case 500:
          error.message = '槽糕~服务器竟然出错了，请稍后重试'
          break
        case 501:
          error.message = '网络未实现'
          break
        case 502:
          error.message = '网络错误'
          break
        case 503:
          error.message = '服务不可用'
          break
        case 504:
          error.message = '网络超时'
          break
        case 505:
          error.message = 'http版本不支持该请求'
          break
        default:
          error.message = `连接错误${error.response.status}`
      }
    } else {
      error.message = '连接到服务器失败'
    }

    if (error.response.status !== 401) {
      console.log('err' + error) // for debug
      Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000
      })
    }

    return Promise.reject(error)
  }
)
function getErrorMsg(msg) {
  console.log(msg)
  const str_arr = JSON.parse(msg)
  let str = ''

  if (str_arr[0]) {
    str = str_arr[0].error[0]
  }
  return str
}
export default service
