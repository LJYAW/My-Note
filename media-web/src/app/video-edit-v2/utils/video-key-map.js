/*
 * @Author: your name
 * @Date: 2021-08-20 17:59:15
 * @LastEditTime: 2021-08-31 16:47:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/utils/video-key-map.js
 */
export default (video) => {
  // return false 禁止函数内部执行其他的事件或者方法
  var vol = 0.1 // 1代表100%音量，每次增减0.1
  var seekTime = 3 // 单位秒，每次增减10秒

  document.onkeyup = function(event) {
    // 键盘事件
    const e = event || window.event
    const code = e.keyCode
    switch (code) {
      case 37:
        video.currentTime !== 0 ? (video.currentTime -= seekTime) : 1
        e.preventDefault()
        break
      // 向右键
      case 39:
        video.volume !== video.duration ? (video.currentTime += seekTime) : 1
        e.preventDefault()
        break
        // 空格键
      case 32:
        // 按空格键 判断当前是否暂停
        video.paused ? video.play() : video.pause()
        e.preventDefault()
        break
    }
  }
}
