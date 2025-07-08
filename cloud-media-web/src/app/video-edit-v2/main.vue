<!--
 * @Author: your name
 * @Date: 2021-08-26 10:21:44
 * @LastEditTime: 2021-08-31 17:50:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/main.vue
-->
<template>
  <div class="video-edit-wrap">
    <div class="main">
      <div class="media-content-wrap" :style="mediaStyle">

        <div class="left">
          <MediaPad />
        </div>

        <div class="right">
          <slot name="rightPad" />
        </div>
      </div>
      <div class="track-content-wrap">
        <TracksPad />
      </div>
    </div>
  </div>
</template>

<script>

import MediaPad from './media-pad/index.vue'
import TracksPad from './tracks-pad/index.vue'
import myvuex from './store/index.js'
import { mapState } from 'vuex'

export default {
  components: {
    MediaPad,
    TracksPad
  },
  props: {

  },
  data() {
    return {

    }
  },
  computed: {
    ...mapState('video', ['canvasOptions']),
    mediaStyle() {
      return {
        height: this.canvasOptions.canvasH + 45 + 'px',
        width: '100%'
      }
    }
  },
  watch: {

  },
  beforeCreate() {
    this.$store.registerModule('video', myvuex)
  },
  destroyed() {
    this.$store.commit('video/resetState')
    this.$store.unregisterModule('video')
  },
  created() {
    this.$store.commit('video/SET_CANVAS_SIZE')
  },
  mounted() {
    window.onresize = () => {
      this.$store.commit('video/SET_CANVAS_SIZE')
    }
  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.video-edit-wrap {
  width: 1440px;
  height: calc(100vh - 90px);
  margin: 0 auto;
  overflow: hidden;

  .main {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    margin-top: 20px;

    .media-content-wrap {
      width: 100%;
      display: flex;

      .left {
        border-radius: 4px;
        overflow: hidden;
      }

      .right {
        flex: 1;
        align-items: stretch;
        background-color: #fff;
        margin-left: 20px;
        border-radius: 4px;
        overflow: hidden;
        overflow-y: auto;
      }

    }

    .track-content-wrap {
      flex: 1;
      margin-top: 20px;
      background-color: #fff;
      border-radius: 4px;
    }
  }
}
</style>
