/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-06-08 11:40:38
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/store/modules/user.js
 */
import { getToken, setToken, removeToken, getUserInfo, setUserInfo } from '@/utils/auth'
import { resetRouter } from '@/router'
import sha256 from 'js-sha256'
import axios from '@/axios/request'
import { get } from '@/axios/http'

const state = {
  token: getToken(),
  userInfo: getUserInfo(),
  roles: [],
  teamData: '',
  limitData: []
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
  SET_ROLE(state, arr) {
    state.roles = arr
  },
  SET_LIMITDATA(state, arr) {
    state.limitData = arr
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { phone, password } = userInfo
    const params = {
      mobile: phone.trim(),
      passwd: password
    }

    return new Promise((resolve, reject) =>
      axios
        .post('/login', params)
        .then((res) => {
          const data = res.data.data
          if (res.data.code === '200') {
            commit('SET_USERINFO', data)
            setUserInfo(res.data.data)
            const TOKEN = res.headers.token || ''
            commit('SET_TOKEN', TOKEN)
            setToken(TOKEN)

            resolve()
          } else {
            reject(res.data)
          }
        })
        .catch((error) => {
          reject(error)
        })
    )
  },

  // 获取用户 权限列表
  async getUserLimits({ commit, state }) {
    const { data, err } = await get('/limits')
    return new Promise((resolve, reject) => {
      if (err) {
        this.$message.error(err.msg)
        reject(err)
        return
      } else {
        commit('SET_LIMITDATA', data.data)
        resolve(data)
        const roleList = data.data
        const roles = []
        if (state.userInfo.Limits === '*') {
          roles.push('superman')
        } else {
          const Limits = state.userInfo.Limits.split(',')
          // roles = roleList.filter((item) => Limits.some((role) => parseInt(role) === parseInt(item.Id)))
          roleList.forEach(item => {
            if (Limits.some((role) => parseInt(role) === parseInt(item.Id))) {
              roles.push(item.Names)
            }
          })
        }

        commit('SET_ROLE', roles)
      }
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
    return new Promise((resolve) => {
      removeToken() // must remove  token  first
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
