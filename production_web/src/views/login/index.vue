<!--
 * @Author: zll
 * @Date: 2021-01-11 15:50:41
 * @LastEditTime: 2021-04-23 14:21:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/login/login.vue
-->
<template>
  <div class="login-wrap">
    <div class="logo">
      <img :src="logo" alt="">
    </div>
    <div class="login-box">
      <div class="login-left">
        <div class="triangle-bottomleft" />
        <div class="triangle-bottomright" />
        <div class="message">
          <div>
            <p class="text-1">welcome</p>
            <p class="text-2">{{ settings.enLoginTitle }}</p>
            <div class="border-div" />
            <p class="text-3">{{ settings.loginTitle }}</p>
          </div>
          <div class="text-4">
            <div>©tmvideo</div>
            <div>版本：V3.0.0</div>
          </div>
        </div>
      </div>
      <div class="login-right">
        <div class="title">
          账户登录
        </div>
        <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="0" class="demo-ruleForm">
          <el-form-item class="item-border" prop="username">
            <el-input
              v-model="ruleForm.username"
              style="height:60px"
              clearable
              placeholder="请输入用户名"
            >
              <i
                slot="prefix"
                class="el-icon-user-solid icon-list"
              />
            </el-input>
          </el-form-item>
          <el-form-item class="item-border password-wrap" prop="password">
            <el-input
              v-model="ruleForm.password"
              type="password"
              clearable
              placeholder="请输入密码"
              @keyup.enter.native="handleLogin('ruleForm')"
            >
              <i
                slot="prefix"
                class="el-icon-lock icon-list"
              />
            </el-input>
          </el-form-item>
          <el-form-item class="remember-pass">
            <el-checkbox-group v-model="remember">
              <el-checkbox label="记住密码" name="remember" disabled />
            </el-checkbox-group>
            <a style="margin-top:10px">忘记密码</a>
          </el-form-item>
          <el-form-item class="btns">
            <el-button
              :loading="loading"
              type="primary"
              @click="handleLogin('ruleForm')"
            >登  录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>

export default {
  components: {},
  data() {
    return {
      logo: require('@/assets/images/logo.png'),
      ruleForm: {
        username: '',
        password: '',
        ver_code: '',
        type: 'password' // or ver_code
      },
      remember: '',
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      loading: false
    }
  },
  computed: {
    settings() {
      return this.$store.state.settings
    }
  },
  watch: {},
  created() {
  },
  mounted() {},
  methods: {
    handleLogin(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.ruleForm)
            .then(() => {
              this.$router.push({
                path: '/',
                query: {
                  dateid: new Date().getTime()
                }
              })
              this.loading = false
            })
            .catch((res) => {
              this.$message({
                message: res.msg,
                type: 'error'
              })
              this.loading = false
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>
<style lang='scss' scoped>
@import "./login.scss";
</style>
