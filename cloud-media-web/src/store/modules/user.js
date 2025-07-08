/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-10-14 11:01:26
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/store/modules/user.js
 */
import {
  getToken,
  setToken,
  removeToken,
  getUserInfo,
  setUserInfo,
  removeUserInfo,
  getDirInfo,
  setDirInfo,
  removeDirInfo
} from '@/utils/auth'
import { resetRouter } from '@/router'
import sha256 from 'js-sha256'
import axios from '@/axios/index'

const state = {
  token: getToken(),
  userInfo: getUserInfo(),
  roles: [],
  teamData: '',
  dirInfo: getDirInfo()
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_USERINFO: (state, data) => {
    state.userInfo = data
  },
  SET_DIRINFO: (state, data) => {
    state.dirInfo = data
  },
  SET_TEAMDATA: (state, data) => {
    state.teamData = data
  },
  SET_ROLE(state, data) {
    if (data) {
      const { after_menu, befor_menu, type } = data
      // if (type === 1) {
      //   state.roles.push('superman')
      // }
      state.roles.push(...befor_menu.split(','), ...after_menu.split(','))
    } else {
      state.roles = []
    }
  }
}

const actions = {
  // user login
  login({ commit }, params) {
    // const { mobile, passwd } = userInfo
    // const params = {
    //   mobile: mobile.trim(),
    //   // passwd: sha256(passwd),
    //   passwd: passwd
    // }
    // if (type === 'ver_code') params.ver_code = ver_code

    return new Promise((resolve, reject) => {
      axios
        .post('/login', params)
        .then(res => {
          if (res.data.code === '200') {
            const { data } = res.data
            const TOKEN = res.headers.token || ''
            commit('SET_TOKEN', TOKEN)
            setToken(TOKEN)
            resolve()
          } else {
            reject(res.data)
          }
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // 获取用户信息
  getUserInfo({ commit }) {
    return new Promise((resolve, reject) => {
      axios.get('/user/myself')
        .then(res => {
          const data = res.data.data
          if (res.data.code === '200') {
            commit('SET_USERINFO', data)
            setUserInfo(data)
            commit('SET_ROLE', data)
            resolve(data)
          } else {
            reject(res)
          }
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  // 获取用户目录
  getDirInfo({ commit }) {
    return new Promise((resolve, reject) => {
      axios.get('/userdirs/getdir/byuser')
        .then(response => {
          commit('SET_DIRINFO', response.data.data)
          setDirInfo(response.data.data)
          resolve()
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // user logout
  logout({ commit, state }) {
    commit('SET_TOKEN', '')
    commit('SET_USERINFO', '')
    commit('SET_DIRINFO', [])
    commit('SET_TEAMDATA', '')
    commit('SET_ROLE', '')
    removeToken() // must remove  token  first
    removeUserInfo()
    removeDirInfo()
    resetRouter()
  },

  // remove token
  resetToken({ commit }) {
    removeToken() // must remove  token  first
    removeUserInfo()
    removeDirInfo()
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

