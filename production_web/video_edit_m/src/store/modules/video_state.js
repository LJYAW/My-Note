/*
 * @Author: zzx
 * @Date: 2020-08-07 19:27:20
 * @LastEditTime: 2021-07-06 18:17:46
 * @FilePath: /video_edit/src/store/modules/video_state.js
 */
export default {
  state: {
    duration: 0,
    player_is_ready: false,
    player_loadeddata: false,
    current_time_ms: 100,
    video_play_status: '',
    video_play_muted: 0
  },
  mutations: {
    setDurantion(state, num) {
      state.duration = Math.max(num, 4000)
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
      let video = document.getElementById('myVideo')
      status ? video.play() : video.pause()
    },
    setVideoPlayStatus(state, status) {
      state.video_play_status = status
    },
    setVideoPlayMuted(state, status) {
      state.video_play_muted = !status
      let video = document.getElementById('myVideo')
      video.muted = false
      video.volume = state.video_play_muted ? 1 : 0
      console.log(
        '🚀 ~ file: video_state.js ~ line 40 ~ setVideoPlayMuted ~ state.video_play_muted',
        state.video_play_muted
      )
    }
  },
  actions: {},
  getters: {}
}
