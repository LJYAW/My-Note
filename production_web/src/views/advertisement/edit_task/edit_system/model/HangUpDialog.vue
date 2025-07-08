<!--
 * @Author: your name
 * @Date: 2021-02-24 11:33:20
 * @LastEditTime: 2021-03-19 17:51:16
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/model/HangUpDialog.vue
-->
<template>
  <base-dialog
    :show="show"
    title="任务挂起说明"
    @close="close"
  >
    <el-form :inline="true" label-position="left">
      <div v-for="item in formData" :key="item.name">
        <el-checkbox v-model="item.isChecked" :label="item.name" />
        <el-form-item label-width="90px">
          <el-input v-model="item.desc" type="textarea" />
        </el-form-item>
      </div>
    </el-form>
    <div class="dialog-footer">
      <base-btn size="mini" @click="submit">确定</base-btn>
      <base-btn size="mini" type="info" @click="cancel">取消</base-btn>
    </div>
  </base-dialog>
</template>

<script>
export default {
  components: {

  },
  props: {
    id: {
      type: Number,
      default() {
        return null
      }
    }
  },
  data() {
    return {
      show: false,
      formData: [{
        name: '播出不符',
        desc: '',
        isChecked: false
      }, {
        name: '音画不同步',
        desc: '',
        isChecked: false
      }, {
        name: '视频源花屏',
        desc: '',
        isChecked: false
      }, {
        name: '跳帧',
        desc: '',
        isChecked: false
      }, {
        name: '其他',
        desc: '',
        isChecked: false
      }]
      // checkForm: {
      //   broadcast: false,
      //   async: false,
      //   blurred_screen: false,
      //   frame_skip: false,
      //   other_reason: false
      // },
      // form: {
      //   broadcast: '',
      //   async: '',
      //   blurred_screen: '',
      //   frame_skip: '',
      //   other_reason: ''
      // },
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
    open() {
      this.show = true
    },
    close() {
      this.show = false
    },
    cancel() {
      this.close()
    },
    setDesc() {
      const descArr = []
      this.formData.forEach((item) => {
        if (item.isChecked) {
          descArr.push({
            name: item.name,
            desc: item.desc
          })
        }
      })
      return descArr
    },
    checkData() {
      const arr = this.formData.filter((item) => {
        return item.isChecked
      })
      if (!arr.length) {
        return false
      }
      return true
    },
    submit() {
      if (!this.checkData()) {
        this.$message('请选择挂起原因')
        return
      }
      const params = {
        task_id: this.id,
        status: '挂起中',
        desc: this.setDesc()
      }
      this.$post('/production/setStatus', params).then((res) => {
        this.$message({
          type: 'success',
          message: '挂起成功'
        })
        this.close()
        this.$router.push({
          path: '/advertisement/edit-task'
        })
      }).catch((error) => {
        this.$message(error.msg)
      })
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep .el-form{
    .el-form-item__label{
        line-height: 0;
    }
    .el-checkbox{
        margin-right: 10px;
        width: 100px;
    }
    .el-form-item{
        width: 72%;
        .el-form-item__content{
            width: 95%;
            textarea{
                resize: none;
                min-height: 80px!important;
                max-height: 100px;
            }
        }
    }
}
.dialog-footer{
    text-align: center;
}
</style>
