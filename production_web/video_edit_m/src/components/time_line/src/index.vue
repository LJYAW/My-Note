<!--
 * @Author: zzx
 * @Date: 2020-08-03 13:58:32
 * @LastEditTime: 2021-05-08 17:55:00
 * @FilePath: /video_edit/src/components/time_line/src/index.vue
-->
<template>
  <!-- <div class="time-line" :style="{'width': time_line_width + 'px'}"> -->
  <div class="time-line" :style="{'width': time_line_width + 'px'}">
    <div class="time-block"
      v-for="(style) in domList"
      :key="style.id"
      :style="style">
      <span class="mm">{{ msToSecond(BLOCK_TOTAL_MS*style.id) }}</span>
       <div class="scales">
        <div class="sub" v-for="j in 10" :key="j"></div>
      </div>
    </div>
  </div>
</template>

<script>
import rangeChange from './range.js'
import { mixinMethods } from './mixin'

export default {
  props: {
    // 传入的总毫秒数
    duration: {
      type: Number,
      default: 3600000
    },
    // 传入的 时间轴比列
    range: {
      type: Number,
      default: 4
    },
    parentDomClass: {
      type: String,
      default: ''
    }
  },

  mixins: [mixinMethods],

  data() {
    return {
      time_line_width: 1000,
      BLOCK_TOTAL_MS: 20000,
      BLOCK_WIDTH: 120,
      block_num: 24,
      parentDom: null
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
  methods: {
    _initTimeLine() {
      this.BLOCK_TOTAL_MS = rangeChange(this.range).BLOCK_TOTAL_MS
      this.BLOCK_WIDTH = rangeChange(this.range).BLOCK_WIDTH
      // 计算有多少个方块
      this.block_num = Math.max(Math.ceil(this.duration / this.BLOCK_TOTAL_MS) + 1, 15)
      this.time_line_width = this.block_num * this.BLOCK_WIDTH
      this.setList()
    }
  },
  components: {},
  created() {
    this._initTimeLine()
  },
  mounted() {
    this.listenerScroll()
  }
}
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
