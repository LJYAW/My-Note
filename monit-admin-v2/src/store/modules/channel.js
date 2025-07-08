/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-07-16 16:24:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/store/modules/user.js
 */
import axios from '../../axios/index'

const state = {
  channelData: [],
  allChannelData: [],
  channelLevel: [],
  channelType: [],
  channelCount: 0
}

const mutations = {
  SET_CHANNELDATA: (state, data) => {
    state.channelData = data
  },
  SET_AllCHANNELDATA: (state, data) => {
    state.allChannelData = data
  },
  SET_CHANNELLEVEL: (state, data) => {
    state.channelLevel = data
  },
  SET_CHANNELTYPE: (state, data) => {
    state.channelType = data
  },
  SET_CHANNELCOUNT: (state, data) => {
    state.channelCount = data
  }
}

const actions = {
  // 获取频道列表
  getChannelData({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get('/v1/channels/', { params: params })
        .then(response => {
          const { data } = response
          commit('SET_CHANNELDATA', data.data)
          commit('SET_CHANNELCOUNT', data.count)
          resolve(data.data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  getAllChannelData({ commit }) {
    return new Promise((resolve, reject) => {
      axios
        .get('/v1/channels/getallchannellist')
        .then(response => {
          const { data } = response
          commit('SET_AllCHANNELDATA', data.data)
          resolve(data.data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  getChannelLevel({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get('/v1/channelslevel', { params: params })
        .then(response => {
          const { data } = response
          commit('SET_CHANNELLEVEL', data.data)
          resolve(data.data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  getChannelType({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get('/v1/channelstype', { params: params })
        .then(response => {
          const { data } = response
          commit('SET_CHANNELTYPE', data.data)
          resolve(data.data)
        })
        .catch(error => {
          reject(error)
        })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
