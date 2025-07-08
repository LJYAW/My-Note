<template>
  <div class="share_wrap">
    <el-form :model="form" label-width="100px">
      <el-form-item label="分享链接:">
        <div class="video_break">jvhwkjvhw</div>
      </el-form-item>
      <el-form-item label="分享二维码:">
        <img src="../../../assets/images/sucai_bg.png" alt="">
      </el-form-item>
      <el-form-item class="btn">
        <el-button type="primary" @click="determine" round size="small">确定</el-button>
      </el-form-item>
    </el-form>
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
      form: {
        project_name: '',
      },
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    determine() {
      this.$layer.close(this.layerid)
    },
  },
  created() {},
  mounted() {},
}
</script>

<style lang="scss" scoped>
@import './index.scss';
.share_wrap {
  width: 100%;
  padding: 10px;
  img {
    width: 150px;
    height: 150px;
  }
  .btn {
    margin-top: 30px;
  }
}
</style>
