<!--
 * @Author: your name
 * @Date: 2021-08-05 17:03:51
 * @LastEditTime: 2021-08-31 15:28:13
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/directory-manage/model/addPerson.vue
-->
<template>
  <div class="add-person">
    <div class="svg-box">
      <svg-icon icon-class="catalogue" class="svg-icon" />
    </div>
    <el-form ref="form" :model="form" class="form" :rules="rules">
      <el-form-item prop="name">
        <div class="label">成员名称</div>
        <el-select v-model="form.name" filterable placeholder="请选择成员">
          <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.names"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item prop="jurisdiction">
        <div class="label">权限选择</div>
        <el-select v-model="form.jurisdiction" clearable placeholder="请选择权限">
          <el-option
            v-for="item in roleData"
            :key="item.id"
            :label="item.names"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" @click="add('form')">添加</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  components: {

  },
  props: {
    id: {
      type: Number,
      default: 0
    },
    roleData: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      rules: {
        name: [{ required: true, message: '请输入成员名称', trigger: 'blur' }],
        jurisdiction: [{ required: true, message: '经选择权限', trigger: 'change' }]
      },
      form: {
        name: '',
        jurisdiction: ''
      }
    }
  },
  computed: {
    ...mapGetters(['userList'])
  },
  watch: {

  },
  created() {
    this.initData()
  },
  mounted() {

  },
  methods: {
    initData() {
      const params = {
        limit: 100,
        page: 1,
        query: '',
        sortby: 'id',
        order: 'desc'
      }
      this.$store.dispatch('directoryData/getUserData', params)
    },
    add(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.postData()
        } else {
          return false
        }
      })
    },
    postData() {
      const parmas = {
        dir_id: this.id,
        user_group_id: this.form.jurisdiction,
        user_id: this.form.name
      }
      this.$post('/userdirs/', parmas).then(response => {
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
          this.$parent.$parent.$parent.initData()
          this.$bus.$emit('resetData')
          this.$emit('close')
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.add-person {

  .svg-box {
    text-align: center;

    .svg-icon {
      width: 88px;
      height: 60px;
    }
  }

  .form {
    margin-top: 20px;

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

    ::v-deep .el-input__inner {
      width: 100%;
      height: 30px;
      color: #c1c1c1;
      font-weight: bolder;
      font-size: 12px;
    }

    .el-autocomplete {
      width: 100%;
    }

    .btns {
      width: 100%;
      margin-top: 30px;

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
