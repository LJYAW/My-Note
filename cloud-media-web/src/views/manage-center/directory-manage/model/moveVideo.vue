<!--
 * @Author: your name
 * @Date: 2021-08-06 11:20:26
 * @LastEditTime: 2021-08-10 11:31:31
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
      <el-form-item prop="catalogue">
        <div class="label">移动到</div>
        <el-select v-model="form.catalogue" clearable placeholder="请选择目录" size="small">
          <el-option
            v-for="item in directoryData"
            :key="item.id"
            :label="item.names"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" @click="sbumitData('form')">确定</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  components: {

  },
  naem: 'moveVideo',
  props: {
    id: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      form: {
        catalogue: ''
      },
      rules: {
        catalogue: [{ required: true, message: '请选择目录', trigger: 'change' }]
      }
    }
  },
  computed: {
    ...mapGetters(['directoryData'])
  },
  watch: {

  },
  created() {
    this.getDirectoryData()
  },
  mounted() {

  },
  methods: {
    getDirectoryData() {
      this.$store.dispatch('directoryData/getDierctoryData')
    },
    poatData() {
      const params = {
        old_id: this.id,
        new_id: this.form.catalogue
      }
      this.$post('/dirs/resetdirs', params).then(response => {
        const { err, res } = response
        if (err) {
          this.$message({
            message: err.msg,
            type: 'error'
          })
        } else {
          this.$message({
            message: '移动成功',
            type: 'success'
          })
          this.$bus.$emit('resetData')
          this.$emit('close')
        }
      })
    },
    sbumitData(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.poatData()
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
    margin-top: 30px;

    .el-form-item {
      margin: 0;
      margin-top: 10px;
    }

    .el-select {
      width: 100%;
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
