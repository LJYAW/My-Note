<!--
 * @Author: your name
 * @Date: 2021-10-25 17:31:22
 * @LastEditTime: 2021-11-05 14:54:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/product-manage/sale-product/search.vue
-->
<template>
  <div class="search-wrap">
    <div>
      <el-input
        v-model="form.name"
        clearable
        size="mini"
        :maxlength="30"
        @keyup.enter.native="search"
      >
        <i slot="suffix" class="el-input__icon el-icon-search" @click="search" />
      </el-input>
      <el-select v-model="form.inventory" size="mini" clearable placeholder="剩余库存">
        <el-option label="剩余库存升序" value="asc" />
        <el-option label="剩余库存降序" value="desc" />
      </el-select>
      <!-- <el-select v-model="form.on_at" size="mini" clearable placeholder="上架时间">
        <el-option label="上架时间从近到远" value="desc" />
        <el-option label="上架时间从远到近" value="asc" />
      </el-select> -->
      <el-select v-model="form.sales_volume" size="mini" clearable placeholder="销量">
        <el-option label="销量从高到低" value="desc" />
        <el-option label="销量从低到高" value="asc" />
      </el-select>
      <div class="price">
        <div class="price-wrap">
          <el-input-number v-model="form.minPrice" :controls="false" size="mini" :max="999999" placeholder="价格" />
          至
          <el-input-number v-model="form.maxPrice" :controls="false" size="mini" :max="999999" placeholder="价格" />
        </div>
        <base-btn class="confirm-btn" @click="search">确认</base-btn>
      </div>
    </div>
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
        name: '',
        minPrice: undefined,
        maxPrice: undefined,
        inventory: null,
        on_at: null,
        sales_volume: null
      }
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
    }
  }
}
</script>

<style scoped lang="scss">
.search-wrap {
  display : flex;
  justify-content : space-between;
  .el-input {
    width : 365px;
    margin-right : 25px;
    margin-bottom : 10px;
  }
  ::v-deep .el-select {
    margin-right : 10px;
    input {
      height : 30px;
    }
  }
  .price {
    display : inline-flex;
    margin-bottom : 10px;

    .price-wrap {
      display : inline-block;
      border-radius : 4px 0px 0px 4px;
      border : 1px solid rgba(151,151,151,.4);
      .el-input-number {
        width : 65px;
        ::v-deep input {
          border : none;
        }
      }
    }
    .confirm-btn {
      height : 30px;
      border-radius : 0px 4px 4px 0px;
    }
  }
  .new-btn {
    height : 30px;
  }
}

</style>
