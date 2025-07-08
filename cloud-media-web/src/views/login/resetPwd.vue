<template>
  <div class="login">
    <div class="login-bg">
      <img src="@/assets/images/loginBg.png" alt="">
    </div>
    <div class="login-box">
      <div class="title">欢迎使用企业云媒资</div>

      <el-tabs v-model="activeName">
        <el-tab-pane name="first">
          <span slot="label"> 原密码修改</span>

          <el-form ref="ruleForm" :model="form" :rules="rules" label-width="0">
            <el-form-item prop="oldpassword">
              <el-input
                v-model="form.oldpassword"
                type="password"
                clearable
                placeholder="请输入原密码"
              />
            </el-form-item>
            <el-form-item prop="newpassword">
              <el-input
                v-model="form.newpassword"
                type="password"
                clearable
                placeholder="请输入新的密码"
              />
            </el-form-item>
            <el-form-item prop="newpassword2">
              <el-input
                v-model="form.newpassword2"
                type="password"
                clearable
                placeholder="请确认新的密码"
                @keyup.enter.native="handleReset('ruleForm')"
              />
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <el-button
        style="widht: 100%"
        :loading="loading"
        type="primary"
        @click="handleReset('ruleForm')"
      >完成</el-button>

      <el-button
        style="widht: 100%"
        :loading="loading"
        type="info"
        @click="handleBack"
      >返回</el-button>
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
        oldpassword: '',
        newpassword: '',
        newpassword2: ''
      },
      rules: {
        newpassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
        oldpassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
        newpassword2: [{ required: true, message: '请确认新密码', trigger: 'blur' }]
      },

      loading: false,
      activeName: 'first'
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    async handleReset(formName) {
      this.$refs[formName].validate(async(valid) => {
        if (valid) {
          if (this.form.newpassword !== this.form.newpassword2) {
            this.$message({
              message: '两次输入的密码不一致',
              type: 'error'
            })
            return
          }
          const params = {
            old_pwd: this.form.oldpassword,
            new_pwd: this.form.newpassword
          }
          const { err, res } = await this.$post('/user/resetmypwd', params)
          if (err) {
            this.$message({
              message: err.msg,
              type: 'error'
            })
            return
          }
          this.$store.dispatch('user/logout')
          this.$router.push({
            path: '/login',
            query: {
              dateid: new Date().getTime()
            }
          })
        }
      })
    },
    handleBack() {
      history.go(-1)
    }
  }
}
</script>

<style scoped lang="scss">
.login {
  display: flex;
  height: 100vh;

  .login-bg {
    flex: 1;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .login-box {
    width: 558px;
    background: #fff;
    padding: 0 151px;
    display: flex;
    flex-direction: column;
    justify-content: center;

    .title {
      text-align: center;
      font-weight: 600;
      font-size: 20px;
      color: #404040;
      margin-bottom: 50px;
    }
  }

  .getCode {
    display: flex;

    ::v-deep.el-form-item__content {
      display: flex;

      .el-input {
        margin-right: 10px;
      }
    }

    .btn {
      width: 96px;
      height: 40px;
      line-height: 40px;
      background: #f7f8f9;
      border-radius: 4px;
      font-size: 12px;
      text-align: center;
      color: #404040;
      cursor: pointer;
    }
  }
}

.tips {
  text-align: center;
  font-size: 10px;
  color: #404040;

  span {
    color: blue;
  }
}

.forget {
  font-size: 10px;
  color: rgba(64,64,64,.6);
  text-align: right;
  cursor: pointer;
  margin-top: 10px;
}

::v-deep.el-tabs {

  .el-tabs__nav-wrap::after {
    display: none;
  }
}

::v-deep.el-input {

  .el-input__inner {
    border-color: transparent;
    background: #f7f8f9;
    font-size: 14px;
    box-sizing: border-box;

    &::placeholder {
      color: rgba(64,64,64,.2);
    }

    &:focus {
      border-color: #5675e8;
    }
  }

}

.el-button {
  width: 100%;
  height: 30px;
  margin-top: 20px;
  margin-left: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
