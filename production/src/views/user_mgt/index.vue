<!--
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-03-27 18:50:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/task_mgt/index.vue
-->
<template>
  <div class="user-mgt">
    <div class="user-head">
      <el-form :inline="true">
        <el-form-item label="手机号">
          <el-input v-model="form.phone" placeholder="请输入手机号" clearable />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.name" placeholder="请输入姓名" clearable />
        </el-form-item>
        <el-form-item label="团队">
          <el-select v-model="form.teamId" placeholder="请选择团队" filterable clearable>
            <el-option
              v-for="item in teamData"
              :key="item.id"
              :label="item.department_name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <base-btn size="big" @click.native="search">搜索</base-btn>
        <base-btn size="big" @click.native="clear">清除</base-btn>
      </el-form>
      <div>
        <base-btn size="big" @click.native="addUser">新建用户</base-btn>
      </div>
    </div>
    <el-table v-loading="loading" :data="userData" border>
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="nickname" label="姓名" />
      <el-table-column prop="department" label="团队" />
      <el-table-column prop="role" label="角色" />
      <el-table-column prop="status" label="状态" />
      <el-table-column prop="work" label="业务">
        <template slot-scope="scope">
          {{ scope.row.work.split(' ').join('、') }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <base-btn type="text" @click.native="updateUser(scope.row)">编辑</base-btn>
          <base-btn v-if="scope.row.status == '正常'" type="text" @click.native="deleteUser(scope.row.id,-1)">禁用</base-btn>
          <base-btn v-else type="text" @click.native="deleteUser(scope.row.id, 1)">恢复</base-btn>
        </template>
      </el-table-column>
    </el-table>
    <base-pag :total="total" :limit="pageSize" :page="currentPage" @current-change="handleCurrentChange" @size-change="handleSizeChange" />

    <add ref="add" :show="showAddModel" @close="close" />
  </div>
</template>
<script>
import add from './models/add'
import { mapGetters } from 'vuex'
export default {
  components: { add },
  data() {
    return {
      showAddModel: false,
      form: {
        name: '',
        phone: '',
        teamId: ''
      },
      pageSize: 10,
      currentPage: 1,
      total: 0,
      userData: [],
      loading: false
    }
  },
  computed: {
    ...mapGetters(['teamData'])
  },
  watch: {

  },
  created() {
    this.getUserList()
    this.$store.dispatch('user/getTeamData', { page: 1, limit: 10000 })
  },
  mounted() {

  },
  methods: {
    handleCurrentChange(val) {
      this.currentPage = val
      this.getUserList()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getUserList()
    },
    search() {
      this.currentPage = 1
      this.getUserList()
    },
    // 获取用户列表
    getUserList() {
      this.loading = true
      var params = {
        page: this.currentPage,
        limit: this.pageSize,
        keyword: this.form.name,
        phone: this.form.phone,
        team_id: this.form.teamId
      }
      this.$get('/user/userList', params).then((res) => {
        this.userData = res.data.list
        this.total = res.data.total
        this.loading = false
      }).catch((error) => {
        this.$message.error(error.msg)
        this.loading = false
      })
    },
    // 关闭弹窗
    close() {
      this.showAddModel = false
      this.getUserList()
    },
    // 编辑弹窗
    updateUser(item) {
      this.showAddModel = true
      this.$refs.add.rowData = item
    },
    // 新增弹窗
    addUser() {
      this.showAddModel = true
      this.$refs.add.rowData = {}
    },
    // 删除用户
    deleteUser(id, status) {
      this.$confirm('确定要禁用此用户吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$post('/user/change-status', { id: id, status: status }).then((res) => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getUserList()
        }).catch((error) => {
          this.$message.error(error.msg)
        })
      })
    },
    clear() {
      Object.keys(this.form).forEach(item => {
        this.form[item] = ''
      })
      this.getUserList()
    }
  }
}
</script>

<style lang='scss' scoped>
.user-mgt{
  background: #fff;
  padding: 20px;
  .user-head{
    display: flex;
    justify-content: space-between;
    .el-form-item{
      margin-right: 20px;
    }
  }
}
</style>
