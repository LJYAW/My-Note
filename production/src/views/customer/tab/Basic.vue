<!--
 * @Author:zll
 * @Date: 2021-01-12 00:05:43
 * @LastEditTime: 2021-01-12 11:38:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/customer/tab/basic.vue
-->
<template>
  <div class="basic">
    <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="100px" class="demo-ruleForm">
      <el-form-item label="客户全称" prop="name">
        <el-input v-model="ruleForm.name" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
      </el-form-item>
      <el-form-item label="客户简称" prop="abbreviation">
        <el-input v-model="ruleForm.abbreviation" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
      </el-form-item>
      <el-form-item label="客户等级" prop="grade">
        <el-select v-model="ruleForm.grade" placeholder="请选择客户等级" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}">
          <el-option
            v-for="item in options_grade"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="合同编号" prop="num">
        <el-input v-model="ruleForm.num" :disabled="type=='查看列表'" :class="{backtraf:type=='查看列表'}" />
      </el-form-item>
      <el-form-item label="合同期限" prop="time">
        <div v-if="type!='查看列表'" class="item-times">
          <el-date-picker v-model="ruleForm.start" type="date" placeholder="选择开始日期" />
          <span>至</span>
          <el-date-picker v-model="ruleForm.end" type="date" placeholder="选择结束日期" />
        </div>
        <div v-else class="times">
          <div>{{ ruleForm.start }}</div>
          <span>至</span>
          <div>{{ ruleForm.end }}</div>
        </div>
      </el-form-item>
      <el-form-item class="btns">
        <el-button v-if="type!='查看列表'" type="primary" @click="next('ruleForm')">下一步</el-button>
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
        name: '',
        abbreviation: 'hjgfjgerjgjh',
        grade: '',
        num: '',
        start: '2020-09-21',
        end: '2020-10-21'
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
    next(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$emit('toggle', '联系人')
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
.basic{
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
       margin-top: 50px;
       .el-form-item__content{
          button{
            width: 300px;
          }
      }
    }
    .times{
      display: flex;
      font-size: 16px;
      color: #333333;
      font-weight: 500;
      div{
        min-width: 100px;
        text-align: center;
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
