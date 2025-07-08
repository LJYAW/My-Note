<!--
 * @Author: your name
 * @Date: 2021-07-27 10:18:48
 * @LastEditTime: 2021-09-28 10:26:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/additional/model/playVideo.vue
-->
<template>
  <div class="aduio-list-model">
    <div slot="title" class="header-title">
      <Upload
        custom-jiaobiao
        :file-type="fileType"
        class="upload"
        @getProgress="getProgress"
        @getFileData="getFileData"
        @getBosKey="getBosKey"
      />
      <!-- <base-upload :file-type="fileType" :disabled="submitLoading" :button-name="title" @getFileUrl="getFileUrl" /> -->
      <!-- <div class="aduio-message">注：建议上传50px高度的透明背景png图</div> -->
    </div>
    <div class="aduio-list">
      <div class="search">
        <el-input
          v-model="keyWord"
          placeholder="请输入内容"
        >
          <i slot="suffix" class="el-input__icon el-icon-search icon-search" @click="search()" />
        </el-input>
      </div>
      <div class="adiuo-box">
        <ul v-cloak>
          <li v-for="(item,index) in tempData" :key="item.id">
            <i class="icon-detelte el-icon-delete" @click="deteleData(item)" />
            <div class="cover-img" :class="tabIndex===index?'activeClass':''" @click="tabCLick(item,index)">
              <div v-if="item.status === '上传中'" class="progress-item">
                <el-progress :text-inside="true" :stroke-width="13" :percentage="complete" />
              </div>
              <div>
                <!-- 角标 -->
                <img v-if="title==='上传角标'" :src="item.file_url" alt="">
                <!-- 片头、片尾 -->
                <div v-else class="video-box">
                  <VideoCard :key="item.id" :cover-url="item.cover_url" :video-url="item.resource_url" />
                </div>
              </div>
            </div>
            <div class="aduio-title">{{ item.title }}</div>
          </li>
        </ul>
      </div>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitData()">确定</el-button>
    </span>
  </div>
</template>

<script>
import Upload from '../../../../components/upload.vue'
import VideoCard from '@/app/jianshi-video/components/VideoCard'
import guiId from '../../../../../../utils/guid'

export default {
  components: {
    VideoCard,
    Upload
  },
  props: {
    tag: {
      type: String,
      default: ''
    },
    subType: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    },
    fileType: {
      type: String,
      default: ''
    },
    temList: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      itemData: {},
      complete: 0,
      tabIndex: '',
      keyWord: '',
      submitLoading: false,
      tempData: [],
      fileData: {}
    }
  },
  computed: {
  },
  watch: {
    temList: {
      handler(arr) {
        this.initData(arr)
      },
      deep: true,
      immediate: true
    }
  },
  created() {

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
      this.tempData.unshift({
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
        media_type: this.title === '上传角标' ? 'pic' : 'video',
        source: '个人',
        type: this.subType
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
      this.tempData.splice(0, 1)
      this.$message({
        message: '上传成功!',
        type: 'success'
      })
      this.$emit('resetData')
      this.complete = 0
    },
    // 删除
    deteleData(item) {
      this.$deleteRun(`/materials/${item.uuid}`).then(res => {
        console.log(res)
        if (res.res.code === '200') {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.$emit('resetData')
        } else {
          this.$message.error(res.data.msg)
        }
      })
    },
    // 初始化转换数据
    initData(data) {
      this.tempData = JSON.parse(JSON.stringify(data))
    },
    // 点击选中
    tabCLick(item, index) {
      this.tabIndex = index
      this.itemData = item
    },
    // 搜索
    search() {
      if (this.keyWord) {
        this.tempData = this.tempData.filter(item => {
          return item.title.match(this.keyWord)
        })
      } else {
        this.initData(this.temList)
      }
    },
    submitData() {
      const data = JSON.stringify(this.itemData) === '{}' ? null : this.itemData
      this.$emit('resetData', data)
    }
  }
}
</script>

<style scoped lang="scss">
[v-cloak] {
  display: none !important;
}

img {
  object-fit: cover;
}

.aduio-list-model {
  position: relative;
}

.upload {
  position: absolute;
  top: -60px;
  right: 0;
}

@import "../model/index.scss"
</style>
