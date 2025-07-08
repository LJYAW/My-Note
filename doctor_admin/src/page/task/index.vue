<template>
  <div class="task_wrap">
    <div class="searchBox">
      <div class="d-flex flex-1">
        <el-input
          placeholder="请输入任务名称"
          v-model="form.title"
          clearable>
        </el-input>
        <div class="search_img" @click="search()">
          <img src="../../assets/images/search.png" alt="">
        </div>
      </div>
      <div class="task_item ml-20px flex-1">
        <span>任务状态:</span>
        <el-select v-model="form.status" placeholder="请选择审核状态" @change="search()" style="width:100%">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
      <div class="task_item ml-20px flex-1">
        <span>任务类型:</span>
        <el-select v-model="form.type" placeholder="请选择任务类型" style="width:100%" @change="search()">
          <el-option
            v-for="item in type_options"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
      <div class="add_tsak ml-60px">
        <el-button type="primary" round size="small" @click="addTask()">创建任务</el-button>
      </div>
    </div>
    <div class="tableBox">
      <table class="table" style="table-layout:fixed;width:100%;">
        <thead>
          <tr>
            <th>任务名称</th>
            <th v-if="form.type=='video'">任务海报</th>
            <th>任务表格</th>
            <th>任务时限</th>
            <th>任务类型</th>
            <th>任务状态</th>
            <th v-if="form.type=='video'">任务附件</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody class="tbody">
          <tr v-if="!this.tableData.length">
            <td style="border:none;">暂无数据</td>
          </tr>
          <tr v-for="(item,index) in tableData" :key="index">
            <td>
              <div class="ellipsis" style="color:#2d86e4">
                <a @click="deatils(item)">{{item.title}}</a>
              </div>
            </td>
            <td v-if="item.type=='video'">
              <img :src="item.cover_pic_url" alt="" class="coverimg" v-if="item.cover_pic_url">
            </td>
            <td>
              <div class="options">
                <a class="aSucess" @click="seetTable(item)" v-if="item.has_question=='是'">有</a>
                <a v-else>无</a>
              </div>
            </td>
            <td>
              <div class="ellipsis">
                <span>{{item.start_date}}至{{item.end_date}}</span>
              </div>
            </td>
            <td v-if="item.type=='video'">视频任务</td>
            <td v-if="item.type=='audio'">音频任务</td>
            <td>{{item.status}}</td>
            <td v-if="item.type=='video'">{{item.attach_file_name}}</td>
            <td>
              <div class="options">
                <a class="aSucess" @click="examine(item)" v-if="item.status=='进行中' || item.status=='已结束'">素材</a>
                <a class="aSucess" @click="edit(item)" v-if="item.status!='已结束'">修改</a>
                <a class="aSucess" @click="tabLIst(item)">表格</a>
                <a class="aError" @click="deletes(item)" v-if="item.status=='未开始' || item.status=='已结束'">删除</a>
                <a class="aError" @click="end(item)" v-if="item.status=='进行中'">结束</a>
              </div>
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
import add from './model/add'
import addProblem from './model/add_problem'

export default {
  name: '',
  data() {
    return {
      tableData: [],
      loading_status: false,
      type_options: [
        // {
        //   name: '全部',
        //   value: ''
        // },
        {
          name: '视频任务',
          value: 'video'
        },
        {
          name: '音频任务',
          value: 'audio'
        }
      ],
      options: [
        {
          name: '全部',
          value: ''
        },
        {
          name: '进行中',
          value: '进行中'
        },
        {
          name: '未开始',
          value: '未开始'
        },
        {
          name: '已结束',
          value: '已结束'
        }
      ],
      form: {
        page: 1,
        limit: 20,
        total: null,
        title: '',
        status: '',
        type: 'audio'
      }
    }
  },
  components: {
    add,
    addProblem
  },
  computed: {},
  watch: {},
  methods: {
    tabLIst(item) {
      this.$router.push({
        path: '/task/doctor-table',
        query: {
          task_id: item.task_id,
          status: item.status
        }
      })
    },
    // 查看/修改表格
    seetTable(item) {
      // let obj = {
      //   content: addProblem,
      //   title: '任务表格',
      //   width: '800px',
      //   height: '666px',
      //   data: {
      //     type: '任务详情',
      //     question_list: item.question_list
      //   }
      // }
      // this.$layerIfream(obj)
    },
    // 详情
    deatils(item) {
      this.$router.push({
        path: '/task/add',
        query: {
          type: '任务详情',
          taskData: JSON.stringify(item),
          paroblem: '查看问题列表'
        }
      })
    },
    // 修改
    edit(item) {
      this.$router.push({
        path: '/task/add',
        query: {
          type: '修改任务',
          taskData: JSON.stringify(item),
          paroblem: '修改问题列表'
        }
      })
    },
    // 审核
    examine(item) {
      let path = ''
      if (item.has_question == '是') {
        path = `/task/tab-examine`
      } else {
        path = `/task/examine`
      }
      this.$router.push({
        path: path,
        query: {
          task_id: item.task_id,
          type: item.type
        }
      })
    },
    // 结束
    end(item) {
      var param = {
        task_id: item.task_id
      }
      this.$post('/admin/task/end-task', param).then(
        res => {
          if (res.data.code == '0000') {
            this.$message({
              message: '操作成功',
              type: 'success'
            })
            this.getList()
          } else {
            this.$message({
              message: res.data.msg,
              type: 'warning'
            })
          }
        },
        err => {}
      )
    },
    // 删除
    deletes(item) {
      var param = {
        task_id: item.task_id
      }
      this.$deleteRun('/admin/task/delete-task', {
        data: param
      }).then(res => {
        if (res.data.code == '0000') {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.getList()
        } else {
          this.$message({
            message: res.data.msg,
            type: 'warning'
          })
        }
      })
    },
    // 创建
    addTask() {
      this.$router.push({
        path: '/task/add',
        query: {
          type: '创建任务',
          paroblem: '添加问题列表'
        }
      })
      // let obj = {
      //   content: add,
      //   title: '创建任务',
      //   width: '800px',
      //   height: '626px',
      //   data: {
      //     _this: this,
      //     type: 'add'
      //   }
      // }
      // this.$layerIfream(obj)
    },
    search() {
      this.getList()
    },
    getList() {
      this.tableData = []
      this.loading_status = true
      this.$get('/admin/task/list', {
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
  padding: 10px;
  box-sizing: border-box;
  overflow: auto;
  .paginationBox {
    text-align: right;
    margin-top: 20px;
  }
  .searchBox {
    position: relative;
    display: flex;
    padding: 15px;
    box-sizing: border-box;
    .task_item {
      display: flex;
      span {
        min-width: 80px;
        margin-top: 10px;
      }
    }
    /deep/ .el-input {
      // min-width: 100px;
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
    overflow: auto;
    .ellipsis {
      line-height: 20px;
      text-overflow: -o-ellipsis-lastline;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      line-clamp: 2;
      -webkit-box-orient: vertical;
    }
    .options {
      display: flex;
      a {
        margin-right: 10px;
      }
      .aError {
        color: #c51b19;
      }
      .aSucess {
        color: #2d86e4;
      }
    }

    .coverimg {
      width: 60px;
      height: 50px;
    }
  }
}
</style>
