<template>
  <div class="addimg_wrap">
    <div class="div_item">
      <span>画面模板:</span>
      <div>
        <upload :button_name="'上传图片'"
          @getFileUrl="getbannerUrl" />
        <img :src="img" alt="" class="">
      </div>
    </div>
    <div class="div_item">
      <span class="mt-10px">模板名称:</span>
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
      img: '',
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    getbannerUrl(url, file) {
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
.addimg_wrap {
  width: 100%;
  padding: 15px;
  .div_item {
    display: flex;
    margin-top: 20px;
    img {
      width: 468px;
      height: 263px;
      margin-top: 15px;
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
