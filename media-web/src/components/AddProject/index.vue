<!--
 * @Author: your name
 * @Date: 2021-08-31 17:41:41
 * @LastEditTime: 2021-09-23 18:52:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/components/AddProject/index.vue
-->
<template>
  <div class="add-project">
    <div v-if="!videoSrc" class="upload-wrap">
      <base-upload
        button-name=""
        :file-type="fileType"
        @getFileUrl="getFileUrl"
      >
        <div>
          <svg-icon icon-class="upload" />
          <span>选择本地文件</span>
        </div>
      </base-upload>
      <div @click="openLibrary">
        <svg-icon icon-class="library" />
        <span>从视频库选择</span>
      </div>
    </div>

    <!-- 内容主体 -->
    <div v-else class="video-item">
      <!-- :controls="progress==100||!uploader" -->
      <video v-if="isVideo" id="video" :src="videoSrc" class="video" :controls="!progressShow" />
      <img v-else src="@/assets/images/audio.png" alt="">
      <div v-if="progressShow" class="video-msg">
        <el-progress :text-inside="true" :stroke-width="26" :percentage="progress" />
      </div>
      <slot v-if="isVideo" />
    </div>
    <!-- 视频标题 -->
    <div ref="upload-input1" class="upload-input title">
      <div class="input-tit">标题</div>
      <input
        v-model="title"
        type="text"
        class="input"
        @focus="inputFocus('upload-input1')"
        @blur="inputBlur('upload-input1')"
      >
    </div>

    <el-button
      :loading="loading"
      style="width: 100%"
      type="primary"
      size="small"
      @click="addPro"
    >开始编辑</el-button>

    <base-dialog
      :show.sync="dialogVisible"
      width="1000px"
    >
      <VideoLibrary @handelClick="chooseVideo" />
    </base-dialog>
  </div>
</template>

<script>
import VideoLibrary from '@/components/VideoLibrary'
import sdkUploadFile from '@/utils/sdk-upload-file.js'

export default {
  components: {
    VideoLibrary
  },
  props: {
    fileType: {
      type: String,
      default: 'video/mp4'
    },
    uploadType: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      dialogVisible: false,

      videoSrc: '',
      title: '',

      progress: 0,

      videoData: {},

      loading: false,
      filesType: ''
    }
  },
  computed: {
    isVideo() {
      return this.filesType === 'video/mp4'
    },
    progressShow() {
      return this.progress < 100
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    openLibrary() {
      this.dialogVisible = true
    },
    // 选择本地文件
    async getFileUrl(url, file) {
      this.loading = true
      this.videoSrc = url
      this.filesType = file.type
      this.title = this.getFileName(file.name)

      this.videoData.video_size = file.size
      this.videoData.titles = this.title
      const data = await sdkUploadFile(file, this.uploadType, (total) => {
        const progress = Math.min((Math.floor(total / file.size * 100)), 100)
        this.progress = progress
      })
      this.getBosKey(data)
    },
    // 视频库选择
    chooseVideo(item) {
      this.progress = 100
      this.videoSrc = item.video_url
      this.title = item.titles
      this.videoData = item
      // this.uuid = item.uuid
      this.filesType = 'video/mp4'
      this.dialogVisible = false
    },
    inputFocus(el) {
      this.$refs[el].style = `border: 1px solid #5675E8`
    },
    inputBlur(el) {
      this.$refs[el].style = `border: 0px solid #5675E8`
    },
    // 获取上传的服务器key
    async getBosKey(e) {
      this.key = e.body.key
      this.videoSrc = e.body.location
      if (this.filesType === 'video/mp4') {
        const { video_duration, video_height, video_width } = await this.getVideoData(e.body.location)
        this.videoData.video_duration = parseInt(video_duration * 1000)
        this.videoData.create_time = this.getNowFormatDate()
        this.videoData.video_width = video_height
        this.videoData.video_height = video_width
      }
      this.loading = false
    },
    // 创建
    addPro() {
      if (!this.videoSrc) {
        this.$message.error('请选择文件')
        return
      }
      const obj = Object.assign({}, this.videoData)
      obj.videoSrc = this.videoSrc
      this.title && (obj.title = this.title)
      this.key && (obj.video_local_key = this.key)

      sessionStorage.setItem('videoMarkDetails', JSON.stringify(obj))
      this.$emit('addProject', this)
    },
    getFileName(data) {
      return data.substring(0, data.indexOf('.'))
    },

    getVideoData(videoUrl) {
      return new Promise((resolve, reject) => {
        const videoDom = document.createElement('video')
        videoDom.src = videoUrl
        videoDom.addEventListener('loadeddata', () => {
          const videoData = {
            video_duration: videoDom.duration,
            video_height: videoDom.videoHeight,
            video_width: videoDom.videoWidth
          }
          resolve(videoData)
        })
      })
    },

    getNowFormatDate() {
      var date = new Date()
      var seperator1 = '-'
      var seperator2 = ':'
      var month = date.getMonth() + 1
      if (month >= 1 && month <= 9) {
        month = '0' + month
      }
      var strDate = date.getDate()
      if (strDate >= 0 && strDate <= 9) {
        strDate = '0' + strDate
      }
      var hour = date.getHours()
      if (hour >= 0 && hour <= 9) {
        hour = '0' + hour
      }
      var minute = date.getMinutes()
      if (minute >= 0 && minute <= 9) {
        minute = '0' + minute
      }
      var sec = date.getSeconds()
      if (sec >= 0 && sec <= 9) {
        sec = '0' + sec
      }
      var currentdate = date.getFullYear() + seperator1 + month + seperator1 +
         strDate + ' ' + hour + seperator2 + minute +
         seperator2 + sec
      return currentdate
    }
  }
}
</script>

<style scoped lang="scss">
.upload-dialog {

  .upload-wrap {
    width: 100%;
    height: 308px;
    background: #f2f2f2;
    border-radius: 4px;
    margin: 0 auto;
    margin-top: -20px;
    display: flex;
    align-items: center;
    justify-content: space-around;

    div {
      display: flex;
      justify-content: center;
      align-items: center;
      width: 120px;
      height: 120px;
      // opacity: .6;
      border: 1px dashed #979797;
      cursor: pointer;
      flex-direction: column;

      span {
        color: rgba(64, 64, 64, .4);
      }

      svg {
        fill: rgba(64, 64, 64, .2);
        background: #f2f2f2;
        font-size: 36px;
      }
    }
  }
}

.upload-input {
  width: 100%;
  height: 30px;
  display: flex;
  background: #f2f2f2;
  border-radius: 4px;
  align-items: center;
  margin-bottom: 10px;
  margin-top: 10px;
  box-sizing: border-box;

  &.upload-input-tag {
    width: 66%;
    overflow: hidden;

    .input {
      width: 70px;
    }

    .tip-img {
      width: 10px;
    }
  }

  .input-tit {
    flex-shrink: 0;
    width: 58px;
    font-size: 12px;
    color: #404040;
    margin: 9px;
    padding-right: 9px;
    box-sizing: border-box;
    border-right: 1px solid #d8d8d8;
    text-align: center;
  }

  .input {
    flex: 1;
    font-size: 12px;
    color: #404040;
  }
}

.video-item {
  position: relative;
  width: 100%;
  height: 308px;
  border-radius: 4px;
  overflow: hidden;

  img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

}

.video {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
}

.video-msg {
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  background: rgba(0,0,0,.6);
  padding: 80px;

  .el-progress {
    width: 90%;

    ::v-deep.el-progress-bar__outer {
      background: #000;
      opacity: .6;
    }
  }

  span {
    font-size: 14px;
    color: #fff;
    cursor: pointer;
  }

}

.btn-wrap {
  position: absolute;
  bottom: 20px;
  width: 100%;
  display: flex;
  justify-content: space-around;
  padding: 0 25%;

  .btn {
    width: 112px;
    height: 30px;
    background: #f8f8f8;
    border-radius: 4px;
    font-size: 12px;
    color: #404040;
    line-height: 30px;
    text-align: center;
    cursor: pointer;

    &.active {
      background: #5675e8;
      color: #fff;
    }
  }
}
</style>
