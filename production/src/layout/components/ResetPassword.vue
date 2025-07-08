<!--
 * @Author: your name
 * @Date: 2021-03-26 18:14:17
 * @LastEditTime: 2021-04-15 15:11:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/layout/components/ResetPassword.vue
-->
<template>
  <base-dialog :show="show" title="修改密码" width="400px" class="uploadPasswordDialog" :close-on-click-modal="false" @close="close">
    <el-form ref="ruleForm" :model="form" :inline="true" label-width="100px" label-position="left" :rules="rules">
      <el-form-item prop="oldPassword" label="原密码">
        <el-input v-model="form.oldPassword" placeholder="请输入原密码" size="mini" />
      </el-form-item>
      <el-form-item prop="newPassword" label="新密码">
        <el-input v-model="form.newPassword" placeholder="请输入新密码" size="mini" />
      </el-form-item>
      <el-form-item prop="confirmPassword" label="确认新密码">
        <el-input v-model="form.confirmPassword" placeholder="请输入确认新密码" size="mini" />
      </el-form-item>
      <div class="dialog-footer">
        <base-btn class="confirm-btn" @click="resetPassword(&quot;ruleForm&quot;)">确定</base-btn>
        <base-btn type="info" @click="cancel">取消</base-btn>
      </div>

    </el-form>
  </base-dialog>
</template>

<script>
import sha256 from 'js-sha256'
export default {
  components: {

  },
  props: {
    show: {
      type: Boolean,
      default() {
        return false
      }
    }
  },
  data() {
    return {
      form: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      rules: {
        oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
        newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
        confirmPassword: [{ required: true, message: '请输入确认新密码', trigger: 'blur' }]
      }
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    close() {
      this.$emit('close')
    },
    resetPassword(formName) {
      const params = {
        old_password: sha256(this.form.oldPassword),
        new_password: sha256(this.form.newPassword)
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.form.newPassword !== this.form.confirmPassword) {
            this.$message({
              type: 'warning',
              message: '新密码与确认新密码不一致'
            })
            return
          }
          this.$post('/user/change-password', params).then((res) => {
            this.$message({
              type: 'success',
              message: '提交成功'
            })
            this.$emit('submit')
            this.close()
          }).catch((error) => {
            this.$message.error(error.msg)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    cancel() {
      this.close()
    }
  }
}
</script>

<style scoped lang="scss">
.uploadPasswordDialog{
    .dialog-footer{
        text-align: center;
        .confirm-btn{
            margin-right: 20px;
        }
    }
}
</style>
