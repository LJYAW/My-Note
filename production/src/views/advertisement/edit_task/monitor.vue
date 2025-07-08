<!--
 * @Author: your name
 * @Date: 2021-04-09 09:59:49
 * @LastEditTime: 2021-04-12 21:28:29
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/edit_task/monitor.vue
-->
<template>
  <div>
    <div class="top-details">
      <el-form :inline="true">
        <el-form-item label="频道:">
          <el-select v-model="form.channel_id" clearable filterable placeholder="请选择频道名称">
            <el-option
              v-for="item in channelData"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="日期">
          <el-date-picker
            v-model="form.date"
            type="date"
            placeholder="日期"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
        <base-btn size="big" @click.native="getRecordList">搜索</base-btn>
      </el-form>
    </div>
    <div class="details-table">
      <el-table
        v-loading="loading"
        :data="tableData"
        class="table-list"
        border
        style="margin-top:15px"
        :default-sort="{prop: 'incomplete_num', order: 'descending'}"
        @sort-change="changeTableSort"
      >
        <el-table-column prop="channel_name" label="频道名称" />
        <el-table-column prop="incomplete_num" label="未完成任务数" sortable />
        <el-table-column prop="start_time" label="任务时间" sortable />
        <el-table-column prop="updated_at" label="更新时间" />
      </el-table>
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
import { mapGetters } from 'vuex'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      tableData: [],
      loading: false,
      total: null,
      form: {
        channel_id: '',
        date: null
      },
      page: 1,
      limit: 10,
      order: 'num_desc'
    }
  },
  computed: {
    ...mapGetters([
      'channelData'
    ])
  },
  watch: {

  },
  created() {
    this.getRecordList()
  },
  mounted() {

  },
  methods: {
    getRecordList() {
      this.loading = true
      var params = JSON.parse(JSON.stringify(this.form))
      params.order_by = this.order
      params.is_manage = false
      params.page = this.page
      params.limit = this.limit
      this.$get('/ads-task/monitor-list', params).then((res) => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.loading = false
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    handleCurrentChange(val) {
      this.page = val
      this.getRecordList()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getRecordList()
    },
    changeTableSort(column) {
      const order = column.order === 'ascending' ? 'asc' : 'desc'
      const prop = column.prop === 'incomplete_num' ? 'num' : 'time'
      this.order = order + '_' + prop
      this.getRecordList()
    }
  }
}
</script>

<style scoped lang="scss">

</style>
