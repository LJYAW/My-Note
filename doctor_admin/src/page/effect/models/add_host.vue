<template>
  <div class="addtitle_wrap">
    <div class="div_item">
      <span class="label_title">主持人缩略图:</span>
      <div>
        <upload :button_name="'上传图片'"
          @getFileUrl="getImgUrl" />
        <img :src="img" alt="" class="">
      </div>
    </div>
    <div class="div_item">
      <span class="mt-10px label_title">主持人名称:</span>
      <el-input v-model="name" placeholder="请输入主持人名称"></el-input>
    </div>
    <div class="div_item">
      <span class="label_title">视频片头:</span>
      <div>
        <upload :button_name="'上传视频'"
          @getFileUrl="getbannerVideo"
          :file_type='file_type' />
        <video :src="videoUrl" controls='controls'></video>
      </div>
    </div>
    <div class="div_item">
      <span class="label_title">主持人位置:</span>
      <div class="host_position">
        <el-radio-group v-model="form.position" class="mt-3px">
          <div class="position">
            <el-radio label="左边"></el-radio>
            <div class="d-flex">
              <span class="label_title">距离左边距离</span>
              <el-input v-model="form.left_l" placeholder=""></el-input>
              <span>px</span>
            </div>
            <div class="d-flex ml-10px">
              <span class="label_title">距离上边距离</span>
              <el-input v-model="form.left_t" placeholder=""></el-input>
              <span>px</span>
            </div>
          </div>
          <div class="position mt-10px">
            <el-radio label="右边"></el-radio>
            <div class="d-flex">
              <span class="label_title">距离左边距离</span>
              <el-input v-model="form.right_l" placeholder=""></el-input>
              <span>px</span>
            </div>
            <div class="d-flex ml-10px">
              <span class="label_title">距离上边距离</span>
              <el-input v-model="form.right_t" placeholder=""></el-input>
              <span>px</span>
            </div>
          </div>
        </el-radio-group>
      </div>
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
      file_type: 'all',
      img: '',
      form: {
        position: '左边',
        left_l: '',
        left_t: '',
        right_l: '',
        right_t: '',
      },
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    getbannerVideo(url, file) {
      this.videoUrl = url
    },
    getImgUrl(url, file) {
      this.img = url
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
    .position {
      display: flex;
      span {
        font-size: 12px;
        margin-top: 8px;
      }
      /deep/ .el-input__inner {
        width: 60px;
        height: 30px;
        margin-right: 8px;
      }
    }
    img {
      width: 70px;
      height: 70px;
      margin-top: 15px;
    }
    video {
      width: 200px;
      height: 120px;
      margin-top: 15px;
      border: 1px solid #dddddd;
    }
    .label_title {
      min-width: 86px;
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
