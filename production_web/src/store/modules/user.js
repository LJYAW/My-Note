/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-03-18 16:22:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/store/modules/user.js
 */
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import sha256 from 'js-sha256'
import axios from '@/axios/request'

const state = {
  token: getToken(),
  userInfo: '',
  roles: [],
  teamData: ''
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_USERINFO: (state, data) => {
    state.userInfo = data
  },
  SET_TEAMDATA: (state, data) => {
    state.teamData = data
  },
  SET_ROLE(state, obj) {
    const data = JSON.parse(JSON.stringify(obj))
    if (!data) {
      state.roles = []
      return
    }

    let roles = []
    let work = []
    data.work ? work = data.work.split(',') : work = []
    roles = roles.concat(work)

    if (data.is_superman) {
      roles.push('superman')
    } else if (data.is_team_leader) {
      roles.push('teamLeader')
    } else {
      roles.push('members')
    }

    state.roles = roles
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password, type, ver_code } = userInfo
    const params = {
      username: username.trim(),
      password: sha256(password),
      type: type
    }
    if (type === 'ver_code') params.ver_code = ver_code

    return new Promise((resolve, reject) => {
      axios
        .post('/user/login', params)
        .then(res => {
          if (res.data.code === '0000') {
            const { data } = res.data
            commit('SET_TOKEN', data.access_token)
            setToken(data.access_token)
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
      axios.get('/user/info')
        .then(response => {
          commit('SET_USERINFO', response.data.data)
          commit('SET_ROLE', response.data.data)
          resolve(response.data.data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // user logout
  logout({ commit, state }) {
    commit('SET_TOKEN', '')
    commit('SET_ROLE', '')
    removeToken() // must remove  token  first
    resetRouter()
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      resolve()
    })
  },
  getTeamData({ commit }, params) {
    return new Promise((resolve, reject) => {
      axios
        .get('/user/teamList', { params: params })
        .then(response => {
          const { data } = response.data
          commit('SET_TEAMDATA', data.list)
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

