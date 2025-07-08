/*
 * @Author: your name
 * @Date: 2021-09-10 16:39:35
 * @LastEditTime: 2021-09-10 16:41:57
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/subtitleCreation/js/ms-to-second.js
 */
export default (s) => {
  var result = '00:00.000'

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

    ms = (
      Math.floor(parseFloat(s) - second * 60000 - minute * 1000) / 1
    ).toFixed()
    if (ms < 10) {
      ms = '00' + ms
    }
    result = second + ':' + minute + '.' + ms
  }

  return result
}
