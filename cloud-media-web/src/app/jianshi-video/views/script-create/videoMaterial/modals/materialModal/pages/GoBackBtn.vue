<!--
 * @Author: zzx
 * @Date: 2020-10-28 14:32:00
 * @LastEditTime: 2021-08-09 18:31:30
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/src/go_back_btn.vue
-->
<template>
  <!-- 返回按钮 -->
  <div class="material-back-btn">
    <a @click="backTo">
      <i class="el-icon-arrow-left" />返回
    </a>
    <!-- <quesTion
      v-if="modalM.videoEditVisible"
      video_title="视频剪辑帮助"
      video_url="https://cdn-magic.weijian.video/storage/mnt/online/intelligent_writing/system/FinalVideo_1594183093.200344.mp4"
    /> -->
  </div>
</template>

<script>
export default {
  components: {
    // QuesTion: () => import('@/components/question')
  },
  props: {},
  data() {
    return {}
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    backTo() {
      this.$emit('backTo')
    }
    // backFromVideo() {
    //   this.$emit('setComponentName', '')
    //   // this.$set(this.modalM, 'videoEditVisible', false)
    // }
  }
}
</script>

<style scoped lang="scss">
.material-back-btn {
  font-size: 14px;
  color: #333;
}
</style>
