/*
 * @Author: your name
 * @Date: 2021-04-20 16:48:03
 * @LastEditTime: 2021-04-20 16:48:35
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /opinion-monitor-web/src/utils/downloadIamge.js
 */
export function downloadIamge(imgsrc, name) {
  var image = new Image()
  // 解决跨域 Canvas 污染问题
  image.setAttribute('crossOrigin', 'anonymous')
  image.onload = function() {
    var canvas = document.createElement('canvas')
    canvas.width = image.width
    canvas.height = image.height
    var context = canvas.getContext('2d')
    context.drawImage(image, 0, 0, image.width, image.height)
    var url = canvas.toDataURL('image/png') // 得到图片的base64编码数据

    var a = document.createElement('a') // 生成一个a元素
    var event = new MouseEvent('click') // 创建一个单击事件
    a.download = name || 'photo' // 设置图片名称
    a.href = url // 将生成的URL设置为a.href属性
    a.dispatchEvent(event) // 触发a的单击事件
  }
  image.src = imgsrc
}
