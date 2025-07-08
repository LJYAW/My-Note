import * as dom from './dom.js'
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

// 传入参数 DOM id  是否支持上下变形 MAXWIDTH 支持callback
// 移动DOM 函数
var translation = function(id, xy = 'x', MAXWIDTH = 10000, callback) {
  var Drag = document.getElementById(id)

  Drag.onmousedown = function(event) {
    var e = event || window.event
    if (e.stopPropagation) {
      e.stopPropagation()
    } else {
      e.cancelBubble = true
    }

    var translateX = parseInt(dom.getTranslateX(Drag))

    if (!translateX) {
      translateX = 0
    }

    var disX = e.clientX - translateX

    // 如果传入 xy 表明支持上下的变形
    if (xy == 'xy') {
      var disY = e.clientY - Drag.offsetTop
    }
    document.onmousemove = function(event) {
      var e = event || window.event
      if (e.stopPropagation) {
        e.stopPropagation()
      } else {
        e.cancelBubble = true
      }

      var X = e.clientX - disX
      X = maxWidth(X, MAXWIDTH)

      Drag.style.transform = 'translateX(' + X + 'px)'
      Drag.style.top = e.clientY - disY + 'px'
    }
  }
  document.onmouseup = function(event) {
    var e = event || window.event
    if (e.stopPropagation) {
      e.stopPropagation()
    } else {
      e.cancelBubble = true
    }

    document.onmousemove = null
    if (callback && typeof callback === 'function') {
      callback()
    }
  }
}

var touchChangeSize = function(xy, id, MAXWIDTH, callback) {
  var Drag = document.getElementById(id)
  Drag.onmousedown = function(event) {
    var e = event || window.event
    if (e.stopPropagation) {
      e.stopPropagation()
    } else {
      e.cancelBubble = true
    }

    var translateX = parseInt(dom.getTranslateX(Drag))
    const mX = getMouseXY(e).x
    const translateX_copy = translateX
    const offsetWidth = Drag.offsetWidth

    document.onmousemove = function(event) {
      var e = event || window.event
      if (e.stopPropagation) {
        e.stopPropagation()
      } else {
        e.cancelBubble = true
      }

      let DOMWidth = offsetWidth

      if (xy == 'right') {
        var w = (DOMWidth += getMouseXY(e).x - mX)
        w = maxWidth(w, MAXWIDTH)
      }

      if (xy == 'left') {
        let x = translateX_copy + (getMouseXY(e).x - mX)
        x = maxWidth(x, MAXWIDTH)
        Drag.style.transform = 'translateX(' + x + 'px)'
        let w = (DOMWidth += translateX_copy - x)
      }

      Drag.style.width = w + 'px'
    }
  }

  document.onmouseup = function(event) {
    var e = event || window.event
    if (e.stopPropagation) {
      e.stopPropagation()
    } else {
      e.cancelBubble = true
    }

    document.onmousemove = null
    if (callback && typeof callback === 'function') {
      callback()
    }
  }
}
// 吸附值
const RANGE = 2
// 限制 位置
var maxWidth = function(w, MAXWIDTH) {
  var x = Math.min(Math.max(0, w), MAXWIDTH)

  // 磁性吸附部分
  if (x < RANGE) {
    x = 0
  }
  if (MAXWIDTH - x < RANGE) {
    x = MAXWIDTH
  }
  return x
}

export { getMouseXY, translation, touchChangeSize }
