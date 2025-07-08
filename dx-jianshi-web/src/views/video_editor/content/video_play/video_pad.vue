<template>
  <div class="video-canvas-wrap" @click.stop="handerClick($event)">
    <video-palyer
      class="vjs-custom-skin"
      ref="videoPlayer"
      :options="playerOptions"
      :playsinline="true"
      @play="onPlayerPlay($event)"
      @pause="onPlayerPause($event)"
      @ended="onPlayerEnded($event)"
      @loadeddata="onPlayerLoadeddata($event)"
      @waiting="onPlayerWaiting($event)"
      @playing="onPlayerPlaying($event)"
      @seeked="onSeeked()"
      @timeupdate="onPlayerTimeupdate($event)"
      @canplay="onPlayerCanplay($event)"
      @canplaythrough="onPlayerCanplaythrough($event)"
      @statechanged="playerStateChanged($event)"
    >
    </video-palyer>

    <!-- <canvas id="canvas1"
            height="324"
            width="576"> </canvas> -->

    <!-- <loading v-if="player_waiting" /> -->

    <!-- <video-controls ref="controls"
                    @setVideoPaused="setVideoPaused">
    </video-controls> -->

    <!-- <textDragResize></textDragResize> -->

    <!-- <imageDragResize @computeFrameStart="computeFrameStart"></imageDragResize> -->
  </div>
</template>

<script>
import videoPalyer from './video_palyer'
import videoControls from './video_controls'
import textDragResize from '../../content/right_pad/text_main/drag-resize/index'
import imageDragResize from '../../content/right_pad/image_main/drag-resize/index'
import * as canvasFunction from '@/utils/canvas.js'

export default {
  props: {},
  data() {
    return {
      video: null,
      canvas: null,
      context: null,
      player_waiting: false,
      video_index: null,
      paused: true,
      autoplay: false,
      playerOptions: {
        width: '576',
        autoplay: true,
        muted: false,
        language: 'en',
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        src: '',
        poster: '',
        fluid: true
      },
      mosaic_show_index: -1,
      loading_index: 0
    }
  },
  components: {
    videoPalyer,
    videoControls,
    textDragResize,
    imageDragResize
  },

  computed: {
    player() {
      return this.$refs.videoPlayer
    },
    progrees_x() {
      return this.$store.state.videoM.progrees_x
    },
    clip_list() {
      return this.$store.state.videoM.cut_track_list
    },
    video_paused() {
      return this.$store.state.videoM.video_paused
    },
    currentTime() {
      return this.$store.state.videoM.currentTime
    },
    currentTimeForprogress() {
      return this.$store.state.videoM.currentTimeForprogress
    }
  },

  watch: {
    currentTimeForprogress(ms) {
      this.video.pause()
      this.video.currentTime = ms / 1000
      // this.video.play()
    },
    progrees_x(val) {
      var left = val
      this.findIndex()
    },
    clip_list: {
      handler() {
        this.findIndex()
      },
      deep: true
    },
    video_index(index) {
      // 找到对应时间轨道的视频
      if (parseInt(index) >= 0) {
        this.player_waiting = true
        this.playerOptions.src = this.clip_list[index].video_url
        var start_time = this.clip_list[0].start_time || 0
        var currentTime = Math.floor(start_time)
        this.$nextTick(() => {
          this.video.currentTime = currentTime
          this.video.play()
          // this.$refs.controls.videoPlay()
          var timeLine = document.querySelector('.time-line-wrap')
          let left = start_time * (this.$store.state.videoM.per_px_ms / 1000)
        })
      } else {
        // this.playerOptions.src = ''
      }
    },
    video_paused() {
      // 时间轴是否暂停事件
      console.log(this.video_paused)

      if (parseInt(this.video_index) >= 0) {
        if (this.video_paused) {
          this.video.pause()
          this.playerOptions.autoplay = false
        } else {
          this.playerOptions.autoplay = true
          this.video.play()
        }
      }
    }
  },

  methods: {
    // listen event
    onSeeked(player) {
      console.log('onSeeking!')
      let left =
        this.$store.state.videoM.currentTime /
        this.$store.state.videoM.per_px_ms
      this.scrollToBottom(left - 500)
    },
    onPlayerPlay(player) {
      console.log('player play!')
      // this.switchToCanvas()
      // this.player_waiting = false
    },
    onPlayerPause(player) {
      // console.log('player pause!', player)
    },
    onPlayerEnded(player) {
      // console.log('player ended!', player)
    },
    onPlayerLoadeddata(player) {
      console.log('player Loadeddata!')
      this.$store.commit('setTotalMs', player.duration * 1000)

      // this.player_waiting = true
      // this.$store.commit('setVideoPaused', false)
    },
    onPlayerWaiting(player) {
      console.log('player Waiting!')
      // this.player_waiting = true
    },
    onPlayerPlaying(player) {
      // console.log(player.played)
      // console.log('player Playing!', player)
    },
    onPlayerTimeupdate(player) {
      // console.log('player Timeupdate!', player.currentTime)
      this.$store.commit('setCurrentTime', player.currentTime)
    },
    onPlayerCanplay(player) {
      console.log('player Canplay!')
      console.log(
        'onPlayerLoadeddata -> player.duration',
        player.duration * 1000
      )
      this.setCutListDuration(player.duration * 1000)
    },
    onPlayerCanplaythrough(player) {
      // this.canvas.height = this.canvas.width * (this.video.videoHeight / this.video.videoWidth)
      // this.switchToCanvas()
      this.player_waiting = false
      this.loading_index++
      if (this.loading_index < 2) {
        this.scrollToBottom(this.clip_list[0].left)
      }
      console.log('player Canplaythrough!')
    },
    // or listen state event
    playerStateChanged(playerCurrentState) {
      // console.log('player current update state', playerCurrentState)
    },

    switchToCanvas() {
      if (parseInt(this.video_index) >= 0) {
        this.context.drawImage(
          this.video,
          0,
          0,
          this.canvas.width,
          this.canvas.height
        )
        window.requestAnimationFrame(this.switchToCanvas)

        if (this.video.paused || this.video.ended) {
          return
        }

        console.log('绘制canvas结束')
      } else {
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height)
        this.context.fillStyle = '#000'
        this.context.fillRect(0, 0, this.canvas.width, this.canvas.height)
      }
    },
    setCanvas() {
      this.canvas = document.getElementById('canvas1')
      this.context = this.canvas.getContext('2d')
      this.context.fillStyle = '#000'
      this.context.fillRect(0, 0, this.canvas.width, this.canvas.height)
      this.video = document.getElementById('myVideo')
      this.video.startTime = 100
    },
    handerClick(ev) {},

    // 找到 当前应该播放的视频 返回 index
    findIndex() {
      // var progrees_x = this.progrees_x
      this.video_index = 0
      // this.video_index = this.clip_list.findIndex(item => {
      //   var left = item.left || 0
      //   return progrees_x >= left && progrees_x <= left + item.width
      // })
    },
    setVideoPaused(status) {
      this.paused = status
    },
    // 滚动到底部
    scrollToBottom(left) {
      let scrollLeft = Math.max(left, 0)
      console.log('scrollToBottom -> scrollLeft', scrollLeft)
      this.$nextTick(() => {
        setTimeout(() => {
          var scrollDom = document.querySelector('.time-line-wrap')
          scrollDom.scrollLeft = scrollLeft
        }, 13)
      })
    },
    setCutListDuration(d) {
      var track_obj = this.$store.state.videoM.cut_track_list[0]
      var edtortotalms = this.$store.state.edtor_total_ms
      let per_px_ms = this.$store.state.videoM.per_px_ms
      var video_duration = parseInt(d)

      edtortotalms = Math.min(edtortotalms, video_duration)

      // let left = track_obj.start_time * (1000 / per_px_ms)

      track_obj.width = edtortotalms / per_px_ms
      track_obj.maxWidth = Math.ceil(edtortotalms / per_px_ms)
      // track_obj.left = left

      var arr = [track_obj]
      console.log('setCutListDuration -> track_obj', track_obj)

      this.$store.commit('setCutTrackList', arr)
    }
  },

  created() {
    this.findIndex()
  },

  mounted() {
    this.video = document.getElementById('myVideo')
  }
}
</script>

<style lang="scss">
.video-canvas-wrap {
  position: relative;
  .bg-loading {
    position: absolute;
    top: 0;
    background-color: rgba(255, 255, 255, 0.4);
    z-index: 10;
  }
  .video-wrap {
    position: relative;
    // overflow: hidden;

    #myVideo {
      width: 770px;
      // height: 100%;
      // position: absolute;
      // left: 99999;
      // display: none;
    }
  }
}
</style>
