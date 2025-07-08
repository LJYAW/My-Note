/*
 * @Author: your name
 * @Date: 2021-08-25 15:21:55
 * @LastEditTime: 2021-09-27 11:37:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/utils/contain-video.js
 */
/**
 *
 * @param {Number} canvasW
 * @param {Number} canvasH
 * @param {Number} imgW
 * @param {Number} imgH
 */
const getContainPosition = (canvasW, canvasH, imgW, imgH) => {
  let dx, dy, dw, dh
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

export default getContainPosition
