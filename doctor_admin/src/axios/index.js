import axios from 'axios'
import generateSignString from './setsign'
import vue from '../main.js'
const CancelToken = axios.CancelToken // 取消请求

// axios.defaults.withCredentials = true

// axios.defaults.baseURL = '/api/v1/'
// if (process.env.NODE_ENV == 'development') {
//   axios.defaults.baseURL = '/api/v1/'
// } else {
axios.defaults.baseURL = process.env.VUE_APP_BASE_API
// console.log("process.env.VUE_APP_BASE_API", process.env.VUE_APP_BASE_API)
// console.log('process.env.VUE_APP_BASE_API', process.env.VUE_APP_BASE_API)
// axios.defaults.baseURL = 'https://test-api-magic.weijian.video/api/v1/'
// }
// if (!process.env.VUE_APP_BASE_API) {
//   axios.defaults.baseURL = '/api/v1/'
// }

// axios.defaults.baseURL = baseURL
// axios.defaults.baseURL = host
axios.defaults.timeout = 60000 // 超时设置
axios.interceptors.request.use(
  config => {
    // 设置默认请求头
    var methodArr = ['post', 'delete', 'put']
    var addSign = methodArr.includes(config.method)
    if (addSign) {
      // 说明有特殊 sign

      if (!config.headers.sign) {
        config.headers.sign = generateSignString(config.url, config.data)
      }
    }

    let user_info = JSON.parse(localStorage.getItem('user_info'))
    if (user_info) {
      config.headers.Authorization = 'Bearer ' + user_info.access_token
    }

    // config.headers['X-Requested-With'] = 'XMLHttpRequest'
    // 指定允许其他域名访问
    // config.header('Access-Control-Allow-Origin:*')
    // // 响应类型
    // config.header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE')
    // config.header('Access-Control-Request-Method', '')

    // // 响应头设置
    // config.header('Access-Control-Allow-Headers:x-requested-with,content-type')

    let cancelGroupName
    if (config.method === 'post') {
      if (config.data && config.data.cancelGroupName) {
        // post请求ajax取消函数配置
        cancelGroupName = config.data.cancelGroupName
      }
      // config.data = qs.stringify(config.data)
    } else {
      if (config.params && config.params.cancelGroupName) {
        // get请求ajax取消函数配置
        cancelGroupName = config.params.cancelGroupName
      }
    }
    if (cancelGroupName) {
      if (axios[cancelGroupName] && axios[cancelGroupName].cancel) {
        axios[cancelGroupName].cancel()
      }
      config.cancelToken = new CancelToken(c => {
        axios[cancelGroupName] = {}
        axios[cancelGroupName].cancel = c
      })
    }
    return config
  },
  error => {
    console.log('error', error)
    return Promise.reject(error)
  }
)

axios.interceptors.response.use(
  config => {
    return config
  },
  error => {
    if (error && error.response) {
      switch (error.response.status) {
        case 401:
          // localStorage.removeItem('user_info')
          // vue.$store.commit('modalShow', 'loginM')
          error.message = '登录信息已过期，请重新登录!'
          vue.$router.push('/')
          break
        case 422:
          // localStorage.removeItem('user_info')
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
    if (error.response.status != 401) {
      // vue.$alertMsg(error.message)
    }
    return Promise.reject(error)
  }
)

function getErrorMsg(msg) {
  console.log(msg)
  let str_arr = JSON.parse(msg)
  let str = ''

  if (str_arr[0]) {
    str = str_arr[0].error[0]
  }
  return str
}

export default axios
