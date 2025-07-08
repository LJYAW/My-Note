/*
 * @Author: your name
 * @Date: 2021-04-08 16:21:22
 * @LastEditTime: 2021-04-22 19:36:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-beautiful-template/src/main.js
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import '@/assets/scss/reset.scss'
import '@/styles/index.scss' // global css

import axios from './axios/request'
import { post, get, deleteRun, put } from './axios/http'
import bus from '@/utils/bus'
import '@/plugins/index.js'
import VueVideoPlayer from '@/components/VideoPlayer/src/index.vue'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/utils/flexible'
import '@/permission' // permission controli
import '@/utils/filter'

Vue.config.productionTip = false

Vue.prototype.$axios = axios
Vue.prototype.$post = post
Vue.prototype.$get = get
Vue.prototype.$put = put
Vue.prototype.$deleteRun = deleteRun
Vue.prototype.$bus = bus
Vue.use(ElementUI)
Vue.component('video-player', VueVideoPlayer)

new Vue({
  router,
  store,
  render: (h) => h(App)
}).$mount('#app')
