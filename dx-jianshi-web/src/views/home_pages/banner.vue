<!--
 * @Author: zll
 * @Date: 2020-12-04 10:58:36
 * @LastEditTime: 2020-12-17 11:14:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/home_pages/banner.vue
-->
<template>
  <div class="banner_warp">
    <div @mouseenter="on_top_enter" @mouseleave="on_top_leave">
      <swiper ref="mySwiper" :options="SwiperOptions">
        <swiper-slide v-for="(video_item, video_index) in src" :key="video_index" style="position:relative;">
          <img :src="video_item.url" alt="" class="banner_img">
        </swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
      </swiper>
    </div>
  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'

export default {
  name: '',
  props: {},
  data() {
    return {
      SwiperOptions: {
        pagination: {
          el: '.swiper-pagination'
        },
        loop: true,
        //切换速度
        speed: 1000,
        //左右箭头按钮
        navigation: {
          prevEl: '.swiper-button-prev',
          nextEl: '.swiper-button-next'
        },
        autoplay: {
          delay: 1000,
          stopOnLastSlide: false,
          disableOnInteraction: false
        }
      },
      src: [
        {
          url: require('@/assets/images/home_page/banner1.png')
        },
        {
          url: require('@/assets/images/home_page/banner2.png')
        },
        {
          url: require('@/assets/images/home_page/banner3.png')
        }
        // {
        //   url: require('@/assets/images/home_banner.png')
        // },
        // {
        //   url: require('@/assets/images/home_banner.png')
        // }
      ]
    }
  },
  computed: {
    swiper() {
      return this.$refs.mySwiper.$swiper
    }
  },
  methods: {
    on_top_enter() {
      this.swiper.autoplay.stop()
    },
    on_top_leave() {
      this.swiper.autoplay.start()
    }
  },
  mounted() {
    this.swiper.slideTo(1, 1000, false)
  },
  created() {},
  components: {},
  watch: {}
}
</script>

<style lang='scss' scoped>
.banner_warp {
  position: relative;
  .banner_img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  .title {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 100%;
    transform: translate(-50%, -50%);
    z-index: 300;
    font-size: 48px;
    font-weight: bolder;
    color: #ffffff;
    text-align: center;
    img {
      width: 140px;
      object-fit: cover;
    }
  }
}
</style>
