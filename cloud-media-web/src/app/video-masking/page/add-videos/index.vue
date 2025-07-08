<!--
 * @Author: your name
 * @Date: 2021-09-10 14:40:57
 * @LastEditTime: 2021-10-12 14:42:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/add-videos/index.vue
-->
<template>
  <div class="add-video">
    <Theader name="视频水印">
      <div slot="btn">
        <el-button type="primary" size="small" :loading="loading" @click="analysis">开始编辑</el-button>
      </div>
    </Theader>
    <SelectFile @getBosKey="getBosKey" @changeTit="change" />
  </div>
</template>

<script>
import Theader from '@/components/ToolsHeader'
// import FileFrom from './components/fileFrom.vue'
import SelectFile from './components/selectFile.vue'

import { PostVideos } from '@/app/jianshi-video/api/videos'
export default {
  components: {
    // FileFrom,
    SelectFile,
    Theader
  },
  props: {

  },
  data() {
    return {
      dialogVisible: false,
      videoData: {},
      loading: false
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
    analysis() {
      if (!this.videoData.video_url) {
        this.$message.warning('请添加视频')
        return
      }
      sessionStorage.setItem('videoMarkDetails', JSON.stringify(this.videoData))
      this.$router.push('/video-masking/video-edit-view')
    },
    change(e) {
      this.videoData.titles = e
    },
    getBosKey(obj) {
      this.videoData = obj
    }
  }
}
</script>

<style scoped lang="scss">
.add-video {
  flex: 1;
  padding: 20px 10%;
  height: 100%;
  display: flex;

  ::v-deep.el-dialog {

    .dialogTit {
      font-size: 18px;
      font-weight: 600;
      color: #5675e8;
    }

    .content {
      font-size: 14px;
      font-weight: 400;
      color: #000;
    }
  }
}
</style>
