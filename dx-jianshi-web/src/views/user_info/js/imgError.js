/*
 * @Author: your name
 * @Date: 2020-11-19 17:49:55
 * @LastEditTime: 2021-07-15 16:22:07
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/user_info/js/imgError.js
 */
export const imgError = {
  data() {
    return {}
  },
  methods: {
    imgError() {
      let img = event.srcElement
      let img_error_url = require('@/assets/images/img_error.svg')
      img.src = img_error_url
      img.onerror = null // 防止闪图
    }
  }
}
