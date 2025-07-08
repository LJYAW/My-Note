<!--
 * @Author: your name
 * @Date: 2021-08-03 14:42:51
 * @LastEditTime: 2021-10-13 15:24:39
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/order-info/index.vue
-->
<template>
  <div class="order-info">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="充值订单" name="1" />
      <el-tab-pane label="消费记录" name="2" />
    </el-tabs>
    <div v-if="activeName==='1'">
      <div class="search-wrap">
        <el-date-picker
          v-model="form.date"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          size="mini"
        />
        <div class="package-wrap">
        <!-- <el-select v-model="form.orderType" size="mini" class="package-select" clearable>
          <el-option
            v-for="item in orderData"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
        <el-select v-if="form.orderType=='套餐购买'" v-model="form.package" size="mini" clearable>
          <el-option
            v-for="item in packageData"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select> -->
        </div>
        <el-select v-model="form.status" size="mini" class="package-wrap" placeholder="订单状态" clearable>
          <el-option
            v-for="item in statusData"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
        <base-btn size="mini" class="btn" @click="search">查询</base-btn>
        <base-btn size="mini" class="export-btn">导出excel</base-btn>
      </div>
      <el-table :data="tableData" border>
        <el-table-column label="订单编号" prop="id" />
        <el-table-column label="购买用户" prop="person" />
        <el-table-column label="订单名称" prop="order_name" />
        <el-table-column label="订单状态" prop="status" />
        <el-table-column label="订单创建时间" prop="create_time" />
      </el-table>
      <div class="page-list">
        <base-pag
          :total="total"
          :limit="limit"
          :page="page"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
        />
      </div>
    </div>
    <Consumption v-else />
  </div>
</template>

<script>
import setQueryParams from '@/utils/setQueryParams'
import Consumption from './consumption.vue'
export default {
  components: {
    Consumption
  },
  props: {

  },
  data() {
    return {
      form: {
        date: '',
        orderType: '套餐购买',
        package: '套餐A'
      },
      orderData: ['套餐购买', '单项购买'],
      packageData: ['套餐A', '套餐B'],
      statusData: ['待支付', '支付成功'],
      tableData: [],
      total: null,
      limit: 10,
      page: 1,

      activeName: '1'
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    // this.getPackageData()
    this.getOrderData()
  },
  mounted() {

  },
  methods: {
    handleClick(tab, event) {
      this.activeName = tab.name
    },
    // 获取订单列表
    async getOrderData() {
      const query = {
        // admin_names: this.form.name,
        create_time__gt: this.form.date ? this.form.date[0] : '',
        create_time__lt: this.form.date ? this.form.date[1] : '',
        status: this.form.status
      }
      const params = {
        limit: this.limit,
        page: this.page,
        query: setQueryParams(query),
        sortby: 'id',
        order: 'desc'
      }
      const { err, res } = await this.$get('/orders', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.tableData = res.data
      this.total = res.count
    },
    search() {
      this.page = 1
      this.getOrderData()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getOrderData()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getOrderData()
    },
    // 获取套餐
    async getPackageData() {
      const { err, data } = await this.$get('/taocan')
      console.log('🚀 ~ file: index.vue ~ line 108 ~ etPackageData ~ data', data)
    }
  }
}
</script>

<style scoped lang="scss">
.order-info {
  padding-bottom: 20px;

  .search-wrap {
    margin: 20px 0;
    display: flex;
    position: relative;

    .el-date-editor.el-input__inner {
      width: 250px;
      min-width: 300px;
    }

    .el-select {

      ::v-deep input {
        border: 0;
        background: #efefef;
        width: 100px;
        border-radius: 0;
      }
    }

    .package-wrap {
      margin-left: 20px;
      border-radius: 4px;
      overflow: hidden;
      opacity: .8;

      .package-select::after {
        content: "";
        width: 1px;
        height: 14px;
        background: #d8d8d8;
        opacity: .6;
        position: absolute;
        top: 7px;
        right: 0;

      }
    }

    .btn {
      margin-left: 20px;
    }

    .export-btn {
      position: absolute;
      right: 0;
    }
  }
}

</style>
