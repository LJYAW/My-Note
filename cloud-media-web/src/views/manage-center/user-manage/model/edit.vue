<!--
 * @Author: your names
 * @Date: 2021-08-04 14:42:41
 * @LastEditTime: 2021-08-23 16:39:54
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
        <div>
          <span class="swtichbtn">用户禁用</span>
          <el-switch
            v-model="switchBtn"
            active-color="#13ce66"
          />
        </div>
      </div>
      <el-form-item prop="names" class="item-list">
        <span class="title-top">操作人员</span>
        <el-input v-model="ruleForm.names" placeholder="请输入操作人员" />
      </el-form-item>
      <el-form-item prop="mobile" class="item-list">
        <span class="title-top">绑定手机</span>
        <el-input v-model="ruleForm.mobile" placeholder="请输入绑定手机号码" autocomplete="new-password" />
      </el-form-item>
      <el-form-item prop="" class="item-list">
        <span class="title-top">用户密码</span>
        <el-input v-model="ruleForm.passwd" placeholder="请输入8至16位用户密码" type="password" autocomplete="new-password" />
      </el-form-item>
      <div class="person-top">
        <div class="person-title">功能授权</div>
      </div>
      <el-form-item prop="" class="fun-box">
        <div class="frontfun-box">
          <JurisdictionCheck key="befor_menu" ref="befor_menu" title="前端功能" :check-data="receptionData" :check-list="befor_menu" default-val="视频库" @resetData="resetData" />
          <JurisdictionCheck v-if="videoMenuShow" key="video_menu" ref="video_menu" title="视频工具箱" :check-data="videoToolData" :check-list="video_menu" />
        </div>
        <div class="afterfun-box">
          <JurisdictionCheck key="after_menu" ref="after_menu" title="后台管理系统" :check-data="backstageData" :check-list="after_menu" />
        </div>
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" @click="submitForm('ruleForm')">修改账号</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import mixins from '../js/mini'
export default {
  components: {
  },
  mixins: [mixins],
  props: {
    id: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      switchBtn: false,
      befor_menu: [],
      video_menu: [],
      after_menu: []
    }
  },
  computed: {
  },
  watch: {

  },
  created() {
    if (this.id) {
      this.getInitData()
    }
  },
  mounted() {

  },
  methods: {
    // 修改提交数据
    editPostData() {
      this.setMenuData() // 设置菜单数据
      // 设置禁用启用账号字段
      this.ruleForm.status = this.switchBtn ? -1 : 1
      this.$put(`/user/${this.id}`, this.ruleForm).then(response => {
        const { err, res } = response
        if (err) {
          this.$message({
            message: err.msg,
            type: 'error'
          })
        } else {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
          this.$parent.$parent.$parent.getData()
          this.$emit('close')
        }
      })
    },
    // 回显功能授权整理数据
    setData() {
      this.befor_menu = []
      this.video_menu = []
      // 后台菜单
      this.after_menu = this.ruleForm.after_menu ? JSON.parse(JSON.stringify(this.ruleForm.after_menu)).split(',') : []
      // 前台菜单
      const arr = this.ruleForm.befor_menu ? JSON.parse(JSON.stringify(this.ruleForm.befor_menu)).split(',') : []
      this.videoMenuShow = arr.indexOf('视频工具箱') !== -1
      if (arr.indexOf('视频工具箱') !== -1) {
        this.befor_menu = ['视频库', '视频工具箱']
        this.video_menu = arr.slice(arr.indexOf('视频工具箱') + 1)
      }
    },
    async getInitData() {
      const { err, res } = await this.$get(`/user/${this.id}`)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      // 禁用开关按钮回显数据
      this.switchBtn = res.data.status === -1
      Object.keys(this.ruleForm).forEach(item => {
        this.ruleForm[item] = res.data[item]
      })
      this.ruleForm.passwd = ''
      this.setData()
    }
  }
}
</script>

<style scoped lang="scss">
@import "./index.scss"
</style>
