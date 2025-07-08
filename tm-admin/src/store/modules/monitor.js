/*
 * @Author: your name
 * @Date: 2021-04-19 10:56:42
 * @LastEditTime: 2021-04-19 18:47:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/store/modules/monitor.js
 */

const state = {
  // entityListSelf: {},
  // entityListCompetitor: {},
  // professionalList: [],
  reportList: {}
}

const mutations = {
  // SET_ENTITYLISTSELF: (state, data) => {
  //   state.channelData = data;
  // },
  // SET_ENTITYLISTCOMPETITOR: (state, data) => {
  //   state.itemData = data;
  // },
  // SET_PROFESSIONALLIST: (state, data) => {
  //   state.professionalList = data;
  // },
  SET_REPORTLIST: (state, data) => {
    state.reportList = data
  }
}

const actions = {}
export default {
  namespaced: true,
  state,
  mutations,
  actions
}
