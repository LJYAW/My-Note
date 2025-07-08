<!--
 * @Author: your name
 * @Date: 2021-10-27 16:49:06
 * @LastEditTime: 2021-11-18 13:43:33
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/user-manage/detail/index.vue
-->
<template>
  <div class="user-detail">
    <base-tab :tab-arr="tabArr" />
    <base-btn v-if="userMessage.status=='正常'" class="st-btn normal-btn" @click="changeStatus('禁用')">停用账号</base-btn>
    <base-btn v-else class="normal-btn" @click="changeStatus('正常')">启用账号</base-btn>
    <div class="user-message">
      <div class="avatar">
        <base-image :src="userMessage.pc_avatar_url" />
      </div>
      <div class="userinfo">
        <p><span>用户账号</span>{{ userMessage.phone }}</p>
        <p><span>消费金额</span>￥{{ (userMessage.total_amount_fen/100).toFixed(0) }}</p>
        <!-- <p><span>钱包余额</span>{{ userMessage.total_coins }}金币</p> -->
        <p><span>用户昵称</span>{{ userMessage.nickname||'无' }}</p>
        <p><span>设备信息</span>{{ userMessage.device||'无' }}</p>
        <p><span>注册日期</span>{{ userMessage.created_at }}</p>
        <p><span>最后活跃</span>{{ userMessage.last_login_at||'无' }}</p>
      </div>
    </div>
    <!-- 地址列表 -->
    <div class="table-wrap">
      <el-table :data="addressData">
        <el-table-column label="地址列表">
          <el-table-column prop="name" width="180" />
          <el-table-column prop="phone" width="180" />
          <el-table-column>
            <template slot-scope="scope">
              {{ scope.row.province }} {{ scope.row.city }} {{ scope.row.area }} {{ scope.row.street }}
              {{ scope.row.detail }}
            </template>
          </el-table-column>
        </el-table-column>
      </el-table>
    </div>
    <!-- 消费信息 -->
    <div class="table-wrap">
      <el-table :data="consumeData">
        <el-table-column label="消费信息">
          <el-table-column prop="pay_at" width="180" />
          <el-table-column prop="total_coins" width="180">
            <template slot-scope="scope">
              ￥{{ (scope.row.total_amount_fen/100).toFixed(0)||0 }}
            </template>
          </el-table-column>
          <el-table-column prop="num">
            <template slot-scope="scope">
              {{ scope.row.goods_num||0 }}件
            </template>
          </el-table-column>
          <el-table-column prop="id">
            <template slot-scope="scope">
              订单号：{{ scope.row.order_no }}
            </template>
          </el-table-column>
          <el-table-column>
            <template slot-scope="scope">
              <base-btn type="text" @click="toDetail(scope.row.id)">查看详情</base-btn>
            </template>
          </el-table-column>
        </el-table-column>
      </el-table>
    </div>
    <!-- 充值信息 -->
    <!-- <div class="table-wrap">
      <el-table :data="rechargeData">
        <el-table-column label="充值信息">
          <el-table-column prop="paid_at" width="180" />
          <el-table-column prop="total_coins" width="180">
            <template slot-scope="scope">
              ￥{{ scope.row.total_coins }}
            </template>
          </el-table-column>
          <el-table-column prop="type">
            <template slot-scope="scope">
              {{ scope.row.pay_type }}
            </template>
          </el-table-column>
          <el-table-column prop="order_no" width="240">
            <template slot-scope="scope">
              订单号：{{ scope.row.order_no }}
            </template>
          </el-table-column>
          <el-table-column>
            <template slot-scope="scope">
              <base-btn type="text" @click="toDetail(scope.row.id)">查看详情</base-btn>
            </template>
          </el-table-column>
        </el-table-column>
      </el-table>
    </div> -->
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
      tabArr: ['用户详情'],
      addressData: [],
      consumeData: [{
        date: '2021-19-19 15:43:32',
        price: 3321,
        num: 13,
        id: 392709136
      }],
      rechargeData: [{
        date: '2021-19-19 15:43:32',
        price: 3321,
        type: '百度支付',
        id: 392709136
      }],
      userMessage: {}
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getUserDetail()
    this.getAddressData()
    this.getConsumeData()
    // this.getRechargeData()
  },
  mounted() {

  },
  methods: {
    toDetail(id) {
      this.$router.push({
        path: '/order-detail',
        query: {
          id: id
        }
      })
    },
    async getUserDetail() {
      const params = {
        ids: this.$route.query.id
      }
      const { err, res } = await this.$get('/admin/users', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.userMessage = res.data.list[0]
    },
    async changeStatus(type) {
      const { err, res } = await this.$post(`/admin/users/${this.$route.query.id}/status`, { status: type })
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.getUserDetail()
    },
    // 获取地址列表
    async getAddressData() {
      const { err, res } = await this.$get(`/admin/users/${this.$route.query.id}/address`)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.addressData = res.data.list
    },
    async getConsumeData() {
      const { err, res } = await this.$get(`/admin/users/${this.$route.query.id}/consume`)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.consumeData = res.data.list
    },
    async getRechargeData() {
      const { err, res } = await this.$get(`/admin/users/${this.$route.query.id}/recharge`)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.rechargeData = res.data.list
    }
  }
}
</script>

<style scoped lang="scss">
.user-detail {
  position : relative;
  .st-btn {
    background : #F16B6B;
    border-color : #F16B6B ;
  }
  .normal-btn {
    position : absolute;
    top : 0;
    right : 0;
  }
  .user-message {
    display : flex;
    margin-top : 10px;
    margin-bottom: 20px;
    .avatar {
      margin-right : 30px;
      .el-image {
        width : 77px;
        height : 77px;
        background : #EEEEEE;
      }
    }
    .userinfo {
      display : flex;
      flex : 1;
      flex-wrap : wrap;
      p {
        width : 33%;
        margin-bottom : 22px;
        font-size : 11px;
        line-height : 11px;
        span {
          color : #A0A0A0;
          margin-right : 15px;
        }
      }
    }
  }
  .table-wrap {
    margin-bottom : 20px;
  }
  ::v-deep .el-table {
    thead {
      tr:last-child {
        display : none;
      }
    }
    td .cell {
      text-align : center;
    }
  }
}

</style>
