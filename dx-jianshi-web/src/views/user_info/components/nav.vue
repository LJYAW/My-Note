<!--
 * @Author: zzx
 * @Date: 2020-07-23 12:01:19
 * @LastEditTime: 2020-11-16 15:31:53
 * @FilePath: /weijian_web/src/views/user_info/components/nav.vue
--> 
<template>
  <el-tabs v-model="active_id"
    @tab-click="tabClick">
    <el-tab-pane v-for="(item,index) in nav_list"
      :key=index
      :name="item.activeId"
      :label="item.name">
    </el-tab-pane>
  </el-tabs>
</template>

<script>
export default {
  props: {
    nav_list: {
      type: Array,
      default() {
        return []
      }
    },
    activeId: {
      type: String,
      default() {
        return '0'
      }
    }
  },
  data() {
    return {
      active_id: this.activeId
    }
  },
  computed: {},
  watch: {},
  methods: {
    tabClick(tab, event) {
      this.$parent.$data.currentPage = 1
      this.$emit('getActiveId', tab.name)
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.user-nav {
  padding: 13px 30px;
  background-color: #fff;
  .mini-nav {
    border-bottom: none;
    justify-content: flex-start;
    li {
      a {
        padding-bottom: 10px;
      }
    }
  }
}
/deep/.el-tabs__header {
  background: #fff;
  padding: 0 31px;
  .el-tabs__item {
    height: 50px;
    line-height: 50px;
  }
}
/deep/.el-tabs__nav-wrap::after {
  background: none;
}
</style>
