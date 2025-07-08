<!--
 * @Author: your name
 * @Date: 2021-10-21 11:10:01
 * @LastEditTime: 2021-12-03 14:34:57
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/order-manage/InfoWrap.vue
-->
<template>
  <div class="order-info-wrap">
    <div class="order-info-content">
      <div v-for="item in orderData" :key="item.id" class="order-item">
        <div class="order-base-wrap">
          <p>订单编号：{{ item.order_no }}</p>
          <p>订单日期：{{ item.created_at }}</p>
          <p>用户账号：{{ item.info_snapshot.user.phone }}</p>
          <p class="order-status">订单状态：{{ item.status }}</p>
        </div>
        <div class="order-product-wrap">
          <div class="product-wrap">
            <div v-for="product in item.info_snapshot.goods_list" :key="product.id" class="product-item">
              <base-image :src="product.main_img" />
              <div class="product-message">
                <div>
                  <p class="message name">{{ product.name }}</p>
                  <p class="message">数量：{{ product.goods_num }}</p>
                  <p class="message">价格：￥{{ product.price }}</p>
                </div>
                <p class="number">{{ product.number }}</p>
              </div>
            </div>
          </div>
          <div class="btn-wrap">
            <p>总计金额：{{ (item.total_amount_fen/100).toFixed(0) }}</p>
          </div>
          <div class="btn-wrap">
            <base-btn type="text" @click="routerGo('/order-detail',item.id)">订单详情</base-btn>
          </div>
          <div class="btn-wrap close-wrap">
            <base-btn v-if="item.status=='待发货'" type="text" @click="deliver(item.id)">立即发货</base-btn>
            <base-btn v-if="item.status=='运输中'||item.status=='已完成'" type="text" @click="routerGo('/logistics-message',item.id)">查看物流</base-btn>
            <base-btn v-if="item.status=='待付款'" type="text" class="close-btn" @click="closeOrder(item)">关闭订单</base-btn>
          </div>
        </div>
      </div>
    </div>
    <base-pag
      :page="pag.page"
      :limit="pag.limit"
      :total="pag.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <base-dialog
      :show.sync="dialogVisible"
      width="500px"
      title="立即发货"
    >
      <Deliver :product-id="productId" @close="close" />
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
    orderData: {
      type: Array,
      default() {
        return []
      }
    },
    pag: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      productId: null
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
    //   发货
    deliver(id) {
      this.dialogVisible = true
      this.productId = id
    },
    close() {
      this.dialogVisible = false
      this.$emit('update')
    },
    routerGo(url, id) {
      this.$router.push({
        path: url,
        query: {
          id: id
        }
      })
    },
    // checkLog(obj) {
    //   this.routerGo('/logistics-message')
    //   // localStorage.setItem('orderData', JSON.stringify(obj))
    // },
    handleSizeChange(val) {
      this.$emit('sizeChange', val)
    },
    handleCurrentChange(val) {
      this.$emit('currentChange', val)
    },
    // 关闭订单
    closeOrder(obj) {
      this.$confirm('确认要关闭此订单吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.confirmClose(obj)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async confirmClose(obj) {
      const params = {
        status: '已取消',
        tracking: {
          tracking_company: obj.tracking_company,
          tracking_code: obj.tracking_code,
          tracking_number: obj.tracking_number
        }
      }
      const { err, res } = await this.$put(`/admin/goods-order/${obj.id}/status`, params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.$emit('update')
    }
  }
}
</script>

<style scoped lang="scss">
.order-info-wrap {
  margin-top : 30px;
  margin-bottom : 20px;
  .order-info-content {
    height : calc(100vh - 420px);
    overflow-y : auto;
  }
  .order-item {
    border-radius : 4px;
    border : 1px solid #ECECEC;
    margin-top : 20px;
    .order-base-wrap {
      padding : 17px 22px;
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
      .product-wrap {
        flex : 1;
        .product-item {
          display : flex;
          padding : 20px;
          border-top : 1px solid #ECECEC;
          .el-image {
            width : 75px;
            height : 75px;
            background : #EEEEEE;
            margin-right : 20px;
          }
          .product-message {
            display : flex;
            flex : 1;
            justify-content : space-between;
            .message {
              font-size : 14px;
              line-height : 14px;
              color : #2F3A48;
              margin-bottom : 10px;
              &.name {
                margin-bottom : 24px;
              }
            }
            .number {
              font-size : 11px;
              color : #2F3A48;
              opacity : .4;
            }
          }
        }
      }
      .btn-wrap {
        padding : 0 65px;
        font-size : 14px;
        line-height : 19px;
        display : flex;
        flex-wrap : wrap;
        align-items : center;
        border : 1px solid #ECECEC;
        .el-button {
          font-size : 14px;
          padding : 0;
        }
        &.close-wrap {
          width : 280px;
          justify-content : center;
        }
        .close-btn {
          color : #F16B6B;
          margin-left : 20px;
        }
      }
    }
  }
}

</style>
