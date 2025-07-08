<!--
 * @Author: your name
 * @Date: 2021-09-10 14:40:57
 * @LastEditTime: 2021-10-12 18:16:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/add-videos/index.vue
-->
<template>
  <div class="add-video">
    <Theader name="字幕创作">
      <div slot="btn">
        <el-button type="primary" size="small" :loading="loading" @click="analysis">开始分析</el-button>
      </div>
    </Theader>
    <SelectFile @getBosKey="getBosKey" @changeTit="change" />
    <!-- :loading="loading" -->
    <FileFrom ref="from" @analysis="fromClick" />
    <el-dialog
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      width="30%"
    >
      <span slot="title" class="dialogTit">字幕正在识别中…</span>
      <span class="content">字幕正在识别，详情可在我的作品中查看。</span>
      <span slot="footer" class="dialog-footer">
        <!-- <el-button type="info" size="small" @click="dialogVisible = false">留在本页</el-button> -->
        <el-button type="primary" size="small" @click="goVideoManage">我的作品</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Theader from '@/components/ToolsHeader'
import FileFrom from './components/fileFrom.vue'
import SelectFile from './components/selectFile.vue'

import { PostVideos } from '@/app/jianshi-video/api/videos'
export default {
  components: {
    FileFrom,
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
      this.$refs.from.analysis()
    },
    async fromClick(obj) {
      if (!this.videoData.video_paths) {
        this.$message.warning('请添加视频')
        return
      }
      this.loading = true
      const parmas = Object.assign(obj, this.videoData)
      const { err, res } = await PostVideos(parmas)
      this.loading = false
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.dialogVisible = true
    },
    change(e) {
      console.log(e)
      this.videoData.titles = e
    },
    getBosKey(obj) {
      this.videoData = obj
    },
    goVideoManage() {
      this.$router.push(
        { path: '/manage-center/creative-center',
          query: { tab: '字幕创作' }
        }
      )
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
