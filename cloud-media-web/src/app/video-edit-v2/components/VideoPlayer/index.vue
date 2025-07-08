<!--
 * @Author: zzx
 * @Date: 2020-07-22 20:26:53
 * @LastEditTime: 2021-08-25 18:35:18
 * @FilePath: /video_edit/src/components/video_play/video_palyer.vue
-->
<template>
  <div class="videoWrap">
    <video
      id="myVideo"
      ref="video"
      :autoplay="options.autoplay"
      :poster="options.poster"
      controlslist="nodownload"
      :preload="options.preload"
      :muted="options.muted"
      controls
      :src="options.src"
    />
  </div>
</template>

<script>
const DEFAULT_EVENTS = ['loadeddata', 'loadedmetadata', 'canplay', 'volumechange', 'durationchange', 'canplaythrough', 'play', 'pause', 'waiting', 'playing', 'ended', 'timeupdate', 'seeked', 'seeking']

export default {
  props: {
    options: {
      type: Object,
      default: () => {
        return {
          autoplay: 'autoplay',
          src: '',
          poster: '',
          preload: 'auto',
          muted: false
        }
      },
      required: true
    },
    events: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      player: null
    }
  },

  mounted() {
    this.initialize()
  },

  methods: {
    initialize() {
      // cross origin
      if (this.options.crossOrigin) {
        this.$refs.video.crossOrigin = this.options.crossOrigin
        this.$refs.video.setAttribute('crossOrigin', this.options.crossOrigin)
      }

      // emit event
      const emitPlayerState = (event, value) => {
        this.$emit(event, this.player)
        this.$emit('videoStatusChange', event)
      }

      this.player = document.getElementById('myVideo')

      this.player.addEventListener('error', (err) => {
        this.$emit('error', this.player, err)
      })

      const events = DEFAULT_EVENTS.concat(self.events)
      // watch events
      const onEdEvents = {}
      for (let i = 0; i < events.length; i++) {
        this.player.addEventListener(
          events[i],
          function() {
            emitPlayerState(events[i], this.player)
          },
          false
        )
      }
    }
  }
}
</script>

<style scoped lang="scss">
.videoWrap {
  width: 576px;
  height: 320px;
  margin-bottom: 20px;

  video {
    width: 100%;
    height: 100%;
  }
}
</style>
