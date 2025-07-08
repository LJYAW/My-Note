<!--
 * @Author: your name
 * @Date: 2021-09-03 15:20:12
 * @LastEditTime: 2021-10-15 17:57:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/create-center/intellectCreate/components/audioSubtitle/contentEdit/contentLeft.vue
-->
<template>
  <div class="content-left">
    <div class="upload-box">
      <base-upload
        :button-name="!fileName?'点击选取音频文件':fileName"
        class="upload-content"
        file-type="audio/mpeg,audio/aac,audio/wav,audio/m4a"
        :disabled="processShow"
        @getFileUrl="getFileUrl"
      />
      <el-button type="primary" class="btns" :loading="aduioLoading" :disabled="taskId===''" @click="aduioTurnsubtitle">音频转字幕</el-button>
    </div>
    <div class="content-list">
      <div v-if="processShow" class="progress-box">
        <el-progress :text-inside="true" :stroke-width="13" :percentage="complete" class="progress-item" />
      </div>
      <div class="list">
        <div v-for="(item,index) in contentList" :key="index" class="item-content" @click="edit(item,index)">{{ item.txt }}</div>
      </div>
    </div>
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="588px" title="识别修改">
      <AduioEdit
        :item-data="itemData"
        :audio-url="audioUrl"
        @resetData="resetData"
        @close="close"
      />
    </base-dialog>
  </div>
</template>

<script>
import AduioEdit from '../model/aduioEdit'
import sdkUploadFile from '@/utils/sdk-upload-file.js'

export default {
  components: {
    AduioEdit
  },
  props: {
    showData: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      itemData: {},
      timer: null,
      audioUrl: '',
      fileName: '',
      dialogVisible: false,
      taskId: '',
      aduioLoading: false,
      contentList: [],
      complete: 0,
      processShow: false,
      tabindex: '',
      duration: 0,
      uploadComplete: 0,
      audioFileKey: ''
    }
  },
  computed: {
    routerData() {
      return this.$route.query
    }
  },
  watch: {
    showData: {
      handler(newName, oldName) {
        if (newName) {
          this.getInitData()
        }
      },
      immediate: true
    },
    duration(val) {
      if (val) {
        this.$emit('resetData', val)
      }
    },
    audioFileKey(val) {
      if (val) {
        this.$emit('resetData', val)
      }
    },
    contentList(val) {
      if (val.length > 0) {
        this.$emit('resetData', val)
      }
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {
    // 获取页面初始化数据
    async getInitData() {
      await this.getAduioList(this.routerData.taskId)
      this.audioFileKey = this.routerData.audioFileKey
      this.fileName = this.routerData.title
      this.duration = Number(this.routerData.duration)
    },
    // 上传音频文件
    async getFileUrl(url, file) {
      this.audioUrl = url
      this.fileName = file.name
      this.complete = 0
      this.contentList = []
      this.processShow = false
      this.getTimes(file)
      const { body } = await sdkUploadFile(file)
      this.audioFileKey = body.key

      this.uploadAduio(file)
    },
    // 获取时长
    getTimes(file) {
      const url = URL.createObjectURL(file)
      var audioElement = new Audio(url)
      audioElement.addEventListener('loadedmetadata', (_event) => {
        this.duration = parseInt(audioElement.duration * 1000)
      })
    },
    // 上传数据
    async uploadAduio(file) {
      var formData = new FormData()
      formData.append('audio', file)
      const { err, res } = await this.$post('https://tjzm.tmsx.net/v1/audio-creation/analysis-audio', formData)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.taskId = res.data.taskId
    },
    // 音频转字幕
    aduioTurnsubtitle() {
      this.getAduioList(this.taskId)
    },
    // 获取音频文件数据
    getAduioList(taskId) {
      this.timer = setTimeout(async() => {
        if (this.complete >= 100) {
          this.timer = null
          clearInterval(this.timer)
          return
        }
        this.complete += 1
        await this.getAudioData(taskId)
        this.getAduioList(taskId)
      }, 1000)
    },
    async getAudioData(taskId) {
      this.aduioLoading = true
      this.processShow = true
      const { res, err } = await this.$get('https://tjzm.tmsx.net/v1/audio-creation/analysis-audio-result', { taskId: taskId })
      if (err) {
        return
      }
      if (res.data.done) {
        this.complete = 100
        this.aduioLoading = false
        this.contentList = res.data.result
        this.contentList.forEach((item, index) => {
          item.start_time = Number(item.start_time)
          item.end_time = Number(item.end_time)
        })
        this.processShow = false
        this.timer = null
        clearTimeout(this.timer)
      }
    },
    // 编辑音频
    edit(item, index) {
      this.dialogVisible = true
      this.itemData = item
      this.tabindex = index
    },
    // 关闭弹框
    close() {
      this.dialogVisible = false
      this.itemData = {}
    },
    // 更新数据
    resetData(obj) {
      this.contentList[this.tabindex].txt = obj.txt
      this.dialogVisible = false
      this.$emit('resetData', '')
    }
  }
}
</script>

<style scoped lang="scss">
.content-left {
  flex: 1;
  max-width: calc(100% - 484px);

  .upload-box {
    width: 100%;
    display: flex;

    .upload-content {
      flex: 1;
      height: 30px;
      overflow: auto;
      background: #f7f8f9;
      border-radius: 4px 0px 0px 4px;
      line-height: 30px;
      padding-left: 20px;
      z-index: 100;

      ::v-deep .btn-style {
        color: rgba(194, 196, 199, 1);
        font-size: 14px;
        font-weight: bold;
      }
    }

    .btns {
      width: 180px;
      height: 30px;
      background: rgba(86, 117, 232, 1);
      font-size: 12px;
      padding: 0;
    }
  }

  .content-list {
    position: relative;
    height: 220px;
    background: #f7f8f9;
    border-radius: 4px;
    overflow: auto;
    margin-top: 10px;
    padding: 20px;
    box-sizing: border-box;

    .progress-box {
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, .2);
      z-index: 1000;

      .progress-item {
        position: absolute;
        width: 300px;
        height: 30px;
        left: 50%;
        top: 50%;
        transform: translate(-50%,-50%);
      }
    }

    .list {
      display: flex;
      flex-wrap: wrap;

      .item-content {
        width: auto;
        height: 20px;
        line-height: 5px;
        padding: 8px;
        box-sizing: border-box;
        background: #e5eaff;
        font-weight: 400;
        color: #404040;
        font-size: 12px;
        margin-right: 10px;
        margin-top: 15px;
        cursor: pointer;

        &:last-child {
          margin-right: 0;
        }
      }
    }

  }
}
</style>
