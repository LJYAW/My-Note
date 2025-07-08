<!--
 * @Author: your name
 * @Date: 2021-07-23 16:52:32
 * @LastEditTime: 2021-09-28 10:24:26
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/frame/model/imgShow.vue
-->
<template>
  <div class="img-wrap">
    <Upload
      :btn-name="`上传${type}`"
      class="upload-position"
      file-type="image/gif,image/jpg,image/png,image/jepg"
      @getProgress="getProgress"
      @getFileData="getFileData"
      @getBosKey="getBosKey"
    />
    <div v-infinite-scroll="load" class="img-box" infinite-scroll-immediate>
      <div class="img-list">
        <div
          v-for="(item,index) in TempImgList"
          :key="index"
          class="item-list"
          :class="activeIndex===index?'activeClass':''"
          @click="chooseImg(item,index)"
        >
          <img v-if="item.file_url" :src="item.file_url" alt="">
          <div v-if="item.progress === '上传中'" class="progress-item">
            <el-progress :text-inside="true" :stroke-width="13" :percentage="complete" />
          </div>
        </div>
      </div>
    </div>
    <div class="btns">
      <el-button type="primary" @click="determine">确定</el-button>
    </div>
  </div>
</template>

<script>
import Upload from '../../../../components/upload.vue'
import { getMaterial, posttMaterial } from '@/app/jianshi-video/api/source-material/index.js'
export default {
  components: {
    Upload
  },
  props: {
    type: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      submitLoading: false,
      activeIndex: '',
      itemData: {},
      complete: 0,
      fileName: '',
      TempImgList: [],
      page: 1,

      fileData: {}
    }
  },
  computed: {
  },
  watch: {
  },
  created() {
    this.getinitData()
  },
  mounted() {

  },
  methods: {
    load() {
      if (this.TempImgList.length < this.count) {
        this.page++
        this.getinitData()
      }
    },
    async getinitData() {
      const params = {
        query: 'media_type:pic,type:视频模板,sub_type:横屏',
        sortby: 'id',
        order: 'desc',
        limit: 30,
        page: this.page
      }
      const { err, res } = await getMaterial(params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      const { data } = res
      this.count = res.count
      this.TempImgList = this.TempImgList.concat(data)
    },
    getProgress(ev) {
      this.complete = ev
    },
    // getFileName(name) {
    //   this.fileName = name
    //   this.TempImgList.unshift({
    //     progress: '上传中'
    //   })
    // },
    getFileData(obj) {
      this.TempImgList.unshift({
        progress: '上传中'
      })
      this.fileData = obj
    },
    chooseImg(item, index) {
      this.activeIndex = index
      this.itemData = item
    },
    async getBosKey(file) {
      const { key } = file.fileData.body
      this.uploadData(key, this.fileName)
    },
    async uploadData(key, name) {
      let params = {
        file_key: key,
        media_type: 'pic',
        sub_type: '横屏',
        // title: name,
        type: '视频模板'
      }
      params = Object.assign(params, this.fileData)
      const { err, res } = await posttMaterial(params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.TempImgList.splice(0, 1)
      this.$message({
        message: '上传成功!',
        type: 'success'
      })
      this.$emit('resetData')
      this.TempImgList = []
      await this.getinitData()
      this.complete = 0
    },
    determine() {
      this.$emit('saveData', this.itemData)
    }
  }
}
</script>

<style scoped lang="scss">
.img-wrap {
  height: 516px;
  position: relative;

  .upload-position {
    position: absolute;
    right: 0;
    top: -60px;
  }

  .img-box {
    height: 450px;
    overflow: auto;
  }

  .img-list {

    display: flex;
    flex-wrap: wrap;

    .activeClass {
      border: 1px solid blue !important;
    }

    .item-list {
      position: relative;
      width: 96px;
      height: 54px;
      margin-right: 10px;
      border: 1px solid #ddd;
      cursor: pointer;
      margin-bottom: 10px;

      .progress-item {
        position: absolute;
        top: 22px;
        left: 0;
        padding: 0 6px;
        box-sizing: border-box;
        width: 100%;
        z-index: 100;
        width: 80%;
      }

      img {
        width: 100%;
        height: 100%;
        background: #f9f9f9;
      }
    }
  }

  .btns {
    width: 100%;
    text-align: center;
    margin-top: 30px;

    button {
      width: 300px;
      height: 30px;
      padding: 0;
      font-size: 14px;
    }
  }
}
</style>
