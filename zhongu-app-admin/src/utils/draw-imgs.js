/*
 * @Author: your name
 * @Date: 2021-11-04 15:42:38
 * @LastEditTime: 2021-11-06 23:07:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/utils/draw-imgs.js
 */
// /*
//  * @Author: your name
//  * @Date: 2021-11-03 15:47:23
//  * @LastEditTime: 2021-11-03 17:36:16
//  * @LastEditors: Please set LastEditors
//  * @Description: In User Settings Edit
//  * @FilePath: /zhongu-app-admin/src/utils/draw-imgs.js
//  */

export default class ImgMerge {
  constructor(imgs = [], options, canvasOptipns = { w: 678, h: 400 }) {
    // 图片数组默认配置项
    const defaultImgsItem = {
      url: '',
      x: 0,
      y: 0
    }
    // 导出图片的格式与压缩程度默认配置项
    const defaultOpts = {
      type: 'image/jpeg',
      compress: 1
    }
    this.canvasOptipns = canvasOptipns

    imgs.forEach((item, i, arr) => {
      arr[i] = Object.assign({}, defaultImgsItem, item)
    })

    this.imgs = imgs // 图片数组配置项
    this.opts = Object.assign({}, defaultOpts, options) // 其他配置项
    this.imgObjs = [] // 图片对象数组

    this.createCanvas() // 创建画布
    return this.outputImg() // 导出图片
  }

  // 创建画布
  createCanvas() {
    this.canvas = document.createElement('canvas')
    this.ctx = this.canvas.getContext('2d')
    console.log(this.imgs)
    const { w, h } = this.canvasOptipns
    this.canvas.width = w
    this.canvas.height = h
    this.ctx.fillStyle = '#F5F6F6'
    this.ctx.fillRect(0, 0, w, h)
  }

  // 绘制图片
  drawImg(i) {
    const img = new Image()
    img.src = this.imgs[i].url
    img.crossOrigin = 'Anonymous'
    this.imgObjs.push(img)

    return new Promise((resolve) => {
      img.onload = resolve
    })
  }

  // 导出图片
  outputImg() {
    const imgArr = []
    // 将单张图片的Promise对象存入数组
    this.imgs.forEach((item, i) => {
      imgArr.push(this.drawImg(i))
    })

    // 所有图片加载成功后将图片绘制于Canvas中，后将Canvas导出为图片
    return Promise.all(imgArr).then(() => {
      this.imgs.forEach((item, i) => {
        const drawPara = [this.imgObjs[i], this.imgs[i].x, this.imgs[i].y]
        // 此处判断参数中图片是否设置了宽高，若宽高均设置，则绘制已设置的宽高，否则按照图片默认宽高绘制
        if (this.imgs[i].width && this.imgs[i].height) {
          drawPara.push(this.imgs[i].width, this.imgs[i].height)
        }
        this.ctx.drawImage(...drawPara)
      })
      // 以base64格式导出图片
      return Promise.resolve(
        this.ctx.canvas.toDataURL(this.opts.type),
        this.opts.compress
      )
    })
  }
}
