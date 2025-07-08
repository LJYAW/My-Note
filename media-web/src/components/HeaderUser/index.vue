<template>
  <div class="headlogin">
    <template v-if="userInfo.mobile && roles.length">
      <el-dropdown trigger="click">
        <div class="user-info">
          <span class="nickname" style="margin-right: 5px;">{{ formatmobile() }}</span>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link v-if="haveAfterMenu" to="/manage-center">
            <el-dropdown-item>进入管理中心</el-dropdown-item>
          </router-link>
          <router-link v-if="hasVideoLibrary" to="/my-collection">
            <el-dropdown-item>我的收藏</el-dropdown-item>
          </router-link>
          <router-link v-if="hasVideoLibrary" to="/my-cut-videos">
            <el-dropdown-item>我的剪辑</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="changePassword">
            <span style="display: block;">修改密码</span>
          </el-dropdown-item>
          <el-dropdown-item @click.native="logout">
            <span style="display: block;">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </template>
    <template v-else>
      <div style="margin-right: 20px;" @click="login()">登录</div>
      <div @click="register()">注册</div>
    </template>

    <!-- login -->
    <base-dialog :show.sync="dialog.show" :show-close="false" width="710px" class="dialog311">
      <div class="popup311 list_flex align_items" style="height: 100%;">
        <div class="con list_flex">
          <div class="close" @click="dialogclose">
            <div class="icon">close</div>
          </div>
          <div class="leftpane list_flex align_items">
            <div>
              <div class="title">视迅云</div>
              <div class="subtitle">一站式视频管理与创作</div>
              <div class="desc">
                智能化视频分析技术<br>
                企业级的视频管理方案<br>
                视频快速剪辑创作
              </div>
            </div>
          </div>
          <div class="rightpane">

            <div v-if="dialog.type === 'register'" class="dialogform">
              <div class="title">账号注册</div>
              <div class="fdiv">
                <input v-model="dialog.registerForm.mobile" type="text" maxlength="11" placeholder="手机号" autocomplete="new-mobile">
              </div>
              <div class="fdiv list_flex align_items">
                <div class="box_flex_1">
                  <input v-model="dialog.registerForm.code" type="text" placeholder="输入验证码" autocomplete="new-code">
                </div>
                <div class="codebtn">
                  <div v-if="dialog.countDown" class="code">{{ dialog.countDown }}&nbsp;s</div>
                  <div v-else class="btn" @click="sendCode('signup')">获取验证码</div>
                </div>
              </div>
              <div class="fdiv">
                <input v-model="dialog.registerForm.password" type="password" placeholder="输入密码" autocomplete="new-password">
              </div>
              <div class="fdiv">
                <div class="btn" @click="handleRegister()">立即注册</div>
              </div>
              <div class="fdiv">
                <div class="tcp">
                  <span @click="login()">用已有账号登录</span>
                </div>
              </div>
              <div class="fdiv">
                <div class="tcp">
                  注册即表明同意<span>服务协议</span>和<span>隐私条款</span>
                </div>
              </div>
            </div>
            <div v-else-if="dialog.type === 'changepassword'" class="dialogform">
              <div class="title">重置密码</div>
              <div class="fdiv">
                <input v-model="dialog.changepasswordForm.mobile" type="text" maxlength="11" placeholder="请输入手机号" autocomplete="new-mobile">
              </div>
              <div class="fdiv list_flex align_items">
                <div class="box_flex_1">
                  <input v-model="dialog.changepasswordForm.code" type="text" placeholder="输入验证码" autocomplete="new-code">
                </div>
                <div class="codebtn">
                  <div v-if="dialog.countDown" class="code">{{ dialog.countDown }}&nbsp;s</div>
                  <div v-else class="btn" @click="sendCode('resetpasswd')">获取验证码</div>
                </div>
              </div>
              <div class="fdiv">
                <input v-model="dialog.changepasswordForm.password" type="password" placeholder="请输入新密码" autocomplete="new-password">
              </div>
              <div class="fdiv">
                <div class="btn" @click="handleChangepassword()">立即修改</div>
              </div>
            </div>
            <div v-else-if="dialog.type === 'login'" class="dialogform">
              <div class="title">欢迎登录</div>
              <div class="fdiv list_flex align_items">
                <div :class="dialog.accountType?'active':''" class="t_item" @click="dialog.accountType = true">账号登录</div>
                <div :class="!dialog.accountType?'active':''" class="t_item" @click="dialog.accountType = false">验证码登录</div>
              </div>
              <div class="fdiv">
                <input v-model="dialog.loginForm.mobile" type="text" maxlength="11" placeholder="手机号" autocomplete="new-mobile">
              </div>
              <template v-if="dialog.accountType">
                <div class="fdiv">
                  <input v-model="dialog.loginForm.password" type="password" placeholder="输入密码" autocomplete="new-password">
                </div>
              </template>
              <template v-else>
                <div class="fdiv list_flex align_items">
                  <div class="box_flex_1">
                    <input v-model="dialog.loginForm.code" type="text" placeholder="输入验证码" autocomplete="new-code">
                  </div>
                  <div class="codebtn">
                    <div v-if="dialog.countDown" class="code">{{ dialog.countDown }}&nbsp;s</div>
                    <div v-else class="btn" @click="sendCode('login')">获取验证码</div>
                  </div>
                </div>
              </template>
              <div class="fdiv">
                <div class="tcp">
                  登录即表明同意<span>服务协议</span>和<span>隐私条款</span>
                </div>
              </div>
              <div class="fdiv">
                <div class="btn" @click="handleLogin()">立即登录</div>
              </div>
              <div class="fdiv">
                <div class="tcp">
                  <span @click="register()">立即注册</span><span @click="changePassword()">忘记密码</span>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    </base-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import setMobile from '@/utils/mobile.js'
import router from '../../router'

export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      dialog: {
        show: false,
        countDown: 0,
        countDownTimeout: null,
        loading: false,
        redirect: '', // url
        type: '', // register login changepassword
        accountType: true, // true 账号, false 手机
        loginForm: {
          mobile: '',
          password: '',
          code: ''
        },
        registerForm: {
          mobile: '',
          password: '',
          code: ''
        },
        changepasswordForm: {
          mobile: '',
          code: '',
          password: ''
        }
      }
    }
  },
  computed: {
    ...mapGetters([
      'roles',
      'userInfo',
      'showHeader'
    ]),
    hasVideoLibrary() {
      return this.roles.some(item => item === '视频管家')
    }
  },
  watch: {

  },
  beforeDestroy() {
    this.$bus.$off('openLogin')
  },
  created() {

  },
  mounted() {
    this.$bus.$on('openLogin', (path) => {
      this.dialog.redirect = path
      this.login()
    })
  },
  methods: {
    formatmobile() {
      return setMobile(this.userInfo.mobile)
    },
    haveAfterMenu() {
      return !!this.userInfo.after_menu || this.userInfo.type === 1
    },
    openblank(path) {
      const { href } = this.$router.resolve(path)
      window.open(href, '_blank')
    },
    // 登录注册 相关
    dialogclose() {
      this.dialog.show = false
      this.dialog.redirect = ''
    },
    resetdialog(type) {
      this.dialog.show = true
      this.dialog.type = type
      this.dialog.countDown = 0
      if (this.dialog.countDownTimeout) {
        clearTimeout(this.dialog.countDownTimeout)
      }
    },
    changePassword() {
      this.resetdialog('changepassword')
    },
    register() {
      this.resetdialog('register')
    },
    login() {
      this.resetdialog('login')
    },
    cdtimer() {
      if (this.dialog.countDownTimeout) {
        clearTimeout(this.dialog.countDownTimeout)
      }
      this.dialog.countDownTimeout = setTimeout(() => {
        if (this.dialog.countDown > 0) {
          this.dialog.countDown--
          this.cdtimer()
        } else {
          clearTimeout(this.dialog.countDownTimeout)
        }
      }, 1000)
    },
    async sendCode(type) {
      if (type === 'login') {
        if (!this.dialog.loginForm.mobile) {
          this.$message({
            message: '请输入手机号',
            type: 'error'
          })
          return
        }
      } else if (type === 'signup') {
        if (!this.dialog.registerForm.mobile) {
          this.$message({
            message: '请输入手机号',
            type: 'error'
          })
          return
        }
      } else if (type === 'resetpasswd') {
        if (!this.dialog.changepasswordForm.mobile) {
          this.$message({
            message: '请输入手机号',
            type: 'error'
          })
          return
        }
      }
      if (this.dialog.loading) {
        return
      }
      if (this.dialog.countDown > 0) {
        return
      }
      this.dialog.loading = true
      const params = {
        mobile: '',
        t: type
      }
      if (type === 'login') {
        params['mobile'] = this.dialog.loginForm.mobile
      } else if (type === 'signup') {
        params['mobile'] = this.dialog.registerForm.mobile
      } else if (type === 'resetpasswd') {
        params['mobile'] = this.dialog.changepasswordForm.mobile
      }
      const { err, res } = await this.$post('/user/vcode', params)
      this.dialog.loading = false
      if (res) {
        this.$message({
          message: '验证码已发送',
          type: 'success'
        })
        this.dialog.countDown = 60
        this.cdtimer()
      }
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
      }
    },
    handleLogin() {
      if (!this.dialog.loginForm.mobile) {
        this.$message({
          message: '请输入手机号',
          type: 'error'
        })
        return
      }
      if (this.dialog.accountType && !this.dialog.loginForm.password) {
        this.$message({
          message: '请输入密码',
          type: 'error'
        })
        return
      }
      if (!this.dialog.accountType && !this.dialog.loginForm.code) {
        this.$message({
          message: '请输入验证码',
          type: 'error'
        })
        return
      }
      if (this.dialog.loading) {
        return
      }
      const params = {
        mobile: this.dialog.loginForm.mobile.trim()
      }
      if (this.dialog.accountType) {
        params['passwd'] = this.dialog.loginForm.password
      } else {
        params['code'] = this.dialog.loginForm.code
        params['t'] = 'login'
      }
      this.dialog.loading = true
      this.$store.dispatch('user/login', params).then(async() => {
        const path = {
          path: '/',
          query: {
            dateid: new Date().getTime()
          }
        }
        // todo
        await this.updateRouter()
        if (this.dialog.redirect) {
          path.path = this.dialog.redirect
          if (path.path === '/home') {
            path.path = this.roles.includes('视频管家') ? '/home' : '/pay-qr'
          }
          path.query = {}
          this.openblank(path)
        }
        // this.$router.push(path)
        this.dialog.loading = false
        this.dialogclose()
        // todo
        // this.updateRouter()
      }).catch((res) => {
        this.$message({
          message: res.msg,
          type: 'error'
        })
        this.dialog.loading = false
      })
    },
    async handleChangepassword() {
      if (!this.dialog.changepasswordForm.mobile) {
        this.$message({
          message: '请输入手机号',
          type: 'error'
        })
        return
      }
      if (!this.dialog.changepasswordForm.code) {
        this.$message({
          message: '请输入验证码',
          type: 'error'
        })
        return
      }
      if (!this.dialog.changepasswordForm.password) {
        this.$message({
          message: '请输入密码',
          type: 'error'
        })
        return
      }
      if (this.dialog.loading) {
        return
      }
      this.dialog.loading = true
      const params = {
        mobile: this.dialog.changepasswordForm.mobile,
        code: this.dialog.changepasswordForm.code,
        passwd: this.dialog.changepasswordForm.password,
        t: 'resetpasswd'
      }
      const { err, res } = await this.$post('/user/resetpwdByCode', params)
      this.dialog.loading = false
      if (res) {
        this.$message({
          message: '修改密码成功',
          type: 'success'
        })
        this.dialogclose()
      }
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
      }
    },
    async handleRegister() {
      if (!this.dialog.registerForm.mobile) {
        this.$message({
          message: '请输入手机号',
          type: 'error'
        })
        return
      }
      if (!this.dialog.registerForm.code) {
        this.$message({
          message: '请输入验证码',
          type: 'error'
        })
        return
      }
      if (!this.dialog.registerForm.password) {
        this.$message({
          message: '请输入密码',
          type: 'error'
        })
        return
      }
      if (this.dialog.loading) {
        return
      }
      this.dialog.loading = true
      const params = {
        mobile: this.dialog.registerForm.mobile,
        code: this.dialog.registerForm.code,
        passwd: this.dialog.registerForm.password,
        t: 'signup'
      }
      const { err, res } = await this.$post('/user/signup', params)
      this.dialog.loading = false
      if (res) {
        this.$message({
          message: '注册成功',
          type: 'success'
        })
        this.dialogclose()
      }
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
      }
    },
    // 刷新路由 todo
    async updateRouter() {
      if (this.roles && this.roles.length < 1) {
        await this.$store.dispatch('user/getUserInfo')
        await this.$store.dispatch('user/getDirInfo')
        await this.$store.dispatch('company/getCompanyInfo')
        const roles = this.$store.getters.roles
        const accessRoutes = await this.$store.dispatch('permission/generateRoutes', roles)
        router.addRoutes(accessRoutes)
      }
    },
    // 登出
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login`)
      // this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    }
  }
}
</script>

<style scoped lang="scss">
.list_flex {
  display: flex;
  flex-wrap: wrap;

  .box_item {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: stretch;
    align-content: stretch;
  }
}

.box_flex_1 {
  flex: 1;
}

.align_items {
  align-items: center;
}

.justify_content {
  justify-content: space-between;
}

.btn {
  margin-top: 30px;
  display: inline-block;
  height: 42px;
  line-height: 42px;
  padding: 0 30px;
  background: #5675e8;
  border-radius: 4px;
  color: #fff;
  cursor: pointer;

  &:hover {
    background: #4160d2;
  }
}

.el-dropdown-menu {
  margin-top: 8px;
  padding: 10px;

  .el-dropdown-menu__item {
    padding: 9px 16px;
    font-size: 13px;
    line-height: 1;
    color: #404040;

    &:hover,
    &:focus {
      background: #f7f8f9;
      border-radius: 4px;
    }
  }

  ::v-deep .popper__arrow {
    display: none;
  }
}

.popup311 {
  position: fixed;
  width: 100%;
  left: 0;
  bottom: 0;
  z-index: 1000;
  justify-content: center;

  .con {
    position: relative;
    width: 710px;
    min-height: 300px;

    .leftpane {
      width: 270px;
      background: url("../../assets/images/guide/loginbg.png") no-repeat;
      background-size: cover;
      color: #fff;
      padding-left: 40px;
      border-top-left-radius: 4px;
      border-bottom-left-radius: 4px;

      .title {
        font-size: 30px;
        font-weight: bold;
      }

      .subtitle {
        margin-top: 10px;
        font-size: 18px;
        font-weight: bold;
      }

      .desc {
        margin-top: 35px;
        line-height: 3.4;
        font-size: 14px;
      }
    }

    .rightpane {
      flex: 1;
      background-color: #fff;
      border-top-right-radius: 4px;
      border-bottom-right-radius: 4px;
      padding: 50px 60px;
    }

    .close {
      position: absolute;
      width: 28px;
      height: 28px;
      top: 8px;
      right: 10px;
      cursor: pointer;
      text-indent: -999em;

      &:before,
      &:after {
        position: absolute;
        content: " ";
        width: 80%;
        height: 2px;
        background-color: #9e9e9e;
        top: 50%;
        left: 50%;
        transform: translateX(-50%) translateY(-50%) rotate(45deg);
      }

      &:after {
        transform: translateX(-50%) translateY(-50%) rotate(-45deg);
      }

      &:hover {

        &:before,
        &:after {
          background-color: #5675e8;
        }
      }
    }
  }
}

.dialogform {
  margin: 0;

  .title {
    text-align: center;
    font-size: 20px;
    font-weight: 600;
    color: #404040;
  }

  .fdiv {
    margin-top: 20px;

    .t_item {
      font-size: 16px;
      color: #404040;
      cursor: pointer;
      border-bottom: 2px solid transparent;
      border-radius: 2px;
      padding-bottom: 8px;
      margin-right: 20px;

      &.active {
        color: #5675e8;
        border-bottom-color: #5675e8;
      }
    }

    .label {
      margin-bottom: 8px;
    }

    input {
      height: 34px;
      line-height: 34px;
      background: rgba(247, 248, 249, 1);
      border-radius: 4px;
      border: 0;
      webkit-appearance: none;
      width: 100%;
      padding: 0 12px;
    }

    .codebtn {
      width: 100px;
      margin-left: 10px;

      .code {
        width: 100%;
        height: 100%;
        text-align: center;
        background-color: #f7f8f9;
        height: 34px;
        line-height: 34px;
        border-radius: 4px;
      }
    }

    .btn {
      margin-top: 0;
      width: 100%;
      padding-left: 0;
      padding-right: 0;
      text-align: center;
      height: 34px;
      line-height: 34px;
    }

    .tcp {
      color: #404040;
      font-size: 12px;
      text-align: center;

      span {
        margin: 0 8px;
        color: #5675e8;
        cursor: pointer;
      }
    }
  }
}

.dialog311 {

  ::v-deep .el-dialog__header {
    display: none;
  }

  ::v-deep .el-dialog__body {
    padding: 0;
  }
}
</style>
