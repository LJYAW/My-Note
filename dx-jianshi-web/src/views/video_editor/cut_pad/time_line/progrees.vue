<template>
  <div>
    <div class="progrees"
         @mousedown="progreesMove($event)"
         id="timelineProgrees"
         :style="'transform: translateX(' + progrees_x + 'px)'">
    </div>
  </div>
</template>

<script>
import { getMouseXY, translation } from '@/utils/mouse.js'

export default {
  props: [],
  data() {
    return {
      progrees_x: 0,
      timer: null,
      setCurrentTime: 0
    }
  },
  components: {},

  computed: {
    scrollerDom() {
      return document.querySelector('.vue-recycle-scroller')
    },
    video_paused() {
      return this.$store.state.videoM.video_paused
    },
    per_px_ms() {
      return this.$store.state.videoM.per_px_ms
    },
    one_ms_px() {
      return 1000 / this.per_px_ms
    },
    start_ms() {
      return this.$store.state.videoM.cut_track_list[0].start_time
    },
    currentTime() {
      return this.$store.state.videoM.currentTime
    }
  },

  watch: {
    currentTime(val) {
      this.progrees_x = val / this.per_px_ms
      this.$store.commit('setProgreesX', this.progrees_x)
    }
  },

  methods: {
    pregressTranslation() {
      var x = this.scrollerDom.getBoundingClientRect().x - 60 - document.body.getBoundingClientRect().x
      this.progrees_x = getMouseXY().x - x - 60
      this.$store.commit('setProgreesX', this.progrees_x)
      this.$store.commit('setCurrentTimeForProgress', Math.floor(this.progrees_x * this.per_px_ms))
    },
    progreesMove() {
      document.onmousemove = e => {
        if (e.stopPropagation) {
          e.stopPropagation()
        } else {
          e.cancelBubble = true
        }

        var x = this.scrollerDom.getBoundingClientRect().x - 60 - document.body.getBoundingClientRect().x

        this.progrees_x = Math.max(0, getMouseXY().x - x - 60)
        this.$store.commit('setProgreesX', this.progrees_x)
        this.$store.commit('setCurrentTimeForProgress', Math.floor(this.progrees_x * this.per_px_ms))
      }
      document.onmouseup = function(e) {
        if (e.stopPropagation) {
          e.stopPropagation()
        } else {
          e.cancelBubble = true
        }
        document.onmousemove = null
      }
    }
  },

  created() {
    this.progrees_x = this.start_ms * this.one_ms_px
  },

  mounted() {
    this.timelineProgrees = document.getElementById('timelineProgrees')
  }
}
</script>

<style lang="scss">
</style>
