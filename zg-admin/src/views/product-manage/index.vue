<!--
 * @Author: your name
 * @Date: 2021-10-20 11:58:21
 * @LastEditTime: 2021-10-25 18:04:44
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/product-manage/index.vue
-->
<template>
  <div class="product-manage">
    <base-tab :tab-arr="tabArr" @changeTab="changeTab" />
    <SaleProduct v-if="showType=='出售中的产品'" />
    <MyStore v-if="showType=='我的仓库'" />
  </div>
</template>

<script>
import SaleProduct from './sale-product/index.vue'
import MyStore from './my-store/index.vue'
export default {
  components: {
    SaleProduct,
    MyStore
  },
  props: {

  },
  data() {
    return {
      tabArr: ['出售中的产品', '我的仓库'],
      showType: '出售中的产品'
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
    changeTab(val) {
      this.showType = val
    }
  }
}
</script>

<style scoped lang="scss">

</style>
