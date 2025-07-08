<!--
 * @Author: zzx
 * @Date: 2020-07-22 19:25:47
 * @LastEditTime: 2020-12-07 14:55:36
 * @FilePath: /weijian_web/src/views/user_info/pages/purchase_records.vue
--> 
<template>
  <div class='purchase_record h-100 pt-10px'>
    <tableCard :data='hository_list' :title='tableData'>
      <th slot='th'>支付方式</th>
      <td slot='td'>支付宝</td>
      <div slot='noData' class='no-data'>
        <img :src="src" alt="">
      </div>
    </tableCard>
    <div class='d-flex justify-content-end mt-20px' v-if='total>limit'>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="limit"
        layout="prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
import tableCard from '@/components/table'
export default {
  props: {},
  data() {
    return {
      hository_list: [],
      page: 1,
      currentPage: 0,
      limit: 10,
      total: 0,
      tableData: { product_name: '购买项目', product_num: '购买数量', total_amount_fen: '支付金额', paid_at: '购买时间' },
      src: require('../../../assets/images/user_info/no_purchase.png')
    }
  },
  computed: {},
  watch: {},
  methods: {
    getHistoryList() {
      let params = {
        page: this.page,
        limit: this.limit
      }
      this.$get('/pay/success-order-record-list', { params: params }).then(res => {
        if (res.data.code == '0000') {
          this.hository_list = res.data.data.list
          this.total = res.data.data.total
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    handleCurrentChange(val) {
      this.page = val
      this.getHistoryList()
    }
  },
  components: { tableCard },
  created() {
    this.getHistoryList()
  },
  mounted() {}
}
</script>
<style lang="scss">
.purchase_record {
  background: #fff;
  .table {
    th {
      background: #404040;
      color: #f7f7f7;
    }
  }
}
</style>
