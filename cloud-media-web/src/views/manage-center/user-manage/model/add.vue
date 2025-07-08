<!--
 * @Author: your names
 * @Date: 2021-08-04 14:42:41
 * @LastEditTime: 2021-08-25 16:25:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/user-manage/model/add.vue
-->
<template>
  <div class="add-person">
    <div class="arrow-img">
      <svg-icon icon-class="person" class="icon-person" />
    </div>
    <el-form ref="ruleForm" :rules="rules" label-width="0" class="demo-ruleForm" :model="ruleForm" :inline="true">
      <div class="person-top">
        <div class="person-title">成员信息</div>
        <div class="num">子账号数量  {{ companyList.t_subuser_count || 0 }} /  {{ companyList.subuser_count || 0 }}</div>
      </div>
      <el-form-item prop="names" class="item-list">
        <span class="title-top">操作人员</span>
        <el-input v-model="ruleForm.names" placeholder="请输入操作人员" />
      </el-form-item>
      <el-form-item prop="mobile" class="item-list">
        <span class="title-top">绑定手机</span>
        <el-input v-model="ruleForm.mobile" placeholder="请输入绑定手机号码" autocomplete="new-password" />
      </el-form-item>
      <el-form-item prop="passwd" class="item-list">
        <span class="title-top">用户密码</span>
        <el-input v-model="ruleForm.passwd" placeholder="请输入8至16位用户密码" type="password" autocomplete="new-password" />
      </el-form-item>
      <div class="person-top">
        <div class="person-title">功能授权</div>
      </div>
      <el-form-item prop="" class="fun-box">
        <div class="frontfun-box">
          <JurisdictionCheck key="befor_menu" ref="befor_menu" title="前端功能" :check-data="receptionData" default-val="视频库" @resetData="resetData" />
          <JurisdictionCheck v-if="videoMenuShow" key="video_menu" ref="video_menu" title="视频工具箱" :check-data="videoToolData" />
        </div>
        <div class="afterfun-box">
          <JurisdictionCheck key="after_menu" ref="after_menu" title="后台管理系统" :check-data="backstageData" />
        </div>
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" @click="submitForm('ruleForm')">创建账号</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import mixins from '../js/mini'
import { mapGetters } from 'vuex'

export default {
  components: {
  },
  mixins: [mixins],
  props: {
  },
  data() {
    return {

    }
  },
  computed: {
    ...mapGetters(['companyList'])
  },
  watch: {

  },
  created() {
    this.getCompanyData()
  },
  mounted() {

  },
  methods: {
    getCompanyData() {
      this.$store.dispatch('directoryData/getCompanyData')
    }
  }
}
</script>

<style scoped lang="scss">
@import "./index.scss"
</style>
