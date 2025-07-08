<!--
 * @Author: your name
 * @Date: 2021-10-25 16:44:21
 * @LastEditTime: 2021-11-06 14:08:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/product-manage/sale-product/index.vue
-->
<template>
  <div class="sale-product">
    <Search @search="search" />
    <List
      v-if="productData.length"
      :product-data="productData"
      :pag="pag"
      @sizeChange="sizeChange"
      @currentChange="currentChange"
    />
    <svg-icon v-else icon-class="empty-img" class="empty-img" />
  </div>
</template>

<script>
import Search from './search.vue'
import List from './list.vue'
export default {
  components: {
    Search,
    List
  },
  props: {

  },
  data() {
    return {
      productData: [],
      form: {},
      pag: {
        page: 1,
        limit: 20,
        total: 0
      }
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getGoodsData()
  },
  mounted() {

  },
  methods: {
    search(obj) {
      this.pag.page = 1
      this.form = obj
      this.getGoodsData()
    },
    setParams() {
      return {
        name: this.form.name,
        type: this.form.type,
        status: '已上架',
        sort: this.getSortParams(),
        price: this.getPriceParams(),
        page: this.pag.page,
        limit: this.pag.limit
      }
    },
    getSortParams() {
      const arr = ['inventory', 'on_at', 'sales_volume']
      const sortArr = []
      arr.forEach((item) => {
        if (this.form[item]) {
          sortArr.push(`${item},${this.form[item]}`)
        }
      })
      return sortArr.join('|')
    },
    getPriceParams() {
      if (this.form.minPrice || this.form.maxPrice) {
        return (this.form.minPrice || '') + '|' + (this.form.maxPrice || '')
      }
      return ''
    },
    async getGoodsData() {
      const params = this.setParams()
      const { err, res } = await this.$get('/admin/goods', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.productData = res.data.list
      this.pag.total = res.data.total
    },
    sizeChange(val) {
      this.pag.limit = val
      this.getGoodsData()
    },
    currentChange(val) {
      this.pag.page = val
      this.getGoodsData()
    }
  }
}
</script>

<style scoped lang="scss">
.sale-product {
  .empty-img {
    display : block;
    width : 140px;
    height : 174px;
    margin : 100px auto;
  }
}

</style>
