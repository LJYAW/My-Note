<!--
 * @Author: your name
 * @Date: 2021-10-25 17:33:23
 * @LastEditTime: 2021-11-05 14:55:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/product-manage/sale-product/List.vue
-->
<template>
  <div class="product-list-wrap">
    <div class="product-list-content">
      <div v-for="item in productData" :key="item.id" class="product-item">
        <div class="message-wrap product-message">
          <base-image :src="item.main_img" />
          <p>{{ item.name }}</p>
        </div>
        <div class="message-wrap">
          <p>产品ID：{{ item.id }}</p>
        </div>
        <div class="message-wrap">
          <p>价格：￥{{ item.price }}</p>
        </div>
        <div class="message-wrap">
          <p>库存数量：{{ item.inventory }}</p>
        </div>
        <div class="message-wrap btn-wrap">
          <base-btn type="text" @click="toEdit(item.id)">编辑产品</base-btn>
          <!-- <base-btn type="text" class="del-btn" @click="changeStatus(item.id)">下架产品</base-btn> -->
        </div>
      </div>
    </div>
    <base-pag
      :page="pag.page"
      :limit="pag.limit"
      :total="pag.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    productData: {
      type: Array,
      default() {
        return []
      }
    },
    pag: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {

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
    toEdit(id) {
      this.$router.push({
        path: '/edit-product',
        query: {
          id: id
        }
      })
    },
    handleSizeChange(val) {
      this.$emit('sizeChange', val)
    },
    handleCurrentChange(val) {
      this.$emit('currentChange', val)
    },
    changeStatus(id) {
      this.$confirm('确认要下架该产品吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.confirm(id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async confirm(id) {
      const { err, res } = await this.$put(`/admin/goods/${id}/status`, { status: '已下架' })
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '下架成功'
        })
        this.$emit('currentChange', 1)
      }
    }
  }
}
</script>

<style scoped lang="scss">
.product-list-wrap {
  .product-list-content {
    height : calc(100vh - 310px);
    overflow-y : auto;
  }
  .product-item {
    margin-top : 20px;
    display : flex;
    .message-wrap {
      border : 1px solid #ECECEC;
      border-left : none;
      font-size : 12px;
      color : #404040;
      width : 220px;
      display : flex;
      align-items : center;
      justify-content : center;
      &:nth-child(3) {
        width : 180px;
      }
      &:nth-child(4) {
        font-size : 12px;
      }
    }
    .product-message {
      flex : 1;
      display : flex;
      align-items : center;
      justify-content : flex-start;
      padding : 20px;
      font-size : 14px;
      color : #2F3A48;
      border-left : 1px solid #ECECEC;
      .el-image {
        width : 75px;
        height : 75px;
        min-width : 75px;
        min-height : 75px;
        background : #EEEEEE;
        margin-right : 20px;
      }
      p {
        overflow : hidden;
        text-overflow : ellipsis;
        display : -webkit-box;
        -webkit-line-clamp : 2;
        -webkit-box-orient : vertical;
      }
    }
    .btn-wrap {
      button {
        font-size : 14px;
        &.del-btn {
          color : #F16B6B;
          margin-left : 20px;
        }
      }
    }
  }
}

</style>
