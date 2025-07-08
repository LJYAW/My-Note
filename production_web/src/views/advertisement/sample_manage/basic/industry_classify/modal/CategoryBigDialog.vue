<!--
 * @Author: your name
 * @Date: 2021-10-09 10:34:32
 * @LastEditTime: 2021-10-11 17:27:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/basic/industry_classify/modal/CategoryBigDialog.vue
-->
<template>
  <div>
    <el-form ref="ruleForm" :inline="true" :rules="rules" :model="form">
      <el-form-item label="大类名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入大类名称" />
      </el-form-item>
    </el-form>
    <div class="btn-wrap">
      <base-btn @click="submit">确定</base-btn>
      <base-btn type="info" @click.native="close">取消</base-btn>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      form: {
        name: ''
      },
      rules: {
        name: [{
          required: true,
          message: '请输入大类名称',
          trigger: 'blur'
        }]
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
      this.resetData('ruleForm')
      this.$emit('close')
    },
    resetData(formName) {
      this.$refs[formName].resetFields()
    },
    submit() {
      this.$refs['ruleForm'].validate(valid => {
        if (valid) {
          this.confirmData()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    confirmData() {
      const params = {
        name: this.form.name,
        type: '行业',
        level: '一级'
      }
      this.$post('/ad-sample/ad-type', params).then((res) => {
        this.close()
        this.$emit('update')
      }).catch((err) => {
        this.$message.error(err.msg)
      })
    }
  }
}
</script>

<style scoped lang="scss">
.el-dialog{
  display: flex;
  .el-input{
      width: 400px;
  }
  .btn-wrap{
      margin-top: 20px;
      text-align: center;
      button{
          margin: 0 20px;
      }
  }
}
</style>
