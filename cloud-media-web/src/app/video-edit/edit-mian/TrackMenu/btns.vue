<!--
 * @Author: your name
 * @Date: 2021-08-10 15:44:45
 * @LastEditTime: 2021-09-23 15:25:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/edit-mian/TrackMenu/btns.vue
-->
<template>
  <div class="track-btns-wrap">
    <el-button v-if="!videoMasking" type="primary" size="mini" @click="preview">预览</el-button>
    <!-- <el-button type="primary" size="mini" @click="submit">生成视频</el-button> -->
    <!-- <el-button type="info" size="mini" @click="goback">返回</el-button> -->
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
      isPreviewIng: false
    }
  },
  computed: {
    ...mapState('video', ['cutTrackData', 'currentTimeMs', 'playing']),
    endMs() {
      return this.cutTrackData.endMs
    },
    videoMasking() {
      return this.$route.path.indexOf('video-masking') > -1
    }
  },
  watch: {
    currentTimeMs(ms) {
      if (this.isPreviewIng && ms >= this.endMs) {
        this.$store.commit('video/SET_VIDEO_CURRENTTIME_MS', this.endMs)
        this.$store.commit('video/VIDEO_PAUSE')
        this.isPreviewIng = false
      }
    }
  },
  created() {

  },
  mounted() {

  },
  beforeDestroy() {
  },
  methods: {
    preview() {
      this.isPreviewIng = true
      const { startMs, endMs } = this.cutTrackData
      console.log('🚀 ~ file: btns.vue ~ line 60 ~ preview ~ startMs', startMs)
      this.$store.commit('video/SEEK_CURRENT_TIME_MS', startMs)
      this.$store.commit('video/SET_VIDEO_CURRENTTIME_MS', startMs)
      this.$store.commit('video/VIDEO_PLAY')
    },
    submit() {
      this.$bus.$emit('videoSubmit')
    },
    goback() {
      this.$confirm('此界面没有保存, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.go(-1)
      })
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep .el-message-box {
  position: absolute;
  top: 10px;
}
</style>
