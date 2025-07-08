<!--
 * @Author: your name
 * @Date: 2021-07-27 14:44:52
 * @LastEditTime: 2021-09-01 15:18:25
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

    <canvas id="canvasVideo" />

    <VideoControl @controlHandel="controlHandel" />

    <VLoading v-if="videoLoading" class="canvas-loading" />

    <!-- <VideoCanvas ref="VideoCanvas" />

    <Decorate />

    <Loading v-if="videoLoading" class="canvas-loading" />
    -->

  </div>
</template>

<script>
import { mapState } from 'vuex'
import VideoPlay from '../components/VideoPlayer/index.vue'
import CanvasVideo from '../js/canvas-video'
import VideoControl from '../components/VideoControl/index.vue'
// import VideoCanvas from './canvas.vue'
import VLoading from '../components/VLoading/index.vue'
// import Decorate from './decorate.vue'
import videoKeyMap from '../utils/video-key-map'

export default {
  components: {
    VideoPlay,
    VideoControl,
    VLoading
    // VideoCanvas,
    // Decorate,
  },
  props: {

  },
  data() {
    return {
      videoError: false,
      videoLoading: false,
      loadedmetadata: true,

      canvasVideo: null
    }
  },
  computed: {
    ...mapState('video', ['videoOptions', 'canvasOptions'])
  },
  watch: {
    canvasOptions: {
      handler() {
        this.setCanvasVideo()
      },
      deep: true
    }
  },
  created() {

  },
  mounted() {
    this.setCanvasVideo()
  },
  methods: {
    setCanvasVideo() {
      const canvasEl = document.getElementById('canvasVideo')
      const videoEl = this.$refs.VideoPlay.player
      const { canvasW, canvasH } = this.canvasOptions
      const option = {
        canvasW: canvasW,
        canvasH: canvasH
      }
      this.canvasVideo = new CanvasVideo(canvasEl, videoEl, option)
    },
    // 时长变化。当指定的音频/视频的时长数据发生变化时触发，加载后，时长由 NaN 变为音频/视频的实际时长
    onPlayerDurationchange(player) {
      videoKeyMap(player)
      this.canvasVideo.getContainPosition(player)
      player.poster && this.canvasVideo.drawImg(player.poster)
      this.$store.commit('video/SET_VIDEO_DURANTION', player.duration * 1000)
      console.log('提示视频的时长已改变', player.duration)
    },
    // 元数据加载。当指定的音频/视频的元数据已加载时触发，元数据包括：时长、尺寸（仅视频）以及文本轨道
    onPlayerLoadedmetadata(player) {
      this.$store.commit('video/SET_VIDEO_PLAY', player)
      console.log('提示视频的元数据已加载')
    },
    // 视频下载监听。当当前帧的数据已加载，但没有足够的数据来播放指定音频/视频的下一帧时触发
    onPlayerLoadeddata(player) {
      console.log('提示当前帧的数据是可用的')
    },
    // 可播放监听。当浏览器能够开始播放指定的音频/视频时触发
    onPlayerCanplay(player) {
      this.videoLoading = false
      this.canvasVideo.drawVideo()
      console.log('提示该视频已准备好开始播放')
    },
    // 可流畅播放。当浏览器预计能够在不停下来进行缓冲的情况下持续播放指定的音频/视频时触发
    onPlayerCanplaythrough(player) {
      console.log('提示视频能够不停顿地一直播放')
    },
    // 播放监听
    onPlayerPlay(player) {
      this.$store.commit('video/SET_VIDEO_PLAYING', true)
      this.canvasVideo.play()
      console.log('提示该视频正在播放中')
    },
    // 暂停监听
    onPlayerPause(player) {
      this.$store.commit('video/SET_VIDEO_PLAYING', false)
      this.canvasVideo.paused()
      console.log('暂停播放')
    },
    // video 状态发生改变
    videoStatusChange(event) {
      console.log('状态发生改变')
    },
    // 查找开始。当用户开始移动/跳跃到音频/视频中新的位置时触发
    onPlayerSeeking(player) {
      console.log('开始移动进度条')
    },
    // 视频加载等待。当视频由于需要缓冲下一帧而停止，等待时触发
    onPlayerWaiting(player) {
      this.videoLoading = true
      console.log('视频加载等待')
    },
    // 播放结束
    onPlayerEnded(player) {
      console.log('视频播放完了')
    },
    onPlayerPlaying(player) {
      console.log('player Playing!', player)
    },
    onPlayerTimeupdate(player) {
      this.$store.commit('video/SET_VIDEO_CURRENTTIME_MS', player.currentTime * 1000)
      console.log('timeupdate')
    },
    onPlayerVolumechange(player) {
      this.$store.commit('video/SET_VIDEO_VIDEO_MUTED', player.muted)
      console.log('提示当前声音发生改变', player.muted)
    },
    onPlayerError(player, err) {
      console.log('视频出错了')
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
    height: calc(100% - 50px)
  }
}
</style>
