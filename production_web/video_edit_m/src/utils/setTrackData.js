/*
 * @Author: zzx
 * @Date: 2020-09-16 15:01:20
 * @LastEditTime: 2021-06-23 15:11:04
 * @FilePath: /video_edit_cut/src/utils/setTrackData.js
 */
export default {
  // 装饰轨道
  methods: {
    addTrackData(obj) {
      const MAX_LENGTH = 20
      let decorate_list = this.$store.state.decorate_list
      if (decorate_list.length > MAX_LENGTH) {
        alert('最多添加' + MAX_LENGTH + '条')
        return
      }
      let item = Object.assign({ resizable: true }, obj)

      decorate_list.push(item)
      this.$store.commit('setDecorateList', decorate_list)
    },
    setTrackData(obj, id) {
      let decorate_list = this.$store.state.decorate_list
      let index = decorate_list.findIndex((item) => item.id == id)
      let trackData = decorate_list[index] || {}
      let item = Object.assign(trackData, obj)

      decorate_list.splice(index, 1, item)
      this.$store.commit('setDecorateList', decorate_list)
    },
    deleteTrackData(id) {
      let decorate_list = this.$store.state.decorate_list
      let index = decorate_list.findIndex((item) => item.id == id)
      decorate_list.splice(index, 1)
      this.$store.commit('setDecorateList', decorate_list)
    },
    parentPostMessage(cmd, data) {
      window.parent.postMessage({ cmd: cmd, data: data }, '*')
    }
  }
}
