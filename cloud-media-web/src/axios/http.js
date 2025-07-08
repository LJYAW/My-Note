/*
 * @Author: your name
 * @Date: 2021-01-14 14:32:38
 * @LastEditTime: 2021-08-24 14:54:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/axios/http.js
 */
import service from './index'
import to from '@/utils/to-promise'
const SUCCESS_CODE = code => code === '200'

export function post(url, data, header) {
  return to(
    new Promise((resolve, reject) => {
      service.post(`${url}`, data, { headers: header }).then(
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
  )
}
export function get(url, data, header) {
  return to(
    new Promise((resolve, reject) => {
      service.get(`${url}`, { params: data, headers: header }).then(
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
  )
}

export function deleteRun(url, header) {
  return to(
    new Promise((resolve, reject) => {
      service.delete(`${url}`, { headers: header }).then(
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
  )
}

export function put(url, data, header) {
  return to(
    new Promise((resolve, reject) => {
      service.put(`${url}`, data, { headers: header }).then(
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
  )
}
