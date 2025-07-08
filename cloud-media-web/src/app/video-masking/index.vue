<!--
 * @Author: your name
 * @Date: 2021-08-30 18:13:31
 * @LastEditTime: 2021-10-08 14:39:06
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-masking/view.vue
-->
<template>
  <div class="video-masking-view-wrap">
    <router-view />
  </div>
</template>

<script>
export default {

}
</script>

<style>
.video-masking-view-wrap {
  height: calc(100vh - 80px);
  width: min-content;
  width: 100%;
  margin: 0 auto;
}
</style>
