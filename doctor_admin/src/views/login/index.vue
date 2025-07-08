<!--
 * @Author: zll
 * @Date: 2020-07-22 19:16:56
 * @LastEditTime: 2020-12-18 18:06:43
 * @FilePath: /weijian_web/src/views/404/index.vue
--> 
<template>
  <div class="login_wrap">
    <div class="loginBox">
      <img src="../../assets/images/login.jpg" alt="">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号:" prop="username">
          <el-input v-model="ruleForm.username" placeholder="请输入您的账号"></el-input>
        </el-form-item>
        <el-form-item label="密码:" prop="password">
          <el-input v-model="ruleForm.password" type="password" placeholder="请输入您的密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')" round :plain="true">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import sha256 from 'js-sha256'

export default {
  props: {},
  data() {
    return {
      ruleForm: {
        username: '',
        password: ''
      },
      rules: {
        user_name: [{ required: true, message: '请输入您的账号', trigger: 'blur' }],
        password: [
          { required: true, message: '请输入您的密码', trigger: 'blur' },
          { min: 6, max: 6, message: '请输入6位密码', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {},
  watch: {},
  methods: {
    submitForm(ruleForm) {
      this.$refs[ruleForm].validate(valid => {
        if (valid) {
          this.ruleForm.password = sha256(this.ruleForm.password) //密码加密
          this.$post('/admin/user/login', this.ruleForm).then(
            res => {
              if (res.data.code == '0000') {
                localStorage.setItem('user_info', JSON.stringify(res.data.data))
                this.$router.push({
                  path: `/home`,
                  query: {}
                })
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
      console.log('🚀 ~ file: index.vue ~ line 77 ~ submitForm ~ this.ruleForm', this.ruleForm)
      console.log('🚀 ~ file: index.vue ~ line 77 ~ submitForm ~ this.ruleForm', this.ruleForm)
      console.log('🚀 ~ file: index.vue ~ line 77 ~ submitForm ~ this.ruleForm', this.ruleForm)
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.login_wrap {
  position: relative;
  width: 100%;
  height: 100%;
  // background-color: #4586db;

  .loginBox {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 100%;
    height: auto;
    padding: 30px;
    background: #4586db;
    text-align: center;
    .demo-ruleForm {
      display: inline-block;
      vertical-align: middle;
      /deep/ .el-button {
        width: 300px;
        height: 36px;
        background: #d0e1f6;
        color: #333;
      }
      /deep/ .el-input__inner {
        width: 300px;
      }
      /deep/ .el-form-item__label {
        color: #ffffff;
      }
    }
  }
}
</style>
