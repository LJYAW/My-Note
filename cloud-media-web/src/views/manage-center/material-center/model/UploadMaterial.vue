<!--
 * @Author: your name
 * @Date: 2021-09-02 10:27:49
 * @LastEditTime: 2021-09-16 10:31:53
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/material-center/model/index.vue
-->
<template>
  <div class="upload-material">
    <div class="upload-wrap">
      <base-upload
        v-if="!file"
        :file-type="fileType"
        @getFileUrl="getFileUrl"
      >
        <div class="upload-text">
          <svg-icon icon-class="upload-material" />
          <p>选择{{ btnName }}</p>
        </div>

      </base-upload>
      <div v-else class="material-wrap">
        <video v-if="file.type==='video/mp4'" :src="fileUrl" controls />
        <base-image v-else-if="file.type==='audio/mpeg'" src="@/assets/images/audio.png" class="audio" />
        <base-image v-else :src="fileUrl" />
      </div>
    </div>
    <base-btn size="mini" @click="upload">开始上传</base-btn>
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
      fileUrl: '',
      file: null
    }
  },
  computed: {
    ...mapState('dialog', ['dialogTitle']),
    fileType() {
      switch (this.dialogTitle) {
        case '背景音乐':
          return 'audio/mpeg'
        case '角标素材':case '模板素材':
          return 'image/png, image/jpeg, image/gif, image/jpg'
        default:
          return 'video/mp4'
      }
    },
    btnName() {
      switch (this.dialogTitle) {
        case '背景音乐':
          return '本地音乐'
        case '角标素材':case '模板素材':
          return '本地图片'
        default:
          return '本地视频'
      }
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    upload() {
      this.$emit('upload', this.fileUrl)
    },
    getFileUrl(url, file) {
      this.fileUrl = url
      this.file = file
    }
  }
}
</script>

<style scoped lang="scss">
.upload-material {

  ::v-deep .upload-wrap {
    width: 548px;
    height: 308px;
    background: #f2f2f2;
    border-radius: 4px;
    margin-bottom: 20px;
    display: flex;
    flex-direction: column;
    justify-content: center;

    .upload {
      height: 100%;

      .btn-style {
        display: none;
      }

      .upload-text {
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        svg {
          width: 40px;
          height: 31px;
          margin-bottom: 11px;
        }

        p {
          color: #404040;
          font-size: 14px;
          line-height: 14px;
          opacity: .6;
        }
      }
    }

    .material-wrap {
      width: 100%;
      height: 100%;

      video {
        width: 100%;
      }
    }
  }

  .el-button {
    width: 100%;
  }
}
</style>
