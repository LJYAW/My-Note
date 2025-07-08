<!--
 * @Author: your name
 * @Date: 2021-10-20 15:15:16
 * @LastEditTime: 2021-10-20 15:36:05
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/components/base/Tab.vue
-->
<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane v-for="item in tabArr" :key="item" :label="item" :name="item" />
  </el-tabs>
</template>

<script>
export default {
  components: {

  },
  props: {
    tabArr: {
      type: Array,
      default() {
        return []
      }
    }
  },
  data() {
    return {
      activeName: ''
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.activeName = this.tabArr[0]
  },
  mounted() {

  },
  methods: {
    handleClick(tab, event) {
      this.$emit('changeTab', tab.name)
    }
  }
}
</script>

<style scoped lang="scss">
.el-tabs {
  ::v-deep .el-tabs__header {
    margin-bottom : 20px;
  }

  ::v-deep .el-tabs__nav-wrap::after {
    height : 0;
  }

  ::v-deep .el-tabs__item {
    font-size : 18px;
    font-weight : 400;
    color : #404040;
    opacity : .4;
    line-height : 18px;
    height : auto;
    padding : 0 10px 12px 10px;

    &:nth-child(2) {
      padding-left : 0;
    }

    &.is-active {
      opacity : 1;
    }
  }
}

</style>
