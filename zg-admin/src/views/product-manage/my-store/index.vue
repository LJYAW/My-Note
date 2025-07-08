<!--
 * @Author: your name
 * @Date: 2021-10-25 18:01:42
 * @LastEditTime: 2021-11-05 18:23:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/product-manage/my-store/index.vue
-->
<template>
  <div class="my-store">
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
        status: '已下架,待上架',
        sort: this.getSortParams(),
        on_at: this.form.date ? this.form.date.join('|') : null,
        price: this.getPriceParams(),
        page: this.pag.page,
        limit: this.pag.limit
      }
    },
    // 整合排序参数
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
    // 整合价格参数
    getPriceParams() {
      if (this.form.minPrice || this.form.maxPrice) {
        return (this.form.minPrice || '') + '|' + (this.form.maxPrice || '')
      }
      return ''
    },
    // 获取产品列表
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
.my-store {
  .empty-img {
    display : block;
    width : 140px;
    height : 174px;
    margin : 100px auto;
  }
}

</style>
