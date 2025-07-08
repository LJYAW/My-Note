// 时间戳转 时分秒
export function timesToHMS(timestamp) {
  var date = new Date(timestamp)
  var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':'
  var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':'
  var s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()

  var strDate = h + m + s
  return strDate
}

export function formatDate(date_str) {
  // '2020-3-2'
  if (!isNaN(date_str)) {
    var now = new Date(date_str)
  }

  var year = now.getFullYear() // 取得4位数的年份
  var month = now.getMonth() + 1 + '' // 取得日期中的月份，其中0表示1月，11表示12月
  var date = now.getDate() + '' // 返回日期月份中的天数（1到31）
  var hour = now.getHours() // 返回日期中的小时数（0到23）
  var minute = now.getMinutes() // 返回日期中的分钟数（0到59）
  var second = now.getSeconds() // 返回日期中的秒数（0到59）
  month = month.replace(/\b\d\b/g, '0$&')
  date = date.replace(/\b\d\b/g, '0$&')

  return year + '-' + month + '-' + date
}
