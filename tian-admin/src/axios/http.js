/*
 * @Author: your name
 * @Date: 2021-01-14 14:32:38
 * @LastEditTime: 2021-06-01 18:56:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/axios/http.js
 */
import service from './request'
import to from '../utils/to-promise'
const SUCCESS_CODE = '200'

export function post(url, data) {
  return to(
    new Promise((resolve, reject) => {
      service.post(`${url}`, data).then(
        (res) => {
          if (res.data.code === SUCCESS_CODE) {
            resolve(res.data)
          } else {
            reject(res.data)
          }
        },
        (err) => {
          reject(err)
        }
      )
    })
  )
}
export function get(url, data) {
  return to(
    new Promise((resolve, reject) => {
      service.get(`${url}`, { params: data }).then(
        (res) => {
          if (res.data.code === SUCCESS_CODE) {
            resolve(res.data)
          } else {
            reject(res.data)
          }
        },
        (err) => {
          reject(err)
        }
      )
    })
  )
}

export function deleteRun(url, data) {
  return to(
    new Promise((resolve, reject) => {
      service.delete(`${url}`, { data }).then(
        (res) => {
          if (res.data.code === SUCCESS_CODE) {
            resolve(res.data)
          } else {
            reject(res.data)
          }
        },
        (err) => {
          reject(err)
        }
      )
    })
  )
}

export function put(url, data) {
  return to(
    new Promise((resolve, reject) => {
      service.put(`${url}`, data).then(
        (res) => {
          if (res.data.code === SUCCESS_CODE) {
            resolve(res.data)
          } else {
            reject(res.data)
          }
        },
        (err) => {
          reject(err, 'put')
        }
      )
    })
  )
}
