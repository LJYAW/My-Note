<!--
 * @Author: your name
 * @Date: 2021-08-09 15:48:19
 * @LastEditTime: 2021-09-17 17:33:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/app-tools/index.vue
-->
<template>
  <div class="app-tools">
    <title>视频工具箱页面</title>
    <div class="hedaer-img">
      <swiper ref="mySwiper" :options="SwiperOptions" class="swiper-list">
        <swiper-slide v-for="(item,index) in swiperData" :key="index" class="item-slide">
          <img :src="item.src" alt="" class="item-img">
        </swiper-slide>
        <!-- 分页器 -->
        <div slot="pagination" class="swiper-pagination" />
      </swiper>
    </div>
    <div class="router-list container">
      <router-link v-for="(item,index) in routerList" :key="index" :to="item.path" class="item-router">
        <div class="svg-box">
          <svg-icon :icon-class="item.icon" class="svg-icon" />
        </div>
        <div class="item-label">{{ item.label }}</div>
        <div class="item-title">{{ item.title }}</div>
      </router-link>
    </div>
  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
var mySwiper = new Swiper('.swiper-container')
export default {
  components: {
    Swiper,
    SwiperSlide
  },
  props: {

  },
  data() {
    return {
      SwiperOptions: {
        speed: 1000, // 匀速时间
        loop: true,
        slidesPerView: 1,
        pagination: {
          el: '.swiper-pagination',
          clickable: true // 允许点击小圆点跳转
        },
        autoplay: {
          delay: 2000,
          disableOnInteraction: false
        },
        observer: true,
        observeParents: true
      },
      swiperData: [
        { src: require('@/assets/images/header.png') },
        { src: require('@/assets/images/header.png') },
        { src: require('@/assets/images/header.png') }
      ],
      routerList: [
        {
          label: '虚拟直播',
          path: '/live-video',
          icon: 'fictitious-live'
        },
        {
          label: '简视视频创作',
          path: '/jianshi-video',
          icon: 'video-cut',
          title: ''
        },
        {
          label: '视频水印',
          path: '/video-masking',
          // path: '',
          icon: 'video-watermark',
          title: ''
        },
        {
          label: '语音转写',
          path: '/phonetic-transcription',
          // path: '',
          icon: 'voice-Transcribe',
          title: ''
        },
        {
          label: '直播剪辑',
          path: '',
          icon: 'live-cut',
          title: '敬请期待'
        }
      ]
    }
  },
  computed: {
    swiper() {
      return this.$refs.mySwiper.$swiper
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
.app-tools {
  max-width: 1440px;
  margin: 0 auto;

  .hedaer-img {
    margin-top: 20px;

    .swiper-list {
      position: relative;

      ::v-deep .swiper-pagination-bullet-active {
        width: 16px;
        border-radius: 10px;
      }

      ::v-deep .swiper-pagination-bullet {
        width: 8px;
        height: 8px;
        background: #fff;
        opacity: .6;
      }

      .item-img {
        width: 100%;
        height: 240px;
        object-fit: cover;
      }
    }
  }

  .router-list {
    display: flex;
    margin-top: 30px;

    .item-router {
      width: 220px;
      height: 220px;
      background: #fff;
      border-radius: 5px;
      margin-right: 24px;
      text-align: center;
      display: flex;
      flex-direction: column;
      justify-content: center;
      transition: all .15s;

      &:hover {
        background: #5675e8;
        box-shadow: 0px 5px 10px 0px rgba(86, 117, 232, .3);
        transform: translateY(-11px);

        .svg-box {
          display: none;
        }

        .item-title {
          opacity: 1;
          margin-top: 30px;
        }

        .item-label {
          color: #fff;
        }
      }

      &:last-child {
        margin-right: 0px;
      }

      .item-title {
        opacity: 0;
        height: 20px;
        font-size: 14px;
        font-weight: 400;
        color: #fff;
      }

      .item-label {
        font-size: 18px;
        font-weight: 400;
        color: #404040;
      }

      .svg-box {
        display: block;
        margin-bottom: 40px;

        .svg-icon {
          width: 70px;
          height: 70px;
        }
      }
    }
  }
}
</style>
