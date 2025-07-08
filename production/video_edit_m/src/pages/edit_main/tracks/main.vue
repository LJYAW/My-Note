<!--
 * @Author: zzx
 * @Date: 2020-09-17 16:49:23
 * @LastEditTime: 2021-07-06 15:06:01
 * @FilePath: /video_edit/src/pages/edit_main/tracks/main.vue
-->
<template>
  <div class="tracks-wrap" :style="style">
    <cut-track></cut-track>
    <!-- <decorate-track></decorate-track> -->
  </div>
</template>

<script>
import cutTrack from './cut_track/index'
import decorateTrack from './decorate-track/decorate_group'

export default {
  name: 'tracks',
  props: {},
  provide() {
    return {
      track: this
    }
  },
  data() {
    return {}
  },
  computed: {
    videoDuration() {
      return this.$store.state.videoPlay.duration
    },
    PER_PX_MS() {
      return this.$store.state.PER_PX_MS
    },
    style() {
      return {
        width: this.videoDuration / this.PER_PX_MS + 'px'
      }
    }
  },
  watch: {
    style: {
      handler() {
        // 重新计算 drageable的parentWidth
        // this.$bus.$emit('checkParentSize')
      },
      deep: true
    }
  },
  methods: {},
  components: {
    cutTrack,
    decorateTrack
  },
  created() {},
  mounted() {
    window.parent.postMessage({ cmd: 'playerLoadeddata' }, '*')
  }
}
</script>

<style lang="scss">
.tracks-wrap {
  .draggable-box {
    background-color: transparent;
    border: 1px solid rgba($color: #b62f26, $alpha: 0.3);
    // background-color: turquoise;
    border-radius: 5px;
    transition: background-color 100ms linear;
    box-sizing: border-box;
    &.is_avtive {
      border: 1px solid #b62f26;
    }
  }

  .my-dragging-class {
    // background-color: rgba($color: #b62f26, $alpha: 0.2);
    border: 1px solid #b62f26;
  }

  .my-handle-class {
    position: absolute;
    background-color: transparent;
    height: 100%;
    width: 10px;
    border-radius: 50px;
    transition: all 300ms linear;
  }

  .my-handle-class-ml {
    top: 0;
    left: -5px;
    cursor: w-resize;
  }

  .my-handle-class-mr {
    top: 0;
    right: -5px;
    cursor: e-resize;
  }
}
</style>
