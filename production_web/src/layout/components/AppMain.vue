<!--
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-12-20 13:16:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/layout/components/AppMain.vue
-->
<template>
  <transition name="fade-transform">
    <section class="app-main">
      <keep-alive>
        <router-view v-if="$route.meta.keepAlive" :key="key" class="contain" />
      </keep-alive>
      <router-view v-if="!$route.meta.keepAlive" :key="key" class="contain" />
    </section>
  </transition>

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
  height: 100%;
  margin-top: 20px;
  position: relative;
  background-color: #f3f3f3;
  .contain {
    background-color: #fff;
    height: 100%;
    min-height: calc(100vh - 85px);
    margin: 0 20px;
  }
}
.fixed-header+.app-main {
  padding-top: 50px;
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
