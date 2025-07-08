/*
 * @Author: your name
 * @Date: 2021-08-05 16:01:42
 * @LastEditTime: 2021-08-05 17:23:39
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/utils/throttle.js
 */

// 节流的效果，在规定的每300毫秒触发一次
export default function throttle(method, time = 300) {
  var timer = null
  return function() {
    var context = this
    var args = arguments
    if (!timer) {
      timer = setTimeout(() => {
        method.apply(context, args)
        timer = null
      }, time)
    }
  }
}
