<!--
 * @Author: your name
 * @Date: 2020-12-23 18:09:52
 * @LastEditTime: 2020-12-26 18:54:07
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/page/task/model/add_problem.vue
-->
<template>
  <div class="add_problem_wrap">
    <div class="d-flex" v-if="type!='任务详情'">
      <upload :button_name="'导入excel'"
        @getFileUrl="getFileUrl" :file_type="file_type" />
      <!-- <div class="fz-14px">{{file.name}}</div> -->
    </div>
    <div class="table_box">
      <table class="table" style="table-layout:fixed;width:100%;">
        <thead>
          <tr>
            <th>标题</th>
            <th>正文</th>
            <th>科室</th>
          </tr>
        </thead>
        <tbody class="tbody">
          <tr v-if="!this.tableData.length">
            <td style="border:none;">暂无数据</td>
          </tr>
          <tr v-for="(item,index) in tableData" :key="index">
            <td>{{item.title}}</td>
            <td>{{item.question}}</td>
            <td>{{item.department}}</td>
          </tr>
        </tbody>
      </table>
      <div class="add_list" v-if="type!='任务详情'">
        <el-input placeholder="请输入标题" v-model="form.title" clearable style="width:30%"></el-input>
        <el-input placeholder="请输入正文描述" v-model="form.question" clearable style="width:50%"></el-input>
        <el-input placeholder="请输入医生科室" v-model="form.department" clearable style="width:22%"></el-input>
        <span @click="addProblems()">添加</span>
      </div>
    </div>
    <div class="footer_btn" v-if="type!='任务详情'">
      <el-button type="primary" round @click="determine()">确定</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'addProblem',
  props: {
    layerid: {
      type: String,
      default: ''
    },
    type: '',
    _this: '',
    question_list: {
      type: Array,
      default: function() {
        return []
      }
    },
    lydata: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      file_type: 'all',
      file: {
        attach_file: null,
        name: ''
      },
      form: {
        title: '',
        question: '',
        department: ''
      },
      tableData: []
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    getData() {
      if (this.type != '创建任务') {
        this.tableData = this.question_list
      }
    },
    getFileUrl(url, file) {
      // this.file.attach_file = file
      this.getFileUrldata(file)
      // this.file.name = file.name
    },
    getFileUrldata(file) {
      var self = this
      var formData = new FormData()
      formData.append('excel', file)
      this.$post('/admin/task/read-question—from-excel', formData).then(
        res => {
          if (res.data.code == '0000') {
            console.log(res)
            res.data.data.forEach(function(item, index) {
              self.tableData.push(item)
            })
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
    determine() {
      if (this.tableData.length > 0) {
        this.lydata._this.ruleForm.has_question = '是'
        this.lydata._this.ruleForm.question_list = JSON.stringify(this.tableData)
      }
      this.$layer.close(this.layerid)
    },
    addProblems() {
      let lastData = JSON.parse(JSON.stringify(this.form))
      this.tableData.push(lastData)
      //   this.form = {
      //     title: '',
      //     describe: '',
      //     department: ''
      //   }
    }
  },
  created() {},
  mounted() {
    this.getData()
  }
}
</script>

<style lang="scss" scoped>
.add_problem_wrap {
  padding: 20px;
  .table_box {
    height: calc(100% - 50px);
    overflow: auto;
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
