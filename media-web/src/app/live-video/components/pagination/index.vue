<template>
  <div style="text-align: center;">
    <el-pagination
      style="padding-top: 20px;"
      background
      :hide-on-single-page="true"
      :page-sizes="[10, 30, 50]"
      :page-size="pageSize"
      layout="prev, pager, next"
      :current-page="page"
      :total="totalPage"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>
<script>

export default {
  components: {},
  props: {
    source: {
      type: Function,
      required: true
    },
    page: {
      required: true,
      type: Number,
      default: 0
    },
    pageSize: {
      required: true,
      type: Number,
      default: 0
    },
    totalPage: {
      required: true,
      type: Number,
      default: 0
    }
  },
  data() {
    return {}
  },
  mounted() {

  },
  beforeDestroy() {

  },
  methods: {
    getData(page, pageSize) {
      this.source(page, pageSize)
    },
    handleSizeChange: function(pageSize) {
      this.getData(1, pageSize)
    },
    handleCurrentChange: function(page) {
      this.getData(page)
    }
  }
}
</script>

<style scoped lang="scss">

</style>
