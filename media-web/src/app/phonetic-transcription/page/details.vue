<!--
 * @Author: your name
 * @Date: 2021-09-01 14:03:39
 * @LastEditTime: 2021-09-03 17:55:56
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/phonetic-transcription/details.vue
-->
<template>
  <div class="container details">
    <title-bar title="语音转写" />
    <div class="title">
      {{ title }}
    </div>
    <div v-if="swiperImg.length" class="images">
      <img :src="activeImage" alt="">
      <swiper ref="mySwiper" :options="SwiperOptions" class="swiper-list">
        <swiper-slide v-for="(item,index) in swiperImg" :key="index" class="item-slide">
          <img :src="item" alt="" class="item-img" @click="activeImage=item">
        </swiper-slide>

        <div slot="button-prev" class="swiper-button-prev" />
        <div slot="button-next" class="swiper-button-next" />
      </swiper>
    </div>

    <el-input
      v-model="text"
      size="normal"
      type="textarea"
      class="textarea"
      :autosize="{ minRows: 10, maxRows: 10 }"
      resize="none"
    />
    <div class="btn-wrap">
      <el-button type="primary" size="small" @click="preserve">保存</el-button>
      <el-button type="info" size="small" @click="cancel">取消</el-button>
    </div>

  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    const _this = this
    return {
      title: '',
      types: '',
      activeImage: '', // 当前选择图片

      SwiperOptions: {
        speed: 1000, // 匀速时间
        // loop: true,
        slidesPerView: 5,
        spaceBetween: 10,
        slidesOffsetBefore: 70,
        slidesOffsetAfter: 70,
        slideToClickedSlide: true,
        // centeredSlides: true,
        navigation: {
          nextEl: '.swiper-button-next', // 前进按钮的css选择器或HTML元素。
          prevEl: '.swiper-button-prev', // 后退按钮的css选择器或HTML元素。
          disabledClass: 'my-button-disabled' // 前进后退按钮不可用时的类名。
        },
        on: {
          slideChangeTransitionStart: function() {
            // console.log(this.activeIndex)
            // _this.activeImage = _this.swiperImg[this.activeIndex]
          }
        }
      },
      swiperImg: [],
      text: ''

    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.initDetail()
  },
  mounted() {

  },
  methods: {
    async initDetail() {
      const { err, res } = await this.$get(`/voice_txt/${this.$route.query.id}`)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      const data = res.data
      this.text = data.content
      this.title = data.title
      this.swiperImg = data.images.split(',').filter(item => item.trim())
      this.activeImage = this.swiperImg[0]
      this.types = data.types
    },
    async preserve() {
      const { err, res } = await this.$put(`/voice_txt/${this.$route.query.id}`, { content: this.text })
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.$message.success('保存成功')
      this.cancel()
    },
    cancel() {
      history.go(-1)
    }
  }
}
</script>

<style scoped lang="scss">
.details {
  padding-top: 30px;
  padding-bottom: 30px;

  .title {
    font-size: 14px;
    font-weight: 600;
    color: #404040;
    line-height: 14px;
    margin-top: 20px;
  }

  .images {
    width: 846px;
    height: 475px;
    position: relative;
    margin-top: 20px;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .swiper-button-prev ,
    .swiper-button-next {
      color: #fff;

      &.my-button-disabled {
        display: none;
      }
    }

    .swiper-list {
      position: absolute;
      bottom: 0;
      width: 100%;
      height: 130px ;
      padding-top: 20px;
      background: rgba(0,0,0,.4);

      .swiper-wrapper {
        display: flex;
        align-items: center;
      }

      .item-slide {
        // width: 160px!important;
        height: 90px;

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }
    }
  }

  .textarea {
    width: 846px;
    height: 250px;
    margin-top: 20px;

    ::v-deep.el-textarea__inner {
      // background: #f7f8f9;
      border-radius: 4px;
    }
  }

  .btn-wrap {
    width: 846px;
    text-align: right;
  }
}
</style>
