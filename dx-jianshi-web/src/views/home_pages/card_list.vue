<!--
 * @Author: zll
 * @Date: 2020-12-04 10:58:55
 * @LastEditTime: 2020-12-17 11:47:45
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/home_pages/card_list.vue
-->
<template>
  <div class="card_warp">
    <div class="title">
      <p>像写Word一样做视频</p>
      <div>轻量级短视频一键快速创作，0基础快速上手创作短视频，图文资讯秒变视频化传播</div>
    </div>
    <div class="card_list container">
      <!-- <ul>
        <li v-for="(item, index) in card_list" :key="index">
          <img :src="item.url" alt="" />
          <div>
            {{ item.name }}
          </div>
        </li>
      </ul> -->
      <el-row :gutter="20" :offset="20" class="cardBox">
        <el-col :span="8" v-for="(item,index) in card_list" :key="index" class="tablist">
          <img :src="item.url" alt="" />
          <div class="card_item">
            {{ item.name }}
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="btns">
      <div class="make" @click="make()">
        马上创作
        <img src="../../assets/images/homepage/right_icon.png" />
      </div>
    </div>
    <div class="carousel_box" @mouseenter="on_top_enter" @mouseleave="on_top_leave">
      <swiper ref="mySwiper" :options="SwiperOptions">
        <swiper-slide v-for="(video_item, video_index) in video_card_list" :key="video_index" style="position:relative;" @click.native="playVideo(video_item)">
          <img :src="video_item.src" alt="" style="width:463px;height:260px">
          <!-- <videoPalyer :video_options="{ width: '463', height: '260', autoplay: false, src: video_item.video_url, poster: video_item.cover_pic }" @click="videoBtn(video_item)" /> -->
          <div class="iconfont palybtn">
            <vsvg icon="iconbofanganniu" class="fz-36px"></vsvg>
          </div>
        </swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
      </swiper>
    </div>
    <showVideoM v-if="modaName == 'showVideoM'" @Modalclose="Modalclose" :video_options="video_options" :video_details="video_details" />
  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
import showVideoM from '@/components/show_video_m'

import 'swiper/css/swiper.css'
export default {
  name: '',
  props: {},
  data() {
    return {
      modaName: '',
      video_options: {
        width: '800px',
        height: '450px',
        src: ''
      },
      video_details: {
        title: '视频'
      },
      SwiperOptions: {
        pagination: {
          el: '.swiper-pagination'
        },
        slidesPerView: 4,
        spaceBetween: 10,
        // slidesOffsetBefore: 100,
        loop: false,
        //切换速度
        speed: 1000,
        //左右箭头按钮
        navigation: {
          prevEl: '.swiper-button-prev',
          nextEl: '.swiper-button-next'
        },
        //不需要自动轮播时
        // autoplay: false,
        // 设置自动轮播的延迟时间
        autoplay: {
          delay: 1000,
          stopOnLastSlide: false,
          disableOnInteraction: false
        }
        // on: {
        //   click(e) {
        //     let url = e.target.dataset.cover_pic
        //     console.log('🚀 ~ file: card_list.vue ~ line 89 ~ click ~ url', url)
        //   }
        // }
      },
      falg: false,
      video_card_list: [
        {
          src: require('../../assets/images/material_library/cover/4.jpg'),
          url: 'https://static-magic.weijian.video/storage/mnt/zhijian/system/temp/4.mp4'
        },
        {
          src: require('../../assets/images/material_library/cover/5.jpg'),
          url: 'https://static-magic.weijian.video/storage/mnt/zhijian/system/temp/5.mp4'
        },
        {
          src: require('../../assets/images/material_library/cover/6.jpg'),
          url: 'https://static-magic.weijian.video/storage/mnt/zhijian/system/temp/6.mp4'
        },
        {
          src: require('../../assets/images/material_library/cover/1.jpg'),
          url: 'https://static-magic.weijian.video/storage/mnt/zhijian/system/temp/1.mp4'
        },
        {
          src: require('../../assets/images/material_library/cover/2.jpg'),
          url: 'https://static-magic.weijian.video/storage/mnt/zhijian/system/temp/2.mp4'
        },
        {
          src: require('../../assets/images/material_library/cover/3.jpg'),
          url: 'https://static-magic.weijian.video/storage/mnt/zhijian/system/temp/3.mp4'
        },
        {
          src: require('../../assets/images/material_library/cover/7.jpg'),
          url: 'https://static-magic.weijian.video/storage/mnt/zhijian/system/temp/7.mp4'
        },
        {
          src: require('../../assets/images/material_library/cover/8.jpg'),
          url: 'https://static-magic.weijian.video/storage/mnt/zhijian/system/temp/8.mp4'
        },
        {
          src: require('../../assets/images/material_library/cover/9.jpg'),
          url: 'https://static-magic.weijian.video/storage/mnt/zhijian/system/temp/9.mp4'
        },
        {
          src: require('../../assets/images/material_library/cover/10.jpg'),
          url: 'https://static-magic.weijian.video/storage/mnt/zhijian/system/temp/10.mp4'
        },
        {
          src: require('../../assets/images/material_library/cover/11.jpg'),
          url: 'https://static-magic.weijian.video/storage/mnt/zhijian/system/temp/11.mp4'
        }
      ],
      card_list: [
        {
          name: '图文链接一键导入',
          url: require('../../assets/images/home_page/card1.png')
        },
        {
          name: '长文章自动摘要成短文章',
          url: require('../../assets/images/home_page/card2.png')
        },
        {
          name: '全智能云端合成',
          url: require('../../assets/images/home_page/card3.png')
        },
        {
          name: '多种模板样式选择',
          url: require('../../assets/images/home_page/card4.png')
        },
        {
          name: '海量免费音视频素材',
          url: require('../../assets/images/home_page/card5.png')
        },
        {
          name: '虚拟形象内容播报',
          url: require('../../assets/images/home_page/card6.png')
        }
      ]
    }
  },
  computed: {},
  methods: {
    playVideo(item) {
      this.falg = true
      this.swiper.autoplay.stop()
      this.modaName = 'showVideoM'
      this.video_options.src = item.url
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showVideoM')
      })
    },
    getVideoList() {
      this.$get('intelligent-creation/intelligent-video-square-list', { params: { page: 0, limit: 10 } }).then(res => {
        if (res.data.code == '0000') {
          this.video_card_list = res.data.data.list
        }
      })
    },
    on_top_enter() {
      this.swiper.autoplay.stop()
    },
    on_top_leave() {
      if (!this.falg) {
        this.swiper.autoplay.start()
      } else {
        this.falg = false
      }
    },
    make() {
      this.$router.push({
        path: '/intellect-create',
        query: {}
      })
    },
    Modalclose() {
      this.swiper.autoplay.start()
      this.video_options.src = ''
      this.modaName = ''
    }
  },
  mounted() {
    this.swiper.slideTo(1, 1000, false)
  },
  created() {
    // this.getVideoList()
  },
  components: {
    Swiper,
    SwiperSlide,
    showVideoM
  },
  computed: {
    swiper() {
      return this.$refs.mySwiper.$swiper
    }
  },
  watch: {}
}
</script>

<style lang="scss" scoped>
.card_warp {
  margin-top: 45px;
  .title {
    p {
      font-size: 30px;
      font-weight: 500;
      text-align: center;
      color: #333333;
      font-family: PingFang SC, PingFang SC-Bold;
    }
    div {
      text-align: center;
      color: #666666 !important;
      font-size: 12px;
      margin-top: 20px;
    }
  }
  .card_list {
    width: 100%;
    /deep/ .el-row {
      margin-left: auto !important;
      margin-right: auto !important;
    }
    .cardBox {
      width: 75%;
      margin: 30px auto;
      .tablist {
        position: relative;
        margin-top: 20px;
        height: 300px;
        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
        .card_item {
          width: 100%;
          position: absolute;
          bottom: 26px;
          text-align: center;
          color: #333333;
          font-size: 16px;
        }
      }
    }
  }
  .btns {
    display: flex;
    justify-content: center;
    .make {
      background: $c;
      color: #fff;
      height: 43px;
      font-size: 16px;
      line-height: 43px;
      width: 160px;
      text-align: center;
      margin-top: 40px;
      border-radius: 100px;
      cursor: pointer;
      img {
        width: 22px;
        height: 13px;
      }
    }
  }
  .carousel_box {
    width: 100%;
    margin-top: 50px;
    .palybtn {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
    }
    .swiper-pagination {
      bottom: 0;
    }
    img {
      // cursor: pointer;
    }
    video {
      width: 330px;
      height: 185px;
      opacity: 0.8;
    }
  }
}
@media screen and (min-width: 1921px) {
  .tablist {
    height: 400px !important;
    img {
      height: 100%;
    }
  }
}
</style>
