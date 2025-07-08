<!--
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-08-02 10:26:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/task_mgt/index.vue
-->
<template>
  <div class="task-met">
    <div class="numBox">
      <numsList :num-data="numData" />
    </div>
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
          <div v-if="isShow()" class="item-serach">
            <span class="item-label">执行团队:</span>
            <el-select v-model="form.team" filterable clearable placeholder="请选择执行团队" style="width:100%">
              <el-option
                v-for="item in teamData"
                :key="item.id"
                :label="item.department_name"
                :value="item.id"
              />
            </el-select>
          </div>
          <div v-if="isShow()" class="item-serach">
            <span class="item-label">执行人:</span>
            <el-select v-model="form.executor_id" filterable clearable placeholder="请选择状态" style="width:100%">
              <el-option
                v-for="item in userData()"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select>
          </div>
          <div class="date-search">
            <span class="item-label">任务日期:</span>
            <el-date-picker
              v-model="date"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            />
          </div>
          <div class="item-serach checked">
            <el-checkbox v-model="form.is_unassigned" class="is_unassigned">未派发任务</el-checkbox>
            <base-btn size="small" @click="search()">搜索</base-btn>
            <base-btn size="small" @click="exportData()">导出</base-btn>
          </div>
        </div>
        <p class="count">当前条件下共{{ total }}条数据</p>
      </div>
    </div>

    <div style="margin-top:20px">
      <el-table v-loading="tableLoading" :data="tableData" class="table-list" border @selection-change="handleSelectionChange">
        <el-table-column prop="channel_name" label="频道" width="150" />
        <el-table-column prop="date" label="任务日期" width="120" />
        <el-table-column prop="start_time" label="任务时间" width="180">
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
                  <div v-for="(item,index) in JSON.parse(scope.row.hang_up_reason)" :key="index">
                    <div>{{ index+1 }}. {{ item.name }}</div>
                    <div>{{ item.desc }}</div>
                  </div>
                </div>
              </el-tooltip>
            </div>
            <span v-else>{{ scope.row.status }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="片段数" />
        <el-table-column prop="department_name" label="团队" width="180" />
        <el-table-column prop="executor_name" label="执行人" />
        <el-table-column prop="updated_at" label="更新时间" width="200px" />
        <el-table-column prop="" label="操作" width="260px">
          <template slot-scope="scope">
            <base-btn type="text" @click.native="details(scope)">查看详情</base-btn>
            <span v-if="scope.row.status!=='已完成'&&$store.state.user.userInfo.is_superman">
              <base-btn
                v-if="scope.row.status=='挂起中'||scope.row.status=='已关闭'"
                type="text"
                @click="startTask(scope)"
              >开启任务</base-btn>
              <base-btn v-else type="text" class="end-task" @click="resetTask(scope)">关闭任务</base-btn>
            </span>

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
import numsList from '../../../components/NumsLIst/index'
import axios from '@/axios/request.js'
export default {
  components: {
    numsList
  },
  data() {
    return {
      form: {
        channel_id: null,
        status: null,
        team: null,
        executor_id: null,
        is_unassigned: false
      },
      numData: [],
      status_options: [
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
      page: 1,
      list: [
        { key: 'hang_up_num',
          value: '挂起任务数'
        },
        { key: 'incomplete_num',
          value: '未完成任务数'
        },
        { key: 'on_going_num',
          value: '进行中任务数'
        },
        { key: 'today_complete_num',
          value: '今天已完成任务' }
      ],
      userList: [],
      date: [],
      tableLoading: false
      // roles: ['superman', '广告编辑', '广告编辑管理员']
    }
  },
  computed: {
    ...mapGetters(['channelData', 'teamData', 'roles'])
  },
  watch: {
    'form.team'(id) {
      this.form.executor_id = null
    }
  },
  created() {
    this.$store.dispatch('channel/getChannelData')
    this.$store.dispatch('user/getTeamData', { page: 1, limit: 10000 })
    this.getTjNum()
    this.getList()
    this.getUser()
  },
  mounted() {

  },
  methods: {
    isShow() {
      return this.roles.includes('superman') || this.roles.includes('广告审核') || this.roles.includes('广告审核管理员')
    },
    // 获取用户列表
    getUser() {
      this.$get('/user/userList', { page: 1, limit: 10000 }).then((res) => {
        this.userList = res.data.list
      })
    },
    userData(id) {
      if (!this.form.team) return []
      return this.userList.filter(item => item.department_id === this.form.team)
    },
    details(scope) {
      this.$router.push({
        path: '/advertisement/detail-system',
        query: {
          status: 'detail',
          tid: scope.row.id
        }
      })
    },
    handleSelectionChange(val) {},
    search() {
      this.page = 1
      this.getList()
    },
    getList() {
      this.tableLoading = true
      const params = {
        channel_id: this.form.channel_id || null,
        status: this.form.status || null,
        department_id: this.form.team || null,
        is_unassigned: this.form.is_unassigned,
        executor_id: this.form.executor_id,
        start_date: this.date ? this.date[0] : '',
        end_date: this.date ? this.date[1] : '',
        page: this.page,
        limit: this.limit
      }
      this.$get('/ads-task/detail-list', params).then((res) => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.tableLoading = false
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    exportData() {
      axios({
        method: 'get',
        url: '/ads-task/detail-list',
        headers: {
          token: this.token
        },
        params: {
          channel_id: this.form.channel_id || null,
          status: this.form.status || null,
          department_id: this.form.team || null,
          is_unassigned: this.form.is_unassigned,
          executor_id: this.form.executor_id,
          start_date: this.date ? this.date[0] : '',
          end_date: this.date ? this.date[1] : '',
          page: 1,
          is_export: true,
          limit: 10000
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
            link.setAttribute('download', '广告任务详情表.xlsx')
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
    upsStatus(id, status) {
      const params = {
        id: id,
        status: status
      }
      this.$post('/epg-task/up-status', params).then((res) => {
        this.$message({
          showClose: true,
          message: status === 1 ? '开启任务成功' : '关闭任务成功',
          type: 'success'
        })
        this.getList()
      }).catch(res => {
        this.$message({
          showClose: true,
          message: status === 1 ? '开启任务失败' : '关闭任务失败',
          type: 'error'
        })
      })
    },
    startTask: window.lodash.throttle(function(scope) {
      this.upsStatus(scope.row.id, 1)
    }, 2000),
    resetTask: window.lodash.throttle(function(scope) {
      this.upsStatus(scope.row.id, -1)
    }, 2000),
    handleCurrentChange(val) {
      this.page = val
      this.getList()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getList()
    },
    getTjNum() {
      this.$get('/ads-task/administer-tj').then((res) => {
        this.list.forEach((item, index) => {
          this.numData.push({
            label: item.value,
            value: res.data[item.key]
          })
        })
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    }
  }
}
</script>

<style lang='scss' scoped>
.task-met{
  width: 100%;
  background: #fff;
  .numBox{
    padding: 10px;
    box-sizing: border-box;
  }
  .search-top {
    padding-top: 10px;
    .count{
      padding-left: 20px;
      margin-top: 20px;
    }
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
      .is_unassigned{
        line-height: 40px;
        margin: 0 30px;
      }
    }
    .date-search{
      display: flex;
      margin-top: 10px;
      .item-label{
        min-width: 100px;
        margin-top: 10px;
        text-align: center;
        color: #606266;
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
    button{
      margin-right: 20px;
    }
  }
}
</style>
