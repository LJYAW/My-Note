<!--
 * @Author: your name
 * @Date: 2021-08-18 15:55:45
 * @LastEditTime: 2021-08-18 16:09:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/pay/product.vue
-->
<template>
  <div class="product">
    <title-bar title="套餐购买" />
    <div class="product-wrap">
      <div v-for="(item,index) in productList" :key="index" class="product-list">
        <Vue3dCard :angle="angle" :scale-number="scaleNumber">
          <base-image :src="require(`@/assets/images/pay/${index+1}.png`)" />
          <div class="product-detail border-detail" :style="getBorderStyle(index)">
            <div class="detail-wrap">
              <div>
                <div class="detail-item">
                  <p :class="[index!==0&&'active']">基础功能</p>
                  <p :class="[index!==0&&'active']">视频剪辑 添加水印 帧搜索</p>
                </div>
                <div class="detail-item">
                  <p class="active">存储空间</p>
                  <p class="active">{{ item.storage }}</p>
                </div>
                <div class="detail-item">
                  <p>视频分析</p>
                  <p :class="[(index===1||index===2)&&'special','active']">{{ item.hour }}</p>
                </div>
                <div class="detail-item">
                  <p>分享流量包</p>
                  <p :class="[(index===1||index===2)&&'special','active']">{{ item.flow }}</p>
                </div>
                <div class="detail-item">
                  <p>视频创作</p>
                  <p :class="[(index===1||index===2)&&'special','active']">{{ item.create_num }}</p>
                </div>
                <div class="detail-item">
                  <p>子账号</p>
                  <p>{{ item.account }}</p>
                </div>
                <div v-if="item.price" class="detail-item">
                  <p>特惠价</p>
                  <p>{{ item.price }}</p>
                </div>
              </div>
              <base-btn class="btn" size="big" @click="buy(index)">{{ index===3?'敬请期待':'立即购买' }}</base-btn>
            </div>
          </div>
          <div v-if="index===1||index===2" class="tag">
            {{ index===1?'超值':'现在购买享好礼' }}
          </div>
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
      productList: [{
        storage: '1000GB',
        hour: '100小时',
        flow: '2000GB',
        create_num: '100条',
        account: '10个',
        price: '¥9888.00'
      },
      {
        storage: '1000GB',
        hour: '100小时',
        flow: '2000GB',
        create_num: '100条',
        account: '10个',
        price: '¥9888.00'
      },
      {
        storage: '1000GB',
        hour: '100小时',
        flow: '2000GB',
        create_num: '100条',
        account: '10个',
        price: '¥9888.00'
      },
      {
        storage: '♾️',
        hour: '♾️',
        flow: '♾️',
        create_num: '♾️',
        account: '♾️'
      }],
      dialogVisible: false
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
    // 立即购买
    buy(index) {
      if (index === 3) return
      this.dialogVisible = true
    },
    hideDialog() {
      this.dialogVisible = false
      this.$emit('changeComponent', 'PayQr')
    }
  }
}
</script>

<style scoped lang="scss">
.product-wrap {
  display: flex;
  margin-top: 30px;

  .product-list {
    margin-right: 24px;
    flex: 1;
    position: relative;

    &:last-child {
      margin-right: 0;
    }

    .card {
      width: 100%;
      height: auto;
      background: #fff;
      border-radius: 10px;
      overflow: visible;

      ::v-deep .reflection {
        background: none!important;
      }

      .product-detail {
        border-radius: 0 0 10px 10px;
        padding: 1px;
        background-image: linear-gradient(270deg, rgba(204, 213, 223, 1), rgba(188, 198, 212, 1));

        &.border-detail {
          padding: 0;
          background: none;
          border: 1px solid;
        }

        .detail-wrap {
          border-radius: 0 0 10px 10px;
          background: #fff;
          padding: 40px 20px;
          height: 528px;
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

          .btn {
            width: 100%;
            position: relative;
            z-index: 9;
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
