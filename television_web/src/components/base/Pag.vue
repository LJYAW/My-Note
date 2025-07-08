<!--
 * @Author: your name
 * @Date: 2021-01-15 14:57:38
 * @LastEditTime: 2021-07-20 17:11:07
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work_order_web/src/components/base/pagination.vue
-->
<!--
 * @Author: your name
 * @Date: 2021-01-15 14:31:14
 * @LastEditTime: 2021-01-15 14:50:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work_order_web/src/components/base/btn.vue
-->
<template>
  <el-pagination
    background
    class="pagination"
    layout="sizes, prev, pager, next"
    :total="total"
    :page-sizes="[10, 20, 50, 100]"
    :page-size="limit"
    :current-page.sync="curPage"
    v-bind="$attrs"
    v-on="$listeners"
  />

</template>

<script>
export default {
  name: 'BasePag',

  props: {
    total: {
      type: Number,
      default: 1000
    },
    limit: {
      type: Number,
      default: 5
    },
    page: {
      type: Number,
      default: 1
    }
  },

  data() {
    return {
      curPage: 1
    }
  },
  watch: {
    page: {
      handler(num) {
        this.curPage = num
      }
    }
  },
  methods: {
  }
}
</script>

<style scoped lang="scss">
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
