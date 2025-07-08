<template>
  <div class="banner-wrap">
    <el-carousel trigger="click"
      height="437px">
      <el-carousel-item v-for="(banner,j) in banner_list"
        :key="j">
        <div class="banner">
          <img :src="banner.img">
        </div>

        <div class="banner-1_cotnet container d-flex align-content-center flex-column">
          <p class="title text-center">短视频智能创作系统
            <img src="../../assets/images/homepage/shanchuang.png">
            全新上线
          </p>

          <!-- <div class="search-box">
            <input v-model="search_key"
              class="border-none search-input"
              placeholder="视频资源搜索" />
            <i class="iconfont iconsearch fc-c"></i>
          </div> -->

          <!-- <div class="hot-key-wrap">
            热门搜索：
            <span v-for="(word,i) in hot_key_list"
              class="mr-5px cp"
              :key="i">{{word}}
            </span>
          </div> -->
        </div>

      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      search_key: '',
      hot_key_list: ['周杰伦', '双刀', '呦西'],
      banner_list: [
        {
          img: require('@/assets/images/home_banner.png'),
        },
      ],
    }
  },
  computed: {},
  watch: {},
  methods: {},
  components: {},
  created() {},
  mounted() {},
}
</script>

<style lang="scss">
.banner-wrap {
  position: relative;
  /deep/.el-carousel__indicators--horizontal {
    top: 240px;
  }
  .banner {
    height: 100%;
    img {
      height: 100%;
      width: 100%;
      object-fit: cover;
    }
  }
  .banner-1_cotnet {
    position: absolute;
    top: 30%;
    left: 50%;
    transform: translate(-50%, -30%);
    .title {
      font-size: 48px;
      color: white;
      margin-bottom: 30px;
      img {
        width: 140px;
        object-fit: cover;
      }
    }
    .search-box {
      height: 55px;
      background-color: white;
      border-radius: 50px;
      overflow: hidden;
      width: 78%;
      margin: 0 auto;
      position: relative;
      .search-input {
        font-size: 15px;
        width: 100%;
        margin-left: 60px;
        height: 100%;
      }
      .iconfont {
        position: absolute;
        right: 25px;
        top: 14px;
        font-size: 25px;
      }
    }
    .hot-key-wrap {
      width: 78%;
      margin: 0 auto;
      color: white;
      font-size: 16px;
      text-align: left;
      margin-top: 15px;
    }
  }
  .card-list {
    position: absolute;
    bottom: -200px;
    width: 80%;

    .card {
      width: 330px;
      height: 350px;
      margin: 0 38px;
      line-height: 20px;
      border-radius: 8px;
      text-align: center;
      padding: 40px 43px;
      box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.1);
      border: 1px solid rgba(255, 255, 255, 0);
      background: white;
      img {
        width: 100px;
        height: 100px;
      }
    }
  }
}
</style>
