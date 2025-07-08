<!--
 * @Author: your name
 * @Date: 2020-11-27 18:18:21
 * @LastEditTime: 2020-12-11 17:52:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/mobile_pay/pay_qr.vue
-->
<template>
  <div class='pay-qr text-center'>
    <p class='title'>
      <img src="../../assets/images/mobile/zhifu.png" alt="">
      支付宝支付 <span>{{this.$route.query.price}}</span> 元
    </p>
    <div class='qr-content'>
      <vue-qr :text="qr_code" :size="200"></vue-qr>
    </div>
    <p class='bottom'>请将二维码截图保存至手机相册<br />然后支付宝扫一扫打开即可扫码完成充值</p>
  </div>
</template>

<script>
import vueQr from 'vue-qr'
import * as mobile from '@/utils/mobile.js'
export default {
  props: {},
  data() {
    return {
      qr_code: 'XXX'
    }
  },
  computed: {},
  watch: {},
  methods: {
    getQr() {
      let arr = {
        type: '支付宝',
        scene: 'web',
        product_id: this.$route.query.product_id,
        product_num: this.$route.query.product_num
      }
      this.$post('pay/buy-product', arr).then(res => {
        if (res.data.code == '0000') {
          this.qr_code = res.data.data.pay_info.qr_code
        } else {
          this.$message({
            type: 'error',
            message: res.data.msg
          })
        }
      })
    }
  },
  components: { vueQr },
  created() {},
  mounted() {
    mobile.setRem()
    this.getQr()
  }
}
</script>

<style scoped lang="scss">
.pay-qr {
  height: 100vh;
  background: #fff;
  padding-top: 17.3vh;
  .title {
    font-size: 0.28rem;
    img {
      display: inline-block;
      width: 0.4rem;
      margin-right: 0.03rem;
    }
    span {
      font-weight: 500;
    }
  }
  .qr-content {
    width: 60%;
    padding-top: 0.14rem;
    padding-bottom: 0.2rem;
    margin: 0 auto;
    img {
      width: 100%;
    }
  }
  .bottom {
    font-size: 0.24rem;
    color: #999;
    line-height: 0.4rem;
  }
}
</style>
