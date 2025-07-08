<!--
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-06-08 16:15:57
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/task_mgt/index.vue
-->
<template>
  <div class="user-mgt">
    <!-- 搜索条件 -->
    <div class="user-head">
      <el-form :inline="true">

        <el-form-item label="手机号">
          <el-input v-model="form.mobile" placeholder="请输入手机号" clearable />
        </el-form-item>

        <el-form-item label="姓名">
          <el-input v-model="form.names" placeholder="请输入姓名" clearable />
        </el-form-item>

        <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="请选择状态" filterable clearable>
            <el-option
              v-for="(value,key) in statusData"
              :key="key"
              :label="value"
              :value="key"
            />
          </el-select>
        </el-form-item>

        <base-btn size="big" @click.native="search">查询</base-btn>
        <base-btn size="big" @click.native="clear">清除</base-btn>
      </el-form>
      <div>
        <base-btn v-has="['superman']" size="big" @click.native="addUser">新建用户</base-btn>
      </div>
    </div>
    <!-- 用户列表 -->
    <userTable
      :user-data="userData"
      :pag="pag"
      @currentChange="handleCurrentChange"
      @sizeChange="handleSizeChange"
      @updateData="getUserList"
    />
    <!-- 新建用户 -->
    <BaseDialog :show.sync="showAddModel" :title="dialogTitle">
      <add :modal-data="modalData" @updateData="getUserList" @close="close" />
    </BaseDialog>
  </div>
</template>
<script>
import add from './model/add'
import userTable from './components/userTable'
import setQueryParams from '../../utils/setQueryParams'
import { mapGetters } from 'vuex'
export default {
  components: { add, userTable },
  data() {
    return {
      // 是否显示新增用户弹窗
      showAddModel: false,
      // 查询条件
      form: {
        names: '',
        mobile: '',
        status: ''
      },
      // 分页数据
      pag: {
        pageSize: 10,
        currentPage: 1,
        total: 0
      },
      // 用户列表
      userData: [],
      // 用户状态
      statusData: { '1': '正常', '-1': '禁用' },
      loading: false,
      // 弹窗数据
      modalData: {},
      dialogTitle: ''
    }
  },
  computed: {
    ...mapGetters(['limitData'])
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
      this.pag.currentPage = val
      this.getUserList()
    },
    handleSizeChange(val) {
      this.pag.pageSize = val
      this.getUserList()
    },
    search() {
      this.pag.currentPage = 1
      this.getUserList()
    },
    // 获取用户列表
    async getUserList() {
      this.loading = true
      const query = {
        names: this.form.names,
        mobile: this.form.mobile,
        status: this.form.status
      }
      var params = {
        page: this.pag.currentPage,
        limit: this.pag.pageSize,
        query: setQueryParams(query)

      }

      const { data, err } = await this.$get('/user', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.userData = data.data
      this.pag.total = data.count
      this.loading = false
    },
    // 关闭弹窗
    close() {
      this.showAddModel = false
    },
    // 新增弹窗
    addUser() {
      this.dialogTitle = '新增用户'
      this.showAddModel = true
      this.modalData = {}
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
