/*
 * @Author: your name
 * @Date: 2021-04-01 15:26:29
 * @LastEditTime: 2021-06-08 11:11:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-beautiful-template/src/main.js
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Elementui from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './styles/index.scss'
import axios from './axios/request'
import { post, get, put, deleteRun } from './axios/http'
import VueVideoPlayer from '@/components/VideoPlayer/src/index.vue'

import '@/plugins/index.js'
import '@/utils/flexible'
import '@/permission' // permission control
import '@/utils/filter'
import '@/utils/btn-permission'

Vue.prototype.$axios = axios
Vue.prototype.$post = post
Vue.prototype.$get = get
Vue.prototype.$put = put
Vue.prototype.$deleteRun = deleteRun
Vue.component('video-player', VueVideoPlayer)

Vue.use(Elementui)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: (h) => h(App)
}).$mount('#app')
