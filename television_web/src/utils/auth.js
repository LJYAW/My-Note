/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-04-26 16:49:19
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/utils/auth.js
 */
export function getToken() {
  return localStorage.getItem('token') || ''
}

export function setToken(token) {
  localStorage.setItem('token', token)
}

export function removeToken() {
  localStorage.removeItem('token')
}
