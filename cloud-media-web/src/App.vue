<!--
 * @Author: your name
 * @Date: 2021-07-26 10:26:52
 * @LastEditTime: 2021-10-09 11:37:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/App.vue
-->
<template>
  <div id="app">
    <router-view v-wechat-title="$route.meta.appName?'视迅云-'+$route.meta.appName:'视迅云'" />
  </div>
</template>

<style lang="scss">
#app {
  width: 100%;
  min-height: 100vh;
}
</style>
