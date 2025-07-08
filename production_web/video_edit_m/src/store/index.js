/*
 * @Author: zzx
 * @Date: 2020-08-07 19:26:48
 * @LastEditTime: 2021-07-07 19:24:14
 * @FilePath: /video_edit/src/store/index.js
 */
import Vue from 'vue'
import Vuex from 'vuex'

import range from './modules/range'
import videoPlay from './modules/video_state'
import mutations from './mutations'
import actions from './actions'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    PER_PX_MS: 100,
    progrees_x: 0,
    cut_details: {
      startMs: 0,
      trackLeft: 0,
      trackWidth: 30
    },
    decorate_list: [],
    decorate_active_id: ''
  },
  mutations: mutations,
  actions: actions,
  modules: {
    range,
    videoPlay
  }
})
