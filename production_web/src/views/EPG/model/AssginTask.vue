<!--
 * @Author: your name
 * @Date: 2021-02-18 17:19:52
 * @LastEditTime: 2021-04-16 11:16:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/model/AssginTask.vue
-->
<template>
  <base-dialog
    :show="show"
    :title="dialogsObj.title"
    :width="dialogsObj.width"
    :center="dialogsObj.center"
    @close="close"
  >
    <div class="assgin-task">
      <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="120px" class="demo-ruleForm">
        <el-form-item label="请选择团队" prop="department_id">
          <el-select v-model="ruleForm.department_id" placeholder="请选择团队">
            <el-option
              v-for="item in teamData"
              :key="item.id"
              :label="item.department_name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" size="small" :loading="Loading" @click="submitForm('ruleForm')">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </base-dialog>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: '',
  components: {},
  props: {
    agginObj: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      show: false,
      Loading: false,
      dialogsObj: {
        width: '400px',
        title: '派发任务',
        center: true
      },
      ruleForm: {
        department_id: ''
      }
    }
  },
  computed: {
    ...mapGetters(['teamData']),
    rules() {
      return {
        department_id: [
          { required: true, message: '请选择团队', trigger: 'change' }
        ]
      }
    }
  },
  watch: {},
  mounted() {},
  created() {
    this.$store.dispatch('user/getTeamData', { page: 1, limit: 10000 })
  },
  methods: {
    open() {
      this.show = true
    },
    close() {
      this.show = false
    },
    assginTeam() {
      this.Loading = true
      const params = {
        broadcast_time_id: this.agginObj.broadcastId,
        department_id: this.ruleForm.department_id,
        date: this.agginObj.date
      }
      this.$post('/epg-task/assign-daily-task', params).then((res) => {
        this.$message({
          showClose: true,
          message: '指派成功',
          type: 'success'
        })
        this.close()
        this.Loading = false
        this.$parent.setTimeList()
      }).catch(res => {
        this.$message({
          showClose: true,
          message: '指派失败',
          type: 'error'
        })
      }).finally(() => {
        this.Loading = false
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.assginTeam()
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang='scss' scoped>
  .assgin-task{
     height: 100px;
     .btns{
         text-align: center;
         ::v-deep.el-form-item__content{
             margin-left: 0 !important;
         }
     }
  }
</style>
