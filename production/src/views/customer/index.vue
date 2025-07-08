<!--
 * @Author: zll
 * @Date: 2021-01-11 21:42:01
 * @LastEditTime: 2021-01-20 21:23:12
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/customer/index.vue
-->
<template>
  <div class="customer-warp">
    <div class="customer-top">
      <el-breadcrumb class="breadcrumb">
        <el-breadcrumb-item to="">客户管理</el-breadcrumb-item>
        <el-breadcrumb-item>客户列表</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="add-customer">
        <el-button @click="add()">+创建客户</el-button>
      </div>
    </div>
    <div class="customer-list">
      <div class="search-top">
        <div class="item-list">
          <span>客户简称:</span>
          <el-input
            v-model="form.name"
            placeholder="请输入客户简称"
          />
        </div>
        <div class="item-list">
          <span>业务类型:</span>
          <el-select v-model="form.type" placeholder="请选择业务类型">
            <el-option
              v-for="item in options_type"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </div>
        <div class="btns">
          <el-button type="primary">搜索</el-button>
        </div>
      </div>
      <div class="table-list">
        <el-table :data="tableData">
          <el-table-column prop="name" label="客户全称">
            <template slot-scope="scope">
              <div class="ellipsis">
                {{ scope.row.name }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="dau" label="客户简称" />
          <el-table-column prop="pv" label="合同编号" />
          <el-table-column prop="zb" label="客户等级" />
          <el-table-column prop="csp" label="业务类型" />
          <el-table-column prop="csp" label="操作">
            <template slot-scope="scope">
              <a class="item-scope" @click="edit(scope)">编辑</a>
              <a class="item-scope" @click="deatils(scope)">查看</a>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pagination-list">
        <el-pagination
          :current-page="form.page"
          :page-sizes="[100, 200, 300, 400]"
          :page-size="form.lmit"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />

      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      total: 20,
      form: {
        name: '',
        type: '',
        page: 1,
        lmit: 10
      },
      options_type: [
        {
          label: '栏目卡',
          value: '栏目卡'
        }
      ],
      tableData: [
        {
          name: 'scscms'
        }
      ]
    }
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() {

  },
  methods: {
    edit(scope) {
      this.$router.push({
        path: `/customer-add`,
        query: {
          type: '编辑列表'
        }
      })
    },
    deatils(scope) {
      this.$router.push({
        path: `/customer-add`,
        query: {
          type: '查看列表'
        }
      })
    },
    add() {
      this.$router.push({
        path: `/customer-add`,
        query: {
          type: '创建列表'
        }
      })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
    }
  }
}
</script>

<style lang='scss' scoped>
.customer-warp{
  width: 100%;
  height: 100vh;
  background: #f4f4f5;
  padding: 0 20px;
  .customer-top{
    height: 60px;
    line-height: 60px;
    display: flex;
    justify-content: space-between;
    .breadcrumb{
      line-height: 60px;
    }
    button{
      background: #f3893c;
      color: #ffffff;
      border:none;
    }
  }
  .customer-list{
    height: calc(100% - 60px);
    padding: 20px;
    background: #ffffff;
    .search-top{
      display: flex;
      .item-list{
        display: flex;
        margin-right: 30px;
        ::v-deep .el-input__inner{
          background: #f8f8f8;
          border: none;
        }
        ::-webkit-input-placeholder {
          /* WebKit browsers */
          color: #555;
          font-size: 14px;
        }
        span{
          min-width: 80px;
          margin-top: 10px;
        }
      }
    }
    .table-list{
      margin-top: 30px;
       .ellipsis {
          line-height: 20px;
          text-overflow: -o-ellipsis-lastline;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          line-clamp: 2;
          -webkit-box-orient: vertical;
        }
      .item-scope{
        color:#2d86e4;
        margin-right: 10px;
        font-weight: 500;
      }
    }
    .pagination-list{
      width: 100%;
      text-align: right;
      margin-top: 30px;
    }
  }
}
</style>
