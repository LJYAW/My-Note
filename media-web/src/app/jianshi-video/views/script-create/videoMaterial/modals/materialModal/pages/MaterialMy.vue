<!--
 * @Author: zzx
 * @Date: 2020-10-27 16:28:22
 * @LastEditTime: 2021-10-12 10:13:44
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/src/material_my.vue
-->
<template>
  <div class="material-my">
    <div class="material-my-head">
      <Upload
        btn-name="上传素材"
        class="upload-position"
        file-type="image/gif,image/jpg,image/png,image/jepg,video/mp4"
        @getProgress="getProgress"
        @getFileData="getFileData"
        @getBosKey="getBosKey"
      />
    </div>
    <ul v-if="materialData.length" v-infinite-scroll="load" class="infinite-list list">
      <base-card
        v-for="(item,index) in materialData"
        :key="item.id"
        :show-mark="false"
        :class="['list-item infinite-list-item',{'active': my_image_index == index}]"
        @click.native="addItem(item,index)"
      >
        <div slot="img">
          <div v-if="item.progress === '上传中'" class="progress-item">
            <el-progress :text-inside="true" :stroke-width="13" :percentage="complete" />
          </div>
          <div v-else>
            <VideoCard v-if="item.cover_url" :cover-url="item.cover_url" :video-url="item.file_url" />
            <div v-else class="image-slot">
              <base-image :src="item.file_url" />
            </div>
            <div class="btn-wrap">
              <p v-if="item.cover_url">{{ item.duration|timesToHMS }}</p>
              <p v-else>图片素材</p>
            </div>
          </div>
        </div>
      </base-card>
    </ul>
    <svg-icon v-else icon-class="empty-img" class="empty-img" />
  </div>
</template>

<script>
import Upload from '../../../../components/upload.vue'
import setQueryParams from '@/utils/setQueryParams.js'
import VideoCard from '@/app/jianshi-video/components/VideoCard'
import { getMaterial, posttMaterial } from '@/app/jianshi-video/api/source-material/index.js'
export default {
  components: { VideoCard, Upload },
  props: {},
  data() {
    return {
      materialData: [],
      complete: 0,
      my_image_index: null,
      page: 1,
      limit: 20,
      count: 0,
      fileName: '',
      // duration: 0,
      videoData: {}
    }
  },
  computed: {
  },
  watch: {},
  created() {
    this.getMyFileList()
  },
  mounted() {
  },
  methods: {
    // 原文图片加载
    load() {
      if (this.materialData.length < this.count) {
        this.page++
        this.getMyFileList()
      }
    },
    getFileData(obj) {
      this.videoData = obj
      this.materialData.unshift({
        progress: '上传中'
      })
    },
    getProgress(ev) {
      this.complete = ev
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
          type: 'video'

        }
      }
      this.uploadData(params)
    },
    async uploadData(obj) {
      let params = {
        file_key: obj.file_key,
        media_type: obj.type ? 'video' : 'pic',
        source: '个人',
        type: '个人素材'
      }
      params = Object.assign(params, this.videoData)
      if (obj.type === 'video') params.cover_key = obj.cover_key
      const { err, res } = await posttMaterial(params)
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
      this.page = 1
      this.materialData = []
      await this.getMyFileList()
      this.complete = 0
    },
    // 获取用户本地素材列表
    async getMyFileList() {
      this.status_loading = true
      const query = {
        source: '个人',
        type: '个人素材'
      }
      const params = {
        page: this.page,
        limit: this.limit,
        query: setQueryParams(query)
      }
      const { err, res } = await getMaterial(params)
      this.status_loading = false
      if (err) {
        this.$message.err(err.msg)
        return
      }
      this.count = res.count
      this.materialData = this.page === 1 ? res.data : this.materialData.concat(res.data)
    },
    addItem(item, index) {
      if (item.imgError || item.progress === '上传中') {
        return
      }
      // 选择用户素材
      this.my_image_index = index
      this.$emit('selectImg', this.materialData[index])
    }
  }
}
</script>

<style scoped lang="scss">
.material-my {

  .material-my-head {
    position: absolute;
    right: 0;
    top: 0;

    ::v-deep .upload-position {
      background: #5675e8;
      border-radius: 4px;
      padding: 9px 10px;

      .btn-name {
        color: #fff;

      }
    }
  }

  .list {
    display: flex;
    flex-wrap: wrap;
    overflow: auto;
    height: auto;
    max-height: 480px;
    margin-bottom: 0 !important;

    .list-item {
      width: 252px;
      height: 142px;
      margin-bottom: 20px;
      position: relative;
      overflow: hidden;
      margin-right: 15px;
      background-color: #e5e5e5;

      &.active {
        border: 1px solid #5675e8;
      }

      ::v-deep .image {
        background: none;
        height: 100%;
        position: relative;

        .progress-item {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%,-50%);
          width: 80%;
        }

        .el-image {
          position: static;

          img {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
          }
        }
      }

      .btn-wrap {
        position: absolute;
        width: 76px;
        height: 24px;
        line-height: 24px;
        font-size: 12px;
        background: rgba(0,0,0,.4);
        border-radius: 4px;
        bottom: 20px;
        right: 10px;
        color: #fff;
        text-align: center;
      }
    }
  }

  .empty-img {
    width: 140px;
    height: 174px;
    margin: 100px auto;
    display: block;
  }
}
</style>
