<!--
 * @Author: zzx
 * @Date: 2020-07-22 19:27:15
 * @LastEditTime: 2020-12-15 18:11:57
 * @FilePath: /weijian_web/src/views/user_info/pages/account_info.vue
-->
<template>
  <div class="account_info h-100 px-80px py-40px">
    <el-form ref="form" :model="form" label-width="100px" label-position="left">
      <el-form-item label="手机号">
        <span>{{ form.phone }}</span>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.name" type="text"></el-input>
        <el-button round @click="updateData()" :disabled="btnloading">修改</el-button>
      </el-form-item>
      <el-form-item label="密码" class="mb-26px">
        <!-- <el-input v-model="form.password" type='password'></el-input> -->
        <el-button round @click.native="resetPassword">重置</el-button>
      </el-form-item>
      <el-form-item label="头像" class="mb-30px">
        <div class="d-flex">
          <img v-if="form.avatar_url" :src="form.avatar_url" alt="" @error="imgError()" />
          <!-- <img :src="form.avatar_url" alt="" @error="imgError()"> -->
          <img src="../../../assets/images/user_info/touxiang.png" alt="" v-else />
          <upload button_name="上传头像" class="ml-30px" @getFileUrl="getFileUrl" />
          <!-- <el-button round @click="updateData()" style="height:40px;" class="mt-30px">修改</el-button> -->
        </div>
      </el-form-item>
      <el-form-item label="绑定微信" class="mb-26px">
        <div v-if="wx_openid">已绑定微信昵称</div>
        <div v-else>
          <el-input v-model="form.vx_name" placeholder="绑定成功显示微信昵称"></el-input>
          <el-button round @click="binding()">绑定</el-button>
        </div>
      </el-form-item>
      <!-- <el-form-item label='绑定百家号'>
        <el-input v-model="form.baijiahao" placeholder="暂未绑定百家号"></el-input>
        <el-button round>绑定</el-button>
      </el-form-item> -->
    </el-form>
    <resetPassword v-if="modal_show_name == 'resetPassword'" @Modalclose="Modalclose" />
    <bingding v-if="modal_show_bingding == 'bingding'" @bingdingClose="bingdingClose" />
  </div>
</template>

<script>
import resetPassword from '../components/change_password'
import bingding from '../components/binding'
export default {
  props: {},
  data() {
    return {
      form: {
        phone: '',
        name: '',
        avatar_url: '',
        password: '',
        vx_name: '',
        baijiahao: '',
        file: null
      },
      wx_openid: '',
      modal_show_name: '',
      modal_show_bingding: '',
      btnloading: false
    }
  },
  computed: {
    user_details() {
      return this.$store.state.user_details
    },
    user_info() {
      return this.$store.state.user_info
    }
  },
  watch: {},
  methods: {
    getInit() {
      this.$get('/user/info', { params: {} }).then(res => {
        if (res.data.code == '0000') {
          this.form.phone = res.data.data.phone
          this.form.name = res.data.data.nickname
          this.form.avatar_url = res.data.data.avatar_url
          this.wx_openid = res.data.data.wx_openid
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    bingdingClose() {
      this.modal_show_bingding = ''
    },
    binding() {
      this.modal_show_bingding = 'bingding'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'bingding')
      })
    },
    Modalclose() {
      this.modal_show_name = ''
    },
    resetPassword() {
      this.modal_show_name = 'resetPassword'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'resetPassword')
      })
    },
    getFileUrl(url, file) {
      this.form.avatar_url = url
      this.form.file = file
      this.updateData()
    },
    // 更新
    updateData() {
      this.btnloading = true
      let _this = this
      let from = new FormData()
      if (this.form.name != '') {
        from.append('nickname', this.form.name)
      }
      if (this.form.file != null) {
        from.append('avatar', this.form.file)
      }

      this.$post('/user/pc-update-user-info', from)
        .then(function(res) {
          if (res.data.code == '0000') {
            _this.btnloading = false
            _this.$message({
              message: '修改成功',
              type: 'success'
            })
          } else {
            _this.$message.error(res.data.msg)
          }
        })
        .finally(res => {
          _this.btnloading = false
        })
    },
    imgError() {
      let img = event.srcElement
      let img_error_url = require('@/assets/images/user_info/touxiang.png')
      img.src = img_error_url
      img.onerror = null // 防止闪图
    }
  },
  components: { resetPassword, bingding },
  created() {
    this.getInit()
  },
  mounted() {
    // let user_details = JSON.parse(localStorage.getItem('userDetails')) || {}
    // this.form.phone = user_details.phone
    // this.form.name = user_details.nickname
    // this.form.avatar_url = user_details.avatar_url || ''
  }
}
</script>

<style scoped lang="scss">
.account_info {
  background: #fff;
  /deep/.el-form {
    .el-form-item__label,
    .el-input__inner {
      color: #333 !important;
    }
    .el-input {
      width: 360px;
      input {
        background: #f7f7f7;
        border: 1px solid #f7f7f7;
        border-radius: 5px;
      }
    }
    .el-button {
      background: #feeded;
      color: $c;
      border: none;
      margin-left: 30px;
    }
    img {
      width: 92px;
      border-radius: 100%;
      vertical-align: -15px;
    }
  }
  // input {
  //   width: 360px;
  // }
}
</style>
