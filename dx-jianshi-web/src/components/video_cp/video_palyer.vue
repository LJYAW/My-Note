<template>
  <div id="videoWrap">
    <video :id="'videoPlayer' + this._uid" playsinline webkit-playsinline crossOrigin="Anonymous" class="video-js"></video>
  </div>
</template>

<script>
import './video_offset'
import 'video.js/dist/video-js.css'
import '@/assets/scss/mixins/custom-video.scss'
import './video_watermark.js'
import './video_watermark.scss'
import { stringify } from 'querystring'

export default {
  name: 'VideoPlayer',
  props: {
    video_options: {
      type: Object,
      default() {
        return {}
      }
    },
    controls_show: {
      type: Boolean,
      default() {
        return true
      }
    },
    crossOrigin: {
      type: [String, Boolean],
      default() {
        return 'anonymous'
      }
    },
    start_time_s: {
      type: Number,
      default() {
        return 0
      }
    },
    second_time: {
      type: Number,
      default() {
        return 0
      }
    },
    video_offset_ms: {
      type: Object,
      default() {
        return {
          start_ms: 0,
          end_ms: 0
        }
      }
    },
    pause_time: {
      type: Number,
      default() {
        return 0
      }
    },
    checkout_video_src: {
      type: String,
      default: ''
    },
    fluid: {
      type: Boolean,
      default() {
        return true
      }
    }
  },
  data() {
    return {
      player: null,
      options: {
        autoplay: false, // 自动播放
        controls: true, // 是否显示控制栏
        sourceOrder: true,
        crossOrigin: 'Anonymous',
        notSupportedMessage: '此视频暂无法播放', // 无法播放时显示的信息
        height: '',
        width: '',
        language: 'zh',
        source: [
          {
            // type: 'video/mp4',
            src: ''
          }
        ],
        fluid: true,
        poster: '',
        featuresPlaybackRate: false,
        playbackRates: [0.7, 1, 1.5, 2, 3],
        controlBar: {
          // 配置控制栏
          currentTimeDisplay: true, // 当前时间
          timeDivider: true, // 时间分割线
          durationDisplay: true, // 总时间
          progressControl: true, // 进度条
          customControlSpacer: false, // 未知
          fullscreenToggle: false, // 全屏
          remainingTimeDisplay: false
        }
      },
      status_loading: true,
      video_error: false,
      withSourceHandlers: false
    }
  },
  computed: {
    updata_num() {
      return this.$store.state.updata_unmebr
    }
  },
  watch: {
    second_time() {
      if (this.player) {
        var s = this.second_time
        // this.player.currentTime(parseFloat(100))
        this.player.currentTime(parseFloat(s / 1000))
      }
    },
    updata_num(val) {
      var s = this.player.currentTime()
      this.$store.commit('getVideoTime', s)
    },
    checkout_video_src() {
      this.player.src([
        {
          type: this.video_options.type || 'video/mp4',
          src: this.checkout_video_src
        }
      ])
      this.player.load()
      this.player.play()

      this.player.currentTime(parseFloat(this.second_time / 1000))
    }
  },
  methods: {
    videoInit() {
      const self = this

      var video_options = JSON.parse(JSON.stringify(self.video_options))
      self.options.width = video_options.width
      self.options.height = video_options.height
      self.options.poster = video_options.poster
      self.options.autoplay = video_options.autoplay
      self.options.controls = self.controls_show
      self.options.fluid = self.fluid
      // 快剪
      if (self.$route.path.indexOf('live_video') != -1) {
        self.options.autoplay = true
      }
      var id = 'videoPlayer' + this._uid

      self.player = this.$videojs(id, self.options, function onPlayerReady() {
        // 直播
        if (self.video_options.is_live) {
          self.options.controlBar.currentTimeDisplay = false
          var dom = this.$$('.vjs-time-control')
          dom.forEach(item => {
            self.$videojs.dom.removeClass(item, 'vjs-time-control')
          })
        }
        self.player.src([
          {
            type: video_options.type || 'video/mp4',
            src: video_options.src
          }
        ])
        self.player.load()
        self.player.reset()
        // this.volume(0)

        if (self.start_time_s > 0) {
          var s = self.start_time_s
          this.currentTime(s)
        }

        this.on('play', function() {
          self.$emit('videoPlay')
        })
        // 是否暂停
        this.on('pause', function() {
          self.$emit('videoPause', this.currentTime())
        })

        // 视频error
        this.on('error', function() {
          self.video_error = true
          self.setError()
        })
        // 时间更新
        this.on('timeupdate', function() {
          const curTime = this.currentTime()

          if (self.pause_time && parseInt(self.$store.state.clip_index) >= 0) {
            if (curTime * 1000 - self.pause_time > 0) {
              self.player.pause()
            }
          }

          // self.$store.commit('timeupdate', curTime)
          self.$emit('timeupdate', curTime)
          if (curTime > 60) {
            self.videoOffset()
          }
          // 已经播完
          if (this.ended()) {
            self.$emit('videoEnded')
          }
        })
        let id = 'videoPlayer' + self._uid + '_html5_api'
        var videoEl = document.getElementById(id)

        self.$emit('getVideoEl', videoEl)
        self.$emit('getVideoId', id)

        if (self.crossOrigin) {
          self.$videojs.dom.setAttribute(videoEl, 'crossOrigin', self.crossOrigin)
        }
        // self.$store.commit('setVideoId', id)
      })
    },
    videoOffset() {
      if (this.player) {
        var start_ms = this.video_offset_ms.start_ms / 1000
        var end_ms = this.video_offset_ms.end_ms / 1000
        this.player.offset({
          start: start_ms || 0,
          end: end_ms || 0,
          restart_beginning: true /// 视频结束时是否应转到开头
        })
      }
    },
    setWatermark() {
      // var imgUrl = require('@/assets/images/watermark.png')
      // this.player.watermark({
      //   image: imgUrl,
      //   fadeTime: null
      // })
    },
    setError() {
      var dom = document.querySelector('.vjs-modal-dialog-content')
      var str =
        '<div class="h-100 d-flex justify-content-center align-items-center"><i class="iconfont iconcuowutishi fz-30px fc-c mr-10px"></i>糟糕，视频播放出错！请刷新页面重试或者换个视频看看</div>'
      dom.innerHTML = str
      this.player.load()
      this.player.reset()
    },
    videoPause() {
      this.player.pause()
    }
  },
  created() {},
  mounted() {
    this.videoInit()
    this.videoOffset()
    this.setWatermark()
  },
  beforeDestroy() {
    if (this.player) {
      this.player.dispose()
    }
  }
}
</script>
<style scoped lang="scss">
#videoWrap {
  position: relative;
  .watermark {
    position: absolute;
    top: 0;
    pointer-events: none;
    opacity: 0.3;
  }
}
</style>
