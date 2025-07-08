<!--
 * @Author: your name
 * @Date: 2020-11-12 17:23:23
 * @LastEditTime: 2020-11-24 11:35:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/pay.vue
-->
<template>
  <div class='px-40px pt-16px pb-45px pay-model' id='pay-model'>
    <p class='mb-12px'>充值账户：<span class='fw-blod'>{{changePhone(userInfo.phone)}}</span></p>
    <p class='mb-12px'>账户余额：<span class='fw-blod'>100 min</span></p>
    <div class='d-flex justify-content-between product-list mb-30px mt-26px'>
      <div v-for="item in product_list" :key='item.id'
        :class="['px-15px pt-17px flex-1 mr-20px',activeId==item.id&&'active']"
        @click='activeId=item.id,price_count=item.price'>
        <p class='pb-20px text-center fw-blod mb-10px'>{{item.time}}<span>min</span></p>
        <p class='text-center fz-12px'>{{item.price}}元</p>
        <div class='logo'>
          <span>
            <img src="../../assets/images/user_info/vip/check.svg" alt="">
          </span>
        </div>
      </div>
      <div :class="['px-15px py-17px flex-1 mr-0px',activeId==4&&'active']"
        @click='activeId=4,price_count=time*price'>
        <el-input placeholder="请输入" v-model.trim='time'
          class='text-center mb-10px fw-blod' type="text" @input="test()"></el-input>
        <p class='text-center fz-12px'><span class='price'>{{time*price}}</span>元</p>
        <div class='logo'>
          <span>
            <img src="../../assets/images/user_info/vip/check.svg" alt="">
          </span>
        </div>
      </div>
    </div>
    <p class='mb-20px'>支付方式:</p>
    <div class='active mb-30px px-15px py-12px pr-29px zhifu'>
      <img src="../../assets/images/user_info/vip/zhifubao.svg" alt="">
      <span class='ml-11px'>支付宝</span>
      <div class='logo'>
        <span>
          <img src="../../assets/images/user_info/vip/check.svg" alt="">
        </span>
      </div>
    </div>
    <div class='text-center qr-code'>
      <el-button round @click.native='getQr' v-if='!showQr'>获取支付二维码</el-button>
      <div v-if='showQr'>
        <vue-qr :text="qr_code" :size="200"></vue-qr>
        <p>请使用支付宝扫码支付<span class='fw-blod'>{{price_count}}</span>元</p>
      </div>
    </div>

  </div>
</template>

<script>
import vueQr from 'vue-qr'
import encrypt from './encrypt'
import paySuccess from './pay_success'
export default {
  props: {
    layerid: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      time: '',
      activeId: 0,
      price: 10,
      price_count: 100,
      qr_code: 'xxx',
      product_list: [
        {
          id: 0,
          price: 100,
          time: 10
        },
        {
          id: 1,
          price: 500,
          time: 50
        },
        {
          id: 2,
          price: 1000,
          time: 100
        },
        {
          id: 3,
          price: 5000,
          time: 500
        }
      ],
      showQr: false,
      userInfo: JSON.parse(localStorage.getItem('user_info')) || {}
    }
  },
  computed: {},
  watch: {
    activeId(val) {
      if (val == 4) {
        this.showQr = false
      }
    }
  },
  methods: {
    close() {
      // this.$layer.close(this.layerid)
    },
    changePhone(val) {
      return encrypt(val)
    },
    test() {
      this.time = this.time.replace(/[^\d]/g, '')
    },
    getQr() {
      if (this.activeId == 4 && !this.time) {
        this.$alertMsg('请输入时长')
        return
      }
      this.showQr = true
    }
  },
  components: { vueQr, paySuccess },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.pay-model {
  color: #101010;
  .fw-blod {
    font-weight: bold;
  }
}
.product-list {
  & > div {
    border: 1px solid rgba(187, 187, 187, 100);
    border-radius: 3px;
    position: relative;
    .fw-blod {
      border-bottom: 1px solid rgba(234, 234, 234, 100);
    }
    /deep/ input {
      border: none;
      height: 34px;
      padding: 0 10px;
      padding-bottom: 18px;
      color: #101010;
      font-weight: bold;
      font-family: SourceHanSansSC-bold;
    }
    .price {
      display: inline-block;
      max-width: 55px;
      overflow-x: auto;
      vertical-align: -2px;
      &::-webkit-scrollbar {
        display: none;
      }
    }
  }
  .active {
    border: 1px solid #ed0000;
  }
}
.zhifu {
  span {
    vertical-align: -2px;
  }
}
.logo {
  display: inline-block;
  color: #fff;
  width: 0;
  height: 0;
  border-top: 20px solid transparent;
  border-bottom: 20px solid transparent;
  border-left: 20px solid #ed0000;
  transform: rotate(-45deg);
  position: absolute;
  right: -3px;
  top: -13px;
  display: none;

  span {
    color: #fff;
    position: absolute;
    top: -8px;
    right: 5px;
    img {
      max-width: 14px;
      transform: rotate(45deg);
    }
  }
}
.qr-code {
  button {
    background: #c51b18;
    color: #fff;
  }
}
.active {
  position: relative;
  display: inline-block;
  border: 1px solid #ed0000;
  border-radius: 3px;

  .logo {
    display: block !important;
  }
}
</style>

