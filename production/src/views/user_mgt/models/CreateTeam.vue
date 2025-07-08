<!--
 * @Author: your name
 * @Date: 2021-01-18 21:05:55
 * @LastEditTime: 2021-01-19 17:49:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/user_mgt/models/CreateTeam.vue
-->
<template>
  <div>
    <el-form ref="ruleForm" :model="form" :rules="rules" :inline="true">
      <el-form-item prop="team_name" label="团队名称" label-width="auto">
        <el-input v-model="form.team_name" placeholder="请输入团队名称" />
      </el-form-item>
    </el-form>
    <div class="dialog_footer">
      <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
      <el-button @click="close('ruleForm')">取消</el-button>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      form: {
        team_name: ''
      },
      rules: {
        team_name: [{
          required: true, message: '请输入团队名称', trigger: 'blur'
        }]
      }
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.form.team_name = ''
  },
  mounted() {

  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$post('/user/teamAdd', { name: this.form.team_name }).then((res) => {
            this.$message({
              message: '创建团队成功',
              type: 'success'
            })
            this.close()
          }).catch((error) => {
            this.$message.error(error.msg)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    close() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped lang="scss">
.dialog_footer{
    text-align: center;
}
</style>
