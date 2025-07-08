<!--
 * @Author: zzx
 * @Date: 2020-07-23 15:02:33
 * @LastEditTime: 2021-07-27 19:58:38
 * @FilePath: /weijian_web/src/views/user_info/weijian_vip/index.vue
-->
<template>
  <div class="weijian-vip pb-110px">
    <div class="content-bg">
      <img src="../../../assets/images/user_info/vip/banner.png" alt="" />
    </div>

    <loading v-if="loading" />

    <div v-else>
      <div class="container content px-0px mb-40px">
        <div class="d-flex justify-content-between">
          <div v-for="(item, index) in vip_list" :key="index">
            <el-card shadow="hover" class="h-100 vip-item">
              <div :class="['vip-type pt-45px', item.name == '年卡' && 'vip-year']">
                <div class="text-center">
                  <h5 class="mb-15px text-center fz-28px">{{ item.name }}</h5>
                  <div class="text-center fz-48px mt-32px vip-price">
                    {{ item.price_fen | formatCash }}
                    <span class="fz-16px ml-8px">元</span>
                    <div class="old-price fz-28px">
                      {{ item.name == '年卡' ? '4999' : '499' }}
                      <em class="fz-10px">元</em>
                      <hr />
                    </div>
                    <span class="logo fz-12px">会员专享</span>
                  </div>
                </div>
                <span class="fz-16px pos-buy" v-if="item.name == '年卡'">推荐购买</span>
                <img :src="require(`@/assets/images/user_info/vip/vip_${index + 1}.png`)" class="w-100 h-100" />
              </div>

              <div class="px-32px py-32px vip-detail fz-16px">
                <ul class="card-des">
                  <li>高清无水印视频不限量下载</li>
                  <li>提供能量值充值购买（10能量起）</li>
                  <li>
                    购买即赠
                    <span class="energy">{{ item.property_details.energy }}点</span>
                    能量
                    <span class="tag">赠</span>
                  </li>
                  <li>
                    每日可领取
                    <span class="energy">{{ index ? '30' : '20' }}点</span>
                    能量（仅限当日使用)
                    <span class="tag">赠</span>
                  </li>
                  <li class="mt-40px fz-12px fc-999">ps：能量用于智能创作生成视频，每成功生成一个视频消耗10点能量</li>
                </ul>

                <div class="text-center mb-10px">
                  <el-button type="primary" round :disabled="btn_status_loading" size="mini" @click="buyNow(item)" class="fz-14px">立即购买</el-button>
                </div>
              </div>
            </el-card>
          </div>
        </div>
      </div>

      <div class="bag container d-flex justify-content-between" v-if="user_details.is_vip">
        <el-row :gutter="24" class="w-100">
          <el-col :span="14">
            <div class="">
              <p class="text-center mb-40px title"><span class="px-34px fz-20px">能量购买</span></p>
              <div class="input-wrap fz-16px fc-999">
                <span class="fc-333">购买能量数</span>
                <el-input v-model.number.trim="video_num" class="mx-7px" type="text" @input="changeInput" maxlength="4"></el-input>
                <span class="mr-16px">×10点</span>
                <span>
                  （￥
                  <em class="fz-28px">{{ (single_video.price_fen * video_num) | formatCash }}</em>
                  ）元
                </span>
                <div class="ml-16px">
                  <span>单价{{ single_video.price_fen * 0.01 }}元/10点</span>
                </div>
                <p class="mt-35px fz-12px mb-45px">注：能量值10点起购</p>
                <el-button type="primary" round :disabled="btn_status_loading" size="mini" @click="buyNow(single_video)" class="fz-16px">立即充值</el-button>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>

      <div class="container mt-30px  text-center mt-65px custom-content">
        <img src="../../../assets/images/user_info/vip/custom.png" alt="" />
        <div class="ml-220px">
          <p class="fz-32px mb-15px">
            企业私有套餐，
            <br />
            定制化合作需求
          </p>
          <!-- <el-button type="primary" @click="goToApplication" round>联系我们</el-button> -->
        </div>
      </div>
    </div>

    <!-- 购买二维码 弹框 -->
    <!-- <payModel v-if="modal_show_name == 'isPayModel'" @close="Modalclose" ref="payModel" :qr_code='qr_code' />
    <payModel v-if="modal_show_name == 'isPaySuccess'" @close="Modalclose" /> -->
  </div>
</template>

<script>
import payModel from '@/components/pay/pay_model'
import paySuccess from '@/components/pay/pay_success'

export default {
  name: 'vip',
  props: {},
  data() {
    return {
      btn_status_loading: false,
      vip_list: [],
      video_num: 1,
      single_video: {},
      modal_show_name: '',
      qr_code: '',
      order_no: '',
      timer: null,
      loading: false,
      buyClickable: true
    }
  },
  components: { payModel, paySuccess },
  computed: {
    user_details() {
      return this.$store.state.user_details
    }
  },
  watch: {},
  methods: {
    goToApplication() {
      window.open('https://cloud.weijian.video/application')
    },
    // 限制只能输入 正整数
    changeInput() {
      var pattern = /^[1-9][0-9]*$/ // 正整数的正则表达式
      // 不符合正整数时
      if (!pattern.test(this.video_num)) {
        // input 框绑定的内容为空
        this.video_num = ''
      }
    },
    getProductList() {
      this.loading = true
      this.$get('pay/product-list')
        .then(res => {
          if (res.data.code == '0000') {
            this.vip_list = res.data.data[0].list
            this.single_video = res.data.data[1].list[0]
          } else {
            this.$message({
              type: 'error',
              message: res.data.msg
            })
          }
        })
        .finally(() => {
          this.loading = false
        })
    },
    buyNow(item) {
      let obj = {
        type: '支付宝',
        scene: 'web',
        product_id: item.id,
        product_num: item.id == 5 ? this.video_num : 1,
        purchase_details: item
      }
      this.btn_status_loading = true
      this.$post('pay/buy-product', obj).then(res => {
        if (res.data.code == '0000') {
          this.$pay.buy(obj)
        } else {
          this.$alertMsg(res.data.msg)
        }
        this.btn_status_loading = false
      })
    },
    getSuccess() {
      this.$get('pay/find', { params: { order_no: this.order_no } }).then(res => {
        if (res.data.code == '0000') {
          if (res.data.data.status != '待支付') {
            this.clearTime()
          }
          if (res.data.data.status == '支付成功') {
            this.$message({
              type: 'success',
              message: '支付成功!'
            })
            this.Modalclose()
            this.showPaySuccess()
          } else if (res.data.data.status == '支付失败') {
            this.$message({
              type: 'error',
              message: '支付失败!'
            })
          }
        }
      })
    },
    clearTime() {
      clearInterval(this.timer)
      this.timer = null
    },
    Modalclose() {
      this.clearTime()
      this.modal_show_name = ''
    },
    showPaySuccess() {
      this.$nextTick(() => {
        this.modal_show_name = 'isPaySuccess'
        this.$nextTick(() => {
          this.$store.commit('modalShow', 'isPaySuccess')
        })
      })
    }
  },
  created() {},
  mounted() {
    this.getProductList()
  },
  beforeDestroy() {
    this.clearTime()
  }
}
</script>

<style scoped lang="scss">
.weijian-vip {
  position: relative;
  background: #fff;
  button {
    background: $c;
    border-color: $c;
  }
  /deep/ .el-card__body {
    padding: 0;
  }
  @media screen and (max-width: 1200px) and (min-width: 768px) {
    .content-bg {
      max-width: 1200px;
      min-width: 1200px;
    }
  }
  .content {
    margin-top: -200px;
    min-height: 500px;
    .flex-1:last-child {
      margin-right: 0;
    }
    .el-card {
      border: none;
      border-radius: 10px;
      overflow: visible;
      max-width: 580px;
      min-width: 580px;
      // margin: 0 30px;
    }
    .vip-item {
      transition: all 0.25s ease-in;
      border: 1px solid #dedede;
      &:hover {
        transform: translateY(-20px);
        box-shadow: 0px 10px 10px 0px rgba(0, 0, 0, 0.1);
      }
    }
    .vip-type {
      height: 235px;
      position: relative;
      // overflow: hidden;
      .vip-price {
        color: #333;
        display: inline-block;
        position: relative;
        .old-price {
          color: #5f5f5f;
          margin-left: 14px;
          position: absolute;
          right: -76px;
          bottom: 0;
          opacity: 0.5;
          hr {
            position: absolute;
            width: 100%;
            top: 0px;
            left: 0;
            border: 1px solid #5f5f5f;
          }
        }
        .logo {
          position: absolute;
          top: -25px;
          right: -55px;
          color: #fff;
          width: 73px;
          padding: 6px 0px;
          border-radius: 13px 13px 13px 3px;
          background: linear-gradient(270deg, #f1564f -110%, $c 211%);
        }
      }
      h5 {
        color: #404040;
      }
      img {
        position: absolute;
        top: 0;
        object-fit: cover;
        border-radius: 10px 10px 0 0;
      }
      .pos-buy {
        display: inline-block;
        position: absolute;
        right: -1px;
        top: -28px;
        z-index: 9;
        padding: 11px 21px;
        background: linear-gradient(273deg, #333333 1%, #4a4a4a 100%);
        border-radius: 22px 22px 0px 22px;
        color: #fff;
      }
      div {
        position: relative;
        z-index: 9;
        color: #fff;
      }
    }
    .vip-year {
      h5,
      .vip-price {
        color: #864505 !important;
        .old-price {
          right: -95px;
        }
      }
    }
    .vip-detail {
      .card-des {
        color: #333333;
        padding-bottom: 40px;
        // font-family: PingFang SC, PingFang SC-Medium;
        margin-top: 10px;
        li {
          margin-bottom: 16px;
          position: relative;
          .energy {
            color: #b3773d;
          }
          .tag {
            font-size: 12px;
            color: #fff;
            position: absolute;
            right: 0;
            width: 24px;
            height: 17px;
            line-height: 17px;
            text-align: center;
            background: linear-gradient(90deg, #f1564f, $c);
            border-radius: 6px;
          }
        }
      }
      button {
        display: inline-block;
        width: 200px;
        border-radius: 4px;
        box-shadow: 0px 3px 6px 0px #ffbcba;
        padding: 12px 0;
      }
    }
  }
  .bag {
    border-radius: 10px;
    background: #fff;
    padding: 34px 20px;
    border: 1px solid #dedede;
    .el-col {
      margin: 0 auto;
      float: none;
      p {
        span {
          display: inline-block;
          background: url('../../../assets/images/user_info/vip/buy.png');
          background-size: 100%;
          color: #b3773d;
          background-repeat: no-repeat;
        }
      }
      .input-wrap {
        div {
          display: inline-block;
        }
        em {
          color: #b3773d;
        }
        /deep/ .el-input {
          width: auto;
          input {
            width: 185px;
            border: none;
            border-bottom: 1px solid #dedede;
            font-size: 20px;
            font-family: PingFang SC, PingFang SC-Bold;
            font-weight: 700;
            text-align: center;
            height: 25px;
            color: #333333;
          }
        }
        button {
          border-radius: 0;
          padding: 15px 40px;
          display: block;
          margin: 0 auto;
        }
      }
    }
  }
  .custom-content {
    img {
      display: inline-block;
      width: 400px;
      vertical-align: -50px;
    }
    div {
      display: inline-block;
      text-align: left;
      p {
        font-family: PingFang SC, PingFang SC-Semibold;
        font-weight: 600;
        text-align: left;
        color: #333333;
        line-height: 60px;
        letter-spacing: 1px;
      }
      button {
        border-radius: 0;
        padding: 13px 40px;
        background: #333;
      }
    }
  }
}
</style>
