<template>
  <div class="task_wrap">
    <el-breadcrumb class="breadcrumb">
      <el-breadcrumb-item to="/task">任务管理</el-breadcrumb-item>
      <el-breadcrumb-item v-if="$route.query.type=='video'">视频审核</el-breadcrumb-item>
      <el-breadcrumb-item v-if="$route.query.type=='audio'">音频审核</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="searchBox">
      <div class="d-flex flex-1">
        <el-input
          placeholder="请输入视频名称"
          v-model="form.title"
          clearable>
        </el-input>
        <div class="search_img" @click="search()">
          <img src="../../assets/images/search.png" alt="">
        </div>
      </div>
      <div class="task_item flex-1">
        <span>审核状态:</span>
        <el-select v-model="form.status" placeholder="请选择审核状态" @change="search()" class="w-100">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="tableBox">
      <table class="table" style="table-layout:fixed;width:100%;">
        <thead>
          <tr>
            <th>任务素材名称</th>
            <th v-if="$route.query.type=='video'">素材封面</th>
            <th>素材作者</th>
            <th>提交时间</th>
            <th>审核状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody class="tbody">
          <tr v-if="!this.tableData.length">
            <td style="border:none;">暂无数据</td>
          </tr>
          <tr v-for="(item,index) in tableData" :key="index">
            <td>
              <a style="color:#2d86e4" @click="examine(item)">{{item.title}}</a>
            </td>
            <td v-if="$route.query.type=='video'">
              <img :src="item.cover_pic_url" alt="" class="coverimg" v-if="item.cover_pic_url">
            </td>
            <td>{{item.uploader}}</td>
            <td>{{item.created_at}}</td>
            <td>{{item.status}}</td>
            <td>
              <a style="color:#2d86e4;" @click="down(item)" v-if="item.status=='审核通过'">下载</a>
            </td>
          </tr>
        </tbody>
      </table>
      <loading v-if="loading_status" />

    </div>
    <div class="paginationBox">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page.sync="form.page"
        :page-size="form.limit"
        layout="prev, pager, next, jumper"
        :total="form.total"></el-pagination>
    </div>
  </div>
</template>

<script>
import videoExamine from './model/video_examine'
import audioExamine from './model/aduio_model'
export default {
  name: '',
  data() {
    return {
      name: '',
      task_select: '',
      tableData: [],
      loading_status: false,
      options: [
        {
          name: '全部',
          value: ''
        },
        {
          name: '待审核',
          value: '待审核'
        },
        {
          name: '审核通过',
          value: '审核通过'
        },
        {
          name: '审核不通过',
          value: '审核不通过'
        }
      ],
      form: {
        page: 1,
        limit: 20,
        total: null,
        task_id: this.$route.query.task_id,
        status: '',
        title: ''
      }
    }
  },
  components: { videoExamine, audioExamine },
  computed: {},
  watch: {},
  methods: {
    // 下载
    down(item) {
      if (item.type == 'video') {
        window.open(item.video_url)
      } else {
        window.open(item.audio_url)
      }
    },
    search() {
      this.getList()
    },
    examine(item) {
      let obj = {
        content: null,
        title: '审核',
        width: '',
        height: '',
        data: {
          _this: this,
          item: JSON.stringify(item),
          type: 'no_tab',
          status: item.status
        }
      }
      if (this.$route.query.type == 'video') {
        obj.content = videoExamine
        obj.width = '800px'
        obj.height = '530px'
      } else if (this.$route.query.type == 'audio') {
        obj.content = audioExamine
        obj.width = '600px'
        obj.height = '460px'
      }
      this.$layerIfream(obj)
    },
    getList() {
      this.tableData = []
      this.loading_status = true
      this.$get('/admin/task/task-video-list', {
        params: this.form
      })
        .then(
          res => {
            if (res.data.code == '0000') {
              this.form.total = res.data.data.total
              this.tableData = res.data.data.list
              this.loading_status = false
            }
          },
          err => {}
        )
        .finally(res => {
          this.loading_status = false
        })
    },
    handleCurrentChange(val) {
      this.form.page = val
      this.getList()
    }
  },
  created() {
    this.getList()
  },
  mounted() {}
}
</script>

<style lang="scss" scoped>
.task_wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background: #ffffff;
  // padding: 15px;
  box-sizing: border-box;
  .breadcrumb {
    height: 40px;
    line-height: 40px;
    padding-left: 15px;
  }
  .paginationBox {
    text-align: right;
    margin-top: 20px;
  }
  .searchBox {
    position: relative;
    display: flex;
    padding: 15px;
    .task_item {
      display: flex;
      margin-left: 20px;
      span {
        min-width: 80px;
        margin-top: 10px;
      }
    }
    /deep/ .el-input {
      height: 36px;
    }
    .search_img {
      width: 56px;
      height: 36px;
      border-radius: 3px;
      background-color: rgba(43, 121, 223, 100);
      text-align: center;
      line-height: 36px;
      img {
        width: 24px;
        height: 24px;
      }
    }
  }
  .tableBox {
    margin-top: 15px;
    .coverimg {
      width: 60px;
      height: 50px;
    }
  }
}
</style>
