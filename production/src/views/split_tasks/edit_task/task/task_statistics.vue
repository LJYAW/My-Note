<!--
 * @Author: your name
 * @Date: 2021-01-13 18:08:14
 * @LastEditTime: 2021-01-25 15:05:49
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
          border
          :span-method="dataSpanMethod"
          :summary-method="getSummaries"
          show-summary
        >
          <el-table-column>
            <template>
              {{ userName }}
            </template>
          </el-table-column>
          <el-table-column label="加工方式" prop="processing_require" />
          <el-table-column prop="processing_efficiency" label="时效要求" />
          <el-table-column prop="is_overtime_no" label="时效内（条）" />
          <el-table-column prop="is_overtime_yes" label="超时（条）" />
          <el-table-column prop="hanging" label="挂起（条）" />
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      total: null,
      form: {
        date: '',
        page: 1,
        limit: 10
      },
      tableData: [{}],
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
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计'
          return
        }
        const values = data.map(item => Number(item[column.property]))
        if (!values.some(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr)
            if (!isNaN(value)) {
              return prev + curr
            } else {
              return prev
            }
          }, 0)
        } else {
          sums[index] = ''
        }
      })

      return sums
    },
    getTaskList() {
      this.loading = true
      var params = {
        order_type: '拆条',
        start_time: this.form.date ? this.form.date[0] : '',
        end_time: this.form.date ? this.form.date[1] : ''
      }
      this.$get('/production/taskStatisticalList', params).then((res) => {
        this.userName = res.data.username
        this.tableData = res.data.list
        this.total = res.data.list.length
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
