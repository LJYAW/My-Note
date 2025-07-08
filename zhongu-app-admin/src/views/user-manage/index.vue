<!--
 * @Author: your name
 * @Date: 2021-10-20 12:12:57
 * @LastEditTime: 2021-11-04 16:52:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/user-manage/index.vue
-->
<template>
  <div class="user-manage">
    <base-tab :tab-arr="tabArr" />
    <div class="search-wrap">
      <el-input
        v-model="form.phone"
        size="mini"
        clearable
        :maxlength="30"
        suffix-icon="el-icon-search"
      />
      <el-select v-model="form.total_coins" size="mini" clearable placeholder="消费金额">
        <el-option label="消费金额升序" value="asc" />
        <el-option label="消费金额降序" value="desc" />
      </el-select>
      <el-select v-model="form.created_at" size="mini" clearable placeholder="创建时间">
        <el-option label="创建时间从近到远" value="desc" />
        <el-option label="创建时间从远到近" value="asc" />
      </el-select>
      <el-select v-model="form.status" size="mini" clearable placeholder="账号状态">
        <el-option label="正常" value="正常" />
        <el-option label="禁用" value="禁用" />
      </el-select>
      <div class="price-wrap">
        <el-input-number v-model="form.minPrice" :controls="false" size="mini" placeholder="价格" :max="999999" />
        至
        <el-input-number v-model="form.maxPrice" :controls="false" size="mini" placeholder="价格" :max="999999" />
      </div>
      <base-btn class="confirm-btn" @click="search">确认</base-btn>
    </div>
    <el-table :data="tableData" border stripe height="calc(100vh - 350px)">
      <el-table-column label="账号信息" prop="phone" />
      <el-table-column label="用户昵称" prop="nickname" />
      <el-table-column label="创建日期" prop="created_at" />
      <el-table-column label="消费金额" prop="total_coins" />
      <el-table-column label="设备型号" prop="device" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <base-btn type="text" @click="routerGo(scope.row.id)">查看详情</base-btn>
        </template>
      </el-table-column>
    </el-table>
    <base-pag
      :page="page"
      :limit="limit"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
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
      tabArr: ['用户管理'],
      form: {
        phone: '',
        created_at: null,
        minPrice: undefined,
        maxPrice: undefined,
        status: null
      },
      page: 1,
      limit: 20,
      total: 0,
      tableData: [],
      dialogVisible: false,
      dialogTitle: '新建用户',
      userData: {}
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getUserData()
  },
  mounted() {

  },
  methods: {
    async getUserData() {
      const params = this.setParams()
      const { err, res } = await this.$get('/admin/users', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.tableData = res.data.list
      this.total = res.data.total
    },
    // 用户列表参数
    setParams() {
      return {
        phone: this.form.phone,
        status: this.form.status,
        sort: this.getSortParams(),
        price: this.getPriceParams(),
        page: this.page,
        limit: this.limit
      }
    },
    // 排序参数
    getSortParams() {
      const arr = ['total_coins', 'created_at']
      const sortArr = []
      arr.forEach((item) => {
        if (this.form[item]) {
          sortArr.push(`${item},${this.form[item]}`)
        }
      })
      return sortArr.join('|')
    },
    // 价格参数
    getPriceParams() {
      if (this.form.minPrice || this.form.maxPrice) {
        return (this.form.minPrice || '') + '|' + (this.form.maxPrice || '')
      }
      return ''
    },
    search() {
      this.page = 1
      this.getUserData()
    },
    routerGo(id) {
      this.$router.push({
        path: 'user-detail',
        query: {
          id: id
        }
      })
    },
    // 分页器
    handleSizeChange(val) {
      this.limit = val
      this.getUserData()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getUserData()
    }
  }
}
</script>

<style scoped lang="scss">
.user-manage {
  .search-wrap {
    .el-input {
      width : 365px;
      margin-right : 20px;
      margin-bottom : 10px;
    }
    .el-select {
      margin-right : 20px;
    }
    .search-btn {
      display : inline-block;
      padding : 6px 10px;
      background : rgba(239,239,239,.8);
      border-radius : 4px;
      margin-right : 10px;
      i {
        font-size : 12px;
        margin-left : 10px;
      }
    }
    .price-wrap {
      display : inline-block;
      border-radius : 4px 0px 0px 4px;
      // opacity : .4;
      border : 1px solid rgba(151,151,151,.4);
      .el-input-number {
        width : 65px;
        ::v-deep input {
          border : none;
        }
      }
    }
    .confirm-btn {
      height : 30px;
      margin-right : 20px;
      border-radius : 0px 4px 4px 0px;
    }
    .new-btn {
      margin-left : 0;
      height : 30px;
    }
  }
  .el-table {
    margin-top : 20px;
  }
}

</style>
