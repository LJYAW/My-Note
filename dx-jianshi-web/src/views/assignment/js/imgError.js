/*
 * @Author: your name
 * @Date: 2020-11-12 18:05:11
 * @LastEditTime: 2020-11-12 18:06:24
 * @LastEditors: your name
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/assignment/js/imgError.js
 */
export const imgError = {
  data() {
    return {}
  },
  methods: {
    imgError(item) {
      // this.img_error = true
      let img = event.srcElement
      let img_error_url = require('@/assets/images/img_error.svg')
      img.src = img_error_url
      img.onerror = null // 防止闪图
      this.$emit('imgError')
    }
  }
}
