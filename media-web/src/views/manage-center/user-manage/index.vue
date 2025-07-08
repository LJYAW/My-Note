<!--
 * @Author: your name
 * @Date: 2021-08-03 14:45:28
 * @LastEditTime: 2021-09-02 17:28:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/user-manage/index.vue
-->
<template>
  <div class="user-wrap">
    <title-bar title="用户管理" />
    <div class="user-top">
      <div>
        <el-input
          v-model="form.keyWord"
          placeholder="请输入用户名称"
        >
          <i slot="suffix" class="el-input__icon el-icon-search icon-search" @click="search()" />
        </el-input>
      </div>
      <div>
        <el-button type="primary" size="mini" @click="toRole()">角色管理</el-button>
        <el-button type="primary" size="mini" @click="showDialog(588,'新建用户','AddPerson')">创建用户</el-button>
      </div>
    </div>
    <div class="table-list">
      <el-table v-loading="loading" :data="tableData" border>
        <el-table-column prop="mobile" label="登录账号" />
        <el-table-column prop="names" label="用户名称" />
        <el-table-column prop="date" label="用户状态">
          <template slot-scope="scoped">
            <span>{{ scoped.row.status===1?'正常':'禁用' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="用户角色">
          <template slot-scope="scoped">
            <span>{{ scoped.row.type===1?'管理员':'子管理员' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scoped">
            <base-btn type="text" @click="showDialog(588,'修改用户','EditPerson',scoped.row.id)">修改信息</base-btn>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="page-list">
      <base-pag
        :total="total"
        :limit="limit"
        :page="page"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </div>
    <!-- 弹窗 -->
    <BaseDialog
      :title="dialogTit"
      :show.sync="dialogVisible"
      :width="width"
      class="dialog"
    >
      <compontents
        :is="dialogCompomtentsName"
        :id="id"
        @showDialog="showDialog"
        @close="close"
      />
    </BaseDialog>
  </div>
</template>

<script>
import AddPerson from './model/add'
import EditPerson from './model/edit'
import setQueryParams from '@/utils/setQueryParams'

export default {
  name: 'UserManage',
  components: {
    AddPerson, EditPerson
  },
  props: {

  },
  data() {
    return {
      loading: false,
      dialogVisible: false,
      width: '',
      dialogTit: '',
      total: null,
      limit: 10,
      page: 1,
      form: {
        keyWord: ''
      },
      id: '',
      dialogCompomtentsName: '',
      tableData: [
      ]
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getData()
  },
  mounted() {

  },
  methods: {
    // 权限管理
    toRole() {
      this.$router.push({
        path: 'user-manage/role-manage'
      })
    },
    async getData() {
      this.loading = true
      const query = {
        names: this.form.keyWord
      }
      const params = {
        limit: this.limit,
        page: this.page,
        query: setQueryParams(query),
        sortby: 'id',
        order: 'desc'
      }
      const { err, res } = await this.$get('/user/', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.loading = false
      this.tableData = res.data
      this.total = res.count
    },
    search() {
      this.getData()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getData()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getData()
    },
    // 展示dialog
    showDialog(width, title, name, id) {
      this.width = width + 'px'
      this.dialogTit = title
      this.dialogCompomtentsName = name
      this.dialogVisible = true
      this.id = id
    },
    close() {
      this.id = ''
      this.dialogVisible = false
    }
  }
}
</script>

<style scoped lang="scss">
.user-wrap {

  .user-top {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;

    ::v-deep .el-input__inner {
      width: 365px;
      height: 30px;
    }

    .el-input__icon {
      line-height: 0  !important;
      cursor: pointer;
    }
  }

  .table-list {
    margin-top: 20px;
  }
}
</style>
