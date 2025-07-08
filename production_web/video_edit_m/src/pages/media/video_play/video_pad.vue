<!--
 * @Author: zzx
 * @Date: 2020-08-05 15:24:55
 * @LastEditTime: 2021-07-12 10:54:57
 * @FilePath: /video_edit_cut/src/pages/media/video_play/video_pad.vue
-->
<template>
  <div class="player">
    <video-player
      ref="videoPlayer"
      :options="playerOptions"
      :playsinline="true"
      @play="onPlayerPlay($event)"
      @pause="onPlayerPause($event)"
      @ended="onPlayerEnded($event)"
      @loadeddata="onPlayerLoadeddata($event)"
      @waiting="onPlayerWaiting($event)"
      @playing="onPlayerPlaying($event)"
      @timeupdate="onPlayerTimeupdate($event)"
      @canplay="onPlayerCanplay($event)"
      @error="onPlayerError($event)"
      @canplaythrough="onPlayerCanplaythrough($event)"
      @statechanged="playerStateChanged($event)"
    ></video-player>

    <canvas id="videoCanvas" height="324" width="576"></canvas>

    <video-control></video-control>

    <decorate-box ref="decorateBox" @changeDecorateList="switchToCanvas"></decorate-box>
  </div>
</template>

<script>
import CanvasMixin from './canvas_mixins'
import BusEventMixin from './bus_event'
import videoControl from '../video_control/index'
// import decorateBox from '../decorate/btn_list'
import decorateBox from '../decorate/decorate'

export default {
  name: 'videoPad',
  props: {
    playerOptions: {
      type: Object,
      default() {
        return {
          proViewStatus: false,
          height: '360',
          autoplay: true,
          muted: false,
          language: 'en',
          playbackRates: [0.7, 1.0, 1.5, 2.0],
          fluid: true,
          // src: 'http://vd-drm.mtq.tvm.cn/video/DaTangLeiYinSi/2019/04/26/GanSuTV_1500000_20190426_1556283676616_0.mp4',
          src: '',
          poster: ''
        }
      }
    }
  },
  data() {
    return {
      // player: null,
      end_time: undefined
    }
  },
  mixins: [CanvasMixin, BusEventMixin],

  components: { videoControl, decorateBox },

  computed: {
    player() {
      return this.$refs.videoPlayer.player
    },
    videoPlayStatus() {
      return this.$store.state.videoPlay.video_play_status
    }
  },
  methods: {
    onPlayerLoadeddata(player) {
      if (!this.playerOptions.hls) {
        this.$store.commit('setDurantion', player.duration * 1000)
      }
      this.$store.commit('setPlayLoadeddataStatus', true)

      // 获取真实video的宽高
      // console.log('player Loadeddata!', player)
    },
    // or listen state event
    playerStateChanged(playerCurrentState) {
      // console.log('player current update state', playerCurrentState)
    },
    // listen event
    onPlayerPlay(player) {
      this.$store.commit('setVideoPlayStatus', 'play')
      this.switchToCanvas()
      // console.log('player play!', player)
    },
    onPlayerPause(player) {
      // console.log('player pause!', player)
      this.$store.commit('setVideoPlayStatus', 'pause')
    },
    onPlayerEnded(player) {
      // console.log('player ended!', player)
    },

    onPlayerWaiting(player) {
      // console.log('player Waiting!', player)
    },
    onPlayerPlaying(player) {
      // console.log('player Playing!', player)
    },
    onPlayerTimeupdate(player) {
      // this.player = player
      // console.log('player Timeupdate!', player.currentTime())
      // 设置当前播放时间
      if (player.currentTime >= this.end_time && this.proViewStatus) {
        this.proViewStatus = false
          player.currentTime = this.end_time
          player.pause()
      }
      this.$store.commit('setVideoCurrentTime', player.currentTime * 1000)
    },
    onPlayerCanplay(player) {
      // console.log(player.duration)
      // console.log('player Canplay!', player)
      this.$store.commit('setPlayerReadyStatus', true)
      this.setCurrentTimeMs(player)
      this.setStartTimeMs(player)
    },
    onPlayerCanplaythrough(player) {
      // console.log('player Canplaythrough!', player)
    },
    onPlayerError(player, err) {
      let canvas = document.getElementById('videoCanvas')
      let width = canvas.width
      let height = canvas.height
      let context = canvas.getContext('2d') // 获取对应的2D对象(画笔)
      context.fillStyle = '#DC143C'
      context.strokeStyle = '#ddd' // 设置笔触的颜色
      context.font = "bold 16px '字体','字体','微软雅黑','宋体'" // 设置字体
      context.textAlign = 'center'
      context.fillText('糟糕，视频出错了 !', width / 2, height / 2) // 设置文本内容
    }
  },

  mounted() {
    this.$bus.$on('setTime', s => {
      this.proViewStatus = true
      this.player.pause()
      // this.$store.commit('setVideoPlayOrPasu', 'pause')
      this.player.currentTime = s.start_ms / 1000
      this.end_time = s.end_ms / 1000
    })
    document.onkeydown = event => {
      const e = event || window.event
      if (e) {
        const code = e.keyCode
         this.keyEvents()[code] && this.keyEvents(this.player)[code]()
         return false
      }
    }
  },
  beforeDestroy() {
    // 销毁监听事件
    this.$bus.$off('setTime')
  },
  created() {

  }
}
</script>
<style lang="scss">
#myVideo {
  display: none;
}
</style>
