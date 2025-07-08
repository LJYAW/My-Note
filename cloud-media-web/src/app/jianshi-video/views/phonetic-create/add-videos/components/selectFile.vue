<!--
 * @Author: your name
 * @Date: 2021-09-10 14:46:23
 * @LastEditTime: 2021-10-15 19:48:41
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
              <div class="bold">导入音频文件</div>
              <div class="text">支持mp3、aac、wav格式，单个文件大小 不超过 2GB</div>
            </div>
          </div>
        </div>
      </base-upload>
    </div>

    <!-- 内容主体 -->
    <div v-show="videoSrc" class="video-item">
      <div class="video-box">
        <video id="video" :src="videoSrc" class="video" crossorigin="anonymous" :controls="!progressShow" />
        <div v-if="progressShow" class="video-msg">
          <el-progress :text-inside="true" :stroke-width="26" :percentage="parseInt(progress)" />
        </div>
      </div>

      <div class="video-msg-item">
        <div class="tit">音频名称</div>
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
        <div class="tit">音频信息</div>
        <div class="cont">
          <span>音频时长  {{ audioData.video_duration*1000 |timesToHMS }}</span>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import VideoLibrary from '@/components/VideoLibrary'
import sdkUploadFile from '@/utils/sdk-upload-file.js'
import { getFileUrl, dataURLtoFile, getBase64Video, cutVideoPoster } from '@/utils/file.js'

export default {
  components: {
  },
  props: {
    fileType: {
      type: String,
      default: 'audio/mpeg, audio/wav,audio/aac'
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

      audioData: {},
      // progress: 0,
      imgBoskey: '',
      key: ''//
    }
  },
  computed: {
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
      // const posterUrl = await cutVideoPoster(url)
      this.audioData = await this.getVideoData(url)

      // 将 base64 转成 file 文件
      // const imgName = new Date().getTime() + '.jpg'
      // const imgFile = await dataURLtoFile(posterUrl, imgName)
      // const imgData = await sdkUploadFile(imgFile, 'jianshi/zm/images/')

      const audioData = await sdkUploadFile(file, 'jianshi/yy/audio/', (total) => {
        const progress = Math.min((Math.floor(total / file.size * 100)), 100)
        this.progress = progress
      })

      const params = {
        file: file,
        audio_url: audioData.body.location,
        audio_key: audioData.body.key,
        titles: this.title,
        audio_duration: Math.floor(this.audioData.video_duration * 1000)
      }
      this.$emit('getBosKey', params)
    },

    getVideoData(url) {
      return new Promise((resolve, reject) => {
        const videoDom = document.createElement('video')
        videoDom.src = url
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
    background: url("../../../../../../assets/images/audio.png");
    background-size: 100% 100%;

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
