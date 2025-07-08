<!--
 * @Author: your name
 * @Date: 2021-10-20 11:19:06
 * @LastEditTime: 2021-10-22 20:55:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/layout/components/AppMain.vue
-->
<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <router-view :key="key" />
    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    key() {
      return this.$route.path
    }
  }
}
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  position : relative;
  overflow : hidden;
  padding : 30px;
  min-height : calc(100vh - 70px);
  width : 100%;
  background : #FFFFFF;
}

</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right : 15px;
  }
}

</style>
