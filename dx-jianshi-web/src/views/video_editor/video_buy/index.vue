<template>
  <div class="video-editor-buy-wrap d-flex flex-wrap h-100">

    <div class="video-buy-header"></div>

    <div class="d-flex container h-100 justify-content-center">
      <div class="video-cut-progress"
           style="width: 570px;height: 360px">

        <video v-if="video_cut_done"
               style="width: 570px;height: 360px"
               controls
               autoplay
               :src="video_url"></video>

        <progressBar v-else
                     @changVideoUrl="changVideoUrl"
                     :done="done"
                     :progress="parseInt(progress)" />
      </div>

      <videoBuy />
    </div>

  </div>
</template>

<script>
import videoBuy from './buy.vue'
import progressBar from '../compents/progress/index.vue'

export default {
  props: [],
  data() {
    return {
      ajax_timer: null,
      video_url: '',
      video_cut_done: false,
      done: 0,
      progress: 0
    }
  },
  components: {
    videoBuy,
    progressBar
  },

  computed: {},

  watch: {},

  methods: {
    changVideoUrl() {}
  },

  created() {},
  mounted() {
    var self = this
    this.ajax_timer = setInterval(() => {
      self.getVideoCutStatus()
    }, 1000)
  },
  beforeDestroy() {
    // clearInterval(this.timer)
    // this.timer = null
  }
}
</script>

<style scoped lang="scss">
.video-editor-buy-wrap {
  .video-cut-progress {
    background: #000;
  }
  .video-buy-header {
    width: 100%;
    height: 50px;
    padding-top: 15px;
    padding-bottom: 15px;
    background: #333;
  }
}
</style>
