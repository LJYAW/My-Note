/*
 * @Author: your name
 * @Date: 2021-08-18 14:44:32
 * @LastEditTime: 2021-08-18 14:45:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/utils/mobile.js
 */
export default function(phone) {
  var reg = /^(\d{3})\d{4}(\d{4})$/
  if (phone) return phone.replace(reg, '$1****$2')
  return ''
}
