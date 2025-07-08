<!--
 * @Author: your name
 * @Date: 2021-05-27 15:33:02
 * @LastEditTime: 2021-08-03 14:41:33
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/videoAdmin/index.vue
-->
<!--  -->
<template>
  <div class="video-warp">
    <div class="video-count">
      <el-form :inline="true" :model="form">
        <el-form-item label="视频名称">
          <el-input v-model="form.videoName" placeholder="请输入视频名称" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" clearable filterable placeholder="请选择状态">
            <el-option
              v-for="item in statusList"
              :key="item.val"
              :label="item.label"
              :value="item.val"
            />
          </el-select>
        </el-form-item>
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
          <el-select v-model="form.program_id" filterable clearable placeholder="请选择栏目名称">
            <el-option
              v-for="item in itemData"
              :key="item.item_uuid"
              :label="item.name"
              :value="item.item_uuid"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="日期">
          <el-date-picker
            v-model="form.date"
            type="daterange"
            value-format="timestamp"
            :default-time="['00:00:00', '23:59:59']"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          />
        </el-form-item>
        <base-btn class="search-btn" size="big" @click="searchData">查询</base-btn>
        <base-btn class="search-btn create-btn" size="big" @click="clearSearch">清除</base-btn>
      </el-form>
    </div>
    <div class="total">
      <div>当前条件下共 {{ total }} 条数据</div>
      <base-btn type="text" @click="exportData()">导出EXCEL</base-btn>
    </div>
    <el-table :data="tableData" border>
      <el-table-column prop="Id" width="80" label="ID" />
      <el-table-column prop="status" label="状态">
        <template slot-scope="scope">
          <div>
            {{ statusIf(scope.row.status) }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="videotitle" label="视频名称" />
      <el-table-column prop="playtime" label="开播时间" />
      <el-table-column label="封面图">
        <template slot-scope="scope">
          <img :src="scope.row.videopic" alt="" class="cover-img">
        </template>
      </el-table-column>
      <el-table-column prop="descs" label="简介" width="200">
        <template slot-scope="scope">
          <div class="descs">
            {{ scope.row.descs }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="program_name" label="栏目名称" />
      <el-table-column prop="channel_name" label="频道名称" />
      <!-- <el-table-column prop="fileName" label="文件名称" /> -->
      <el-table-column label="更新时间" width="160">
        <template slot-scope="scope">
          {{ (scope.row.updatetime)*1000|unixToHms }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="240">
        <template slot-scope="scope">
          <base-btn v-if="scope.row.status>0" v-has="['superman','视频编辑']" type="text" @click="showDialog('编辑物料','editM',scope.row)">编辑物料</base-btn>
          <!-- <base-btn type="text" @click="crawling(scope.row)">重新爬取</base-btn> -->
          <base-btn v-has="['superman','视频删除']" type="text" @click="deleteClick(scope.row)">删除</base-btn>
          <base-btn type="text" @click="showDialog('播放','playVideo',scope.row)">播放</base-btn>
          <base-btn type="text" @click="showDialog('播放bos','playVideo',scope.row)">播放bos</base-btn>
        </template>
      </el-table-column>
    </el-table>
    <base-pag :total="total" :limit="limit" :page="page" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
    <!-- 弹窗 -->
    <BaseDialog
      :title="dialogTit"
      :show.sync="dialogVisible"
      width="660px"
      class="dialog"
    >
      <compontents
        :is="dialogCompomtentsName"
        :video-data="videoData"
        @showDialog="showDialog"
        @close="close"
      />
    </BaseDialog>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';
import editM from './model/editM'
import playVideo from './model/playVideo'
import { mapGetters } from 'vuex'
import setQueryParams from '../../utils/setQueryParams'
import axios from '@/axios/request.js'

export default {
// import引入的组件需要注入到对象中才能使用
  components: { editM, playVideo },
  data() {
    // 这里存放数据
    return {
      videoData: {},
      dialogTit: '',
      dialogVisible: false,
      dialogCompomtentsName: '',
      form: {
        videoName: '',
        status: '',
        channel_id: '',
        program_id: '',
        date: []
        // invalid: ''
      },
      total: null,
      limit: 10,
      page: 1,
      statusList: [
        {
          label: '爬取成功',
          val: 1
        },
        {
          label: '入库成功',
          val: 2
        },
        {
          label: '爬取失败',
          val: -1
        },
        {
          label: '入库失败',
          val: -2
        },
        {
          label: '重新爬取',
          val: -3
        },
        {
          label: '视频不存在',
          val: -4
        }, {
          label: '截图失败',
          val: -5
        }, {
          label: '图片上传BOS失败',
          val: -6
        },
        {
          label: '视频上传BOS失败',
          val: -7
        },
        {
          label: '时长获取失败',
          val: -8
        },
        {
          label: '上传高清视频失败',
          val: -9
        },
        {
          label: 'jobs不存在',
          val: -10
        },
        {
          label: '遮码失败',
          val: -11
        },
        {
          label: '需要截图',
          val: -12
        },
        {
          label: '需要遮码',
          val: -13
        },
        {
          label: '遮码中',
          val: -14
        },
        {
          label: '时间超长',
          val: -15
        }
      ],
      itemData: [],
      tableData: [
      ]
    }
  },
  // 监听属性 类似于data概念
  computed: {
    ...mapGetters(['channelData'])
  },
  // 监控data中的数据变化
  watch: {
    'form.channel_id'(val) {
      this.getItemList()
      this.form.program_id = null
    }
  },
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {
    !this.channelData.length && this.$store.dispatch('channel/getChannelData')
    this.getList()
  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {

  },
  beforeCreate() {}, // 生命周期 - 创建之前
  beforeMount() {}, // 生命周期 - 挂载之前
  beforeUpdate() {}, // 生命周期 - 更新之前
  updated() {}, // 生命周期 - 更新之后
  beforeDestroy() {}, // 生命周期 - 销毁之前
  destroyed() {}, // 生命周期 - 销毁完成
  activated() {},
  // 方法集合
  methods: {
    // 判断状态
    statusIf(id) {
      const index = this.statusList.findIndex(item => item.val === id)
      return index >= 0 ? this.statusList[index].label : ''
    },
    // 导出表格
    exportData() {
      const params = this.setParams()
      params.export = 1
      axios({
        method: 'get',
        url: '/videos',
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
            link.setAttribute('download', '内容爬取.xlsx')
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
    // 重新爬取
    crawling() {
    },
    deleteClick(e) {
      this.$confirm('是否删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteData(e)
      })
    },
    async deleteData(e) {
      const res = await this.$deleteRun('/videos/' + e.Id)
      if (res.err === 'OK') {
        this.$message({ type: 'success', message: '删除成功' })
        this.getList()
      } else {
        this.$message({ type: 'error', message: '删除失败' })
      }
    },
    async getItemList() {
      if (!this.form.channel_id) {
        return
      }
      const id = this.channelData.find((item) => item.channel_uuid === this.form.channel_id).channel_id
      const res = await this.$get('/jobs/programs', { channel_id: id })
      const { data, err } = res
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.itemData = data.data
    },
    // 设置参数
    setParams() {
      const query = {
        channel_id: this.form.channel_id,
        program_id: this.form.program_id,
        status: this.form.status,
        videotitle: this.form.videoName,
        createtime__gt: this.form.date ? this.form.date[0] / 1000 : '',
        createtime__lt: this.form.date ? this.form.date[1] / 1000 : ''
      }
      const params = {
        page: this.page,
        limit: this.limit,
        sortby: 'id',
        order: 'desc',
        query: setQueryParams(query)
      }
      return params
    },
    async getList() {
      const res = await this.$get('/videos', this.setParams())
      const { data, err } = res
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.tableData = data.data
      this.total = res.data.count
    },
    searchData() {
      this.getList()
    },
    clearSearch() {
      Object.keys(this.form).forEach(item => {
        this.form[item] = ''
      })
      this.getList()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getList()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getList()
    },
    // 展示dialog
    showDialog(title, name, e) {
      this.dialogTit = title
      this.dialogCompomtentsName = name
      this.dialogVisible = true
      this.videoData = e
    },
    close() {
      this.videoData = {}
      this.dialogVisible = false
    }
  } // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style lang='scss' scoped>
//@import url(); 引入公共css类
.video-warp{
  padding: 20px;
  box-sizing: border-box;
  .el-date-editor {
    flex-shrink: 0;
    width: 300px !important;
  }
  .cover-img{
    width: 100%;
    height: 100px;
    object-fit: contain;
    margin-top: 10px;
  }
  .descs{
      overflow:hidden; //超出的文本隐藏
      text-overflow:ellipsis; //溢出用省略号显示
      white-space:nowrap; //溢出不换行
  }
  .total{
    display: flex;
    justify-content: space-between;
    font-size: 14px;
    color: #101010;
    margin-bottom: 10px;
  }
}
</style>
