<!--
 * @Author: your name
 * @Date: 2021-10-22 16:06:35
 * @LastEditTime: 2021-12-09 11:15:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/order-manage/detail/index.vue
-->
<template>
  <div v-loading="loading" class="order-detail">
    <base-tab :tab-arr="tabArr" />
    <base-btn v-if="orderDetail.status==='待发货'" class="deliver-btn" @click="deliver">立即发货</base-btn>
    <div class="detail-wrap">
      <div class="order-base-wrap">
        <p>订单编号：{{ orderDetail.order_no }}</p>
        <p>订单日期：{{ orderDetail.created_at }}</p>
      </div>
      <div class="order-product-wrap">
        <div class="user-detail">
          <p><span class="title">收货人</span> <span>{{ orderDetail.info_snapshot?orderDetail.info_snapshot.user_address.name:'无' }}</span></p>
          <p><span class="title">联系电话</span> <span>{{ orderDetail.info_snapshot&&orderDetail.info_snapshot.user_address?orderDetail.info_snapshot.user_address.phone:'无' }}</span></p>
          <p><span class="title">收货地址</span> <span>{{ orderDetail.info_snapshot&&orderDetail.info_snapshot.user_address?orderDetail.info_snapshot.user_address.province:'' }}
            {{ orderDetail.info_snapshot&&orderDetail.info_snapshot.user_address?orderDetail.info_snapshot.user_address.city:'' }}
            {{ orderDetail.info_snapshot&&orderDetail.info_snapshot.user_address?orderDetail.info_snapshot.user_address.area:'' }}
            {{ orderDetail.info_snapshot&&orderDetail.info_snapshot.user_address?orderDetail.info_snapshot.user_address.street:'' }}
            {{ orderDetail.info_snapshot&&orderDetail.info_snapshot.user_address?orderDetail.info_snapshot.user_address.detail:'' }}</span>
          </p>
        </div>
        <div class="btn-wrap">
          <p>总计金额：{{ (orderDetail.total_amount_fen/100).toFixed(0) }}</p>
        </div>
        <div class="btn-wrap">
          <p>{{ orderDetail.status }}</p>
        </div>
      </div>
    </div>
    <div v-if="orderDetail.info_snapshot" class="product-wrap">
      <div v-for="product in orderDetail.info_snapshot.goods_list" :key="product.id" class="product-item">
        <base-image :src="product.main_img" />
        <div class="product-message">
          <p class="message name">{{ product.name }}</p>
          <p class="message">数量：{{ product.goods_num }}</p>
          <p class="message price">价格：￥{{ product.price }}</p>
          <p class="message number">{{ product.number }}</p>
        </div>
      </div>
    </div>
    <base-dialog
      :show.sync="dialogVisible"
      width="500px"
      title="立即发货"
    >
      <Deliver :product-id="$route.query.id" @close="close" />
    </base-dialog>
  </div>
</template>

<script>
import Deliver from '../components/deliver.vue'
export default {
  components: {
    Deliver
  },
  props: {

  },
  data() {
    return {
      tabArr: ['订单详情'],
      orderDetail: {},
      dialogVisible: false,
      loading: false
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getDetailData()
  },
  mounted() {

  },
  methods: {
    deliver() {
      this.dialogVisible = true
    },
    async getDetailData() {
      this.loading = true
      const params = {
        ids: this.$route.query.id
      }
      const { err, res } = await this.$get('/admin/goods-order', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.loading = false
      this.orderDetail = res.data.list[0]
    },
    close() {
      this.dialogVisible = false
      this.getDetailData()
    }
  }
}
</script>

<style scoped lang="scss">
.order-detail {
  position : relative;
  .deliver-btn {
    position : absolute;
    right : 0;
    top : 0;
  }
  .detail-wrap {
    border-radius : 4px;
    border : 1px solid #ECECEC;
    .order-base-wrap {
      padding : 16px 23px;
      font-size : 14px;
      line-height : 14px;
      background : #F8F9FA;
      border-radius : 4px 4px 0px 0px;
      p {
        display : inline-block;
        margin-right : 50px;
        &.order-status {
          float : right;
          margin-right : 0;
        }
      }
    }
    .order-product-wrap {
      display : flex;
      .user-detail {
        display : flex;
        flex : 1;
        flex-direction : column;
        font-size : 14px;
        line-height : 19px;
        padding : 24px;
        border-top : 1px solid #ECECEC;

        p {
          margin-bottom : 17px;
          span {
            display: inline-block;
            &.title{
              width: 80px;
            }
          }

        }
      }
      .btn-wrap {
        width : auto;
        padding : 0 65px;
        font-size : 14px;
        line-height : 19px;
        display : flex;
        align-items : center;
        border : 1px solid #ECECEC;
        border-right : none;
        border-bottom : none;
        &.el-button {
          font-size : 14px;
          padding : 0;
        }
        .close-btn {
          color : #F16B6B;
          margin-left : 20px;
        }
      }
    }
  }
  .product-wrap {
    margin-top : 20px;
    border-bottom : 1px solid #ECECEC;
    .product-item {
      border : 1px solid #ECECEC;
      border-bottom : none;
      padding : 20px;
      display : flex;
      .el-image {
        width : 75px;
        height : 75px;
        background : #EEEEEE;
        margin-right : 20px;
      }
      .product-message {
        display : flex;
        align-items : center;
        .message {
          font-size : 14px;
          color : #2F3A48;
          display : inline-block;
          margin-right : 75px;
          &.price {
            font-size : 12px;
          }
          &.number {
            font-size : 12px;
            opacity : .4;
          }
          &.name{
            width: 200px;
            margin-right: 30px;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
          }
        }
      }
    }
  }
}

</style>
