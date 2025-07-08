/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-07-16 10:46:26
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/store/modules/user.js
 */
import { get } from '@/axios/http.js'

const state = {
  channelData: '',
  itemData: '',
  categoryData: ''
}

const mutations = {
  SET_CHANNELDATA: (state, data) => {
    state.channelData = data
  },
  SET_ITEMDATA: (state, data) => {
    state.itemData = data
  },
  SET_CATEGORY: (state, data) => {
    state.categoryData = data
  }
}

const actions = {
  // 获取频道列表
  async getChannelData({ commit }, params) {
    const { data, err } = await get('/jobs/channels', params)
    if (err) {
      this.$message.error(err.msg)
      return
    }
    commit('SET_CHANNELDATA', data.data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
