/*
 * @Author: your name
 * @Date: 2020-11-23 19:15:24
 * @LastEditTime: 2020-11-24 10:22:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/utils/phone.js
 */
//手机号加密
export default function(val) {
  var phone = val //获取到的电话信息
  phone = '' + phone
  var reg = /(\d{3})\d{4}(\d{4})/ //正则表达式
  var phone = phone.replace(reg, '$1****$2')
  return phone
}
