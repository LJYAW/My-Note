<!--
 * @Author: your name
 * @Date: 2021-01-14 14:19:38
 * @LastEditTime: 2021-07-27 16:40:31
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
      <div class="add-person">
        <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rules"
          label-width="90px"
          label-position="left"
          class="demo-ruleForm"
        >
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="ruleForm.phone" placeholder="请输入手机号" />
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入姓名" />
          </el-form-item>
          <el-form-item label="团队名称" prop="team_id">
            <el-select v-model="ruleForm.team_id" placeholder="请选择团队名称" clearable filterable>
              <el-option
                v-for="item in teamData"
                :key="item.id"
                :label="item.department_name"
                :value="item.id"
              />
            </el-select>
            <base-btn class="newBtn" @click.native="showAddTeam=true">新增团队</base-btn>
          </el-form-item>
          <el-form-item label="密码" :prop="!rowData.id?'password':''">
            <el-input v-model="ruleForm.password" placeholder="请输入密码" autocomplete="new-password" type="password" />
          </el-form-item>

          <el-form-item label="业务" prop="work">
            <el-checkbox-group
              v-for="(list,index) in businessList"
              :key="index"
              v-model="ruleForm.work"
            >
              <el-checkbox
                v-for="(item,j) in list"
                :key="j"
                :label="item.id"
                :name="item.name"
                :disabled="isDisabled(item)"
              >{{ item.name }}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>

          <el-form-item v-if="ruleForm.work.includes('广告审核')" label="频道">
            <div v-if="tagData.length" class="tag-wrap">
              <el-tag
                v-for="(tag,index) in tagData"
                :key="tag.name"
                closable
                class="tag-item"
                :type="tag.type"
                @close="deleteTag(index)"
              >
                {{ tag.name }}
              </el-tag>
              <base-btn type="text" @click="tagData=[]">清空</base-btn>
            </div>
            <div class="all-btn">
              <el-input v-model="channel_name" placeholder="请输入内容" @keyup.enter.native="search">
                <i slot="suffix" class="el-input__icon el-icon-search" @click="search" />
              </el-input>
              <base-btn @click="selectAll">全选</base-btn>
            </div>

            <div class="channel-wrap">
              <span
                v-for="item in filterChannelData"
                :key="item.id"
                :class="[isChoose(item.id)&&'active','channel-item']"
                @click="addChannel(item)"
              >{{ item.name }}</span>
            </div>
          </el-form-item>
          <!-- <el-form-item label="管理员" prop="adminPerson">
            <el-switch v-model="ruleForm.is_admin" />
          </el-form-item> -->
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
            <el-button @click="close('ruleForm')">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <base-dialog title="新建团队" :show="showAddTeam" :append-to-body="true" @close="closeCreateModel">
      <CreateModel @close="closeCreateModel" />
    </base-dialog>
  </base-dialog>
</template>
<script>
import rules from '../js/verification'
import CreateModel from './CreateTeam'
import { mapGetters } from 'vuex'
import sha256 from 'js-sha256'
import business from '../js/business'
export default {
  name: 'AssignTask',
  components: { CreateModel },
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      dialogsObj: {
        width: '8 00px',
        title: '新增用户',
        center: true
      },
      rules: rules,
      ruleForm: {
        phone: '',
        name: '',
        team_id: '',
        password: '',
        work: []
      },
      channel_id: null,
      channel_name: null,
      filterChannelData: [],
      tagData: [],
      rowData: {},
      showAddTeam: false,
      businessList: business
    }
  },
  computed: {
    ...mapGetters(['teamData', 'channelData'])
  },
  watch: {
    rowData(val) {
      if (val.id) {
        this.dialogsObj.title = '编辑用户'
        this.ruleForm.id = val.id
        this.ruleForm.name = val.nickname
        this.ruleForm.phone = val.phone
        this.ruleForm.team_id = val.department_id
        // this.ruleForm.work = []
        val.work ? this.ruleForm.work = val.work.split(',') : this.ruleForm.work = []
        const channelData = val.ad_channel_ids.split(',')
        // channelData.some(id => Number(id) === item.id
        this.tagData = this.filterChannelData.filter(item => channelData.includes(String(item.id)))
        // val.work.split(',').forEach(item => {
        //   this.ruleForm.work.push(item)
        // })
      } else {
        this.dialogsObj.title = '新增用户'
        Object.keys(this.ruleForm).forEach(item => { this.ruleForm[item] = '' })
        this.ruleForm.work = []
      }
    }
  },
  async created() {
    this.$store.dispatch('user/getTeamData', { page: 1, limit: 10000 })
    if (!this.channelData.length) {
      await this.$store.dispatch('channel/getChannelData')
    }
    this.filterChannelData = JSON.parse(JSON.stringify(this.channelData))
  },
  mounted() {
  },
  methods: {
    open() {
      this.show = true
    },
    close() {
      this.$emit('close')
    },
    search() {
      const params = {
        keyword: this.channel_name
      }
      this.$get('/production/channelList', params).then((res) => {
        this.filterChannelData = res.data.list
      })
    },
    isChoose(id) {
      const index = this.tagData.findIndex(item => item.id === id)
      if (index !== -1) return true
      return false
    },
    addChannel(obj) {
      const index = this.tagData.findIndex(item => item.id === obj.id)
      if (index !== -1) return
      this.tagData.push(obj)
    },
    selectAll() {
      this.tagData = JSON.parse(JSON.stringify(this.filterChannelData))
    },
    deleteTag(index) {
      this.tagData.splice(index, 1)
    },
    closeCreateModel() {
      this.showAddTeam = false
      this.$store.dispatch('user/getTeamData', { page: 1, limit: 10000 })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const params = JSON.parse(JSON.stringify(this.ruleForm))
          params.ad_channel_ids = params.work.includes('广告审核') ? this.getChannelIds() : ''
          params.work = params.work.join(',')
          if (params.password) {
            params.password = sha256(params.password)
          } else {
            delete params['password']
          }
          this.userAdd(params)
        } else {
          return false
        }
      })
    },
    getChannelIds() {
      const arr = []
      this.tagData.forEach(item => {
        arr.push(item.id)
      })
      return arr.join(',')
    },
    isDisabled(item) {
      if (!item.mutex) return false
      for (var i = 0; i < item.mutex.length; i++) {
        if (this.ruleForm.work.indexOf(item.mutex[i]) !== -1) {
          return true
        }
      }
    },
    userAdd(params) {
      this.$post('/user/userAdd', params).then((res) => {
        if (this.rowData.id) {
          this.$message({
            message: '编辑用户成功',
            type: 'success'
          })
        } else {
          this.$message({
            message: '新建用户成功',
            type: 'success'
          })
        }
        this.close()
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.add-person{
  height: 550px;
  overflow-y: auto;
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
    .tag-wrap{
      max-height: 200px;
      overflow-y: auto;
      line-height: 28px;
      .tag-item{
        margin-right: 10px;
        height: 22px;
        line-height: 20px;
      }
    }
    .all-btn{
      .el-input{
        width: 60%;
        margin-right: 20px;
      }
    }
    .channel-wrap{
      display: flex;
      flex-wrap: wrap;
      max-height: 200px;
      overflow-y: auto;
      margin-top: 20px;
      background: #f1f1f1;
      padding: 10px;
      .channel-item{
        border: 1px solid #ccc;
        margin-right: 10px;
        margin-bottom: 10px;
        padding: 0 10px;
        font-size: 12px;
        line-height: 24px;
        border-radius: 4px;
        &.active{
          background: #409EFF;
          color: #fff;
        }
      }
    }
  }
}
</style>
