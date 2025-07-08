<!--
 * @Author: your name
 * @Date: 2021-08-10 18:46:27
 * @LastEditTime: 2021-10-11 16:35:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/pay-qr/index.vue
-->
<template>
  <div class="pay-info">
    <div class="pay-info-wrap">
      <div v-if="pay_success" class="pay-success">
        <i class="el-icon-circle-check" />
        <div class="pay-time">
          <p class="message">订单支付成功！</p>
          <p>您已成功支付订单：{{ order }}，您可到
            <router-link to="/manage-center/order-info">订单管理</router-link>
            页面查询相关信息！
          </p>

        </div>
      </div>
      <div v-else class="pay-status">
        <div class="qr-code">
          <vue-qr :text="qr" :size="200" />
        </div>
        <div class="pay-time">
          <p class="message">订单提交成功，请尽快付款！</p>
          <p>您可以使用支付宝扫码付款<span class="division">|</span>请在<span class="time">{{ time }}内</span>完成付款</p>
          <div class="pay-type"><span>支付方式</span><svg-icon icon-class="zfb" class="pay-icon" />  </div>

        </div>
        <div class="pay-price">应付金额 <span class="price">{{ totalPrice }}</span>元</div>
      </div>
      <div class="pay-detail">
        <title-bar title="购买详情" />
        <el-table :data="tableData" border>
          <el-table-column label="购买项目" prop="name" />
          <el-table-column label="购买数量" prop="num" />
          <el-table-column label="价格" prop="price" />
        </el-table>
        <p v-if="tableData.length>1" class="price-count">合计价格：{{ totalPrice }}元</p>
      </div>
    </div>

  </div>
</template>

<script>
import vueQr from 'vue-qr'
var maxtime = 24 * 60 * 60 - 2
export default {
  components: {
    vueQr
  },
  props: {
    tableData: {
      type: Array,
      default: () => []
    },
    order: {
      type: String,
      default: ''
    },
    qr: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      // qr_code: 'xxx',
      // tableData: [{
      //   name: '存储空间',
      //   time: '100G/年',
      //   price: '¥5000.00'
      // }],
      pay_success: false,
      time: '23时59分59秒',
      timer: null,

      timerStatus: null
    }
  },
  computed: {
    totalPrice() {
      let price = 0
      this.tableData.forEach(element => {
        price += Number(element.price)
      })
      return price
    }
  },
  watch: {

  },
  destroyed() {
    clearInterval(this.timerStatus)
    clearInterval(this.timer)
  },
  created() {
    this.timer = setInterval(this.CountDown, 1000)
    if (this.order) {
      this.timerStatus = setInterval(this.getStatus, 2000)
    }
  },
  mounted() {

  },
  methods: {
    async getStatus() {
      const { err, res } = await this.$get('/pay/find', { order_no: this.order })
      const { data } = res
      if (data.status === '支付成功') {
        this.pay_success = true
        clearInterval(this.timerStatus)

        // TODO支付完成增加视频管家权限

        // await this.$store.dispatch('user/getUserInfo')
        // const router = this.$route.path
        // if (router === '/pay-qr') {
        //   this.router.push('/home')
        // }
      }
    },
    CountDown() {
      if (maxtime >= 0) {
        const time = parseInt(maxtime % 3600)
        const hour = Math.floor(maxtime / 3600)
        const minutes = Math.floor(time / 60)
        const seconds = Math.floor(time % 60)
        this.time = hour + '时' + minutes + '分' + seconds + '秒'
        --maxtime
      } else {
        clearInterval(this.timer)
        alert('时间到，结束!')
      }
    }
  }
}
</script>

<style scoped lang="scss">
.pay-info {
  flex: 1;
  padding: 50px;
  background: #eee;

  .pay-type {
    margin-top: 20px;
    display: flex;

    .pay-icon {
      font-size: 30px;
      margin-left: 10px;
    }
  }

  .pay-info-wrap {
    width: 756px;
    margin: 0 auto;

    .pay-time {
      font-size: 12px;

      .message {
        font-size: 18px;
        font-weight: 600;
        margin-bottom: 10px;
      }

      .division {
        color: #d8d8d8;
        opacity: .6;
        margin: 0 10px;
      }

      .time {
        color: #e52b11;
      }
    }

    .pay-status {
      display: flex;
      padding: 30px;
      background: #fff;
      margin: 30px 0;
      align-items: center;
      border-radius: 4px;

      .qr-code {
        width: 100px;
        height: 100px;
        margin-right: 30px;
      }

      .pay-price {
        font-size: 14px;
        margin-left: 80px;

        .price {
          font-size: 18px;
          color: #e52b11;
        }
      }
    }

    .pay-success {
      margin: 30px 0;
      display: flex;
      background: #fff;
      padding: 50px;
      align-items: center;
      border-radius: 4px;

      i {
        color: #59d194;
        font-size: 60px;
        margin-right: 30px;
      }

      a {
        color: #5675e8;
      }
    }

    .pay-detail {
      padding: 30px;
      background: #fff;
      border-radius: 4px;

      .el-table {
        margin-top: 30px;

        .el-table__body-wrapper {
          max-height: 257px;
          overflow-y: auto;
        }
      }

      .price-count {
        font-size: 14px;
        color: #404040;
        text-align: right;
        margin-top: 20px;
      }
    }

  }

}
</style>
