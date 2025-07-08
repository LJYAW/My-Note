/*
 * @Author: your name
 * @Date: 2021-08-12 16:17:47
 * @LastEditTime: 2021-08-12 16:32:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/utils/getImageSize.js
 */
export default function(src) {
  return new Promise((resolve, reject) => {
    var image = new Image()
    image.src = src
    image.onload = () => {
      resolve({
        w: image.width,
        h: image.height
      })
    }
    image.onerror = () => {
      reject('图片加载失败')
    }
  })
}
