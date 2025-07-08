<template>
  <div class="examine_wrap">
    <div style="padding:20px 20px 60px 20px">
      <div v-if="type=='have_tab'">
        <div class="title">{{params.title}}</div>
        <div class="describe">{{params.question}}</div>
      </div>
      <video :src="params.video_url" class="videoStyle" controls="controls" v-if="params.video_url"></video>
      <div class="videoError" v-else>糟糕，视频播放出错！请刷新页面重试或者换个视频看看~~</div>
    </div>
    <div class="btns" v-if="this.status=='待审核'">
      <el-button type="primary" plain @click="adopt()">审核通过</el-button>
      <el-button type="primary" round @click="no_adopt()">审核不通过</el-button>
    </div>
  </div>
</template>

<script>
import remarks from './remarks'
export default {
  name: '',
  props: {
    layerid: {
      type: String,
      default: ''
    },
    item: {
      type: String
    },
    _this: {
      type: Object
    },
    type: '',
    status: {
      type: String,
      default: ''
    }
  },
  data() {
    return {}
  },
  components: { remarks },
  computed: {
    params() {
      return JSON.parse(this.item)
    }
  },
  watch: {},
  methods: {
    adopt() {
      var path = ''
      var params = {}
      if (this.type == 'have_tab') {
        params = {
          task_question_id: this.params.task_question_id,
          approve_status: '审核通过'
        }
        path = '/admin/task/approve-task-question-video'
      } else {
        params = {
          task_video_id: this.params.task_video_id,
          status: '审核通过'
        }
        path = '/admin/task/approve-task-video'
      }
      this.$post(path, params).then(
        res => {
          if (res.data.code == '0000') {
            this.$message({
              message: '审核通过',
              type: 'success'
            })
            this._this.getList()
            this.$layer.close(this.layerid)
          } else {
            this.$message({
              message: res.data.msg,
              type: 'warning'
            })
          }
        },
        err => {
          console.log(err)
        }
      )
    },
    no_adopt() {
      this.$layer.close(this.layerid)
      let obj = {
        content: remarks,
        title: '审核不通过',
        width: '800px',
        height: '486px',
        data: {
          item: this.item,
          _this: this._this,
          type: this.type
        }
      }
      this.$layerIfream(obj)
    }
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
.examine_wrap {
  position: relative;
  width: 100%;
  height: 100%;
  .describe {
    font-size: 12px;
    color: #999999;
    margin-bottom: 20px;
    line-height: 20px;
    word-break: break-all;
  }
  .title {
    font-size: 16px;
    color: #333333;
    font-weight: bold;
    margin-bottom: 20px;
  }
  .videoStyle {
    width: 100%;
    height: 380px;
    background: #000;
  }
  .videoError {
    width: 100%;
    height: 380px;
    background: black;
    color: #ffffff;
    font-size: 14px;
    text-align: center;
    line-height: 360px;
  }
  .btns {
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    height: 60px;
    background: #f8f8f8;
    line-height: 60px;
    /deep/ .el-button.is-plain {
      background-color: #f2f6fc;
      color: #2b79df;
      border: 1px solid #2b79df;
    }
  }
}
</style>
