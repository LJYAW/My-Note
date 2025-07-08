/*
 * @Author: your name
 * @Date: 2021-08-03 19:38:10
 * @LastEditTime: 2021-09-28 17:17:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/utils/filter.js
 */
exports.timesToHMS = timestamp => {
  var time
  var hours = parseInt(timestamp / (1000 * 60 * 60))
  var minutes = parseInt((timestamp % (1000 * 60 * 60)) / (1000 * 60))
  var seconds = Math.floor((timestamp % (1000 * 60)) / 1000)
  time =
     (hours < 10 ? '0' + hours : hours) +
     ':' +
     (minutes < 10 ? '0' + minutes : minutes) +
     ':' +
     (seconds < 10 ? '0' + seconds : seconds)
  return time
}

exports.timesToYMD = timestamp => {
  var now = new Date(timestamp)
  var year = now.getFullYear() // 取得4位数的年份
  var month = now.getMonth() + 1 + '' // 取得日期中的月份，其中0表示1月，11表示12月
  var date = now.getDate() + '' // 返回日期月份中的天数（1到31）
  var hour = now
    .getHours()
    .toString()
    .padStart(2, '0') // 返回日期中的小时数（0到23）
  var minute = now
    .getMinutes()
    .toString()
    .padStart(2, '0') // 返回日期中的分钟数（0到59）
  var second = now
    .getSeconds()
    .toString()
    .padStart(2, '0') // 返回日期中的秒数（0到59）
  month = month.replace(/\b\d\b/g, '0$&')
  date = date.replace(/\b\d\b/g, '0$&')

  return year + '-' + month + '-' + date + '  ' + hour + ':' + minute + ':' + second
}

exports.vcaStatus = status => {
  var msg = ''
  if (status === 1) {
    msg = '已分析'
  } else if (status === 2) {
    msg = '未分析'
  } else if (status === 3) {
    msg = '分析中'
  } else if (status === 4) {
    msg = '分析失败'
  }
  return msg
}
exports.clipStatus = status => {
  var msg = ''
  if (status === 0) {
    msg = '准备剪辑'
  } else if (status === 1) {
    msg = '正在剪辑'
  } else if (status === 2) {
    msg = '剪辑成功'
  } else {
    msg = '剪辑失败'
  }
  return msg
}
exports.initSize = limit => {
  var size = ''
  if (limit < 1 * 1024) {
    // 小于1KB，则转化成B
    size = limit.toFixed(2) + 'B'
  } else if (limit < 1 * 1024 * 1024) {
    // 小于1MB，则转化成KB
    size = (limit / 1024).toFixed(2) + 'KB'
  } else if (limit < 1 * 1024 * 1024 * 1024) {
    // 小于1GB，则转化成MB
    size = (limit / (1024 * 1024)).toFixed(2) + 'MB'
  } else {
    // 其他转化成GB
    size = (limit / (1024 * 1024 * 1024)).toFixed(2) + 'GB'
  }
  var sizeStr = size + '' // 转成字符串
  var index = sizeStr.indexOf('.') // 获取小数点处的索引
  var dou = sizeStr.substr(index + 1, 2) // 获取小数点后两位的值
  if (dou === '00') {
    // 判断后两位是否为00，如果是则删除00
    return sizeStr.substring(0, index) + sizeStr.substr(index + 3, 2)
  }
  return size
}
