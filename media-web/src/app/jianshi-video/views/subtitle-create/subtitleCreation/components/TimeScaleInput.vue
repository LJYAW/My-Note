<!--
 * @Author: your name
 * @Date: 2021-09-10 16:22:20
 * @LastEditTime: 2021-09-29 12:13:29
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/subtitleCreation/components/TimeScale.vue
-->
<template>
  <div class="time-scale-input">
    <div class="times-counter-box">
      <i class="el-icon-caret-left icon-caret" @click="handel(true)" />
      <el-input v-model="times" size="mini" />
      <i class="el-icon-caret-right icon-caret" @click="handel(false)" />
    </div>
  </div>
</template>

<script>
import msToSecond from '../js/ms-to-second'

export default {
  name: 'TimeScaleInput',
  components: {

  },
  props: {
    curMs: {
      type: Number,
      default: 1000
    },
    maxMs: {
      type: Number,
      default: 1000
    },
    minMs: {
      type: Number,
      default: 0
    },
    seekMs: {
      type: Number,
      default: 100
    }
  },
  data() {
    return {
      ms: 0,
      times: ''
    }
  },
  computed: {
  },
  watch: {
    ms: {
      handler(newName, oldName) {
        this.times = msToSecond(newName)
      },
      immediate: true
    }
  },
  created() {
    this.ms = this.curMs
  },
  mounted() {

  },
  methods: {
    handel(status) {
      status && (this.ms = Math.max(this.ms - this.seekMs, this.minMs))
      !status && (this.ms = Math.min(this.ms + this.seekMs, this.maxMs))
      this.$emit('timeHandel', this.ms)
    }
  }
}
</script>

<style scoped lang="scss">
.time-scale-input {

  .times-counter-box {
    display: flex;
    align-items: center;

    .icon-caret {
      cursor: pointer;
    }

    ::v-deep .el-input__inner {
      width: 86px;
      height: 24px;
    }
  }
}
</style>
