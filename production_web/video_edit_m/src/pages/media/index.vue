<!--
 * @Author: zzx
 * @Date: 2020-09-16 14:25:30
 * @LastEditTime: 2021-07-12 15:05:31
 * @FilePath: /video_edit/src/pages/media/index.vue
-->
<template>
  <div class="medioPad">
    <div class="mediaWrap">
      <div v-if="loading" style="color: white">loading...</div>
      <video-play v-else class="videoPaly" :playerOptions="playerOptions"></video-play>
    </div>
  </div>
</template>

<script>
import videoPlay from './video_play/video_pad'
export default {
  props: {},
  data() {
    return {
      loading: true,
      playerOptions: {
        height: '360',
        autoplay: true,
        muted: false,
        language: 'en',
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        fluid: true,
        src: 'http://video.cloud.tvmining.com/TVM/MP4_MAIN/NeiMengGuTV/2016/11/01/NeiMengGuTV_512000_20161101_21820898_0.mp4',
        // src: '',
        poster: ''
      }
    }
  },
  components: {
    videoPlay
  },
  computed: {},
  watch: {},
  methods: {
    // setVideoOption(event) {
    //   // 父组件传送过来的数据 必传数据 src
    //   if (event.data.cmd == 'setVideoOptions') {
    //     let data = event.data
    //     let options = Object.assign(this.playerOptions, data.data)
    //     this.playerOptions = { ...options }
    //     this.loading = false
    //   }
    // },
    listenMessage(event) {
      // this.loading = true
      // 父组件传送过来的数据 必传数据 src
      if (event.data.cmd == 'setVideoOptions') {
        let data = event.data
        let options = Object.assign(this.playerOptions, data.data)
        this.playerOptions = { ...options }
        this.loading = false
      }
      // 父组件传送过来的数据 必传数据 src
      if (event.data.cmd == 'setVideoDurantion') {
        let data = event.data
        this.$store.commit('setDurantion', data.data * 1000)
      }
      // 父组件传送过来的方法
      if (event.data.cmd == 'getVideoEditAllData') {
        let data = this.$store.state
        window.parent.postMessage({ cmd: 'exportVideoEditAllData', data: data }, '*')
      }
    }
  },
  created() {},
  mounted() {
    // event 参数中有 data 属性，就是父窗口发送过来的数据
    window.addEventListener('message', this.listenMessage, false)
  }
}
</script>

<style scoped lang="scss">
.medioPad {
  background-color: #232323;
  .mediaWrap {
    height: 324px;
    width: 576px;
  }
}
</style>
