// import Vue from "vue";

export default {
  state: {
    track_text_style: {}, // 添加的文字样式
    track_text_list: [], // 添加文字列表
    text_editor_active: false,
    text_active_index: null, // 当前选中的 文本框

    cut_track_list: [], // 剪辑框的 数据列表

    image_track_list: [], // 贴图的数据列表
    image_active_index: -1,

    progrees_x: 0, // 时间轨 指针线位置
    per_px_ms: 100, // 每像素多少毫秒
    total_ms: 180000,

    currentTime: 0,
    currentTimeForprogress: 0,
    // 播放还是暂停
    video_paused: true,
    second_time_line_width: 100,

    // 标记选中
    active_data: {
      index: 0,
      type: 'video'
    }
  },
  mutations: {
    // 添加文本数据
    setTrackTextStyle(state, data) {
      state.track_text_style = data
    },
    setTrackTextList(state, list) {
      state.track_text_list = list
    },
    setTextEditorActive(state, data) {
      state.text_editor_active = data
    },
    setTextActiveIndex(state, number) {
      state.text_active_index = number
    },

    // 设置指针
    setProgreesX(state, number) {
      state.progrees_x = number
    },

    setCutTrackList(state, list) {
      state.cut_track_list = list
    },
    setPerPxMs(state, number) {
      state.per_px_ms = number
    },
    setTotalMs(state, number) {
      state.total_ms = number
    },
    setSecondTimeLineWidth(state, number) {
      state.second_time_line_width = number
    },
    setActiveData(state, data) {
      state.active_data = data
    },
    setVideoPaused(state, data) {
      state.video_paused = data
    },
    setCurrentTime(state, number) {
      var ms = Math.floor(number * 1000)
      state.currentTime = ms
    },
    setCurrentTimeForProgress(state, ms) {
      state.currentTimeForprogress = ms
    },
    setImageTrackList(state, data) {
      state.image_track_list = data
    },
    setImageActiveIndex(state, number) {
      state.image_active_index = number
    }
  },
  actions: {},
  getters: {}
}
