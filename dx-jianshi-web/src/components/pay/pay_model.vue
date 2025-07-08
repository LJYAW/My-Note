<!--
 * @Author: your name
 * @Date: 2020-11-10 17:35:44
 * @LastEditTime: 2020-12-16 17:38:38
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/pay_model.vue
-->
<template>
  <div :id="'isPayModel'">
    <div>
      <img src='@/assets/images/user_info/vip/zhifubao_logo.jpg' class='mr-10px' style="width:46px" />
      <span class='fz-12px fc-666 fz-18px'>支付宝</span>
    </div>

    <div
      class="d-flex justify-content-center videom-video-wrap pt-40px">

      <div class='d-flex flex-column align-items-center pt-50px'
        style="min-width:280px;min-height:280px;position: relative;">
        <div v-if="status_loading" style="width: 200px; height: 200px" class="d-flex justify-content-center align-items-center">
          <loading style="margin: 0 auto" />
        </div>
        <vue-qr v-else :text="qr_code" :size="200"></vue-qr>
        <div class="fz-14px fc-333 mt-10px">请使用支付宝扫描二维码支付
        </div>
      </div>

      <div class='flex-1'>
        <img src='@/assets/images/user_info/vip/zhifubao.png' />
      </div>
    </div>
  </div>
</template>
<script>
import vueQr from 'vue-qr'
export default {
  name: 'PayModel',
  props: {
    layerid: {
      //自动注入的layerid
      type: String,
      default: ''
    },
    iframeData: {
      //传递的数据
      type: Object,
      default: () => {
        return {}
      }
    },
    lydata: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      status_loading: true,
      qr_code: '',
      timer: null,
      order_no: ''
    }
  },

  methods: {
    getPayQr() {
      this.status_loading = true
      this.$post('pay/buy-product', this.lydata).then(res => {
        if (res.data.code == '0000') {
          this.qr_code = res.data.data.pay_info.qr_code
          this.order_no = res.data.data.order_no

          if (!this.timer) {
            this.timer = setInterval(() => {
              this.getSuccess(this.lydata)
            }, 1000)
          }
          this.status_loading = false
        } else {
          this.$message({
            type: 'error',
            message: res.data.msg
          })
        }
      })
    },
    getSuccess(iframeData) {
      this.$get('pay/find', { params: { order_no: this.order_no } }).then(res => {
        if (res.data.code == '0000') {
          let status = res.data.data.status
          if (status != '待支付') {
            this.clearTime()
          }
          if (status == '支付成功') {
            this.$message({
              type: 'success',
              message: '支付成功!'
            })
            this.$pay.buySuccess(iframeData)
            this.$layer.close(this.layerid)
          } else if (status == '支付失败') {
            this.$message({
              type: 'error',
              message: '支付失败!'
            })
          }
        } else {
          this.$message({
            type: 'error',
            message: res.data.msg
          })
          this.clearTime()
        }
      })
    },
    clearTime() {
      clearInterval(this.timer)
      this.timer = null
    }
  },
  components: { vueQr },

  created() {
    this.getPayQr()
  },
  beforeDestroy() {
    this.clearTime()
  }
}
</script>
<style lang='scss'>
#isPayModel {
  margin: 0 auto;
  padding-top: 20px;
  .modal-content {
    .modal-header {
      padding: 20px 20px 0 20px;
    }
    .modal-body {
      min-height: 454px;
      #qrcode {
        display: inline-block;
        img {
          background-color: #fff; //设置白色背景色
          padding: 6px; // 利用padding的特性，挤出白边
        }
      }
      .payLoading {
        width: 237px;
        height: 237px;
      }
    }
  }
}
</style>