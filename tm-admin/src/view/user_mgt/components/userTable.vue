<!--
 * @Author: your name
 * @Date: 2021-05-27 10:50:49
 * @LastEditTime: 2021-07-22 14:39:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/user_mgt/components/userTable.vue
-->
<template>
  <div class="user-table">
    <p class="total">当前条件下共{{ pag.total }}条数据</p>
    <!-- 用户列表 -->
    <el-table :data="userData" border>
      <el-table-column prop="mobile" label="手机号" />
      <el-table-column prop="names" label="姓名" />

      <el-table-column prop="Limits" label="权限">
        <template slot-scope="scope">
          {{ getLimits(scope.row.Limits) }}
        </template>
      </el-table-column>

      <el-table-column prop="status" label="状态">
        <template slot-scope="scope">
          {{ statusData[scope.row.status] }}
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <base-btn v-has="['superman','用户编辑']" type="text" @click.native="updateUser(scope.row)">编辑</base-btn>
          <base-btn
            v-if="scope.row.status == 1"
            v-has="['superman','用户删除']"
            type="text"
            @click.native="deleteUser(scope.row.id,-1)"
          >禁用</base-btn>
          <base-btn v-else type="text" @click.native="deleteUser(scope.row.id, 1)">恢复</base-btn>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <base-pag
      :total="pag.total"
      :limit="pag.pageSize"
      :page="pag.currentPage"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    />
  </div>
</template>

<script>
// import business from '../js/business'
import { mapGetters } from 'vuex'
export default {
  components: {

  },
  props: {
    // 用户列表数据
    userData: {
      type: Array,
      default() {
        return []
      }
    },
    // 分页数据
    pag: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      // 用户状态
      statusData: { '1': '正常', '-1': '禁用' }
      // 权限
    }
  },
  computed: {
    ...mapGetters([
      'limitData'
    ])
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    handleCurrentChange(val) {
      this.$emit('currentChange', val)
    },
    handleSizeChange(val) {
      this.$emit('sizeChange', val)
    },
    // 编辑弹窗
    updateUser(item) {
      this.$parent.modalData = item
      this.$parent.showAddModel = true
      this.$parent.dialogTitle = '编辑用户'
    },
    async delete(id, status, statusText) {
      const { data, err } = await this.$put(`/user/${id}`, { status: status })
      if (err) {
        this.$message.error(err.msg)
        return
      } else {
        this.$message({
          type: 'success',
          message: `${statusText}用户成功`
        })
        this.$emit('updateData')
      }
    },
    // 删除用户
    deleteUser(id, status) {
      const statusText = status === -1 ? '禁用' : '恢复'

      this.$confirm(`确定要${statusText}此用户吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.delete(id, status, statusText)
      })
    },
    getLimits(limit) {
      if (limit === '*') return '*'
      const arr = []
      if (this.limitData.length) {
        this.limitData.forEach(item => {
          limit.indexOf(item.Id) !== -1 && arr.push(item.Names)
        })
      }
      return arr.join(',') || ''
    }
  }
}
</script>

<style scoped lang="scss">
.user-table{
    .total{
        margin: 20px 0;
    }
}
</style>
