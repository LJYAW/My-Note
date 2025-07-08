/*
 * @Author: zzx
 * @Date: 2020-09-16 15:01:20
 * @LastEditTime: 2021-07-09 18:45:22
 * @FilePath: /video_edit_cut/src/pages/media/video_play/bus_event.js
 */
export default {
  methods: {
    setCurrentTimeMs(player) {
      // 时间轴点击事件
      this.$bus.$on('setCurrentTimeMs', ms => {
        let s = ms / 1000
        player.currentTime = s
        this.$store.commit('setVideoPlayOrPasu', false)
      })
    },
    setStartTimeMs(player) {
      // 时间设置跳转时间
      this.$bus.$on('setStartTimeMs', ms => {
        let s = ms / 1000
        player.currentTime = s
      })
    },
    keyEvents(player) {
      return {
        // 左
        37: () => {
          const s = 0.1
          player.pause()
          player.currentTime = player.currentTime - s
          this.$store.commit('setVideoPlayStatus', 'pause')
        },
        // 右
        39: () => {
          const s = 0.1
          player.pause()
          player.currentTime = player.currentTime + s
          this.$store.commit('setVideoPlayStatus', 'pause')
        },
        // 空格
        32: () => {
          if (player.paused) {
            player.play()
            this.$store.commit('setVideoPlayStatus', 'play')
          } else {
            player.pause()
            this.$store.commit('setVideoPlayStatus', 'pause')
          }
        }
      }
    }
  },
  created() {
  },
  mounted() {}
}
