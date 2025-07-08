/*
 * @Author: zzx
 * @Date: 2020-07-29 15:11:48
 * @LastEditTime: 2021-04-28 14:53:29
 * @FilePath: /video_edit/src/main.js
 */
import Vue from 'vue'
import App from './App.vue'
import store from './store/index'
import './assets/scss/reset.scss'
import './filter'

import videoPlay from '@/components/video_play/video_palyer.vue'
import bus from '@/utils/bus.js'

import VueDraggableResizable from '@/components/vue-draggable-resizable'
import VueVirtualScroller from 'vue-virtual-scroller'
Vue.use(VueVirtualScroller)

Vue.component('vue-draggable-resizable', VueDraggableResizable)
Vue.component('video-player', videoPlay)
Vue.prototype.$bus = bus

Vue.config.productionTip = false

new Vue({
  render: (h) => h(App),
  store
}).$mount('#app')
