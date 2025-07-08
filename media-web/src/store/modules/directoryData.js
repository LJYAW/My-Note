/*
 * @Author: your name
 * @Date: 2021-08-09 18:10:12
 * @LastEditTime: 2021-08-19 11:29:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/store/modules/directoryData.js
 */
import axios from '@/axios/index'
const state = {
  directoryData: [],
  userDirectData: [],
  userRoleData: [],
  userList: [],
  companyList: {}
}
const mutations = {
  SET_DIRECTORY(state, data) {
    state.directoryData = data.data
  },
  SET_USER_DIRECT(state, data) {
    state.userDirectData = data
  },
  SET_USER_ROLE(state, data) {
    state.userRoleData = data.data
  },
  SET_USERLIST(state, data) {
    state.userList = data
  },
  SET_COMPANYLIST(state, data) {
    state.companyList = data
  }
}
const actions = {
  // 获取公司列表数据
  getCompanyData({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get('/companies/', { params: params })
        .then(response => {
          const { data } = response.data
          commit('SET_COMPANYLIST', data)
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  // 获取用户列表数据
  getUserData({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get('/user/', { params: params })
        .then(response => {
          const { data } = response.data
          commit('SET_USERLIST', data)
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  // 获取目录数据
  getDierctoryData({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get('/dirs/', { params: params })
        .then(response => {
          const { data } = response
          commit('SET_DIRECTORY', data)
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  // 获取用户角色数据
  getUserRoleData({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get('/usergroup/', params)
        .then(response => {
          const { data } = response
          commit('SET_USER_ROLE', data)
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  // 获取当前目录下的user角色数据
  getUserDirectData({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get(`/userdirs`, { params: params })
        .then(response => {
          const { data } = response.data
          commit('SET_USER_DIRECT', data)
          resolve(data)
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
