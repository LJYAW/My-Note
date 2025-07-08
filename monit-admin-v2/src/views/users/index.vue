<!--
 * @Author: your name
 * @Date: 2021-07-02 16:12:26
 * @LastEditTime: 2021-07-12 11:51:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin-template/src/views/users/index.vue
-->
<template>
  <div class="urer-wrap">
    <p class="title">账户管理</p>
    <div class="top">
      <el-button type="primary" class="btn" @click="add()">创建用户</el-button>
      <el-input v-model="form.name" class="input-with-select">
        <el-select
          slot="prepend"
          v-model="form.selectName"
          placeholder="公司名称"
        >
          <el-option
            v-for="(item) in companyList"
            :key="item.id"
            :value="item.id"
            :label="item.label"
          />
        </el-select>
      </el-input>
      <el-button type="primary" class="search" @click="search()">
        搜索
      </el-button>
    </div>
    <el-table
      :data="tableData"
      border
      stripe
      class="user-table"
    >
      <el-table-column
        prop="id"
        label="用户ID"
      />
      <el-table-column
        prop="names"
        label="用户账号"
      />
      <el-table-column
        prop="company"
        label="公司名称"
      />
      <el-table-column
        prop="passwd"
        label="用户密码"
      />
      <el-table-column
        prop="mobile"
        label="绑定手机"
      />
      <el-table-column
        prop="create_time"
        label="开设时间"
      >
        <template slot-scope="scope">
          <span> {{ (scope.row.create_time)*1000 | unixToHms }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="endtime"
        label="到期时间"
      >
        <template slot-scope="scope">
          <span> {{ (scope.row.endtime)*1000 | unixToHms }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        label="当前状态"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.status===1?'已开启':'已停用' }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="saleuser"
        label="销售顾问"
      />
      <el-table-column
        prop="saleuser"
        label="人工判断"
      >
        <template slot-scope="scope">
          {{ scope.row.status===1?'是':'否' }}
        </template>
      </el-table-column>
      <el-table-column
        label="设置"
      >
        <template slot-scope="scope">
          <base-btn type="text" @click="edit(scope.row)">修改</base-btn>
        </template>
      </el-table-column>
    </el-table>
    <div class="pageList">
      <base-pag
        :total="total"
        :limit="limit"
        :page="page"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </div>
  </div>
</template>

<script>
import setQueryParams from '@/utils/setQueryParams'

export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      total: null,
      limit: 10,
      page: 1,
      tableData: [
      ],
      form: {
        name: '',
        selectName: ''
      },
      query: {
        company__icontains: '',
        mobile: '',
        names: '',
        saleuser: ''
      },
      companyList: [
        {
          label: '公司名称',
          id: 'company__icontains'
        },
        {
          label: '手机号',
          id: 'mobile'
        },
        {
          label: '用户账号',
          id: 'names'
        },
        {
          label: '销售顾问',
          id: 'saleuser'
        }
      ]
    }
  },
  computed: {

  },
  watch: {
    'form.selectName'(val) {
      this.form.name = ''
      Object.keys(this.query).forEach(item => {
        this.query[item] = ''
      })
    }
  },
  created() {
    this.getList()
  },
  mounted() {

  },
  methods: {
    search() {
      this.query[this.form.selectName] = this.form.name
      this.getList()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getList()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getList()
    },
    async getList() {
      const params = {
        limit: this.limit,
        page: this.page,
        query: setQueryParams(this.query),
        sortby: 'id',
        order: 'desc'
      }
      const res = await this.$get('/v1/user/', params)
      if (res.err) {
        return
      }
      this.tableData = res.data.data
      this.total = res.data.count
    },
    edit(item) {
      this.$router.push({
        path: 'edit',
        query: {
          id: item.id
        }
      })
    },
    add() {
      this.$router.push({
        path: 'add'
      })
    }
  }
}
</script>

<style lang="scss" >
.urer-wrap {
  padding: 30px;
  box-sizing: border-box;

  .title {
    font-size: 32px;
    line-height: 32px;
    font-weight: 600;
    margin-bottom: 28px;
  }

  .top {
    display: flex;

    .btn {
      width: 287px;
      height: 60px;
      font-size: 24px;
      margin-right: 40px;
    }

    .el-input__inner {
      height: 60px;
    }

    .el-input--suffix {
      width: 145px;
      height: 60px;
      background: #eaf1fe;
      border-radius: 4px 0px 0px 4px;
      color: #888887;
      font-weight: 600;
    }

    .search {
      width: 150px;
      height: 60px;
    }
  }

  .user-table {
    margin-top: 20px;
  }

  .pageList {
    margin-top: 20px;
  }
}
</style>
