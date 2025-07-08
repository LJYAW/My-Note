<!--
 * @Author: zzx
 * @Date: 2020-09-24 14:04:24
 * @LastEditTime: 2020-12-14 15:49:36
 * @FilePath: /zhijian_web/src/views/intellect_create/video_create/videoModels/model_set.vue
-->
<template>
  <div class="video-effect">
    <p class="fz-20px mb-40px module-title">视频效果选项</p>
    <!-- <loading  /> -->
    <el-form ref="form" label-width="120px" label-position="left" class="text-left">
      <!-- 声音画面部分 -->
      <frame ref='frame' />
      <!-- 附加模块 -->
      <attach ref='attach' />
    </el-form>
  </div>
</template>
<script>
import attach from './attach'
import frame from './frame'
export default {
  data() {
    return {}
  },
  components: {
    attach,
    frame
  }
}
</script>
<style lang="scss">
@import '../../css/model_set.scss';
</style>
