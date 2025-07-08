/*
 * @Author: zzx
 * @Date: 2020-10-09 18:03:34
 * @LastEditTime: 2020-11-09 16:03:46
 * @FilePath: /zhijian_web/src/views/intellect_create/js/model_set.js
 */
// 获取视频时长
export function getVideoDuration(url) {
  return new Promise((resolve, reject) => {
    let videoDom = document.createElement('video')
    videoDom.src = url
    let duration = 0
    videoDom.addEventListener('durationchange', function(e) {
      duration = parseInt(videoDom.duration * 1000)
      resolve(duration)
    })
  })
}
//防抖
export function debounce(func, wait) {
  let timer = null
  return function() {
    let args = arguments
    if (timer) {
      clearTimeout(timer)
    }
    timer = setTimeout(() => {
      func.call(this, args)
    }, wait)
  }
}
