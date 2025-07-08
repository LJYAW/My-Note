<!--
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-03-27 18:25:45
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/task_mgt/index.vue
-->
<template>
  <div class="task-met">
    <div class="search-top">
      <div>
        <div class="search-list">
          <div class="item-serach">
            <span class="item-label">频道:</span>
            <el-select v-model="form.channel_id" filterable clearable placeholder="请选择频道" style="width:100%">
              <el-option
                v-for="item in channelData"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </div>
          <div class="item-serach">
            <span class="item-label">播出日期:</span>
            <el-date-picker
              v-model="form.date"
              type="date"
              placeholder="选择日期"
              style="width:100%"
              value-format="yyyy-MM-dd"
              :clearable="false"
            />
          </div>
          <div class="item-serach">
            <span class="item-label">状态:</span>
            <el-select v-model="form.status" filterable clearable placeholder="请选择状态" style="width:100%">
              <el-option
                v-for="item in status_options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="item-serach btns">
            <base-btn size="big" @click="search()">搜索</base-btn>
          </div>
        </div>
      </div>
    </div>
    <div style="margin-top:36px">
      <el-table :data="tableData" class="table-list" border>
        <el-table-column prop="task_id" label="TID" />
        <el-table-column prop="channel_name" label="频道" />
        <el-table-column prop="start_time" label="任务时间段">
          <template slot-scope="scope">
            <span>{{ scope.row.start_time }}</span>
            <span>-</span>
            <span>{{ scope.row.end_time }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="任务状态">
          <template slot-scope="scope">
            <div v-if="scope.row.status=='挂起中'">
              <span>{{ scope.row.status }}</span>
              <el-tooltip class="item tooltip-item" effect="light" placement="top">
                <a class="el-icon-warning warning-icon" />
                <div slot="content">
                  <div v-for="(item,index) in scope.row.hang_up_reason" :key="index">
                    <div>{{ index+1 }}. {{ item.name }}</div>
                    <div>{{ item.desc }}</div>
                  </div>
                </div>
              </el-tooltip>
            </div>
            <span v-else>{{ scope.row.status }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="department" label="团队" />
        <el-table-column prop="executor" label="执行人" />
        <el-table-column prop="updated_at" label="更新时间" />
        <el-table-column prop="" label="操作" width="100px">
          <template slot-scope="scope">
            <div v-if="$store.state.user.userInfo.is_superman">
              <base-btn v-if="scope.row.status==='挂起中' || scope.row.status==='已关闭'" type="text" @click="startTask(scope)">开启任务</base-btn>
              <base-btn v-else type="text" class="end-task">
                <span v-if="scope.row.status!=='已完成'" @click="resetTask(scope)">关闭任务</span>
              </base-btn>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-list">
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
import { formatDate } from '../js/times'
export default {
  components: {
  },
  data() {
    return {
      form: {
        channel_id: null,
        date: formatDate(new Date()),
        status: ''
      },
      status_options: [
        {
          label: '全部',
          value: ''
        },
        {
          label: '挂起中',
          value: '挂起中'
        },
        {
          label: '已完成',
          value: '已完成'
        },
        {
          label: '进行中',
          value: '进行中'
        },
        {
          label: '待分配',
          value: '待分配'
        },
        {
          label: '等待中',
          value: '等待中'
        },
        {
          label: '已关闭',
          value: '已关闭'
        }
      ],
      tableData: [
      ],
      limit: 10,
      total: null,
      page: 1
    }
  },
  computed: {
    ...mapGetters(['channelData'])
  },
  watch: {

  },
  created() {
    this.$store.dispatch('channel/getChannelData')
    this.getList()
  },
  mounted() {

  },
  methods: {
    search() {
      this.page = 1
      this.getList()
    },
    getList() {
      const params = {
        channel_id: this.form.channel_id || null,
        date: this.form.date,
        status: this.form.status || null,
        page: this.page,
        limit: this.limit
      }
      this.$get('/epg-task/epg-task-list', params).then((res) => {
        this.tableData = res.data.list
        this.total = res.data.total
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    upsStatus(id, status) {
      const params = {
        id: id,
        status: status
      }
      this.$post('/epg-task/up-status', params).then((res) => {
        this.$message({
          showClose: true,
          message: status === 1 ? '开启任务成功' : '释放任务成功',
          type: 'success'
        })
        this.getList()
      }).catch(res => {
        this.$message({
          showClose: true,
          message: status === 1 ? '开启任务失败' : '释放任务失败',
          type: 'error'
        })
      })
    },
    startTask: window.lodash.throttle(function(scope) {
      this.upsStatus(scope.row.task_id, 1)
    }, 2000),
    resetTask: window.lodash.throttle(function(scope) {
      this.upsStatus(scope.row.task_id, -1)
    }, 2000),
    handleCurrentChange(val) {
      this.page = val
      this.getList()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getList()
    }
  }
}
</script>

<style lang='scss' scoped>
.task-met{
  width: 100%;
  background: #fff;
  .search-top {
    padding-top: 10px;
  }
  .pagination-list{
      width: 100%;
      text-align: right;
      margin-top: 20px;
   }
  .search-list{
    display: flex;
    flex-wrap: wrap;
    .btns{
      justify-content: flex-end;
      margin-left: 20px;
    }
    .item-serach{
      display: flex;
      margin-top: 10px;
      ::v-deep.el-input__inner{
        width: 200px;
        height: 40px;
      }
      .item-label{
        min-width: 100px;
        margin-top: 10px;
        text-align: center;
        color:#606266;
        font-size: 14px;
        font-weight: 700;
      }
    }
  }
  .table-list{
    ::v-deep .cell{
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .tooltip-item{
      position: relative;
      top: 2px;
    }
    .end-task{
      color: #DD3737;
    }
    .warning-icon{
      font-size: 16px;
    }
  }
}
</style>
