/*
 * @Author: your name
 * @Date: 2020-12-23 18:35:51
 * @LastEditTime: 2020-12-23 18:58:56
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/utils/debounce.js
 */
// 防抖
export function _debounce(fn, delay) {
  var delay = delay || 200
  var timer
  return function() {
    var th = this
    var args = arguments
    if (timer) {
      clearTimeout(timer)
    }
    timer = setTimeout(function() {
      timer = null
      fn.apply(th, args)
    }, delay)
  }
}
// 节流
export function _throttle(fn, interval) {
  var last
  var timer
  var interval = interval || 200
  return function() {
    var th = this
    var args = arguments
    var now = +new Date()
    if (last && now - last < interval) {
      clearTimeout(timer)
      timer = setTimeout(function() {
        last = now
        fn.apply(th, args)
      }, interval)
    } else {
      last = now
      fn.apply(th, args)
    }
  }
}
