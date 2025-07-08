<!--
 * @Author: zzx
 * @Date: 2020-07-22 20:26:53
 * @LastEditTime: 2021-09-26 21:07:48
 * @FilePath: /video_edit/src/components/video_play/video_palyer.vue
-->
<template>
  <div class="videoWrap">
    <video
      id="myVideo"
      ref="video"
      :autoplay="optionsTemp.autoplay"
      :poster="optionsTemp.poster"
      controlslist="nodownload"
      :preload="optionsTemp.preload"
      :muted="optionsTemp.muted"
      controls
      :src="optionsTemp.src"
    />
  </div>
</template>

<script>
const DEFAULT_EVENTS = ['loadeddata', 'loadedmetadata', 'canplay', 'volumechange', 'durationchange', 'canplaythrough', 'play', 'pause', 'waiting', 'playing', 'ended', 'timeupdate', 'seeked', 'seeking', 'ratechange']

export default {
  props: {
    options: {
      type: Object,
      default: () => {
        return {

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
      optionsTemp: {
        autoplay: false,
        src: '',
        poster: '',
        preload: 'auto',
        muted: false
      },
      player: null
    }
  },
  watch: {
    options: {
      handler() {
        this.optionsTemp = Object.assign(this.optionsTemp, this.options)
      },
      deep: true,
      immediate: true
    }
  },

  mounted() {
    this.initialize()
  },

  methods: {
    initialize() {
      // cross origin
      if (this.optionsTemp.crossOrigin) {
        this.$refs.video.crossOrigin = this.optionsTemp.crossOrigin
        this.$refs.video.setAttribute('crossOrigin', this.optionsTemp.crossOrigin)
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
  // width: 576px;
  // height: 320px;
  // margin-bottom: 20px;

  // video {
  //   width: 100%;
  //   height: 100%;
  // }
}
</style>
