/*
 * @Author: zzx
 * @Date: 2020-08-10 16:18:02
 * @LastEditTime: 2021-02-24 14:56:59
 * @FilePath: /video_edit/src/components/time_line/src/mixin.js
 */
export const mixinMethods = {
  methods: {
    msToSecond(s) {
      var result = '00'

      var minute, second, ms
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
          result = second
        }
      }
      return result
    }
  }
}
