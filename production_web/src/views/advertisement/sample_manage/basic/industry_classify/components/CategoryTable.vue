<!--
 * @Author: your name
 * @Date: 2021-10-08 18:26:44
 * @LastEditTime: 2021-10-11 16:01:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/basic/industry_classify/components/CategoryBig.vue
-->
<template>
  <el-table :data="tableData" border>
    <el-table-column prop="id" label="ID" />
    <el-table-column prop="pid" label="PID" />
    <el-table-column prop="name" label="标题" />
    <!-- <el-table-column v-if="type!='CategoryBig'" prop="title" label="所属大类" /> -->
    <!-- <el-table-column v-if="type=='CategorySmall'" prop="title" label="所属中类" /> -->
    <el-table-column prop="updated_at" label="更新时间" />
    <el-table-column label="操作">
      <template slot-scope="scope">
        <base-btn type="text" class="del-btn">删除</base-btn>
      </template>
    </el-table-column>
  </el-table>
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
    type: {
      type: String,
      default() {
        return ''
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

  }
}
</script>

<style scoped lang="scss">
.del-btn{
  color: #F71D1D;
}
</style>
