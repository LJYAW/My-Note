<!--
 * @Author: zzx
 * @Date: 2020-08-03 13:58:32
 * @LastEditTime: 2021-04-12 22:26:57
 * @FilePath: /video_edit/src/components/time_line/src/index.vue
-->
<template>
  <div class="time-line" :style="{'width': time_line_width + 'px'}">
    <div
      v-for="(num,index) in block_num"
      :key="index"
      class="time-block"
      :style="{'width': BLOCK_WIDTH + 'px'}"
    >
      <span class="mm">{{ msToSecond(BLOCK_TOTAL_MS*index) }}</span>

      <div class="scales">
        <!-- <div v-for="j in 10" :key="j" class="sub" /> -->
        <div class="sub" />
      </div>
    </div>

    <slot />
  </div>
</template>

<script>
import rangeChange from './range.js'
import { mixinMethods } from './mixin'

export default {
  components: {},

  mixins: [mixinMethods],
  props: {
    // 传入的总毫秒数
    duration: {
      type: Number,
      default: 3600000
    },
    onParent: {
      type: Boolean,
      default: false
    },
    // 传入的 时间轴比列
    range: {
      type: Number,
      default: 4
    }
  },

  data() {
    return {
      time_line_width: 1000,
      BLOCK_TOTAL_MS: 20000,
      BLOCK_WIDTH: 120,
      block_num: 24,
      PER_PX_MS: 0
    }
  },
  computed: {},
  watch: {
    range() {
      this._initTimeLine()
    },
    duration() {
      this._initTimeLine()
    }
  },
  created() {
    this._initTimeLine()
  },
  mounted() {
    this.setTimeWidth()
  },
  methods: {
    getParentWidth() {
      var dom = document.querySelectorAll('.time-line')[0].parentNode
      return dom.offsetWidth
    },
    setTimeWidth() {
      if (this.onParent) {
        this.time_line_width = this.getParentWidth()
        this.BLOCK_WIDTH = this.time_line_width / this.block_num
        this.PER_PX_MS = this.BLOCK_TOTAL_MS / this.BLOCK_WIDTH
      }
    },
    _initTimeLine() {
      this.BLOCK_TOTAL_MS = rangeChange(this.range).BLOCK_TOTAL_MS
      this.BLOCK_WIDTH = rangeChange(this.range).BLOCK_WIDTH
      // 计算有多少个方块
      this.block_num = Math.ceil(this.duration / this.BLOCK_TOTAL_MS) + 1
      this.time_line_width = this.block_num * this.BLOCK_WIDTH
      this.PER_PX_MS = this.BLOCK_TOTAL_MS / this.BLOCK_WIDTH
    }
  }
}
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
