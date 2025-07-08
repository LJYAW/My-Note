<!--
 * @Author: your name
 * @Date: 2021-01-28 17:58:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/assign/index.vue
-->
<template>
  <div class="order-assign">
    <div class="content-header">
      <el-form :inline="true" :model="form">
        <el-form-item label="工单编号">
          <el-input v-model="form.order_no" placeholder="请输入工单编号" />
        </el-form-item>
        <el-form-item label="工单类型">
          <el-select v-model="form.order_type" clearable filterable placeholder="请选择">
            <el-option
              v-for="item in order_type_list"
              :key="item.type_name"
              :label="item.type_name"
              :value="item.type_name"
            />
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.order_type && form.order_type!=='EPG'" label="加工要求">
          <el-select v-model="form.processing_require" filterable clearable placeholder="请选择">
            <el-option
              v-for="item in processingRequire"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="频道名称">
          <el-select v-model="form.channel_id" filterable clearable placeholder="请选择">
            <el-option
              v-for="item in channelData"
              :key="'频道名称'+item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="栏目名称">
          <el-select v-model="form.item_name" filterable clearable placeholder="请选择">
            <el-option
              v-for="item in itemData"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="任务等级">
          <el-select v-model="form.task_level" clearable placeholder="请选择">
            <el-option
              v-for="item in task_level_list"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="任务周期">
          <el-select v-model="form.task_cycle" clearable placeholder="请选择">
            <el-option
              v-for="item in task_cycle_list"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="加工时效">
          <el-select v-model="form.processing_efficiency" clearable placeholder="请选择">
            <el-option
              v-for="(item,index) in processing_efficiency"
              :key="index"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="工单状态">
          <el-select v-model="form.status" clearable placeholder="请选择">
            <el-option
              v-for="(value,key) in order_status"
              :key="key"
              :label="value"
              :value="value"
            />
          </el-select>
        </el-form-item>
        <base-btn class="search-btn" size="big" @click="searchData">搜索</base-btn>
        <base-btn class="search-btn create-btn" size="big" @click="clearSearch">清除</base-btn>
      </el-form>
    </div>
    <el-table :data="tableData" border>
      <el-table-column prop="order_no" width="80" label="工单编号" />
      <el-table-column prop="task_cycle" label="任务周期" />
      <el-table-column label="工单类型">
        <template slot-scope="scope">
          {{ scope.row.order_type.join('、') }}
        </template>
      </el-table-column>
      <el-table-column label="加工要求">
        <template slot-scope="scope">
          {{ scope.row.processing_require.join('、') }}
        </template>
      </el-table-column>
      <el-table-column prop="item_name" label="栏目名称" />
      <el-table-column prop="channel_name" label="频道名称" />
      <el-table-column label="加工流程">
        <template slot-scope="scope">
          {{ scope.row.processing_flow.join('、') }}
        </template>
      </el-table-column>
      <el-table-column label="加工时效">
        <template slot-scope="scope">
          {{ scope.row.processing_efficiency.join('、') }}
        </template>
      </el-table-column>
      <el-table-column prop="task_level" label="任务等级" />
      <el-table-column prop="customers" label="客户数">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" :content="scope.row.customers.join('、')" placement="top-start">
            <span>{{ scope.row.customers.length }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="工单状态" />
      <el-table-column label="操作" width="55">
        <template>
          <span>指派</span>
        </template>
      </el-table-column>
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-table :data="scope.row.time_list" :span-method="objectSpanMethod">
            <el-table-column :prop="scope.row.task_cycle=='短期播放'?'date':'week'" label="日期" />
            <el-table-column prop="time" label="播出时间">
              <template slot-scope="scopes">
                {{ scopes.row.start_time }} - {{ scopes.row.end_time }}
              </template>
            </el-table-column>
            <el-table-column prop="processing_require" label="加工要求" />
            <el-table-column label="操作" width="500">
              <template slot="header">
                <div class="table-head">
                  <span>操作</span>
                  <i class="iconfont el-icon-refresh" @click="syncTeam(scope.row.time_list)" />
                </div>

              </template>
              <template slot-scope="scopes">
                <div v-if="scopes.row.isAssign&&!scopes.row.toUpdate">
                  <span class="text">{{ scopes.row.department_name }}</span>
                  <span v-if="scopes.row.processing_require!=='EPG'&&scopes.row.processing_require!=='广告'" class="text">{{ scopes.row.executor_name }}</span>
                  <base-btn size="mini" round @click.native="updateAssign(scope.$index,scopes.$index)">修改指派</base-btn>
                </div>
                <div v-else>
                  <el-select
                    v-model="scopes.row.department_id"
                    placeholder="请选择"
                    size="mini"
                    clearable
                    @change="handleSelectChange(scopes.row)"
                  >
                    <el-option
                      v-for="item in teamData"
                      :key="item.id"
                      :label="item.department_name"
                      :value="item.id"
                    />
                  </el-select>
                  <el-select
                    v-if="scopes.row.processing_require!=='EPG'&&scopes.row.processing_require!=='广告'"
                    v-model="scopes.row.executor_id"
                    placeholder="请选择"
                    clearable
                    size="mini"
                  >
                    <el-option
                      v-for="item in userData(scopes.row.department_id)"
                      :key="item.id"
                      :label="item.nickname"
                      :value="item.id"
                    />
                  </el-select>
                  <base-btn
                    size="mini"
                    round
                    @click="submit(scope.$index,scopes.$index,scopes.row)"
                  >提交</base-btn>
                  <base-btn
                    v-if="scopes.row.isAssign"
                    size="mini"
                    round
                    @click="cancel(scope.$index,scopes.$index)"
                  >取消</base-btn>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
    </el-table>
    <base-pag :total="total" :limit="limit" :page="page" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Assign',
  components: {

  },
  props: {

  },
  data() {
    return {
      loading: true,
      form: {
        order_no: '',
        order_type: '',
        task_level: '',
        task_cycle: '',
        channel_id: '',
        item_name: '',
        status: '',
        processing_require: '',
        processing_efficiency: ''
      },
      total: 0,
      page: 1,
      limit: 10,
      order_type_list: [{
        type_name: '整档',
        require_items: ['节目整档', '新闻整档']
      }, {
        type_name: '拆条',
        require_items: ['新闻拆条', '节目分段']
      },
      {
        type_name: '广告',
        require_items: ['商业广告', '公益广告', '购物广告', '植入广告', '赞助广告']
      },
      {
        type_name: 'EPG'
      }
      ],
      processing_efficiency: ['5分钟', '10分钟', '15分钟', '30分钟', '50分钟', '60分钟', '1.5小时', '2小时', '4小时', '1天', '3天'],
      processing_require_data: [],
      task_level_list: ['正常', '加急'],
      task_cycle_list: ['周期播放', '短期播放'],
      order_status: {
        ORDER_STATUS_执行中: '执行中',
        ORDER_STATUS_待执行: '待执行',
        ORDER_STATUS_已失效: '已失效'
      },
      tableData: [],
      userList: []
    }
  },
  computed: {
    processingRequire() {
      const data = this.order_type_list.filter((item) => item.type_name === this.form.order_type)
      return data.length ? data[0].require_items : []
    },
    ...mapGetters(['channelData', 'itemData', 'teamData'])
  },
  watch: {
    'form.channel_id'(id) {
      if (id) {
        this.$store.dispatch('channel/getItemList', { channel_id: id })
      }
      this.form.item_name = null
    },
    'form.order_type'(val) {
      this.form.processing_require = ''
    }
  },
  created() {
    this.loading = true
    if (!this.channelData.length) {
      this.$store.dispatch('channel/getChannelData')
    }
    if (!this.teamData.length) {
      this.$store.dispatch('user/getTeamData', { page: 1, limit: 10000 })
    }
    this.getUser()
    this.getTableData()
  },
  mounted() {

  },
  methods: {
    // 设置表格数据
    async getTableData() {
      await this.getOrderList()
      this.setTableData()
    },
    setFieldValue(type, time, field) {
      switch (type) {
        case '整档':
          return time['video_' + field]
        case '拆条':
          return time['cut_video_' + field]
        case 'EPG':
          return time['epg_' + field]
        case '广告':
          return time['ad_' + field]
      }
    },
    // 拆分表格数据
    setTableData() {
      this.tableData.forEach((item, index) => {
        const { order_type, time_list } = item
        const orderTimeList = []
        time_list.forEach(time => {
          order_type.forEach(type => {
            orderTimeList.push({
              processing_require: type,
              order_type: order_type,
              department_id: this.setFieldValue(type, time, 'department_id'),
              department_name: this.setFieldValue(type, time, 'department_name'),
              executor_id: type === '整档' ? time.video_executor_id : time.cut_video_executor_id,
              executor_name: type === '整档' ? time.video_executor_name : time.cut_video_executor_name,
              time_id: time.time_id,
              start_time: time.start_time,
              end_time: time.end_time,
              week: time.week,
              date: time.date
            })
          })
        })
        item.time_list = orderTimeList
        item.time_list.forEach(time => {
          time.isAssign = time.department_id !== ''
          if (time.department_id) {
            time.toUpdate = false
          }
        })
      })
    },
    // 表格合并
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (row.order_type && row.order_type.length > 1) {
        if (columnIndex === 0 || columnIndex === 1) {
          if (rowIndex % 2 === 0) {
            return {
              rowspan: 2,
              colspan: 1
            }
          } else {
            return {
              rowspan: 0,
              colspan: 0
            }
          }
        }
      }
    },
    // 修改指派
    submit(orderIndex, timeIndex, obj) {
      if (!obj.department_id) {
        this.$message({
          message: '请选择指派团队',
          type: 'warning'
        })
        return false
      }
      // if (!obj.executor_id) {
      //   this.$message({
      //     message: '请选择指派人',
      //     type: 'warning'
      //   })
      //   return false
      // }
      const params = {
        time_id: [obj.time_id],
        department_id: obj.department_id,
        executor_id: obj.executor_id,
        order_type: obj.processing_require
      }
      this.$post('/production/assign-order-time', params).then((res) => {
        this.$message({
          message: '指派成功',
          type: 'success'
        })
        // this.getTableData()
        const params = JSON.parse(JSON.stringify(this.tableData[orderIndex].time_list[timeIndex]))
        params.toUpdate = false
        params.isAssign = true
        params.department_name = this.teamData.filter(item => item.id === obj.department_id)[0].department_name
        params.executor_name = obj.executor_id ? this.userList.filter(item => item.id === obj.executor_id)[0].nickname : ''
        this.$set(this.tableData[orderIndex].time_list, timeIndex, params)
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    searchData() {
      this.page = 1
      this.getTableData()
    },
    clearSearch() {
      for (const key in this.form) {
        this.form[key] = ''
      }
    },
    handleSelectChange(val) {
      val.executor_id = ''
    },
    userData(id) {
      return this.userList.filter(item => item.department_id === id)
    },
    // 获取用户列表
    getUser() {
      this.$get('/user/userList', { page: 1, limit: 10000 }).then((res) => {
        this.userList = res.data.list
      })
    },
    // 获取工单列表
    getOrderList() {
      var params = this.form
      params.page = this.page
      params.limit = this.limit
      return new Promise((reslove, reject) => {
        this.$get('/production/order-list', params).then((res) => {
          this.tableData = res.data.list
          this.total = res.data.total
          reslove(res.data)
        }).catch(res => {
          reject(res)
        })
      })
    },
    updateAssign(orderIndex, timeIndex) {
      this.changeTimeListStatus(orderIndex, timeIndex, true)
    },
    cancel(orderIndex, timeIndex) {
      this.changeTimeListStatus(orderIndex, timeIndex, false)
    },
    // 更改操作状态
    changeTimeListStatus(orderIndex, timeIndex, status, obj) {
      const params = JSON.parse(JSON.stringify(this.tableData[orderIndex].time_list[timeIndex]))
      params.toUpdate = status
      this.$set(this.tableData[orderIndex].time_list, timeIndex, params)
    },
    // 同步操作
    syncTeam(timeData) {
      this.$nextTick(() => {
        timeData.forEach((item, index) => {
          item.department_id = timeData[0].department_id
          item.executor_id = timeData[0].executor_id
          if (item.isAssign && index) {
            item.toUpdate = true
            this.$set(timeData, index, item)
          }
        })
      })
    },
    handleCurrentChange(val) {
      this.page = val
      this.getTableData()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getTableData()
    }
  }
}
</script>

<style scoped lang="scss">
.order-assign{
    padding: 20px;
    .create-btn{
        margin-bottom: 20px;
    }
    ::v-deep .el-form{
        .el-input,input{
            width: 180px;
        }
    }
    ::v-deep .el-table{
        .el-table__expand-icon--expanded{
          transform: rotate(0deg);
          .el-icon{
            transform: rotate(90deg);
            transition: transform .2s ease-in-out;
          }
        }
        th:nth-child(12),td:nth-child(12){
          border-right: none;
        }
        td:nth-child(13){
          .cell{
              padding: 0;
              margin-left: -50px;
          }
          .el-table__expand-icon{
              height: 40px;
          }
        }
        .el-table__expanded-cell{
            padding: 0;
            padding-left: 80px;
        }
        .el-table{
            border-left: 1px solid #ddd;
            .el-select{
                margin-right: 20px;
            }
            .el-input{
                width:140px;
            }
            .text{
                margin-right: 20px;
                display: inline-block;
                min-width: 140px;
                padding-left: 15px;
            }
            .el-icon-refresh{
                margin-left: 20px;
                cursor: pointer;
            }
        }
    }
}
</style>
