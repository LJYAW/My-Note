/*
 * @Author: your name
 * @Date: 2021-01-14 14:32:38
 * @LastEditTime: 2021-10-11 17:32:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/axios/http.js
 */
import service from './request'
// import isEmpty from 'lodash/camelCase'

export function post(url, data) {
  return new Promise((resolve, reject) => {
    service.post(url, data).then(
      res => {
        if (res.data.code === '0000') {
          resolve(res.data)
        } else {
          reject(res.data)
        }
      },
      err => {
        reject(err)
      }
    )
  })
}
export function get(url, data) {
  return new Promise((resolve, reject) => {
    service.get(url, { params: data }).then(
      res => {
        if (res.data.code === '0000') {
          resolve(res.data)
        } else {
          reject(res.data)
        }
      },
      err => {
        reject(err)
      }
    )
  })
}

export function deleteRun(url, data) {
  return new Promise((resolve, reject) => {
    service.delete(url, data).then(
      res => {
        if (res.data.code === '0000') {
          resolve(res.data)
        } else {
          reject(res.data)
        }
      },
      err => {
        reject(err)
      }
    )
  })
}
