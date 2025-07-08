<!--
 * @Author: your name
 * @Date: 2021-12-08 15:21:43
 * @LastEditTime: 2021-12-08 18:45:52
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /zhongu-app-admin/src/views/auction/details.vue
-->
<template>

  <div v-loading="loading" class="auctio-details">
    <div class="order-details">
      <div class="wrap block">
        <el-image :src="goodsInfo.main_img" width="30" height="100" />
        <div>{{ goodsInfo.name }}</div>
      </div>
      <div class="text block" style="min-width: 270px">
        竞拍结束时间:  {{ goodsInfo.end_at }}
      </div>
      <div class="text block">
        产品ID:  {{ goodsInfo.goods_id }}
      </div>
      <div v-if="dataDetails.goods_auction_bid_list" class="text block">
        竞拍人数:  {{ dataDetails.goods_auction_bid_list.length }}
      </div>
      <div class="text block">
        起拍价格:  {{ goodsInfo.price }}
      </div>
      <div class="btn-wrap block">
        <base-btn :disabled="status !== '竞拍中'" type="text" @click="showComDialog">修改拍卖信息</base-btn>
      </div>
    </div>

    <el-table
      :data="tableData"
      border
      height="calc(100vh - 360px)"
    >
      <el-table-column prop="phone" label="用户账号" width="180" />
      <el-table-column prop="nickname" label="用户信息" width="180" />
      <el-table-column prop="created_at" label="出价时间" />
      <el-table-column prop="bid_coins" label="出价金额" />
    </el-table>

    <base-dialog
      :show.sync="dialogVisible"
      width="400px"
      title="修改竞拍信息"
    >
      <el-form ref="ruleForm" :model="form" :inline="true" label-width="100px">
        <el-form-item label="竞拍ID:" prop="goods_auction_id">
          <el-input v-model="form.goods_auction_id" :disabled="true" />
        </el-form-item>
        <el-form-item label="竞拍结束时间:" prop="end_at">
          <el-date-picker
            v-model="form.end_at"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            format="yyyy-MM-dd HH:mm:ss"
            :picker-options="pickerOptionsStart"
            placeholder="选择日期时间"
          />
        </el-form-item>
        <el-form-item label="封顶价格:" prop="max_price">
          <el-input
            v-model="form.max_price"
            type="Number"
            oninput="if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"
            max-length="9"
          />
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <base-btn plain @click="dialogVisible = false">取消</base-btn>
        <base-btn class="deliver-btn" @click="changeInfo">确定</base-btn>
      </div>
    </base-dialog>

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
      form: {
        goods_auction_id: '',
        end_at: '',
        max_price: ''
      },
      dataDetails: {},
      // 限制开始时间
      pickerOptionsStart: {
        disabledDate(time) {
          return time.getTime() < new Date(new Date().toLocaleDateString()).getTime()
        }
      },
      status: '',
      dialogVisible: false,
      loading: true,
      tableData: [],
      goodsInfo: {}
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getAuctionData()
  },
  mounted() {

  },
  methods: {
    showComDialog(item) {
      console.log('🚀 ~ file: details.vue ~ line 102 ~ showComDialog ~ item', item)
      this.dialogVisible = true
      const date = new Date().getTime()
      this.form = {
        goods_auction_id: this.dataDetails.goods_auction_id,
        end_at: this.goodsInfo.end_at,
        max_price: this.goodsInfo.max_price
      }
    },
    async changeInfo() {
      let maxCoins = this.goodsInfo.price
      this.tableData.forEach(item => {
        item.bid_coins > maxCoins && (maxCoins = item.bid_coins)
      })

      const { res, err } = await this.$post('/admin/goods-auction/update-end-time-and-max-price', this.form)
      if (err) {
        this.$message.error(err.msg || '出错啦')
      } else {
        this.$message.success(res.msg || '出错啦')
        this.dialogVisible = false
        this.getAuctionData()
      }
    },
    async getAuctionData() {
      this.loading = true
      const id = this.$route.query.id
      const { res, err } = await this.$get('/amdin/goods-auction/detail', { goods_auction_id: id })
      this.dataDetails = res.data
      this.goodsInfo = res.data.goods_info
      this.tableData = res.data.goods_auction_bid_list
      this.status = res.data.status

      console.log('🚀 ~ file: details.vue ~ line 44 ~ getAuctionData ~ res, err', res, err)
      this.loading = false
    }
  }
}
</script>

<style scoped lang="scss">
.order-details {
  display: flex;
  align-items: center;
  border: 1px solid #ECECEC;
  margin-bottom: 30px;

  .el-image {
    width: 100px;
    height: 100px;
    margin-right: 20px;
  }
  .wrap {
    display: flex;
    align-items: center;
    min-width: 300px;
  }
  .block {
    height: 140px;
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0 20px;
    border-right: 1px solid #ECECEC;
  }
}
</style>
