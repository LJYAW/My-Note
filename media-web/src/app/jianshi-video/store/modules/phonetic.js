/*
 * @Author: your name
 * @Date: 2021-09-12 15:57:37
 * @LastEditTime: 2021-10-14 15:50:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/store/modules/subtitle.js
 */
import axios from '@/axios/index'

const getDefaultState = () => {
  return {
    tracks: [], // 素材资源
    caption_tracks: [], // 音频转字幕数据
    phoneticTitle: '', // 语音标题
    phoneticVideoSale: '16:9', // 视频比例
    subtitlePosition: { // 字幕位置
      margin_bottom: 0.3
    },
    hostPosition: ''
  }
}

export default {
  state: getDefaultState(),
  getters: {},
  mutations: {
    set_resourcedetail: (state, data) => {
      state.tracks = data
    },
    set_caption_tracks: (state, data) => {
      state.caption_tracks = data
    },
    set_phonetictitle: (state, data) => {
      state.phoneticTitle = data
    },
    set_phoneticVideoSale: (state, data) => {
      state.phoneticVideoSale = data
    },
    set_subtitlePosition: (state, data) => {
      state.subtitlePosition.margin_bottom = data
    },
    set_hostPosition: (state, data) => {
      state.hostPosition = data
    }
  },
  actions: {

  }
}
