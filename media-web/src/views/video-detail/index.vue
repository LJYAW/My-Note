<!--
 * @Author: your name
 * @Date: 2021-07-29 10:45:27
 * @LastEditTime: 2021-08-24 14:50:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/videoDetail/index.vue
-->
<template>
  <div v-loading="loading" class="video-wrap container">
    <VideoDetail :video-data="videoData" @getDetail="getDetail()" />
    <ToolPad :video-data="videoData" />
  </div>
</template>

<script>
import VideoDetail from './components/videoDetail.vue'
import ToolPad from './components/toolPad.vue'

export default {
  components: {
    VideoDetail,
    ToolPad
  },
  props: {

  },
  data() {
    return {
      uuid: 0,
      videoData: {},
      loading: true
    }
  },
  computed: {

  },
  watch: {

  },
  destroyed() {
    sessionStorage.removeItem('videoData')
  },

  created() {
    this.uuid = this.$route.query.uuid
    this.getDetail()
  },
  mounted() {

  },
  methods: {
    async getDetail() {
      const { err, res } = await this.$get(`/videos/${this.uuid}`, {}, {
        DirId: this.$route.query.DirId
      })
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.loading = false
      this.videoData = res.data
      this.$store.commit('videoStatus/VIDEO_DATA_CHANGE', res.data)
    }
  }
}
</script>

<style scoped lang="scss">
.video-wrap {
  display: flex;
  padding-top: 15px;
}

</style>
