<!--
 * @Author: your name
 * @Date: 2021-08-06 11:20:26
 * @LastEditTime: 2021-08-24 16:08:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/directory-manage/model/addCatalogue.vue
-->
<template>
  <div class="add-catalogue">
    <div class="svg-box">
      <svg-icon icon-class="catalogue" class="svg-icon" />
    </div>
    <el-form ref="form" :model="form" class="form" :rules="rules">
      <el-form-item prop="names">
        <div class="label">项目名称</div>
        <el-input v-model="form.names" placeholder="请输入项目名称" :maxlength="10" />
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" @click="add('form')">创建</el-button>
      </el-form-item>
    </el-form>
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
        names: ''
      },
      rules: {
        names: [{ required: true, message: '请输入项目名称', trigger: 'blur' }]
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
    postData() {
      this.$post('/dirs/', this.form).then(response => {
        const { err, res } = response
        if (err) {
          this.$message({
            message: err.msg,
            type: 'error'
          })
        } else {
          this.$message({
            message: '创建成功',
            type: 'success'
          })
          this.$bus.$emit('resetData')
          this.$emit('close')
        }
      })
    },
    add(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.postData()
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.add-catalogue {

  .svg-box {
    text-align: center;

    .svg-icon {
      width: 88px;
      height: 60px;
    }
  }

  .form {

    .el-form-item {
      margin: 0;
      margin-top: 10px;
    }

    ::v-deep .el-input__inner {
      width: 100%;
      height: 30px;
      color: #c1c1c1;
      font-weight: bolder;
      font-size: 12px;
    }

    .label {
      font-size: 12px;
      font-weight: 600;
      color: #404040;
      height: 32px;
      line-height: 32px;
    }

    .btns {
      width: 100%;
      margin-top: 20px;

      button {
        width: 100%;
        height: 30px;
        background: #5675e8;
        border-radius: 4px;
        padding: 0;
        font-size: 12px;
      }
    }
  }
}
</style>
