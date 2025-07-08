/*
 * @Author: your name
 * @Date: 2021-08-27 11:58:09
 * @LastEditTime: 2021-09-01 11:32:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit-v2/utils/ms-to-second.js
 */
export const msToSecond = (s) => {
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

    ms = (
      Math.floor(parseFloat(s) - second * 60000 - minute * 1000) / 10
    ).toFixed()
    if (ms < 10) {
      ms = '0' + ms
    }
    result = second + ':' + minute + '.' + ms
  }

  return result
}
