<!--
 * @Author: your name
 * @Date: 2021-02-18 11:39:49
 * @LastEditTime: 2021-08-23 18:37:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/Examine.vue
-->
<template>
  <div class="examine-task">
    <div class="search-top">
      <div>
        <div class="search-list">
          <div class="item-serach">
            <span class="item-label">频道:</span>
            <el-select v-model="form.channel_id" filterable placeholder="请选择频道" style="width:100%" clearable>
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
              clearable
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
          <div class="item-serach">
            <span class="item-label">类型:</span>
            <el-select v-model="form.type" filterable clearable placeholder="请选择类型" style="width:100%">
              <el-option
                v-for="item in type_options"
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
              <span>广告总时长：</span>
              <div>{{ getTime(ad_total_duration )||0 }}</div>
            </div>
          </div>
          <div class="btns">
            <base-btn @click="search()">查询</base-btn>
            <el-button v-if="showBtn" type="primary" size="small" @click="exportData">导出品控表</el-button>
            <el-button type="primary" size="small" @click="batchAudit">批量审核通过</el-button>
            <base-btn @click="showAddModal">新增广告</base-btn>
            <base-upload
              v-if="showBtn"
              class="upload-btn"
              button-name="删除广告"
              file-type=".xlsx"
              @getFileUrl="deleteAd"
            />
            <base-btn v-if="isOverTime(getFormTime())&&showBtn" @click="batchAddSample">添加样本</base-btn>
            <base-upload
              v-if="showBtn"
              class="upload-btn"
              button-name="导入"
              file-type=".xlsx"
              @getFileUrl="getFileUrl"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="table-list">
      <el-table
        ref="multipleTable"
        v-loading="loading"
        class="my-table"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%;"
        border="border"
        :row-class-name="tableRowClassName"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" min-width="55" :selectable="selectEnable" />
        <el-table-column prop="type" label="类型">
          <template slot-scope="scope">
            <span :class="scope.row.type=='广告'&&'special-tag'">{{ scope.row.type }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="id" label="广告Id" min-width="100" />
        <el-table-column prop="epg_id" label="片段Id" min-width="100" />
        <el-table-column prop="sample_id" label="样本Id" min-width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.type=='非广告'">-</span>
            <div v-else>
              <base-btn v-if="scope.row.sample_id&&scope.row.sample_id!==0" type="text" @click="playSample(scope.row)">{{ scope.row.sample_id }}</base-btn>
              <span v-else>无</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="source" label="数据来源" min-width="100" />
        <el-table-column prop="name" label="时间" min-width="180">
          <template slot-scope="scope">
            <div>
              <span>{{ getDateTime(scope.row.start_time) }}</span>
              <span>-</span>
              <span>{{ getDateTime(scope.row.end_time) }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="标题" min-width="400">
          <template slot-scope="scope">
            <span v-if="scope.row.type==='广告'">
              {{ scope.row.title }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="净时长" min-width="100">
          <template slot-scope="scope">
            {{ getTime(scope.row.duration).split('.')[0] }}
          </template>
        </el-table-column>
        <el-table-column prop="tags" label="分类" min-width="100" />
        <el-table-column prop="updated_at" label="更新时间" min-width="180" />
        <el-table-column prop="status" label="审核状态" min-width="100">
          <template slot-scope="scope">
            <template>
              <div v-if="scope.row.type !=='非广告'">
                <el-button
                  v-if="scope.row.status==='待审核'"
                  size="mini"
                  type="primary"
                  :disabled="scope.row.type==='非广告'"
                  @click="approved(scope.row)"
                >审核通过</el-button>
                <a v-else>{{ scope.row.status }}</a>
              </div>
            </template>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="操作" min-width="220">
          <template slot-scope="scope">
            <div>
              <base-btn class="start-task" type="text" @click="update(scope.row,scope.$index,$event)">修改</base-btn>
              <base-btn
                v-if="scope.row.type=='广告'&&scope.row.source!=='机器录入'&&isOverTime(scope.row.start_time)"
                type="text"
                class="start-task"
                @click="addSample([scope.row.id])"
              >添加样本</base-btn>
              <base-btn
                v-if="scope.row.type !=='非广告'"
                class="end-task"
                type="text"
                @click="deleteTask(scope.row.id)"
              >删除</base-btn>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- <base-pag
        :total="total"
        :limit="limit"
        :page="page"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      /> -->
    </div>
    <NewAdvt
      v-if="showAddDialog"
      :show="showAddDialog"
      :channel-id="form.channel_id"
      @close="closeAddModal"
      @submit="getApprovedList()"
    />
    <ExamineModel
      v-if="modelShow"
      ref="examineModel"
      :model-data="tableData"
      :current-index="currentIndex"
      @submit="getApprovedList()"
      @close="close"
    />

    <PlaySample
      v-if="showPlayModel"
      :show="showPlayModel"
      :video-url="playModelData.mp4_url"
      @close="closePlayModel"
    >
      <div class="model-text">
        <p>样本 ID ：{{ playModelData.id }}</p>
        <p>标题 ：{{ playModelData.title }}</p>
        <p>分类 ：{{ playModelData.tags }}</p>
        <p>品牌 ：{{ playModelData.brand }}</p>
        <p>样本来源 ：{{ playModelData.channel_name }} {{ playModelData.start_time|unixToHms }} - {{ playModelData.end_time|unixToHms }}</p>
      </div>
    </PlaySample>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import ExamineModel from '../model/ExamineModel'
import { timesToHMS } from '../../js/times'
import { parseTime } from '@/utils/index.js'
import axios from '@/axios/request.js'
import NewAdvt from '../model/NewAdvt'
import PlaySample from '../../sample_manage/components/PlaySample'
// const self = this
export default {
  name: 'ExamineTask',
  components: { ExamineModel, NewAdvt, PlaySample },
  props: {},
  data() {
    return {
      form: {
        channel_id: '',
        date: '',
        status: '',
        type: '',
        title: '',
        firstCategory: '',
        secondCategory: ''
      },
      modelShow: false,
      showAddDialog: false,
      showPlayModel: false,
      page: 1,
      limit: 20,
      total: 0,
      loading: false,
      status_options: ['全部', '已审核', '待审核', '审核中'],
      type_options: ['广告', '非广告'],
      currentStatus: '',
      tableData: [],
      channel_total_duration: 0,
      ad_total_duration: 0,
      modelData: [],
      playModelData: {},
      multipleSelection: [],
      currentIndex: null,
      addSampleData: [],
      sample_date: ''
    }
  },
  computed: {
    ...mapGetters([
      'categoryData', 'channelData', 'token'
    ]),
    channelName() {
      if (!this.form.channel_id) return ''
      const arr = this.channelData.filter(item => {
        return item.id === this.form.channel_id
      })
      return arr[0].name
    },
    showBtn() {
      return this.$store.state.user.userInfo.is_superman ||
      this.$store.state.user.userInfo.work.indexOf('广告审核管理员') !== -1
    }
  },
  watch: {},
  mounted() {
  },
  async created() {
    if (!this.channelData.length) {
      await this.$store.dispatch('channel/getChannelData')
    }
    this.form.channel_id = Number(this.$route.query.id) || this.channelData[0].id
    this.form.date = this.$route.query.date
    this.getApprovedList()
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = []
      val.forEach((item) => {
        this.multipleSelection.push(item.id)
      })
      this.addSampleData = val
    },
    update(row, index, event) {
      this.setUpdateData(row.id)
      this.currentStatus = row.status
      this.currentIndex = index
      row.status = '审核中'
      this.modelData = JSON.parse(JSON.stringify(this.tableData))
      this.modelShow = true

      this.$nextTick(() => {
        this.$refs.examineModel.open()
        setTimeout(() => {
          this.$bus.$emit('getChannelId', this.form.channel_id)
        })
      })
    },
    approved(row) {
      const params = {
        status: '已审核',
        ids: [row.id]
      }
      this.$post('/epg-ad/status', params).then((res) => {
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
    search() {
      this.sample_date = this.form.date
      this.page = 1
      this.getApprovedList()
    },
    getApprovedList() {
      this.loading = true
      const params = {
        channel_id: this.form.channel_id,
        status: this.form.status === '全部' ? '' : this.form.status,
        day: this.form.date,
        type: this.form.type,
        page: this.page,
        limit: 1000
      }
      this.$get('/epg-ad/epg-ad-list', params).then((res) => {
        this.tableData = res.data.list
        this.loading = false
        this.ad_total_duration = res.data.ad_total_duration
        this.channel_total_duration = res.data.channel_total_duration
        this.total = res.data.total
      }).catch((error) => {
        this.loading = false
        console.log(error)
      })
    },
    handleCurrentChange(val) {
      this.page = val
      this.getApprovedList()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getApprovedList()
    },
    // 获取列表分类
    getCategory(obj, type) {
      const haveItemInfo = window.lodash.has(obj, 'item_info')
      const haveI = window.lodash.has(obj, 'item_info.first_categories')
      const arr = []
      const itemCategory = haveItemInfo && haveI ? obj.item_info[type + '_categories'] : []
      const category = obj[type + '_customer_categories'] || itemCategory
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
        ids: this.multipleSelection
      }
      if (this.multipleSelection.length) {
        this.$post('/epg-ad/status', params).then((res) => {
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
    // 批量添加样本
    batchAddSample: window.lodash.throttle(function() {
      if (this.multipleSelection.length) {
        const filterData = this.addSampleData.filter((item) => item.source === '机器录入')
        if (filterData.length) {
          this.$message({
            type: 'warning',
            message: '所选样本存在数据来源为机器录入，请取消勾选'
          })
          return false
        }
        this.addSample(this.multipleSelection)
      } else {
        this.$message('请选择样本')
      }
    }, 2000),
    addSample(arr) {
      this.$confirm('添加至样本队列！', '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '去查看'
      }).then(() => {
        this.$post('/ad-sample/queue', { ad_ids: arr }).then((res) => {
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.getApprovedList()
        }).catch((error) => {
          this.$message({
            type: 'error',
            message: error.msg
          })
        })
      }).catch((action) => {
        if (action === 'cancel') {
          this.$router.push({
            path: '/advertisement/sample-queue'
          })
        }
      })
    },
    // 导出
    exportData() {
      const status = this.form.status === '全部' || this.form.status === '' ? null : this.form.status
      // if (!this.form.channel_id) {
      //   this.$message({
      //     type: 'warning',
      //     message: '请选择频道'
      //   })
      //   return
      // }
      const channel_name = this.channelName || '全部'
      axios({
        method: 'get',
        url: '/epg-ad/export',
        headers: {
          token: this.token
        },
        params: {
          channel_id: this.form.channel_id || null,
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
            link.setAttribute('download', channel_name + '-' + this.form.date + '-广告业务品控表.xlsx')
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
    // 导入
    getFileUrl(url, file) {
      var formData = new FormData()
      formData.append('excel', file)
      this.$post('/epg-ad/update-ads-by-excel', formData).then(res => {
        this.$message({
          type: 'success',
          message: res.msg
        })
        this.getApprovedList()
      }).catch((error) => {
        console.log(error)
      })
    },
    // 删除广告
    deleteAd(url, file) {
      this.$confirm('此操作将永久删除表格中广告数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.confirmDeleteAd(file)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 确认删除广告
    confirmDeleteAd(file) {
      var formData = new FormData()
      formData.append('excel', file)
      this.$post('/epg-ad/delete-ads-by-excel', formData).then(res => {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        this.getApprovedList()
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    selectEnable(row, rowIndex) {
      if (row.type === '非广告') {
        return false
      }
      return true
    },
    deleteTask(id) {
      const params = {
        data_id: id,
        action: 'delete'
      }
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$post('/epg-ad/modify', params).then((res) => {
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
    closeAddModal() {
      this.showAddDialog = false
    },
    showAddModal() {
      this.showAddDialog = true
    },
    // 播放
    playSample(obj) {
      if (obj.sample_id) {
        this.getPlayModelData(obj)
      }
    },
    getPlayModelData(obj) {
      const params = {
        sample_ids: obj.sample_id,
        show_delete: 1
      }
      this.$get('/ad-sample/search', params).then((res) => {
        this.playModelData = res.data.list[0]
        this.showPlayModel = true
      }).catch((error) => {
        this.$message({
          type: 'error',
          message: error.msg
        })
      })
    },
    // 关闭播放弹窗
    closePlayModel() {
      this.showPlayModel = false
    },
    getFormTime() {
      return new Date(this.sample_date + ' 00:00:00').getTime()
    },
    isOverTime(time) {
      if (new Date().getTime() - time > 7 * 24 * 60 * 60 * 1000) {
        return false
      }
      return true
    },
    tableRowClassName({ row }) {
      if (row.edit_status === 1) return 'isUpdate'
      return ''
    },
    setUpdateData(id) {
      this.$post('/ads-task/edit', { id: id }).then((res) => {
        this.getApprovedList()
      })
    }
  }
}
</script>

<style lang='scss' scoped>
.app-main .contain.examine-task{
  height: calc(100vh - 123px);
  display: flex;
  flex-direction: column;
  .search-top {
    padding: 10px;
    .search-duration{
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
      margin-left: 30px;
      line-height: 50px;
      margin-top: 10px;
      .item-serach{
        display: flex;
      }
      .btns{
        padding-right: 60px;
        display: flex;
        align-items: center;
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
    ::v-deep.upload-btn{
      display: inline-flex;
      align-items: center;
      height: 32px;
      line-height: 32px;
      padding: 9px 15px;
      margin-left: 10px;
      margin-right: 10px;
      background-color: #409EFF;
      border-color: #409EFF;
      border-radius: 3px;
      .btn-style{
        color: #FFF;
        line-height: 1;
      }
    }
    .search-list{
      display: flex;
      flex-wrap: wrap;
        .item-serach{
          display: flex;
          margin-top: 10px;
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
    margin-top: 20px;
    padding-bottom: 10px;
    display: flex;
    flex: 1;
    height: 0;
    flex-direction: column;
    .start-task{
      font-size: 14px;
      color: #167CFF;
      margin-right: 10px;
      font-weight: 500;
    }
    .special-tag{
      color:#167cff;
    }
    ::v-deep .el-table{
      .el-table__body-wrapper {
        height: calc(100% - 40px);
        overflow-y: auto;
        .isUpdate{
          background: #fff0df;
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
  .model-text{
    margin-top: 20px;
    p{
        color: #333;
        margin-bottom: 10px;
    }
  }
}
</style>
