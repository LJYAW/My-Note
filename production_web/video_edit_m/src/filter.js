/*
 * @Author: zzx
 * @Date: 2020-09-17 16:06:50
 * @LastEditTime: 2020-09-17 16:06:57
 * @FilePath: /video_edit/src/filter.js
 */
import Vue from 'vue'

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
    }
    result = second + ':' + minute + '.' + ms
  }

  return result
})
