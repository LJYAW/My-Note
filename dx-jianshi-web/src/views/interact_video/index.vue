<!--
 * @Author: wxh
 * @Date: 2020-09-27 11:00:18
 * @LastEditTime: 2021-07-15 11:34:41
 * @FilePath: /zhijian_web/src/views/interact_video/index.vue
-->
<template>
  <div class="mt-20px container d-flex flex-column pb-100px">
    <div class="banner">
      <img src="../../assets/images/interact-banner.png" class="obj" />
    </div>

    <div class="d-flex mt-30px">
      <videoCreate class="flex-1 mr-20px" />

      <!-- <hotSearch /> -->
      <div>
        <div class="using-tutorials mb-23px">
          <p class="fz-20px mb-12px">
            <i class="iconfont iconshipin3 fz-22px fc-ccc"></i>
            使用教程
          </p>
          <div class="p-23px" style="background: #fff;">
            <videoPalyer :video_options="video_options" />

            <p class="fz-12px mt-12px">手把手教你如何使用微剪快速创作视频</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import videoCreate from './video_create/index'
// import hotSearch from '../hot_search/index'
export default {
  data() {
    return {
      video_options: {
        width: '332',
        height: '190',
        autoplay: false,
        src: 'https://cdn-magic.weijian.video/storage/mnt/online/intelligent_writing/system/intro2.mp4'
      }
    }
  },
  components: { videoCreate },
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
.banner {
  width: 100%;
  height: 150px;
  img {
    width: 100%;
    height: 100%;
    object-fit: contain;
  }
}
</style>
