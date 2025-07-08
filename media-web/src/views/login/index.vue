<!--
 * @Author: your name
 * @Date: 2021-08-03 14:37:09
 * @LastEditTime: 2021-08-24 15:54:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/login/index.vue
-->
<template>
  <div class="login">
    <div class="login-bg">
      <img src="@/assets/images/loginBg.png" alt="">
    </div>
    <div class="login-box">
      <div class="title">欢迎使用企业云媒资</div>

      <el-tabs v-model="activeName">
        <el-tab-pane name="first">
          <span slot="label"> 账号登录</span>

          <el-form ref="ruleForm" :model="form" :rules="rules" label-width="0">
            <el-form-item prop="mobile">
              <el-input
                v-model="form.mobile"
                clearable
                placeholder="登录账号"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="form.password"
                type="password"
                clearable
                placeholder="输入密码"
                @keyup.enter.native="handleLogin('ruleForm')"
              />
            </el-form-item>
          </el-form>

        </el-tab-pane>

        <!-- <el-tab-pane name="second">
          <span slot="label">手机登录</span>

          <el-form ref="ruleForm" :model="form" :rules="rules" label-width="0">
            <el-form-item prop="mobile">
              <el-input
                v-model="form.mobile"
                clearable
                placeholder="登录账号"
              />
            </el-form-item>
            <el-form-item class="getCode" prop="mcode">
              <el-input
                v-model="form.mcode"
                type="password"
                clearable
                placeholder="输入验证码"
                @keyup.enter.native="handleLogin('ruleForm')"
              />
              <div v-show="show" class="btn" @click="getCode">获取验证码</div>
              <div v-show="!show" class="btn">{{ count }} s</div>
            </el-form-item>
          </el-form>

        </el-tab-pane> -->
      </el-tabs>

      <div class="tips">登录即表明同意<span> 服务协议 </span>和 <span>隐私条款</span></div>
      <el-button
        style="widht: 100%"
        :loading="loading"
        type="primary"
        @click="handleLogin('ruleForm')"
      >立即登录</el-button>
      <!-- <div class="forget">忘记密码</div> -->
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
        mobile: '11111111112',
        password: '123456',
        mcode: ''
      },
      rules: {
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        mobile: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        mcode: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
      },

      loading: false,
      activeName: 'first',
      show: true,
      count: '',
      timer: null
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
    getCode() {
      const TIME_COUNT = 60
      if (!this.timer) {
        this.count = TIME_COUNT
        this.show = false
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= TIME_COUNT) {
            this.count--
          } else {
            this.show = true
            clearInterval(this.timer)
            this.timer = null
          }
        }, 1000)
      }
    },
    async handleLogin(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true
          const params = {
            mobile: this.form.mobile,
            passwd: this.form.password
          }
          this.$store.dispatch('user/login', params)
            .then(async() => {
              // await this.$store.dispatch('user/getDirInfo')
              // await this.$store.dispatch('company/getCompanyInfo')

              this.$router.push({
                path: '/',
                query: {
                  dateid: new Date().getTime()
                }
              }).catch(() => {})
              this.loading = false
            })
            .catch((res) => {
              this.$message({
                message: res.msg,
                type: 'error'
              })
              this.loading = false
            })
        }
      })
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
    margin-top: -200px;
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
  margin-top: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #5675e8;
  padding: 0;
}
</style>
