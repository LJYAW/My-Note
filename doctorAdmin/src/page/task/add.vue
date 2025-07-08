<template>
  <div class="add_task">
    <el-breadcrumb class="breadcrumb">
      <el-breadcrumb-item to="/task">任务管理</el-breadcrumb-item>
      <el-breadcrumb-item>{{$route.query.type}}</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form :model="ruleForm" ref="ruleForm" label-width="120px" class="demo-ruleForm" :rules="rules">
      <el-form-item label="任务类型:" prop="type">
        <el-select v-model="ruleForm.type" placeholder="请选择任务类型" style="width:100%" :disabled="$route.query.type=='任务详情'">
          <!-- <el-option label="视频任务" value="video"></el-option> -->
          <el-option label="音频任务" value="audio"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任务名称:" prop="title">
        <el-input v-model="ruleForm.title" :disabled="$route.query.type=='任务详情' || tempData.status=='进行中'"></el-input>
      </el-form-item>
      <el-form-item label="任务时间" class="mt-20px " prop="date">
        <el-date-picker
          v-model="ruleForm.date"
          type="daterange"
          range-separator="至"
          :picker-options="pickerOptions"
          value-format="yyyy-MM-dd"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          class="itemDate"
          :disabled="$route.query.type=='任务详情'">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="任务表格:" class="task_table error_before" v-if="$route.query.type=='创建任务'">
        <div style="display:flex">
          <!-- <a @click="addProblem()">{{$route.query.paroblem}}</a> -->
          <!-- <a @click="addProblem()" v-if="$route.query.type!='创建任务'">常见问题</a> -->
          <div class="d-flex">
            <upload :button_name="'导入excel'"
              @getFileUrl="getFileUrl" :file_type="file_type" />
            <div class="fz-14px">{{file.name}}</div>
          </div>
          <span>指定任务的标题、说明以及可领取任务的医生所属科室</span>
          <a @click="downTemplate()">下载模板</a>
          <span class="fz-14px fc-333" v-if="ruleForm.has_question==='是'">已添加</span>
        </div>
      </el-form-item>
      <el-form-item label="指定企业:">
        <searchTags ref="searchTags" :tags_item="tags_item" :tags="tags" @tasList="tasList" v-if="!companyLoading">
        </searchTags>
      </el-form-item>
      <addVideo v-if="ruleForm.type=='video'" ref="addVideo" :defaultData="defaultData" />
      <addAudio v-else ref="addAudio" :defaultData="defaultData" />
      <el-form-item class="btns" v-if="$route.query.type!='任务详情'">
        <el-button type="primary" @click="add()" round :disabled="submit_btn_loading">确定</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import addVideo from './add_tsk/add_video'
import addAudio from './add_tsk/add_audio'
import rules from './js/verification'

export default {
  name: 'addAssins',
  props: {},
  data() {
    return {
      companyLoading: false,
      file_type: 'all',
      defaultData: {},
      rules: rules,
      tags_item: [],
      tempData: {},
      tags: [],
      submit_btn_loading: false,
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      file: {
        name: ''
      },
      ruleForm: {
        title: '',
        date: ['', ''],
        has_question: '否',
        question_list: [],
        type: 'audio'
      }
    }
  },
  components: {
    addVideo,
    addAudio
  },
  computed: {},
  watch: {},
  // activated() {
  //   this.update()
  // },
  methods: {
    //   添加问题列表
    addProblem() {
      this.$router.push({
        path: '/task/doctor-table',
        query: {
          task_id: this.defaultData.task_id,
          type: this.$route.query.type
        }
      })
    },
    // 下载模板
    downTemplate() {
      let urls = 'https://cdn-doctor.weijian.video/storage/mnt/doctor/system/example.xlsx'
      window.location.href = urls
    },
    // 设置默认选中的企业
    setDeauflutTags() {
      if (this.$route.query.type == '创建任务') return
      this.tempData.invitation_codes.forEach(code => {
        let item = this.tags.find(item => code == item.invitation_code)
        this.tags_item.push(item)
      })
    },
    getFileUrl(url, file) {
      this.file.name = file.name
      var self = this
      var formData = new FormData()
      formData.append('excel', file)
      this.ruleForm.question_list = []
      this.$post('/admin/task/read-question—from-excel', formData).then(
        res => {
          if (res.data.code == '0000') {
            res.data.data.forEach(function(item, index) {
              self.ruleForm.question_list.push(item)
              self.ruleForm.has_question = '是'
            })
            self.ruleForm.question_list = JSON.stringify(self.ruleForm.question_list)
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
    // 指定企业数据
    getcompany_name() {
      this.companyLoading = true
      this.tags = []
      let params = {
        company_name: this.keywords
      }
      this.$get('/admin/task/company-user-list', {
        params: params
      })
        .then(
          res => {
            if (res.data.code == '0000') {
              this.tags = res.data.data
              this.setDeauflutTags()
            }
          },
          err => {}
        )
        .finally(res => {
          this.companyLoading = false
        })
    },
    // 更新
    update() {
      if (this.tempData) {
        Object.keys(this.ruleForm).forEach(item => {
          this.ruleForm[item] = this.tempData[item]
        })
        this.ruleForm.date = [this.tempData.start_date, this.tempData.end_date]

        this.defaultData = this.tempData
      }
    },
    postTark(formData) {
      this.submit_btn_loading = true
      this.$post('/admin/task/task', formData)
        .then(
          res => {
            if (res.data.code == '0000') {
              this.$message({
                message: '提交成功',
                type: 'success'
              })
              this.$router.push({
                path: '/task',
                query: {}
              })
            } else {
              this.$message({
                message: res.data.msg,
                type: 'warning'
              })
            }
          },
          err => {}
        )
        .finally(res => {
          this.submit_btn_loading = false
        })
    },
    tasList(val) {
      this.tags_item = val
    },
    add() {
      if (this.ruleForm.type == 'video') {
        this.$refs.addVideo.checkFrom(this.formDatas)
      } else {
        this.$refs.addAudio.checkFrom(this.formDatas)
      }
    },
    checkFun() {
      if (this.ruleForm.has_question === '否' && this.$route.query.type == '创建任务') {
        this.$message({
          message: '请选择任务表格',
          type: 'warning'
        })
        return false
      }
      return true
    },
    setformDatas() {
      // let addAudio = this.$refs.addAudio.ruleForm
      var formData = new FormData()
      var addVideo = ''
      formData.append('type', this.ruleForm.type)
      formData.append('title', this.ruleForm.title)
      formData.append('start_date', this.ruleForm.date[0])
      formData.append('end_date', this.ruleForm.date[1])

      this.tags_item.forEach(item => {
        formData.append('invitation_codes[]', item.invitation_code)
      })
      // video
      if (this.ruleForm.type == 'video') {
        addVideo = this.$refs.addVideo.ruleForm
        formData.append('cover_pic', addVideo.cover_pic)
        formData.append('banner', addVideo.banner)
        formData.append('banner', addVideo.attach_file)
        formData.append('intro', addVideo.intro)
      } else {
        addVideo = this.$refs.addAudio
        let str = ''
        formData.append('earnings', addVideo.ruleForm.earnings)
        addVideo.keys_item.forEach(item => {
          str += item.id + ','
        })
        //去掉最后一个逗号(如果不需要去掉，就不用写)
        if (str.length > 0) {
          str = str.substr(0, str.length - 1)
        }
        formData.append('keywords_ids', str)
      }
      // 修改
      if (this.$route.query.type == '修改任务') {
        if (!addVideo.attach_file) formData.delete('attach_file')
        if (!addVideo.banner) formData.delete('banner')
        if (!addVideo.cover_pic) formData.delete('cover_pic')
        formData.append('task_id', this.tempData.task_id)
        formData.append('has_question', '否')
      } else {
        if (this.ruleForm.has_question == '是') {
          formData.append('has_question', '是')
        } else {
          formData.append('has_question', '否')
        }
        formData.append('question_list', this.ruleForm.question_list)
      }
      return formData
    },
    formDatas() {
      let formData = this.setformDatas()
      this.$refs['ruleForm'].validate(valid => {
        if (valid && this.checkFun()) {
          this.postTark(formData)
        } else {
          return false
        }
      })
    }
  },
  created() {
    if (this.$route.query.type != '创建任务') {
      this.tempData = JSON.parse(this.$route.query.taskData)
    }
    this.getcompany_name()
  },
  mounted() {
    if (this.$route.query.type != '创建任务') {
      this.update()
    }
  }
}
</script>

<style lang="scss">
.add_task {
  width: 100%;
  .demo-ruleForm {
    padding: 10px;
    margin-top: 20px;
    background-color: #fff;
    .task_table {
      display: flex;
      /deep/.el-form-item__content {
        display: flex;
        margin-left: 0 !important;
        a {
          color: #2a79df;
          text-decoration: underline;
          font-size: 14px;
        }
        span {
          font-size: 12px;
          color: #999999;
          margin: 0 50px;
        }
      }
    }

    .error_before {
      /deep/ .el-form-item__label::before {
        content: '*';
        color: red;
        margin-right: 4px;
      }
    }
    .cover_Img {
      width: 350px;
      height: 190px;
      border: 1px solid #dddddd;
      img {
        width: 100%;
        height: 100%;
        object-fit: contain;
      }
    }
    .bannerImg {
      width: 650px;
      height: 195px;
      border: 1px solid #dddddd;
      img {
        width: 100%;
        height: 100%;
        object-fit: contain;
      }
    }
    .btns {
      text-align: center;
      /deep/ .el-form-item__content {
        margin-left: 0 !important;
      }
    }
    /deep/ .el-form-item__label {
      text-align: center;
      color: #333333;
    }
    /deep/ .el-textarea__inner {
      height: 200px;
    }
    .enclosure {
      font-size: 12px;
      color: #3485e2;
    }
  }
}
</style>
