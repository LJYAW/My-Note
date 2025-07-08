<template>
  <div class="invitation-wrap">

    <model id="invitationWrap"
      ref="invitationWrap"
      :show_title="false"
      class="model-sm">

      <div slot="body">
        <div v-if="0" class="fz-24px text-center my-40px">申请邀请码</div>

        <el-form ref="form"
          :model="form"
          :rules="rules"
          label-width="0">

          <el-form-item prop="nickname"
            label-width="0">
            <el-input v-model="form.nickname"
              style="width: 100%;height: 46px"
              placeholder="请输入姓名">
              <i slot="prefix"
                class="iconfont iconzhanghao fz-22px"></i>
            </el-input>
          </el-form-item>

          <el-form-item prop="phone"
            label-width="0">
            <el-input v-model="form.phone"
              style="width: 100%;height: 46px"
              placeholder="请输入手机号"><i slot="prefix"
                class="iconfont iconmsnui-tel fz-22px"></i></el-input>
          </el-form-item>

          <el-form-item prop="link"
            label-width="0">
            <div class="d-flex align-items-center">
              <el-input v-model="form.link"
                style="width: 100%;height: 46px"
                placeholder="请输入自媒体号链接"><i slot="prefix"
                  class="iconfont iconfangweichaxun- fz-22px"></i></el-input>
            </div>
          </el-form-item>

          <!-- <el-form-item prop="ver_code"
                        label-width="0">
            <div class="d-flex align-items-center">
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
          </el-form-item> -->

          <!-- <el-form-item prop="invitation_code"
                        style="margin-bottom: 30px"
                        label-width="0">
            <div class="d-flex align-items-center">
              <el-input v-model="form.emli"
                        style="width: 100%;height: 46px"
                        placeholder="请输入邮箱"><i slot="prefix"
                   class="iconfont iconyaoqingmaicon fz-22px"></i></el-input>
            </div>
          </el-form-item> -->

        </el-form>

        <a @click="login">已有账号，立即登录</a>

        <div class="w-100 mt-35px mb-50px">
          <el-button style="width: 100%"
            type="info"
            :disabled="btn_status_loading"
            :loading="btn_status_loading"
            @click="submit">立即申请</el-button>
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
        link: '',
        emli: ''
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
    submit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          var params = {
            name: this.form.nickname,
            phone: this.form.phone,
            media_url: this.form.link
          }

          this.btn_status_loading = true

          this.$post('/user/apply-invitation-code', params)
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
      }

      let phone = Number(this.form.phone)

      this.$post('/user/apply-invitation-code', { phone: phone }).then(res => {
        console.log(res)
        this.time = setInterval(() => {
          this.setRemainTime()
        }, 1000)
      })
    },
    setRemainTime() {
      if (this.waitTime == 0) {
        clearInterval(this.time) // 停止计时器
        this.time = null
      } else {
        this.waitTime--
      }
    },
    login() {
      this.$refs.invitationWrap.close()
      this.$store.commit('modalShow', 'loginM')
    },
    validatePhoneNumber(str) {
      const reg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/
      return reg.test(str)
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
</style>
