<!--
 * @Author: your name
 * @Date: 2021-04-01 16:53:15
 * @LastEditTime: 2021-06-09 15:38:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-beautiful-template/src/view/home/content/index.vue
-->
<template>
  <div class="task-mgt">
    <!-- 任务列表条件筛选 -->
    <div class="search-wrap">
      <el-form :inline="true" label-width="70px" label-position="left" :model="form">

        <el-form-item label="频道名称">
          <el-select v-model="form.channel_id" clearable filterable placeholder="请选择频道">
            <el-option
              v-for="item in channelData"
              :key="item.channel_uuid"
              :value="item.channel_uuid"
              :label="item.name"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="栏目名称">
          <el-select v-model="form.program_id" clearable filterable placeholder="请选择栏目">
            <el-option
              v-for="item in itemData"
              :key="item.item_uuid"
              :value="item.item_uuid"
              :label="item.name"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="路径来源">
          <el-input v-model="form.source" placeholder="请输入路径来源" />
        </el-form-item>

        <el-form-item label="任务状态">
          <el-select v-model="form.doType" clearable filterable placeholder="请选择任务状态">
            <el-option
              v-for="(value,key) in baseData.statusData"
              :key="key"
              :value="key"
              :label="value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="资源类型">
          <el-select v-model="form.resourcetype" clearable filterable placeholder="请选择资源类型">
            <el-option
              v-for="(item,index) in baseData.typeData"
              :key="item"
              :value="index+1"
              :label="item"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="业务">
          <el-select v-model="form.business" clearable filterable placeholder="请选择任务业务">
            <el-option
              v-for="item in baseData.workData"
              :key="item"
              :value="item"
              :label="item"
            />
          </el-select>
        </el-form-item>

        <el-form-item class="btn-item">
          <base-btn @click="search">查询</base-btn>
          <base-btn @click="clearSearch">清除</base-btn>
          <base-btn @click="exportData">导出</base-btn>
          <base-btn v-has="['superman','任务管理']" class="add-btn" @click="addTask">新增任务</base-btn>
        </el-form-item>
      </el-form>
    </div>
    <!-- 任务列表 -->
    <taskTable
      v-loading="loading"
      :task-data="taskData"
      :pag="pag"
      @currentChange="handleCurrentChange"
      @sizeChange="handleSizeChange"
      @updateData="getTaskList"
    />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import baseData from './js/baseData'
import taskTable from './components/taskTable'
import setQueryParams from '../../utils/setQueryParams'
import axios from '@/axios/request.js'
export default {
  name: 'Task',
  components: { taskTable },
  props: {},
  data() {
    return {
      form: {
        channel_id: null,
        program_id: null,
        source: '',
        doType: null,
        resourcetype: null,
        business: null
      },
      loading: true,
      // 栏目列表
      itemData: [],
      // 下拉列表基础数据
      baseData: baseData,
      // 任务列表
      taskData: [],
      pag: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      roles: localStorage.getItem('roles')
    }
  },
  computed: {
    ...mapGetters(['channelData'])
  },
  watch: {
    'form.channel_id'(val) {
      if (val) {
        this.getItemList()
      }
      this.form.program_id = null
    }
  },
  created() {
    !this.channelData.length && this.$store.dispatch('channel/getChannelData')
    this.getTaskList()
  },
  mounted() {},
  methods: {
    // 获取栏目列表
    async getItemList() {
      const channel_id = this.channelData.filter((item) => item.channel_uuid === this.form.channel_id)[0].channel_id
      const params = {
        channel_id: channel_id
      }
      const { data, err } = await this.$get('/jobs/programs', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.itemData = data.data
    },
    // 获取任务列表
    async getTaskList() {
      const query = {
        channel_id: this.form.channel_id,
        program_id: this.form.program_id,
        source: this.form.source,
        doType: this.form.doType,
        resourcetype: this.form.resourcetype,
        business: this.form.business
      }
      const params = {
        page: this.pag.currentPage,
        limit: this.pag.pageSize,
        query: setQueryParams(query)
      }
      this.loading = true
      const { data, err } = await this.$get('/jobs', params)

      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.pag.total = data.count
      this.taskData = data.data
      this.loading = false
    },
    exportData() {
      const query = {
        channel_id: this.form.channel_id,
        program_id: this.form.program_id,
        source: this.form.source,
        doType: this.form.doType,
        resourcetype: this.form.resourcetype,
        business: this.form.business
      }
      const params = {
        page: this.pag.currentPage,
        limit: this.pag.pageSize,
        export: 1,
        query: setQueryParams(query)
      }
      axios({
        method: 'get',
        url: '/jobs',
        params: params,
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
            link.setAttribute('download', '任务列表.xlsx')
            link.click()
            link = null
            this.$message.success('导出成功')
          } else {
            // 返回json
            this.$message.warning(res.data.msg)
          }
        }).catch((error) => {
          console.log(error)
          this.$message.error(error.msg)
        })
    },
    handleCurrentChange(val) {
      this.pag.currentPage = val
      this.getTaskList()
    },
    handleSizeChange(val) {
      this.pag.pageSize = val
      this.getTaskList()
    },
    search() {
      this.pag.currentPage = 1
      this.getTaskList()
    },
    clearSearch() {
      for (const key in this.form) {
        this.form[key] = null
      }
      this.search()
    },
    addTask() {
      this.$router.push({
        path: '/addTask'
      })
    }
  }
}
</script>

<style scoped lang="scss">
.task-mgt{
  padding: 20px;
  .search-wrap{
    display: flex;
    justify-content: space-between;
    .el-form-item{
      margin-right: 20px;
    }
    .btn-item{
      width: 100%;
      ::v-deep .el-form-item__content{
        width: 100%;
        .add-btn{
          float:right;
        }
      }
    }
  }
}
</style>
