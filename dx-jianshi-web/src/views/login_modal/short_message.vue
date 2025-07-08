<!--
 * @Author: zll
 * @Date: 2020-12-02 11:57:21
 * @LastEditTime: 2020-12-21 11:29:15
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/login_modal/short_message.vue
-->
<template>
  <div class="message_wrap">
    <model id="messageWrap"
      :show_title="false"
      ref="messageWrap"
      class="model-sm">
      <div slot="body">
        <div class="fz-22px text-center my-40px">短信登录</div>

        <el-form ref="form"
          :model="form"
          :rules="rules"
          label-width="0">
          <el-form-item prop="phone"
            label-width="0" class="mb-16px">
            <el-input v-model="form.phone"
              class="mb-10px"
              style="width: 100%;height: 46px"
              clearable
              placeholder="请输入手机号">
              <i slot="prefix"
                class="iconfont iconmsnui-tel fz-22px"></i>
            </el-input>
          </el-form-item>
          <el-form-item prop="ver_code"
            label-width="0">
            <el-input v-model="form.ver_code"
              style="width: 100%;height: 46px"
              type="text"
              clearable
              placeholder="请输入验证码">
              <i slot="prefix"
                class="iconfont iconmima fz-20px"></i>
              <el-button slot="append" @click="getCode()" :disabled='disabled'>{{btnTitle}}</el-button>
            </el-input>
          </el-form-item>
        </el-form>
        <div class="mt-15px fz-12px passlogin" @click="backLogin()">密码登录</div>
      </div>
      <div slot="foot" style="width:100%">
        <div class="btns">
          <el-button type="info"
            style="width: 100%;height: 46px"
            size="small"
            @click="login('form')" :loading="btn_loading">登录</el-button>
        </div>
      </div>
    </model>
  </div>
</template>

<script>
import sha256 from 'js-sha256'
import rules from './validatae'
export default {
  props: {},
  data() {
    return {
      form: {
        ver_code: '',
        phone: ''
      },
      btnTitle: '获取验证码',
      disabled: false,
      rules: rules,
      btn_loading: false
    }
  },
  computed: {},
  watch: {},
  methods: {
    backLogin() {
      this.$refs.messageWrap.close()
      this.$store.commit('modalShow', 'loginM')
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
                let data = res.data.data
                this.$store.commit('SET_USER_INFO', data)
                this.$store.dispatch('setUserDetails').then(res => {
                  this.$emit('loginIsDone')
                  this.$refs.messageWrap.close()
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
    getCode() {
      if (this.form.phone == '') {
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
  mounted() {}
}
</script>

<style lang="scss" scoped>
.message_wrap {
  padding: 15px;
  .passlogin {
    &:hover {
      color: #1616b3;
      cursor: pointer;
    }
  }
  .btns {
    width: 90%;
    margin: 0 auto;
    margin-top: 20px;
    padding-bottom: 30px;
    button {
      width: 100%;
    }
  }
}
</style>