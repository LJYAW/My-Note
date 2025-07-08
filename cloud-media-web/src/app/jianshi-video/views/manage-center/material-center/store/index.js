/*
 * @Author: your name
 * @Date: 2021-09-01 18:01:10
 * @LastEditTime: 2021-09-01 18:29:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/material-center/store/index.js
 */
const getDefaultState = () => {
  return {
    dialogTitle: ''
  }
}

export default {
  namespaced: true,
  state: getDefaultState(),
  getters: {},
  mutations: {
    // 设置上传素材弹窗标题
    SET_DIALOG_TITLE(state, data) {
      state.dialogTitle = data
    },
    resetState(state) {
      Object.assign(state, getDefaultState())
    }
  }
}
