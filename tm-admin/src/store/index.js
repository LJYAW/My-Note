/*
 * @Author: your name
 * @Date: 2021-01-13 10:19:32
 * @LastEditTime: 2021-06-07 16:22:29
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/store/index.js
 */
import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import channel from './modules/channel'
import permission from './modules/permission'
import videoPlay from './modules/video_state'
import monitor from './modules/monitor'
// import limit from './modules/limit'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    channel,
    permission,
    videoPlay,
    monitor
    // limit
  },
  getters
})

export default store
