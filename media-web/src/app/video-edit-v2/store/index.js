/*
 * @Author: your name
 * @Date: 2021-07-28 16:51:29
 * @LastEditTime: 2021-09-01 17:31:56
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/store/index.js
 */
const getDefaultState = () => {
  return {
    // video 数据
    videoOptions: {
      poster: '',
      src: ''
    },
    videoPlay: null,
    videoStatus: '',
    playing: false,
    muted: false,
    videoEvent: '',
    VIDEO_DURANTION_MS: 0,
    currentTimeMs: 0,

    // canvas 数据
    canvasOptions: {
      canvasW: 952,
      canvasH: 535
    },

    // 时间轴返回数据
    PER_PX_MS: 0,
    CUT_TRACK_WIDTH: 1200,

    // cut track 数据 目前剪辑只支持一个
    cutTrackData: {
      startMs: 0,
      endMs: 10000,
      left: 0,
      width: 100
    },
    range: 4,
    maxRange: 6,

    progressX: 0,

    // 装饰数据
    decorateList: [],
    decorateActiveId: 0,

    // video 原始尺寸
    videoSize: {
      w: 1980,
      h: 1080
    }
  }
}

export default {
  namespaced: true,
  state: getDefaultState(),
  getters: {},
  mutations: {
    // 设置 video 数据
    SET_VIDEO_OPTIONS(state, obj) {
      state.videoOptions = obj
    },
    SEEK_CURRENT_TIME_MS(state, ms) {
      if (isNaN(ms)) {
        console.warn(ms)
      } else {
        state.videoPlay && (state.videoPlay.currentTime = ms / 1000)
      }
    },
    VIDEO_PLAY_OR_PAUSE(state) {
      try {
        state.videoPlay.paused
          ? state.videoPlay.play()
          : state.videoPlay.pause()
      } catch (err) {
        console.warn(err)
      }
    },
    VIDEO_PAUSE(state) {
      !state.videoPlay.paused && state.videoPlay.pause()
    },
    VIDEO_PLAY(state) {
      state.videoPlay.paused && state.videoPlay.play()
    },
    VIDEO_MUTED(state, val) {
      try {
        state.videoPlay.muted
          ? (state.videoPlay.muted = false)
          : (state.videoPlay.muted = true)
      } catch (err) {
        console.warn(err)
      }
    },
    SET_VIDEO_CURRENTTIME_MS(state, number) {
      state.currentTimeMs = number
    },
    SET_VIDEO_VIDEO_MUTED(state, status) {
      state.muted = status
    },
    SET_VIDEO_PLAYING(state, status) {
      state.playing = status
    },
    SET_VIDEO_PLAY(state, dom) {
      state.videoPlay = dom
    },
    SET_VIDEO_STATUS(state, status) {
      state.videoStatus = status
    },
    SET_VIDEO_DURANTION(state, ms) {
      state.VIDEO_DURANTION_MS = ms
    },
    SET_VIDEO_EVENT(state, data) {
      state.videoEvent = data
    },

    SET_PER_PX_MS(state, number) {
      state.PER_PX_MS = number
    },
    SET_CUT_TRACK_WIDTH(state, number) {
      state.CUT_TRACK_WIDTH = number
    },

    // 设置 剪辑轨道数据
    SET_CUT_TRACK_DATA(state, data) {
      state.cutTrackData = data
    },

    SET_RANGE(state, number) {
      state.range = number
    },
    SET_MAX_RANGE(state, number) {
      state.maxRange = number
    },

    // 设置当前游标位置
    SET_PROGRESS_X(state, number) {
      state.progressX = number
    },

    // 设置装饰
    SET_DECORATE_LIST(state, list) {
      state.decorateList = list
    },
    ADD_DECORATE_LIST(state, list) {
      state.decorateList.push(list)
    },
    DELETE_DECORATE(state, index) {
      state.decorateList = state.decorateList.splice(index, 0)
    },
    SET_DECORATE_ACTIVE_ID(state, id) {
      state.decorateActiveId = id
    },

    SET_CANVAS_SIZE(state) {
      const clientW = Math.min(document.body.clientWidth, 1920)
      const baseSize = 1920
      const baseCanvasW = 952
      const baseCanvasH = 535
      const dpr = baseCanvasW / baseCanvasH

      const dprH = clientW / baseSize
      const canvasW = dprH * baseCanvasW
      const canvasH = canvasW / dpr

      state.canvasOptions.canvasW = Number(canvasW.toFixed(2))
      state.canvasOptions.canvasH = Number(canvasH.toFixed(2))
    },

    // 设置video 尺寸
    SET_VIDEO_SIZE(state, obj) {
      state.videoSize = obj
    },

    resetState(state) {
      Object.assign(state, getDefaultState())
    }
  }
}
