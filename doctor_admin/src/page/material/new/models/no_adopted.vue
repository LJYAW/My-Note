<!--
 * @Author: zll
 * @Date: 2020-12-03 15:33:40
 * @LastEditTime: 2020-12-03 16:57:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /weijian_cms_web/src/page/material/new/models/adopt.vue
-->
<template>
  <div class="noadopt_wrap">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="" prop="remarks">
        <el-input type="textarea" v-model="ruleForm.remarks" placeholder="请输入审核不通过原因"></el-input>
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" @click="submitForm('ruleForm')" round size="medium">确定</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: '',
  props: {
    layerid: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      ruleForm: {
        remarks: ''
      },
      rules: {
        remarks: [{ required: true, message: '请填写审核不通过原因', trigger: 'blur' }]
      }
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    submitForm(ruleForm) {
      this.$refs[ruleForm].validate(valid => {
        if (valid) {
          this.$layer.close(this.layerid)
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
.noadopt_wrap {
  width: 100%;
  padding: 30px;
  /deep/ .el-textarea__inner {
    height: 300px;
  }
  /deep/ .el-form-item__label {
    color: #333333;
  }
  /deep/ .el-form-item__content {
    margin: 0 !important;
    text-align: center;
  }
}
</style>
