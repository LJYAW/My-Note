<!--
 * @Author: your name
 * @Date: 2021-05-19 10:57:59
 * @LastEditTime: 2021-06-03 15:20:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/examine_log/LogTable.vue
-->
<template>
  <div class="log-table">
    <el-table :data="tableData" border>
      <el-table-column prop="type" label="操作记录" width="100" />
      <el-table-column label="时间" width="200">
        <template slot-scope="scope">
          {{ scope.row.start_time|unixToHmsStr }} - {{ scope.row.end_time|unixToHmsStr }}
        </template>
      </el-table-column>
      <el-table-column prop="item_name" label="栏目" width="200" />
      <el-table-column prop="title" label="标题" width="300" />
      <el-table-column label="一级分类" width="200">
        <template slot-scope="scope">
          {{ getCustomerCate(scope.row.first_customer_categories) }}
        </template>
      </el-table-column>
      <el-table-column label="二级分类" width="200">
        <template slot-scope="scope">
          {{ getCustomerCate(scope.row.second_customer_categories) }}
        </template>
      </el-table-column>
      <el-table-column prop="operator_name" label="操作人" width="100" />
      <el-table-column prop="operator_department_name" label="所属组" width="100" />
      <el-table-column prop="add_time" label="操作时间" width="200" />
      <el-table-column prop="action" label="操作类型" width="100" />
    </el-table>
    <base-pag
      :total="pag.total"
      :limit="pag.limit"
      :page="pag.page"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    />
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    tableData: {
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
    handleCurrentChange(val) {
      this.$emit('currentChange', val)
    },
    handleSizeChange(val) {
      this.$emit('sizeChange', val)
    },
    getCustomerCate(data) {
      const arr = []
      data.forEach(item => {
        arr.push(item.name)
      })
      return arr.join('、') || ''
    }
  }
}
</script>

<style scoped lang="scss">
.log-table{
    display: flex;
    flex: 1;
    flex-direction: column;
    .el-table{
        flex: 1;
        height: 0;
    }
    ::v-deep .el-table__body-wrapper{
        height: calc(100% - 40px);
        overflow-y: auto;
    }
}
</style>
