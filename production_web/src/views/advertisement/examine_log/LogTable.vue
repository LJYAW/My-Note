<!--
 * @Author: your name
 * @Date: 2021-07-30 09:58:45
 * @LastEditTime: 2021-08-04 14:39:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/examine_log/LogTable.vue
-->
<template>
  <div class="log-table">
    <p>当前条件下共{{ pag.total }}条数据</p>
    <el-table :data="tableData" border>
      <el-table-column prop="type" label="操作记录" min-width="100" />
      <el-table-column label="时间" min-width="200">
        <template slot-scope="scope">
          {{ scope.row.start_time|unixToHmsStr }} - {{ scope.row.end_time|unixToHmsStr }}
        </template>
      </el-table-column>
      <el-table-column prop="channel_name" label="频道名称" min-width="300" />
      <el-table-column prop="title" label="标题" min-width="300" />
      <el-table-column prop="duration" label="净时长" min-width="100">
        <template slot-scope="scope">
          {{ getTime(scope.row.duration) }}
        </template>
      </el-table-column>
      <el-table-column prop="operator_name" label="操作人" min-width="100" />
      <el-table-column prop="operator_department_name" label="所属组" min-width="100" />
      <el-table-column prop="add_time" label="操作时间" min-width="200" />
      <el-table-column prop="action" label="操作类型" miin-width="100" />
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
import { timesToHMS } from '../js/times'
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
    getTime(duration) {
      return timesToHMS(duration)
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
        margin-top: 20px;
    }
    ::v-deep .el-table__body-wrapper{
        height: calc(100% - 40px);
        overflow-y: auto;
    }
}
</style>
