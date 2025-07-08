<!--
 * @Author: your name
 * @Date: 2021-10-08 14:57:57
 * @LastEditTime: 2021-10-13 15:19:26
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/base_message/index.vue
-->
<template>
  <div class="base-message">
    <!-- tab切换 -->
    <div
      v-for="item in tabData"
      :key="item.name"
      :class="[activeName==item.name&&'active','tabs-item']"
      @click="activeName=item.name,dialogTitle=item.label"
    >{{ item.label }}列表</div>
    <!-- 数据列表 -->
    <div class="table-content">
      <div class="table-search">
        <el-input v-model="keyword" placeholder="请输入关键字" size="small" clearable @keyup.enter.native="getData" />
        <base-btn @click.native="getData">查询</base-btn>
        <base-btn @click.native="add">新建</base-btn>
      </div>
      <p>当前条件下共{{ total }}条数据</p>
      <el-table v-loading="loading" :data="tableData" border height="calc(100vh - 400px)">
        <el-table-column prop="id" label="ID" />
        <el-table-column prop="name" label="标题" />
        <el-table-column prop="updated_at" label="更新时间" />
        <el-table-column label="操作">
          <template slot-scope="scope">
            <base-btn type="text" class="del-btn" @click.native="deleteData(scope.row.id)">删除</base-btn>
          </template>
        </el-table-column>
      </el-table>
      <base-pag
        :total="total"
        :limit="limit"
        :page="page"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </div>
    <!-- 新建弹窗 -->
    <base-dialog
      :show="show"
      :title="`新建${dialogTitle}`"
      width="600"
      @close="close"
    >
      <el-form ref="ruleForm" :inline="true" :rules="rules" :model="form">
        <el-form-item :label="`${dialogTitle}名称`" prop="dialogKeyword">
          <el-input v-model="form.dialogKeyword" :placeholder="`请输入${dialogTitle}名称`" />
        </el-form-item>
      </el-form>
      <div class="btn-wrap">
        <base-btn @click.native="submit">确定</base-btn>
        <base-btn type="info" @click.native="close">取消</base-btn>
      </div>
    </base-dialog>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      activeName: 'commodity',
      dialogTitle: '商品',
      tabData: [{
        name: 'commodity',
        label: '商品'
      }, {
        name: 'brand',
        label: '品牌'
      }, {
        name: 'advertiser',
        label: '广告主'
      }],
      keyword: '',
      tableData: [],
      show: false,
      form: {
        dialogKeyword: ''
      },
      page: 1,
      limit: 20,
      total: 0,
      loading: false,
      rules: {
        dialogKeyword: [{
          required: true,
          message: '请输入名称',
          trigger: 'blur'
        }]
      }
    }
  },
  computed: {

  },
  watch: {
    activeName(val) {
      this.page = 1
      this.getData()
    }
  },
  created() {
    this.getData()
  },
  mounted() {

  },
  methods: {
    async getData() {
      this.loading = true
      const params = {
        type: this.dialogTitle,
        name: this.keyword,
        page: this.page,
        limit: this.limit
      }
      this.$get('/ad-sample/ad-additional/list', params).then((res) => {
        this.tableData = res.data.list
        this.total = res.data.total
      }).catch((err) => {
        this.$message.error(err.msg)
      }).finally(() => {
        this.loading = false
      })
    },
    handleCurrentChange(val) {
      this.page = val
      this.getData()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getData()
    },
    close() {
      this.show = false
      this.form.dialogKeyword = ''
    },
    add() {
      this.show = true
    },
    submit() {
      this.$refs['ruleForm'].validate(valid => {
        if (valid) {
          this.confirmData()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    confirmData() {
      const params = {
        name: this.form.dialogKeyword,
        type: this.dialogTitle
      }
      this.$post('/ad-sample/ad-additional', params).then((res) => {
        this.show = false
        this.getData()
      }).catch((err) => {
        this.$message.error(err.msg)
      })
    },
    deleteData(id) {
      this.$confirm('确认删除此数据？', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.confirmDelete(id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async confirmDelete(id) {
      this.$deleteRun(`/ad-sample/ad-additional/${id}`).then((res) => {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        this.page = 1
        this.getData()
      }).catch((err) => {
        this.$message.error(err.msg)
      })
    }
  }
}
</script>

<style scoped lang="scss">
.base-message{
  margin-top: 10px;
  .tabs-item {
    display: inline-block;
    padding: 9px 10px;
    background: rgba(239,239,239,.8);
    border-radius: 4px;
    color: #404040;
    margin-right: 20px;
    font-size: 12px;
    line-height: 12px;
    cursor: pointer;
    transition: all .3s cubic-bezier(.645,.045,.355,1);

    &.active {
      background: #5675e8;
      color: #fff;
    }
  }
  .table-content{
      margin-top: 20px;
      .table-search{
          display: flex;
          margin-bottom: 20px;
          .el-input{
              width: 300px;
              margin-right: 20px;
          }
      }
      .el-table{
        margin-top: 10px;
      }
      .del-btn{
          color: #F71D1D;
      }
  }
}
.el-dialog{
  display: flex;
  .el-input{
      width: 400px;
  }
  .btn-wrap{
      margin-top: 20px;
      text-align: center;
      button{
          margin: 0 20px;
      }
  }
}
</style>
