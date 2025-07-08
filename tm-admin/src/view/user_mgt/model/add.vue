<!--
 * @Author: your name
 * @Date: 2021-01-14 14:19:38
 * @LastEditTime: 2021-06-04 17:51:38
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/task_mgt/models/assign_task.vue
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
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="ruleForm.mobile" placeholder="请输入手机号" />
      </el-form-item>

      <el-form-item label="姓名" prop="names">
        <el-input v-model="ruleForm.names" placeholder="请输入姓名" />
      </el-form-item>

      <el-form-item label="密码" :prop="!modalData.id?'passwd':''">
        <el-input v-model="ruleForm.passwd" placeholder="请输入密码" autocomplete="new-password" type="password" />
      </el-form-item>

      <el-form-item label="业务" prop="Limits">
        <el-checkbox-group v-model="ruleForm.Limits">
          <el-checkbox
            v-for="(item,index) in limitData"
            :key="index"
            :label="item.Id"
          >{{ item.Names }}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
        <el-button @click="close('ruleForm')">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import rules from '../js/verification'
import { mapGetters } from 'vuex'
// import sha256 from 'js-sha256'
// import business from '../js/business'
export default {
  name: 'AssignTask',
  components: {},
  props: {
    show: {
      type: Boolean,
      default: false
    },
    modalData: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      dialogsObj: {
        width: '700px',
        title: '新增用户',
        center: true
      },
      rules: rules,
      ruleForm: {
        mobile: '',
        names: '',
        passwd: '',
        Limits: []
      },
      businessList: []
    }
  },
  computed: {
    ...mapGetters([
      'limitData'
    ])
  },
  watch: {
  },
  created() {
    this.modalData.id && this.setUpdateData(this.modalData)
    // this.getLimit()
  },
  mounted() {
  },
  methods: {
    close() {
      this.$emit('close')
    },
    // 回显编辑用户数据
    setUpdateData(val) {
      this.dialogsObj.title = '编辑用户'
      this.ruleForm.id = val.id
      this.ruleForm.names = val.names
      this.ruleForm.mobile = val.mobile
      this.ruleForm.Limits = this.getLimit(val.Limits)
    },
    getLimit(limits) {
      let arr = []
      if (!limits) return arr
      if (limits === '*') {
        this.limitData.forEach(item => {
          arr.push(item.Id)
        })
      } else {
        arr = limits.split(',').map(Number)
      }
      return arr
    },
    // 表单验证
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const params = JSON.parse(JSON.stringify(this.ruleForm))
          params.Limits = params.Limits.join(',')
          !params.passwd && delete params['passwd']
          this.modalData.id ? this.userUpdate(params) : this.userAdd(params)
        } else {
          return false
        }
      })
    },
    async userAdd(params) {
      params.status = 1
      const { data, err } = await this.$post('/user', params)
      if (err) {
        this.$message.error(err.msg)
        return
      } else {
        this.$message({
          type: 'success',
          message: '新建用户成功'
        })
        this.$emit('updateData')
        this.close()
      }
    },
    async userUpdate(params) {
      params.status = this.modalData.status
      const { data, err } = await this.$put(`/user/${this.modalData.id}`, params)
      if (err) {
        this.$message.error(err.msg)
        return
      } else {
        this.$message({
          type: 'success',
          message: '编辑用户成功'
        })
        this.$emit('updateData')
        this.close()
      }
    }
    // async getLimit() {
    //   const { data, err } = await this.$get('/limits')
    //   if (err) {
    //     this.$message.error(err.msg)
    //     return
    //   }
    //   this.businessList = data.data
    // }

  }
}
</script>

<style lang="scss" scoped>
.add-person{
  height: 488px;
  .demo-ruleForm{
    .btns{
      margin-top: 50px;
      text-align: center;
      button{
        height:36px;
        line-height: 10px;
      }
    }
    .newBtn{
      margin-left: 20px;
    }
  }
}
</style>
