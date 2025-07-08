/*
 * @Author: your name
 * @Date: 2021-01-13 10:19:32
 * @LastEditTime: 2021-07-01 15:46:08
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/main.js
 */
import Vue from 'vue'
import './utils/dialog'
import '@/assets/scss/reset.scss'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import Plugin from 'v-fit-columns'
import 'element-ui/lib/theme-chalk/index.css'
import VueVideoPlayer from '@/components/VideoPlayer/src/index.vue'
import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import axios from '@/axios/request.js'
import bus from '@/utils/bus'
import { post, get, deleteRun } from '@/axios/http.js'
import '@/plugins/index.js'
import '@/utils/flexible'
import '@/utils/filter'

import '@/icons' // icon
import '@/permission' // permission control
import videoCut from 'vue-video-cut'
import {
  nConfirm,
  vLoading
} from '@/utils/package.js'
Vue.prototype.$nConfirm = nConfirm
Vue.prototype.$vLoading = vLoading

Vue.use(ElementUI)
Vue.use(Plugin)

Vue.use(videoCut)
Vue.component('video-player', VueVideoPlayer)
window.lodash = require('lodash')
Vue.prototype.$axios = axios
Vue.prototype.$post = post
Vue.prototype.$get = get
Vue.prototype.$deleteRun = deleteRun
Vue.prototype.$bus = bus
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
