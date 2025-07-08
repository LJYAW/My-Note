<!--
 * @Author: zzx
 * @Date: 2020-10-28 14:32:00
 * @LastEditTime: 2020-12-01 11:18:17
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/src/go_back_btn.vue
-->
<template>
  <!-- 返回按钮 -->
  <div class="material-back-btn d-flex">
    <a
      v-if="modalM.videoEditVisible"
      class="mr-20px fz-14px hove-c"
      @click="backFromVideo">
      <i class="iconfont iconfanhui d-inline-block fz-12px"></i>返回
    </a>
    <quesTion
      v-if="modalM.videoEditVisible"
      video_title="视频剪辑帮助"
      video_url="https://cdn-magic.weijian.video/storage/mnt/online/intelligent_writing/system/FinalVideo_1594183093.200344.mp4" />
    <a
      v-if="!modalM.videoEditVisible"
      @click="backFromImg"
      class="fz-14px hove-c">
      <i class="iconfont iconfanhui d-inline-block fz-12px"></i>返回
    </a>
  </div>
</template>

<script>
export default {
  props: {},
  inject: ['modalM'],
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {
    backFromImg() {
      this.$set(this.modalM, 'aiListName', '')
    },
    backFromVideo() {
      this.$set(this.modalM, 'videoEditVisible', false)
    }
  },
  components: {
    QuesTion: () => import('@/components/question')
  },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.material-back-btn {
  position: absolute;
  left: 26px;
  top: 10px;
}
</style>
