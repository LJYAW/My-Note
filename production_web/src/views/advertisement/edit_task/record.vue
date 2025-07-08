<!--
 * @Author: your name
 * @Date: 2021-04-09 10:35:29
 * @LastEditTime: 2021-04-09 11:42:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/edit_task/record.vue
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
        <el-form-item label="TID:">
          <el-input v-model="form.tid" clearable placeholder="请输入TID" />
        </el-form-item>
        <base-btn size="big" @click.native="search">搜索</base-btn>
      </el-form>
    </div>
    <el-table v-loading="loading" :data="tableData" class="table-list" border style="margin-top:15px">
      <el-table-column label="TID" prop="task_id" width="80" />
      <el-table-column prop="channel_name" label="频道名称" />
      <el-table-column prop="date" label="任务时间" />
      <el-table-column label="任务时间段" width="180">
        <template slot-scope="scope">
          {{ scope.row.start_time }}-
          {{ scope.row.end_time }}
        </template>
      </el-table-column>
      <el-table-column label="片段数" prop="num" width="100" />
      <el-table-column prop="updated_at" label="完成时间" width="180" />
      <el-table-column label="片段明细">
        <template slot-scope="scope">
          <base-btn class="start-task" type="text" @click="edit(scope.row.task_id)">查看片段</base-btn>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-list">
      <base-pag :total="total" :limit="form.limit" :page="form.page" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
    </div>
    <base-dialog
      :show="show"
      title="查看片段"
      width="600"
      @close="close"
    >
      <el-table :data="ad_points" class="table-list" border style="margin-top:15px">
        <el-table-column label="标题" prop="title" />
        <el-table-column prop="tags" label="分类" />
        <el-table-column label="开始时间">
          <template slot-scope="scope">
            {{ getTime(scope.row.start_time) }}
          </template>
        </el-table-column>
        <el-table-column label="结束时间">
          <template slot-scope="scope">
            {{ getTime(scope.row.end_time) }}
          </template>
        </el-table-column>
      </el-table>
    </base-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import dateFun from '@/utils/time.js'
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
        channel_id: null,
        tid: null,
        page: 1,
        limit: 10
      },
      show: false,
      ad_points: []
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

  },
  mounted() {
    this.getRecordList()
  },
  methods: {
    handleSizeChange(val) {
      this.form.limit = val
      this.getRecordList()
    },
    handleCurrentChange(val) {
      this.form.page = val
      this.getRecordList()
    },
    close() {
      this.show = false
    },
    edit(tid) {
      this.getAdsList(tid)
    },
    getAdsList(id) {
      this.$get('/ads-task/ads-list', { tid: id }).then((res) => {
        this.show = true
        this.ad_points = res.data
      }).catch((error) => {
        this.$message(error.msg)
      })
    },
    getRecordList() {
      this.loading = true
      var params = JSON.parse(JSON.stringify(this.form))
      params.channel_id = params.channel_id || null
      params.tid = params.tid || null
      this.$get('/ads-task/edit-list', params).then((res) => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.loading = false
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    getTime(val) {
      return dateFun.convert(val, 'y-m-d h:m:s').split(' ')[1]
    }
  }
}
</script>

<style scoped lang="scss">

</style>
