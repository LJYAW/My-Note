<!--
 * @Author: your name
 * @Date: 2021-10-28 17:40:52
 * @LastEditTime: 2021-11-18 10:07:33
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/order-manage/logistics/order.vue
-->
<template>
  <div class="order-wrap">
    <div class="detail-wrap">
      <div class="order-base-wrap">
        <p>订单编号：{{ orderDetail.order_no }}</p>
        <p>订单日期：{{ orderDetail.created_at }}</p>
      </div>
      <div class="order-product-wrap">
        <div class="user-detail">
          <p><span class="title">收货人</span> <span>{{ orderDetail.info_snapshot?orderDetail.info_snapshot.user.nickname:'无' }}</span></p>
          <p><span class="title">联系电话</span> <span>{{ orderDetail.info_snapshot?orderDetail.info_snapshot.user_address.phone:'无' }}</span></p>
          <p><span class="title">收货地址</span> <span>{{ orderDetail.info_snapshot?orderDetail.info_snapshot.user_address.province:'' }}
            {{ orderDetail.info_snapshot?orderDetail.info_snapshot.user_address.city:'' }}
            {{ orderDetail.info_snapshot?orderDetail.info_snapshot.user_address.area:'' }}
            {{ orderDetail.info_snapshot?orderDetail.info_snapshot.user_address.street:'' }}
            {{ orderDetail.info_snapshot?orderDetail.info_snapshot.user_address.detail:'' }}</span>
          </p>
        </div>
        <div class="btn-wrap">
          <p>总计金额：{{ (orderDetail.total_amount_fen/100).toFixed(0) }}</p>
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
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    orderDetail: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {

    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.order-wrap {
  flex : 1;
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
        padding : 23px;
        border-top : 1px solid #ECECEC;

        p {
          margin-bottom : 15px;
          span {
            display : inline-block;
            &.title {
              width : 80px;
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
          font-size : 18px;
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
          // width: 100px;
          &.price {
            font-size : 12px;
          }
          &.number {
            font-size : 11px;
            opacity : .4;
          }
          &.name {
            width : 180px;
            margin-right : 30px;
            overflow : hidden;
            white-space : nowrap;
            text-overflow : ellipsis;
          }
        }
      }
    }
  }
}

</style>
