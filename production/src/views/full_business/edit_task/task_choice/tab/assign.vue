
<template>
  <div class="assign-wrap">
    <div class="table-liat">
      <el-table :data="tableData" class="table-list">
        <el-table-column prop="task_id" label="TID" />
        <el-table-column prop="channel_name" label="频道" />
        <el-table-column prop="item_name" label="栏目" />
        <el-table-column prop="date" label="播出日期" />
        <el-table-column prop="time" label="播出时间">
          <template slot-scope="scope">
            <span>{{ scope.row.start_time }}</span>
            <span>-</span>
            <span>{{ scope.row.end_time }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="processing_require" label="加工方式" />
        <el-table-column prop="status" label="任务状态" />
        <el-table-column prop="" label="操作" width="180px">
          <template slot-scope="scope">
            <!-- <a class="start-task" @click="startTask(scope.row)">开始任务</a> -->
            <base-open-qt v-if="scope.row.status !== '已完成' && scope.row.status !== '挂起中'" :task-id="scope.row.task_id" />
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-list">
      <base-pag :total="total" :limit="limit" :page="page" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
    </div>
  </div>
</template>
<script>
import openQweb from '@/utils/open-qweb.js'
export default {
  components: {},
  data() {
    return {
      page: 1,
      limit: 10,
      total: null,
      tableData: [
      ]
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getList()
  },
  mounted() {},
  methods: {
    handleSizeChange(val) {
      this.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getList()
    },
    getList() {
      const params = {
        order_type: '整档',
        page: this.page,
        limit: this.limit
      }
      this.$get('/production/privateTask', params).then(res => {
        const { list } = res.data
        this.tableData = list
        const { total } = res.data
        this.total = total
      }).catch(res => {
        console.log(res)
      })
    },
    startTask(item) {
      openQweb(item.task_id)
    }
  }
}
</script>

<style lang='scss' scoped>
.assign-wrap{
  .start-task{
    font-size: 14px;
    color: #167CFF;
    margin-right: 10px;
    font-weight: 500;
  }
  .end-task{
    font-size: 14px;
    color: #DD3737;
    margin-right: 10px;
    font-weight: 500;
  }
  .pagination-list{
    width: 100%;
    text-align: right;
    margin-top: 30px;
  }
}
</style>
