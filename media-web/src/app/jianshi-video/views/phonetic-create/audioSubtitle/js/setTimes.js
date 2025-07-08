/*
 * @Author: your name
 * @Date: 2021-09-09 14:50:50
 * @LastEditTime: 2021-09-10 17:26:06
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/subtitleCreation/js/setTimes.js
 */
// 转时间戳
export function msToSecondtamp(times) {
  const minute = Number(times.split(':')[0]) * 1000 * 60
  const second = Number(times.split('.')[0].split(':')[1]) * 1000
  const millisecond = Number(times.split('.')[1])
  const result = minute + second + millisecond
  return result
}
// 转时间 （00:09.100）
export function msToSecond(times) {
  const minute = Math.floor((times % (1000 * 60 * 60)) / (1000 * 60))
  const seconds = Math.round((times % (1000 * 60)) / 1000)
  const millisecond = times - ((minute * 1000 * 60) + (seconds * 1000))

  const minutesStr = minute > 10 ? minute + '' : '0' + minute
  const secondssStr = seconds > 10 ? seconds + '' : '0' + seconds
  const result = minutesStr + ':' + secondssStr + '.' + millisecond
  return result
}
