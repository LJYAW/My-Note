<!--
 * @Author: your name
 * @Date: 2021-09-10 14:40:57
 * @LastEditTime: 2021-10-08 17:20:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/add-videos/index.vue
-->
<template>
  <div class="add-video">
    <Theader name="语音创作">
      <div slot="btn">
        <el-button type="primary" size="small" :loading="loading" @click="analysis">开始分析</el-button>
      </div>
    </Theader>
    <SelectFile @getBosKey="getBosKey" @changeTit="change" />
  </div>
</template>

<script>
import Theader from '@/components/ToolsHeader'
import SelectFile from './components/selectFile.vue'

import { PostAudio } from '@/app/jianshi-video/api/audio-creation'
export default {
  components: {
    SelectFile,
    Theader
  },
  props: {

  },
  data() {
    return {
      dialogVisible: false,
      audioData: {},
      loading: false,
      taskId: 0
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
    async analysis() {
      if (!this.audioData.file) {
        this.$message.warning('请选择文件')
        return
      }
      await this.uploadAduio(this.audioData.file)
      this.$router.push({
        path: '/jianshi-video/phonetic-create',
        query: {
          taskId: this.taskId,
          title: this.audioData.titles,
          audioFileKey: this.audioData.audio_key,
          duration: this.audioData.audio_duration
        }
      })
    },

    // 上传数据
    async uploadAduio(file) {
      this.loading = true
      var formData = new FormData()
      formData.append('audio', file)
      const { err, res } = await PostAudio(formData)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.loading = false
      this.taskId = res.data.taskId
    },
    change(e) {
      console.log(e)
      this.audioData.titles = e
    },
    getBosKey(obj) {
      this.audioData = obj
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
