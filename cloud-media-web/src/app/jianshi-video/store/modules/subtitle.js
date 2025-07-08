/*
 * @Author: your name
 * @Date: 2021-09-12 15:57:37
 * @LastEditTime: 2021-10-19 16:36:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/store/modules/subtitle.js
 */
import axios from '@/axios/index'
const getDefaultState = () => {
  return {
    // 字幕信息
    subTitleSingle: {}, // 设置单条数据
    subTitleIndex: 0, // 设置字幕idnex
    videoCurrentTimes: '', // 设置视频当前时间
    setVideoEndTimes: '', // 设置视频开始时间
    setVideoStartTimes: '', // 设置视频结束时间
    subTilteList: [], // 字幕数据
    // 视频选项
    videoDuration: 0, // 视频时长
    subtitleVideoSale: '', // 视频比例
    showTitle: '', // 显示标题
    // 视频效果选项
    audioTimbreList: [], // 音色数据
    backMusicList: [], // 背景音乐数据
    subtitleBackList: [], // 字幕模板数据
    subtitleVideoBackList: [], // 字幕视频背景数据
    subTitleLogoList: [], // 角标数据
    subTitleTailList: [], // 片尾数据
    subTitleTitleList: [], // 片头数据
    subtitleBackSingleData: {}, // 字幕背景单条数据
    subtitlevideoSingleData: {}, // 视频背景单条数据
    translateLanguage: '', // 翻译语言
    language: '', // 源语言
    displayLangage: '', // 输出语言

    // 虚拟主持人
    virtual_presenter: [],
    timbreSwitch: false,
    // 提交数据整合,
    submitData: {
      title_position: 0.3, // 字幕位置
      update_audio_id: {}, // 替换原生id
      bg_musice: {}, // 背景音乐id
      bg: {}, // 视频背景id
      bg_zm_id: {}, // 字幕背景id
      caption_bg_id: {}, // 视频模板
      logo: {}, // 角标Id
      video_title_id: {}, // 片头id
      video_tail_id: {}, // 片尾id
      virtual_presenter_obj: {} // 虚拟主持人id
    },
    // 装饰 字幕数据位置
    contentListTemp: []
  }
}

export default {
  state: getDefaultState(),
  getters: {},
  mutations: {
    // 回显数据大对象
    SET_SUBMITDATA: (state, data) => {
      state.submitData = data
    },
    SET_LANGUAGE: (state, data) => {
      state.language = data
    },
    SET_DISPLAYLANGUAGE: (state, data) => {
      state.displayLangage = data
    },
    SET_TANGSLATELANGUAGE: (state, data) => {
      state.translateLanguage = data
    },
    SET_BACKMUSICLIST: (state, data) => {
      state.backMusicList = data
    },
    SET_SUBTITLEBACKSINGLEDATA: (state, data) => {
      state.subtitleBackSingleData = data
    },
    SET_SUBTITLEVIDEOSINGLEDATA: (state, data) => {
      state.subtitlevideoSingleData = data
    },
    SET_BGID: (state, data) => {
      state.submitData.bg = data
    },
    SET_ZMBGID: (state, data) => {
      state.submitData.bg_zm_id = data
    },
    SET_BACKMUSICID: (state, data) => {
      state.submitData.bg_musice = data
    },
    SET_JBLOGOID: (state, data) => {
      state.submitData.logo = data
    },
    SET_TITLEID: (state, data) => {
      state.submitData.video_title_id = data
    },
    SET_TAILID: (state, data) => {
      state.submitData.video_tail_id = data
    },

    SET_TEMPLATE: (state, data) => {
      state.submitData.caption_bg_id = data
    },

    // 字幕位置
    SET_SUBTITLE(state, data) {
      state.submitData.title_position = data
    },

    // 虚拟主持人
    SET_VIRTUAL_PRESENTER: (state, data) => {
      state.virtual_presenter = data
    },

    SET_PERSONDEATIL: (state, data) => {
      state.submitData.virtual_presenter_obj = data
    },

    // 虚拟主持人END
    SET_SUBTITLEBACKLIST: (state, data) => {
      state.subtitleBackList = data
    },
    SET_SUBTITLVIDEOEBACKLIST: (state, data) => {
      state.subtitleVideoBackList = data
    },
    SET_SUBTITLELOGOLIST: (state, data) => {
      state.subTitleLogoList = data
    },
    SET_SUBTITLETAILLIST: (state, data) => {
      state.subTitleTailList = data
    },
    SET_SUBTITLETITLELLIST: (state, data) => {
      state.subTitleTitleList = data
    },
    SET_ADUIOID: (state, data) => {
      state.submitData.update_audio_id = data
    },
    SET_TIMBRESWITCH: (state, data) => {
      state.timbreSwitch = data
    },
    SET_TIMBRELIST: (state, data) => {
      state.audioTimbreList = data
    },
    SET_VIDEOSALESUBTITLE: (state, data) => {
      state.subtitleVideoSale = data
    },
    SET_SHOWTILTE: (state, data) => {
      state.showTitle = data
    },
    SET_VIDEODURATION: (state, data) => {
      state.videoDuration = data
    },
    SET_TITLESINGLE: (state, data) => {
      state.subTitleSingle = data
    },
    SET_SUBTITLELIST: (state, data) => {
      state.subTilteList = data
    },
    SET_SBUTITLEINDEX: (state, data) => {
      state.subTitleIndex = data
    },
    SET_VIDEOCURRENTTIMES: (state, data) => {
      state.videoCurrentTimes = data
    },
    SET_VIDEOSTARTTIMES: (state, data) => {
      state.setVideoStartTimes = data
    },
    SET_VIDEOENDTIMES: (state, data) => {
      state.setVideoEndTimes = data
    },
    resetState(state) {
      Object.assign(state, getDefaultState())
    },
    setContentListTemp(state, data) {
      state.contentListTemp = data
    }
  },
  actions: {
    // 获取 视频背景音乐，字幕模板，及附加项片头，片尾，logo的数据
    async getSubtitleData({ commit }, params) {
      return new Promise((resolve, reject) => {
        axios
          .get('/materials', { params: params })
          .then(response => {
            const { data } = response.data
            // if (data.length <= 0) return
            const type = params.types
            switch (type) {
              case '片头':
                commit('SET_SUBTITLETITLELLIST', data)
                break
              case '片尾':
                commit('SET_SUBTITLETAILLIST', data)
                break
              case '角标':
                commit('SET_SUBTITLELOGOLIST', data)
                break
              case '字幕背景':
                commit('SET_SUBTITLEBACKLIST', data)
                break
              case '视频背景':
                commit('SET_SUBTITLVIDEOEBACKLIST', data)
                break
              case '音色':
                var arr = []
                data.forEach(element => {
                  element.title.includes('莎莎') || element.title.includes('峰哥')
                    ? arr.unshift(element)
                    : arr.push(element)
                })
                commit('SET_TIMBRELIST', arr)
                break
            }
            resolve(data)
          })
          .catch(error => {
            reject(error)
          })
      })
    }
  }
}
