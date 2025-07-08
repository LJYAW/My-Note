/*
 * @Author: your name
 * @Date: 2021-08-12 16:45:39
 * @LastEditTime: 2021-10-22 20:31:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/utils/imgSize.js
 */
/**
 *
 * @param {String} src
 * @returns { w, h } Object
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
