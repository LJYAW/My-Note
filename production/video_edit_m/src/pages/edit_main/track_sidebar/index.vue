<!--
 * @Author: zzx
 * @Date: 2020-09-18 16:20:14
 * @LastEditTime: 2020-09-25 18:32:44
 * @FilePath: /video_edit/src/pages/edit_main/track_sidebar/index.vue
-->
<template>
  <div class="track-sidbar-content">
    <div class="track-time"></div>
    <ul class="icon-list">
      <li>
        <i class="iconfont icon-staff"></i>
      </li>
      <!-- <li>
        <i class="iconfont icon-jianji"></i>
      </li>-->
      <li>
        <i class="iconfont icon-zhuanchang"></i>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {},
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.track-sidbar-content {
  .icon-list {
    margin: 0;
    height: 100%;
    li:first-child {
      height: 50px;
      line-height: 45px;
      .icon-staff {
        font-size: 18px;
      }
    }
    li {
      height: 30px;
      line-height: 30px;
      margin-bottom: 10px;
    }
  }
}
</style>
