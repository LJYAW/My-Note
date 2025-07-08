/*
 * @Author: your name
 * @Date: 2021-04-20 15:39:17
 * @LastEditTime: 2021-04-22 14:41:07
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /opinion_monitor_web/src/utils/canvas.js
 */
export function canvasImgSrc(id) {
  const canvas = document.createElement('canvas')

  const dom = document.getElementById(id)

  if (dom.nodeName === 'VIDEO') {
    canvas.width = dom.videoWidth * 1
    canvas.height = dom.videoHeight * 1
  } else {
    canvas.width = dom.clientWidth
    canvas.height = dom.clientHeight
  }
  console.log(canvas)

  canvas.getContext('2d').drawImage(dom, 0, 0, canvas.width, canvas.height)
  let dataUrl = ''
  try {
    dataUrl = canvas.toDataURL('image/png')
  } catch (error) {
    // alert('截取封面失败请手动上传\n' + error)
    dataUrl = ''
  }
  return dataUrl
}

export function canvasImgSrcIframe(id, vidoeId) {
  const canvas = document.createElement('canvas')

  const dom = document.getElementById(id).contentWindow.document.getElementById(vidoeId)

  if (dom.nodeName === 'VIDEO') {
    canvas.width = dom.videoWidth * 1
    canvas.height = dom.videoHeight * 1
  } else {
    canvas.width = dom.clientWidth
    canvas.height = dom.clientHeight
  }
  console.log(canvas)

  canvas.getContext('2d').drawImage(dom, 0, 0, canvas.width, canvas.height)
  let dataUrl = ''
  try {
    dataUrl = canvas.toDataURL('image/png')
  } catch (error) {
    // alert('截取封面失败请手动上传\n' + error)
    dataUrl = ''
  }
  return dataUrl
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
export function imgToBase64(url) {
  return new Promise((resolve, reject) => {
    let canvas = document.createElement('canvas')
    const ctx = canvas.getContext('2d')
    const img = new Image()
    img.src = url

    img.crossOrigin = 'Anonymous'
    img.onload = function() {
      canvas.height = img.height
      canvas.width = img.width
      ctx.drawImage(img, 0, 0)
      var dataURL = canvas.toDataURL('image/png')
      resolve(dataURL)
      canvas = null
    }
  }).then()
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
    img.src =
      'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw=='
    img.src = src
  }
  return img
}
