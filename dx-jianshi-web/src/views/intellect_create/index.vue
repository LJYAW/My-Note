<!--
 * @Author: zzx
 * @Date: 2020-09-24 11:39:28
 * @LastEditTime: 2021-07-15 20:06:36
 * @FilePath: /zhijian_web/src/views/intellect_create/index.vue
-->
<template>
  <div class="intelligent-creation">
    <div class="header d-flex align-content-center banner" style="margin-top:0">
      <!-- <div class="banner content container d-flex justify-content-between flex-1"> -->
      <img src="../../assets/images/banner.png" class="obj" />
      <!-- </div> -->
    </div>
    <div class="container">
      <div class="d-flex mt-24px mb-50px">
        <videoCreate class="mr-20px" />
        <videoLibrary />
        <!-- <hotSearch /> -->
      </div>
    </div>
  </div>
</template>
<script>
import videoCreate from './video_create/index'
import videoLibrary from '../video_library/index'
// import hotSearch from '../hot_search/index'
export default {
  data() {
    return {}
  },
  components: {
    videoCreate,
    videoLibrary
    // hotSearch
  },
  mounted() {}
}
</script>
<style scoped lang="scss">
.intelligent-creation {
  .banner {
    margin-top: -15px;
    padding: 0;
  }
}
@media screen and (max-width: 768px) {
  .banner {
    max-width: 1000px;
    min-width: 1000px;
  }
}
@media screen and (max-width: 1200px) and (min-width: 768px) {
  .banner {
    max-width: 1200px;
    min-width: 1200px;
  }
}
</style>
