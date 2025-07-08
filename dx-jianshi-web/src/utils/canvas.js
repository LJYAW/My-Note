/*
 * @Author: zzx
 * @Date: 2020-05-11 16:15:20
 * @LastEditTime: 2020-08-31 18:59:54
 * @FilePath: /zhijian_web/src/utils/canvas.js
 */
export function canvasImgSrc(id) {
  const canvas = document.createElement('canvas')

  const dom = document.getElementById(id)

  if (dom.nodeName == 'VIDEO') {
    canvas.width = dom.videoWidth * 1
    canvas.height = dom.videoHeight * 1
  } else {
    canvas.width = dom.clientWidth
    canvas.height = dom.clientHeight
  }

  canvas.getContext('2d').drawImage(dom, 0, 0, canvas.width, canvas.height)
  let dataUrl = ''
  try {
    dataUrl = canvas.toDataURL('image/png')
  } catch (error) {
    alert('截取封面失败请手动上传\n' + error)
    dataUrl = ''
  }

  return dataUrl
}

export function imgUrlTofile(url) {
  return new Promise((resolve, reject) => {
    var Img = new Image()
    var dataURL = ''

    // Img.src = url +"?v=" + Math.random(); // 处理缓存,fix缓存bug,有缓存，浏览器会报错;
    // 下面已修正ios的兼容微信打开失败的原因
    Img.src = url.replace(/^http/, 'https') + '&v=' + Math.random() // 处理缓存,fix缓存bug,有缓存，浏览器会报错;
    Img.crossOrigin = 'anonymous' // 解决控制台跨域报错的问题

    Img.onload = function() {
      // 要先确保图片完整获取到，这是个异步事件
      var canvas = document.createElement('canvas') // 创建canvas元素
      var width = Img.width // 确保canvas的尺寸和图片一样
      var height = Img.height
      canvas.width = width
      canvas.height = height
      canvas.getContext('2d').drawImage(Img, 0, 0, width, height) // 将图片绘制到canvas中
      dataURL = canvas.toDataURL('image/jpeg') // 转换图片为dataURL
      resolve(dataURL)
    }
    Img.onerror = function() {
      reject(new Error('哦豁~ 图片转文件发生错误，请换一张图片试试'))
    }
  })
}
function drawBase64Image(img) {
  var canvas = document.createElement('canvas')
  canvas.width = img.width
  canvas.height = img.height
  var ctx = canvas.getContext('2d')
  ctx.drawImage(img, 0, 0, img.width, img.height)
  var dataURL = canvas.toDataURL('image/png')
  return dataURL
}

// 将base64转换成file对象
export function dataURLtoFile(dataurl, filename = 'file') {
  const arr = dataurl.split(',')
  const mime = arr[0].match(/:(.*?);/)[1]
  const suffix = mime.split('/')[1]
  const bstr = atob(arr[1])
  let n = bstr.length
  const u8arr = new Uint8Array(n)
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n)
  }
  return new File([u8arr], `${filename}.${suffix}`, { type: mime })
}

function convertBase64UrlToBlob(base64) {
  var urlData = base64.dataURL
  var type = base64.type
  var bytes = window.atob(urlData.split(',')[1]) // 去掉url的头，并转换为byte
  // 处理异常,将ascii码小于0的转换为大于0
  var ab = new ArrayBuffer(bytes.length)
  var ia = new Uint8Array(ab)
  for (var i = 0; i < bytes.length; i++) {
    ia[i] = bytes.charCodeAt(i)
  }
  return new Blob([ab], { type: type })
}

export function cacheExternalImage(url) {
  var img = new Image()
  var src = url
  var cvs = document.createElement('canvas')
  var ctx = cvs.getContext('2d')
  img.crossOrigin = 'Anonymous'
  img.onload = function() {
    // ctx.drawImage( img, 0, 0 );
  }
  img.src = src
  if (img.complete || img.complete === undefined) {
    img.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw=='
    img.src = src
  }
  return img
}

function dataURItoBlob(base64Data) {
  var byteString
  if (base64Data.split(',')[0].indexOf('base64') >= 0) byteString = atob(base64Data.split(',')[1])
  else byteString = unescape(base64Data.split(',')[1])
  var mimeString = base64Data
    .split(',')[0]
    .split(':')[1]
    .split(';')[0]
  var ia = new Uint8Array(byteString.length)
  for (var i = 0; i < byteString.length; i++) {
    ia[i] = byteString.charCodeAt(i)
  }
  return new Blob([ia], {
    type: mimeString
  })
}
