/*
 * @Author: your name
 * @Date: 2021-01-18 18:05:41
 * @LastEditTime: 2021-09-22 12:21:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/store/modules/settings.js
 */

const state = {
  fixedHeader: false,
  showHeader: true
}

const mutations = {
  CHANGE_SETTING: (state, data) => {
    state = Object.assign(state, data)
  }
}

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

