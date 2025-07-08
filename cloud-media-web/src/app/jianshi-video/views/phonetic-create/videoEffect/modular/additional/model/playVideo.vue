<!--
 * @Author: your name
 * @Date: 2021-08-02 16:41:15
 * @LastEditTime: 2021-09-17 10:46:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/additional/model/playVideo.vue
-->
<template>
  <div class="playVidep">
    <video-player
      ref="videoPlayer"
      :options="getOption()"
      class="videoPlayerBox"
    />
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    url: {
      type: String,
      default: ''
    }
  },
  data() {
    return {

    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    getOption() {
      return {
        autoplay: false,
        fluid: true, // 自适应
        muted: false,
        language: 'en',
        // playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [{
          type: 'video/mp4',
          src: this.url
        }],
        poster: ''
      }
    }
  }
}
</script>

<style scoped lang="scss">
.playVidep {
  height: 500px;

  .videoPlayerBox {
    width: 100%;
    height: 100%;

    ::v-deep.video-js {
      height: 100% !important;
    }
  }
}
</style>
