
/*
 * @Author: your name
 * @Date: 2021-09-14 17:02:03
 * @LastEditTime: 2021-09-24 10:38:50
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/utils/get-file-url.js
 */

// 获取本地 文件地址
export function getFileUrl(file) {
  var url = null
  if (window.createObjectURL !== undefined) {
    // basic
    url = window.createObjectURL(file)
  } else if (window.URL !== undefined) {
    // mozilla(firefox)
    url = window.URL.createObjectURL(file)
  } else if (window.webkitURL !== undefined) {
    // webkit or chrome
    url = window.webkitURL.createObjectURL(file)
  }
  return url
}

// 将base64转换为文件
export function dataURLtoFile(dataurl, filename) {
  const arr = dataurl.split(',')
  const mime = arr[0].match(/:(.*?);/)[1]
  const bstr = atob(arr[1])
  let n = bstr.length
  const u8arr = new Uint8Array(n)
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n)
  }
  return new File([u8arr], filename, { type: mime })
}

export function getBase64ToImg(url) {
  return new Promise((resolve, reject) => {
    const Img = new Image()
    Img.crossOrigin = 'Anonymous'
    Img.src = url

    Img.onload = () => { // 要先确保图片完整获取到，这是个异步事件
      let dataURL = ''
      const canvas = document.createElement('canvas') // 创建canvas元素
      const width = Img.width // 确保canvas的尺寸和图片一样
      const height = Img.height
      canvas.width = width
      canvas.height = height
      canvas.getContext('2d').drawImage(Img, 0, 0, width, height) // 将图片绘制到canvas中
      dataURL = canvas.toDataURL('image/jpeg') // 转换图片为dataURL
      resolve(dataURL)
    }
  })
}
// 根据 viedeoURl 转成 base64 地址
export function getBase64Video(file) {
  return new Promise((resolve, reject) => {
    var reader = new FileReader()
    // 传入一个参数对象即可得到基于该参数对象的文本内容
    reader.readAsDataURL(file)
    reader.onload = function(e) {
      // target.result 该属性表示目标对象的DataURL
      resolve(e.target.result)
    }
  })
}

export function cutVideoPoster(videoURL) {
  return new Promise((resolve, reject) => {
    const videoDom = document.createElement('video')
    videoDom.src = videoURL
    videoDom.preload = 'auto'

    let dataUrl = ''

    try {
      videoDom.addEventListener('loadeddata', () => {
        const canvas = document.createElement('canvas')
        canvas.width = videoDom.videoWidth
        canvas.height = videoDom.videoHeight
        canvas
          .getContext('2d')
          .drawImage(videoDom, 0, 0, canvas.width, canvas.height)
        dataUrl = canvas.toDataURL('image/png')
        resolve(dataUrl)
      })
    } catch (error) {
      alert('截取封面失败' + error)
      reject()
    }
  })
}

