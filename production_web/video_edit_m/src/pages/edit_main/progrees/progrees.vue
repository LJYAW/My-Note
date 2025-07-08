<!--
 * @Author: zzx
 * @Date: 2020-08-14 14:44:20
 * @LastEditTime: 2021-07-09 17:58:49
 * @FilePath: /video_edit/src/pages/edit_main/progrees/progrees.vue
-->
<template>
  <div
    class="progrees"
    @mousedown="progreesMove($event)"
    id="timelineProgrees"
    :style="'transform: translateX(' + progrees_x + 'px)'"
  ></div>
</template>

<script>
import keyMap from '../../../utils/key-map.js'

export default {
  props: {},
  data() {
    return {
      progrees_x: 0
    }
  },
  computed: {
    PER_PX_MS() {
      return this.$store.state.PER_PX_MS
    },
    current_time_ms() {
      return this.$store.state.videoPlay.current_time_ms
    }
  },
  watch: {
    current_time_ms(val) {
      this.progrees_x = val / this.PER_PX_MS
      this.$store.commit('setProgreesX', this.progrees_x)
      // this.$store.commit('setdecorateActiveId', '')
    }
  },
  methods: {
    progreesMove() {
      // 暂时不写 移动功能
      // document.onmousemove = e => {
      //   if (e.stopPropagation) {
      //     e.stopPropagation()
      //   } else {
      //     e.cancelBubble = true
      //   }
      // }
      // document.onmouseup = e => {
      //   if (e.stopPropagation) {
      //     e.stopPropagation()
      //   } else {
      //     e.cancelBubble = true
      //   }
      //   document.onmousemove = null
      // }
    }
  },
  components: {},
  created() {

  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.progrees {
  position: absolute;
  width: 1px;
  height: 100%;
  background-color: #c51a1a;
  top: 20px;
  z-index: 100;
  cursor: move;

  &::before {
    display: block;
    content: " ";
    position: absolute;
    right: -4px;
    top: -4px;
    width: 0;
    height: 0;
    border-width: 5px;
    border-style: solid;
    border-color: #c51a1a transparent transparent transparent;
  }

  &::after {
    display: block;
    content: " ";
    position: absolute;
    right: -4px;
    top: -12px;
    width: 10px;
    height: 8px;
    background-color: #c51a1a;
  }
}
</style>
