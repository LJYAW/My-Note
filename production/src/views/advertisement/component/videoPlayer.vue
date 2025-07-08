<!--
 * @Author: your name
 * @Date: 2021-03-05 17:30:25
 * @LastEditTime: 2021-03-05 18:34:37
 * @LastEditors: your name
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/model/videoPlayer.vue
-->
<template>
  <div class="item">
    <div class="player">
      <video-player
        ref="videoPlayer"
        class="vjs-custom-skin"
        :options="playerOptions"
        :playsinline="true"
        @timeupdate="onPlayerTimeupdate($event)"
      />
    </div>
  </div>
</template>

<script>

export default {
  props: {
    playerOptions: {
      type: Object,
      default() {
        return {
          height: '360',
          autoplay: true,
          muted: true,
          language: 'en',
          playbackRates: [0.7, 1.0, 1.5, 2.0],
          sources: [{
            type: 'video/mp4',
            // mp4
            src: 'http://vjs.zencdn.net/v/oceans.mp4'
            // webm
            // src: "https://cdn.theguardian.tv/webM/2015/07/20/150716YesMen_synd_768k_vp8.webm"
          }],
          poster: 'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-1.jpg'
        }
      }
    }
  },
  data() {
    return {
      // videojs options

    }
  },
  computed: {
    player() {
      return this.$refs.videoPlayer.player
    }
  },
  mounted() {
  },
  methods: {
    onPlayerTimeupdate(player) {
      this.$store.commit('setVideoCurrentTime', player.currentTime() * 1000)
    }
  }
}
</script>

