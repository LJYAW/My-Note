<!--
 * @Author: your name
 * @Date: 2021-01-13 18:07:27
 * @LastEditTime: 2021-05-17 11:18:03
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
          <el-form-item label="栏目:">
            <el-select v-model="form.item_id" clearable filterable placeholder="请选择栏目名称" :filter-method="dataFilter" @clear="clearData" @blur="blur">
              <el-option
                v-for="item in itemData"
                :key="item.item_id"
                :label="item.name"
                :value="item.item_id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="加工方式:">
            <el-select v-model="form.processing_require" clearable filterable placeholder="请选择加工方式">
              <el-option
                v-for="item in task_options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题:">
            <el-input v-model="form.title" clearable placeholder="请输入标题" />
          </el-form-item>
          <el-form-item label="日期:">
            <el-date-picker
              v-model="date"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            />
          </el-form-item>
          <el-form-item>
            <el-checkbox-group v-model="is_overtime">
              <el-checkbox label="仅展示超时任务" name="overtime" />
            </el-checkbox-group>
          </el-form-item>
          <base-btn size="big" @click.native="search">搜索</base-btn>
        </el-form>
      </div>
      <div class="details-table">
        <el-tabs v-model="activeName">
          <el-tab-pane label="编辑记录" name="编辑记录">
            <el-table v-loading="loading" :data="tableData" class="table-list" border style="margin-top:15px">
              <el-table-column label="TID">
                <template slot-scope="scope">
                  {{ scope.row.task_id }}
                  <span :class="[scope.row.task_property=='公'&&'pub-logo','pri-logo']">{{ scope.row.task_property }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="channel_name" label="频道名称" />
              <el-table-column prop="item_name" label="栏目名称" />
              <el-table-column prop="title" label="标题" />
              <el-table-column prop="time" label="任务时间段">
                <template slot-scope="scope">
                  {{ scope.row.broadcast_time }}<br>
                  {{ scope.row.broadcast_end_time }}
                </template>
              </el-table-column>
              <el-table-column prop="type" label="超时(分钟)">
                <template slot-scope="scope">
                  {{ Math.floor(scope.row.overtime_seconds/60) }}
                </template>
              </el-table-column>
              <el-table-column prop="created_at" label="提交时间" />
              <el-table-column prop="processing_require" label="加工方式" />
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div class="pagination-list">
        <base-pag :total="total" :limit="form.limit" :page="form.page" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
      </div>
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
      activeName: '编辑记录',
      total: null,
      form: {
        channel_id: '',
        item_id: '',
        title: '',
        processing_require: '',
        page: 1,
        limit: 10
      },
      date: [],
      loading: false,
      is_overtime: false,
      task_options: [
        {
          label: '新闻整档',
          value: '新闻整档'
        },
        {
          label: '节目整档',
          value: '节目整档'
        }
      ]
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
    search() {
      this.form.page = 1
      this.getRecordList()
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
      if (this.is_overtime) {
        params.is_overtime = '是'
      }
      params.start_time = this.date ? this.date[0] : ''
      params.end_time = this.date ? this.date[1] : ''
      params.order_type = '拆条'
      this.$get('/task/finish-task-video-list', params).then((res) => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.loading = false
      }).catch((error) => {
        this.$message.error(error.msg)
        this.loading = false
      })
    }
  }
}
</script>

<style lang='scss' scoped>

</style>
