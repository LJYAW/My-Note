<!--
 * @Author: your name
 * @Date: 2021-10-20 17:59:51
 * @LastEditTime: 2021-11-03 20:20:33
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/view-app-edit/components/swiper.vue
-->
<template>
  <div class="swiper-wrap">
    <div class="card">
      <div class="swiper">
        <swiper ref="mySwiper" :options="swiperOptions">
          <swiper-slide
            v-for="(item,index) in bannerData"
            :key="index"
          >
            <BaseImage :src="item.imgMergeUrl" />
            <!-- <BaseImage class="sub-banner-src" :src="item.subBannerSrc" /> -->
          </swiper-slide>
          <div slot="pagination" class="swiper-pagination" />
        </swiper>
      </div>

    </div>
  </div>
</template>

<script>
import 'swiper/dist/css/swiper.css'
import { swiper, swiperSlide } from 'vue-awesome-swiper'

export default {
  name: 'Swiper',
  components: {
    swiper,
    swiperSlide
  },
  props: {

  },
  data() {
    return {
      swiperOptions: {
        pagination: {
          el: '.swiper-pagination', // 与slot="pagination"处 class 一致
          clickable: true // 轮播按钮支持点击
        }
      }
    }
  },
  computed: {
    bannerData() {
      return this.$store.state.bdapp.appData.HeaderBanner
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
.swiper-wrap {
  width: 100%;
  height: 180px;

  .swiper {
    height: 100%;

    .swiper-container {
      height: 100%;
    }

    .swiper-slide {
      position: relative;
      .banner-src {
        position: absolute;
        left: 0;
        bottom: 0;
        height: 128px;
      }
      .sub-banner-src {
        position: absolute;
        right: 70px;
        bottom: 0;
      }
    }

  }
  .card {
    background-color: transparent;
  }
}
</style>
