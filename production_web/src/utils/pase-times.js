/*
 * @Author: your name
 * @Date: 2021-02-19 11:01:38
 * @LastEditTime: 2021-03-09 16:19:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/utils/times.js
 */
// 时间戳转 时分秒
export function timesToHMS(timestamp) {
  var time
  var hours = parseInt((timestamp % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
  var minutes = parseInt((timestamp % (1000 * 60 * 60)) / (1000 * 60))
  var seconds = (timestamp % (1000 * 60)) / 1000
  time =
    (hours < 10 ? '0' + hours : hours) +
    ':' +
    (minutes < 10 ? '0' + minutes : minutes) +
    ':' +
    (seconds < 10 ? '0' + seconds : seconds)
  return time
}

export function formatDate(date_str) {
  // '2020-3-2'
  if (!isNaN(date_str)) {
    var now = new Date(date_str)
  }

  var year = now.getFullYear() // 取得4位数的年份
  var month = now.getMonth() + 1 + '' // 取得日期中的月份，其中0表示1月，11表示12月
  var date = now.getDate() + '' // 返回日期月份中的天数（1到31）
  // var hour = now.getHours() // 返回日期中的小时数（0到23）
  // var minute = now.getMinutes() // 返回日期中的分钟数（0到59）
  // var second = now.getSeconds() // 返回日期中的秒数（0到59）
  month = month.replace(/\b\d\b/g, '0$&')
  date = date.replace(/\b\d\b/g, '0$&')

  return year + '-' + month + '-' + date
}

// 时间格式转秒
export function timeToS(str) {
  // 00:00:00 转秒
  const dateArr = str.split(':')
  const H = Number(dateArr[0]) * 3600000
  const M = Number(dateArr[1]) * 60000
  const s = Number(dateArr[2]) * 1000
  return Math.floor((H + M + s) / 60)
}
export function timeToSecond(time) {
  var hour = time.split(':')[0]
  var min = time.split(':')[1]
  var sec = time.split(':')[2]

  const s = Number(hour * 3600) + Number(min * 60) + Number(sec)
  return s
}
