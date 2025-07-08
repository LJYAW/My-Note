<!--
 * @Author: your name
 * @Date: 2020-12-16 14:56:01
 * @LastEditTime: 2020-12-21 14:30:56
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/login_modal/enterprise.vue
-->
<template>
  <div class='enterprise-page'>
    <el-breadcrumb class="breadcrumb">
      <el-breadcrumb-item to="/user?activeName=enterprise">用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>{{userDetails.nickname}}</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="elFrom">
      <el-form :model="form" ref="form" label-width="120px">
        <el-form-item label='企业名称：'>
          <span>{{userDetails.company_name}}</span>
        </el-form-item>
        <el-form-item label='营业执照编号：'>
          <span>{{userDetails.cert_number}}</span>
        </el-form-item>
        <el-form-item label='营业执照：'>
          <div class='d-flex'>
            <div class='license d-flex justify-content-center align-items-center'>
              <!-- <p v-if='!form.src'>营业执照图片</p>
          <img :src="form.src" alt="" class='h-100'> -->
              <img :src="userDetails.cert_pic_one_url" v-if="userDetails.cert_pic_one_url">
            </div>
          </div>
        </el-form-item>
        <el-form-item label='管理员：'>
          <span>{{userDetails.nickname}}</span>
        </el-form-item>
        <el-form-item label='手机号：'>
          <span>{{userDetails.phone}}</span>
        </el-form-item>
      </el-form>
      <div class='mt-50px' v-if="$route.query.type=='examine'">
        <el-button type="primary" plain @click="adopt('审核通过')">审核通过</el-button>
        <el-button type="primary" round @click="adopt('审核不通过')">审核不通过</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'userDetails',
  props: {},
  data() {
    return {
      form: {
        enterprise_name: '',
        enterprise_number: '',
        src: '',
        admin_name: '',
        admin_phone: '',
        ver_code: '',
        password: ''
      },
      btnTitle: '获取验证码',
      disabled: false
    }
  },
  computed: {
    userDetails() {
      return this.$route.query.item
    }
  },
  watch: {},
  methods: {
    adopt(type) {
      this.$post('/admin/approve-user', {
        user_id: this.userDetails.user_id,
        status: type
      }).then(res => {
        if (res.data.code == '0000') {
          if (type == '审核通过') {
            this.$message({
              message: '审核通过'
            })
          } else {
            this.$message({
              message: '审核不通过'
            })
          }
          this.$router.push({
            path: '/user',
            query: {
              activeName: 'enterprise'
            }
          })
        } else {
          this.$message({
            message: res.data.msg
          })
        }
      })
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.enterprise-page {
  width: 100%;
  height: 100%;
  .elFrom {
    width: 100%;
    height: 100%;
    background: #ffffff;
    padding: 50px;
    box-sizing: border-box;
    margin-top: 20px;
    img {
      width: 196px;
      height: 116px;
      margin-top: 15px;
    }
  }
}
</style>
