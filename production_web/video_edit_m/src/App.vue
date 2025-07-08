<!--
 * @Author: zzx
 * @Date: 2020-07-29 15:11:48
 * @LastEditTime: 2021-06-22 12:06:16
 * @FilePath: /video_edit/src/App.vue
-->
<template>
  <div id="app">
    <Vmain />
  </div>
</template>

<script>
import Vmain from './pages/index.vue'
export default {
  name: 'App',
  components: {
    Vmain
  }
}
</script>

<style>
.test {
  width: 875px;
  height: 150px;
}
</style>
