<!--
 * @Author: your name
 * @Date: 2021-11-01 16:32:48
 * @LastEditTime: 2021-11-04 16:53:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/account-manage/index.vue
-->
<template>
  <div class="account-mgt">
    <div class="user-head">
      <el-form :inline="true">
        <el-form-item label="手机号">
          <el-input
            v-model="form.username"
            placeholder="请输入手机号"
            clearable
            size="mini"
            :maxlength="30"
            @keyup.enter.native="search()"
          />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input
            v-model="form.nickname"
            size="mini"
            placeholder="请输入姓名"
            :maxlength="30"
            clearable
            @keyup.enter.native="search()"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" size="mini" placeholder="请选择用户状态" filterable clearable @keydown.enter.native="search()">
            <el-option
              v-for="item in userStatus"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <base-btn size="mini" @click.native="search">搜索</base-btn>
        <base-btn size="mini" @click.native="clear">清除</base-btn>
      </el-form>
      <div>
        <base-btn size="mini" @click.native="addUser">新建用户</base-btn>
      </div>
    </div>
    <p>当前条件下共{{ total }}条数据</p>
    <el-table v-loading="loading" :data="userData" border>
      <el-table-column prop="username" label="手机号" />
      <el-table-column prop="nickname" label="姓名" />
      <el-table-column prop="work" label="权限" width="400">
        <template slot-scope="scope">
          {{ scope.row.work }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <base-btn type="text" @click.native="updateUser(scope.row)">编辑</base-btn>
          <base-btn v-if="scope.row.status == '正常'" type="text" @click.native="deleteUser(scope.row.user_id,'禁用')">禁用</base-btn>
          <base-btn v-else type="text" @click.native="deleteUser(scope.row.user_id, '正常')">恢复</base-btn>
        </template>
      </el-table-column>
    </el-table>
    <base-pag :total="total" :limit="pageSize" :page="currentPage" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
    <base-dialog
      :show.sync="dialogVisible"
      :title="dialogTitle"
      width="500px"
    >
      <add :user-data="userInfo" @close="close" />
    </base-dialog>
  </div>
</template>
<script>
import add from './components/Add.vue'
export default {
  components: { add },
  data() {
    return {
      showAddModel: false,
      form: {
        nickname: '',
        username: '',
        status: ''
      },
      dialogTitle: '',
      dialogVisible: false,
      pageSize: 10,
      currentPage: 1,
      total: 0,
      userData: [],
      loading: false,
      userStatus: ['正常', '禁用'],
      userInfo: {}
    }
  },
  computed: {
  },
  watch: {

  },
  created() {
    this.getUserList()
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
    // 获取用户列表
    async getUserList() {
      const params = {
        page: this.currentPage,
        limit: this.pageSize,
        ...this.form
      }
      const { err, res } = await this.$get('/admin/admin-user/user-list', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.userData = res.data.list
      this.total = res.data.total
    },
    // 关闭弹窗
    close() {
      this.dialogVisible = false
      this.getUserList()
    },
    // 编辑弹窗
    updateUser(item) {
      this.dialogVisible = true
      this.dialogTitle = '编辑用户'
      this.userInfo = item
    //   this.$refs.add.rowData = item
    },
    // 新增弹窗
    addUser() {
      this.userInfo = {}
      this.dialogVisible = true
      this.dialogTitle = '新建用户'
    },
    // 删除用户
    deleteUser(id, status) {
      if (status === '禁用') {
        this.$confirm('确定要禁用此用户吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.changeStatus(id, status)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消禁用'
          })
        })
      } else {
        this.changeStatus(id, status)
      }
    },
    async changeStatus(id, status) {
      const params = {
        user_id: id,
        status: status
      }
      const { err, res } = await this.$post('/admin/admin-user/change-status', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.getUserList()
    },
    clear() {
      Object.keys(this.form).forEach(item => {
        this.form[item] = ''
      })
      this.getUserList()
    },
    search() {
      this.page = 1
      this.getUserList()
    }
  }
}
</script>

<style lang='scss' scoped>
.account-mgt {
  background : #FFFFFF;
  .user-head {
    display : flex;
    justify-content : space-between;
    ::v-deep .el-form-item {
      margin-right : 20px;
      .el-form-item__content, .el-form-item__label {
        line-height : 28px;
      }
    }
  }
  .el-table {
    margin-top : 20px;
  }
}

</style>
