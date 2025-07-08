<!--
 * @Author: your name
 * @Date: 2021-10-20 17:45:25
 * @LastEditTime: 2021-12-07 12:05:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/order-manage/search.vue
-->
<template>
  <div class="search-wrap">
    <base-form-presend label="用户账号">
      <el-input v-model="form.phone" placeholder="请输入用户账号" size="mini" clearable :maxlength="30" />
    </base-form-presend>
    <base-form-presend label="用户昵称">
      <el-input v-model="form.nickname" placeholder="请输入用户昵称" size="mini" clearable :maxlength="30" />
    </base-form-presend><br>
    <base-form-presend label="订单编号">
      <el-input v-model="form.order_no" placeholder="请输入订单编号" size="mini" clearable :maxlength="30" />
    </base-form-presend>
    <base-form-presend label="订单日期">
      <el-date-picker
        v-model="form.pay_at"
        format="yyyy-MM-dd HH:mm:ss"
        value-format="yyyy-MM-dd HH:mm:ss"
        type="datetimerange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        size="mini"
        clearable
      />
    </base-form-presend>
    <base-form-presend label="订单金额">
      <el-input-number v-model="form.minPrice" :controls="false" size="mini" :max="999999" />
      至
      <el-input-number v-model="form.maxPrice" :controls="false" size="mini" :max="999999" />
    </base-form-presend>
    <base-form-presend label="订单状态">
      <el-select v-model="form.status" size="mini" clearable>
        <el-option v-for="item in status" :key="item" :label="item" :value="item" />
      </el-select>
    </base-form-presend>
    <div class="btn-wrap">
      <base-btn @click="search">查询订单</base-btn>
      <base-btn type="info" @click="clearData">清空信息</base-btn>
      <base-btn @click="exportExt">导出待发货订单</base-btn>
    </div>
  </div>
</template>

<script>
import downloadFile from '@/utils/download'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      form: {
        phone: null,
        nickname: null,
        order_no: null,
        pay_at: '',
        minPrice: undefined,
        maxPrice: undefined,
        status: null
      },
      status: ['待付款', '待发货', '运输中', '已完成', '已取消', '已退款']
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
    search() {
      this.$emit('search', this.form)
    },
    clearData() {
      for (var key in this.form) {
        this.form[key] = null
        this.form.minPrice = undefined
        this.form.maxPrice = undefined
      }
      this.search()
    },
    async exportExt() {
      // downloadFile('/admin/goods-order/export')
      this.$axios({
        url: '/admin/goods-order/export',
        method: 'GET',
        responseType: 'blob' // important
      }).then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', 'file.xlsx')
        document.body.appendChild(link)
        link.click()
      })
    }
  }
}
</script>

<style scoped lang="scss">
.search-wrap {
  ::v-deep .form-presend {
    margin-right : 30px;
    margin-bottom : 10px;
    .form-label {
      height : 14px;
      font-size : 12px;
      font-weight : 400;
      line-height : 12px;
      padding-right : 10px;
    }
    .el-input {
      width : auto;
    }
    .el-input-number {
      width : 65px;
    }
    .el-select {
      width : 100px;
    }
    input, .el-input__inner {
      border : none;
      &::placeholder {
        color : rgba(64,64,64,.4);
      }
    }
    .el-date-editor {
      width : 300px;
      padding-right : 0;
      .el-icon-time {
        display : none;
      }
      .el-range-input {
        width : 42%;
      }
    }
  }
  .btn-wrap {
    margin-top : 10px;
    .el-button--info {
      color : #505050;
    }
  }
}

</style>
