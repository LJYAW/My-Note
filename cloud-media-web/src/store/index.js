/*
 * @Author: your name
 * @Date: 2021-01-13 10:19:32
 * @LastEditTime: 2021-09-01 16:16:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/store/index.js
 */
import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import user from './modules/user'
import settings from './modules/settings'
import permission from './modules/permission'
import keepAlive from './modules/keepAlive'
import directoryData from './modules/directoryData'
import videoStatus from './modules/videoStatus'
import company from './modules/company'
Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    settings,
    user,
    permission,
    keepAlive,
    videoStatus,
    directoryData,
    company
  },
  getters
})

export default store
