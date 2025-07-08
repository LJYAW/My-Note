<!--
 * @Author: your name
 * @Date: 2021-03-07 18:40:09
 * @LastEditTime: 2021-05-18 15:59:56
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/model/VideoHls.vue
-->
<template>
  <div class="video-hls-wrap">
    <video-player
      ref="videoPlayer"
      class="vjs-custom-skin"
      :options="options"
      @ready="playerReadied"
      @timeupdate="onPlayerTimeupdate($event)"
      @loadeddata="onPlayerLoadeddata($event)"
    />
  </div>
</template>

<script>
// videojs
import videojs from 'video.js'
window.videojs = videojs

// hls plugin for videojs6
require('videojs-contrib-hls/dist/videojs-contrib-hls.js')
import './video_offset'

export default {
  name: 'VideoHls',
  components: {

  },
  props: {
    videoOffsetMs: {
      type: Object,
      default() {
        return {
          start_ms: 0,
          end_ms: 0
        }
      }
    },
    playerOptions: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      options: {
        // videojs and plugin options
        height: '360',
        sources: [{
          withCredentials: false,
          type: 'application/x-mpegURL',
          src: ''
        }],
        controlBar: {
          timeDivider: false,
          durationDisplay: false
        },
        flash: { hls: { withCredentials: false }},
        html5: { hls: { withCredentials: false }}
        // poster: 'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-5.jpg'
      },
      canPlay: false
    }
  },
  computed: {
    player() {
      return this.$refs.videoPlayer.player
    }
  },
  watch: {
  },
  created() {
    this.options = Object.assign(this.playerOptions, this.options)
    this.options.sources[0].src = this.playerOptions.src
  },
  mounted() {

  },
  methods: {
    onPlayerLoadeddata(player) {
    },
    playerReadied(player) {
      var hls = player.tech({ IWillNotUseThisInPlugins: true }).hls
      player.tech_.hls.xhr.beforeRequest = function(options) {
        return options
      }
      this.videoOffset(player, this.videoOffsetMs)
    },
    onPlayerTimeupdate(player) {
      // 不应该在全局处理 展示这么用
      this.$store.commit('setVideoCurrentTime', player.currentTime() * 1000)
    },
    videoOffset(player, obj) {
      if (player) {
        var start_ms = this.videoOffsetMs.start_ms / 1000
        var end_ms = this.videoOffsetMs.end_ms / 1000
        player.offset({
          start: start_ms || 0,
          end: end_ms || 0,
          restart_beginning: true // /视频结束时是否应转到开头
        })
      }
    }
    // setVideoProgress(player, time) {
    //   console.log(player.currentTime(), time)
    // }
  }
}
</script>

<style scoped lang="scss">

</style>
