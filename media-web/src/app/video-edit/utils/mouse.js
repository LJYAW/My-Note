/*
 * @Author: zzx
 * @Date: 2020-08-14 16:06:50
 * @LastEditTime: 2020-08-14 16:07:17
 * @FilePath: /video_edit/src/utils/mouse.js
 */
var getMouseXY = function(e) {
  // 函数用于获取鼠标的位置
  var x = 0
  var y = 0
  e = e || window.event

  if (e.pageX) {
    x = e.pageX
    y = e.pageY
  } else {
    x = e.clientX + document.body.scrollLeft - document.body.clientLeft
    y = e.clientY + document.body.scrollTop - document.body.clientTop
  }
  return {
    x: x,
    y: y
  }
}

export { getMouseXY }
