<!--
 * @Author: your name
 * @Date: 2021-10-09 10:39:02
 * @LastEditTime: 2021-10-11 17:28:16
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/basic/industry_classify/modal/CategoryMediumDialog.vue
-->
<template>
  <div>
    <el-form ref="ruleForm" :inline="true" :rules="rules" :model="form">
      <el-form-item label="中类名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入中类名称" />
      </el-form-item>
      <el-form-item label="所属大类" prop="general_category">
        <el-select v-model="form.general_category" placeholder="请选择所属行业大类" filterable clearable>
          <el-option
            v-for="item in generalCategoryData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <div class="btn-wrap">
      <base-btn @click.native="submit">确定</base-btn>
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
        name: '',
        general_category: ''
      },
      rules: {
        name: [{
          required: true,
          message: '请输入中类名称',
          trigger: 'blur'
        }],
        general_category: [{
          required: true,
          message: '请选择所属行业大类',
          trigger: 'change'
        }]
      },
      generalCategoryData: []
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getGeneralCategoryData()
  },
  mounted() {

  },
  methods: {
    async getGeneralCategoryData() {
      this.loading = true
      const params = {
        type: '行业',
        level: '一级',
        page: 1,
        limit: 1000
      }
      const { err, data } = await this.$get('/ad-sample/ad-type/list', params)
      this.loading = false
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.generalCategoryData = data.list
    },
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
    async confirmData() {
      const params = {
        name: this.form.name,
        type: '行业',
        level: '二级',
        pid: this.form.general_category
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
