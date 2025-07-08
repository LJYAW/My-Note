<!--
 * @Author: your name
 * @Date: 2021-01-14 14:19:38
 * @LastEditTime: 2021-04-23 11:55:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/task_mgt/models/assign_task.vue
-->
<template>
  <base-dialog
    :show="show"
    :title="dialogsObj.title"
    :width="dialogsObj.width"
    :center="dialogsObj.center"
    @close="close"
  >
    <div>
      <div class="assign-task">
        <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="120px" class="demo-ruleForm">
          <el-form-item v-if="roles.some(item => item === 'superman')" label="请选择工作组" prop="department_id">
            <el-select v-model="ruleForm.department_id" placeholder="请选择工作组">
              <el-option
                v-for="item in tempteamData"
                :key="item.id"
                :label="item.department_name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="请选择执行人" prop="executor_id">
            <el-select v-model="ruleForm.executor_id" placeholder="请选择执行人">
              <el-option
                v-for="item in person_options"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item class="btns">
            <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </base-dialog>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'AssignTask',
  components: {},
  props: {
    taskArr: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      show: false,
      person_options: [
      ],
      tempteamData: [],
      dialogsObj: {
        width: '400px',
        title: '指派执行人',
        center: true
      },

      ruleForm: {
        executor_id: '',
        department_id: ''
      }
    }
  },
  computed: {
    ...mapGetters(['teamData', 'roles', 'userInfo']),
    tempTaskArr() {
      const arr = []
      this.taskArr.forEach(item => {
        arr.push(item.task_id)
      })
      return arr
    },
    rules() {
      return {
        executor_id: [
          { required: !this.roles.some(item => item === 'superman'), message: '请选择执行人', trigger: 'change' }
        ],
        department_id: [
          { required: true, message: '请选择工作组', trigger: 'change' }
        ]
      }
    }
  },
  watch: {
    'ruleForm.department_id'(id) {
      if (id) {
        this.getUserList()
        this.ruleForm.executor_id = ''
      }
    },
    teamData(val) {
      if (val.length !== 0) {
        if (this.roles.some(item => item === 'superman')) {
          this.tempteamData = JSON.parse(JSON.stringify(val))
        } else {
          this.tempteamData = []
          this.ruleForm.department_id = this.userInfo.department_id
        }
      }
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {
    getUserList() {
      const params = {
        department_id: this.ruleForm.department_id,
        keyword: ''
      }
      this.$get('/user/team-user-list', params).then(res => {
        const { data } = res
        this.person_options = data
      }).catch(res => {
        console.log(res)
      })
    },
    open() {
      this.show = true
    },
    close() {
      this.show = false
      this.ruleForm.department_id = ''
      this.ruleForm.executor_id = ''
      this.$emit('clearSelection')
    },
    submitForm(formName) {
      const params = {
        executor_id: this.ruleForm.executor_id,
        department_id: this.ruleForm.department_id,
        task_ids: this.tempTaskArr
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$post('/production/assign-task', params).then((res) => {
            this.$message({
              showClose: true,
              message: '指派成功',
              type: 'success'
            })
            this.close()
            this.$parent.getList()
          }).catch(res => {
            this.$message({
              showClose: true,
              message: '指派失败',
              type: 'error'
            })
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.assign-task{
  height: 229px;
  .demo-ruleForm{
    ::v-deep.el-form-item__content{
      margin-left: 0 !important;
    }
    .btns{
      margin-top: 50px;
      text-align: center;
      button{
        height:36px;
        line-height: 10px;
      }
    }
  }
}
</style>
