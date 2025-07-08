<!--
 * @Author: your name
 * @Date: 2021-02-10 18:04:13
 * @LastEditTime: 2021-05-13 15:51:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/TaskMonitor.vue
-->
<template>
  <div class="task-monitor">
    <div class="search-list">
      <div class="item-serach">
        <span class="item-label">频道:</span>
        <el-select
          v-model="form.channel_id"
          filterable
          remote
          clearable
          placeholder="请选择频道"
          :loading="channelLoading"
          @focus="getChannelData"
        >
          <el-option
            v-for="item in channelData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>

      </div>
      <div class="item-serach">
        <span class="item-label">日期:</span>
        <el-date-picker
          v-model="form.date"
          :clearable="false"
          type="date"
          placeholder="选择日期"
          style="width:100%"
          value-format="yyyy-MM-dd"
        />
      </div>
      <div v-if="temp_roles" class="item-serach">
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
      <div class="item-serach checked">
        <el-checkbox v-if="temp_roles" v-model="form.is_unassigned" class="is_unassigned">未派发任务</el-checkbox>
        <base-btn size="small" @click="search()">搜索</base-btn>
        <base-btn v-if="temp_roles" size="small" :loading="sendLoading" @click="sendEmail()">发送邮件</base-btn>
      </div>
    </div>
    <div class="status-list">
      <div v-for="(item,j) in statusOption" :key="j" class="item-status">
        <div class="circular" :style="{'background':item.background}" />
        <div class="item-label">{{ item.label }}</div>
      </div>
    </div>
    <div class="total">
      总共有{{ timeList.length }}个频道
    </div>
    <div v-infinite-scroll="loadTimeList" :infinite-scroll-immediate="false" class="line-list" style="overflow:auto">

      <base-loading v-if="status_loading" class="loading" />

      <div
        v-for="(item,index) in timeListTemp"
        v-else
        :key="item.broadcast_time_id + 'wrap'"
        class="item-line"
      >
        <div class="item-message">
          <div>{{ item.channel_name }}</div>
          <div class="implement">
            <div>
              <span>执行团队:</span>
              <span>{{ item.department?item.department:'无' }}</span>
            </div>
            <div style="margin-left:15px">
              <span>完成进度:</span>
              <span class="proportion">{{ item.compelte_proportion?item.compelte_proportion:0+'%' }}</span>
            </div>
            <div v-if="temp_roles" class="btns">
              <base-btn
                size="mini"
                @click="assgin(item)"
              >派发任务</base-btn>
            </div>
          </div>
        </div>

        <div class="time-box">
          <timeLine
            ref="timeLine"
            :key="item.broadcast_time_id + 'timeLine'"
            :range="range"
            :duration="duration"
            :on-parent="true"
          >
            <div v-for="(date) in dateTrack[index]" :key="date.task_id">
              <div
                slot="reference"
                class="item-progress"
                :style="date.style"
                :class="tabIndex===date.task_id?'tabActive':''"
                @click="showTimeDetails(date,$event,index)"
              />
            </div>
          </timeLine>
        </div>
      </div>

      <div v-if="timeList.length===0 && !status_loading" class="nodata">
        <div>暂无数据</div>
        <img src="../../../assets/images/nodata.png" alt="">
      </div>

    </div>

    <AssginTask ref="assginTask" :aggin-obj="agginObj" />

    <BaseTip
      :show.sync="showDetails"
      :position="showPosition"
      @close="close"
    >
      <ShowDateis :active-date="activeDate" :show-dateis-item="showDateisItem" @playFlow="playFlow" />
    </BaseTip>

    <PlayVideoFlow
      v-if="showFlow"
      ref="playVideoFlow"
      :show="showFlow"
      :video-data="videoData"
      @playFolwShow="playFolwShow"
    />
  </div>
</template>

<script>
import timeLine from '../components/time_line/index'
import AssginTask from '../model/AssginTask'
import { formatDate, timeToS } from '../js/times'
import { mapGetters } from 'vuex'
import to from '@/utils/to-promise.js'
import ShowDateis from './showDateilsModel/ShowDateis'
import PlayVideoFlow from './showDateilsModel/PlayVideo'
export default {
  name: 'TaskMonitor',
  components: { timeLine, AssginTask, ShowDateis, PlayVideoFlow },
  props: {},
  data() {
    return {
      tabIndex: 0,
      videoData: {},
      showFlow: false,
      sendLoading: false,
      channelLoading: false,
      showDetails: false,
      showPosition: {},
      activeDate: {},
      showDateisItem: {},
      duration: 1440000,
      falg: '',
      range: 7,
      timeList: [],
      timeListTemp: [],
      // 挂起中 已完成 进行中 待分配 等待中
      agginObj: {
        broadcastId: '',
        date: ''
      },
      statusOption: [
        {
          label: '挂起中',
          value: '挂起中',
          background: 'rgb(232 78 78)'
        },
        {
          label: '已关闭',
          value: '已关闭',
          background: '#3737b3'
        },
        {
          label: '已完成',
          value: '已完成',
          background: '#3aab3a'
        },
        {
          label: '进行中',
          value: '进行中',
          background: 'orange'
        },
        {
          label: '待分配',
          value: '待分配',
          background: '#ddd'
        },
        {
          label: '等待中',
          value: '等待中',
          background: '#fff'
        }
      ],
      form: {
        channel_id: null,
        team: null,
        date: formatDate(new Date()),
        is_unassigned: false
      },
      dateTrack: [],
      status_loading: true
    }
  },
  computed: {
    ...mapGetters(['channelData', 'teamData', 'roles']),
    temp_roles() {
      if (this.roles.find(value => value === 'superman')) {
        return true
      } else {
        return false
      }
    }
  },
  watch: {
  },
  mounted() {
    const targetNode = document.querySelector('.sidebar-container')
    targetNode.addEventListener('transitionend', this.handle, false)
    window.addEventListener('resize', this.setData)
  },
  beforeDestroy() {
    const targetNode = document.querySelector('.sidebar-container')
    window.removeEventListener('resize', this.setData, false)
    targetNode.removeEventListener('transitionend', this.handle, false)
  },
  async created() {
    await this.setTimeList()
    this.getChannelData()
  },
  methods: {
    playFlow(item) {
      this.videoData.url = item.url
      this.showFlow = true
    },
    playFolwShow() {
      this.showFlow = false
      this.videoUrl = {}
    },
    sedDate() {
      const date = new Date()
      const preDate = new Date(date.getTime() - 24 * 60 * 60 * 1000) // 前一天
      return formatDate(preDate)
    },
    getEmail() {
      this.sendLoading = true
      const params = {
        day: this.sedDate()
      }
      this.$get('/epg/send-excel-email', params).then((res) => {
        this.$message({
          message: '发送成功',
          type: 'success'
        })
        this.sendLoading = false
      }).catch((error) => {
        this.$message.error(error.msg)
      }).finally(() => {
        this.sendLoading = false
      })
    },
    // 发送邮件
    sendEmail() {
      this.$confirm('是否发送邮件?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        this.getEmail()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消发送'
        })
      })
    },
    loadTimeList() {
      const length = this.timeListTemp.length
      this.timeListTemp = this.timeListTemp.concat(this.timeList.slice(length, length + 5))
      console.log('设置初始数据完成')
    },
    close() {
      // this.showDetails = false
      this.activeDate = {}
      this.showDateisItem = {}
    },
    showTimeDetails(item, e, index) {
      this.tabIndex = item.task_id
      const { x, y } = e
      this.showPosition = {
        left: x + 'px',
        top: y + 'px'
      }
      this.videoData.channel_name = this.timeListTemp[index].channel_name
      this.videoData.start_time = item.start_time
      this.videoData.end_time = item.end_time
      this.showDateisItem = item
      this.showDetailsData(item.task_id)
      this.showDetails = true
    },
    showDetailsData(id) {
      this.$get('/epg-task/point', { task_id: id }).then((res) => {
        this.activeDate = res.data
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    async getChannelData() {
      if (this.channelData.length !== 0) return
      this.channelLoading = true
      await this.$store.dispatch('channel/getChannelData')
      this.channelLoading = false
      console.log('获取 数据 加载完成')
    },
    handle() {
      this.setData()
    },
    setData() {
      if (this.timeList.length !== 0) {
        this.$refs.timeLine.forEach(dom => {
          dom.setTimeWidth()
        })
        this.setDateTrack()
      }
    },
    setDateTrack() {
      this.$nextTick(() => {
        if (this.timeList.length !== 0) {
          const PER_PX_MS = this.$refs.timeLine[0].PER_PX_MS
          this.dateTrack = []
          var lengthArr = []
          let length = ''
          this.timeList.forEach((itemList, index) => {
            const arr = itemList.tasks
            this.dateTrack[index] = []
            lengthArr = []
            arr.forEach((item, j) => {
              if (item.end_time === '00:00:00') {
                item.end_time = '24:00:00'
              }
              const width = (this.timeToS(item.end_time) - this.timeToS(item.start_time)) / PER_PX_MS
              const color = this.statusOption.find(data => data.value === item.status).background
              if (item.status === '已完成') {
                lengthArr.push(item)
                length = lengthArr.length / arr.length
                itemList.compelte_proportion = parseInt(length * 100) + '%'
              }
              const obj = Object.assign({
                style: {
                  width: width + 'px',
                  height: '20px',
                  transform: 'translateX(' + this.timeToS(item.start_time) / PER_PX_MS + 'px)',
                  backgroundColor: color
                }
              }, item)
              this.dateTrack[index].push(obj)
            })
          })
        }
      })
    },
    timeToS(str) {
      return timeToS(str)
    },
    search() {
      this.setTimeList()
    },
    async setTimeList() {
      this.status_loading = true
      const params = {
        channel_id: this.form.channel_id || null,
        // team: this.form.team || null,
        date: this.form.date,
        is_unassigned: this.form.is_unassigned,
        department_id: this.form.team
      }
      const [err, data] = await to(this.getList(params))
      if (err) {
        this.$message.error(err.msg)
        return
      }

      this.timeList = data
      this.timeListTemp = []

      this.status_loading = false
      this.setDateTrack()
      this.loadTimeList()
      console.log('DOM 加载完成')
    },
    getList(params) {
      return new Promise((resolve, reject) => {
        this.$get('/epg-task/epg-task-control', params).then((res) => {
          resolve(res.data)
        }).catch((error) => {
          reject(error)
        })
      })
    },
    assgin(item) {
      this.agginObj = {
        broadcastId: item.broadcast_time_id,
        date: item.date
      }
      this.$refs.assginTask.open()
    }
  }
}
</script>

<style lang='scss' scoped>
 .popove-box{
    .item-popove{
      display: flex;
      font-size: 14px;
      margin-bottom: 10px;
      span{
        width: 100px;
      }
    }
  }
.task-monitor{
    height: calc(100vh - 100px);
    padding: 10px;
    display: flex;
    flex-direction: column;
    .total{
      font-size: 16px;
    }
    .circular{
      width: 18px;
      height:18px;
      border: 1px solid #ddd;
      border-radius: 3px;
    }
    .search-list{
        display: flex;
        flex-wrap: wrap;
        margin-top: 10px;
        .checked{
          margin-left: 20px;
            .is_unassigned{
              margin-top: 10px;
              margin-right: 10px;
            }
        }
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
    .status-list{
        display: flex;
        justify-content: flex-end;
        margin-top: 20px;
        .item-status{
            display: flex;
            line-height: 20px;
            margin-right: 10px;
            font-size: 12px;
            color: #333;
            .item-label{
                margin-left: 10px;
            }
        }
    }
    .line-list{
       margin-top: 20px;
       overflow: hidden;
       padding-bottom: 100px;
       .loading {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
       }
       .nodata{
         position: relative;
         width: 600px;
         height: 600px;
         margin: 30px auto;
         text-align: center;
         div{
           position: absolute;
           top: 210px;
           left: 280px;
           font-size: 14px;
           color: #5e99e7;
         }
         img{
           width: 100%;
           height: 100%;
           object-fit: contain;
         }
       }
       .item-line{
         position: relative;
         margin-top:30px;
         .time-box{
          width: 100%;
          overflow: auto;
          margin-top: 10px;
         }
          .tabActive{
            border:2px solid #000 !important;
            z-index: 100;
          }
         .item-progress{
            position: absolute;
            border: 1px solid #000;
            border-radius: 5px;
          }
         .item-message{
           display: flex;
           justify-content: space-between;
           align-items: flex-end;
            .implement{
                display:flex;
                align-items: flex-end;
                justify-content: center;
                .proportion{
                  color: red;
                }
                .btns{
                  margin-left: 10px;
                }
            }
         }
       }
    }
}
</style>
