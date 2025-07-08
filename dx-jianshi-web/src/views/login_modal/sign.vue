<template>
  <div class="sign-wrap">

    <model id="signWrap"
      ref="signWrap"
      :show_title="false"
      class="model-sm">

      <div slot="body">
        <div class="fz-24px text-center my-40px">注册</div>

        <el-form ref="form"
          :model="form"
          :rules="rules"
          label-width="0">

          <el-form-item prop="nickname"
            label-width="0">
            <el-input v-model="form.nickname" type="password" style="position:fixed;bottom:-9999px;display:none;"></el-input>
            <el-input v-model="form.nickname"
              placeholder="请输入昵称">
              <i slot="prefix"
                class="iconfont iconzhanghao fz-22px"></i>
            </el-input>
          </el-form-item>

          <el-form-item prop="phone"
            label-width="0">
            <el-input v-model="form.phone" type="password" style="position:fixed;bottom:-9999px;display:none;"></el-input>
            <el-input v-model="form.phone"
              placeholder="请输入手机号"><i slot="prefix"
                class="iconfont iconmsnui-tel fz-22px"></i></el-input>
          </el-form-item>

          <el-form-item prop="ver_code"
            label-width="0">
            <div class="d-flex align-items-center">
              <el-input v-model="form.ver_code" type="password" style="position:fixed;bottom:-9999px;display:none;"></el-input>
              <el-input v-model="form.ver_code"
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

          <!-- <el-form-item prop="invitation_code"
            style="margin-bottom: 30px"
            label-width="0">
            <div class="d-flex align-items-center">
              <el-input v-model="form.invitation_code"
                placeholder="请输入邀请码" auto-complete="off"><i slot="prefix"
                  class="iconfont iconyaoqingmaicon fz-22px"></i></el-input>
            </div>
            <span class="invitation-text fc-999">系统内测阶段，只接受邀请用户注册</span>
          </el-form-item> -->
          <el-input v-model="form.password" type="password" style="position:fixed;bottom:-9999px;display:none;"></el-input>
          <el-input v-model="form.password"
            class="mb-5px"
            type="password"
            placeholder="请输入密码">
            <i slot="prefix"
              class="iconfont iconmima fz-22px"></i>
          </el-input>

        </el-form>

        <a @click="login">已有账号，立即登录</a>

        <div class="w-100 mt-35px mb-50px">
          <el-button style="width: 100%"
            type="info"
            :disabled="btn_status_loading"
            :loading="btn_status_loading"
            @click="sign">注册</el-button>
          <p @click="invitaion" v-if="0"
            class="fz-12px text-center mt-15px fc-c cp">申请邀请码</p>
        </div>

      </div>

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
        ver_code: '',
        invitation_code: ''
      },
      rules: rules,
      waitTime: 61,
      time: null,
      btn_status_loading: false
    }
  },
  computed: {},
  watch: {},
  methods: {
    invitaion() {
      this.$refs.signWrap.close()
      this.$store.commit('modalShow', 'invitationWrap')
    },
    sign() {
      this.$refs.form.validate(valid => {
        if (valid) {
          var params = {
            phone: this.form.phone,
            ver_code: this.form.ver_code,
            password: sha256(this.form.password),
            nickname: this.form.nickname,
            invitation_code: this.form.invitation_code
          }

          this.btn_status_loading = true

          this.$post('/user/register', params)
            .then(res => {
              if (res.data.code == '0000') {
                this.$store.commit('modalHidden', 'signWrap')
                this.$store.commit('modalShow', 'loginM')
              }
              this.$alertMsg(res.data.msg)
            })
            .finally(res => {
              this.btn_status_loading = false
            })
        }
      })
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
    login() {
      this.$refs.signWrap.close()
      this.$store.commit('modalShow', 'loginM')
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
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.invitation-text {
  font-size: 12px;
  line-height: 1;
  padding-top: 4px;
  position: absolute;
  top: 100%;
  left: 0;
}
</style>
