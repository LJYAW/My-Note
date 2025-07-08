<!--
 * @Author: zzx
 * @Date: 2020-07-22 20:26:53
 * @LastEditTime: 2021-05-08 19:12:07
 * @FilePath: /video_edit/src/components/video_play/video_palyer.vue
-->
<template>
  <div class="videoWrap">
    <video
      id="myVideo"
      ref="video"
      :autoplay="options.autoplay"
      muted
      controlslist="nodownload"
      preload="auto"
      controls
      :src="options.src"
    ></video>
  </div>
</template>

<script>
import Hls from 'hls.js'
const DEFAULT_EVENTS = ['loadeddata', 'canplay', 'canplaythrough', 'play', 'pause', 'waiting', 'playing', 'ended', 'timeupdate', 'seeked']

export default {
  props: {
    options: {
      type: Object,
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
  components: {},

  computed: {},

  watch: {},

  methods: {
    setHls() {
      if (!this.options.hls) return
      if (Hls.isSupported()) {
        var video = document.getElementById('myVideo')

        var hls = new Hls()
        // bind them together
        hls.attachMedia(video)
        hls.on(Hls.Events.MEDIA_ATTACHED, () => {
          hls.loadSource(this.options.src)
          hls.on(Hls.Events.MANIFEST_PARSED, function (event, data) {
            console.log(
              'manifest loaded, found ' + data.levels.length + ' quality level'
            )
          })
        })
      }
    },
    initialize() {
        // cross origin
      if (this.options.crossOrigin) {
        this.$refs.video.crossOrigin = this.options.crossOrigin
        this.$refs.video.setAttribute('crossOrigin', this.options.crossOrigin)
      }

      // emit event
      const emitPlayerState = (event, value) => {
        this.$emit(event, this.player)
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
  },

  created() {

  },

  mounted() {
    this.setHls()
    this.initialize()
  }
}
</script>

<style scoped lang="scss">
</style>
