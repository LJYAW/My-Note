<!--
 * @Author: zzx
 * @Date: 2020-07-22 20:26:52
 * @LastEditTime: 2021-07-15 15:44:16
 * @FilePath: /zhijian_web/src/views/main.vue
-->
<template>
  <div class="mt-20px container d-flex flex-column">
    <div class="banner">
      <img src="../assets/images/banner.png" class="obj" />
    </div>
    <div class="d-flex mt-30px">
      <videoCreate class="mr-20px" />
      <!-- <hotSearch /> -->
    </div>
  </div>
</template>

<script>
import videoCreate from './video_create/index'
// import hotSearch from './hot_search/index'
export default {
  data() {
    return {}
  },
  components: { videoCreate },
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {},
}
</script>

<style lang="scss" scoped>
.banner {
  width: 100%;
  height: 150px;
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}
</style>
