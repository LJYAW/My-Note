<!--
 * @Author: your name
 * @Date: 2021-08-18 15:55:45
 * @LastEditTime: 2021-10-22 14:09:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/pay/product.vue
-->
<template>
  <div class="product">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="能量点充值" name="1" />
      <el-tab-pane label="存储空间购买" name="2" />
    </el-tabs>
    <!-- <title-bar title="能量点充值" /> -->
    <div v-if="activeName === '1'" class="product-wrap">
      <div v-for="(item,index) in productList" :key="index" class="product-list">
        <Vue3dCard :angle="angle" :scale-number="scaleNumber">
          <base-image :src="require(`@/assets/images/pay/bg${index+1}.png`)" />

          <div class="product-detail border-detail">
            <div class="detail-wrap">
              <base-image :src="require(`@/assets/images/pay/title1.png`)" />
              <div class="num" :style="getColorStyle(index)">{{ item.Name }}</div>
              <base-btn class="btn" size="small" @click="buyPoint(index)">{{ '立即购买' }}</base-btn>
            </div>
          </div>
          <!-- <div v-if="index===1||index===2" class="tag">
            {{ index===1?'超值':'现在购买享好礼' }}
          </div> -->
        </Vue3dCard>
      </div>
    </div>
    <!-- <title-bar title="空间购买" /> -->
    <div v-else class="product-wrap">
      <div v-for="(item,index) in spaceList" :key="index" class="product-list">
        <Vue3dCard :angle="angle" :scale-number="scaleNumber">
          <base-image :src="require(`@/assets/images/pay/bg${index+1}.png`)" />

          <div class="product-detail border-detail">
            <div class="detail-wrap">
              <base-image :src="require(`@/assets/images/pay/title2.png`)" />
              <div class="num" :style="getColorStyle(index)">{{ item.text }}</div>
              <base-btn class="btn space-btn" size="small" @click="buy(index)">{{ '立即购买' }}</base-btn>
            </div>
          </div>
          <!-- <div v-if="index===1||index===2" class="tag">
            {{ index===1?'超值':'现在购买享好礼' }}
          </div> -->
        </Vue3dCard>
      </div>
    </div>
    <BaseDialog
      title="订单详情"
      :show.sync="dialogVisible"
      width="294px"
      class="dialog"
    >
      <OrderInfo @hideDialog="hideDialog" />
    </BaseDialog>
  </div>
</template>

<script>
import OrderInfo from './orderInfo.vue'
import Vue3dCard from 'vue-3d-card'
export default {
  components: {
    Vue3dCard,
    OrderInfo
  },
  props: {

  },
  data() {
    return {
      angle: 3,
      scaleNumber: 1.05,
      productList: [
        {
          num: 100
        },
        {
          num: 1000
        },
        {
          num: 10000
        },
        {
          num: 100000
        }
      ],
      spaceList: [
        {
          text: '1TB/1年'
        },
        {
          text: '5TB/1年'
        },
        {
          text: '10TB/1年'
        },
        {
          text: '100TB/1年'
        }
      ],
      dialogVisible: false,
      order: '',
      qr: '',
      activeName: '1'

    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.initProducts()
    this.activeName = this.$route.query.id || '1'
  },
  mounted() {

  },
  methods: {
    handleClick(tab, event) {
      this.activeName = tab.name
    },
    async initProducts() {
      const { err, res } = await this.$get('/products/')
      this.productList = err.filter(item => item.Status === 1)
    },
    getBorderStyle(index) {
      switch (index) {
        case 2:
          return 'border-color:#E0BD84'
        case 3:
          return 'border-color:#9198B3'
        default:
          return 'border-color:#bcc6d4'
      }
    },
    getColorStyle(index) {
      switch (index) {
        case 1:
          return 'background: linear-gradient(230deg, #51719B 0%, #244C79 50%, #51719B 100%);-webkit-background-clip: text;-webkit-text-fill-color: transparent;'
        case 2:
          return 'background: linear-gradient(230deg, #714D14 0%, #B58840 50%, #573D15 100%);-webkit-background-clip: text;-webkit-text-fill-color: transparent;'
        case 3:
          return 'background: linear-gradient(230deg, #454D6C 0%, #5A6383 52%, #454D6C 100%);-webkit-background-clip: text;-webkit-text-fill-color: transparent;'
        default:
          return 'background: linear-gradient(230deg, #085A6E 0%, #2795B0 50%, #085A6E 100%);-webkit-background-clip: text;-webkit-text-fill-color: transparent;'
      }
    },
    // 立即购买
    buy(index) {

    },
    buyPoint(index) {
      const data = this.productList[index]
      const tableData = [{
        name: data.Category,
        num: JSON.parse(data.PropertyDetails).energy,
        price: data.PriceFen / 100
      }]
      this.hideDialog(tableData, data.Id)
    },
    async hideDialog(data, id) {
      this.dialogVisible = false
      await this.getQr(id)
      const params = {
        data: data,
        order: this.order,
        qr: this.qr
      }
      this.$emit('changeComponent', 'PayQr', params)
    },
    async getQr(id) {
      const params = {
        product_id: id,
        product_num: 1,
        scene: 'web',
        type: '支付宝'
      }
      const { err, res } = await this.$post('/pay/buy-product', params)
      const { data } = res
      this.order = data.order_no
      this.qr = data.qr_code
    }
  }
}
</script>

<style scoped lang="scss">
.product-wrap {
  display: flex;
  margin-top: 30px;
  // margin-bottom: 30px;
  flex-wrap: wrap;
  // justify-content: space-between;

  .product-list {
    margin-right: 24px;
    margin-bottom: 24px;
    // flex: 1;
    position: relative;

    // &:last-child {
    //   margin-right: 0;
    // }

    .card {
      width: 100%;
      height: auto;
      background: #fff;
      border-radius: 10px;
      overflow: visible;

      &:hover {
        transform: scale(1);
      }

      ::v-deep .reflection {
        background: none!important;
      }

      .product-detail {
        border-radius: 0 0 10px 10px;
        padding: 1px;
        // background-image: linear-gradient(270deg, rgba(204, 213, 223, 1), rgba(188, 198, 212, 1));
        position: absolute;
        top: 0;

        // &.border-detail {
        //   padding: 0;
        //   background: none;
        //   border: 1px solid;
        // }

        .detail-wrap {
          border-radius: 0 0 10px 10px;
          // background: #fff;
          padding: 20px 20px;
          // height: 528px;
          display: flex;
          flex-direction: column;
          justify-content: space-between;

          .detail-item {
            display: flex;
            font-size: 18px;
            line-height: 18px;
            justify-content: space-between;
            margin-bottom: 40px;
            color: #404040;

            p {
              opacity: .6;

              &.active {
                opacity: 1;
              }

            }

            .special {
              color: #d6ac69;
            }
          }

          .num {
            margin-top: 10px;
            font-size: 32px;
            font-weight: bold;
            color: #085a6e;
            line-height: 32px;
            background: linear-gradient(230deg, #714d14 0%, #b58840 50%, #573d15 100%);
            background-clip: text;
            -webkit-text-fill-color: transparent;          }

          .btn {
            width: 100px;
            // height: 24px;
            // line-height: 24px;
            // background: linear-gradient(135deg, #ffdc92 0%, #ffdf94 100%);
            background: rgba(255,255,255,.6);
            border-radius: 4px;
            position: relative;
            z-index: 9;
            color: #79612c;
            border: 0;
            margin-top: 10px;
          }

          .space-btn {
            background: linear-gradient(135deg, #ffdc92 0%, #ffdf94 100%);
          }
        }
      }

      .tag {
        background: linear-gradient(225deg, #fe3a3f 0%, #ff6555 100%);
        border-radius: 8px 0px 8px 0px;
        font-size: 12px;
        line-height: 12px;
        padding: 5px;
        color: #fff;
        position: absolute;
        top: -9px;
        left: 20px;
        z-index: 9;
      }
    }
  }
}
</style>
