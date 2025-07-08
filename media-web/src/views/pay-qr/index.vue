<!--
 * @Author: your name
 * @Date: 2021-08-10 18:46:27
 * @LastEditTime: 2021-10-15 19:08:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/pay-qr/index.vue
-->
<template>
  <!-- <PayQr :table-data="tableData" :qr="qr" :order="order" /> -->
  <div class="pay-info">
    <div class="pay-info-wrap">
      <div class="pay-detail">
        <title-bar title="购买详情" />
        <el-table :data="tableData" border>
          <el-table-column label="购买项目" prop="name" />
          <el-table-column label="购买数量" prop="num" />
          <el-table-column label="价格" prop="price">
            <template slot-scope="scope">
              <div>{{ scope.row.price }}能量点</div>
            </template>
          </el-table-column>
        </el-table>
        <p v-if="tableData.length>1" class="price-count">合计价格：{{ totalPrice }}能量点</p>
      </div>
    </div>
    <div class="pay-wrap">
      <span>账户余额：{{ companyInfo.energy }}能量点 </span>
      <router-link to="/manage-center/pay"><span class="recharge">立即充值</span></router-link>
      <el-button type="primary" size="small" :disabled="companyInfo.energy<totalPrice" @click="pay">立即支付</el-button>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import router from '../../router'

// import PayQr from '@/views/manage-center/pay/pay-qr/index.vue'
export default {
  components: {
    // PayQr
  },
  props: {

  },
  data() {
    return {
      tableData: [
        {
          name: '开通视频管家',
          num: '1TB/30天',
          price: '30'
        }
      ],
      qr: '',
      order: ''
    }
  },
  computed: {
    ...mapGetters(['companyInfo', 'roles']),

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
  created() {
    // this.getQr()
  },
  mounted() {

  },
  methods: {
    async pay() {
      const { err, res } = await this.$post('/user/startgj', { types: 1 })
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.$message.success('支付成功')
      await this.updateRouter()
      this.$router.push({
        path: '/home'
      })
    },
    async updateRouter() {
      await this.$store.dispatch('user/getUserInfo')
      await this.$store.dispatch('user/getDirInfo')
      await this.$store.dispatch('company/getCompanyInfo')
      const roles = this.$store.getters.roles
      const accessRoutes = await this.$store.dispatch('permission/generateRoutes', roles)
      router.addRoutes(accessRoutes)
    }
  }
}
</script>

<style scoped lang="scss">
.pay-info {
  flex: 1;
  padding: 50px;
  background: #eee;
  height: calc(100vh - 70px);

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

  .pay-wrap {
    width: 756px;
    margin: 0 auto;
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    text-align: right;
    color: #404040;

    .recharge {
      color: #5675e8;
      margin-left: 10px;
      margin-right: 20px;
      cursor: pointer;
    }

  }

}
</style>
