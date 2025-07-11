/*
 * @Author: your name
 * @Date: 2021-07-07 17:13:42
 * @LastEditTime: 2021-07-07 17:14:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/users/js/time.js
 */
export default function getYMDHMS(timestamp) {
  const time = new Date(timestamp)
  const year = time.getFullYear()
  let month = time.getMonth() + 1
  let date = time.getDate()
  let hours = time.getHours()
  let minute = time.getMinutes()
  let second = time.getSeconds()

  if (month < 10) { month = '0' + month }
  if (date < 10) { date = '0' + date }
  if (hours < 10) { hours = '0' + hours }
  if (minute < 10) { minute = '0' + minute }
  if (second < 10) { second = '0' + second }
  return year + '-' + month + '-' + date + ' ' + hours + ':' + minute + ':' + second
}
