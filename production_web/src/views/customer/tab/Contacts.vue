<!--
 * @Author:zll
 * @Date: 2021-01-12 00:05:43
 * @LastEditTime: 2021-01-12 11:38:49
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/customer/tab/basic.vue
-->
<template>
  <div class="contacts">
    <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="100px" class="demo-ruleForm">
      <div class="tab-box">
        <div class="item-list">
          <div class="title-top">
            <div class="title-message">商务</div>
          </div>
          <el-form-item label="商务姓名" prop="businessName">
            <el-input v-model="ruleForm.businessName" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
          </el-form-item>
          <el-form-item label="联系电话" prop="businessTele">
            <el-input v-model="ruleForm.businessTele" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
          </el-form-item>
          <el-form-item label="微信" prop="businessWeixin">
            <el-input v-model="ruleForm.businessWeixin" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
          </el-form-item>
          <el-form-item label="QQ" prop="businessQQ">
            <el-input v-model="ruleForm.businessQQ" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
          </el-form-item>
          <el-form-item label="邮箱" prop="businessEmail">
            <el-input v-model="ruleForm.businessEmail" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
          </el-form-item>
        </div>
        <div class="item-list">
          <div class="title-top">
            <div class="title-message">对接人</div>
          </div>
          <el-form-item label="对接人姓名" prop="persoName">
            <el-input v-model="ruleForm.persoName" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
          </el-form-item>
          <el-form-item label="联系电话" prop="persoTele">
            <el-input v-model="ruleForm.persoTele" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
          </el-form-item>
          <el-form-item label="微信" prop="persoWeixin">
            <el-input v-model="ruleForm.persoWeixin" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
          </el-form-item>
          <el-form-item label="QQ" prop="persoQQ">
            <el-input v-model="ruleForm.persoQQ" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
          </el-form-item>
          <el-form-item label="邮箱" prop="persoEmail">
            <el-input v-model="ruleForm.persoEmail" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
          </el-form-item>
        </div>
      </div>

      <el-form-item class="btns">
        <el-button v-if="type!='查看列表'" type="primary" @click="determine('ruleForm')">确定</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import rules from '../js/verification.js'
export default {
  components: {},
  data() {
    return {
      ruleForm: {
        businessName: '11',
        businessTele: '',
        businessWeixin: '',
        businessQQ: '',
        businessEmail: '',
        persoName: '',
        persoTele: '',
        persoWeixin: '',
        persoQQ: '',
        persoEmail: ''
      },
      rules: rules,
      options_grade: [
        {
          label: '1',
          value: '1'
        }
      ]
    }
  },
  computed: {
    type() {
      return this.$route.query.type
    }
  },
  watch: {},
  created() {
  },
  mounted() {

  },
  methods: {
    determine(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$router.push({
            path: `/customer`,
            query: {}
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang='scss' scoped>
.contacts{
  .demo-ruleForm{
    margin-top: 20px;
    ::v-deep .el-input.is-disabled .el-input__inner{
      color: #333333;
      font-weight: 700;
      font-size: 16px;
    }
    .backtraf{
      ::v-deep input{
        background: transparent !important;
      }
    }
    .btns{
       width: 100%;
       margin-top: 50px;
       text-align: center;
      ::v-deep .el-form-item__content{
        margin-left: 0 !important;
        button{
          width: 300px;
        }
      }
    }
    .tab-box{
      display: flex;
      .item-list{
        box-shadow: 0 2px 15px #ddd, 0 0 1px #ddd, 0 0 1px #ddd;
        margin-left: 30px;
        padding: 18px;
        .title-top{
          .title-message{
              position: relative;
              font-size: 14px;
              color: #409EFF;
              font-weight: 700;
              left: 30px;
              margin-bottom: 10px;
              &::after{
                position: absolute;
                content: '';
                top: 0px;
                left: -10px;
                width: 3px;
                height: 16px;
                background: #409EFF;
              }
          }
        }
      }
    }
    ::v-deep input{
      width: 360px;
      border: none;
      background: #f5f5f5;
      font-weight: 700;
      font-size: 16px;
    }
    .item-times{
      width: 360px;
      display: flex;
      span{
        padding: 0 10px;
      }
      ::v-deep .el-date-editor{
        width: 180px;
        input{
          width: 100%;
        }
      }
    }
  }
}
</style>
