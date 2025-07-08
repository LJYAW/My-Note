/*
 * @Author: your name
 * @Date: 2021-08-31 17:19:21
 * @LastEditTime: 2021-09-02 17:08:53
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/store/index.js
 */
import axios from '@/axios/index'

const getDefaultState = () => {
  return {
    effectList: {}, // 视频效果init初始化数据
    virtual_presenter: [], // 虚拟主持人arr
    timbreCheckout: false, // 设置默认音色
    muasicName: '', // 音乐回显name
    cornerMarker: [], // 自定义角标数据
    videoTail: [], // 自定义片尾数据
    videoTitle: [], // 自定义片头数据
    effectData: {
      bg_music_id: '', // 音乐id
      caption_bg_id: '', // 设置视频模板id
      tts_per_id: '', // 音色id
      virtual_presenter_detail: {}, // 虚拟主持人
      subtitle: {
        margin_bottom: 0.3// 字幕位置
      },
      video_logo_type: '', // 角标类型
      video_logo_user_res_id: null, // 角标id
      video_begin_type: '', // 片头类型
      video_begin_user_res_id: null, // 片头id
      video_end_type: '', // 片尾类型
      video_end_user_res_id: null// 片尾id
    }
  }
}

export default {
  state: getDefaultState(),
  getters: {},
  mutations: {
    // 视频效果init初始化数据
    SET_INItDATA: (state, data) => {
      state.effectList = data
    },
    // 虚拟主持人arr
    SET_VIRTUAL: (state, data) => {
      state.virtual_presenter = data
    },
    // 设置默认音色
    SET_CHECKOUT: (state, data) => {
      // 缓存
      state.timbreCheckout = JSON.parse(localStorage.getItem('timbreCheckout'))// 转布尔值
    },
    // 音乐id
    SET_MUSICID: (state, data) => {
      state.effectData.bg_music_id = data
    },
    // 音乐回显name
    SET_MUSICNAME: (state, data) => {
      state.muasicName = data
    },
    // 设置视频模板id
    SET_TEMPLATEID: (state, data) => {
      state.effectData.caption_bg_id = data
    },
    // 音色id
    SET_TIMBRE: (state, data) => {
      state.effectData.tts_per_id = data
    },
    // 虚拟主持人arr
    SET_PERSONDEATILS(state, data) {
      state.effectData.virtual_presenter_detail = data
    },
    // 字幕位置
    SET_SUBTITLES(state, data) {
      state.effectData.subtitle.margin_bottom = data
    },
    // 自定义角标数据
    SET_ICONMAKER(state, data) {
      state.cornerMarker = data.video_logo
    },
    // 自定义片头数据
    SET_VIDEOTITLE(state, data) {
      state.videoTitle = data.video_begin
    },
    // 自定义片尾数据
    SET_VIDEOTAIL(state, data) {
      state.videoTail = data.video_end
    },
    // 角标类型
    SET_LOGOTYPE(state, data) {
      state.effectData.video_logo_type = data
    },
    // 片头类型
    SET_VIDEOBEGINTYPE(state, data) {
      state.effectData.video_begin_type = data
    },
    // 片尾类型
    SET_VIDEOENDTYPE(state, data) {
      state.effectData.video_end_type = data
    },
    // 角标id
    SET_LOGOID(state, data) {
      state.effectData.video_logo_user_res_id = data
    },
    // 片头id
    SET_VIDEOBEGINID(state, data) {
      state.effectData.video_begin_user_res_id = data
    },
    // 片尾id
    SET_VIDEOENDID(state, data) {
      state.effectData.video_end_user_res_id = data
    },
    resetState(state) {
      Object.assign(state, getDefaultState())
    }
  },
  actions: {
    // 获取附加项数据
    getAddData({ commit }, params) {
      return new Promise((resolve, reject) => {
        axios
          .get('intelligent-creation/user-resource-list', { params: params })
          .then(response => {
            const { list } = response.data.data
            switch (params.tag) {
              case 'video_logo':
                commit('SET_ICONMAKER', list)
                break
              case 'video_begin':
                commit('SET_VIDEOTITLE', list)
                break
              default:
                commit('SET_VIDEOTAIL', list)
            }
            resolve(list)
          })
          .catch(error => {
            reject(error)
          })
      })
    }
  }
}
