<!--
 * @Author: your name
 * @Date: 2020-12-16 15:04:24
 * @LastEditTime: 2021-02-09 16:28:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/login_modal/personal.vue
-->
<template>
  <div class='personal-page'>
    <el-breadcrumb class="breadcrumb">
      <el-breadcrumb-item to="/user?activeName=persopn">用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>{{userDetails.nickname}}</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="elFrom">
      <el-form :model="form" ref="form" label-width="160px">
        <el-form-item label='医生姓名：'>
          <span>{{userDetails.nickname}}</span>
        </el-form-item>
        <el-form-item label='医生头像：'>
          <div class='d-flex'>
            <img :src="userDetails.avatar_url" alt="" v-if="userDetails.avatar_url">
          </div>
        </el-form-item>
        <el-form-item label='医生简介：'>
          <div>
            <audio controls :src="userDetails.audio_url"></audio>
            <div>{{userDetails.audio_content}}</div>
          </div>
        </el-form-item>
        <el-form-item label='执业医院：'>
          <span>{{userDetails.legal_name}}</span>
        </el-form-item>
        <el-form-item label='线下科室名称：'>
          <span>{{userDetails.department}}</span>
        </el-form-item>
        <el-form-item label='医生职称：'>
          <span>{{userDetails.job_title}}</span>
        </el-form-item>
        <el-form-item label='执业证书编号：'>
          <span>{{userDetails.cert_number}}</span>
        </el-form-item>
        <el-form-item label='执业证书照片：'>
          <div>
            <img :src="userDetails.cert_pic_one_url" alt="" v-if="userDetails.cert_pic_one_url">
            <img :src="userDetails.cert_pic_two_url" alt="" v-if="userDetails.cert_pic_two_url">
            <br />
            <img :src="userDetails.cert_pic_three_url" alt="" v-if="userDetails.cert_pic_three_url">
            <img :src="userDetails.cert_pic_four_url" alt="" v-if="userDetails.cert_pic_four_url">
          </div>
        </el-form-item>
        <el-form-item label='资质照片:【职称证】' class='qualifications'>
          <img :src="userDetails.job_title_pic_url" alt="" v-if="userDetails.job_title_pic_url">
        </el-form-item>
        <el-form-item label='医生简介：'>
          <span>{{userDetails.doctor_intro}}</span>
        </el-form-item>
        <el-form-item label='擅长信息描述：'>
          <span>{{userDetails.good_at_information}}</span>
        </el-form-item>
        <el-form-item label='手机号：'>
          <span>{{userDetails.phone}}</span>
        </el-form-item>
        <el-form-item label='所属企业:'>
          <span>{{userDetails.company_name}}</span>
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
  props: {},
  data() {
    return {
      form: {}
    }
  },
  computed: {
    userDetails() {
      return JSON.parse(this.$route.query.item)
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
              activeName: 'persopn'
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
  created() {
    console.log(this.userDetails)
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.personal-page {
  width: 100%;
  height: 100%;
  .elFrom {
    width: 100%;
    height: 100%;
    background: #ffffff;
    padding: 50px;
    box-sizing: border-box;
    margin-top: 20px;
    overflow: auto;
    img {
      width: 196px;
      height: 116px;
      margin-top: 15px;
      // object-fit: cover;
      margin-right: 10px;
    }
  }
}
</style>
