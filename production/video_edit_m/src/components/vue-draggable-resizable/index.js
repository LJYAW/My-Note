/*
 * @Author: zzx
 * @Date: 2020-09-18 11:12:07
 * @LastEditTime: 2020-09-18 11:15:29
 * @FilePath: /video_edit/src/components/vue-draggable-resizable/index.js
 */
import './src/vue-draggable-resizable.css'

import VueDraggableResizable from './src/vue-draggable-resizable'

export function install(Vue) {
  if (install.installed) return
  install.installed = true
  Vue.component('VueDraggableResizable', VueDraggableResizable)
}

const plugin = {
  install
}

let GlobalVue = null
if (typeof window !== 'undefined') {
  GlobalVue = window.Vue
} else if (typeof global !== 'undefined') {
  GlobalVue = global.Vue
}
if (GlobalVue) {
  GlobalVue.use(plugin)
}

export default VueDraggableResizable
