/*
 * @Author: your name
 * @Date: 2021-06-08 12:14:20
 * @LastEditTime: 2021-07-09 11:05:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin-template/src/main.js
 */
import Vue from 'vue'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/plugins/index.js'
import '@/styles/index.scss' // global css
import '@/utils/flexible'
import App from './App'
import store from './store'
import router from './router'
import '@/utils/filter'

import '@/icons' // icon
import '@/permission' // permission control

import { post, get, put, deleteRun } from '@/axios/http'
import axios from '@/axios/index'

Vue.prototype.$axios = axios
Vue.prototype.$post = post
Vue.prototype.$get = get
Vue.prototype.$put = put
Vue.prototype.$deleteRun = deleteRun

Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
