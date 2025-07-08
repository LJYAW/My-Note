/*
 * @Author: your name
 * @Date: 2021-01-18 18:05:41
 * @LastEditTime: 2021-01-21 15:47:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/store/modules/settings.js
 */
import defaultSettings from '@/settings'

const state = {
  showSettings: defaultSettings.showSettings,
  fixedHeader: defaultSettings.fixedHeader,
  sidebarLogo: defaultSettings.sidebarLogo,
  loginTitle: defaultSettings.loginTitle,
  enLoginTitle: defaultSettings.enLoginTitle
}

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    // eslint-disable-next-line no-prototype-builtins
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
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

