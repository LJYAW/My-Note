<!--
 * @Author: zzx
 * @Date: 2020-09-16 18:57:37
 * @LastEditTime: 2020-11-25 11:43:01
 * @FilePath: /video_edit/src/pages/media/video_control/index.vue
-->
<template>
  <div class="videoControls">
    <div class="play-wrap">
      <a @click="videoPlayOrPause(true)" v-if="videoPlayStatus == 'pause'">
        <i class="iconfont icon-yinfukaishijian"></i>
      </a>
      <a @click="videoPlayOrPause(false)" v-if="videoPlayStatus == 'play'">
        <i class="iconfont icon-yinfuzantingjian"></i>
      </a>
    </div>
    <div class="volume-wrap">
      <a @click="setVideoPlayMuted(true)" v-if="videoPlayMuted">
        <i class="iconfont icon-volume"></i>
      </a>
      <a @click="setVideoPlayMuted(false)" v-else>
        <i class="iconfont icon-jingyin"></i>
      </a>
    </div>
    <div class="video-time">
      <span>{{ current_time_ms | msToSecond }} / {{ duration | msToSecond}}</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {}
  },
  computed: {
    videoPlayStatus() {
      return this.$store.state.videoPlay.video_play_status
    },
    videoPlayMuted() {
      return this.$store.state.videoPlay.video_play_muted
    },
    current_time_ms() {
      return this.$store.state.videoPlay.current_time_ms
    },
    duration() {
      return this.$store.state.videoPlay.duration
    }
  },
  watch: {},
  methods: {
    videoPlayOrPause(status) {
      this.$store.commit('setVideoPlayOrPasu', status)
    },
    setVideoPlayMuted(status) {
      this.$store.commit('setVideoPlayMuted', status)
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.videoControls {
  display: flex;
  align-items: center;
  position: absolute;
  bottom: -25px;
  width: 100%;
  color: white;
  font-size: 12px;
  .volume-wrap {
    margin-left: 10px;
  }
  .icon-volume,
  .icon-jingyin {
    font-size: 18px;
  }
  .video-time {
    margin-left: auto;
  }
}
</style>
