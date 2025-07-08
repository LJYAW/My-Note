<!--
 * @Author: your name
 * @Date: 2021-10-25 17:33:23
 * @LastEditTime: 2021-11-17 20:42:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/product-manage/sale-product/List.vue
-->
<template>
  <div class="product-list-wrap">
    <div class="product-list-content">
      <!-- <div v-for="item in productData" :key="item.id" class="product-item">
        <div class="message-wrap product-message">
          <base-image :src="item.main_img" />
          <p>{{ item.name }}</p>
        </div>
        <div class="message-wrap">
          <p>{{ item.on_at||'无' }}</p>
        </div>
        <div class="message-wrap">
          <p>产品类型：{{ item.type }}</p>
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
          <base-btn type="text" @click="changeStatus(item.id)">上架产品</base-btn>
          <base-btn type="text" class="del-btn" @click="deleteGood(item.id)">删除</base-btn>
        </div>
      </div> -->
      <div class="batch-wrap">
        <base-btn @click="batchChange">批量上架</base-btn>
      </div>
      <el-table
        ref="multipleTable"
        :data="productData"
        border
        stripe
        height="calc(100vh - 360px)"
        @selection-change="handleSelectionChange"
      >
        <el-table-column
          type="selection"
          width="55"
        />
        <el-table-column label="产品图" width="400">
          <template slot-scope="scope">
            <base-image :src="scope.row.main_img" />
            <p class="pro-name">{{ scope.row.name }}</p>
          </template>
        </el-table-column>
        <el-table-column prop="on_at" label="上架时间" width="180" />
        <el-table-column prop="type" label="产品类型" />
        <el-table-column prop="id" label="产品ID" />
        <el-table-column prop="price" label="产品价格" />
        <el-table-column prop="inventory" label="库存数量" />
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <base-btn type="text" @click="toEdit(scope.row.id)">编辑产品</base-btn>
            <base-btn type="text" @click="changeStatus(scope.row.id)">上架产品</base-btn>
            <base-btn type="text" class="del-btn" @click="deleteGood(scope.row.id)">删除</base-btn>
          </template>
        </el-table-column>
      </el-table>
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
      productId: []
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
    deleteGood(id) {
      this.$confirm('是否确认删除此商品?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.confirmDelete(id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async confirmDelete(id) {
      const { err, res } = await this.$deleteRun(`/admin/goods/${id}`)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        this.$emit('currentChange', 1)
      }
    },
    changeStatus(id) {
      this.$confirm('确认要上架该产品吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const p = this.confirm(id)
        p.then(() => {
          this.$message({
            type: 'success',
            message: '上架成功'
          })
          this.$emit('currentChange', 1)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async confirm(id) {
      const { err, res } = await this.$put(`/admin/goods/${id}/status`, { status: '已上架' })
      if (err) {
        this.$message.error(err.msg)
      }
    },
    handleSelectionChange(val) {
      this.productId = val
    },
    batchChange() {
      const pArr = []
      if (!this.productId.length) {
        this.$message.error('请选择需要上架的产品')
        return
      }
      for (let i = 0; i < this.productId.length; i++) {
        const p = this.confirm(this.productId[i].id)
        pArr.push(p)
      }
      Promise.all(pArr).then(() => {
        this.$message({
          type: 'success',
          message: '上架成功'
        })
        this.$emit('currentChange', 1)
      })
    }
  }
}
</script>

<style scoped lang="scss">
.product-list-wrap {
  .product-list-content {
    // height : calc(100vh - 310px);
    // overflow-y : auto;
    .batch-wrap{
      margin-bottom: 20px;
    }
    .el-image {
      width : 75px;
      height : 75px;
      background : #EEEEEE;
      margin-right : 20px;
      display: inline-block;
    }
    p {
      flex : 1;
      overflow : hidden;
      text-overflow : ellipsis;
      display : -webkit-inline-box;
      -webkit-line-clamp : 2;
      -webkit-box-orient : vertical;
    }
    .del-btn {
      color : #F16B6B;
      margin-left : 10px;
    }
  }
}

</style>
