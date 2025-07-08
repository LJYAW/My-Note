/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-03-04 13:40:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/utils/auth.js
 */
export function getToken() {
  // return sessionStorage.getItem('token') || ''
  return localStorage.getItem('token') || ''
}

export function setToken(token) {
  // sessionStorage.setItem('token', token)
  localStorage.setItem('token', token)
}

export function removeToken() {
  // sessionStorage.removeItem('token')
  localStorage.removeItem('token')
}
