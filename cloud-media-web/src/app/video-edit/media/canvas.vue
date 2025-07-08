<!--
 * @Author: your name
 * @Date: 2021-07-27 16:28:35
 * @LastEditTime: 2021-09-27 12:10:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/media/video-canvas.vue
-->
<template>
  <!-- <div class="video-canvas-wrap" :style="`width:${canvasW}px;height:${canvasH}px`"> -->
  <div class="canvas-parent" :style="`width:${parentW}px;height:${parentH}px`">
    <canvas :id="canvaID" />

    <Decorate />
  </div>

  <!-- </div> -->
</template>

<script>
import { mapState } from 'vuex'
import getContainPosition from '../utils/get-contain-position'
import { gaussBlur, mosaic } from '../utils/canvas-mosaic'
import Decorate from './decorate.vue'

export default {
  name: 'VideoCanvas',
  components: {
    Decorate
  },
  props: {

  },
  data() {
    return {
      canvas: null,
      drawAnimation: null,

      videoHeight: 0,
      videoWidth: 0,

      parentW: 0,
      parentH: 0,

      canvasPosition: {
        dx: 0,
        dy: 0,
        dw: 0,
        dh: 0
      },

      mosaicPosition: []
    }
  },
  computed: {
    ...mapState('video', ['videoStatus', 'progressX', 'videoPlay', 'videoSize', 'videoEvent', 'playing', 'canvasOptions', 'decorateListTemp']),
    canvaID() {
      return this.canvasOptions.canvaID
    },
    canvasW() {
      return this.canvasOptions.canvasW
    },
    canvasH() {
      return this.canvasOptions.canvasH
    }
  },
  watch: {
    videoStatus: {
      handler(status) {
        this.watchVideoStatus(status)
      },
      deep: true
    },
    // decorateListTemp: {
    //   handler() {
    //     if (!this.playing) {
    //       this.decorateListTemp.forEach(item => {
    //         const { x, y, w, h } = item
    //         const { dx, dy, dw, dh } = this.canvasPosition
    //         this.clearCanvas()
    //         this.context.drawImage(this.videoPlay, dx, dy, dw, dh)
    //         gaussBlur(this.context, x, y, w, h)
    //       })
    //     }
    //   },
    //   deep: true
    // },
    canvasOptions: {
      handler(val) {
        const { width, height } = this.setCanvasSize()
        this.parentW = width
        this.parentH = height
      },
      immediate: true,
      deep: true
    }
  },
  created() {

  },
  mounted() {
    this.init()

    this.$bus.$on('onDragStopChangePosition', () => {
      this.startDraw()
    })
  },
  methods: {
    init() {
      this.canvas = document.getElementById(this.canvaID)
      const { width, height } = this.setCanvasSize()
      this.canvas.width = width
      this.canvas.height = height
      this.context = this.canvas.getContext('2d')
      this.context.fillStyle = '#000'
      this.context.fillRect(0, 0, width, height)
    },
    startDraw() {
      this.clearCanvas()
      const { width, height } = this.setCanvasSize()
      this.context.drawImage(this.videoPlay, 0, 0, width, height)
      this.deawGaussBlur()

      if (this.playing) {
        this.drawAnimation = window.requestAnimationFrame(this.startDraw)
      } else {
        this.stopDraw()
      }
    },
    stopDraw() {
      window.cancelAnimationFrame(this.drawAnimation)
    },
    deawGaussBlur() {
      this.decorateListTemp.forEach(item => {
        if (item.type === 'gaussBlur') {
          const { x, y, w, h } = item
          gaussBlur(this.context, x, y, w, h)
        }
        if (item.type === 'mosaic') {
          const { x, y, w, h } = item
          mosaic(this.context, x, y, w, h)
        }
      })
    },
    clearCanvas() {
      const { canvasW, canvasH } = this.canvasOptions
      this.context.clearRect(0, 0, canvasW, canvasH)
    },
    drawPoster() {
      const poster = this.videoPlay.poster

      const img = new Image()
      img.crossOrigin = 'Anonymous'
      img.src = poster

      img.onload = () => {
        this.context.drawImage(this.videoPlay, 0, 0, this.videoSize.w, this.videoSize.h)
        // this.context.save()
      }
    },
    setCanvasSize() {
      const { w, h } = this.videoSize
      const { canvasW, canvasH } = this.canvasOptions

      const rImg = w / h
      const rCanvas = canvasW / canvasH
      let width, height
      if (rImg <= rCanvas) {
        width = canvasH * rImg
        height = canvasH
      } else {
        width = canvasW
        height = canvasW / rImg
      }
      return {
        width,
        height
      }
    },
    watchVideoStatus(status) {
      const event = {
        loadeddata: () => {
        },
        playing: () => {
          this.startDraw()
        },
        canplay: () => {
          this.startDraw()
        }
      }
      event[status] && event[status]()
    }
  }
}
</script>

<style scoped lang="scss">
.canvas-parent {
  position: relative;

  .decorate-dragegable-wrap {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
}
</style>
