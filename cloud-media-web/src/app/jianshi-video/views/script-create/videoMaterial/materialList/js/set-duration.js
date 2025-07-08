/*
 * @Author: your name
 * @Date: 2021-07-26 17:46:25
 * @LastEditTime: 2021-09-07 15:39:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoMaterial/materialList/js/set-duration.js
 */
const TEXT_MS = 4.5 // 一秒6个字
const code = '#pau#'
const code_ms = 500
// 获取去除停顿符的素材长度
export function getLength(text) {
  const sum = text.split(code).length - 1
  return text.length - code.length * sum
}
export function getMs(text) {
  // sum有几个停顿符
  const sum = text.split(code).length - 1
  const length = getLength(text)
  return Math.ceil((length / TEXT_MS) * 1000) + code_ms * sum
}
export function getDuration(arr) {
  let duration = 0
  arr.forEach((item) => {
    duration += getMs(item.text)
  })
  return duration / 1000
}
