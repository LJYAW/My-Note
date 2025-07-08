<!--
 * @Author: your name
 * @Date: 2021-09-16 17:56:06
 * @LastEditTime: 2021-09-28 11:24:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/components/upload.vue
-->
<template>
  <div>
    <base-upload
      button-name=""
      :file-type="fileType"
      @getFileUrl="getFileUrl"
    >
      <div>
        <!-- <solt /> -->
        <span class="btn-name">{{ btnName }}</span>
      </div>
    </base-upload>
  </div>
</template>

<script>
import sdkUploadFile from '@/utils/sdk-upload-file.js'
import getImageSize from '../../../utils/get-image-size'
import { getFileUrl, dataURLtoFile, getBase64Video, cutVideoPoster } from '@/utils/file.js'
import { canvasToImgUrl } from '../../../utils/reset-img-size'

export default {
  components: {

  },
  props: {
    // 自定义角标 特殊处理
    customJiaobiao: {
      type: Boolean,
      default: false
    },
    fileType: {
      type: String,
      default: ''
    },
    btnName: {
      type: String,
      default: '选择文件'
    }
  },
  data() {
    return {

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
    async getFileUrl(url, file) {
      // console.log('🚀 ~ file: upload.vue ~ line 68 ~ getFileUrl ~ url', url)
      let imgData, videoData, fileData
      // this.$emit('getFileName', this.getFileName(file.name))
      if (file.type === 'video/mp4') {
        const posterUrl = await cutVideoPoster(url)
        // 将 base64 转成 file 文件
        const imgName = new Date().getTime() + '.jpg'
        const imgFile = await dataURLtoFile(posterUrl, imgName)
        imgData = await sdkUploadFile(imgFile, 'materials')
        // 获取视频相关信息
        const { video_duration, video_height, video_width } = await this.getVideoData(url)
        const videoD = {
          duration: Math.floor(video_duration * 1000),
          width: video_width,
          height: video_height,
          title: this.getFileName(file.name),
          filesize: file.size
        }
        this.$emit('getFileData', videoD)

        videoData = await sdkUploadFile(file, 'materials', (total) => {
          const progress = Math.min((Math.floor(total / file.size * 100)), 100)
          this.$emit('getProgress', progress)
        })
      } else {
        if (file.type === 'audio/mpeg') {
          const duration = await this.getAudioData(url)
          const audioD = {
            title: this.getFileName(file.name),
            duration: Math.floor(duration * 1000)
          }
          this.$emit('getFileData', audioD)
        } else {
          const { w, h } = await getImageSize(url)
          const imgD = {
            width: w,
            height: h,
            title: this.getFileName(file.name)
          }
          this.$emit('getFileData', imgD)
        }
        if (this.customJiaobiao) {
          const imgUrl = await canvasToImgUrl(url)
          const imgName = new Date().getTime() + '.jpg'

          const newImgFile = await dataURLtoFile(imgUrl, imgName)
          fileData = await sdkUploadFile(newImgFile, 'materials', (total) => {
            const progress = Math.min((Math.floor(total / file.size * 100)), 100)
            this.$emit('getProgress', progress)
          })
          console.log('🚀 ~ file: upload.vue ~ line 99 ~ fileData=awaitsdkUploadFile ~ fileData', fileData)
        } else {
          fileData = await sdkUploadFile(file, 'materials', (total) => {
            const progress = Math.min((Math.floor(total / file.size * 100)), 100)
            this.$emit('getProgress', progress)
          })
        }
      }

      const params = {
        imgData: imgData,
        videoData: videoData,
        fileData: fileData
      }
      this.$emit('getBosKey', params)
    },

    getAudioData(url) {
      return new Promise((resolve, reject) => {
        var audio = new Audio(url)
        // 元数据已加载
        audio.addEventListener('loadedmetadata', () => {
          const duration = audio.duration
          resolve(duration)
        })
      })
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
.btn-name {
  color: rgba(86, 117, 232, 1);
}
</style>
