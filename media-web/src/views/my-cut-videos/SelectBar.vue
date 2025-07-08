<!--
 * @Author: your name
 * @Date: 2021-08-12 11:37:04
 * @LastEditTime: 2021-08-12 19:39:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/my-cut-videos/SelectBar.vue
-->
<template>
  <div class="select-wrap">
    <el-input v-model.trim="keywords" size="mini" clearable>
      <i slot="suffix" class="el-input__icon el-icon-search" @click="handelSearch" />
    </el-input>
    <div class="videoSum">共有{{ count }}个内容</div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    count: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      keywords: ''
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    handelSearch() {
      this.$emit('search', this.keywords)
    }
  }
}
</script>

<style scoped lang="scss">
.select-wrap {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 30px 0;

  .el-input {
    width: 365px;

    ::v-deep input {
      height: 30px;
      line-height: 30px;
    }
  }

  .videoSum {
    font-size: 12px;
    color: #bababa;
  }
}
</style>
