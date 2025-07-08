<!--
 * @Author: your name
 * @Date: 2020-11-20 17:28:22
 * @LastEditTime: 2020-12-03 11:31:50
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /weijian_cms_web/src/page/user/models/recharge.vue
-->
<template>
  <div class="recharge_wrap">
    <div class="recharge_item">
      <span>账户能量:</span>
      <p>200 点</p>
    </div>
    <div class="flex-1 d-flex mt-20px recharge_item">
      <span>充值能量:</span>
      <el-input v-model="nums" placeholder="请输入充值能量" style="width:200px"></el-input>
      <p class="mt-5px ml-5px">点</p>
    </div>
    <div class="recharge_item">
      <span>账户VIP:</span>
      <el-select v-model="vip" clearable placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </div>
    <div class="btns">
      <el-button type="primary" round size="small" @click="determine()">确定</el-button>
    </div>
  </div>
</template>
<script>
export default {
  name: '',
  props: {
    layerid: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      nums: '',
      vip: '无',
      options: [
        {
          label: '无',
          value: '无'
        },
        {
          label: '月卡',
          value: '月卡'
        },
        {
          label: '年卡',
          value: '年卡'
        }
      ]
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    determine() {
      this.$layer.close(this.layerid)
    }
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
.recharge_wrap {
  width: 100%;
  padding: 10px 20px;
  .btns {
    display: block;
    text-align: center;
    margin-top: 20px;
  }
  .recharge_item {
    display: flex;
    margin-top: 20px;
    span {
      display: block;
      min-width: 70px;
    }
    /deep/ .el-input {
      margin-top: 0;
      /deep/ .el-input__inner {
        height: 30px;
      }
    }
  }
}
</style>
