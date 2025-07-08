<template>
  <div class="baijiahaom-wrap">
    <model id="baijiahaoM"
           ref="baijiahaoM"
           class="model-mini"
           @close="close">

      <div slot="body">
        <div class="px-24px">
          <el-form :model="from"
                   ref="ruleForm"
                   :rules="rules"
                   label-width="100px"
                   label-position="top"
                   class="demo-ruleForm">
            <el-form-item label="app_id:"
                          :inline="true"
                          prop="app_id">
              <el-input v-model="from.app_id"></el-input>
            </el-form-item>
            <el-form-item label="app_token:"
                          prop="app_token">
              <el-input v-model="from.app_token"></el-input>
            </el-form-item>
            <el-form-item label="百家号昵称:"
                          prop="nickname">
              <el-input v-model="from.nickname"></el-input>
            </el-form-item>
          </el-form>

          <div class="mb-45px mt-40px">
            <el-button type="primary"
                       v-if="!parentObj.app_id"
                       class="w-100"
                       @click="addAccount">添加账号</el-button>
            <el-button v-else
                       type="primary"
                       class="w-100"
                       @click="addAccount">修改账号</el-button>
          </div>

        </div>

      </div>
    </model>
  </div>
</template>

<script>
export default {
  props: {
    parentObj: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      from: {
        nickname: '',
        app_id: '',
        app_token: ''
      },
      rules: {
        app_id: [{ required: true, message: '请输入api_id', trigger: 'blur' }],
        app_token: [{ required: true, message: '请输入api_token', trigger: 'blur' }],
        nickname: [{ required: true, message: '请输入百家号昵称', trigger: 'blur' }]
      }
    }
  },
  computed: {},
  watch: {},
  methods: {
    close() {
      this.$emit('Modalclose')
    },
    forParentObj() {
      this.from = JSON.parse(JSON.stringify(this.parentObj))
    },
    addAccount() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          this.postAccount()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    postAccount() {
      var data = {
        nickname: this.from.nickname,
        app_id: this.from.app_id,
        app_token: this.from.app_token
      }

      this.$post('/user/bind-baijiahao', data).then(res => {
        if (res.data.code == '0000') {
          this.$alertMsg('添加成功')
        } else {
          this.$alertMsg(res.data.msg)
        }
        this.$parent.getBindList()
      })
      this.$refs.baijiahaoM.close()
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style lang="scss">
.baijiahaom-wrap {
  .modal-wrap {
    max-width: 500px !important;
    min-width: 500px !important;
  }
  .el-form-item__label {
    height: 35px;
  }
}
</style>
