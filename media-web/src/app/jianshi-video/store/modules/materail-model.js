/*
 * @Author: your name
 * @Date: 2021-08-31 17:19:21
 * @LastEditTime: 2021-09-16 12:03:35
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/store/index.js
 */

const getDefaultState = () => {
  return {
    // 组件公用数据
    searchKey: ''
  }
}

export default {
  state: getDefaultState(),
  getters: {},
  mutations: {
    // 组件公用数据赋值
    SET_SEARCH_KEY: (state, data) => {
      state.searchKey = data
    },
    resetState(state) {
      Object.assign(state, getDefaultState())
    }
  },
  actions: {

  }
}
