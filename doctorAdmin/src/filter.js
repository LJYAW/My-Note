import Vue from 'vue'
import { spawn } from 'child_process'

Vue.filter('msToSecond', (s) => {
  var result = '00:00:00'

  var hour, minute, second, ms

  if (s > 0) {
    second = Math.floor(s / 60000)
    if (second < 10) {
      second = '0' + second
    }

    minute = Math.floor(s / 1000 - second * 60)
    if (minute < 10) {
      minute = '0' + minute
    }

    ms = (Math.floor(parseFloat(s) - second * 60000 - minute * 1000) / 10).toFixed()
    if (ms < 10) {
      ms = '0' + ms

      result = second + ':' + minute + '.' + ms
    }

    return result
  }
})

Vue.filter('MillisecondToDate', (msd) => {
  // 毫秒 换算 55分钟58秒
  var time = parseFloat(msd) / 1000
  if (time != null && time != '') {
    if (time > 60 && time < 60 * 60) {
      time = parseInt(time / 60.0) + '分钟' + parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60) + '秒'
    } else if (time >= 60 * 60 && time < 60 * 60 * 24) {
      time =
        parseInt(time / 3600.0) +
        '小时' +
        parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) +
        '分钟' +
        parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) - parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60) +
        '秒'
    } else {
      time = parseInt(time) + '秒'
    }
  }
  return time
})

Vue.filter('msDecorate', (s) => {
  var newStr = s.substring(0, 6)
  var Str = '<span style="font-size: 12px">' + s.substr(6, 2) + '</span>'
  return newStr + Str
})

Vue.filter('newDate', (s) => {
  var date_str = '2019年5月12号'
  var now = new Date(s)
  var year = now.getFullYear()
  var month = now.getMonth() + 1
  var date = now.getDate()
  var hour = now.getHours()
  var minute = now.getMinutes()
  var second = now.getSeconds()
  date_str = year + '年' + month + '月' + date + '日'
  return date_str
})

Vue.filter('timeFilter', (seconds) => {
  let ss = Math.floor(seconds) // 秒
  let mm = 0 // 分
  let hh = 0 // 小时
  if (ss > 60) {
    mm = Math.floor(ss / 60)
    ss = Math.floor(ss % 60)
  }
  if (mm > 60) {
    hh = Math.floor(mm / 60)
    mm = Math.floor(mm % 60)
  }

  let result = ('00' + Math.floor(ss)).slice(-2)
  if (mm > 0) result = ('00' + Math.floor(mm)).slice(-2) + ':' + result
  else result = '00:' + result

  if (hh > 0) result = ('00' + Math.floor(hh)).slice(-2) + ':' + result
  return result
})

Vue.filter('msToTime', (msd) => {
  var time = msd / 1000

  if (time != null && time != '') {
    if (time > 60 && time < 60 * 60) {
      // 只有分钟和秒的情况
      let min = 0
      let sec = 0
      // 分钟前面的数字小于10位在前面补个0
      if (parseInt(time / 60.0) < 10) {
        min = '0' + parseInt(time / 60.0)
      } else {
        min = parseInt(time / 60.0)
      }
      // 秒前面的数字小于10位在前面补个0
      if (parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60) < 10) {
        sec = '0' + parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60)
      } else {
        sec = parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60)
      }
      time = '00:' + min + ':' + sec + ''
    } else if (time >= 60 * 60 && time < 60 * 60 * 24) {
      // 时分秒的情况
      //            console.log(parseInt(time / 3600.0));
      // 小时前面的数字小于10位在前面补个0
      var hour = 0
      var min = 0
      var sec = 0
      if (parseInt(time / 3600.0) < 10) {
        hour = '0' + parseInt(time / 3600.0)
      } else {
        hour = parseInt(time / 3600.0)
      }
      // 分钟前面的数字小于10位在前面补个0
      if (parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) < 10) {
        min = '0' + parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)
      } else {
        min = parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)
      }
      // 秒前面的数字小于10位在前面补个0
      if (parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) - parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60) < 10) {
        sec = '0' + parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) - parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60)
      } else {
        sec = parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) - parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60)
      }
      time = hour + ':' + min + ':' + sec + ''
    } else {
      // 单纯是秒的情况
      let sec = 0
      // 分钟前面的数字小于10位在前面补个0
      if (parseInt(time) < 10) {
        sec = '0' + parseInt(time)
      } else {
        sec = parseInt(time)
      }
      time = '00:00:' + parseInt(time) + ''
    }
  }
  return time
})
Vue.filter('timestampToTime', (time) => {
  let date = new Date(time * 1000)
  let Y = date.getFullYear() + '-'
  let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
  let D = date.getDate() + ' '
  let h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':'
  let m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':'
  let s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
  return h + m + s
})

Vue.filter('numberToThousand', (number) => {
  var num = parseInt(number)
  var str = '1万＋'
  if (num > 10000) {
    str = Math.floor(num / 10000) + '万+'
  }
  if (num > 100000000) {
    str = Math.floor(num / 100000000) + '亿+'
  }
  if (num < 10000) {
    str = num
  }
  return str
})

// 补零
function getzf(num) {
  if (parseInt(num) < 10) {
    num = '0' + num
  }
  return num
}

Vue.filter('phoneFilter', (val) => {
  let reg = /^(.{3}).*(.{4})$/
  return val.replace(reg, '$1****$2')
})
