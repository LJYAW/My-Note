/*
 * @Author: your name
 * @Date: 2021-01-13 10:19:32
 * @LastEditTime: 2021-04-23 10:01:33
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
import permission from './modules/permission'
import videoClip from './modules/videoClip'
Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    permission,
    videoClip
  },
  getters
})

export default store
