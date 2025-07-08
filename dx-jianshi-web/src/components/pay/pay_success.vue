<!--
 * @Author: your name
 * @Date: 2020-11-12 18:32:06
 * @LastEditTime: 2020-12-16 17:39:24
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/pay_success.vue
-->
<template>
  <div id="paySuccess">
    <div class="title">
      <i class="iconfont iconxuanzhong"></i>
      充值成功
    </div>
    <ul class="des" v-if="!status_loading">
      <li>充值项目：{{ lydata.purchase_details.category }} {{ lydata.purchase_details.name}}</li>
      <li>支付金额：{{ lydata.product_num | formatCash }} 元（支付宝支付）</li>
    </ul>
    <div>
      <el-button type="lingt" size="small" style="width: 100px" round @click="cancel">确认</el-button>
      <el-button @click="goToPersonal" type="primary" size="small" style="width: 100px" round>个人中心</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'buySuccess',
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
      order_type: '',
      amount: '',
      status_loading: true
    }
  },
  computed: {
    userInfo() {
      return this.$store.state.user_details
    }
  },
  watch: {},
  methods: {
    getUserInfo() {
      this.status_loading = true
      this.$store.dispatch('setUserDetails').then(() => {
        this.status_loading = false
      })
    },
    getProductList() {
      this.$get('pay/product-list').then(res => {
        if (res.data.code == '0000') {
          let vip_list = res.data.data[0].list
          let single_video = res.data.data[1].list[0]
          this.order_type = vip_list.find(item => item.id === this.lydata.product_id)
        } else {
          this.$message({
            type: 'error',
            message: res.data.msg
          })
        }
      })
    },
    cancel() {
      this.getUserInfo()
      this.$layer.close(this.layerid)
    },
    goToPersonal() {
      this.$router.push({
        path: '/user-info/ai-created'
      })
      this.getUserInfo()
      this.$layer.close(this.layerid)
    }
  },
  components: {},
  created() {
    this.getProductList()
    this.getUserInfo()
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
#paySuccess {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  .iconxuanzhong {
    font-size: 40px;
    color: #67c23a;
  }
  .title {
    font-size: 28px;
    margin: 30px 0 70px 0;
  }
  .des {
    margin-bottom: 46px;
    font-size: 16px;
    li {
      margin-bottom: 13px;
    }
  }
}
</style>

