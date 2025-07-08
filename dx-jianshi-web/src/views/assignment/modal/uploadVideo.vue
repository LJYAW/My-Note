<!--
 * @Author: your name
 * @Date: 2020-11-12 11:38:33
 * @LastEditTime: 2020-11-20 11:49:37
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/assignment/modal/uploadVideo.vue
-->
<template>
  <div>
    <model :id="'uploadVideoAssign'"
      ref="uploadVideoAssign">
      <div slot="body" style="display:flex;flex-direction: column;">
        <div class="assign-upload-video">
          <!-- 上传bar -->
          <div v-if="status_loading" class="assign-upload-pro-wrap">
            <div class="assign-upload-pro">
              <el-progress :text-inside="true" :stroke-width="13" :percentage="complete"></el-progress>
              <p>{{complete != 100 ? '正在上传中...' : '上传完成'}}</p>
            </div>
          </div>

          <!-- video -->
          <div class="h-100">
            <video
              controls="controls"
              style="width:100%;height:100%"
              :src="uploadVideo.url"></video>
          </div>
        </div>

        <!-- 视频标题 -->
        <div style="display:flex;align-items:center;margin-top:20px;">
          视频标题：
          <el-input style="flex:1;" v-model="uploadVideoName" placeholder="请输入视频标题">
          </el-input>
        </div>

        <el-button
          type="primary"
          :disabled="status_loading"
          round
          style="width: 100px;height:30px;margin:30px auto 20px;"
          size="small"
          @click="uploadMyFile">确定</el-button>
      </div>
    </model>
  </div>
</template>

<script>
export default {
  props: ['uploadVideo'],
  inject: ['detail'],
  data() {
    return {
      uploadUrl: 'task/upload-task-video',
      uploadVideoName: '',
      status_loading: false,
      complete: 0, // 文件上传进度
      file: null
    }
  },
  computed: {
    ct_id() {
      return this.detail.ct_id
    }
  },
  watch: {},
  methods: {
    uploadMyFile() {
      if (!this.uploadVideoName) {
        this.$alertMsg('请输入视频标题')
        return
      } else if (!this.uploadVideo.url) {
        this.$alertMsg('请上传视频')
        return
      }
      this.complete = 0
      var formData = new FormData()
      formData.append('video', this.uploadVideo.file)
      formData.append('ct_id', this.ct_id)
      formData.append('title', this.uploadVideoName)

      this.status_loading = true
      this.$axios
        .post(this.uploadUrl, formData, {
          onUploadProgress: progressEvent => {
            this.complete = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          }
        })
        .then(res => {
          if (res.data.code === '0000') {
            this.detail.getAssignVideoList()
          } else {
            this.$alertMsg(res.data.msg)
          }
          // 关闭上传弹框
          this.$refs.uploadVideoAssign.close()
          this.detail.modalName = ''
        })
        .catch(err => {
          this.$alertMsg('上传失败，请重新上传')
          console.error(err)
        })
        .finally(() => {
          this.status_loading = false
        })
    },
    close() {
      // 关闭上传弹框
      this.$refs.uploadVideoAssign.close()
      this.detail.modalName = ''
      // this.$emit('close')
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
#uploadVideoAssign.modal {
  &.model-sm {
    .modal-wrap {
      //   width: auto;
      max-width: auto !important;
    }
  }
}
.assign-upload-video {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 620px;
  height: 348px;
  margin: 0 auto;
  background-color: #e8e8e8;
  .assign-upload-pro-wrap {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    position: absolute;
    left: 0;
    top: 0;
    background-color: #ccc;
    .assign-upload-pro {
      padding: 0 48px;
      p {
        margin-top: 10px;
        text-align: center;
      }
    }
  }
}
/deep/ .upload.assign-upload .btn-style {
  font-size: 14px;
  color: #2a79df;
  text-decoration: underline;
}
</style>
