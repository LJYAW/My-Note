<template>
  <div class="release_wrap">
    <el-form :model="form" label-width="80px">
      <el-form-item label="审批人">
        <el-input v-model="form.project_name" placeholder="项目名称"></el-input>
      </el-form-item>
      <el-form-item label="视频比例">
        <div class="video_break">jvhwkjvhw</div>
      </el-form-item>
      <el-form-item label="视频连接">
        <div class="video_break">vbjvbvkevjv</div>
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
.release_wrap {
  width: 100%;
  padding: 10px;
}
</style>
