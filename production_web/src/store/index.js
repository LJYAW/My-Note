/*
 * @Author: your name
 * @Date: 2021-01-13 10:19:32
 * @LastEditTime: 2021-12-15 11:13:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/store/index.js
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
import keepAlive from './modules/keepAlive'
import newEpgEdit from '@/views/EPG/new-system-pad/store/index'
Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    channel,
    permission,
    videoPlay,
    newEpgEdit,
    keepAlive
  },
  getters
})

export default store
