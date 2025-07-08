<!--
 * @Author: your name
 * @Date: 2021-08-23 18:12:08
 * @LastEditTime: 2021-08-25 12:27:19
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/components/Scrollthumb/index.vue
-->
<template>
  <div ref="scroller" class="scroller-wrap" @click.stop.prevent>
    <div ref="scroller" class="scroller">
      <div v-if="showBar" class="scroll-thumb" :style="style" @mousedown="mousedown($event)" />
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    parentWidth: {
      type: Number,
      default: 1000
    },
    scrollLeft: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      rete: 1,
      translateX: 0,
      barWidth: 100,
      showBar: false,
      clientWidth: 0,
      mouseDownBar: false
    }
  },
  computed: {
    style() {
      return {
        transform: `translateX(${this.translateX}px)`,
        width: `${this.barWidth}px`
      }
    }
  },
  watch: {
    parentWidth() {
      this.init()
    },
    scrollLeft(x) {
      // 滚动条的顶部距离=滑动条的高度/滑道高度*实际内容区域顶部距离
      !this.mouseDownBar && (this.translateX = this.rate * x)
    }
  },
  created() {

  },
  mounted() {
    // this.init()
  },
  methods: {
    init() {
      this.clientWidth = this.$refs.scroller.clientWidth
      const parentWidth = Math.max(this.parentWidth, 1)
      this.rate = this.clientWidth / parentWidth
      this.barWidth = Math.max(this.rate * this.clientWidth, 10)
      this.rate < 1 && (this.showBar = true)
    },
    mousedown(e) {
      const mouseX = e.touches ? e.touches[0].pageX : e.pageX
      const translateXTemp = this.translateX
      const minX = 0
      const maxX = this.clientWidth - this.barWidth
      this.mouseDownBar = true

      const setTranslateX = (e) => {
        const x = e.touches ? e.touches[0].pageX : e.pageX
        const translateX = Math.max(Math.min(maxX, translateXTemp + (x - mouseX)), minX)
        this.translateX = Math.floor(translateX)
        this.$emit('onscroll', this.translateX, this.clientWidth)
      }

      const clearMouseEvent = () => {
        this.mouseDownBar = false
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

      document.onmousemove = (e) => {
        cancelBubble(e)
        setTranslateX(e)
      }

      document.onmouseup = (e) => {
        cancelBubble(e)
        clearMouseEvent()
      }
    }
  }
}
</script>

<style scoped lang="scss">
.scroller-wrap {
  height: 24px;
  width: 100%;
  display: flex;
  align-items: center;

  &:hover {

    .scroller {
      height: 8px;
    }
  }

  .scroller {
    background-color: #f3f3f3;
    height: 4px;
    flex: 1;
    transition: all .25s;

    .scroll-thumb {
      height: 100%;
      background-color: #5675e8;
      border-radius: 10px;
      cursor: pointer;
    }
  }
}

</style>
