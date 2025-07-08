const actions = {}

actions.openWin = function(url) {
  var a = document.createElement('a') // 创建a标签
  a.setAttribute('href', url)
  a.setAttribute('target', '_blank')
  document.body.appendChild(a)
  a.click() // 执行当前对象
}
// / 屏蔽鼠标右键，F12及其它审查元素功能
actions.noViewing = function() {
  var href = window.location.href

  if (href.indexOf('zzxdev') > 1) {
    return
  }
  // 禁用右键（防止右键查看源代码）
  window.oncontextmenu = function() {
    return false
  }
}
// 检查 URL是否合法
// 建议的正则
actions.checkoutIsURL = function(str) {
  /* eslint-disable no-useless-escape */
  var is_error = str.match(/(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/g)

  if (!is_error || !str) {
    return false
  } else {
    return true
  }
}

actions.downloadVideo = function(url) {
  // if (window.localStorage.getItem('user_info')) {
  //   var token = JSON.parse(window.localStorage.getItem('user_info'))
  // }
  // var objectUrl = url + '&Authorization=Bearer ' + token
  var btn = document.createElement('a')
  // console.log(objectUrl)

  btn.setAttribute('target', '_blank') // download属性
  btn.setAttribute('download', '') // download属性
  btn.setAttribute('href', url) // href链接

  btn.click() // 自执行点击事件
}

// 对象数组去重 传入 arr key 确定你所要过滤的属性值
actions.unique = function(arr, key) {
  if (!Array.isArray(arr)) {
    console.log('type error!')
    return
  }

  var obj = {}
  arr = arr.reduce((cur, next) => {
    obj[next[key]] ? '' : (obj[next[key]] = true && cur.push(next))
    return cur
  }, [])
  return arr
}

// 屏蔽鼠标右键，F12及其它审查元素功能

// 改变对象的KEY
actions.changeObjKey = function(data, keyMap) {
  var newObj = Object.keys(data).reduce((newData, key) => {
    const newKey = keyMap[key] || key
    newData[newKey] = data[key]
    return newData
  }, {})
  return newObj
}

// 动态创建 script 标签
actions.readyState = false
actions.createScript = function(src, link_id, callback) {
  var oHead = document.getElementsByTagName('HEAD').item(0)
  var script = document.createElement('script')
  script.setAttribute('id', link_id)
  script.type = 'text/javascript'
  script.src = src
  var linkId = document.getElementById(link_id)
  if (!linkId) {
    oHead.appendChild(script)
  } else {
    callback()
  }

  script.onload = script.onreadystatechange = function() {
    if (!this.readyState || this.readyState === 'loaded' || this.readyState === 'complete') {
      if (callback && typeof callback === 'function') {
        callback()
      }
      script.onload = script.onreadystatechange = null
    }
  }
}
// 深拷贝
function forEach(array, iteratee) {
  let index = -1
  const length = array.length
  while (++index < length) {
    iteratee(array[index], index)
  }
  return array
}
actions.clone = function(target, map = new WeakMap()) {
  if (!target) {
    return
  }

  if (typeof target === 'object') {
    const isArray = Array.isArray(target)
    const cloneTarget = isArray ? [] : {}

    if (map.get(target)) {
      return target
    }
    map.set(target, cloneTarget)

    const keys = isArray ? undefined : Object.keys(target)
    forEach(keys || target, (value, key) => {
      if (keys) {
        key = value
      }
      cloneTarget[key] = actions.clone(target[key], map)
    })

    return cloneTarget
  } else {
    return target
  }
}

/* 鼠标移入移出事件 防止多次触发 */
actions.contains = function(parentNode, childNode) {
  if (parentNode.contains) {
    return parentNode != childNode && parentNode.contains(childNode)
  } else {
    return !!(parentNode.compareDocumentPosition(childNode) & 16)
  }
}

actions.imgLoad = function(url, callback) {
  var img = new Image()
  img.src = url
  if (img.complete) {
    callback(img.width, img.height)
  } else {
    img.onload = function() {
      callback(img.width, img.height)
      img.onload = null
    }
  }
}

actions.checkHover = function(e, target) {
  if (getEvent(e).type == 'mouseover') {
    return !actions.contains(target, getEvent(e).relatedTarget || getEvent(e).fromElement) && !((getEvent(e).relatedTarget || getEvent(e).fromElement) === target)
  } else {
    return !actions.contains(target, getEvent(e).relatedTarget || getEvent(e).toElement) && !((getEvent(e).relatedTarget || getEvent(e).toElement) === target)
  }
}
function getEvent(e) {
  return e || window.event
}

/* 鼠标移入移出事件 防止多次触发 */

export default actions
