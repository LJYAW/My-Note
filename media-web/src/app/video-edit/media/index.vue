<!--
 * @Author: your name
 * @Date: 2021-07-27 14:44:52
 * @LastEditTime: 2021-09-27 11:59:50
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/media/index.vue
-->
<template>
  <div class="media-content">

    <VideoPlay
      ref="VideoPlay"
      class="video-play"
      :options="videoOptions"
      :playsinline="true"
      @loadedmetadata="onPlayerLoadedmetadata($event)"
      @durationchange="onPlayerDurationchange($event)"
      @play="onPlayerPlay($event)"
      @pause="onPlayerPause($event)"
      @seeking="onPlayerSeeking($event)"
      @ended="onPlayerEnded($event)"
      @loadeddata="onPlayerLoadeddata($event)"
      @waiting="onPlayerWaiting($event)"
      @playing="onPlayerPlaying($event)"
      @timeupdate="onPlayerTimeupdate($event)"
      @canplay="onPlayerCanplay($event)"
      @error="onPlayerError($event)"
      @volumechange="onPlayerVolumechange($event)"
      @canplaythrough="onPlayerCanplaythrough($event)"
      @videoStatusChange="videoStatusChange"
    />

    <div class="video-canvas-wrap" :style="`width:${canvasW}px;height:${canvasH}px`">

      <VideoCanvas v-if="!videoLoading" ref="VideoCanvas" />

      <!-- <Decorate /> -->

      <Loading v-if="videoLoading" class="canvas-loading" />

    </div>

    <VideoControl @controlHandel="controlHandel" />

  </div>
</template>

<script>
import VideoPlay from '../components/VideoPlayer/index.vue'
import VideoCanvas from './canvas.vue'
import VideoControl from '../components/VideoControl/index.vue'
import { mapState } from 'vuex'
import Loading from '../components/VLoading/index.vue'
import Decorate from './decorate.vue'
import videoKeyMap from '../utils/video-key-map'

export default {
  components: {
    VideoPlay,
    VideoCanvas,
    Loading,
    // Decorate,
    VideoControl
  },
  props: {

  },
  data() {
    return {
      videoError: false,
      videoLoading: true,
      loadedmetadata: true
    }
  },
  computed: {
    ...mapState('video', ['playing', 'videoPlay', 'canvasOptions', 'muted', 'videoStatus', 'videoOptions', 'VIDEO_DURANTION_MS']),
    canvasW() {
      return this.canvasOptions.canvasW
    },
    canvasH() {
      return this.canvasOptions.canvasH
    }

  },
  watch: {

  },
  created() {

  },
  mounted() {
    videoKeyMap(this.$refs.VideoPlay.$refs.video)
  },
  methods: {
    onPlayerLoadedmetadata(player) {
      this.loadedmetadata = false
      this.$store.commit('video/SET_VIDEO_PLAY', this.$refs.VideoPlay.$refs.video)
      this.$store.commit('video/SET_VIDEO_VIDEO_MUTED', this.videoPlay.muted)
      console.log('🚀 ~ file: index.vue ~ line 141 ~ onPlayerLoadedmetadata ~ this.videoPlay.videoWidth', this.videoPlay.videoWidth)
      this.$store.commit('video/SET_VIDEO_SIZE', { w: this.videoPlay.videoWidth, h: this.videoPlay.videoHeight })
    },
    onPlayerDurationchange(player) {
      console.log('提示视频的时长已改变')
      this.$store.commit('video/SET_VIDEO_DURANTION', player.duration * 1000)
    },
    onPlayerLoadeddata(player) {
      console.log('提示当前帧的数据是可用的')
    },
    onPlayerPlay(player) {
      this.$store.commit('video/SET_VIDEO_PLAYING', true)
    },
    onPlayerPause(player) {
      // console.log('player pause!', player)
      this.$store.commit('video/SET_VIDEO_PLAYING', false)
    },
    onPlayerCanplay(player) {
      this.videoLoading = false
      console.log('提示该视频已准备好开始播放')
    },
    videoStatusChange(event) {
      console.log('提示该视频播放状态发生改变', event)
      this.$store.commit('video/SET_VIDEO_STATUS', event)
    },
    onPlayerSeeking(player) {
      console.log('🚀 ~ file: index.vue ~ line 106 ~ onPlayerSeeking ~ onPlayerSeeking')
    },
    onPlayerEnded(player) {
      // console.log('player ended!', player)
    },
    onPlayerWaiting(player) {
      this.videoLoading = true
      console.log('player Waiting!')
    },
    onPlayerPlaying(player) {
      // console.log('player Playing!', player)
    },
    onPlayerTimeupdate(player) {
      !player.paused && this.$store.commit('video/SET_VIDEO_CURRENTTIME_MS', Math.floor(player.currentTime * 1000))
    },
    onPlayerCanplaythrough(player) {
      // console.log('player Canplaythrough!', player)
    },
    onPlayerVolumechange(player) {
      console.log('提示当前声音发生改变', player.muted)
      this.$store.commit('video/SET_VIDEO_VIDEO_MUTED', player.muted)
    },
    onPlayerError(player, err) {
    },
    controlHandel(str) {
      const event = {
        play: () => {
          this.$store.commit('video/VIDEO_PLAY_OR_PAUSE')
        },
        muted: () => {
          this.$store.commit('video/VIDEO_MUTED')
        }
      }
      event[str] && event[str]()
    }

  }
}
</script>

<style scoped lang="scss">
.media-content {
  position: relative;
  background-color: #000;

  .videoWrap {
    display: none;
  }

  .canvas-loading {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: calc(100% - 50px);
    z-index: 11;
  }

  .decorate-dragegable-wrap {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: calc(100% - 50px);
  }
}

.video-canvas-wrap {
  background-color: #242428;
  display: flex;
  justify-content: center;
}
</style>
