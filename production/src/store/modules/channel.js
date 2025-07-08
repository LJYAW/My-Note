/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-04-22 15:37:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/store/modules/user.js
 */
import axios from '@/axios/request'

const state = {
  channelData: '',
  itemData: '',
  categoryData: '',
  CustomClassList: '',
  CusClassData: ''
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
  },
  SET_CUSTOM: (state, data) => {
    state.CustomClassList = data
  },
  SET_CLASS: (state, data) => {
    state.CusClassData = data
  }
}

const actions = {
  // 获取栏目列表数据
  getItemList({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get('/production/itemList', { params: params })
        .then(response => {
          const { data } = response.data
          commit('SET_ITEMDATA', data)
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  // 获取频道列表
  getChannelData({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get('/production/channelList', { params: params })
        .then(response => {
          const { data } = response.data
          commit('SET_CHANNELDATA', data.list)
          resolve(response.data.data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  // 获取客户分类列表
  getCusList({ commit }) {
    return new Promise((resolve, reject) => {
      axios
        .get('/epg-task/customer-category-list')
        .then(response => {
          const { data } = response.data
          commit('SET_CLASS', data)
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  // 获取客户定制分类
  getEditCustomList({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get('/epg-task/item', { params: params })
        .then(response => {
          const { data } = response.data
          commit('SET_CUSTOM', data)
          resolve()
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  getCategoryList({ commit }) {
    return new Promise((resolve, reject) => {
      axios
        .get('/admin/order/category-list')
        .then(response => {
          const { data } = response.data
          commit('SET_CATEGORY', data)
          resolve()
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
