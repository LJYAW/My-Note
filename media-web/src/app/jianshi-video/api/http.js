/*
 * @Author: your name
 * @Date: 2021-01-14 14:32:38
 * @LastEditTime: 2021-09-16 16:02:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/axios/http.js
 */
import service from './index'
const SUCCESS_CODE = code => code === '200'
const baseURL = process.env.VUE_APP_BASE_API_JIANSHI
export function post(url, data, header) {
  return new Promise((resolve, reject) => {
    service.post(`${baseURL + url}`, data, { headers: header }).then(
      res => {
        if (SUCCESS_CODE(res.data.code)) {
          resolve(res.data)
        } else {
          reject(res.data)
        }
      },
      err => {
        console.log('🚀 ~ file: http.js ~ line 25 ~ newPromise ~ err', err)
        // reject(err)
      }
    )
  })
}
export function get(url, data, header) {
  return new Promise((resolve, reject) => {
    service.get(`${baseURL + url}`, { params: data, headers: header }).then(
      res => {
        if (SUCCESS_CODE(res.data.code)) {
          resolve(res.data)
        } else {
          reject(res.data)
        }
      },
      err => {
        console.log('🚀 ~ file: http.js ~ line 43 ~ newPromise ~ err', err)
        // reject(err)
      }
    )
  })
}

export function deleteRun(url, header) {
  return new Promise((resolve, reject) => {
    service.delete(`${baseURL + url}`, { headers: header }).then(
      res => {
        if (SUCCESS_CODE(res.data.code)) {
          resolve(res.data)
        } else {
          reject(res.data)
        }
      },
      err => {
        console.log('🚀 ~ file: http.js ~ line 62 ~ newPromise ~ err', err)
        // reject(err)
      }
    )
  })
}

export function put(url, data, header) {
  return new Promise((resolve, reject) => {
    service.put(`${baseURL + url}`, data, { headers: header }).then(
      res => {
        if (SUCCESS_CODE(res.data.code)) {
          resolve(res.data)
        } else {
          reject(res.data)
        }
      },
      err => {
        console.log('🚀 ~ file: http.js ~ line 81 ~ newPromise ~ err', err)

        // reject(err, 'put')
      }
    )
  })
}
