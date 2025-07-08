/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-06-09 16:06:49
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/utils/auth.js
 */
export function getToken() {
  try {
    return localStorage.getItem('token') || ''
  } catch {
    console.error('获取Token 失败')
  }
}

export function setToken(token) {
  localStorage.setItem('token', token)
}

export function removeToken() {
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
