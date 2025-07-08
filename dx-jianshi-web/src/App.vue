<!--
 * @Author: zzx
 * @Date: 2020-09-27 10:41:16
 * @LastEditTime: 2021-01-06 12:05:04
 * @FilePath: /zhijian_web/src/App.vue
-->
<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  computed: {
    alertMsg() {
      return this.$store.state.alert_msg
    }
  },
  watch: {
    alert_msg(txt) {
      this.$alertMsg(txt)
    }
  }
}
</script>

<style lang="scss">
#app {
  height: 100%;
  overflow: auto;
}
</style>
