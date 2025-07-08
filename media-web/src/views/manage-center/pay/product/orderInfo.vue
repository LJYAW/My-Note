<!--
 * @Author: your name
 * @Date: 2021-08-10 17:47:49
 * @LastEditTime: 2021-09-27 09:51:37
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/pay/orderInfo.vue
-->
<template>
  <div class="order-info">
    <img src="@/assets/images/pay/order.png" alt="">
    <div class="info">
      <p><span>产品名称</span><span class="message">视频存储空间</span></p>

      <p><span>空间大小</span>
        <span class="message">
          <span class="cut" @click="cuts('kj')">-</span>
          <span class="num">{{ num.kj }}</span>
          <!-- <input v-model="num.kj" type="number" class="num"> -->
          <span class="add" @click="add('kj')">+</span>
          <span class="suffix">TB</span>
        </span>
      </p>
      <p><span>有效时长</span>
        <span class="message">
          <span class="cut" @click="cuts('sj')">-</span>
          <span class="num">{{ num.sj }}</span>
          <!-- <input v-model="num.sj" type="number" class="num"> -->
          <span class="add" @click="add('sj')">+</span>
          <span class="suffix">月</span>
        </span>
      </p>
      <p><span>订单金额</span><span class="message">¥{{ (num.kj*50)+(num.sj*100) }}</span></p>
    </div>
    <div class="agreement">
      <el-checkbox v-model="agree">同意</el-checkbox>
      <router-link to="">《天脉视迅付费服务协议》</router-link>
    </div>
    <base-btn class="pay-btn" @click="pay">扫码支付</base-btn>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      agree: false,
      num: {
        kj: 1,
        sj: 1
      }
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
    pay() {
      if (!this.agree) {
        this.$message({
          type: 'warning',
          message: '请阅读并同意《天脉视迅付费服务协议》'
        })
        return
      }
      const tableData = [
        {
          name: '存储空间',
          num: this.num.kj,
          price: `${this.num.kj * 50}`
        },
        {
          name: '使用期限',
          num: this.num.sj,
          price: `${this.num.sj * 100}`
        }
      ]
      this.$emit('hideDialog', tableData)
      // this.$router.push({
      //   path: 'pay-qr'
      // })
    },
    cuts: function(item) {
      if (this.num[item] <= 1) return
      this.num[item]--
    },
    add: function(item) {
      this.num[item]++
    }
  }
}
</script>

<style scoped lang="scss">
.order-info {

  img {
    display: block;
    width: 90px;
    margin: 0 auto;
  }

  .info {
    margin-bottom: 22px;

    p {
      margin-top: 30px;
      color: #a0a0a0;
      display: flex;
      justify-content: space-between;

      .message {
        display: flex;
        align-items: center;
        justify-content: center;
        color: #404040;
      }

      .cut,
      .add {
        display: inline-block;
        width: 18px;
        height: 18px;
        line-height: 14px;
        text-align: center;
        border-radius: 2px;
        opacity: .24;
        border: 1px solid #404040;
        cursor: pointer;
      }

      .suffix {
        display: inline-block;
        min-width: 20px;
        margin-left: 10px;
        text-align: right;
      }

      .num {
        text-align: center;
        min-width: 20px;
        max-width: 20px;
        margin: 0px 10px;
      }
    }
  }

  .agreement {
    padding: 15px 8px;
    background: #f7f8f9;
    border-radius: 4px;
    font-size: 12px;
    margin-bottom: 30px;

    ::v-deep .el-checkbox__label {
      font-size: 12px;
    }

    a {
      color: #436ce7;
    }

  }

  .pay-btn {
    display: block;
    width: 100%;
  }
}

input[type=number] {
  appearance: textfield;
}

input[type=number]::-webkit-inner-spin-button,
input[type=number]::-webkit-outer-spin-button {
  appearance: none;
  margin: 0;
}
</style>
