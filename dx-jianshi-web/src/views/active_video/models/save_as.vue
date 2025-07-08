<template>
  <div class="save_wrap">
    <div class="save_as">
      <span>另存为命名:</span>
      <el-input v-model="save_name" placeholder="项目名称-副本"></el-input>
    </div>
    <div class="btn">
      <el-button type="primary" round size="small" @click="determine()" :disabled="save_name == '' ? true : false">确定</el-button>
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
      save_name: '',
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
.save_wrap {
  padding: 20px;
  .save_as {
    display: flex;
    span {
      display: block;
      width: 120px;
      margin-top: 10px;
    }
  }
  .btn {
    margin-top: 30px;
  }
}
</style>
