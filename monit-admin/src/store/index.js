/*
 * @Author: your name
 * @Date: 2021-07-02 16:37:54
 * @LastEditTime: 2021-07-07 11:23:49
 * @LastEditors: your name
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/store/index.js
 */
import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import channel from './modules/channel'
Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    channel
  },
  getters
})

export default store
