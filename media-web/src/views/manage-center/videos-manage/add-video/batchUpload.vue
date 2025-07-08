<!--
 * @Author: your name
 * @Date: 2021-09-14 10:33:10
 * @LastEditTime: 2021-10-14 11:03:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/videos-manage/add-video/batchUpload.vue
-->
<template>
  <div class="batch-upload">
    <!-- <BaseUpload file-type="video/mp4" @getFileUrl="getFileUrl" /> -->

    <el-upload
      ref="upload"
      drag
      action=""
      multiple
      :show-file-list="false"
      :http-request="myUploadFile"
      :limit="10"
      accept="video/mp4"
      :auto-upload="true"
      :on-change="onChangeFile"
    >
      <i class="el-icon-upload" />
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    </el-upload>

    <div class="upload-wrap">
      <div class="upload-header">
        <CatalogueInput @getDirId="getDirId" />
        <el-button :disabled="uploadIng" type="primary" size="mini" @click="startUpload">开始上传</el-button>

        <div class="store-size">
          <span class="sub">存储空间使用情况</span>
          <span>
            {{ initSize(companiesData.store_size) }}
          </span>
          <span>&nbsp;/&nbsp;</span>
          <span>
            {{ initSize(companiesData.t_store_size) }}
          </span>
        </div>
      </div>

      <div class="file-list">
        <ul>
          <li v-for="(file) in fileList" :key="file.uid" class="file-item">
            <div class="video-poster">
              <video class="video" :src="file.videoUrl" />
            </div>

            <div class="file-content">
              <div class="video-des">
                <span class="file-name">{{ getFileName(file.name) }}</span>
                <span class="file-type">{{ file.raw.type }}</span>
                <span class="file-name">{{ initSize(file.raw.size) }}</span>
              </div>
              <el-progress :text-inside="true" :stroke-width="12" :percentage="file.percentage" />
            </div>

            <div class="upload-status">
              <span v-if="file.uploadStatus !== '上传成功'">{{ file.uploadStatus }}</span>
              <label v-else class="el-upload-list__item-status-label">
                <i class="el-icon-upload-success el-icon-check" />
              </label>
            </div>

          </li>
        </ul>
      </div>

    </div>

  </div>
</template>

<script>
import { initSize } from '@/utils/filter.js'
import sdkUploadFile from '@/utils/sdk-upload-file.js'
import CatalogueInput from '../components/CatalogueInput'
import { getFileUrl, dataURLtoFile, getBase64Video, cutVideoPoster } from '@/utils/file.js'

export default {
  components: {
    CatalogueInput
  },
  props: {

  },
  data() {
    return {
      uploadIng: false,
      companiesData: {},
      surplusSize: 0,
      fileList: [], // // 上传文件列表
      upFileList: [], // 文件File列表 上传参数,
      dir_id: ''
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getSurplusSize()
  },
  mounted() {

  },
  methods: {
    getDirId(e) {
      this.dir_id = e
    },
    myUploadFile() {
    },
    onChangeFile(files, fileList) {
      files.videoUrl = getFileUrl(files.raw)
      files.uploadStatus = '等待上传...'
      this.fileList.push(files)
    },
    async startUpload() {
      if (this.companiesData.store_size >= this.companiesData.t_store_size) {
        this.$message({
          message: '存储空间不足',
          type: 'error'
        })
        return
      }

      this.uploadIng = true
      for (let i = 0; i < this.fileList.length; i++) {
        const file = this.fileList[i]

        if (file.uploadStatus === '等待上传...' || file.uploadStatus === '上传失败！') {
          try {
            let status = Object.assign(file, {
              uploadStatus: '正在自动截取视频封面'
            })
            this.$set(this.fileList, i, status)

            // 截取一帧封面图
            const posterUrl = await cutVideoPoster(file.videoUrl)

            // 将 base64 转成 file 文件
            const imgName = new Date().getTime() + '.jpg'
            const imgFile = await dataURLtoFile(posterUrl, imgName)
            const imgData = await sdkUploadFile(imgFile)
            const { video_duration, video_height, video_width } = await this.getVideoData(file.videoUrl)

            status = Object.assign(file, {
              uploadStatus: '上传中...'
            })
            this.$set(this.fileList, i, status)
            const vdideoData = await sdkUploadFile(file.raw, '', (total) => {
              const progress = Math.min((Math.floor(total / file.size * 100)), 100)
              file.percentage = progress
            })
            this.$set(this.fileList, i, status)

            const params = {
              cover_bos_key: imgData.body.key,
              titles: this.getFileName(file.name),
              video_bos_key: vdideoData.body.key,
              video_size: file.size,
              dir_id: this.dir_id,
              video_duration: Math.floor(video_duration * 1000),
              video_height: video_height,
              video_width: video_width
            }
            await this.putVideos(params, this.dir_id)
            status = Object.assign(file, {
              uploadStatus: '上传成功'
            })
            this.$set(this.fileList, i, status)
          } catch (err) {
            const status = Object.assign(file, {
              uploadStatus: '上传失败！'
            })
            this.$set(this.fileList, i, status)
            console.error(err)
          }
        }

        this.getSurplusSize()
      }
      this.uploadIng = false
    },
    putVideos(params, dir_id) {
      return new Promise((resolve, reject) => {
        this.$post('/videos/', params, { DirId: dir_id }).then(response => {
          const { err, res } = response
          if (err) {
            this.$message({
              message: err.msg,
              type: 'error'
            })
            reject(err)
          } else {
            resolve(res)
          }
        })
      })
    },
    beforeRemove() {

    },
    onRemoveFile() {

    },
    // 获取剩余空间
    async getSurplusSize() {
      const { res, err } = await this.$get('/companies/')
      // this.surplusSize = res.data.t_store_size - res.data.store_size
      this.companiesData = res.data
    },
    // 获取 video
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
    initSize(size) {
      return initSize(size)
    },
    getFileName(data) {
      return data.substring(0, data.indexOf('.'))
    }
  }
}
</script>

<style scoped lang="scss">
.upload-wrap {

  .file-list {
    margin-top: 30px;

    .file-item {
      display: flex;
      margin-bottom: 20px;
      position: relative;
      height: 50px;

      .video {
        width: 50px;
        height: 50px;
        border-radius: 3px;
        border: 1px solid #ccc;
        margin-right: 15px;
      }

      .file-content {
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: space-around;

        .video-des {
          display: flex;
          justify-content: space-between;

          .file-type {
            margin-left: auto;
            margin-right: 15px;
          }
        }
      }

      .upload-status {
        width: 130px;
        height: 100%;
        text-align: left;
        margin-left: 20px;
        display: flex;
        align-items: center;
        position: relative;

        .el-icon-upload-success {
          // font-size: px;
          color: white;
        }

        .el-upload-list__item-status-label {
          width: 15px;
          height: 15px;
          border-radius: 50%;
          background: #13ce66;
          box-shadow: 0 1px 1px #ccc;
          display: block;
          position: absolute;
          left: 0;
          top: 50%;
          transform: translate(0, -50%);
        }
      }
    }
  }

  .upload-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 30px;

    .store-size {
      display: flex;
      justify-content: center;
      align-items: center;

      .sub {
        margin-right: 10px;
      }
    }

    ::v-deep .el-button--mini {
      height: 30px;
    }

  }
}

</style>
