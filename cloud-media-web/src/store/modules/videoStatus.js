/*
 * @Author: your name
 * @Date: 2021-07-29 17:16:24
 * @LastEditTime: 2021-08-16 16:56:15
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/store/modules/videoStatus.js
 */
const state = {
  searchWord: '',
  videoTime: 0,
  timeLine: 0,
  videoData: {},
  batchVideo: []
}
const mutations = {
  SEARCH_WORD: (state, str) => {
    state.searchWord = str
  },
  VIDEO_TIME_CHANGE: (state, time) => {
    state.videoTime = time
  },
  TIME_LINE_CHANGE: (state, time) => {
    state.timeLine = time
  },
  VIDEO_DATA_CHANGE: (state, data) => {
    state.videoData = data
  },
  BATCH_VIDEO_CHANGE: (state, arr) => {
    state.batchVideo = arr
    state.batchVideo = JSON.parse(JSON.stringify(state.batchVideo))
  }
}

export default {
  namespaced: true,
  state,
  mutations
}
