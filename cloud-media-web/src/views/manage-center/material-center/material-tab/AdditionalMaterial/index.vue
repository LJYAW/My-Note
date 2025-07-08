<!--
 * @Author: your name
 * @Date: 2021-08-31 15:09:45
 * @LastEditTime: 2021-10-12 14:34:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/material-center/material-tab/AdditionalMaterial/index.vue
-->
<template>
  <div class="additional-material">
    <!-- tab -->
    <div class="tabs-wrap">
      <div
        v-for="item in tabs"
        :key="item"
        :class="[activeName==item&&'active','tabs-item']"
        @click="activeName=item"
      >{{ item }}素材</div>
    </div>
    <Upload
      :custom-jiaobiao="activeName === '角标'"
      :file-type="fileType"
      class="upload"
      btn-name=""
      @getProgress="getProgress"
      @getFileData="getFileData"
      @getBosKey="getBosKey"
    >
      <el-button slot="btn" type="primary" size="small" class="upload-btn">上传素材</el-button>
    </Upload>
    <!-- 素材列表 -->
    <svg-icon v-if="!materialData.length&&!loading" icon-class="empty-img" class="empty-img" />
    <ul v-else v-infinite-scroll="load" class="infinite-list transition-wrap">
      <div v-for="item in materialData" :key="item.id" class="infinite-list-item">
        <div v-if="item.status === '上传中'" class="progress-item">
          <el-progress :text-inside="true" :stroke-width="13" :percentage="complete" />
        </div>
        <component :is="componentName" v-else :material-data="item" @delete="deleteMaterial" />
      </div>
    </ul>

  </div>
</template>

<script>
import MaterialVideo from './MaterialVideo.vue'
import MaterialImg from './MaterialImg.vue'
import setQueryParams from '@/utils/setQueryParams.js'

import Upload from '@/app/jianshi-video/views/phonetic-create/components/upload.vue'
import guiId from '@/app/jianshi-video/utils/guid.js'

export default {
  components: {
    MaterialVideo,
    MaterialImg,
    Upload
  },
  props: {

  },
  data() {
    return {
      tabs: ['片头', '片尾', '角标', '视频模板'],
      activeName: '片头',
      media_type: 'video',
      materialData: [],
      componentName: 'MaterialVideo',
      page: 1,
      limit: 20,
      count: 0,
      loading: true,

      complete: 0,
      fileType: 'video/mp4'
    }
  },
  computed: {
  },
  watch: {
    activeName: {
      handler: function(val, oldVal) {
        this.componentName = (val === '片头' || val === '片尾') ? 'MaterialVideo' : 'MaterialImg'
        this.media_type = (val === '片头' || val === '片尾') ? 'video' : 'pic'
        // this.$store.commit('dialog/SET_DIALOG_TITLE', this.tabs[val])
        this.fileType = (val === '片头' || val === '片尾') ? 'video/mp4' : 'image/png, image/jpeg, image/gif, image/jpg'
        this.page = 1
        this.materialData = []
        this.getInitData()
      }
    }
  },
  created() {
    this.getInitData()
    // this.$store.commit('dialog/SET_DIALOG_TITLE', this.tabs[0])
  },
  mounted() {

  },
  methods: {
    getProgress(ev) {
      this.complete = ev
    },
    // getFileName(name) {
    //   this.fileName = name
    //   this.tempData.unshift({
    //     status: '上传中',
    //     id: guiId()
    //   })
    // },
    getFileData(obj) {
      this.materialData.unshift({
        status: '上传中',
        id: guiId()
      })
      this.fileData = obj
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
          file_key: file.videoData.body.key
          // title: this.fileName
        }
      }
      params = Object.assign(params, this.fileData)
      this.uploadData(params)
    },
    async uploadData(paramsObj) {
      let params = {
        media_type: this.activeName === '角标' || this.activeName === '视频模板' ? 'pic' : 'video',
        source: '个人',
        type: this.activeName
        // width: this.videoData.width,
        // height: this.videoData.height,
        // duration: this.videoData.duration || 0
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
      this.dialogVisible = false
      this.page = 1
      this.getInitData()
    },

    load() {
      if (this.materialData.length < this.count) {
        this.page++
        this.getInitData()
      }
    },
    async getInitData() {
      this.loading = true
      const query = {
        type: this.activeName
        // sub_type__in: this.activeName === '模板' ? '视频背景|字幕背景' : this.activeName,
        // media_type: this.media_type
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
      this.loading = false
      this.count = res.count
      this.materialData = this.materialData.concat(res.data)
    },
    async deleteMaterial(id) {
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
.additional-material {

  .tabs-wrap {
    display: flex;
    margin-bottom: 20px;

    .tabs-item {
      padding: 9px 10px;
      background: rgba(239,239,239,.8);
      border-radius: 4px;
      color: #404040;
      margin-right: 10px;
      font-size: 12px;
      line-height: 12px;
      cursor: pointer;
      transition: all .3s cubic-bezier(.645,.045,.355,1);

      &.active {
        background: #5675e8;
        color: #fff;
      }
    }
  }

  .upload-btn {
    // float: right;
    margin-bottom: 20px;
  }

  .transition-wrap {
    display: grid;
    grid-template-columns: repeat(4, 25%);  //同上
    grid-row-gap: 30px;
    margin: 0 -12px;
    max-height: calc(100vh - 200px);
    overflow-y: auto;
  }

  .empty-img {
    display: block;
    width: 140px;
    height: 172px;
    margin: 100px auto;
  }

  .progress-item {
    padding: 20% 10%;
    width: 100%;
    height: calc(100% - 10px);
    border-radius: 4px;
    margin-bottom: 10px;
    background: #f7f8f9;
  }
}
</style>
