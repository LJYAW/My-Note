<!--
 * @Author: your name
 * @Date: 2021-10-22 15:52:48
 * @LastEditTime: 2021-11-03 11:58:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/order-manage/components/deliver.vue
-->
<template>
  <div class="form-deliver">
    <el-form ref="ruleForm" :model="form" :inline="true" :rules="rules">
      <el-form-item label="快递公司" prop="tracking_code">
        <el-select v-model="form.tracking_code" clearable filterable>
          <el-option
            v-for="item in companyData"
            :key="item.code"
            :label="item.name"
            :value="item.code"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="快递单号" prop="tracking_number">
        <el-input v-model="form.tracking_number" />
      </el-form-item>
    </el-form>
    <base-btn class="deliver-btn" @click="commit">立即发货</base-btn>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    productId: {
      type: Number,
      default() {
        return null
      }
    }
  },
  data() {
    return {
      form: {
        tracking_number: null,
        tracking_code: null
      },
      rules: {
        tracking_code: [{
          required: true, message: '请选择快递公司', trigger: 'change'
        }],
        tracking_number: [{
          required: true, message: '请输入快递单号', trigger: 'blur'
        }]
      },
      companyData: []
    }
  },
  computed: {
    tracking_company() {
      const obj = this.companyData.find((item) => item.code === this.form.tracking_code)
      return obj.name || ''
    }
  },
  watch: {

  },
  created() {
    this.getCompanyData()
  },
  mounted() {

  },
  methods: {
    async getCompanyData() {
      const { err, res } = await this.$get('/configs/kuaidi')
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.companyData = res.data.list
    },
    commit() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.submit()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    async submit() {
      const params = {
        status: '运输中',
        tracking: {
          tracking_company: this.tracking_company,
          ...this.form
        }
      }
      const { err, res } = await this.$put(`/admin/goods-order/${this.productId}/status`, params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.$emit('close')
    }
  }
}
</script>

<style scoped lang="scss">
.form-deliver {
  .el-select, .el-input {
    width : 370px;
  }
  .deliver-btn {
    width : 100%;
  }
}

</style>
