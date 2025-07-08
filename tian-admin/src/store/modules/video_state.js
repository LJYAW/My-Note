/*
 * @Author: zzx
 * @Date: 2020-08-07 19:27:20
 * @LastEditTime: 2021-02-24 16:13:29
 * @FilePath: /video_edit/src/store/modules/video_state.js
 */
export default {
  state: {
    duration: 0,
    player_is_ready: false,
    player_loadeddata: false,
    current_time_ms: 0,
    video_play_status: ''
  },
  mutations: {
    setDurantion(state, num) {
      state.duration = num
    },
    setPlayerReadyStatus(state, status) {
      state.player_is_ready = status
    },
    setPlayLoadeddataStatus(state, status) {
      state.player_loadeddata = status
    },
    setVideoCurrentTime(state, num) {
      state.current_time_ms = num
    },
    setVideoPlayOrPasu(state, status) {
      const video = document.getElementById('myVideo')
      status ? video.play() : video.pause()
    },
    setVideoPlayStatus(state, status) {
      state.video_play_status = status
    }
  },
  actions: {},
  getters: {}
}
