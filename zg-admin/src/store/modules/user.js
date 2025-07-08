import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import sha256 from 'js-sha256'
import axios from '@/axios/request'

const state = {
  token: getToken(),
  userInfo: '',
  roles: [],
  teamData: '',
  fileUrl: '',
  adminMenus: ''
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
  SET_FILE_URL: (state, data) => {
    state.fileUrl = data
  },
  SET_ADMIN_MENUS: (state, data) => {
    state.adminMenus = data
  },
  SET_ROLE(state, obj) {
    const data = JSON.parse(JSON.stringify(obj))
    if (!data) {
      state.roles = []
      return
    }
    let roles = []
    let work = []
    data.work ? (work = data.work.split(',')) : (work = [])
    roles = roles.concat(work)

    if (data.is_superman) {
      roles.push('superman')
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
        .post('/admin/admin-user/login', params)
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
      axios
        .get('/admin/admin-user/info')
        .then(response => {
          commit('SET_USERINFO', response.data.data)
          commit('SET_ROLE', response.data.data)
          // commit('SET_ROLE', ['admin'])
          resolve(response.data.data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  getConfigBase({ commit }) {
    return new Promise((resolve, reject) => {
      axios
        .get('/configs/base')
        .then(response => {
          commit('SET_FILE_URL', response.data.data.file_url_prefix)
          commit('SET_ADMIN_MENUS', response.data.data.admin_menus)
          // commit('SET_USERINFO', response.data.data)
          // commit('SET_ROLE', response.data.data)
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
