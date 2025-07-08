<!--
 * @Author: your name
 * @Date: 2021-07-26 20:42:01
 * @LastEditTime: 2021-08-26 14:26:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/banner.vue
-->
<template>
  <div class="banner-wrap">
    <img :src="banner" alt="" class="banner-img">
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {

  components: {

  },
  props: {

  },
  data() {
    return {
    }
  },
  computed: {
    ...mapGetters(['companyInfo', 'userInfo']),

    banner() {
      return this.companyInfo.banner_url || require('@/assets/images/banner.png')
    }

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.banner-wrap {
  width: 100%;
  height: 100%;
  position: relative;

  &::after {
    width: 100%;
    height: 100%;
    display: block;
    content: " ";
    position: absolute;
    top: 0;
    left: 0;
    background: rgba(0,0,0,.4);
  }

  .banner-img {
    width: 100%;
    height: 100%;
    object-fit: cover;

  }
}
</style>
