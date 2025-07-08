<!--
 * @Author: your name
 * @Date: 2021-01-13 18:08:14
 * @LastEditTime: 2021-03-02 15:35:56
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/edit_task/task/task_statistics.vue
-->
<template>
  <div>
    <div class="edit-task-table">
      <div class="search-top">
        <div class="item-search">
          <span class="item-label">日期:</span>
          <el-date-picker
            v-model="form.date"
            type="daterange"
            value-format="yyyy-MM-dd"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :clearable="false"
          />

        </div>
        <div class="item-search btns">
          <base-btn size="big" @click.native="getTaskList">搜索</base-btn>
        </div>
      </div>
      <div class="task-table">
        <el-table
          v-loading="loading"
          :data="tableData"
          class="table-list"
          style="margin-top:15px"
          :span-method="dataSpanMethod"
          show-summary
          border
          :summary-method="getSummaries"
        >
          <el-table-column>
            <template>
              {{ userName }}
            </template>
          </el-table-column>
          <el-table-column label="任务条" prop="duration" />
          <el-table-column prop="processingEfficiency" label="时效(分钟)" />
          <el-table-column prop="finishNum" label="完成（条）" />
          <el-table-column prop="finishDuration" label="完成（小时）" />
          <el-table-column prop="percent" label="占比(%)" />
          <el-table-column prop="hangUpNum" label="挂起(条)" />
          <el-table-column prop="hangUpDuration" label="挂起(小时)" />
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import { formatDate } from '../js/times'

export default {
  components: {},
  data() {
    return {
      totalInfo: {},
      total: null,
      form: {
        date: [formatDate(new Date()), formatDate(new Date())],
        page: 1,
        limit: 10
      },
      tableData: [],
      userName: '',
      loading: false
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getTaskList()
  },
  mounted() {

  },
  methods: {
    handleSizeChange(val) {
      this.form.limit = val
    },
    handleCurrentChange(val) {
      this.form.page = val
    },
    dataSpanMethod({ row, column, rowIndex, columnIndex }) {
      // 只是遍历表格td内容，不包含th表头
      // 对第一列，进行合并列
      if (columnIndex === 0) {
        if (rowIndex % this.tableData.length === 0) {
          return {
            rowspan: this.tableData.length,
            colspan: 1
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0
          }
        }
      }
    },
    getSummaries(param) {
      let sums = []
      if (this.tableData) {
        var selectedColm = param.columns
        const newArray = []
        selectedColm.forEach(a => {
          if (this.totalInfo[a.property]) {
            newArray.push(this.totalInfo[a.property])
          } else { newArray.push('') }
        })
        sums = newArray
        sums[0] = '合计'
        return sums
      }
    },
    getTaskList() {
      this.loading = true
      var params = {
        start_date: this.form.date ? this.form.date[0] : '',
        end_date: this.form.date ? this.form.date[1] : ''
      }
      this.$get('/epg-task/tj', params).then((res) => {
        this.userName = res.data.nickname
        this.tableData = res.data.list
        this.total = res.data.list.length
        this.totalInfo = JSON.parse(JSON.stringify(res.data.totalInfo))
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
@import '../scss/tables.scss'
</style>
