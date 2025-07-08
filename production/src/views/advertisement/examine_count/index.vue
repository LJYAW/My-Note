<!--
 * @Author: your name
 * @Date: 2021-03-28 17:02:41
 * @LastEditTime: 2021-08-18 16:27:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/ExamineCount.vue
-->
<template>
  <div class="examine-count">
    <el-form :inline="true">
      <el-form-item label="频道">
        <el-select v-model="form.channel_id" filterable placeholder="请选择频道" style="width:100%" clearable>
          <el-option
            v-for="item in channelData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="播出日期">
        <el-date-picker
          v-model="form.date"
          type="date"
          placeholder="选择日期"
          style="width:100%"
          value-format="yyyy-MM-dd"
          :clearable="false"
        />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="form.status" filterable clearable placeholder="请选择状态" style="width:100%">
          <el-option
            v-for="item in status_options"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-form-item>
      <base-btn size="big" @click="search">查询</base-btn>
    </el-form>
    <div class="table-content">
      <p class="count">当前条件下共{{ total }}条数据</p>
      <el-table v-loading="loading" :data="tableData">
        <el-table-column prop="channel_name" label="频道" />
        <el-table-column prop="day" label="日期" />
        <el-table-column prop="status" label="状态" />
        <el-table-column prop="undoNums" label="待审核任务数" />
        <el-table-column prop="doneNums" label="已审核任务数" />
        <el-table-column prop="adTotalDuration" label="广告时长">
          <template slot-scope="scope">
            {{ getTime(scope.row.adTotalDuration).split('.')[0] }}
          </template>
        </el-table-column>
        <el-table-column prop="noAdTotalDuration" label="非广告时长">
          <template slot-scope="scope">
            {{ getTime(scope.row.noAdTotalDuration).split('.')[0] }}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <base-btn type="text" @click="toExamineList(scope.row.channel_id)">进入审核列表</base-btn>
            <template /></template></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { formatDate, timesToHMS } from '../js/times'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      form: {
        channel_id: null,
        date: null,
        status: null
      },
      status_options: ['等待处理', '无处理项'],
      total: 0,
      tableData: [],
      loading: true
    }
  },
  computed: {
    ...mapGetters([
      'channelData'
    ]),
    timeDefault() {
      var date = new Date()
      var defaultDate = new Date(date.getTime())
      return formatDate(defaultDate)
    }
  },
  watch: {

  },
  created() {
    if (!this.channelData.length) {
      this.$store.dispatch('channel/getChannelData')
    }
    this.form.date = this.timeDefault
    this.getData()
  },
  mounted() {

  },
  methods: {
    search() {
      this.getData()
    },
    getData() {
      const params = {
        channel_id: this.form.channel_id || null,
        day: this.form.date,
        status: this.form.status || null
      }
      this.$get('/epg-ad/ad-count-by-day', params).then((res) => {
        this.tableData = res.data.list
        this.total = res.data.list.length
        this.loading = false
      }).catch((error) => {
        console.log(error)
      })
    },
    toExamineList(id) {
      this.$router.push({
        path: '/advertisement/examine-task',
        query: {
          id: id,
          date: this.form.date
        }
      })
    },
    getTime(time) {
      return timesToHMS(time)
    }
  }
}
</script>

<style scoped lang="scss">
.examine-count{
    padding: 20px;
    .el-form-item{
        margin-right: 20px;
    }
    .table-content{
        .count{
            margin-bottom: 20px;
        }
    }
}
</style>
