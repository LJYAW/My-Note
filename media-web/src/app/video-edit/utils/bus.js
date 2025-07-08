/*
 * @Author: your name
 * @Date: 2021-08-11 18:19:47
 * @LastEditTime: 2021-08-11 18:25:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/utils/bus.js
 */
import Vue from 'vue'

try {
  if (!Vue.prototype.$bus) {
    Vue.prototype.$bus = new Vue()
  }
} catch (err) {
  console.error(err)
}

