/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-06-07 16:45:37
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
  // sessionStorage.removeItem('token')
  localStorage.removeItem('token')
}

export function getUserInfo() {
  return JSON.parse(localStorage.getItem('user_info')) || {}
}

export function setUserInfo(data) {
  localStorage.setItem('user_info', JSON.stringify(data))
}

export function removeUserInfo() {
  localStorage.setItem('user_info', {})
}
