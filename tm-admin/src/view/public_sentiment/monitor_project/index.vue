<!--
 * @Author: your name
 * @Date: 2021-04-09 14:48:40
 * @LastEditTime: 2021-05-10 17:25:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-beautiful-template/src/view/public_sentiment/monitor_project/index.vue
-->
<template>
  <div class="monitor-wrap">
    <el-tabs v-model="activeName">
      <!-- 监测项目 -->
      <el-tab-pane label="监测项目列表" name="first">
        <div class="d-flex justify-content-between align-items-center">
          <div class="search-input">
            <el-input v-model="searchVal" size="normal" clearable placeholder="输入关键词搜索" @clear="searchClick" @keyup.enter.native="searchClick" />
            <div class="seachBtn" icon="el-icon-search" @click="searchClick">
              <i class="el-icon-search" style="color: white" />
            </div>
          </div>
          <el-button type="primary" size="default" @click="goAddMonitor()">新建项目</el-button>
        </div>

        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="projectname" label="项目名称" width="200" />
          <el-table-column prop="channels" label="监测频道">
            <template slot-scope="scope">
              <div>{{ getChannels(scope.row.channels) }}</div>
            </template>
          </el-table-column>
          <el-table-column prop="createtime" label="创建时间">
            <template slot-scope="scope">
              <div>{{ getDate(scope.row.createtime * 1000) }}</div>
            </template>
          </el-table-column>
          <el-table-column prop="time" label="监测时段">
            <template slot-scope="scope">
              <div>每天：<br>{{ scope.row.monitortimestart }}~{{ scope.row.monitortimeend }}</div>
            </template>
          </el-table-column>
          <el-table-column label="监测自身">
            <template slot-scope="scope"> {{ getString(scope.row.mselfbrand) }} </template>
          </el-table-column>
          <el-table-column label="监测竞品">
            <template slot-scope="scope">
              {{ getString(scope.row.mcompetitorbrand) }}
            </template>
          </el-table-column>
          <el-table-column label="特殊提报">
            <template slot-scope="scope">
              {{ getString(scope.row.diffkeywords) }}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="editClick(scope.row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          :current-page.sync="currentPage"
          hide-on-single-page
          :page-size="10"
          layout="prev, pager, next, jumper"
          :total="projectCount"
          @current-change="projectCurrentChange"
        />
      </el-tab-pane>
      <!-- 监测频道 -->
      <el-tab-pane label="监测频道列表" name="second">
        <div class="d-flex justify-content-between align-items-center">
          <div class="search-input">
            <el-input v-model="searchVal1" size="normal" clearable placeholder="输入关键词搜索" @clear="searchChannel" @keyup.enter.native="searchChannel" />
            <div class="seachBtn" icon="el-icon-search" @click="searchChannel">
              <i class="el-icon-search" style="color: white" />
            </div>
          </div>
          <el-button type="primary" size="default" @click="showAddChannel = true">添加频道</el-button>
        </div>

        <el-table :data="tableDataSec" style="width: 100%">
          <el-table-column prop="names" label="频道名称" />
          <!-- <el-table-column prop="urls" label="链接" width="300" /> -->
          <!-- <el-table-column prop="status" label="状态">
            <template slot-scope="scope">
              <span>{{ scope.row.status == 1 ? "未监测" : "监测中" }}</span>
            </template>
          </el-table-column> -->
          <el-table-column
            label="操作"
            width="100"
            fixed="right"
          >
            <template slot-scope="scope">
              <!-- <el-button type="text" size="small" @click="stateClick(scope.row)">{{ scope.row.status == 1 ? "开启" : "关闭" }}</el-button> -->
              <el-popconfirm
                title="确定删除吗？"
                @confirm="deleteClick(scope.row)"
              >
                <el-button slot="reference" type="text" size="small">删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          :current-page.sync="currentPageSec"
          hide-on-single-page
          :page-size="10"
          layout="prev, pager, next, jumper"
          :total="channelsCount"
          @current-change="handleCurrentChange"
        />
      </el-tab-pane>
    </el-tabs>
    <!-- 添加频道弹窗 -->
    <BaseDialog
      :show.sync="showAddChannel"
      title="添加频道"
      width="60%"
      class="add-channel"
      :close-on-click-modal="false"
    >
      <el-select
        v-model="selectChanne"
        placeholder="请选择频道"
        clearable
        filterable
        style="width:80%"
      >
        <el-option
          v-for="item in channelOption"
          :key="item.channel_en_name"
          :label="item.channel_en_name"
          :value="item.channel_en_name"
        />
      </el-select>

      <el-button class="btn" type="primary" size="default" @click="addChannels">确定</el-button>
    </BaseDialog>
  </div>
</template>

<script>

export default {
  components: {},
  props: {},
  data() {
    return {
      // tab
      activeName: 'first',
      // 项目列表
      tableData: [],
      // 频道列表
      tableDataSec: [],
      // 项目当前页码
      currentPage: 1,
      // 频道列表当前页码
      currentPageSec: 1,
      // 项目总数
      projectCount: 0,
      // 频道总数
      channelsCount: 0,
      // 项目列表搜索
      searchVal: '',
      // 频道列表搜索
      searchVal1: '',
      // 添加频道
      selectChanne: '',
      channelOption: [],
      addChannel: {
        name: '',
        url: ''
      },
      // 添加频道 dialog
      showAddChannel: false
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {
    this.initProject()
    this.initChannels()
    this.useChannels()
  },
  methods: {
    // 初始化监测项目列表
    async initProject(params) {
      const { res } = await this.$get('/api/projects', { ...params, sortby: 'createtime', order: 'desc' })
      this.tableData = res.data
      this.projectCount = res.count
    },
    // 初始化监测频道列表
    async initChannels(params) {
      const { res } = await this.$get('/api/channels', { ...params })
      this.tableDataSec = res.data
      this.channelsCount = res.count
    },

    // 初始化可添加频道
    async useChannels() {
      const { res } = await this.$get('/api/channels/getallchannellist')
      this.channelOption = res.data
    },
    // 监测项目列表筛选
    searchClick() {
      this.initProject({ query: `projectname__contains:${this.searchVal}` })
    },
    // 监测频道筛选
    searchChannel() {
      this.initChannels({ query: `names__contains:${this.searchVal1}` })
    },
    // // 修改频道监测状态
    // async stateClick(item) {
    //   const params = {
    //     status: item.status === 1 ? 2 : 1
    //   }
    //   const { err, res } = await this.$put('/api/channels/' + item.id, params)
    //   err ? this.$message({ type: 'error', message: err.msg }) : this.$message({ type: 'success', message: '修改成功' })

    //   this.initChannels()
    // },
    // 删除频道
    async deleteClick(e) {
      const { err, res } = await this.$deleteRun('/api/channels/' + e.id)
      err ? this.$message({ type: 'error', message: err.msg }) : this.$message({ type: 'success', message: '删除成功' })
      this.initChannels()
    },
    // 新增频道
    async addChannels() {
      if (!this.selectChanne) return
      var params = {
        names: '',
        urls: ''
      }
      this.channelOption.filter(item => {
        if (item.channel_en_name === this.selectChanne) {
          params.names = item.channel_en_name
          params.urls = item.channerl_live_url
        }
      })
      const { err, res } = await this.$post('/api/channels', params)
      err ? this.$message({ type: 'error', message: err.msg }) : this.$message({ type: 'success', message: '新增成功' })
      this.initChannels()
      this.currentPage = 1
      this.showAddChannel = false
    },
    // tableClick
    editClick(e) {
      this.$router.push({
        path: '/public_sentiment/add_monitor',
        query: {
          id: e.id
        }
      })
    },
    // 分页
    projectCurrentChange(val) {
      this.initProject({ page: val })
    },
    handleCurrentChange(val) {
      this.initChannels({ page: val })
    },
    // 新建项目
    goAddMonitor() {
      this.$router.push({
        path: '/public_sentiment/add_monitor'
      })
    },

    getDate(date) {
      var now = new Date(date)
      var y = now.getFullYear()
      var m = now.getMonth() + 1
      var d = now.getDate()
      return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) + ' ' + now.toTimeString().substr(0, 8)
    },

    getChannels(channel) {
      const arr = channel.split(',')
      const arrNew = arr.map(item => item.split('|')[0])
      // console.log(arrNew)
      return arrNew.join()
    },
    getString(str) {
      return str.length > 20 ? str.substr(0, 20) + '...' : str
    }
  }
}
</script>

<style scoped lang="scss">
.monitor-wrap {
  padding: 27px;
  .search-input {
    width: 308px;
    height: 36px;
    display: flex;
    margin-top: 20px;
    margin-bottom: 22px;
    .seachBtn {
      position: relative;
      z-index: 2;
      margin-left: -8px;
      width: 56px;
      height: 40px;
      border-radius: 3px;
      background-color: rgba(42, 112, 193, 100);
      color: rgba(255, 255, 255, 100);
      font-size: 16px;
      text-align: center;
      line-height: 40px;
      box-shadow: 2px 5px 10px 0px rgba(0, 0, 0, 0.1);
    }
  }

  .add-channel {
    text-align: center;
      margin: 0 auto;
    .d-flex {
      margin-bottom: 20px;
    }
    .lable {
      width: 100px;
    }
    .btn {
      display: block;
      margin: 0 auto;
      margin-top: 20px;

    }
  }
  .el-pagination {
    margin-top: 10px;
  }
}
</style>
