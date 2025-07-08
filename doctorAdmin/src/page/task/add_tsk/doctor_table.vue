<!--
 * @Author: your name
 * @Date: 2020-12-23 18:09:52
 * @LastEditTime: 2021-02-09 19:07:42
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/page/task/model/add_problem.vue
-->
<template>
  <div class="add_problem_wrap">
    <el-breadcrumb class="breadcrumb">
      <el-breadcrumb-item to="/task">任务管理</el-breadcrumb-item>
      <el-breadcrumb-item>表格</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="searchBox">
      <div class="d-flex mt-20px">
        <el-input
          placeholder="请输入子任务名称"
          v-model="form.keyword"
          clearable>
        </el-input>
        <div class="search_img" @click="search()">
          <img src="../../../assets/images/search.png" alt="">
        </div>
      </div>
      <div class="task_item ml-20px">
        <span>指定推送:</span>
        <el-select v-model="form.is_assign" placeholder="请选择指定推送" style="width:100%">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
      <div class="task_item ml-20px">
        <span>医生:</span>
        <el-select v-model="form.name" placeholder="请选择医生姓名" style="width:100%" @change="search()">
          <el-option
            v-for="item in doctorData"
            :key="item.id"
            :label="item.join_name"
            :value="item.id">
          </el-option>
        </el-select>
      </div>
      <div class="d-flex mt-20px">
        <upload :button_name="'追加列表'"
          @getFileUrl="getFileUrl(...arguments,'append')" :file_type="file_type" :key="'append'" style="margin-left:10px" :disabled="status=='已结束'" />
        <a class="addTable" @click="addSingle()" :disabled="status=='已结束'">单条添加</a>
        <upload :button_name="'上传表格'"
          @getFileUrl="getFileUrl(...arguments,'replace')" :file_type="file_type" :key="'replace'" v-if="status=='未开始'" style="margin-left:10px" :disabled="status=='已结束'" />
      </div>
      <div class="add_tsak ml-60px mt-20px">
        <el-button type="primary" round size="small" @click="search()">搜索</el-button>
        <el-button type="primary" round size="small" @click="assgin()" :disabled="task_question_id.length<=0 || status=='已结束'">批量指定</el-button>
      </div>
    </div>
    <div class="table_box">
      <el-table
        :header-cell-style="theadStyle"
        ref="multipleTable"
        :data="tableDta"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          label="标题"
          prop="title">
        </el-table-column>
        <el-table-column
          label="正文"
          prop="question">
        </el-table-column>
        <el-table-column
          label="科室"
          prop="department">
        </el-table-column>
        <el-table-column
          label="是否指定推送"
          prop="is_assigned">
          <template slot-scope="scope">
            <a v-if="scope.row.is_assign==true">是</a>
            <a v-if="scope.row.is_assign==false">否</a>
            <span v-if="scope.row.is_assign==undefined">---</span>
          </template>
        </el-table-column>
        <el-table-column
          label="推送医生"
          prop="assigned_name">
        </el-table-column>
        <el-table-column
          label="操作"
          prop="">
          <template slot-scope="scope">
            <a class="aError" @click="reset(scope)" v-if="scope.row.is_assign">释放</a>
            <a class="aSucess" @click="singleAssgin(scope)" v-if="!scope.row.is_assign">指定</a>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="paginationBox">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page.sync="page"
        :page-size="limit"
        layout="prev, pager, next, jumper"
        :total="total"></el-pagination>
    </div>
  </div>
</template>

<script>
import addTable from '../model/assgin_table'
import doctorTable from '../model/adoc_table'
export default {
  name: 'doctorTable',
  props: {},
  data() {
    return {
      options: [
        {
          label: '全部',
          value: ''
        },
        {
          label: '是',
          value: true
        },
        {
          label: '否',
          value: false
        }
      ],
      task_question_id: [],
      theadStyle: {
        background: '#E2EEFD',
        color: '#101010',
        textAlign: 'left',
        padding: '5px 0',
        fontWeight: 'bolder',
        fontSize: '12px'
      },
      file_type: 'all',
      file: {
        attach_file: null,
        name: ''
      },
      form: {
        keyword: '',
        name: '',
        is_assign: ''
      },
      total: null,
      tableDta: [],
      page: 1,
      limit: 20
    }
  },
  components: { addTable, doctorTable },
  computed: {
    task_id() {
      return this.$route.query.task_id
    },
    status() {
      return this.$route.query.status
    },
    doctorData() {
      return this.$store.state.doctorData
    }
  },
  watch: {},
  methods: {
    search() {
      this.getList()
    },

    handleCurrentChange(val) {
      this.page = val
      this.getList()
    },
    reset(scope) {
      if (this.status == '已结束') return

      let params = {
        task_question_id: scope.row.question_id
      }
      this.$post('/admin/task/unassigned', params).then(
        res => {
          if (res.data.code == '0000') {
            this.$message({
              message: '释放成功',
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
    singleAssgin(scope) {
      if (this.status == '已结束') return
      this.$refs.multipleTable.toggleRowSelection(scope.row)
      let obj = {
        content: addTable,
        title: '指定',
        width: '860px',
        height: '480px',
        data: {
          doctorData: this.doctorData,
          task_question_id: this.task_question_id,
          _this: this
        },
        callBack: () => {
          this.$refs.multipleTable.clearSelection()
        }
      }
      this.$layerIfream(obj)
    },
    getList() {
      let params = {
        task_id: this.task_id,
        keyword: this.form.keyword,
        name: this.form.name,
        is_assign: this.form.is_assign || null,
        page: this.page,
        limit: this.limit
      }
      this.$get('/admin/task/get-video-question-list', {
        params: params
      }).then(
        res => {
          if (res.data.code == '0000') {
            const { list } = res.data.data
            this.tableDta = list
            const { total } = res.data.data
            this.total = total
          }
        },
        err => {}
      )
    },
    addSingle() {
      if (this.status == '已结束') return
      let obj = {
        content: doctorTable,
        title: '添加',
        width: '500px',
        height: '480px',
        data: {
          _this: this
        }
      }
      this.$layerIfream(obj)
    },
    assgin() {
      let obj = {
        content: addTable,
        title: '指定',
        width: '860px',
        height: '480px',
        data: {
          task_question_id: this.task_question_id,
          _this: this,
          doctorData: this.doctorData
        },
        callBack: () => {
          this.$refs.multipleTable.clearSelection()
        }
      }
      this.$layerIfream(obj)
    },
    getData() {
      if (this.type != '创建任务') {
        this.tableData = this.question_list
      }
    },
    getFileUrl(url, file, key) {
      this.getFileUrldata(file, key)
    },
    getFileUrldata(file, key) {
      // if (key == 'replace') {
      //   this.tableDta = []
      // }
      var self = this
      var formData = new FormData()
      formData.append('excel', file)
      formData.append('type', key)
      this.$post('/admin/task/read-question—from-excel', formData).then(
        res => {
          if (res.data.code == '0000') {
            this.subData(res.data.data, key)
          } else {
            this.$message({
              message: res.data.msg,
              type: 'warning'
            })
          }
        },
        err => {
          this.$message({
            message: '导入失败,请导入正确模板',
            type: 'warning'
          })
          console.log(err)
        }
      )
    },
    subData(data, key) {
      let params = {
        question_list: JSON.stringify(data),
        has_question: this.tableDta.length != 0 ? '是' : '否',
        task_id: this.task_id,
        page: this.page,
        limit: this.limit,
        type: key
      }
      this.$post('/admin/task/question-save', params).then(
        res => {
          if (res.data.code == '0000') {
            this.$message({
              message: '提交成功',
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
    handleSelectionChange(val) {
      this.task_question_id = val
    },
    addProblems() {
      let lastData = JSON.parse(JSON.stringify(this.form))
      this.tableData.push(lastData)
    }
  },
  created() {
    this.getList()
    this.$store.dispatch('SET_DOCTORDATA')
  },
  mounted() {}
}
</script>

<style lang="scss" scoped>
.add_problem_wrap {
  padding: 20px;
  .paginationBox {
    width: 100%;
    text-align: right;
    margin-top: 20px;
  }
  .searchBox {
    position: relative;
    display: flex;
    box-sizing: border-box;
    flex-wrap: wrap;
    .addTable {
      font-size: 12px;
      margin-top: 12px;
    }
    .task_item {
      display: flex;
      margin-top: 20px;
      span {
        min-width: 80px;
        margin-top: 10px;
        text-align: center;
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
  .table_box {
    height: calc(100% - 50px);
    overflow: auto;
    margin-top: 30px;
    ::v-deep .cell {
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
    }
    .aSucess {
      color: #2d86e4;
    }
    .aError {
      color: #c51b19;
    }
    .table {
      margin-top: 15px;
    }
  }
  .footer_btn {
    width: 100%;
    height: 50px;
    position: absolute;
    bottom: 0;
    left: 0;
    box-shadow: 0px 2px 7px 0px rgba(0, 0, 0, 0.16);
    background: #f8f8f8;
    text-align: center;
    line-height: 50px;
  }
  .add_list {
    display: flex;
    margin-top: 20px;

    span {
      color: #2a79df;
      font-size: 14px;
      min-width: 50px;
      margin-top: 10px;
      text-align: right;
      cursor: pointer;
    }
  }
  /deep/ a {
    color: #2a79df;
    text-decoration: underline;
    font-size: 14px;
  }
}
</style>
