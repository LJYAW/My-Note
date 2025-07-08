<!--
 * @Author: your name
 * @Date: 2021-10-20 12:07:55
 * @LastEditTime: 2021-11-05 14:48:16
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/classify-manage/index.vue
-->
<template>
  <div class="class-manage">
    <base-tab :tab-arr="tabArr" />
    <div class="manage-search">
      <el-input
        v-model="keyword"
        clearable
        size="mini"
        :maxlength="30"
        @keyup.enter.native="search"
      >
        <i slot="suffix" class="el-input__icon el-icon-search" @click="search" />
      </el-input>
      <base-btn size="mini" class="new-btn" @click="routerTo('new-class')">新建分类</base-btn>
    </div>
    <div v-if="classData.length" v-loading="loading">
      <div class="class-main">
        <div v-for="item in classData" :key="item.id" class="class-item">
          <div class="flex-wrap">
            <p>{{ item.name }}</p>
            <div v-if="item.id!==-1" class="btn-wrap">
              <base-btn type="text" @click="routerTo('edit-class',item.id)">修改</base-btn>
              <base-btn type="text" @click="deleteClass(item.id)">删除</base-btn>
            </div>
          </div>
          <p class="desc">{{ item.des }}</p>
          <div class="flex-wrap">
            <p class="goods-num" @click="routerTo('class-goods',item.id)">{{ item.goods_num }}个产品</p>
          </div>
        </div>
      </div>
      <base-pag
        :page="page"
        :limit="limit"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    <svg-icon v-else icon-class="empty-img" class="empty-img" />
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
      tabArr: ['分类管理'],
      classify: '全部分类',
      keyword: '',
      classData: [],
      page: 1,
      limit: 20,
      total: 0,
      loading: false
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getData()
  },
  mounted() {

  },
  methods: {
    routerTo(path, id = null) {
      this.$router.push({
        path: path,
        query: {
          id: id
        }
      })
    },
    handleSizeChange(val) {
      this.limit = val
      this.getData()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getData()
    },
    search() {
      this.page = 1
      this.getData()
    },
    // 获取分类列表
    async getData() {
      this.loading = true
      const params = {
        name: this.keyword,
        page: this.page,
        limit: this.limit
      }
      const { err, res } = await this.$get('/admin/categories', params)
      this.loading = false
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.classData = res.data.list
      this.total = res.data.total
    },
    // 删除提示
    deleteClass(id) {
      this.$confirm('是否确认删除产品分类?', '提示', {
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
    // 确认删除
    async confirmDelete(id) {
      const { err, res } = await this.$deleteRun(`/admin/categories/${id}`)
      if (err) {
        this.$message.error(err.msg)
      }
      this.search()
    }
  }
}
</script>

<style scoped lang="scss">
.class-manage {
  .manage-search {
    .el-input {
      width : 365px;
      margin-right : 20px;
    }
    // .el-select {
    //   width : 100px;
    // }
    .new-btn {
      float : right;
    }
  }
  .class-main {
    margin-top : 15px;
    display : flex;
    flex-wrap : wrap;
    height : calc(100vh - 300px);
    overflow-y : auto;
    .class-item {
      width : 372px;
      height : 172px;
      padding : 18px 20px;
      background : #F8F8F8;
      border-radius : 4px;
      margin-right : 18px;
      margin-bottom : 20px;
      .flex-wrap {
        display : flex;
        justify-content : space-between;
        align-items : center;
        button {
          padding : 0;
        }
        .goods-num {
          cursor : pointer;
        }
      }
      .desc {
        height : 60px;
        font-size : 14px;
        font-weight : 400;
        color : #404040;
        line-height : 20px;
        opacity : .4;
        margin : 20px 0;
        overflow : hidden;
        text-overflow : ellipsis;
        display : -webkit-box;
        -webkit-line-clamp : 3;
        -webkit-box-orient : vertical;
      }
    }
  }
  .empty-img {
    display : block;
    width : 140px;
    height : 174px;
    margin : 100px auto;
  }
}

</style>
