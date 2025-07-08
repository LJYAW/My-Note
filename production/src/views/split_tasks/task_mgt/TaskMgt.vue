<!--
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-06-03 15:19:23
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
            <span class="item-label">全频道:</span>
            <el-select v-model="form.channel_id" filterable clearable placeholder="请选择频道">
              <el-option
                v-for="item in channelData"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </div>
          <div class="item-serach">
            <span class="item-label">栏目:</span>
            <el-select
              v-model="form.item_id"
              filterable
              clearable
              placeholder="请选择栏目"
              :filter-method="dataFilter"
              @clear="clearData"
              @blur="blur"
            >
              <el-option
                v-for="item in itemData"
                :key="item.item_id"
                :label="item.name"
                :value="item.item_id"
              />
            </el-select>
          </div>
          <div class="item-serach">
            <span class="item-label">状态:</span>
            <el-select v-model="form.status" filterable clearable placeholder="请选择状态">
              <el-option
                v-for="item in status_options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="item-serach">
            <span class="item-label">任务显示:</span>
            <el-select v-model="form.assign_status" filterable clearable placeholder="请选择状态">
              <el-option
                v-for="item in task_options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="item-serach">
            <span class="item-label">团队:</span>
            <el-select
              v-model="form.department_id"
              filterable
              clearable
              placeholder="请选择执行团队"
              style="width:100%"
              @clear="clearDepartment"
            >
              <el-option
                v-for="item in teamData"
                :key="item.id"
                :label="item.department_name"
                :value="item.id"
              />
            </el-select>
          </div>
          <div class="item-serach">
            <span class="item-label">执行人:</span>
            <el-select
              v-model="form.executor_id"
              placeholder="请选择执行人"
              clearable
              filterable
            >
              <el-option
                v-for="item in person_options"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select>
          </div>
          <div class="item-serach">
            <span class="item-label">播出日期:</span>
            <el-date-picker
              v-model="form.daterange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
            />
          </div>
          <div class="item-serach">
            <span class="item-label">加工方式:</span>
            <el-select v-model="form.processing_require" clearable filterable placeholder="请选择加工方式">
              <el-option
                v-for="item in processingOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="item-serach">
            <span class="item-label">更新时间:</span>
            <el-date-picker
              v-model="form.updateDate"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
            />
          </div>
          <div class="item-serach btns">
            <base-btn size="big" @click="search()">搜索</base-btn>
            <base-btn size="big" :disabled="task_ids.length<=0" @click="assign()">批量指派任务</base-btn>
            <base-btn size="big" @click="exportData">导出</base-btn>
          </div>
        </div>
      </div>
    </div>
    <div style="margin-top:36px">
      <el-table ref="multipleTable" v-loading="loading" :data="tempTableData" class="table-list" border @selection-change="handleSelectionChange">
        <el-table-column prop="" label="" type="selection" />
        <el-table-column prop="task_id" label="TID" />
        <el-table-column prop="channel_name" label="频道" />
        <el-table-column prop="item_name" label="栏目" width="180">
          <template slot-scope="scope">
            <div class="hang_up_reason">
              <span>{{ scope.row.item_name }}</span>
              <div v-if="scope.row.status==='挂起中' && scope.row.hang_up_reason" style="margin-left:10px">
                <div>
                  <el-tooltip class="item" effect="light" placement="top">
                    <a class="el-icon-warning warning-icon" />
                    <div slot="content">
                      <div v-for="(item,index) in scope.row.hang_up_reason" :key="index">
                        <div>{{ index+1 }}. {{ item.name }}</div>
                        <div>{{ item.desc }}</div>
                      </div>
                    </div>
                  </el-tooltip>
                </div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="任务状态" />
        <el-table-column prop="processing_require" label="加工方式" />
        <el-table-column prop="date" label="播出日期" width="110px" />
        <el-table-column prop="start_time" label="播出时间" width="160px">
          <template slot-scope="scope">
            <el-popover
              v-if="(scope.row.status==='待分配' || scope.row.status==='等待中') && roles.some(item => item === 'superman')"
              ref="popover"
              :key="scope.row.task_id"
              v-model="scope.row.visible"
              trigger="click"
              placement="top"
            >
              <div>
                <el-time-picker
                  v-model="scope.row.start_time_play"
                  value-format="HH:mm:ss"
                  placeholder="开始时间"
                  style="width:120px"
                />
                <span style="margin-left:10px;margin-right: 10px;">至</span>
                <el-time-picker
                  v-model="scope.row.end_time_play"
                  value-format="HH:mm:ss"
                  placeholder="结束时间"
                  style="width:120px"
                />
              </div>
              <div style="text-align: right; margin: 0">
                <el-button size="mini" type="text" @click="scope.row.visible = false">取消</el-button>
                <el-button type="text" size="mini" @click="editTime(scope.row)">确定</el-button>
              </div>
              <div
                slot="reference"
                class="text customers-hover"
              >
                <span>{{ scope.row.start_time }}</span>
                <span>-</span>
                <span>{{ scope.row.end_time }}</span>
                <i class="el-icon-edit" />
              </div>
            </el-popover>
            <div v-else>
              <span>{{ scope.row.start_time }}</span>
              <span>-</span>
              <span>{{ scope.row.end_time }}</span>
            </div>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="" label="进度" width="160px">
          <template slot-scope="scope">
            <span>{{ scope.row.latest_broadcast_end_time }}</span>
            <span>-</span>
            <span>{{ scope.row.end_time }}</span>
          </template>
        </el-table-column> -->
        <el-table-column prop="complete" label="完成数" />
        <el-table-column prop="transcode_fail_num" label="转码失败数" />
        <el-table-column prop="push_customer_wait_num" label="待推送数" />
        <el-table-column prop="push_customer_fail_num" label="推送失败数" />
        <el-table-column prop="department" label="团队" />
        <el-table-column prop="executor" label="执行人" />
        <el-table-column prop="updated_at" label="更新时间" />
        <el-table-column prop="" label="操作" width="280px">
          <template slot-scope="scope">
            <a v-if="!scope.row.is_assign" class="start-task" @click="singleAssgin(scope)">指派</a>
            <a v-else class="start-task" @click="reset(scope)">释放任务</a>
            <base-open-qt class="start-task" :task-id="scope.row.task_id" btn-name="查看详情" />
            <a class="start-task" @click="see(scope)">查看成品</a>
            <a v-if="scope.row.status=='挂起中'" class="start-task" @click="hangUp(scope)">释放挂起</a>
            <a class="end-task" @click="deletes(scope)">删除</a>
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
    <assignTask ref="assignTask" :task-arr="task_ids" @clearSelection="clearSelection" />

  </div>
</template>

<script>
import assignTask from './models/assign_task'
import { mapGetters } from 'vuex'
import axios from '@/axios/request.js'
export default {
  components: {
    assignTask
  },
  data() {
    return {
      keywords: '',
      form: {
        channel_id: null,
        item_id: null,
        status: null,
        task: null,
        assign_status: null,
        daterange: '',
        department_id: null,
        executor_id: null,
        updateDate: ['', ''],
        processing_require: null
      },
      loading: false,
      total: null,
      page: 1,
      limit: 10,
      task_ids: [],
      processingOption: [
        {
          label: '新闻整档',
          value: '新闻整档'
        },
        {
          label: '节目整档',
          value: '节目整档'
        }
      ],
      person_options: [],
      task_options: [
        {
          label: '仅显示派发任务',
          value: '派发任务'
        },
        {
          label: '仅显示公共任务',
          value: '公共任务'
        }
      ],
      column_options: [
      ],
      status_options: [
        {
          label: '待分配',
          value: '待分配'
        },
        {
          label: '等待中',
          value: '等待中'
        },
        {
          label: '进行中',
          value: '进行中'
        },
        {
          label: '挂起中',
          value: '挂起中'
        },
        {
          label: '已完成',
          value: '已完成'
        }
      ],
      channel_all: [
      ],
      tableData: [
      ]
    }
  },
  computed: {
    ...mapGetters([
      'channelData',
      'itemData',
      'teamData',
      'roles'
    ]),
    tempTableData() {
      const arr = JSON.parse(JSON.stringify(this.tableData))
      if (arr.length !== 0) {
        arr.forEach((item, index) => {
          item.start_time_play = item.start_time
          item.end_time_play = item.end_time
          item.visible = false
        })
      }
      return arr
    }
  },
  watch: {
    'form.channel_id'(id) {
      this.getItemList()
      this.form.item_id = ''
    },
    'form.department_id'(id) {
      if (id) {
        this.geTexecutor()
        this.form.executor_id = ''
      }
    }
  },
  created() {
    if (!this.channelData.length) {
      this.$store.dispatch('channel/getChannelData')
    }
    this.$store.dispatch('user/getTeamData', { page: 1, limit: 10000 })
    this.getItemList()
    this.getList()
  },
  mounted() {
  },
  methods: {
    // 更改播放时间
    editTime(item) {
      const params = {
        task_id: item.task_id,
        start_time: item.start_time_play,
        end_time: item.end_time_play
      }
      this.$post('/production/change-task-time', params).then((res) => {
        this.$message({
          type: 'success',
          message: '修改成功'
        })
        this.getList()
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    // 关闭团队事件
    clearDepartment() {
      this.person_options = []
      this.form.executor_id = null
    },
    geTexecutor() {
      const params = {
        department_id: this.form.department_id,
        keyword: ''
      }
      this.$get('/user/team-user-list', params).then(res => {
        const { data } = res
        this.person_options = data
      }).catch(res => {
        console.log(res)
      })
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
    see(scope) {
      this.$router.push({
        path: '/split-tasks/video-list',
        query: {
          task_id: scope.row.task_id
        }
      })
    },
    singleAssgin(scope) {
      this.$refs.multipleTable.toggleRowSelection(scope.row)
      this.$refs.assignTask.open()
    },
    clearSelection() {
      this.$refs.multipleTable.clearSelection()
    },
    handleSelectionChange(val) {
      this.task_ids = val
    },
    search() {
      this.page = 1
      this.getList()
    },
    async getList() {
      this.loading = true
      const params = {
        channel_id: this.form.channel_id || null,
        item_id: this.form.item_id || null,
        status: this.form.status || null,
        page: this.page,
        limit: this.limit,
        assign_status: this.form.assign_status || null,
        start_date: this.form.daterange === null ? '' : this.form.daterange[0],
        end_date: this.form.daterange === null ? '' : this.form.daterange[1],
        department_id: this.form.department_id,
        executor_id: this.form.executor_id,
        update_start_date: this.form.updateDate === null ? '' : this.form.updateDate[0],
        update_end_date: this.form.updateDate === null ? '' : this.form.updateDate[1],
        processing_require: this.form.processing_require || null
      }
      const url = '/production/splitTaskControl'
      const { data } = await this.$get(url, params)
      if (data) {
        this.loading = false
      }
      this.tableData = data.list
      this.total = data.total
    },
    exportData() {
      if (!this.form.daterange) {
        this.$message({
          type: 'warning',
          message: '请选择播出日期'
        })
        return
      }
      axios({
        method: 'get',
        url: '/production/splitTaskControlExport',
        headers: {
          token: this.token
        },
        params: {
          channel_id: this.form.channel_id || null,
          item_id: this.form.item_id || null,
          status: this.form.status || null,
          assign_status: this.form.assign_status || null,
          start_date: this.form.daterange === null ? '' : this.form.daterange[0],
          end_date: this.form.daterange === null ? '' : this.form.daterange[1],
          department_id: this.form.department_id,
          executor_id: this.form.executor_id,
          update_start_date: this.form.updateDate === null ? '' : this.form.updateDate[0],
          update_end_date: this.form.updateDate === null ? '' : this.form.updateDate[1],
          processing_require: this.form.processing_require || null
        },
        responseType: 'blob'
      })
        .then(res => {
          if (res.data.type) {
            // 文件下载
            const blob = new Blob([res.data], {
              type: 'application/vnd.ms-excel'
            })
            let link = document.createElement('a')
            link.href = URL.createObjectURL(blob)
            link.setAttribute('download', '拆条任务列表.xlsx')
            link.click()
            link = null
            this.$message.success('导出成功')
          } else {
            // 返回json
            this.$message.warning(res.data.msg)
          }
        }).catch((error) => {
          console.log(error)
        })
    },
    assign() {
      this.$refs.assignTask.open()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getList()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getList()
    },
    hangUp(scope) {
      const params = {
        task_id: scope.row.task_id
      }
      this.$post('/production/release-hang', params).then((res) => {
        this.$message({
          showClose: true,
          message: '操作成功',
          type: 'success'
        })
        this.getList()
      }).catch(res => {
        this.$message({
          showClose: true,
          message: '操作失败',
          type: 'error'
        })
      })
    },
    reset(scope) {
      const params = {
        task_id: scope.row.task_id
      }
      this.$post('/production/release-task', params).then((res) => {
        this.$message({
          showClose: true,
          message: '释放成功',
          type: 'success'
        })
        this.getList()
      }).catch(res => {
        this.$message({
          showClose: true,
          message: '释放失败',
          type: 'error'
        })
      })
    },
    deletes(scope) {
      const params = {
        task_id: scope.row.task_id
      }
      this.$confirm('确定要删除此任务吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$post('/production/task-delete', params).then((res) => {
          this.$message({
            showClose: true,
            message: '删除成功',
            type: 'success'
          })
          this.getList()
        }).catch(res => {
          this.$message.error(res.msg)
        })
      })
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
    padding-right: 10px;
  }
  .pagination-list{
      width: 100%;
      text-align: right;
      margin-top: 10px;
   }
  .search-list{
    display: flex;
    flex-wrap: wrap;
    .btns{
      justify-content: flex-end;
      margin-left: 10px;
    }
    .item-serach{
      display: flex;
      margin-top: 10px;
      .el-date-editor--daterange.el-input__inner{
        width: auto;
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
    .hang_up_reason{
      display: flex;
    }
    ::v-deep .cell{
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .warning-icon{
      font-size: 20px;
    }
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
  }
}
</style>
