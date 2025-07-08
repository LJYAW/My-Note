<!--
 * @Author: your name
 * @Date: 2021-12-08 15:21:36
 * @LastEditTime: 2021-12-08 18:09:43
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /zhongu-app-admin/src/views/auction/list.vue
-->
<template>
  <div class="auction-list">
    <el-table
      v-loading="loading"
      :data="tableData"
      border
      height="calc(100vh - 360px)"
    >
      <el-table-column prop="goods_name" label="产品名称" width="180" />
      <el-table-column prop="goods_number" label="产品编号" width="180" />
      <el-table-column prop="goods_end_at" label="截止时间" />
      <el-table-column prop="max_bid_coins" label="当前出价" />
      <el-table-column prop="status" label="当前状态" />
      <el-table-column prop="max_bid_user" label="当前出价用户" />
      <el-table-column prop="address" label="操作">
        <template slot-scope="scope">
          <base-btn type="text" @click.native="gotoDataDetails(scope.row)">查看详情</base-btn>
        </template>
      </el-table-column>
    </el-table>

    <base-pag :total="total" :limit="limit" :page="page" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
  </div>
</template>

<script>
export default {
  components: {},
  props: {},
  data() {
    return {
      loading: true,
      tableData: [],
      total: 0,
      currentPage: 0,
      page: 0,
      limit: 10
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getAuctionList()
  },
  mounted() {},
  methods: {
    handleCurrentChange(val) {
      this.page = val
      this.getAuctionList()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getAuctionList()
    },
    gotoDataDetails(item) {
      this.$router.push({
        path: '/auction-details',
        query: {
          id: item.goods_auction_id
        }
      })
    },
    async getAuctionList() {
      this.loading = true
      const parmas = {
        page: this.page,
        limit: this.limit
      }
      const { err, res } = await this.$get('/amdin/goods-auction/list', parmas)
      this.tableData = res.data.list
      this.total = res.data.total
      this.loading = false
    }
  }
}
</script>

<style scoped lang="scss">
</style>
