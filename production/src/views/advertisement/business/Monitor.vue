<!--
 * @Author: your name
 * @Date: 2021-02-10 18:04:13
 * @LastEditTime: 2021-08-02 10:16:33
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/TaskMonitor.vue
-->
<template>
  <div class="task-monitor">
    <div>
      <numsList ref="numsList" :num-data="numData" />
    </div>
    <div class="search-list">
      <div v-if="isShow()" class="item-serach">
        <span class="item-label">执行团队:</span>
        <el-select v-model="form.department_id" filterable clearable placeholder="请选择执行团队" style="width:100%">
          <el-option
            v-for="item in teamData"
            :key="item.id"
            :label="item.department_name"
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
      <div class="item-serach btns">
        <base-btn size="big" @click="search()">搜索</base-btn>
      </div>
    </div>
    <!-- <div class="status-list">
      <div v-for="(item,index) in statusOption" :key="index" class="item-status">
        <div class="circular" :style="{'background':item.background}" />
        <div class="item-label">{{ item.label }}</div>
      </div>
    </div> -->
    <div class="table-data">
      <el-table
        v-loading="status_loading"
        :data="tableData"
        class="table-list"
        border
        :default-sort="{prop: 'incomplete_num', order: 'descending'}"
        @sort-change="changeTableSort"
      >
        <el-table-column prop="channel_name" label="频道名称" />
        <el-table-column prop="incomplete_num" label="未完成任务数" sortable />
        <el-table-column prop="start_time" label="任务时间" sortable />
        <el-table-column prop="process_time" label="处理时间" />
        <el-table-column prop="updated_at" label="更新时间" />
        <el-table-column prop="department_name" label="执行团队" />
      </el-table>
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
import { formatDate } from '../js/times'
export default {
  name: 'TaskMonitor',
  components: { numsList },
  props: {},
  data() {
    return {
      numData: [],
      status_loading: false,
      tableData: [
      ],
      form: {
        department_id: null,
        date: null
      },
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
      order: 'num_desc',
      page: 1,
      limit: 10,
      total: null
    }
  },
  computed: {
    ...mapGetters(['channelData', 'teamData', 'roles'])
  },
  watch: {},
  mounted() {
  },
  created() {
    this.$store.dispatch('channel/getChannelData')
    this.getList()
    this.getTjNum()
    this.$store.dispatch('user/getTeamData', { page: 1, limit: 10000 })
  },
  methods: {
    search() {
      this.getList()
    },
    isShow() {
      return this.roles.includes('superman') || this.roles.includes('广告审核') || this.roles.includes('广告审核管理员')
    },
    getList() {
      this.status_loading = true
      const params = {
        department_id: this.form.department_id || null,
        date: this.form.date,
        is_manage: true,
        order_by: this.order,
        page: this.page,
        limit: this.limit
      }
      this.$get('/ads-task/monitor-list', params).then((res) => {
        this.tableData = res.data.list
        this.total = res.data.total
        // console.log(res.data)
        this.status_loading = false
      }).catch((error) => {
        this.$message.error(error.msg)
      }).finally(() => {
        this.status_loading = false
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
    setNumData() {

    },
    changeTableSort(column) {
      const order = column.order === 'ascending' ? 'asc' : 'desc'
      const prop = column.prop === 'incomplete_num' ? 'num' : 'time'
      this.order = order + '_' + prop
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
.task-monitor{
    padding: 10px;
    .search-list{
        display: flex;
        flex-wrap: wrap;
        margin-top: 20px;
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
        .btns{
          margin-left: 20px;
        }
    }
    .table-list{
      margin-top: 20px;
    }
}
</style>
