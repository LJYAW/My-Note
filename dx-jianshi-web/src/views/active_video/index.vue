<!--
 * @Author: zzx
 * @Date: 2020-10-15 10:48:41
 * @LastEditTime: 2020-10-16 14:45:21
 * @FilePath: /zhijian_web/src/views/active_video/index.vue
-->
<template>
  <div class="active-video">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'ActiveVideo',
  components: {
    MyVideos: () => import('./my_videos/index.vue')
  }
}
</script>

<style scoped lang="scss">
</style>
