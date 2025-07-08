<!--
 * @Author: your name
 * @Date: 2021-04-20 16:58:14
 * @LastEditTime: 2021-04-29 17:18:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-zzx-ui/packages/VueFakeProgress/src/index.vue
-->
<template>
  <div class="fake-progress">
    <div class="bar" role="bar">
      <div class="peg" :style="`width:${value}%;transition: 'all 0 linear'`" />
      <span v-if="showPct" class="percentage">{{ value }}%</span>
    </div>
  </div>
</template>

<script>
import FakeProgress from './fake-progress'
export default {
  name: 'VueFakeProgress',
  components: {},
  props: {
    loadNow: {
      type: Boolean,
      default: true
    },
    showPct: {
      type: Boolean,
      default: true
    },
    trickleSpeed: {
      type: Number,
      default: 2000
    }

  },
  data() {
    return {
      value: 2
    }
  },
  computed: {},
  watch: {

  },
  created() {
    this.start()
  },
  mounted() {

  },
  beforeDestroy() {
    this.done()
  },
  methods: {
    init() {
      FakeProgress.trickleSpeed = this.trickleSpeed
    },
    start() {
      if (!this.loadNow) return
      FakeProgress.set(0)

      FakeProgress.start((status) => {
        this.value = status
      })
    },
    done() {
      FakeProgress.done()
      this.value = FakeProgress.status
      this.$emit('done')
    }
  }
}
</script>

<style lang='scss' scoped>
:root {
  $color: red;
}

.bar {
  width: 100%;
  height: 30px;
  background: #ccc;
  position: relative;
  border-radius: 20px;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;

  .peg {
    position: absolute;
    left: 0;
    height: 100%;
    width: 1px;
    background: orange;
    transition: all .3s linear;
    border-radius: 20px;

  }

  .percentage {
    font-size: 12px;
    z-index: 3;
  }
}
</style>
