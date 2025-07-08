<!--
 * @Author: your name
 * @Date: 2021-11-01 15:52:13
 * @LastEditTime: 2021-11-04 16:20:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/user-manage/components/Add.vue
-->
<template>
  <div class="add-person">
    <el-form
      ref="ruleForm"
      :model="ruleForm"
      :rules="rules"
      label-width="90px"
      label-position="left"
      class="demo-ruleForm"
    >
      <el-form-item label="手机号" prop="username">
        <el-input v-model.trim="ruleForm.username" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item label="姓名" prop="nickname">
        <el-input v-model.trim="ruleForm.nickname" placeholder="请输入姓名" />
      </el-form-item>
      <el-form-item label="密码" :prop="!userData.user_id?'password':''">
        <el-input v-model.trim="ruleForm.password" placeholder="请输入密码" autocomplete="new-password" type="password" />
      </el-form-item>
      <el-form-item label="业务" prop="work">
        <el-checkbox-group v-model="ruleForm.work">
          <el-checkbox
            v-for="item in bussinessList"
            :key="item"
            :label="item"
            :name="item"
          >{{ item }}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
        <el-button @click="close">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import rules from '../js/verification'
import sha256 from 'js-sha256'
import { mapGetters } from 'vuex'
export default {
  components: {

  },
  props: {
    userData: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      rules: rules,
      ruleForm: {
        username: '',
        nickname: '',
        password: '',
        work: []
      }
    }
  },
  computed: {
    ...mapGetters(['adminMenus']),
    bussinessList() {
      return this.adminMenus.split(',')
    }
  },
  watch: {

  },
  created() {
    if (this.userData.user_id) {
      this.ruleForm = JSON.parse(JSON.stringify(this.userData))
      this.ruleForm.work = this.ruleForm.work.split(',')
    }
  },
  mounted() {

  },
  methods: {
    close() {
      this.$emit('close')
    },
    resetForm() {
      this.$refs['ruleForm'].resetFields()
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const params = JSON.parse(JSON.stringify(this.ruleForm))
          params.work = params.work.join(',')
          if (params.password) {
            params.password = sha256(params.password)
          } else {
            delete params['password']
          }
          this.userAdd(params)
        } else {
          return false
        }
      })
    },
    async userAdd(params) {
      const { err, res } = await this.$post('/admin/admin-user/user-add', params)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.close()
        const msg = this.userData.user_id ? '编辑成功' : '新建成功'
        this.$message({
          message: msg,
          type: 'success'
        })
      }
    }
  }
}
</script>

<style scoped lang="scss">

</style>
