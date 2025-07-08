<!--
 * @Author: your name
 * @Date: 2021-08-06 16:16:27
 * @LastEditTime: 2021-08-12 16:01:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/edit-mian/TrackBar/rang.vue
-->
<template>
  <div class="rang-change-wrap">
    <svg-icon icon-class="minus" @click="changeRange('minus')" />
    <div class="progress-wrap">
      <span class="percentage" :style="`width: ${width}%`" />
    </div>
    <svg-icon icon-class="plus" @click="changeRange('plus')" />
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      customColors: '#5675E8',
      percentage: 4
    }
  },
  computed: {
    ...mapState('video', ['maxRange', 'range']),

    width() {
      return Math.floor(this.percentage / this.maxRange * 100)
    }
  },
  watch: {

  },
  created() {
    this.percentage = this.range
  },
  mounted() {

  },
  methods: {
    changeRange(type) {
      if (type === 'minus') {
        this.percentage = Math.max(this.percentage - 1, 0)
      }
      if (type === 'plus') {
        this.percentage = Math.min(this.percentage + 1, this.maxRange)
      }

      this.$store.commit('video/SET_RANGE', this.percentage)
    }
  }
}
</script>

<style scoped lang="scss">
.rang-change-wrap {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #979797;

  .svg-icon {
    cursor: pointer;
  }

  :hover {
    color: #5675e8;
  }

  .progress-wrap {
    width: 80px;
    height: 4px;
    background: #f3f3f3;
    border-radius: 2px;
    position: relative;
    margin: 0 14px;

    .percentage {
      position: absolute;
      top: 0;
      left: 0;
      height: 100%;
      background-color: #5675e8;
      border-radius: 2px;
    }
  }

}
</style>
