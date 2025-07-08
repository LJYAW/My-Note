/*
 * @Author: your name
 * @Date: 2021-09-27 17:36:48
 * @LastEditTime: 2021-09-27 20:00:06
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/utils/reset-img-size.js
 */

/**
 *
 * @param {String} imgUrl
 */
export const canvasToImgUrl = (imgUrl, canvasW = 50, canvasH = 50) => {
  return new Promise((resolve, reject) => {
    try {
      const canvas = document.createElement('canvas')
      canvas.width = canvasW
      canvas.height = canvasH
      const context = canvas.getContext('2d')

      const img = new Image()
      img.crossOrigin = 'Anonymous'
      img.src = imgUrl

      img.onload = () => {
        context.drawImage(img, 0, 0, canvasW, canvasH)
        const dataUrl = canvas.toDataURL('image/png')
        resolve(dataUrl)
      }
    } catch (err) {
      reject(err)
    }
  })
}

export const getContainPosition = (imgDom, canvasW, canvasH) => {
  let dx = 0
  let dy = 0
  let dw = 0
  let dh = 0
  const imgW = imgDom.width
  const imgH = imgDom.height

  const canvasRatio = Number((canvasW / canvasH).toFixed(2))
  const imgRatio = Number((imgW / imgH).toFixed(2))

  if (imgRatio <= canvasRatio) {
    dw = imgRatio * canvasH
    dh = canvasH
    dx = (canvasW - dw) / 2
    dy = 0
  } else {
    dw = canvasW
    dh = dw / imgRatio
    dx = 0
    dy = (canvasH - dh) / 2
  }

  return {
    dx: Math.floor(dx),
    dy: Math.floor(dy),
    dw: Math.floor(dw),
    dh: Math.floor(dh)
  }
}
