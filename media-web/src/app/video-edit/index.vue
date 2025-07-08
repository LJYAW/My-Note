<!--
 * @Author: your name
 * @Date: 2021-07-27 11:49:45
 * @LastEditTime: 2021-09-13 10:51:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/index.vue
-->
<template>
  <div class="video-edit-wrap">

    <div class="main">
      <div class="media-wrap" :style="mediaHieght">
        <div class="left-pad">

          <Media />

        </div>

        <div class="right-pad">
          <slot name="rightPad" />
        </div>

      </div>

      <div class="track-wrap">

        <EditMain />

      </div>
    </div>
  </div>
</template>

<script>
import Media from './media/index.vue'
import EditMain from './edit-mian/index.vue'
import vuex from 'vuex'
import myvuex from './store/index.js'
const { mapMutations, mapState } = vuex.createNamespacedHelpers('video')
import './utils/filter.js'
import './utils/bus'

export default {
  name: 'VideoEdit',
  components: {
    Media,
    EditMain
  },
  props: {

  },
  data() {
    return {

    }
  },
  computed: {
    ...mapState(['VIDEO_DURANTION_MS', 'canvasOptions']),
    videoIsReady() {
      return this.VIDEO_DURANTION_MS > 0
    },
    mediaHieght() {
      return {
        height: this.canvasOptions.canvasH + 50 + 'px'
      }
    }
  },
  watch: {

  },
  beforeCreate() {
    this.$store.registerModule('video', myvuex)
  },
  created() {
  },
  destroyed() {
    // resetState
    this.$store.commit('video/resetState')
    this.$store.unregisterModule('video')
  },
  mounted() {
    // this.$store.commit('video/SET_CANVAS_SIZE')
    // window.onresize = () => {
    //   this.$store.commit('video/SET_CANVAS_SIZE')
    // }
  },
  methods: {

  }
}
</script>

<style lang="scss">
.video-edit-wrap {
  width: 1480px;
  height: 100%;
  margin: 0 auto;
  background: #f7f8f9;
  padding: 20px;

  * {
    box-sizing: border-box;
  }

  .main {
    height: 100%;
    display: flex;
    flex-direction: column;

    .media-wrap {
      display: flex;

      .left-pad {
        background-color: #1a1b1d;
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 4px;
        overflow: hidden;

        .media-content {

          .video-control {
            height: 50px;
          }
        }
      }

      .right-pad {
        overflow: hidden;
        flex: 1;
        margin-left: 24px;
        background-color: #fff;
        border-radius: 4px;
      }
    }

    .track-wrap {
      flex: 1;
      overflow: hidden;
      margin-top: 20px;
      background-color: #fff;
      border-radius: 4px;
      display: flex;
      padding: 20px;
      min-height: 220px;
    }
  }
}

</style>
