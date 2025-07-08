<!--
 * @Author: your name
 * @Date: 2021-08-05 17:02:57
 * @LastEditTime: 2021-08-31 15:28:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/directory-manage/model/personAdministration.vue
-->
<template>
  <div class="person-administration">
    <el-form ref="form" :model="form" :inline="true" class="form">
      <el-form-item class="item-input">
        <el-input
          v-model="form.keyWord"
          placeholder="请输入用户名称"
          size="small"
          @input="search"
        >
          <!-- <i slot="suffix" class="el-input__icon el-icon-search icon-search" @click="search()" /> -->
        </el-input>
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" size="mini" @click="addPerson()">添加成员</el-button>
      </el-form-item>
    </el-form>
    <div class="table-list">
      <el-table :data="tempUserDirectData" border>
        <el-table-column prop="user_names" label="用户姓名" />
        <el-table-column prop="mobile" label="手机号码" />
        <el-table-column prop="date" label="用户角色" width="160px">
          <template slot-scope="scoped">
            <el-select
              v-if="roleData.length>0"
              v-model="scoped.row.user_group_id"
              clearable
              placeholder="请选择角色"
              class="role-select"
            >
              <el-option
                v-for="item in roleData"
                :key="item.id"
                :label="item.names"
                :value="item.id"
                @click.native="editRole(scoped.row,item)"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scoped">
            <base-btn type="text" @click="detelteData(scoped.row.id)">移除成员</base-btn>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 弹窗 -->
    <BaseDialog
      title="添加成员"
      :show.sync="dialogVisible"
      width="294px"
      class="dialog"
      append-to-body
    >
      <AddPerson :id="id" :role-data="roleData" @close="close" />
    </BaseDialog>
  </div>
</template>

<script>
import AddPerson from './addPerson'
import { mapGetters } from 'vuex'

export default {
  name: 'PersonAdministration',
  components: {
    AddPerson
  },
  props: {
    id: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      dialogVisible: false,
      form: {
        keyWord: ''
      },
      roleData: [],
      tempUserDirectData: []
    }
  },
  computed: {
    ...mapGetters(['userDirectData'])
  },
  watch: {

  },
  created() {
    this.initData()
  },
  mounted() {
  },
  methods: {
    // 模糊搜索
    search() {
      this.tempUserDirectData = JSON.parse(JSON.stringify(this.userDirectData))
      if (this.form.keyWord) {
        this.tempUserDirectData = this.tempUserDirectData.filter(item => {
          return item.user_names.match(this.form.keyWord)
        })
      } else {
        this.tempUserDirectData = JSON.parse(JSON.stringify(this.userDirectData))
      }
    },
    // 修改角色
    editRole(scopde, item) {
      const params = {
        user_group_id: item.id, // 更新的角色id
        dir_id: scopde.dir_id// 更新的目录id
      }
      // scopde.id  是更新的目录用户成员的id
      this.$put(`/userdirs/${scopde.id}`, params).then(response => {
        const { err, res } = response
        if (err) {
          this.$message({
            message: err.msg,
            type: 'error'
          })
        } else {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
        }
      })
    },
    async initData() {
      const params = {
        limit: 100,
        page: 1,
        query: `dir_id:${this.id}`,
        sortby: 'id',
        order: 'desc'
      }
      await this.$store.dispatch('directoryData/getUserDirectData', params)
      await this.$store.dispatch('directoryData/getUserRoleData', { params: { page: 1, limit: 100 }}).then((res) => {
        this.roleData = res.data
      })
      this.search()
    },
    // 删除
    detelteData(id) {
      this.$confirm('是否要移除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deteltePerson(id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消移除'
        })
      })
    },
    async deteltePerson(id) {
      const { err, res } = await this.$deleteRun(`/userdirs/${id}`)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.$message.success('删除成功')
      this.initData()
      this.$bus.$emit('resetData')
    },
    addPerson() {
      this.dialogVisible = true
    },
    close() {
      this.dialogVisible = false
    }
  }
}
</script>

<style scoped lang="scss">
.person-administration {
  height: 400px;
  overflow: auto;

  .form {

    .item-input {
      width: 390px;

      ::v-deep .el-form-item__content {
        width: 100%;
      }
    }
  }

  .role-select {

    ::v-deep.el-input {

      .el-input__inner {
        border: none;
        padding: 0;
        background: transparent;
        color: #404040;
        font-weight: 400;
      }
    }
  }

  ::v-deep .el-table {

    th,
    td {
      height: 30px;
      line-height: 30px;
      padding: 0;
      font-size: 12px;
      color: #404040;
      font-weight: 430;
    }
  }
}
</style>
