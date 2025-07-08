/*
 * @Author: your name
 * @Date: 2021-02-22 19:58:59
 * @LastEditTime: 2021-07-14 11:49:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin-template/src/store/modules/user.js
 */
import { getToken, setToken, removeToken, getUserInfo } from '@/utils/auth'
import { resetRouter } from '@/router'
import { get, post } from '@/axios/http'
import axios from '../../axios/index'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    userInfo: getUserInfo()
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: state => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_USERINFO: (state, data) => {
    state.userInfo = data
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
        .post('/v1/login', params)
        .then(res => {
          if (res.data.code === '200') {
            localStorage.setItem('user_info', JSON.stringify(res.data.data))
            commit('SET_USERINFO', res.data.data)
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
    )
  },

  // get user info
  // getInfo({ commit, state }) {
  //   return new Promise((resolve, reject) => {
  //     get('https://yapi.baidu.com/mock/68007/gitUserInfo', { token: state.token })
  //       .then(response => {
  //         const { data } = response
  //         if (!data) {
  //           return reject('Verification failed, please Login again.')
  //         }

  //         const { names, avatar } = data

  //         commit('SET_NAME', names)
  //         commit('SET_AVATAR', avatar)
  //         resolve(data)
  //       })
  //       .catch(error => {
  //         reject(error)
  //       })
  //   })
  // },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      removeToken() // must remove  token  first
      resetRouter()
      commit('RESET_STATE')
      resolve()
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
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

