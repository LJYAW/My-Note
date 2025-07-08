<!--
 * @Author: your name
 * @Date: 2021-10-20 11:57:14
 * @LastEditTime: 2021-11-18 09:58:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/order-manage/index.vue
-->
<template>
  <div class="order-manage">
    <base-tab :tab-arr="tabArr" />
    <Search @search="search" />
    <div v-loading="loading">
      <InfoWrap
        v-if="orderData.length"
        :order-data="orderData"
        :pag="pag"
        @sizeChange="sizeChange"
        @currentChange="currentChange"
        @update="getOrderData"
      />
      <svg-icon v-else icon-class="empty-img" class="empty-img" />
    </div>

  </div>
</template>

<script>
import Search from './manage/search.vue'
import InfoWrap from './manage/InfoWrap.vue'
export default {
  components: {
    Search,
    InfoWrap
  },
  props: {

  },
  data() {
    return {
      tabArr: ['订单搜索'],
      orderData: [],
      form: {},
      pag: {
        page: 1,
        limit: 20,
        total: 0
      },
      loading: false
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getOrderData()
  },
  mounted() {

  },
  methods: {
    search(obj) {
      this.pag.page = 1
      this.form = obj
      this.getOrderData()
    },
    sizeChange(val) {
      this.pag.limit = val
      this.getOrderData()
    },
    currentChange(val) {
      this.pag.page = val
      this.getOrderData()
    },
    async getOrderData() {
      this.loading = true
      const params = this.setParams()
      const { err, res } = await this.$get('/admin/goods-order', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.loading = false
      this.orderData = res.data.list
      this.pag.total = res.data.total
    },
    setParams() {
      return {
        phone: this.form.phone,
        nickname: this.form.nickname,
        status: this.form.status,
        created_at: this.form.pay_at ? this.form.pay_at[0] + '|' + this.form.pay_at[1] : '',
        total_amount_fen: this.getPriceParams(),
        order_no: this.form.order_no,
        page: this.pag.page,
        limit: this.pag.limit
      }
    },
    getPriceParams() {
      if (this.form.minPrice || this.form.maxPrice) {
        return (this.form.minPrice || '') + '|' + (this.form.maxPrice || '')
      }
      return ''
    }
  }
}
</script>

<style scoped lang="scss">
.order-manage {
  .empty-img {
    display : block;
    width : 140px;
    height : 174px;
    margin : 100px auto;
  }
}

</style>
