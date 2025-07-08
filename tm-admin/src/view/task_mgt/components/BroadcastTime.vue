<!--
 * @Author: your name
 * @Date: 2021-01-13 15:31:57
 * @LastEditTime: 2021-07-20 10:01:05
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/views/order_mgt/components/BroadcastTime.vue
-->
<template>
  <div class="broad-cast">
    <!-- 爬取周期 -->
    <el-checkbox-group v-model="weekList">
      <el-checkbox
        v-for="(item,index) in week"
        :key="item"
        :label="index+1"
        @change="handleChange(item,index)"
      >{{ item }}</el-checkbox>

      <base-btn type="text" @click="selectAll">{{ btnStatus }}</base-btn>
    </el-checkbox-group>
    <!-- 爬取周期列表 -->
    <el-table :data="tableData" border>
      <el-table-column prop="name" label="播出日期" />

      <el-table-column label="爬取时间" width="250" class="table-column">
        <template slot="header">
          <div class="table-head">
            <span>爬取时间</span>
            <i class="iconfont el-icon-refresh" @click="syncTime" />
          </div>
        </template>

        <template slot-scope="scope">
          <div v-for="(item,index) in scope.row.time_data" :key="scope.row.date+index">
            <el-select v-model="scope.row.time_data[index]" placeholder="请选择爬取时间" size="mini">
              <el-option
                v-for="(time,timeIndex) in 24"
                :key="timeIndex"
                :label="timeIndex<10?`0${timeIndex}`:timeIndex"
                :value="timeIndex<10?`0${timeIndex}`:timeIndex"
              />
            </el-select>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <div v-for="(pres,index) in scope.row.time_data" :key="scope.row.date+'操作'+index">
            <i class="iconfont i-btn el-icon-circle-plus-outline" @click="addBroadcastTime(scope.row,index)" />
            <i class="iconfont i-btn el-icon-delete" @click="deleteBroadcastTime(scope.row,index)" />
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    broadcastArr: {
      type: Array,
      default() {
        return []
      }
    }
  },
  data() {
    return {
      btnStatus: '全选',
      weekList: [],
      tableData: [],
      broadcast_date: '',
      week: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    }
  },
  computed: {
  },
  watch: {
    broadcastArr() {
      if (this.$route.query.id) {
        this.setBroadcastArr()
      }
    }
  },
  created() {
    this.setBroadcastArr()
  },
  mounted() {
  },
  methods: {
    selectAll() {
      if (this.btnStatus === '全选') {
        this.weekList = [1, 2, 3, 4, 5, 6, 7]
        this.weekList.forEach((item, index) => {
          this.handleChange(this.week[item - 1], item - 1, 'all')
        })
        this.btnStatus = '取消全选'
      } else {
        this.weekList = []
        this.tableData = []
        this.btnStatus = '全选'
      }
    },
    timeToSec(time) {
      var s = ''

      var hour = time.split(':')[0]
      var min = time.split(':')[1]
      var sec = time.split(':')[2]

      s = Number(hour * 3600) + Number(min * 60) + Number(sec)

      return s
    },
    // 校验播放时间
    // chackTimeList() {
    //   let errMsg = ''
    //   for (let i = 0; i < this.tableData.length; i++) {
    //     const { time_data } = this.tableData[i]
    //     for (let j = 0; j < time_data.length; j++) {
    //       if (!time_data[j]) {
    //         errMsg = '请选择爬取时间'
    //         break
    //       }
    //       // if (this.timeToSec(endTime) === this.timeToSec(startTime)) {
    //       //   errMsg = '开始时间不能等于结束时间'
    //       //   break
    //       // }
    //     }
    //     if (errMsg) {
    //       break
    //     }
    //   }
    //   return errMsg
    // },
    // 播放时间
    handleChange(val, valIndex, type) {
      const index = this.tableData.findIndex(item => item.name === val)
      if (index < 0) {
        this.tableData.push({
          date: valIndex + 1,
          name: val,
          time_data: [['', '']]
        })
      } else {
        if (!type) {
          this.tableData = this.tableData.filter(item => item.name !== val)
        }
      }
      this.tableData.sort(function(a, b) {
        return a.date - b.date
      })
    },
    addBroadcastTime(item, index) {
      item.time_data.splice(index + 1, 0, '')
      // item[index + 1] = 'create'
    },
    deleteBroadcastTime(item, index) {
      if (item.time_data.length === 1) {
        // 编辑还是创建
        if (this.$route.query.id) {
          const valIndex = this.tableData.findIndex(obj => obj.id === item.id || obj.date === item.date)
          this.tableData.splice(valIndex, 1)
        } else {
          this.tableData = this.tableData.filter(obj => obj.date !== item.date)
        }
        const dateArr = []
        this.tableData.forEach(item => {
          dateArr.push(item.date)
        })
        this.weekList = [...new Set(dateArr)]
      } else {
        item.time_data.splice(index, 1)
      }
    },
    setTimeData(timeDetails) {
      const time_data = []
      timeDetails.forEach((item) => {
        time_data.push([item.start_time, item.end_time])
      })
      return time_data
    },
    // 回显播放时间
    setBroadcastArr() {
      this.broadcastArr.forEach((item) => {
        this.tableData.push({
          name: this.week[Number(item.date) - 1],
          date: item.date,
          time_data: item.time_data
        })
        this.weekList.push(Number(item.date))
      })
    },
    // 同步时间
    syncTime() {
      this.tableData.forEach(item => {
        item.time_data = JSON.parse(JSON.stringify(this.tableData[0].time_data))
      })
    }
  }
}
</script>

<style scoped lang="scss">
.broad-cast{
  width: 600px;
  ::v-deep {
    .cell{
      padding-left: 20px!important;
    }
    th,td{
      background: #fff!important;
    }
    tbody{
      .cell{
        padding-left: 20px!important;
        .i-btn{
          font-size: 18px;
          margin-right: 10px;
          height: 32px;
          line-height: 32px;
        }
      }
    }
    .el-table__body-wrapper{
      max-height: 300px;
      overflow-y: auto;
    }
    .el-select .el-input {
      width: 140px!important;
      margin:2.5px 10px 2.5px 10px;
    }
  }
  .iconfont{
    cursor: pointer;
    &:hover{
      color:#409eff
    }
  }
  .el-icon-circle-plus-outline{
    font-size: 24px;
  }
  .table-head{
    width: 100%;
    display: flex;
    justify-content: space-between;
    i{
      margin-right: 20px;
      font-size: 18px;
      cursor: pointer;
      line-height: 40px;
    }
  }
  // .el-table{
  //   border: 1px solid #eee;
  //   &::before{
  //     height: 0;
  //   }
  // }
}
</style>
