<!--
 * @Author: your name
 * @Date: 2021-08-04 12:12:57
 * @LastEditTime: 2021-08-16 19:58:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/components/SeekLine/index.vue
-->
<template>
  <div
    ref="seekLine"
    class="seek-line"
    :style="`transform: translateX(${translateX - scrollLeft}px);`"
    @click.stop.prevent
    @mousedown.stop="mousedown"
  >
    <div class="handel" />
    <div class="line" />
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  components: {

  },
  props: {
    scrollLeft: {
      // 传入 父组件滚动的
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      translateX: 0,
      seeKing: false,
      translateXTemp: 0,
      isDown: false
    }
  },
  computed: {
    ...mapState('video', ['VIDEO_DURANTION_MS', 'PER_PX_MS', 'currentTimeMs', 'CUT_TRACK_WIDTH', 'playing'])
  },
  watch: {
    currentTimeMs(ms) {
      this.translateX = ms / this.PER_PX_MS
    },
    PER_PX_MS() {
      this.translateX = this.currentTimeMs / this.PER_PX_MS
    },
    translateX(val) {
      this.$store.commit('video/SET_PROGRESS_X', val)
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    mousedown(e) {
      const setTranslateX = (e) => {
        const x = e.touches ? e.touches[0].pageX : e.pageX
        const translateX = Math.max(Math.min(maxX, translateXTemp + (x - mouseX)), minX)
        const currentTimeMs = translateX * this.PER_PX_MS
        this.$store.commit('video/SET_VIDEO_CURRENTTIME_MS', currentTimeMs)
        this.$store.commit('video/SEEK_CURRENT_TIME_MS', currentTimeMs)
      }

      const clearMouseEvent = () => {
        prentNode.style.overflowX = 'auto'
        document.onmousemove = null
        document.onmouseup = null
      }

      const cancelBubble = (e) => {
        if (e.stopPropagation) {
          e.stopPropagation()
        } else {
          e.cancelBubble = true
        }
      }

      cancelBubble(e)

      this.seekMouseDown()

      const prentNode = this.$refs.seekLine.parentNode
      prentNode.style.overflowX = 'hidden'
      prentNode.onmouseleave = (e) => {
        clearMouseEvent()
      }

      this.seeKing = true
      const mouseX = e.touches ? e.touches[0].pageX : e.pageX

      const translateXTemp = this.translateX
      const minX = 0
      const maxX = this.CUT_TRACK_WIDTH

      document.onmousemove = (e) => {
        cancelBubble(e)
        setTranslateX(e)
      }

      document.onmouseup = (e) => {
        cancelBubble(e)
        clearMouseEvent()
        this.seeKing = false
      }
    },
    seekMousemove(currentTimeMs) {
      this.$store.commit('video/SEEK_CURRENT_TIME_MS', currentTimeMs)
    },

    seekMouseDown() {
      this.$store.commit('video/VIDEO_PAUSE')
    }
  }
}
</script>

<style scoped lang="scss">
.seek-line {
  position: absolute;
  width: 0;
  left: -5px;
  right: 0;
  height: 100%;
  z-index: 3;
  cursor: move;
  pointer-events: auto;

  .handel {
    width: 11px;
    height: 12px;
    background-color: #ffbe00;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 3;

    &::after {
      content: "";
      position: absolute;
      display: block;
      top: 12px;
      right: 0;
      width: 0;
      height: 0;
      border-top: 5px solid #ffbe00;
      border-right: 6px solid transparent;
    }

    &::before {
      content: "";
      position: absolute;
      display: block;
      top: 12px;
      left: 0;
      width: 0;
      height: 0;
      border-top: 5px solid #ffbe00;
      border-left: 6px solid transparent;
    }
  }

  .line {
    position: absolute;
    height: 100%;
    width: 0;
    left: 5px;
    top: 0;
    border-left: 1px solid #ffbe00;
  }
}
</style>
