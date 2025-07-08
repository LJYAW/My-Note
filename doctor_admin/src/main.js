/*
 * @Author: zzx
 * @Date: 2020-07-03 15:24:59
 * @LastEditTime: 2021-01-26 16:10:55
 * @FilePath: /weijian_web/src/main.js
 */

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './assets/scss/index.scss'
// import '../flexible'
import ElementUI from 'element-ui'
import './assets/scss/element-variables.scss'
import './assets/scss/tables.scss'
import axios from '@/axios/index.js'
import AlertMsg from 'vue-mimi-alert'
import { post, get, deleteRun } from '@/axios/http.js'
import upload from '../src/components/upload.vue'
import Loading from '../src/components/loading.vue'
import searchTags from './components/search_tags.vue'
// import 'lib-flexible'
// import './utils/rem'
import store from '../src/store/store'
import layer from 'vue-layer'
import 'vue-layer/lib/vue-layer.css'
import layerIfream from './utils/layer'
import font from './assets/font/iconfont.css'
// import 'vue-audios/dist/vue-audios.css'
// import VueAudios from 'vue-audios'

// Vue.use(VueAudios)
Vue.prototype.$layer = layer(Vue)
Vue.prototype.$axios = axios
Vue.prototype.$post = post
Vue.prototype.$get = get
Vue.prototype.$deleteRun = deleteRun
Vue.prototype.$alertMsg = AlertMsg
Vue.prototype.$layerIfream = layerIfream
Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(AlertMsg)

Vue.component('upload', upload)
Vue.component('loading', Loading)
Vue.component('searchTags', searchTags)

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requireAuth)) {
    // 判断该路由是否需要登录权限
    // if (!localStorage.getItem('user_info')) {
    //   store.commit('modalShow', 'loginM')
    // } else {
    //   next()
    // }
  } else {
    next()
  }
})

// // 判断环境
// if (process.env.NODE_ENV == 'production') {
//   // 禁止右键审查
//   actions.noViewing()
// }

var vue = new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app')

export default vue
