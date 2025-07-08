<!--
 * @Author: your name
 * @Date: 2021-08-18 16:34:56
 * @LastEditTime: 2021-08-24 10:31:53
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/user-manage/roleList.vue
-->
<template>
  <div class="role-list">
    <div class="btns">
      <el-button type="primary" size="mini" @click="addRole">创建角色</el-button>
    </div>
    <div class="table-list">
      <el-table v-loading="loading" :data="tableData" border>
        <el-table-column prop="names" label="角色名称" />
        <el-table-column prop="limitlist" label="权限明细">
          <template slot-scope="scoped">
            {{ setData(scoped.row.limitlist) }}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scoped">
            <base-btn type="text" @click="edit(scoped.row)">编辑</base-btn>
            <base-btn type="text" class="deteleData" @click="detelData(scoped.row.id)">删除</base-btn>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="page-list">
      <base-pag
        :total="form.total"
        :limit="form.limit"
        :page="form.page"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </div>
    <!-- 弹窗 -->
    <BaseDialog
      :title="dialogTit"
      :show.sync="dialogVisible"
      width="294px"
      class="dialog"
    >
      <addRole :id="id" :item-data="itemData" :title="dialogTit" @close="close" />
    </BaseDialog>
  </div>
</template>

<script>
import addRole from './model/addRole'
export default {
  components: {
    addRole
  },
  props: {

  },
  data() {
    return {
      loading: false,
      title: '',
      itemData: {},
      dialogTit: '',
      dialogVisible: false,
      tableData: [],
      id: 0,
      form: {
        page: 1,
        limit: 10,
        total: null
      }
    }
  },
  computed: {
  },
  watch: {

  },
  created() {
    this.getInitData()
  },
  mounted() {

  },
  methods: {
    setData(data) {
      const arr = data.split(',')
      return arr.map(k => k.split('|')[1]).join(',')
    },
    addRole() {
      this.dialogTit = '创建角色'
      this.dialogVisible = true
    },
    handleCurrentChange(val) {
      this.page = val
      this.getInitData()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getInitData()
    },
    getInitData() {
      this.loading = true
      this.$store.dispatch('directoryData/getUserRoleData', this.form).then(res => {
        this.loading = false
        this.tableData = res.data
        this.form.total = res.count
      })
    },
    close() {
      this.dialogVisible = false
      this.id = ''
      this.itemData = {}
    },
    edit(item) {
      this.dialogTit = '修改角色'
      this.dialogVisible = true
      this.id = item.id
      this.itemData = item
    },
    detelData(id) {
      this.$confirm('是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRole(id)
      }).catch(() => {
      })
    },
    async deleteRole(id) {
      const { err, res } = await this.$deleteRun(`/usergroup/${id}`)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.$message.success('删除成功')
      this.getInitData()
    }
  }
}
</script>

<style scoped lang="scss">
.deteleData {
  color: #f03535;
}

.btns {
  width: 100%;
  text-align: right;
  margin-top: 10px;
  margin-bottom: 20px;
}
</style>
