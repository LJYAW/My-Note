<!--
 * @Author: your name
 * @Date: 2020-08-28 18:23:33
 * @LastEditTime: 2021-02-02 15:09:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/App.vue
-->
<template>
  <div id="app">
    <!-- <keep-alive>
      <router-view v-if="$route.meta.keepAlive"></router-view>
    </keep-alive> -->
    <!-- <router-view v-if="!$route.meta.keepAlive" /> -->
    <router-view />
  </div>
</template>

<script>
export default {
  computed: {},
  data() {
    return {}
  },
  watch: {
    // '$route.meta.keepAlive'(val) {
    //   console.log('🚀 ~ file: App.vue ~ line 30 ~ this.keepAlive', val)
    // }
  },
  created() {}
}
</script>

<style lang="scss">
#app {
  height: 100%;
}
</style>
