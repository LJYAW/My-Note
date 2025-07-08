/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-10-29 11:08:04
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
  localStorage.removeItem('user_info')
}

export function getDirInfo() {
  return JSON.parse(localStorage.getItem('dir_info')) || {}
}

export function setDirInfo(data) {
  localStorage.setItem('dir_info', JSON.stringify(data))
}

export function removeDirInfo() {
  localStorage.removeItem('dir_info')
}
