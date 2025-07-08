<!--
 * @Author: your name
 * @Date: 2020-11-27 13:59:28
 * @LastEditTime: 2021-01-06 15:36:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/mobile_login/index.vue
-->
<template>
  <div class='mobile-login'>
    <img src="../../assets/images/mobile/mobile_login.png" alt="">
    <div class='login-form'>
      <p>账号：</p>
      <el-form ref='form' :rules="rules" :model="form">
        <el-input type="text" placeholder="请输入手机号" v-model='form.phone' />
        <p>验证码：</p>
        <el-input v-model="form.ver_code"
          style="width: 100%;height: 46px"
          type="text"
          clearable
          placeholder="请输入验证码">
          <el-button slot="append" @click.native="getCode()" :disabled='disabled' class="ver-code">{{btnTitle}}</el-button>
        </el-input>
        <el-button @click.native="login('form')" class='login-btn' :loading="btn_loading">登录</el-button>
      </el-form>
    </div>
  </div>
</template>

<script>
import sha256 from 'js-sha256'
import rules from './validatae'
import * as mobile from '@/utils/mobile.js'
export default {
  props: {},
  data() {
    return {
      form: {
        phone: '',
        ver_code: ''
      },
      rules: rules,
      btnTitle: '获取验证码',
      disabled: false,
      btn_loading: false
    }
  },
  computed: {},
  watch: {},
  methods: {
    login(form) {
      this.$refs[form].validate(valid => {
        if (valid) {
          var params = {
            type: 'ver_code',
            username: this.form.phone,
            ver_code: this.form.ver_code
          }
          this.btn_loading = true
          this.$post('/user/login', params)
            .then(res => {
              if (res.data.code == '0000') {
                localStorage.setItem('user_info', JSON.stringify(res.data.data))
                this.$router.push({
                  path: '/vip'
                })
              } else {
                this.$alertMsg(res.data.msg)
              }
            })
            .finally(res => {
              this.btn_loading = false
            })
        } else {
          return false
        }
      })
    },
    validateBtn() {
      // //倒计时
      let time = 30
      let timer = setInterval(() => {
        if (time == 0) {
          clearInterval(timer)
          this.disabled = false
          this.btnTitle = '获取验证码'
        } else {
          this.btnTitle = time + '秒后重试'
          this.disabled = true
          time--
        }
      }, 1000)
    },
    getCode() {
      if (this.form.userName == '') {
        this.$message.error('请输入手机号!')
      } else {
        let myreg = /^[1][3,4,5,7,8,9][0-9]{9}$/
        if (!myreg.test(this.form.phone)) {
          return false
        } else {
          this.$post('/user/send-sms-code', { phone: this.form.phone }).then(res => {
            if (res.data.code == '0000') {
              this.validateBtn()
            } else {
              if (res.data.msg) {
                this.$message.error(res.data.msg)
              }
            }
          })
          return true
        }
      }
    }
  },
  components: {},
  created() {},
  mounted() {
    mobile.setRem(375)
  }
}
</script>

<style scoped lang="scss">
.mobile-login {
  width: 100vw;
  height: 100vh;
  background: #fff;
  padding-top: 17vh;
  img {
    display: block;
    margin: 0 auto;
    width: 1.06rem;
  }
  /deep/.login-form {
    padding-top: 8vh;
    width: 88%;
    margin: 0 auto;
    .el-input {
      margin-bottom: 0.25rem;
    }
    p {
      font-size: 0.14rem;
      margin-bottom: 0.06rem;
    }
    input {
      height: 0.44rem;
      font-size: 0.12rem;
      border: 1px solid rgba(187, 187, 187, 100);
      padding-left: 0.16rem;
      display: block;
      width: 100%;
      border-radius: 3px;
    }
    .ver-code {
      font-size: 0.14rem;
    }
    .login-btn {
      display: block;
      width: 100%;
      outline: none;
      border: none;
      background: #c51b19 10000%;
      color: #fff;
      border-radius: 10rem;
      height: 0.4rem;
      font-size: 0.14rem;
      margin-top: 0.12rem;
    }
  }
}
</style>

