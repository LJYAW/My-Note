/*
 * @Author: your name
 * @Date: 2021-10-29 14:31:12
 * @LastEditTime: 2021-11-01 20:42:38
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/store/index.js
 */

// import bdApp from './modules/bd-app'
import appData from './modules/app-data'

const state = { ...appData.state }
const mutations = { ...appData.mutations }
const actions = { ...appData.actions }

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

