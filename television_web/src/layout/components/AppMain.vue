<!--
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-04-26 17:27:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/layout/components/AppMain.vue
-->
<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <router-view :key="key" class="contain" />
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

<style scoped lang="scss">
.app-main {
  /*50 = navbar  */
  width: 100%;
  // height: 100%;
  position: relative;
  background-color: #f3f3f3;
  .contain {
    background-color: #fff;
    height: 100%;
    min-height: calc(100vh - 90px);
    margin: 0 20px;
    padding: 20px 24px;
  }
}
.fixed-header+.app-main {
  padding-top: 70px;
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
