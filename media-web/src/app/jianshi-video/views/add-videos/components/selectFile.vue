<!--
 * @Author: your name
 * @Date: 2021-09-10 14:46:23
 * @LastEditTime: 2021-10-22 14:31:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/add-videos/components/selectFile.vue
-->
<template>
  <div class="upload-file">
    <div
      v-show="!videoSrc"
      class="upload-wrap"
    >
      <base-upload
        ref="upload"
        button-name=""
        :file-type="fileType"
        @getFileUrl="getFileUrl"
      >
        <div class="">
          <div>
            <svg-icon class="svg" icon-class="file" />
            <div>
              <div class="bold">导入视频文件</div>
              <div class="text">支持mp4格式，单个文件大小 不超过 2GB</div>
            </div>
          </div>
        </div>
      </base-upload>

      <div class="upload" @click="openLibrary">
        <div>
          <svg-icon class="svg" icon-class="libFile" />
          <div>
            <div class="bold">从视频库选择文件</div>
            <div class="text">支持mp4格式，单个文件大小 不超过 2GB</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 内容主体 -->
    <div v-show="videoSrc" class="video-item">
      <!-- :controls="progress==100||!uploader" -->
      <div class="video-box">
        <video id="video" :src="videoSrc" class="video" crossorigin="anonymous" :controls="!progressShow" />
        <div v-if="progressShow" class="video-msg">
          <el-progress :text-inside="true" :stroke-width="26" :percentage="parseInt(progress)" />
        </div>
        <!-- <div v-else class="video-msg">
        <el-button type="primary" size="default" @click="uploadAgain">重新上传</el-button>
      </div> -->
      </div>

      <div class="video-msg-item">
        <div class="tit">视频名称</div>
        <div class="cont" style="justify-content: flex-start;">
          <el-input
            v-if="showInput"
            v-model="title"
            size="small"
            placeholder="请输入视频名称"
            maxlength="20"
            show-word-limit
            class="subtitletitel"
            type="text"
            @change="changeTit"
            @blur="setShowInput"
          />
          <p v-else>{{ title }}</p>
          <svg-icon icon-class="edit" @click="setShowInput" />
        </div>
      </div>
      <div class="video-msg-item">
        <div class="tit">视频信息</div>
        <div class="cont">
          <span>视频时长  {{ videoData.video_duration*1000 |timesToHMS }}</span>
          <span>分辨率    {{ videoData.video_width }}*{{ videoData.video_height }}</span>
        </div>
      </div>

    </div>
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
import { getFileUrl, dataURLtoFile, getBase64Video, cutVideoPoster } from '@/utils/file.js'
import { mapGetters } from 'vuex'

export default {
  components: {
    VideoLibrary
  },
  props: {
    fileType: {
      type: String,
      default: 'video/mp4'
    }
  },
  data() {
    return {
      showInput: false,
      videoFile: null, // 视频文件
      videoSrc: '', // 视频链接
      poster_img: '', // 封面图链接
      title: '', // 标题

      dialogVisible: false,

      progress: 0,

      videoData: {},
      // progress: 0,
      imgBoskey: '',
      key: ''//
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ]),
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
      const { href } = this.$router.resolve({
        path: '/guide/video-home'
      })
      this.roles.includes('视频管家')
        ? this.dialogVisible = true
        : window.open(href, '_blank')
    },
    setShowInput() {
      this.showInput = !this.showInput
    },
    changeTit() {
      this.$emit('changeTit', this.title)
    },
    // uploadAgain() {
    //   this.$refs.upload.updataImg()
    // },
    // 选择本地文件
    async getFileUrl(url, file) {
      this.videoFile = file
      this.loading = true
      this.videoSrc = url
      this.filesType = file.type
      this.title = this.getFileName(file.name).substring(0, 20)

      // 截取一帧封面图
      const posterUrl = await cutVideoPoster(url)
      this.videoData = await this.getVideoData(url)

      // 将 base64 转成 file 文件
      const imgName = new Date().getTime() + '.jpg'
      const imgFile = await dataURLtoFile(posterUrl, imgName)
      const imgData = await sdkUploadFile(imgFile, 'jianshi/zm/images/')

      const videoData = await sdkUploadFile(file, 'jianshi/zm/videos/', (total) => {
        const progress = Math.min((Math.floor(total / file.size * 100)), 100)
        this.progress = progress
      })

      const params = {
        video_paths: videoData.body.key,
        cover_paths: imgData.body.key,
        titles: this.title,
        video_duration: Math.floor(this.videoData.video_duration * 1000),
        video_width: this.videoData.video_width,
        video_height: this.videoData.video_height
      }
      this.$emit('getBosKey', params)
    },

    async chooseVideo(item) {
      this.progress = 100
      this.videoSrc = item.video_url
      this.title = item.titles.substring(0, 20)
      this.videoData = {
        video_duration: item.video_duration / 1000,
        video_width: item.video_width,
        video_height: item.video_height
      }
      const params = {
        video_paths: item.video_bos_key,
        cover_paths: item.cover_bos_key,
        titles: item.titles,
        video_duration: item.video_duration,
        video_width: item.video_width,
        video_height: item.video_height
      }
      this.$emit('getBosKey', params)
      this.dialogVisible = false
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
    getFileName(data) {
      return data.substring(0, data.indexOf('.'))
    }
  }
}
</script>

<style scoped lang="scss">
.upload-file {
  flex: 2;
  min-height: 572px;
  background: #fff;
  border-radius: 4px;
  display: flex;
  justify-content: center;
  align-items: center;

  // .upload {
  //   width: 90%;
  //   height: 85%;

  //   a {
  //     width: 100%;
  //     height: 100%;
  //   }

  // }

  .upload-wrap {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    // align-items: center;

    .upload {
      height: 50%;
      width: 40%;
      flex-shrink: 0;
      display: flex;
      margin: 50px 10px;
      align-items: center;
      justify-content: center;
      border: 1px dashed #979797;
      cursor: pointer;
      border-radius: 4px;

      div {
        text-align: center;
      }
    }

    .svg {
      font-size: 48px;
      margin-right: 25px;
    }

    .bold {
      font-size: 20px;
      color: #404040;
      font-weight: 600;
    }

    .text {
      font-size: 12px;
      color: #b3b3b3;
    }
  }

  .video-item {
    position: relative;
    width: 100%;
    height: 100%;
    border-radius: 4px;
    text-align: center;
    overflow: hidden;
    padding-top: 20px;

    img {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

  }

  .video-box {
    position: relative;
    width: 672px;
    border-radius: 5px;
    height: 378px;
    margin: 0 auto;
    overflow: hidden;

    .video {
      width: 100%;
      height: 100%;
      // position: absolute;
      // width: 672px;
      // border-radius: 5px;
      // height: 378px;
      // width: 100%;
      // height: 100%;
      // top: 0;
    }

    .video-msg {
      position: absolute;
      top: 0;
      width: 100%;
      height: 100%;
      z-index: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      background: rgba(0,0,0,.6);
      // padding: 80px;

      .el-progress {
        width: 60%;

        ::v-deep.el-progress-bar__outer {
          background: rgba($color: #000, $alpha: .6);
        }
      }

      span {
        font-size: 14px;
        color: #fff;
        cursor: pointer;
      }

    }
  }

  .video-msg-item {
    text-align: left;
    width: 672px;
    margin: 0 auto;
    margin-top: 30px;
    // display: flex;
    // justify-content: space-between;

    .tit {
      font-size: 14px;
      font-weight: 600;
      color: #404040;
    }

    .cont {
      display: inline-block;
      font-size: 12px;
      font-weight: 400;
      color: hsla(0, 0%, 25%, .6);
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: 10px;

      span {
        flex: 1;
      }

      svg {
        margin-left: 20px;
      }
    }
  }
}
</style>
