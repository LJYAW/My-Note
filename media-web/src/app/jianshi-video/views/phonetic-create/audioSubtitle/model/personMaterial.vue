<template>
  <div class="person-material">
    <Upload
      btn-name="上传素材"
      class="upload-box"
      file-type="image/gif,image/jpg,image/png,image/jepg,video/mp4,video/mpeg"
      @getProgress="getProgress"
      @getBosKey="getBosKey"
      @getFileName="getFileName"
      @getFileData="getFileData"
    />
    <div class="materail-list">
      <ul v-infinite-scroll="load" infinite-scroll-immediate>
        <li
          v-for="(item,index) in materailList"
          :key="index"
          :class="tabIndex===index?'activeClass':''"
          @click="chooseMateril(item,index)"
        >
          <VideoCard v-if="item.media_type==='video'" :key="item.id" :cover-url="item.cover_url" :video-url="item.file_url" />
          <img v-if="item.media_type==='pic'" :src="item.file_url" alt="" class="cover-img">
          <div v-if="item.progress === '上传中'" class="progress-item">
            <el-progress :text-inside="true" :stroke-width="13" :percentage="complete" />
          </div>
        </li>
      </ul>
    </div>
    <div class="btns">
      <el-button type="primary" size="mini" @click="completeData">完成</el-button>
    </div>
  </div>
</template>

<script>
import Upload from '../../components/upload'
import VideoCard from '@/app/jianshi-video/components/VideoCard'
import { getMaterial, posttMaterial } from '@/app/jianshi-video/api/source-material/index.js'

export default {
  components: {
    Upload, VideoCard
  },
  props: {

  },
  data() {
    return {
      materailList: [],
      tabIndex: '',
      itemData: {},
      complete: 0,
      fileName: '',
      duration: 0,
      page: 1,
      limit: 20,
      count: 0,
      width: '',
      height: ''
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getMaterailData()
  },
  mounted() {

  },
  methods: {
    load() {
      if (this.materailList.length < this.count) {
        this.page++
        this.getMaterailData()
      }
    },
    completeData() {
      this.$emit('resetData', this.itemData)
    },
    getProgress(ev) {
      this.complete = ev
    },
    getFileData(obj) {
      this.width = obj.width
      this.height = obj.height
      if (obj.duration) {
        this.duration = obj.duration
      }
    },
    getBosKey(file) {
      let params
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
    getFileName(name) {
      this.fileName = name
      this.materailList.unshift({
        progress: '上传中'
      })
    },
    async uploadData(obj) {
      const params = {
        file_key: obj.file_key,
        media_type: obj.type ? 'video' : 'pic',
        title: this.fileNameame,
        source: '个人',
        type: '个人',
        width: this.width || null,
        height: this.height || null,
        duration: this.duration || 0
      }
      if (obj.type === 'video') params.cover_key = obj.cover_key
      const { err, res } = await posttMaterial(params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.materailList.splice(0, 1)
      this.$message({
        message: '上传成功!',
        type: 'success'
      })
      this.page = 1
      this.materailList = []
      await this.getMaterailData()
      this.complete = 0
    },
    chooseMateril(item, index) {
      this.tabIndex = index
      this.itemData = item
    },
    // 获取素材列表
    async getMaterailData() {
      const params = {
        page: this.page,
        limit: this.limit,
        query: 'source:个人,type:个人'
      }
      const { err, res } = await getMaterial(params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.materailList = this.materailList.concat(res.data)
      this.count = res.count
    }
  }
}
</script>

<style scoped lang="scss">
.person-material {
  position: relative;
  height: 516px;

  .upload-box {
    position: absolute;
    right: 0px;
    top: -60px;
  }

  .materail-list {
    height: 480px;
    overflow: auto;

    ul {
      display: flex;
      flex-wrap: wrap;

      li {
        position: relative;
        width: 252px;
        height: 142px;
        border: 1px solid #ddd;
        margin-right: 10px;
        border-radius: 4px;
        cursor: pointer;
        margin-top: 10px;

        .progress-item {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%,-50%);
          padding: 0 6px;
          box-sizing: border-box;
          width: 100%;
          z-index: 100;
          width: 80%;
        }

        .video-poster {
          width: 100%;
          height: 100%;
        }

        .cover-img {
          width: 100%;
          height: 100%;
          object-fit: contain;
        }
      }

      .activeClass {
        border: 1px solid #5675e8;
      }
    }
  }

  .btns {
    width: 100%;
    text-align: center;
  }
}
</style>
