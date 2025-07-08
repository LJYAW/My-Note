<!--
 * @Author: your name
 * @Date: 2020-11-27 15:51:57
 * @LastEditTime: 2021-07-21 14:37:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/mobile_pay/index.vue
-->
<template>
  <div :class='[user_details.vip_detail&&user_details.vip_detail.name=="年卡"&&"year-mobile","mobile-pay"]'>
    <div class='banner'>
      <img src="../../assets/images/mobile/banner.png" alt="" class='banner-bg'>
      <div class='user-info'>
        <p class='title'>我的VIP卡</p>
        <div class='user-detail'>
          <div class='d-flex'>
            <img src="../../assets/images/mobile/touxiang.svg" alt="" class='touxiang'>
            <div class='d-flex flex-column'>
              <span>{{userInfo.nickname}}</span>
              <p>
                {{user_details.is_vip?'微剪VIP'+user_details.vip_detail.name:'您还不是VIP'}}</p>
            </div>
          </div>
          <div class='energy'>
            <p>| 账户能量值：<img v-if='user_details.vip_detail&&user_details.vip_detail.name=="年卡"'
                src="../../assets/images/mobile/nengliang.png" alt="">
              <img v-else src="../../assets/images/mobile/year_nengliang.png" alt="">
              {{user_details.energy + user_details.temp_energy}}</p>
          </div>
        </div>
      </div>
    </div>
    <loading v-if='loading' />
    <div class='product-list' v-else>
      <div class='d-flex'>
        <div v-for="item in product_list" :key='item.id' :class="['item',activeId==item.id&&'active']"
          @click='activeId=item.id,vip_name=item.name,price=item.price_fen*0.01'>
          <span class='logo' v-if='item.name=="年卡"'>推荐购买</span>
          <p class='text-center fw-blod'>{{item.name}}</p>
          <p class='text-center price'>¥<span>{{item.price_fen*0.01}}</span></p>
        </div>
        <div v-if='user_details.is_vip'>
          <div v-for="item in energy" :key='item.id' :class="['item',activeId==item.id&&'active']"
            @click='activeId=item.id,vip_name=item.name,price=item.price_fen*0.01'>
            <span class='logo' v-if='item.name=="年卡"'>推荐购买</span>
            <p class='text-center fw-blod'>{{item.name}}</p>
            <p class='text-center price'>¥<span>{{item.price_fen*0.01}}</span></p>
          </div>
        </div>
      </div>
      <div class='vip-detail'>
        <p class='title'>{{vip_name}}<span v-if='vip_name!="能量购买"'>VIP会员特权</span></p>
        <ul v-if='vip_name!="能量购买"' :class="['d-flex flex-wrap justify-content-between',vip_name=='年卡'&&'vip-year']">
          <li class='text-center'>
            <img v-if="vip_name=='年卡'" src="../../assets/images/mobile/year_img1.png" alt="">
            <img v-else src="../../assets/images/mobile/img_1.png" alt="">
            <p>高清无水印</p>
            <p class='desc'>视频不限量下载</p>
          </li>
          <li class='text-center'>
            <img v-if="vip_name=='年卡'" src="../../assets/images/mobile/year_img2.png" alt="">
            <img v-else src="../../assets/images/mobile/img_2.png" alt="">
            <p>能量值购买</p>
            <p class='desc'>提供充值购买（10能量起）</p>
          </li>
          <li class='text-center'>
            <img v-if="vip_name=='年卡'" src="../../assets/images/mobile/year_img3.png" alt="">
            <img v-else src="../../assets/images/mobile/img_3.png" alt="">
            <p>赠{{vip_name=='年卡'?'3600':'300'}}点能量</p>
            <p class='desc'>购买及赠{{vip_name=='年卡'?'3600':'300'}}点能量值</p>
          </li>
          <li class='text-center'>
            <img v-if="vip_name=='年卡'" src="../../assets/images/mobile/year_img4.png" alt="">
            <img v-else src="../../assets/images/mobile/img_4.png" alt="">
            <p>每日领{{vip_name=='年卡'?'30':'20'}}点能量</p>
            <p class='desc'>仅限当日使用</p>
          </li>
        </ul>
        <div v-else class='buy-energy'>
          <el-input v-model='energy_num' placeholder="输入能量数"></el-input>
          <div class='energy-price d-flex'>
            <p>×10点</p>
            <p>（￥{{energy_num*energy[0].price_fen|formatCash}}）</p>
          </div>
          <p>注：视频生成、仅限VIP使用</p>
        </div>
      </div>
      <!-- <el-button class='buy-btn' @click.native='pay'>立即续费</el-button> -->
      <div class='consult d-flex align-items-center'>
        <p>企业私有套餐，定制化合作需求</p>
        <el-button round>私有套餐咨询</el-button>
      </div>

    </div>

  </div>
</template>

<script>
import * as mobile from '@/utils/mobile.js'
export default {
  props: {},
  data() {
    return {
      userInfo: JSON.parse(localStorage.getItem('user_info')) || {},
      activeId: 0,
      price: 0,
      product_list: [],
      energy: {},
      loading: false,
      user_details: {},
      vip_name: '',
      energy_num: 0
    }
  },
  watch: {},
  methods: {
    pay() {
      if (this.user_details.vip_detail && this.user_details.vip_detail.name == '年卡' && this.vip_name == '月卡') {
        this.$alertMsg('会员有效期间内，只能购买平级或更高等级的会员套餐')
        return
      }
      if (this.vip_name == '能量购买' && !this.energy_num) {
        this.$alertMsg('请输入购买的能量值')
        return
      }
      this.$router.push({
        path: '/pay-qr',
        query: {
          product_id: this.activeId,
          product_num: this.activeId == '5' ? this.energy_num : 1,
          price: this.activeId == '5' ? this.getPrice(this.energy_num * this.energy[0].price_fen) : this.price
        }
      })
    },
    getPrice(num) {
      var number = num / 100 + ''
      return number.replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    },
    getUserInfo() {
      this.$store.dispatch('setUserDetails').then(res => {
        this.user_details = res
      })
    },
    //获取商品列表
    getProductList() {
      this.loading = true
      this.$get('pay/product-list').then(res => {
        if (res.data.code == '0000') {
          this.product_list = res.data.data[0].list
          this.energy = res.data.data[1].list
          this.activeId = this.product_list[0].id
          this.vip_name = this.product_list[0].name
          this.price = this.product_list[0].price_fen * 0.01
          // this.price = res.data.data[0].list[0].price_fen
          this.loading = false
        }
      })
    }
  },
  components: {},
  created() {},
  mounted() {
    mobile.setRem()
    this.getProductList()
    this.getUserInfo()
  }
}
</script>

<style scoped lang="scss">
.mobile-pay {
  min-height: 100vh;
  background: #fff;
  color: #6e6e6e;

  .banner {
    position: relative;
    .banner-bg {
      position: absolute;
      width: 100%;
    }
    .user-info {
      padding-top: 0.2rem;

      .title {
        color: #fff;
        text-align: center;
        font-size: 0.34rem;
        height: 0.48rem;
        line-height: 0.48rem;
        margin-bottom: 0.5rem;
      }
      .user-detail {
        width: 90%;
        border-radius: 0.1rem;
        padding: 0.2rem;
        background-image: url('../../assets/images/mobile/month_banner.png');
        margin: 0 auto;
        background-repeat: no-repeat;
        background-size: 100%;
        font-size: 0.16rem;
        position: relative;
        z-index: 9;
        .touxiang {
          display: inline-block;
          width: 1rem;
          margin-right: 0.32rem;
        }
        span {
          font-size: 0.36rem;
          line-height: 0.5rem;
          padding-top: 0.05rem;
        }
        p {
          font-size: 0.24rem;
          line-height: 0.33rem;
          margin-top: 0.02rem;
        }
        .energy {
          padding: 0.22rem 0 0.25rem 0.26rem;
          background: rgba(255, 255, 255, 0.3);
          margin-top: 0.5rem;
          border-radius: 0.1rem;
          img {
            width: 0.2rem;
          }
        }
      }
    }
  }
  .product-list {
    background: #fff;
    position: relative;
    padding: 0 5%;
    border-radius: 30px 30px 0px 0px;
    border: 1px solid rgba(255, 255, 255, 100);
    padding-top: 1.38rem;
    margin-top: -0.78rem;
    & > p {
      font-size: 0.14rem;
      margin-bottom: 0.1rem;
    }
    & > div {
      overflow-x: auto;
      &::-webkit-scrollbar {
        display: none;
      }
    }
    .item {
      position: relative;
      flex-shrink: 0;
      width: 2.2rem;
      border: 0.02rem solid #e9e9e9;
      margin-right: 0.19rem;
      margin-bottom: 0.3rem;
      padding: 0.6rem 0.4rem 0.45rem 0.4rem;
      border-radius: 0.06rem;
      &:nth-child(3) {
        margin-right: 0;
      }
      .fw-blod {
        font-family: SourceHanSansSC-bold;
        font-weight: 500;
        padding-bottom: 0.2rem;
        margin-bottom: 0.08rem;
        font-size: 0.3rem;
        color: #313131;
      }
      .el-input.fw-blod {
        padding-bottom: 0;
      }
      .price {
        font-size: 0.3rem;
        color: #d9a869;
        font-weight: bold;
        font-family: DIN;
        span {
          font-size: 0.48rem;
        }
      }
      /deep/input {
        border: none;
        height: 0.32rem;
        padding: 0 0.1rem;
        padding-bottom: 0.18rem;
        color: #101010;
        font-weight: bold;
        font-family: SourceHanSansSC-bold;
      }
    }
    .vip-detail {
      .title {
        font-size: 0.32rem;
        color: #000;
        margin-bottom: 0.3rem;
      }
      ul {
        margin-bottom: 0.28rem;
        li {
          width: 49.8%;
          padding: 0.44rem 0;
          background-repeat: no-repeat;
          background-size: 100% 100%;
          // margin-right: 0.02rem;
          margin-bottom: 0.02rem;
          &:first-child {
            background: url('../../assets/images/mobile/bg_2.png');
          }
          &:nth-child(2) {
            background: url('../../assets/images/mobile/bg_1.png');
          }
          &:nth-child(3) {
            background: url('../../assets/images/mobile/bg_3.png');
          }
          &:nth-child(4) {
            background: url('../../assets/images/mobile/bg_4.png');
          }
          img {
            height: 0.8rem;
            margin-bottom: 0.3rem;
          }
          p {
            font-size: 0.32rem;
            color: #313131;
            font-weight: bold;
          }
          .desc {
            font-size: 0.26rem;
            color: #666;
            margin-top: 0.24rem;
          }
        }
      }
      .vip-year {
        li {
          .desc {
            color: #bc8946;
          }
          &:first-child {
            background: url('../../assets/images/mobile/year_bg1.png');
          }
          &:nth-child(2) {
            background: url('../../assets/images/mobile/year_bg2.png');
          }
          &:nth-child(3) {
            background: url('../../assets/images/mobile/year_bg3.png');
          }
          &:nth-child(4) {
            background: url('../../assets/images/mobile/year_bg4.png');
          }
        }
      }
      .buy-energy {
        position: relative;
        font-size: 0.32rem;

        .energy-price {
          position: absolute;
          right: 0.2rem;
          top: 0.25rem;
          max-width: 40%;
          overflow: auto;
          color: #d9a869;
          font-weight: bold;
          p {
            flex-shrink: 0;
          }
          &::-webkit-scrollbar {
            display: none;
          }
        }
        /deep/.el-input {
          font-size: 0.32rem;
          input {
            height: 0.84rem;
            border: 0.02rem solid #979797;
            padding-left: 0.24rem;
            padding-right: 51%;
          }
        }
        & > p {
          font-size: 0.26rem;
          color: #a29f9f;
          margin-top: 0.28rem;
          margin-bottom: 2.6rem;
        }
      }
    }
    .buy-btn {
      font-size: 0.36rem;
      color: #313131;
      width: 100%;
      padding: 0.18rem 0;
      border: none;
      background-image: linear-gradient(to right, #f8dcb3, #d8a768);
      border-radius: 0.2rem;
    }
    .consult {
      padding: 0.18rem 0.32rem 0.34rem 0.4rem;
      margin-top: 0.28rem;
      background: url('../../assets/images/mobile/consult.png');
      background-repeat: no-repeat;
      background-size: 100%;
      margin-bottom: 0.7rem;
      p {
        font-size: 0.32rem;
        color: #b17439;
        line-height: 0.44rem;
        margin-right: 0.5rem;
      }
      /deep/button {
        height: 0.56rem;
        font-size: 0.26rem;
        background: #fff;
        color: #b17439;
        padding: 0.1rem 0.28rem;
        border: none;
      }
    }
  }
  .logo {
    font-size: 0.24rem;
    color: #fff;
    line-height: 0.33rem;
    padding: 0 0.14rem;
    background: url('../../assets/images/mobile/logo.png');
    background-repeat: no-repeat;
    background-size: 100% 100%;
    position: absolute;
    top: -0.01rem;
    left: -0.01rem;
  }
  .active {
    position: relative;
    border: 0.02rem solid #fd5352 !important;
    .logo {
      display: block !important;
    }
  }
}
.year-mobile {
  color: #b17439;
  .banner .user-info {
    .user-detail {
      background-image: url('../../assets/images/mobile/year_banner.png');
    }
  }
}
</style>

