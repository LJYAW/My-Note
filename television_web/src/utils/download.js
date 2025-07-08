/*
 * @Author: your name
 * @Date: 2021-04-20 17:22:02
 * @LastEditTime: 2021-04-23 15:49:08
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-beautiful-template/src/utils/download.js
 */
export function openWin(url) {
  var a = document.createElement('a') // 创建a标签
  a.setAttribute('href', url)
  a.setAttribute('target', '_blank')
  document.body.appendChild(a)
  a.click() // 执行当前对象
}
export function download(url) {
  var objectUrl = url
  var btn = document.createElement('a')
  btn.setAttribute('target', '_blank')
  btn.setAttribute('download', '') // download属性
  btn.setAttribute('href', objectUrl) // href链接
  btn.click() // 自执行点击事件
}
