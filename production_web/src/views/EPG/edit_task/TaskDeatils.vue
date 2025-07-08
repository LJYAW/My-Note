<!--
 * @Author: your name
 * @Date: 2021-01-13 18:07:27
 * @LastEditTime: 2021-03-27 18:27:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/edit_task/task/task_deatils.vue
-->
<template>
  <div>
    <div class="task-details">
      <div class="top-title">
        任务明细
      </div>
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
            <el-input v-model="form.task_id" clearable placeholder="请输入TID" />
          </el-form-item>
          <base-btn size="big" @click.native="search">搜索</base-btn>
        </el-form>
      </div>
      <div class="details-table">
        <el-tabs v-model="activeName">
          <el-tab-pane label="编辑记录" name="编辑记录">
            <el-table v-loading="loading" :data="tableData" class="table-list" border style="margin-top:15px">
              <el-table-column label="TID" prop="task_id" />
              <el-table-column prop="channel_name" label="频道名称" />
              <el-table-column prop="time" label="任务时间段">
                <template slot-scope="scope">
                  {{ scope.row.start_date_time }}<br>
                  {{ scope.row.end_date_time }}
                </template>
              </el-table-column>
              <el-table-column label="切点数">
                <template slot-scope="scope">
                  {{ scope.row.epg_points.length || 0 }}
                </template>
              </el-table-column>
              <el-table-column prop="updated_at" label="提交时间" />
              <el-table-column label="切点明细">
                <template slot-scope="scope">
                  <a class="start-task" @click="edit(scope)">查看明细</a>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div class="pagination-list">
        <base-pag :total="total" :limit="form.limit" :page="form.page" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
      </div>
      <base-dialog
        :show="show"
        title="切点明细"
        width="400"
        @close="close"
      >
        <el-table :data="epg_points" class="table-list" border style="margin-top:15px">
          <el-table-column label="类型" prop="task_id">
            <template slot-scope="scope">
              {{ scope.row.type+scope.row.position }}
            </template>
          </el-table-column>
          <el-table-column prop="start_date_time" label="切点" />
          <el-table-column prop="title" label="标题" />
        </el-table>
      </base-dialog>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  components: {},
  data() {
    return {
      keywords: '',
      tableData: [],
      loading: false,
      activeName: '编辑记录',
      total: null,
      form: {
        channel_id: null,
        task_id: null,
        // processing_require: '',
        page: 1,
        limit: 10
      },
      show: false,
      epg_points: []
    }
  },
  computed: {
    ...mapGetters([
      'channelData',
      'itemData'
    ])
  },
  watch: {
    'form.channel_id'(val) {
      this.getItemList()
      this.form.item_id = null
    }
  },
  created() {
    if (!this.channelData.length) {
      this.$store.dispatch('channel/getChannelData')
    }
    this.getItemList()
    this.getRecordList()
  },
  mounted() {

  },
  methods: {
    search() {
      this.form.page = 1
      this.getRecordList()
    },
    edit(scope) {
      this.show = true
      this.epg_points = scope.row.epg_points
    },
    close() {
      this.show = false
    },
    blur() {
      if (this.itemData.length === 0) {
        this.keywords = ''
        this.getItemList()
      }
    },
    clearData() {
      this.keywords = ''
      this.getItemList()
    },
    getItemList() {
      this.$store.dispatch('channel/getItemList', { channel_id: this.form.channel_id, keyword: this.keywords })
    },
    dataFilter(val) {
      this.keywords = val
      this.getItemList()
    },
    handleSizeChange(val) {
      this.form.limit = val
      this.getRecordList()
    },
    handleCurrentChange(val) {
      this.form.page = val
      this.getRecordList()
    },
    getRecordList() {
      this.loading = true
      var params = JSON.parse(JSON.stringify(this.form))
      params.channel_id = params.channel_id || null
      params.task_id = params.task_id || null
      this.$get('/epg-task/epg-task-list-with-points', params).then((res) => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.loading = false
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    }
  }
}
</script>

<style lang='scss' scoped>
.start-task{
  font-size: 14px;
  color: #167CFF;
  margin-right: 10px;
  font-weight: 500;
}
::v-deep .el-dialog__body{
  padding-top: 0;
}
</style>
