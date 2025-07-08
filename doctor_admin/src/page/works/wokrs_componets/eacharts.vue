<template>
  <div class="eacharts_wrap">
    <div class="eacharts">
      <eacharts :xAxisData="xAxisData" :seriesData="seriesProductrdata" :seriesData1="seriesUserData" />
    </div>
    <div class="count">
      <el-tabs type="border-card" v-model="activeName" @tab-click="tabclick">
        <el-tab-pane :label="item.name" v-for="(item,index) in tabs" :key="index" :name='item.value'>
          <ul class="tabData">
            <li>
              <div>
                <p>生产视频数</p>
                <span>{{tabData.video_nums}}</span>
              </div>
              <div>
                <p>使用用户数</p>
                <span>{{tabData.user_nums}}</span>
              </div>
            </li>
          </ul>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import eacharts from '../../../components/echarts_line'
import { formatDate } from '@/utils/times'

export default {
  name: '',
  data() {
    return {
      seriesProductrdata: [],
      seriesUserData: [],
      xAxisData: [],
      numDataList: [],
      tabData: {
        user_nums: '',
        video_nums: '',
      },
      activeName: '0',
      echartsParams: {
        start_date: '',
        end_date: formatDate(new Date()),
        type: '',
      },
      tabs: [
        {
          name: '全部',
          value: '0',
        },
        {
          name: '昨日',
          value: '2',
        },
        {
          name: '7日内',
          value: '7',
        },
        {
          name: '30日内',
          value: '30',
        },
      ],
    }
  },
  components: { eacharts },
  computed: {},
  watch: {},
  methods: {
    // eacharts图表展示
    async getEacharts() {
      this.xAxisData = []
      this.seriesProductrdata = []
      this.seriesUserData = []
      let data = await this.geteachartsdata()
      this.numDataList = data
      let listdata = JSON.parse(JSON.stringify(this.numDataList)).reverse()
      listdata.forEach(item => {
        this.xAxisData.push(item.date)
        this.seriesProductrdata.push(item.video_nums)
        this.seriesUserData.push(item.user_nums)
      })
      this.tabclick()
    },
    // 获取eacharts data
    geteachartsdata() {
      return new Promise((resolve, reject) => {
        this.echartsParams.type = 'list'
        this.timeSetting(7)
        this.$get('/admin/intelligent-creation/daily-statistic-sum-or-list', {
          params: this.echartsParams,
        }).then(
          res => {
            let data = res.data.data
            resolve(data)
          },
          err => {}
        )
      })
    },
    // 切换时间获取数据
    getTabdata(num) {
      this.echartsParams.type = 'sum'
      this.timeSetting(num)
      this.$get('/admin/intelligent-creation/daily-statistic-sum-or-list', {
        params: this.echartsParams,
      }).then(
        res => {
          if (res.data.code == '0000') {
            this.tabData.video_nums = res.data.data.video_nums
            this.tabData.user_nums = res.data.data.user_nums
          }
        },
        err => {}
      )
    },
    tabclick(tab, event) {
      let activeName = this.activeName
      if (activeName == '0') {
        delete this.echartsParams.start_date
        delete this.echartsParams.end_date
        activeName = ''
      } else {
        this.echartsParams.end_date = formatDate(new Date())
        activeName = Number(this.activeName)
      }
      this.getTabdata(activeName)
    },
    timeSetting(num) {
      if (num != '') {
        let start = new Date(this.echartsParams.end_date)
        start.setTime(start.getTime() - 3600 * 1000 * 24 * Number(num - 1))
        this.echartsParams.start_date = formatDate(start)
        return this.echartsParams.start_date
      }
    },
  },
  created() {
    this.getEacharts()
  },
  mounted() {},
}
</script>

<style lang="scss" scoped>
.eacharts_wrap {
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding: 10px;
  background: #ffffff;
  .eacharts {
    width: 80%;
    padding-right: 30px;
  }
  .count {
    // width: calc(100% - 60% - 60px);
    height: 300px;
    padding-left: 30px;
    box-sizing: border-box;
    border-left: 1px solid #dddddd;
    .tabData {
      li {
        display: flex;
        justify-content: space-between;
        margin-top: 60px;
        p {
          margin-bottom: 30px;
          color: #999999;
          font-size: 14px;
        }
        span {
          color: #151515;
          font-size: 28px;
          font-weight: 300;
        }
      }
    }
    /deep/ .el-tabs--border-card > .el-tabs__header .el-tabs__item.is-active {
      color: #ffffff;
      background-color: #2A79DF !important;
    }
    /deep/ .el-tabs__item {
      border-radius: 5px;
    }
    /deep/ .el-tabs--border-card {
      border: none;
      box-shadow: 0 0 black;
      margin-top: 10px;
    }
  }
}
</style>
