<!--
 * @Author: your name
 * @Date: 2021-10-13 15:22:14
 * @LastEditTime: 2021-10-13 16:11:56
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/order-info/consumption.vue
-->
<template>
  <div class="consumption">
    <div class="search-wrap">
      <el-date-picker
        v-model="form.date"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        size="mini"
        value-format="yyyy-MM-dd hh:mm:ss"
      />
      <base-btn size="mini" class="btn" @click="search">查询</base-btn>
      <base-btn size="mini" class="export-btn">导出excel</base-btn>
    </div>
    <el-table :data="tableData" border>
      <el-table-column label="订单编号" prop="id" />
      <el-table-column label="消耗能量点" prop="vals" />
      <el-table-column label="视频名称" prop="titles" />
      <el-table-column label="消费来源" prop="source" />
      <el-table-column label="创建时间" prop="create_time" />
    </el-table>
    <div class="page-list">
      <base-pag
        :total="total"
        :limit="limit"
        :page="page"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </div>
  </div>
</template>

<script>
import setQueryParams from '@/utils/setQueryParams'

export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      form: {
        date: ''
      },
      tableData: [],
      total: null,
      limit: 10,
      page: 1
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getOrderData()
  },
  mounted() {

  },
  methods: {
    // 获取订单列表
    async getOrderData() {
      const query = {
        create_time__gt: this.form.date ? this.form.date[0] : '',
        create_time__lt: this.form.date ? this.form.date[1] : '',
        status: this.form.status
      }
      const params = {
        limit: this.limit,
        page: this.page,
        query: setQueryParams(query),
        sortby: 'id',
        order: 'desc'
      }
      const { err, res } = await this.$get('/energyorder', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.tableData = res.data
      this.total = res.count
    },
    search() {
      this.page = 1
      this.getOrderData()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getOrderData()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getOrderData()
    }
  }
}
</script>

<style scoped lang="scss">
.search-wrap {
  margin: 20px 0;
  display: flex;
  position: relative;

  .el-date-editor.el-input__inner {
    width: 250px;
    min-width: 300px;
  }

  .btn {
    margin-left: 20px;
  }

  .export-btn {
    position: absolute;
    right: 0;
  }
}
</style>
