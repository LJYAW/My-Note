<template>
  <div class="addtitle_wrap">
    <div class="div_item">
      <span>视频片头:</span>
      <div>
        <upload :button_name="'上传视频'"
          @getFileUrl="getbannerVideo"
          :file_type='file_type' />
        <video :src="videoUrl" controls='controls'></video>
      </div>
    </div>
    <div class="div_item">
      <span class="mt-10px">片头名称:</span>
      <el-input v-model="name" placeholder="请输入模板名称"></el-input>
    </div>
    <div class="btns">
      <el-button type="primary" round size="small" @click="determine()">确定</el-button>
    </div>
  </div>
</template>
<script>
export default {
  name: '',
  props: {
    layerid: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      name: '',
      videoUrl: '',
      file_type: 'al',
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    getbannerVideo(url, file) {
      this.videoUrl = url
    },
    determine() {
      this.$layer.close(this.layerid)
    },
  },
  created() {},
  mounted() {},
}
</script>

<style lang="scss" scoped>
.addtitle_wrap {
  width: 100%;
  padding: 15px;
  .div_item {
    display: flex;
    margin-top: 20px;
    video {
      width: 468px;
      height: 263px;
      margin-top: 15px;
      border: 1px solid #dddddd;
    }
    span {
      min-width: 70px;
    }
  }
  .btns {
    margin-top: 20px;
    text-align: center;
    /deep/ .el-input {
      margin-top: 0;
      /deep/ .el-input__inner {
        height: 30px;
      }
    }
  }
}
</style>
