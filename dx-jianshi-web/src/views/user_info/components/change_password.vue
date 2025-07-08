<template>
  <div class="sign-wrap">

    <model id="resetPassword"
      ref="changePassword"
      @close="close"
      class="model-sm">

      <div slot="title">
        <div>重置密码</div>
      </div>

      <div slot="body">
        <el-form ref="form"
          :model="form"
          :rules="rules"
          label-width="80px">

          <el-form-item prop="phone"
            label-width="0">
            <el-input v-model="form.phone"
              style="width: 100%;height: 46px"
              placeholder="请输入手机号"><i slot="prefix"
                class="iconfont iconmsnui-tel fz-22px"></i></el-input>
          </el-form-item>

          <el-form-item prop="ver_code"
            label-width="0">
            <div class="d-flex align-items-center">
              <el-input v-model="form.ver_code"
                style="width: 100%;height: 46px"
                placeholder="请输入验证码"><i slot="prefix"
                  class="iconfont iconfangweichaxun- fz-22px"></i></el-input>
              <div style="width: 260px;text-align: right">
                <a class="flex-end fc-blue"
                  v-if="!time"
                  @click="sendCode">获取验证码</a>
                <span v-else
                  class="fc-666 fz-12px">({{this.waitTime}}) 秒内重新获取验证</span>
              </div>
            </div>
          </el-form-item>

          <el-input v-model="form.password"
            class="mb-5px pass"
            type="password"
            style="width: 100%;height: 46px"
            placeholder="请输入新密码"><i slot="prefix"
              class="iconfont iconmima fz-22px"></i></el-input>

        </el-form>

        <!-- <a class="hove-c mt-4px d-block"
          @click="goToLogin()">返回登录</a> -->

        <div class="w-100 mt-35px mb-50px">
          <el-button style="width: 100%"
            type="info"
            @click="save" :disabled="btn_loading">确认</el-button>
        </div>

      </div>

      <!-- <div slot="foot"> -->

      <!-- </div> -->

    </model>

  </div>
</template>

<script>
import rules from './validatae'
import sha256 from 'js-sha256'

export default {
  props: {},
  data() {
    return {
      form: {
        nickname: '',
        phone: '',
        password: '',
        ver_code: ''
      },
      btn_loading: false,
      rules: rules,
      waitTime: 61,
      time: null
    }
  },
  computed: {},
  watch: {},
  methods: {
    close() {
      this.$emit('Modalclose')
    },
    save() {
      let params = {
        new_password: sha256(this.form.password),
        ver_code: this.form.ver_code
      }
      this.btn_loading = true
      this.$post('/user/change-password', params)
        .then(res => {
          if (res.data.code == '0000') {
            this.btn_loading = false
            this.$message({
              message: '重置成功',
              type: 'success'
            })
            this.close()
          } else {
            this.$message.error(res.data.msg)
          }
          console.log(res)
        })
        .finally(res => {
          this.btn_loading = false
        })
    },
    validatePhoneNumber(str) {
      const reg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/
      return reg.test(str)
    },
    setRemainTime() {
      if (this.waitTime == 0) {
        clearInterval(this.time) // 停止计时器
        this.time = null
      } else {
        this.waitTime--
      }
    },
    sendCode() {
      if (!this.validatePhoneNumber(this.form.phone)) {
        this.$alertMsg('请输入正确的手机号')
        return
      }
      let phone = Number(this.form.phone)

      this.$post('/user/send-sms-code', { phone: phone }).then(res => {
        console.log(res)
        this.time = setInterval(() => {
          this.setRemainTime()
        }, 1000)
      })
    },
    goToLogin() {
      this.$store.commit('modalHidden', 'changePassword')
      this.$store.commit('modalShow', 'loginM')
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.sign-wrap {
  .pass {
    /deep/.el-input__prefix {
      top: 8px;
    }
  }
}
</style>
