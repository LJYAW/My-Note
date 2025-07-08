/*
 * @Author: your name
 * @Date: 2021-08-05 16:01:51
 * @LastEditTime: 2021-08-05 17:17:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/utils/debounce.js
 */

export default (fn, delay = 300) => {
  let timer
  return function() {
    const context = this
    const args = arguments
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => {
      fn.apply(context, args)
    }, delay)
  }
}
