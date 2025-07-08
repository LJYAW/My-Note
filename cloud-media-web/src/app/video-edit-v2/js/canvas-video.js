/*
 * @Author: your name
 * @Date: 2021-08-26 17:43:29
 * @LastEditTime: 2021-08-31 15:54:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit-v2/utils/canvas-video.js
 */
export default class CanvasVideo {
  /**
  * @param {Node} canvasEl
  * @param {Node} videoEl
  * @param {Object} option
  */
  constructor(canvasEl, videoEl, option = {}) {
    this.canvas = canvasEl
    this.video = videoEl
    this.context = null

    this.drawAnimation = null

    this.canvasOptions = Object.assign(
      {
        canvasW: 952,
        canvasH: 535,
        bgColor: '#000'
      },
      option
    )

    this.drawPosition = {
      dx: 0,
      dy: 0,
      dw: 0,
      dh: 0
    }

    this.init()
    this.play = this.play.bind(this)
    this.getContainPosition = this.getContainPosition.bind(this)
  }

  init() {
    const { canvasW, canvasH } = this.canvasOptions
    this.canvas.width = canvasW
    this.canvas.height = canvasH
    this.canvas.style.display = 'block'
    this.context = this.canvas.getContext('2d')
    this.context.fillStyle = '#000'
    this.context.fillRect(0, 0, canvasW, canvasH)
  }

  drawVideo() {
    const { dx, dy, dw, dh } = this.drawPosition
    this.context.drawImage(this.video, dx, dy, dw, dh)
  }

  play() {
    this.clearCanvas()
    this.drawVideo()
    this.drawAnimation = window.requestAnimationFrame(this.play)
  }

  paused() {
    window.cancelAnimationFrame(this.drawAnimation)
    this.drawAnimation = null
  }

  drawImg(src) {
    if (!src) return

    const poster = src
    const img = new Image()
    img.crossOrigin = 'Anonymous'
    img.src = poster

    img.onload = () => {
      const { dx, dy, dw, dh } = this.drawPosition
      this.context.drawImage(img, dx, dy, dw, dh)
    }
  }

  clearCanvas() {
    const { canvasW, canvasH } = this.canvasOptions
    this.context.clearRect(0, 0, canvasW, canvasH)
  }

  getContainPosition(videoEl) {
    let dx = 0
    let dy = 0
    let dw = 0
    let dh = 0
    const { canvasW, canvasH } = this.canvasOptions
    const imgW = videoEl.videoWidth
    const imgH = videoEl.videoHeight

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

    this.drawPosition = {
      dx: Math.floor(dx) || 0,
      dy: Math.floor(dy) || 0,
      dw: Math.floor(dw) || canvasW,
      dh: Math.floor(dh) || canvasH
    }
  }
}
