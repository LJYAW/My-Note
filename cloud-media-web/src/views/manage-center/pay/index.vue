<!--
 * @Author: your name
 * @Date: 2021-08-03 14:46:10
 * @LastEditTime: 2021-10-11 19:10:08
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/pay/index.vue
-->
<template>
  <div class="pay-wrap">
    <components
      :is="componentName"
      :table-data="tableData"
      :qr="qr"
      :order="order"
      @changeComponent="changeComponent"
    />
    <!-- <Product /> -->
  </div>
</template>

<script>
import Product from './product/index.vue'
import PayQr from './pay-qr/index.vue'
export default {
  components: {
    Product,
    PayQr
  },
  props: {

  },
  data() {
    return {
      componentName: 'Product',
      tableData: [],
      qr: '',
      order: ''
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
    changeComponent(val, params) {
      this.componentName = val
      this.tableData = params.data
      this.order = params.order
      this.qr = params.qr
    }
  }
}
</script>

<style scoped lang="scss">

</style>
