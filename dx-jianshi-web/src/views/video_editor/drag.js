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

function getElementPosition(e) {
  var left = 0
    var top = 0
  while (e != null) {
    left += e.offsetLeft
    top += e.offsetTop
    e = e.offsetParent
  }
  return { left: left, top: top }
}

let x = 0
let y = 0
const px = 0
const py = 0
let onDrag = false

// 1.移动时DOM的数据 2.移动到什么位置的DOM数据，3.预览函数 4.返回函数
export function dragEvent(eleId, targetDomId, preview, callback) {
  onDrag = true

  const elem = document.getElementById(eleId)

  const targetDom = document.getElementById(targetDomId)
  const domleft = getElementPosition(targetDom).left
  const domtop = getElementPosition(targetDom).top

  document.onmousemove = function(e) {
    if (onDrag) {
      elem.style.display = 'block'
      x = getMouseXY().x
      y = getMouseXY().y
      // console.log('dragEvent', x, y)

      elem.style.position = 'absolute'
      elem.style.top = y + 'px'
      elem.style.left = x + 'px'

      if (domtop < y + 50) {
        preview()
      }
    }
  }

  document.onmouseup = function(e) {
    if (onDrag) {
      onDrag = false
      elem.style.display = 'none'

      y = getMouseXY().y
      if (domtop < y + 50) {
        callback()
      }
    }
  }
}
