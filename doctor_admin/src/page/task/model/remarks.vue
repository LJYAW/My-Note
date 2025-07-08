<!--
 * @Author: your name
 * @Date: 2020-11-12 18:07:48
 * @LastEditTime: 2020-12-28 11:46:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/page/task/model/remarks.vue
-->
<template>
  <div class="remarks_wrap">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="" prop="remarks">
        <el-input type="textarea" v-model="ruleForm.remarks" placeholder="请输入审核不通过原因"></el-input>
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" @click="submitForm('ruleForm')" round size="medium">确定</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: '',
  props: {
    layerid: {
      type: String,
      default: ''
    },
    item: {
      type: String,
      default: ''
    },
    _this: '',
    type: ''
  },
  data() {
    return {
      ruleForm: {
        remarks: ''
      },
      rules: {
        remarks: [{ required: true, message: '请填写审核不通过原因', trigger: 'blur' }]
      }
    }
  },
  components: {},
  computed: {
    params() {
      return JSON.parse(this.item)
    }
  },
  watch: {},
  methods: {
    submitForm(ruleForm) {
      this.$refs[ruleForm].validate(valid => {
        if (valid) {
          var path = ''
          var params = ''
          if (this.type == 'have_tab') {
            params = {
              task_question_id: this.params.task_question_id,
              approve_status: '审核不通过',
              mark: this.ruleForm.remarks
            }
            path = '/admin/task/approve-task-question-video'
          } else {
            params = {
              task_video_id: this.params.task_video_id,
              status: '审核不通过',
              mark: this.ruleForm.remarks
            }
            path = '/admin/task/approve-task-video'
          }
          this.$post(path, params).then(
            res => {
              if (res.data.code == '0000') {
                this.$layer.close(this.layerid)
                this._this.getList()
              } else {
                this.$message({
                  message: res.data.msg,
                  type: 'warning'
                })
              }
            },
            err => {
              console.log(err)
            }
          )
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
.remarks_wrap {
  width: 100%;
  padding: 30px;
  /deep/ .el-textarea__inner {
    height: 300px;
  }
  /deep/ .el-form-item__label {
    color: #333333;
  }
  /deep/ .el-form-item__content {
    margin: 0 !important;
    text-align: center;
  }
}
</style>
