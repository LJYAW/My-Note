/*
 * @Author: zzx
 * @Date: 2020-08-10 11:19:41
 * @LastEditTime: 2021-07-07 19:22:10
 * @FilePath: /video_edit/src/store/modules/range.js
 */
const state = {
  range: 5
}

const mutations = {
  setTimeLineRange(state, num) {
    state.range = num
  }
}

export default {
  state,
  mutations
}
