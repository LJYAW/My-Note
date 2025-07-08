<template>
  <div>
    <model id="loginM" ref="loginM" @close="close" :show_title="false" class="model-sm">
      <!-- <div slot="title"></div> -->

      <div slot="body">
        <div class="fz-24px text-center mb-40px mt-40px">登录</div>
        <el-form ref="form" :model="form" :rules="rules" label-width="0">
          <el-input v-model="form.phone" class="mb-20px" style="width: 100%;height: 46px" placeholder="请输入手机号">
            <i slot="prefix" class="iconfont iconmsnui-tel fz-22px"></i>
          </el-input>

          <el-input v-model="form.password" style="width: 100%;height: 46px" type="password" placeholder="请输入密码">
            <i slot="prefix" class="iconfont iconmima fz-20px"></i>
          </el-input>
        </el-form>
        <div class="d-flex justify-content-between mt-7px">
          <a class="hove-c" @click="sign">立即注册</a>
          <!-- <a class="hove-c"
             @click="forget">忘记密码？</a> -->
          <a class="hove-c" @click="message">短信登录</a>
        </div>

        <div class="mb-40px mt-24px  w-100">
          <div class="text-center">
            <el-button type="info" style="width: 100%;height: 46px" size="small" :loading="btn_loading" @click="login">登录</el-button>
          </div>

          <p @click="invitaion" v-if="0" class="fz-12px text-center mt-15px fc-c cp">申请邀请码</p>

          <p v-if="0" class="fz-12px d-flex justify-content-center mt-10px fc-ccc">
            内测用户请登录：
            <a href="https://test-magic.weijian.video">https://test-magic.weijian.video</a>
          </p>
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
      rules: rules,
      btn_loading: false,
      form: {
        phone: '',
        password: ''
      }
    }
  },
  computed: {},
  watch: {},
  methods: {
    // 短信登录
    message() {
      this.$refs.loginM.close()
      this.$store.commit('modalShow', 'messageWrap')
    },
    invitaion() {
      this.$refs.loginM.close()
      this.$store.commit('modalShow', 'invitationWrap')
    },
    close() {},
    sign() {
      this.$refs.loginM.close()
      this.$store.commit('modalShow', 'signWrap')
    },
    login() {
      var params = {
        type: 'password',
        username: this.form.phone,
        password: sha256(this.form.password)
      }
      this.btn_loading = true
      this.$post('/user/login', params)
        .then(res => {
          if (res.data.code == '0000') {
            let data = res.data.data
            this.$store.commit('SET_USER_INFO', data)
            this.$store.dispatch('setUserDetails').then(res => {
              this.$emit('loginIsDone')
              this.$refs.loginM.close()
            })
          } else {
            this.$alertMsg(res.data.msg)
          }
        })
        .finally(res => {
          this.btn_loading = false
        })
    },
    forget() {
      this.$refs.loginM.close()
      this.$store.commit('modalShow', 'changePassword')
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style lang="scss"></style>
