/*
 * @Author: your name
 * @Date: 2021-01-14 14:32:38
 * @LastEditTime: 2021-07-20 11:36:35
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/axios/http.js
 */
import service from './request'
import to from '../utils/to-promise'
const SUCCESS_CODE = (code) => code === '200' || code === '0000'

export function post(url, data) {
  return to(
    new Promise((resolve, reject) => {
      service.post(`${url}`, data).then(
        (res) => {
          if (SUCCESS_CODE(res.data.code)) {
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
          if (SUCCESS_CODE(res.data.code)) {
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
          if (SUCCESS_CODE(res.data.code)) {
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
          if (SUCCESS_CODE(res.data.code)) {
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
