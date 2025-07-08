<template>
  <div class="video-controls">
    <div class="progress-box"></div>

    <div class="controls-box ">
      <!-- 时间 -->
      <div class="controls-time"></div>

      <!-- 按钮组 -->
      <div class="controls-btn h-100 d-flex flex-column">
        <!-- <videoProgress></videoProgress> -->

        <div class="d-flex justify-content-between flex-1 align-items-center">
          <div class="d-flex align-items-center ml-15px">
            <a>
              <a v-if="paused"
                 class="mb-10px fc-white "
                 @click.stop.prevent="play"><i class="iconfont iconbofang1 fz-18px"></i></a>
              <a v-else
                 class="mb-10px fc-white "
                 @click.stop.prevent="play"><i class="iconfont iconzanting fz-18px"></i></a>
            </a>
            <!-- <div class="ml-15px">
              <span>{{currentTime |timeFilter}}</span>
              <span>/</span>
              <span v-if="this.video.durdation">{{duration |timeFilter}}</span>
              <span v-else>00:00</span>
            </div> -->
          </div>
          <div class="controls-right mr-5px cp">
            <span @click="getPlaybackRate(1)"
                  v-if="playbackRate == 1">1x</span>
            <span @click="getPlaybackRate(2)"
                  v-else-if="playbackRate == 2">2x</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import videoProgress from './video_progress/index'
export default {
  props: ['video'],
  data() {
    return {
      paused: true,
      playbackRate: 1
    }
  },
  components: { videoProgress },

  computed: {
    currentTime() {
      return Math.ceil(this.$store.state.currentTime)
    },
    duration() {
      return Math.ceil(this.video.duration)
    }
  },

  watch: {
    paused() {
      this.$store.commit('setVideoPaused', this.paused)
    }
  },

  methods: {
    // 倍速播放
    getPlaybackRate(num) {
      if (num == 1) {
        this.video.playbackRate = 2
        this.playbackRate = 2
      } else if (num == 2) {
        this.video.playbackRate = 3
        this.playbackRate = 3
      } else if (num == 3) {
        this.video.playbackRate = 1
        this.playbackRate = 1
      }
    },
    play() {
      this.paused = !this.paused
    },
    videoPaused() {
      this.paused = true
    },
    videoPlay() {
      this.paused = false
    }
  },

  created() {}
}
</script>

<style scoped lang="scss">
.video-controls {
  height: 40px;
  width: 100%;
  display: flex;
  flex-direction: column;
  position: absolute;
  bottom: -40px;
  z-index: 11;
  .controls-box {
    flex: 1;
    background: #151515;
    color: white;
    font-size: 14px;
  }
}
</style>
