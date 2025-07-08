/*
 * @Author: your name
 * @Date: 2021-07-26 12:10:55
 * @LastEditTime: 2021-09-22 16:15:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/main.js
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import '@/styles/index.scss' // global css
import '@/plugins/index.js'
import '@/permission' // permission control
import '@/icons'
// rem
// import '@/utils/flexible'

// 全局注册 过滤器
import filters from '@/utils/filter'
Object.keys(filters).forEach(k => Vue.filter(k, filters[k]))

// eventBus
import bus from '@/utils/bus'
Vue.prototype.$bus = bus

// 平滑滚动
import smoothscroll from 'smoothscroll-polyfill'
smoothscroll.polyfill()
// 虚拟滚动

// 滚动条
import scroll from '@/components/VueScroll/index.vue'
Vue.component('scroll', scroll)

import { RecycleScroller } from 'vue-virtual-scroller'
import 'vue-virtual-scroller/dist/vue-virtual-scroller.css'
Vue.component('RecycleScroller', RecycleScroller)
// api
import { post, get, deleteRun, put } from '@/axios/http.js'
import axios from '@/axios/index.js'
Vue.prototype.$axios = axios
Vue.prototype.$post = post
Vue.prototype.$get = get
Vue.prototype.$put = put
Vue.prototype.$deleteRun = deleteRun

// 页面标题
import TitleBar from '@/components/TitleBar/index.vue'
Vue.component('title-bar', TitleBar)

// 播放器
import VueVideoPlayer from '@/components/VideoPlayer/src/index.vue'
Vue.component('video-player', VueVideoPlayer)

// 复制插件
import VueClipboard from 'vue-clipboard2'
Vue.use(VueClipboard)

// swiper插件
import VueAwesomeSwiper from 'vue-awesome-swiper'
Vue.use(VueAwesomeSwiper /* { default options with global component } */)
import 'swiper/css/swiper.css'

// element UI
import ElementUI from 'element-ui'
Vue.use(ElementUI)

// 设置index的title
import VueWechatTitle from 'vue-wechat-title'
Vue.use(VueWechatTitle)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
