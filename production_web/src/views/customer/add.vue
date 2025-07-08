<!--
 * @Author: zll
 * @Date: 2021-01-11 23:18:48
 * @LastEditTime: 2021-01-12 01:38:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/customer/add.vue
-->
<template>
  <div class="add-customer">
    <el-breadcrumb class="breadcrumb">
      <el-breadcrumb-item to="/customer">客户管理</el-breadcrumb-item>
      <el-breadcrumb-item>{{ $route.query.type }}</el-breadcrumb-item>
      <el-breadcrumb-item>{{ activeName }}</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="tab-list">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基本信息" name="基本信息">
          <basic @toggle="toggle" />
        </el-tab-pane>
        <el-tab-pane label="联系人" name="联系人">
          <contacts />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import basic from './tab/basic'
import contacts from './tab/contacts'
export default {
  components: { basic, contacts },
  data() {
    return {
      activeName: '基本信息',
      rules: {}
    }
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() {

  },
  methods: {
    handleClick(e) {
    },
    toggle(value) {
      this.activeName = value
    }
  }
}
</script>

<style lang='scss' scoped>
.add-customer{
  padding: 20px;
  height: 100vh;
  background: #f4f4f5;
  .tab-list{
    height: 100%;
    padding: 20px;
    background: #fff;
    margin-top: 20px;
  }
}
</style>
