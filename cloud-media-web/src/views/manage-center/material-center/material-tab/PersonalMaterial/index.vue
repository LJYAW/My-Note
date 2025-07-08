<!--
 * @Author: your name
 * @Date: 2021-08-31 15:08:58
 * @LastEditTime: 2021-10-22 11:29:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/material-center/material-tab/PersonalMaterial/index.vue
-->
<template>
  <div class="personal-material">
    <Upload
      :file-type="fileType"
      class="upload"
      btn-name=""
      @getProgress="getProgress"
      @getFileData="getFileData"
      @getBosKey="getBosKey"
    >
      <el-button slot="btn" type="primary" size="small" class="upload-btn">上传素材</el-button>
    </Upload>

    <!-- <span v-if="isUpload" class="tips">您有一个PPT文件正在上传处理中，请稍后...</span>
    <span v-else class="tips">支持图片、视频、PPT转图片素材导入</span> -->

    <div v-infinite-scroll="load" class="infinite-list transition-wrap">
      <base-card
        v-for="item in materialData"
        :key="item.id"
        :show-mark="false"
        class="video-item infinite-list-item"
      >

        <div slot="img">
          <div v-if="item.status === '上传中'" class="progress-item">
            <el-progress :text-inside="true" :stroke-width="13" :percentage="complete" />
          </div>
          <VideoCard v-if="item.cover_url" :cover-url="item.cover_url" :video-url="item.file_url" />
          <div v-else class="image-slot">
            <base-image :src="item.file_url" />
          </div>
          <div class="btn-wrap">
            <div>
              <p v-if="item.cover_url">视频时长 {{ item.duration|timesToHMS }}</p>
            </div>
            <!-- {{ item.id }} -->
            <svg-icon icon-class="delete" @click="deleteMaterial(item.uuid)" />
          </div>
        </div>
      </base-card>
    </div>
    <svg-icon v-if="!materialData.length && materialData.length < 1" icon-class="empty-img" class="empty-img" />

  </div>

</template>

<script>
import VideoCard from '@/app/jianshi-video/components/VideoCard'
import setQueryParams from '@/utils/setQueryParams.js'

import Upload from '@/app/jianshi-video/views/phonetic-create/components/upload.vue'
import guiId from '@/app/jianshi-video/utils/guid.js'
import axios from 'axios'
export default {
  name: 'PersonalMaterial',
  components: {
    VideoCard,
    Upload
  },
  props: {

  },
  data() {
    return {
      materialData: [],
      page: 1,
      limit: 20,
      count: 0,
      loading: true,
      fileType: 'video/mp4,image/png, image/jpeg, image/gif, image/jpg',
      // fileType: 'video/mp4,image/png, image/jpeg, image/gif, image/jpg,.ppt,.pptx',
      complete: 0,
      isUpload: false
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getInitData()
    // this.getTransStatus()
  },
  activated() {
    this.resetData()
    // this.getTransStatus()
  },
  mounted() {

  },
  beforeDestroy() {
  },
  methods: {
    getProgress(ev) {
      this.complete = ev
    },
    // 获取上传的文件信息
    getFileData(obj) {
      if (obj.type && obj.type === 'ppt') {
        this.uploadPPt(obj.file)
      } else {
        this.materialData.unshift({
          status: '上传中',
          id: guiId()
        })
        this.fileData = obj
      }
    },
    // 获取当前上传状态
    async getTransStatus() {
      const { err, res } = await this.$get('/transppt/status')
      if (err) {
        this.$message.error(err.msg)
      } else {
        const uploadStatus = localStorage.getItem('isUpload')
        if ((Number(uploadStatus) === 1 && res.data === 0) || res.data === 1) {
          this.$message({
            type: 'info',
            message: '正在处理'
          })
          this.fileType = 'video/mp4,image/png, image/jpeg, image/gif, image/jpg'
          this.isUpload = true
        } else {
          localStorage.setItem('isUpload', res.data)
          this.fileType = 'video/mp4,image/png, image/jpeg, image/gif, image/jpg,.ppt,.pptx'
          this.isUpload = false
        }
      }
    },
    // 上传ppt
    async uploadPPt(file) {
      this.$message({ message: 'ppt上传中，请稍候', type: 'success' })

      const formData = new FormData()
      formData.append('file', file)
      formData.append('resize', '1280x720')
      this.isUpload = true
      this.fileType = 'video/mp4,image/png, image/jpeg, image/gif, image/jpg'
      localStorage.setItem('isUpload', 1)

      await this.transPPt(formData)
      this.resetData()
      this.isUpload = false
      this.fileType = 'video/mp4,image/png, image/jpeg, image/gif, image/jpg,.ppt,.pptx'
    },
    transPPt(formData) {
      return new Promise((resolve, reject) => {
        axios.post('/transppt/do', formData, { timeout: 10 * 60 * 1000 }).then((res) => {
          if (res.data.code === '200') {
            this.$message({
              type: 'success',
              message: '上传成功'
            })
            localStorage.setItem('isUpload', 2)
            resolve(res.data)
          } else {
            localStorage.setItem('isUpload', 3)
            this.$message.error(res.data.msg)
            reject(res.data.msg)
          }
        }).catch((err) => {
          this.$message.error(err.msg)
          localStorage.setItem('isUpload', 3)
          reject(err)
        })
      })
    },
    async getBosKey(file) {
      var params
      if (file.fileData) {
        const { key } = file.fileData.body
        params = {
          file_key: key
        }
      } else {
        params = {
          cover_key: file.imgData.body.key,
          file_key: file.videoData.body.key,
          types: 'video'
        }
      }
      params = Object.assign(params, this.fileData)
      this.uploadData(params)
    },
    // 上传视频，图片
    async uploadData(paramsObj) {
      let params = {
        media_type: paramsObj.types ? 'video' : 'pic',
        source: '个人',
        type: '个人素材'
      }
      params = Object.assign(params, paramsObj)

      const { err, res } = await this.$post('/materials', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.materialData.splice(0, 1)
      this.$message({
        message: '上传成功!',
        type: 'success'
      })
      this.resetData()
      this.complete = 0
    },
    resetData() {
      this.materialData = []
      this.page = 1
      this.getInitData()
    },
    // 滚动加载数据
    load() {
      if (this.materialData.length < this.count) {
        this.page++
        this.getInitData()
      }
    },
    async getInitData() {
      this.loading = true
      const query = {
        type: '个人素材'
      }
      const params = {
        page: this.page,
        limit: this.limit,
        query: setQueryParams(query)
      }
      const { err, res } = await this.$get('/materials', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.count = res.count
      this.materialData = this.page === 1 ? res.data : this.materialData.concat(res.data)
      this.loading = false
    },
    deleteMaterial(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.confirmDelete(id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async confirmDelete(id) {
      const { err, data } = await this.$deleteRun(`/materials/${id}`)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        this.page = 1
        this.materialData = []
        this.getInitData()
      }
    }
  }
}
</script>

<style scoped lang="scss">
.personal-material {
  .upload {
    display : inline-block;
  }

  .tips {
    color : #5685E8;
    margin-left : 10px;
  }

  .upload-btn {
    // float: left;
    margin-bottom : 20px;
  }

  .transition-wrap {
    display : grid;
    grid-template-columns : repeat(4, 25%);  //同上
    grid-row-gap : 30px;
    margin : 0 -12px;
    max-height : calc(100vh - 230px);
    overflow-y : auto;

    .video-item {
      margin : 0 12px;

      ::v-deep .image {
        border-radius : 4px;
        height : auto;
        position : relative;
        background : none;

        .progress-item {
          position : absolute;
          width : 80%;
          z-index : 1;
          top : 50%;
          left : 50%;
          transform : translate(-50%,-50%);
        }

        .btn-wrap {
          position : absolute;
          width : 100%;
          padding : 9px;
          bottom : 0;
          z-index : 9;
          color : #FFFFFF;
          font-size : 12px;
          line-height : 12px;
          background : rgba(0,0,0,.4);
          display : flex;
          justify-content : space-between;
        }

        .image-slot {
          padding-bottom : 56.25%;
          height : 0;
          background : #EEEEEE;
          position : relative;

          .el-image {
            position : static;

            img {
              position : absolute;
              width : 100%;
              height : 100%;
              top : 0;
              // object-fit: cover;
            }
          }
        }
      }
    }
  }

  .empty-img {
    display : block;
    width : 140px;
    height : 172px;
    margin : 100px auto;
  }

  .laoding-text {
    text-align : center;
  }
}

</style>
