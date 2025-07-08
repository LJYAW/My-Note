import { Object } from 'core-js'
import Vue from 'vue'

/*
 * @Author: zzx
 * @Date: 2020-08-07 19:27:01
 * @LastEditTime: 2020-11-11 19:02:55
 * @FilePath: /video_edit/src/store/mutations.js
 */
export default {
  setPerPxMs(state, num) {
    state.PER_PX_MS = num
  },
  setCurrentTimeMs(state, num) {
    state.currrent_time_ms = num
  },
  setDecorateList(state, list) {
    state.decorate_list = list
  },
  deleteDecorate(state, index) {
    state.decorate_list = state.decorate_list.splice(index, 1)
  },
  setProgreesX(state, num) {
    state.progrees_x = num
  },
  setdecorateActiveId(state, id) {
    // 设置马赛克轨道 index
    state.decorate_active_id = id
  },
  setCutTrackDetails(state, data) {
    // state.cut_details = null
    let obj = Object.assign(state.cut_details, data)
    state.cut_details = { ...obj }
  }
}
