/*
 * @Author: zzx
 * @Date: 2020-07-22 20:26:52
 * @LastEditTime: 2021-07-15 10:52:16
 * @FilePath: /zhijian_web/src/main.js
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/scss/index.scss'
import filter from './filter'
import ElementUI from 'element-ui'
import './assets/scss/element-variables.scss'
import model from './components/model.vue'
import axios from '@/axios/index.js'
import actions from './utils/utils.js'
import './permission'
import AlertMsg from 'vue-mimi-alert'
import { post, get, deleteRun } from '@/axios/http.js'
import { RecycleScroller } from 'vue-virtual-scroller'
import 'vue-virtual-scroller/dist/vue-virtual-scroller.css'
import videojs from 'video.js'
import upload from '../src/components/upload.vue'
import video_palyer from '../src/components/video_cp/video_palyer.vue'
import Loading from '../src/components/loading.vue'
import videoCut from 'vue-video-cut'
import scroll from '../src/components/vue_scroll/main.vue'
import MugenScroll from 'vue-mugen-scroll'
import vsvg from '@/components/svg.vue'
import iconImg from '@/components/icon_img.vue'
import layer from 'vue-layer'
import 'vue-layer/lib/vue-layer.css'
import bus from '@/utils/bus'
import pay from '@/components/pay/pay.js'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import '@/icons' // icon

// import style (<= Swiper 5.x)
import 'swiper/css/swiper.css'

Vue.use(VueAwesomeSwiper /* { default options with global component } */)

Vue.prototype.$layer = layer(Vue)

Vue.prototype.$axios = axios
Vue.prototype.$post = post
Vue.prototype.$get = get
Vue.prototype.$deleteRun = deleteRun
Vue.prototype.$videojs = videojs
Vue.prototype.$actions = actions
Vue.prototype.$alertMsg = AlertMsg
Vue.prototype.$bus = bus
Vue.prototype.$pay = pay

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(AlertMsg)
Vue.use(videoCut)
Vue.use(MugenScroll)

Vue.component('model', model)
Vue.component('scroll', scroll)
Vue.component('RecycleScroller', RecycleScroller)
Vue.component('upload', upload)
Vue.component('videoPalyer', video_palyer)
Vue.component('loading', Loading)
Vue.component('vsvg', vsvg)
Vue.component('iconImg', iconImg)

// router.beforeEach((to, from, next) => {
//   if (to.matched.some(record => record.meta.requireAuth)) {
//     // 判断该路由是否需要登录权限
//     if (!localStorage.getItem('user_info')) {
//     } else {
//       next()
//     }
//   } else {
//     next()
//   }
// })
// 判断环境
if (process.env.NODE_ENV == 'production') {
  // 禁止右键审查
  actions.noViewing()
}

var vue = new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

export default vue
