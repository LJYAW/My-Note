<!--
 * @Author: your name
 * @Date: 2021-10-22 19:30:09
 * @LastEditTime: 2021-11-05 18:11:37
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/order-manage/logistics/index.vue
-->
<template>
  <div class="logistics-message">
    <base-tab :tab-arr="tabArr" />
    <div v-loading="loading" class="logistics-main">
      <div class="logistics-wrap">
        <p class="express-name">
          <span>{{ wlInfo.tracking_company }}</span>
          {{ wlInfo.tracking_number }}
          <base-btn type="text" class="update-btn" @click="updateModal">修改</base-btn>
        </p>
        <Logistics :info-data="wlInfo" />
      </div>
      <Detail :order-detail="orderData" />
    </div>
    <base-dialog
      :show.sync="dialogVisible"
      width="500px"
      title="立即发货"
    >
      <Deliver :product-id="orderData.id" @close="close" />
    </base-dialog>
  </div>
</template>

<script>
import Logistics from '@/components/Logistics/index.vue'
import Detail from './order.vue'
import Deliver from '../components/deliver.vue'
export default {
  components: {
    Logistics,
    Detail,
    Deliver
  },
  props: {

  },
  data() {
    return {
      tabArr: ['物流信息'],
      wlInfo: {
        list: []
      },
      orderData: {},
      dialogVisible: false,
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
    async getOrderData() {
      this.loading = true
      const params = {
        ids: this.$route.query.id
      }
      const { err, res } = await this.$get('/admin/goods-order', params)
      this.loading = false
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.orderData = res.data.list[0]
      this.getLogisticsData()
    },
    async getLogisticsData() {
      this.wlInfo.tracking_company = this.orderData.tracking_company
      this.wlInfo.tracking_number = this.orderData.tracking_number
      this.wlInfo.addr = this.orderData.info_snapshot.user_address.province +
      this.orderData.info_snapshot.user_address.city + this.orderData.info_snapshot.user_address.area +
      (this.orderData.info_snapshot.user_address.street || '') + this.orderData.info_snapshot.user_address.detail
      const params = {
        code: this.orderData.tracking_code,
        number: this.orderData.tracking_number,
        phone: this.orderData.info_snapshot.user_address.phone
      }
      const { err, res } = await this.$get('/configs/kuaidi/query', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.wlInfo.list = res.data.list
    },
    updateModal() {
      this.dialogVisible = true
    },
    close() {
      this.dialogVisible = false
      this.getOrderData()
    }
  }
}
</script>

<style scoped lang="scss">
.logistics-message {
  .logistics-main {
    display : flex;
    .logistics-wrap {
      width : 338px;
      margin-right : 22px;
      border-radius : 4px;
      border : 1px solid #ECECEC;
      .express-name {
        padding : 16px 23px;
        background : #F8F9FA;
        border-radius : 4px 4px 0px 0px;
        font-size : 14px;
        line-height : 14px;
        color : #404404;
        span {
          margin-right : 10px;
        }
        .update-btn {
          float : right;
          padding : 0;
        }
      }
      ::v-deep .page {
        height : calc(100vh - 250px);
      }
    }
  }
}

</style>
