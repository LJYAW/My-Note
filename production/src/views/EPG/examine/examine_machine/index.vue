<!--
 * @Author: your name
 * @Date: 2021-04-14 10:29:01
 * @LastEditTime: 2021-04-19 15:22:12
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/examine_count/examine_machine/index.vue
-->
<template>
  <div class="examine-machine">
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
      <base-btn size="big" @click="search">查询</base-btn>
      <p>
        更新时间:{{ updated_at }}
        <base-btn class="search-btn" @click="getUpdateData">触发数据分析</base-btn>
      </p>
    </el-form>

    <div v-loading="loading" class="machine-wrap">
      <el-tabs v-model="activeName" tab-position="left" @tab-click="handleClick">
        <el-tab-pane v-for="(item,index) in tabList" :key="index" :label="`${item.title}(${item.num})`" :name="String(item.title_id)" />
        <div v-if="!tableData.length" class="no-data">
          <p>暂无数据</p>
        </div>
        <el-table v-else v-loading="table_loading" :data="tableData" height="calc(100vh - 235px)">
          <el-table-column
            prop="channel_name"
            label="频道"
            width="150"
          />
          <el-table-column
            prop="duration"
            label="时长"
            width="90"
          >
            <template slot-scope="scope">
              {{ getTime(scope.row.duration-scope.row.ad_duration).split('.')[0] }}
            </template>
          </el-table-column>
          <el-table-column
            prop="title"
            label="标题"
          />
          <el-table-column
            label="片段"
            width="160"
          >
            <template slot-scope="scope">
              {{ getDateTime(scope.row.start_time) }}-{{ getDateTime(scope.row.end_time) }}
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            width="80"
          >
            <template slot-scope="scope">
              <base-btn type="text" @click="toDetail(scope.row)">查看详情</base-btn>
            </template>
          </el-table-column>
        </el-table>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { formatDate } from '../../js/times'
import { timesToHMS } from '../../js/times'
import { parseTime } from '@/utils/index.js'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      form: {
        channel_id: null,
        date: ''
      },
      tableData: [],
      tabList: [],
      activeName: null,
      updated_at: '',
      loading: true,
      table_loading: true
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
    handleClick() {
      this.getInfoList()
    },
    getData() {
      this.loading = true
      const params = {
        date: this.form.date,
        channel_id: this.form.channel_id || null
      }
      this.$get('/examine/epg-list', params).then((res) => {
        this.updated_at = res.data.updated_at
        this.tabList = res.data.list
        this.activeName = this.tabList.length ? String(this.tabList[0].title_id) : null
        this.tableData = []
        if (this.activeName) {
          this.getInfoList()
        }
      }).catch((error) => {
        console.log(error)
      }).finally(() => {
        this.loading = false
      })
    },
    getUpdateData() {
      const params = {
        date: this.form.date,
        channel_id: this.form.channel_id || null
      }
      this.$get('/examine/epg', params).then((res) => {
        this.updated_at = res.data.updated_at
        this.tabList = res.data.list
        this.activeName = String(this.tabList[0].title_id)
        this.activeName = this.tabList.length ? String(this.tabList[0].title_id) : null
        this.tableData = []
        if (this.activeName) {
          this.getInfoList()
        }
      })
    },
    getInfoList() {
      this.table_loading = true
      const params = {
        date: this.form.date,
        title_id: this.activeName,
        channel_id: this.form.channel_id || null,
        page: 1,
        limit: 10000
      }
      this.$get('/examine/epg-info', params).then((res) => {
        this.tableData = res.data.list
      }).catch((error) => {
        console.log(error)
      }).finally(() => {
        this.table_loading = false
      })
    },
    getTime(time) {
      return timesToHMS(time)
    },
    getDateTime(time) {
      return parseTime(time)
    },
    toDetail(obj) {
      this.$router.push({
        path: '/EPG/examine-count/examine-task',
        query: {
          id: obj.channel_id,
          date: this.form.date,
          tid: obj.id
        }
      })
    }
  }
}
</script>

<style lang="scss">
.examine-machine{
  padding: 20px;
  .search-btn{
    margin-left: 20px;
  }
  .machine-wrap{
    margin-top: 20px;
    .el-tabs__item{
      padding-left: 0;
    }
    .no-data{
      display: flex;
      align-items: center;
      justify-content: center;
      min-height: 200px;
    }
  }
}
</style>
