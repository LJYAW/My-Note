<!--
 * @Author: your name
 * @Date: 2021-07-26 20:32:48
 * @LastEditTime: 2021-09-01 11:48:37
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/layout/home/AppBar.vue
-->
<template>
  <div :class="['app-header',{ 'fixed-heade' : fixedHeader,'main-page':!fixedHeader }]">
    <div class="contener">
      <div class="logo">
        <router-link to="/" class="logo-link">
          <img :src="logo">
        </router-link>
      </div>

      <NavList />
      <Avatar />

    </div>

    <div v-if="fixedHeader" class="bg-img">
      <div class="wrap" />
      <img :src="bgImg" alt="">
    </div>

  </div>
</template>

<script>
import NavList from './NavList.vue'
import Avatar from './Avatar.vue'
import { mapGetters } from 'vuex'

export default {
  components: {
    NavList,
    Avatar
  },
  props: {

  },
  data() {
    return {
      // logo: require('@/assets/images/logo-white.png'),
      // bgImg: require('@/assets/images/banner.png')
    }
  },
  computed: {
    ...mapGetters(['companyInfo', 'userInfo']),

    fixedHeader() {
      return this.$route.name !== 'Home'
    },
    logo() {
      return this.companyInfo.logo_url || require('@/assets/images/logo-white.png')
    },
    bgImg() {
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
.app-header {
  height: 70px;
  width: 100%;
  position: absolute;
  z-index: 200;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;

  .contener {
    height: 100%;
    width: 100%;
    max-width: 1980px;
    padding: 0 95px 0 20px;
    display: flex;
    align-items: center;
    position: absolute;
    z-index: 2;
    margin: 0 auto;
  }

  .bg-img {
    width: 100%;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);

    .wrap {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: rgba(0,0,0,.5);
    }
  }

  &.fixed-heade {
    position: fixed;
    top: 0;
    left: 0;
  }

  .logo {

    .logo-link {
      width: 135px;
      height: 30px;
      display: block;
    }
  }
}
</style>
