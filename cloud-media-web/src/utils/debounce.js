/*
 * @Author: your name
 * @Date: 2021-06-16 12:17:13
 * @LastEditTime: 2021-06-16 15:08:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /opinion-monit-web/src/utils/debounce.js
 */
/**
 * @desc 函数防抖
 * @param func 函数
 * @param wait 延迟执行毫秒数
 * @param immediate true 表立即执行，false 表非立即执行
 */
const debounce = function(func, wait, immediate) {
  let timeout

  return function() {
    const context = this
    const args = arguments

    if (timeout) clearTimeout(timeout)
    if (immediate) {
      var callNow = !timeout
      timeout = setTimeout(() => {
        timeout = null
      }, wait)
      if (callNow) func.apply(context, args)
    } else {
      timeout = setTimeout(function() {
        func.apply(context, args)
      }, wait)
    }
  }
}

export default debounce
