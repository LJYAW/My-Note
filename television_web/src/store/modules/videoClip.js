/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-04-23 10:44:06
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/store/modules/user.js
 */
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import axios from '@/axios/request'

const state = {
  videoUrl: '',
  timeData: {}
}

const mutations = {
  SET_VIDEOURL: (state, data) => {
    state.videoUrl = data
  },
  SET_TIMEDATA: (state, data) => {
    state.timeData = data
  }
}

const actions = {
  // user login
  setVideoUrl({ commit }, data) {
    commit('SET_VIDEOURL', data)
  },
  setTimeData({ commit }, data) {
    commit('SET_TIMEDATA', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
