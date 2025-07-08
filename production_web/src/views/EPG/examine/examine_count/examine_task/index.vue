<!--
 * @Author: your name
 * @Date: 2021-02-18 11:39:49
 * @LastEditTime: 2021-12-27 11:18:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/Examine.vue
-->
<template>
  <keep-alive>
    <div class="examine-task">
      <div class="search-top">
        <div>
          <div class="search-list">
            <div class="item-serach">
              <span class="item-label">频道:</span>
              <el-select v-model="form.channel_id" filterable placeholder="请选择频道" style="width:100%">
                <el-option
                  v-for="item in channelData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </div>
            <!-- placeholder="选择日期" -->

            <div class="item-serach">
              <span class="item-label">播出日期:</span>
              <el-date-picker
                v-model="form.date"
                type="date"
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
                  :key="item"
                  :label="item"
                  :value="item"
                />
              </el-select>
            </div>
          </div>
          <div class="search-duration">
            <div class="duration-list">
              <div class="item-serach">
                <span>频道总时长：</span>
                <div>{{ getTime(channel_total_duration)||0 }}</div>
              </div>
              <div class="item-serach">
                <span>正片总净时长：</span>
                <div>{{ getTime(zp_total_duration )||0 }}</div>
              </div>
              <div class="item-serach">
                <span>广告总时长：</span>
                <div>{{ getTime(ad_total_duration )||0 }}</div>
              </div>
            </div>
            <div class="checked">
              <el-radio v-model="ad_show" :label="1">显示全部</el-radio>
              <el-radio v-model="ad_show" :label="2">只展示广告</el-radio>
              <el-radio v-model="ad_show" :label="3">不展示广告</el-radio>
            </div>
            <div class="btns">
              <base-btn @click="getApprovedList()">查询</base-btn>
              <base-btn @click="showAddModel">新增片段</base-btn>
              <el-button type="primary" size="small" @click="exportData('channel')">导出品控表</el-button>
              <base-btn @click="exportData('all')">导出全部EPG数据</base-btn>
              <el-button type="primary" size="small" @click="batchAudit">批量审核通过</el-button>
            </div>
          </div>
        </div>
      </div>
      <div class="table-list">
        <el-table
          ref="multipleTable"
          v-loading="loading"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%;"
          border="border"
          :row-class-name="tableRowClassName"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" min-width="55" :selectable="selectEnable" />
          <el-table-column prop="type" label="类型" min-width="80">
            <template slot-scope="scope">
              <div
                :class="[scope.row.type === '广告'&&'advt-tag',scope.row.type === '正片'&&'advt-bg','type-tag']"
              >{{ scope.row.type }}</div>
            </template>
          </el-table-column>
          <el-table-column prop="id" label="id" min-width="110" />
          <el-table-column prop="source" label="片段来源" min-width="100" />
          <el-table-column prop="name" label="时间" min-width="180">
            <template slot-scope="scope">
              <div>
                <span>{{ getDateTime(scope.row.start_time) }}</span>
                <span>-</span>
                <span>{{ getDateTime(scope.row.end_time) }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="title" label="标题" min-width="250">
            <template slot-scope="scope">
              <span v-if="scope.row.type=='正片'">{{ scope.row.title }}</span>
            </template>
          </el-table-column>
          <el-table-column label="净时长" min-width="100">
            <template slot-scope="scope">
              {{ getTime(scope.row.duration-scope.row.ad_duration).split('.')[0] }}
            </template>
          </el-table-column>
          <el-table-column prop="ad_duration" label="含广告时长" min-width="100">
            <template slot-scope="scope">
              <span v-if="scope.row.type=='正片'">
                {{ getTime(scope.row.ad_duration).split('.')[0] }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="一级分类" min-width="100">
            <template slot-scope="scope">
              <span v-if="scope.row.type=='正片'">
                {{ getCategory(scope.row,'first').join('、') }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="二级分类" min-width="100">
            <template slot-scope="scope">
              <span v-if="scope.row.type=='正片'">
                {{ getCategory(scope.row,'second').join('、') }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="updated_at" label="更新时间" min-width="180" />
          <el-table-column prop="taskStatus" label="任务状态" min-width="100" />
          <el-table-column prop="status" label="审核状态" min-width="100">
            <template slot-scope="scope">
              <template>
                <div v-if="scope.row.type!=='空白'">
                  <el-button
                    v-if="scope.row.status==='待审核'"
                    size="mini"
                    type="primary"
                    :disabled="scope.row.taskStatus==='未完成'&&scope.row.source!=='审核录入'"
                    @click="approved(scope.row)"
                  >审核通过</el-button>
                  <a v-else>{{ scope.row.status }}</a>
                </div>
              </template>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="操作" min-width="150">
            <template slot-scope="scope">
              <div>
                <base-btn
                  class="start-task"
                  type="text"
                  :disabled="scope.row.taskStatus==='未完成'&&scope.row.source!=='审核录入'"
                  @click="update(scope.row,scope.$index)"
                >修改</base-btn>
                <base-btn
                  class="end-task"
                  type="text"
                  :disabled="scope.row.taskStatus==='未完成'"
                  @click="deleteTask(scope.row.id)"
                >删除</base-btn>
              </div>
            </template>
          </el-table-column>
        </el-table>
      <!-- <base-pag
        :total="total"
        :limit="limit"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      /> -->
      </div>
      <ExamineModel
        v-if="modelShow"
        ref="examineModel"
        :model-data="tableData"
        :current-index="currentIndex"
        @submit="getApprovedList()"
        @close="close"
      />
      <AddModel
        v-if="showAddDialog"
        :show="showAddDialog"
        :channel-id="form.channel_id"
        @close="closeAddModel"
        @submit="getApprovedList()"
      />

    </div>
  </keep-alive>
</template>

<script>
import { mapGetters } from 'vuex'
import ExamineModel from './model/ExamineModel'
import { timesToHMS } from '../../../js/times'
import { parseTime } from '@/utils/index.js'
import axios from '@/axios/request.js'

export default {
  name: 'ExamineTask',
  components: {
    ExamineModel,
    AddModel: () => import('./model/AddDialog')
  },
  props: {},
  data() {
    return {
      form: {
        channel_id: '',
        date: '',
        status: '',
        title: '',
        firstCategory: '',
        secondCategory: ''
      },
      modelShow: false,
      page: 1,
      limit: 500,
      loading: false,
      ad_show: 3,
      status_options: ['全部', '已审核', '待审核', '审核中'],
      currentStatus: '',
      tableData: [],
      ad_total_duration: 0,
      channel_total_duration: 0,
      zp_total_duration: 0,
      modelData: [],
      multipleSelection: [],
      currentIndex: null,
      showAddDialog: false
    }
  },
  computed: {
    ...mapGetters([
      'categoryData', 'channelData', 'token'
    ]),
    channelName() {
      const arr = this.channelData.filter(item => {
        return item.id === this.form.channel_id
      })
      return arr[0].name
    }
  },
  watch: {
    ad_show() {
      this.getApprovedList()
    }
  },
  mounted() {

  },
  async created() {
    if (!this.channelData.length) {
      await this.$store.dispatch('channel/getChannelData')
    }
    this.form.channel_id = Number(this.$route.query.id) || this.channelData[0].id
    this.form.date = this.$route.query.date || this.getNowFormatDate()
    this.getApprovedList()
  },
  methods: {
    scrollToActive() {
      this.ad_show = 1
      this.$nextTick(() => {
        const tid = parseInt(this.$route.query.tid)
        const top = this.tableData.findIndex(item => item.id === tid) * 40
        this.$refs.multipleTable.bodyWrapper.scrollTop = top
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = []
      val.forEach((item) => {
        this.multipleSelection.push(item.id)
      })
    },
    update(row, index) {
      this.currentStatus = row.status
      this.currentIndex = index
      row.status = '审核中'
      this.modelData = JSON.parse(JSON.stringify(this.tableData))
      this.modelShow = true

      this.$nextTick(() => {
        this.$refs.examineModel.open()
        // setTimeout(() => {
        this.$refs.examineModel.$nextTick(() => {
          this.$bus.$emit('getChannelId', this.form.channel_id)
        })

        // }, 1000)
      })
    },
    approved(row) {
      const params = {
        status: '已审核',
        epg_ids: [row.id]
      }
      this.$post('/epg/epg-status', params).then((res) => {
        this.$message({
          type: 'success',
          message: res.msg
        })
        this.getApprovedList()
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    getTime(time) {
      return timesToHMS(time)
    },
    getDateTime(time) {
      return parseTime(time)
    },
    getApprovedList() {
      this.loading = true
      const params = {
        channel_id: this.form.channel_id,
        status: this.form.status === '全部' ? '' : this.form.status,
        day: this.form.date,
        page: this.page,
        limit: this.limit,
        keyword: '',
        ad_show: 1,
        type: null
      }
      this.$get('/epg/epg-list', params).then((res) => {
        let list = res.data.list
        this.ad_show === 2 && (list = list.filter(item => item.type === '广告'))
        this.ad_show === 3 && (list = list.filter(item => item.type !== '广告'))

        this.tableData = list
        this.loading = false
        this.ad_total_duration = res.data.ad_total_duration
        this.channel_total_duration = res.data.channel_total_duration
        this.zp_total_duration = res.data.zp_total_duration
        if (this.$route.query.tid) {
          this.scrollToActive()
        }
      }).catch((error) => {
        this.loading = false
        console.log(error)
      })
    },
    handleCurrentChange(val) {
      this.page = val
    },
    handleSizeChange(val) {
      this.limit = val
    },
    // 获取列表分类
    getCategory(obj, type) {
      const arr = []
      const category = obj[type + '_customer_categories']

      if (category && category.length) {
        category.forEach((item) => {
          arr.push(item.name)
        })
      } else {
        return ['无']
      }
      return arr
    },
    getNowFormatDate() {
      var date = new Date()
      var seperator1 = '-'
      var year = date.getFullYear()
      var month = date.getMonth() + 1
      var strDate = date.getDate()
      if (month >= 1 && month <= 9) {
        month = '0' + month
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = '0' + strDate
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate
      return currentdate
    },
    // 批量审核
    batchAudit: window.lodash.throttle(function() {
      const params = {
        status: '已审核',
        epg_ids: this.multipleSelection
      }
      if (this.multipleSelection.length) {
        this.$post('/epg/epg-status', params).then((res) => {
          this.$message({
            type: 'success',
            message: res.msg
          })
          this.getApprovedList()
        }).catch((error) => {
          this.$message.error(error.msg)
        })
      } else {
        this.$message('请选择审核任务')
      }
    }, 2000),
    // 导出
    exportData(type) {
      const channel_id = type === 'all' ? null : this.form.channel_id
      const channelName = type === 'all' ? '全部' : this.channelName
      const status = this.form.status === '全部' || this.form.status === '' ? null : this.form.status

      axios({
        method: 'get',
        url: '/epg/epg-export',
        headers: {
          token: this.token
        },
        params: {
          channel_id: channel_id,
          day: this.form.date,
          status: status
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
            link.setAttribute('download', channelName + '_' + this.form.date + '-EPG业务品控表.xlsx')
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
    selectEnable(row, rowIndex) {
      if (row.taskStatus !== '已完成' || row.type === '空白') {
        return false
      }
      return true
    },
    deleteTask(id) {
      const params = {
        epg_id: id,
        action: 'delete'
      }
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$post('/epg/epg-modify', params).then((res) => {
          this.$message({
            type: 'success',
            message: '删除成功'
          })
          this.getApprovedList()
        }).catch((error) => {
          this.$message.error(error.msg)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    close() {
      this.tableData[this.currentIndex].status = this.currentStatus
      this.modelShow = false
      this.currentIndex = null
    },
    // 关闭新增片段弹窗
    closeAddModel() {
      this.showAddDialog = false
    },
    // 新增片段
    showAddModel() {
      this.showAddDialog = true
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.id === Number(this.$route.query.tid)) {
        return 'row-active'
      }
    }
  }
}
</script>

<style lang='scss' scoped>
  .app-main .contain .examine-task{
    height: calc(100vh - 90px);
    display: flex;
    flex-direction: column;
    .search-top {
      padding: 10px;
      .search-duration{
        display: flex;
        justify-content: space-between;
        flex-wrap: wrap;
        margin-left: 30px;
        line-height: 40px;
        .item-serach{
          display: flex;
        }
        .btns{
          padding-right: 60px;
          justify-content: flex-end;
        }
        .duration-list{
            display: flex;
            font-size: 14px;
            div{
              margin-right: 10px;
            }
          }
      }
      .search-list{
        display: flex;
        flex-wrap: wrap;
          .item-serach{
            display: flex;
            ::v-deep.el-input__inner{
              width: 240px;
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
    }
    .table-list{
      display: flex;
      flex: 1;
      flex-direction: column;
      height: 0;
      .videoPaly{
        width: 200px;
      }
      .start-task{
        font-size: 14px;
        color: #167CFF;
        margin-right: 10px;
        font-weight: 500;
      }
      .advt-tag{
        color:#167cff;
      }
      .type-tag{
        padding-left: 10px;
      }
      .advt-bg{
        background: rgba(243,126,1,1);
      }
      ::v-deep .el-table{
        .el-table__body-wrapper {
          height: calc(100% - 40px);
          overflow-y: auto;
          tr td:nth-child(2){
            .cell{
              padding: 0;
            }
          }
          .el-table__cell {
              padding: 0;
          }
          .row-active{
            background: #fd848e;
          }
        }
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
