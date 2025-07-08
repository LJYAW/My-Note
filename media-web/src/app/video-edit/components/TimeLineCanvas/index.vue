<!--
 * @Author: your name
 * @Date: 2021-07-30 17:18:26
 * @LastEditTime: 2021-08-25 12:29:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/components/TimeLine/index.vue
-->
<template>
  <canvas id="TimeLineCanvas" :width="canvasW" :height="canvasH" />
</template>

<script>
import { range, rangeArr } from './range'

const LING_H = 8 // 段落刻度尺高度
const S_LING_H = 6 // 刻度尺高度
const LING_COLOR = '#979797' // 段落线颜色
const S_LING_COLOR = '#f0f0f0' // 刻度线颜色
const FOND_TOP = 20 // 刻度线颜色

/**
 * @event drawEnd() 组件绘制完成事件
 * @event getRangeArr() 获取range信息
 * **/

export default {
  components: {

  },
  props: {
    canvasW: {
      type: Number,
      default: 1400
    },
    canvasH: {
      type: Number,
      default: 50
    },
    scrollLeft: {
      // 传入 父组件滚动的
      type: Number,
      default: 0
    },
    duration: {
      // 传如总时长
      type: Number,
      default: 10000
    },
    // 传入的 时间轴比列
    range: {
      type: Number,
      default: 3
    }
  },
  data() {
    return {
      BLOCK_TOTAL_MS: 20000,
      BLOCK_WIDTH: 120,
      PER_PX_MS: 0,

      canvas: null,
      ctx: null,

      blockNum: 120,
      drawData: [],
      startIndex: 0,
      endIndex: 120
    }
  },
  computed: {
  },
  watch: {
    scrollLeft() {
      window.requestAnimationFrame(this.moveDraw)
    },
    duration() {
      this.init()
    },
    range() {
      this.init()
    }
  },
  created() {
    this.init()
  },
  mounted() {
  },
  methods: {
    setBaseData() {
      this.BLOCK_TOTAL_MS = range(this.range).BLOCK_TOTAL_MS
      this.BLOCK_WIDTH = range(this.range).BLOCK_WIDTH
      // 计算有多少个方块
      this.PER_PX_MS = this.BLOCK_TOTAL_MS / this.BLOCK_WIDTH
      this.blockNum = Math.max(Math.ceil(this.duration / this.BLOCK_TOTAL_MS) + 10, 15)

      // 根据滚动距离 截取数组的对应数据
      this.startIndex = Math.floor(this.scrollLeft / this.BLOCK_WIDTH)
      this.endIndex = Math.min(Math.floor(this.canvasW / this.BLOCK_WIDTH + this.startIndex), this.blockNum)

      this.drawData = []

      for (let i = 0; i < this.blockNum; i++) {
        const IS_BLOCK = i % 10 === 0
        const beginX = this.BLOCK_WIDTH * i
        const lineH = IS_BLOCK ? LING_H : S_LING_H

        this.drawData.push({
          IS_BLOCK: IS_BLOCK,
          lineH: lineH,
          color: IS_BLOCK ? LING_COLOR : S_LING_COLOR,
          beginX: beginX,
          beginY: this.canvasH,
          endX: beginX,
          endY: this.canvasH - lineH,
          width: 1
        })
      }
    },
    init() {
      this.setBaseData()

      this.$nextTick(() => {
        this.canvas = document.getElementById('TimeLineCanvas')
        this.ctx = this.canvas.getContext('2d')
        const devicePixelRatio = window.devicePixelRatio || 1
        const backingStoreRatio = this.ctx.webkitBackingStorePixelRatio || 1
        const ratio = devicePixelRatio / backingStoreRatio

        this.canvas.width = this.canvasW * ratio
        this.canvas.height = this.canvasH * ratio
        this.canvas.style.width = this.canvasW + 'px'
        this.canvas.style.height = this.canvasH + 'px'
        // 然后将画布缩放，将图像放大两倍画到画布上
        this.ctx.scale(ratio, ratio)

        this.ctx.fillStyle = '#fff'
        this.ctx.fillRect(0, 0, this.canvasW, this.canvasH)

        this.draw(this.startIndex, this.endIndex)
        this.drawLine(0, this.canvasH, this.canvasW, this.canvasH, S_LING_COLOR, 1) // 底线

        this.$emit('drawEnd')
        this.$emit('getRangeArr', rangeArr)
      })
    },
    moveDraw() {
      const start = Math.floor(this.scrollLeft / this.BLOCK_WIDTH)
      const end = Math.floor(this.canvasW / this.BLOCK_WIDTH)
      this.ctx.clearRect(0, 0, this.canvasW, this.canvasH)
      this.draw(start, start + end)
    },
    draw(startIndex = 0, endIndex = 120) {
      const start = Math.max(startIndex - 10, 0)
      const end = Math.min(endIndex + 10, this.drawData.length)

      for (let i = start; i < end; i++) {
        const { IS_BLOCK, beginX, beginY, endX, endY, color, width } = this.drawData[i]
        const beginXTemp = beginX - this.scrollLeft
        const endXTemp = beginXTemp
        this.drawLine(beginXTemp, beginY, endXTemp, endY, color, width)
        IS_BLOCK && this.drawText(this.msToSecond(this.PER_PX_MS * this.BLOCK_WIDTH * i), beginXTemp, endY - FOND_TOP)
      }
    },
    drawLine(beginX, beginY, endX, endY, color, width) {
      this.ctx.beginPath()
      this.ctx.moveTo(beginX + 0.5, beginY) // 加0.5 为了实现 一像素 线的方法
      this.ctx.lineTo(endX + 0.5, endY)
      this.ctx.strokeStyle = color
      this.ctx.lineWidth = width
      this.ctx.stroke()
    },

    drawText(text, x, y) {
      this.ctx.fillStyle = '#404040'
      this.ctx.fillText(text, x, y)
    },

    msToSecond(s) {
      var result = '00:00:00'

      var minute, second, ms

      if (s > 0) {
        second = Math.floor(s / 60000)
        if (second < 10) {
          second = '0' + second
        }

        minute = Math.floor(s / 1000 - second * 60)
        if (minute < 10) {
          minute = '0' + minute
        }

        ms = (
          Math.floor(parseFloat(s) - second * 60000 - minute * 1000) / 10
        ).toFixed()
        if (ms < 10) {
          ms = '0' + ms
        }
        result = second + ':' + minute + '.' + ms
      }

      return result
    }
  }

}
</script>

<style scoped lang="scss">

</style>
