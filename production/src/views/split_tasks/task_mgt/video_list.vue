<!--
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-04-16 10:21:05
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
            <span class="item-label">视频标题:</span>
            <el-input v-model="form.title" placeholder="请输入视频标题" />
          </div>
          <div class="item-serach">
            <span class="item-label">转码状态:</span>
            <el-select v-model="form.status" filterable placeholder="请选择转码状态">
              <el-option
                v-for="item in status_options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="item-serach">
            <span class="item-label">推送状态:</span>
            <el-select v-model="form.push_status" filterable placeholder="请选择推送状态">
              <el-option
                v-for="item in send_options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
          <div class="btns">
            <base-btn size="big" @click="search()">搜索</base-btn>
          </div>
        </div>
      </div>
    </div>

    <div style="margin-top:20px">
      <clientStatus ref="clientStatus" />

      <el-table :data="tableData" class="table-list" border :loading="loading">
        <el-table-column prop="channel_name" label="频道" />
        <el-table-column prop="item_name" label="栏目" />
        <el-table-column prop="title" label="视频标题" />
        <el-table-column prop="status" label="转码状态" />
        <!-- <el-table-column prop="push_status" label="推送状态" /> -->
        <el-table-column prop="department_name" label="团队" />
        <el-table-column prop="executor_name" label="执行人" />
        <el-table-column prop="customers" label="客户" class-name="client-col">
          <template v-if="scope.row.status == '转码成功'" slot-scope="scope">
            <div v-for="(client,index) in scope.row.push_customers" :key="index" class="client-name">
              <el-popover
                ref="popover"
                :key="index"
                v-model="client.visible"
                trigger="click"
                placement="top"
              >
                <p style="padding:20px">{{ client.customer_name }}</p>
                <div style="text-align: right; margin: 0">
                  <el-button size="mini" type="text" @click="client.visible = false">取消</el-button>
                  <el-button type="text" size="mini" :disabled="scope.row.status!=='转码成功'" @click="resetSend(client,scope.row.isPush)">重新推送</el-button>
                </div>
                <a
                  slot="reference"
                  class="text customers-hover"
                  :style="clientStatus.find(item => item.text == client.push_status).style"
                >
                  {{ client.customer_name }}
                </a>
              </el-popover>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="" label="操作" width="160px">
          <template slot-scope="scope">
            <base-btn
              :disabled="scope.row.status !== '转码成功'"
              type="text"
              class="start-task"
              @click="playVideo(scope)"
            >播放视频</base-btn>
            <el-tooltip class="item" effect="dark" :content="scope.row.isPush?'推送开启中':'推送关闭中'" placement="top-start">
              <base-btn type="text">
                <el-switch
                  v-model="scope.row.isPush"
                  active-color="#13ce66"
                  @change="changeSwitch($event,scope)"
                />
              </base-btn>
            </el-tooltip>
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
    <playVideo ref="playVideo" />
  </div>
</template>

<script>
import playVideo from './models/play_video'
import clientStatus from '../../../components/ClientStatus/index'

export default {
  name: 'TaksMgtVideoList',
  components: {
    playVideo,
    clientStatus
  },
  data() {
    return {
      loading: true,
      form: {
        title: '',
        status: '',
        push_status: ''
      },
      total: null,
      page: 1,
      limit: 10,
      task_ids: [],
      send_options: [
        {
          label: '全部',
          value: ''
        },
        {
          label: '待推送',
          value: '待推送'
        },
        {
          label: '推送成功',
          value: '推送成功'
        },
        {
          label: '推送失败',
          value: '推送失败'
        }
      ],
      status_options: [
        {
          label: '全部',
          value: ''
        },
        {
          label: '已提交',
          value: '已提交'
        },
        {
          label: '转码中',
          value: '转码中'
        },
        {
          label: '转码成功',
          value: '转码成功'
        },
        {
          label: '转码失败',
          value: '转码失败'
        }
      ],
      tableData: [
      ]
    }
  },
  computed: {
    clientStatus() {
      return this.$refs.clientStatus.list
    },
    task_id() {
      return this.$route.query.task_id
    }
  },
  watch: {

  },
  created() {
    this.getList()
  },
  mounted() {

  },
  methods: {
    changeSwitch(val, data) {
      const obj = JSON.parse(JSON.stringify(data.row))
      this.$nextTick(() => {
        obj.isPush = val
        this.$set(this.tableData, data.$index, obj)
      })
      const params = {
        video_id: data.row.video_id,
        can_push: val ? '是' : '否'
      }
      this.$post('/task/video-can-push', params).then((res) => {
        console.log(res)
        this.$message({
          type: 'success',
          message: res.msg
        })
      }).catch((error) => {
        this.$message(error.msg)
        this.$nextTick(() => {
          obj.isPush = !val
          this.$set(this.tableData, data.$index, obj)
        })
      })
    },
    resetSend(item, switchStatus) {
      if (!switchStatus) {
        this.$message('推送开关已关闭')
        return
      }
      const params = {
        task_video_push_customer_id: item.push_customer_id
      }
      this.$post('production/push-again', params).then((res) => {
        this.$message({
          showClose: true,
          message: '推送成功',
          type: 'success'
        })
        item.visible = false
      }).catch(res => {
        this.$message({
          showClose: true,
          message: '推送失败',
          type: 'error'
        })
      })
    },
    setcustomersData() {
      this.$nextTick(() => {
        this.tableData.forEach((item, index) => {
          const arr = item.push_customers
          arr.forEach((item, j) => {
            item.visible = false
          })
        })
      })
    },
    handleCurrentChange(val) {
      this.page = val
      this.getList()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getList()
    },
    getList() {
      const params = {
        page: this.page,
        limit: this.limit,
        status: this.form.status,
        push_status: this.form.push_status,
        title: this.form.title,
        task_id: this.task_id
      }
      this.loading = true
      this.$get('/task/video-list-by-task-id', params).then((res) => {
        const { list } = res.data
        this.tableData = list
        const { total } = res.data
        this.tableData.forEach((item, index) => {
          if (item.can_push === '是') {
            item.isPush = true
          } else {
            item.isPush = false
          }
        })
        this.total = total
        this.setcustomersData()
        this.loading = false
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    search() {
      this.page = 1
      this.getList()
    },
    playVideo(scope) {
      this.$refs.playVideo.push_customers = scope.row.push_customers
      this.$refs.playVideo.created_at = scope.row.created_at
      this.$refs.playVideo.open(scope.row.video_id)
    },
    currentChange(val) {
      this.page = val
      this.getList()
    },
    deatils(scope) {}
  }
}
</script>

<style lang='scss' scoped>
.task-met{
  width: 100%;
  background: #fff;
  .pagination-list{
      width: 100%;
      text-align: right;
      margin-top: 30px;
   }
  .search-list{
    display: flex;
    padding: 15px;
    box-sizing: border-box;
    flex-wrap: wrap;
    .btns{
      margin-left: 20px;
      margin-top: 10px;
    }
    .item-serach{
      display: flex;
      margin-top: 10px;
      ::v-deep.el-input__inner{
        width: 200px;
        height: 40px;
      }
      .item-label{
        min-width: 86px;
        margin-top: 10px;
        text-align: center;
        font-size: 14px;
        color: #606266;
        font-weight: 500;
      }
    }
  }
  .table-list{
    ::v-deep .cell{
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    ::v-deep .el-table__body-wrapper .client-col {
      .cell {
        line-height: 2;
        overflow: visible;
        text-overflow: clip;
        max-height: max-content;
        white-space: normal;
      }
    }

    .client-name {
      min-height: 40px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      .text {
        // height: 20px;
        margin: 0 5px;
      }
    }
    .customers-hover{
      &:hover{
        opacity: 0.5;
      }
    }
    .warning-icon{
      font-size: 20px;
    }
     .start-task{
      font-size: 14px;
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
