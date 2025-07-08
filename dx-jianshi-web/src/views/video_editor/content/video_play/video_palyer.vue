<!--
 * @Author: zzx
 * @Date: 2020-07-22 20:26:53
 * @LastEditTime: 2020-07-28 17:38:19
 * @FilePath: /zhijian_web/src/views/video_editor/content/video_play/video_palyer.vue
-->
<template>
  <div class="video-wrap">
    <video id="myVideo"
           :autoplay="options.autoplay"
           :muted="options.muted"
           controlslist="nodownload"
           preload="auto"
           controls
           :src="options.src"></video>
  </div>
</template>

<script>
const DEFAULT_EVENTS = ['loadeddata', 'canplay', 'canplaythrough', 'play', 'pause', 'waiting', 'playing', 'ended', 'error', 'timeupdate', 'seeked']

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
    initialize() {
      // emit event
      const emitPlayerState = (event, value) => {
        this.$emit(event, this.player)
      }

      this.player = document.getElementById('myVideo')
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

  created() {},

  mounted() {
    this.initialize()
  }
}
</script>

<style scoped lang="scss">
</style>
