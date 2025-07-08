/*
 * @Author: your name
 * @Date: 2021-10-20 11:19:06
 * @LastEditTime: 2021-10-26 16:14:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/api/user.js
 */
import request from '@/utils/request'
import sha256 from 'js-sha256'
export function login(data) {
  const obj = JSON.parse(JSON.stringify(data))
  obj.password = sha256(data.password)
  return request({
    url: '/user/login',
    method: 'post',
    data: obj
  })
}

// export function getInfo(token) {
//   return request({
//     url: '/vue-admin-template/user/info',
//     method: 'get',
//     params: { token }
//   })
// }

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}
