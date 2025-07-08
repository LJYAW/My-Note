<!--
 * @Author: your name
 * @Date: 2021-10-21 15:04:00
 * @LastEditTime: 2021-11-05 12:00:05
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/components/buy-card.vue
-->
<template>
  <div class="buy-card-wrap">

    <div v-if="baseData.id" class="buy-card">
      <div class="img-wrap">
        <!-- <img v-if="imgSrc" :src="baseData.imgSrc" alt=""> -->
        <BaseImage key="cover" :src="baseData.main_img" />
      </div>

      <div class="card-footer">
        <div class="des">
          <span class="title">{{ baseData.name }}</span>

          <div class="subtitle-wrap">
            <span class="sub-title">{{ baseData.intro }}</span>
            <span class="price">{{ price }}</span>
          </div>
        </div>

        <!-- <div class="btns" /> -->
        <div> ￥{{ baseData.price }}</div>
      </div>
    </div>

    <div v-else class="buy-card">
      <div class="img-wrap">
        <img v-if="imgSrc" alt="">
      </div>

      <div class="card-footer">
        <div class="des">
          <span class="title">{{ title }}</span>

          <div class="subtitle-wrap">
            <span class="sub-title">{{ subTitle }}</span>
            <span class="price">{{ price }}</span>
          </div>
        </div>

        <div class="btns" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    imgSrc: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: '标题'
    },
    subTitle: {
      type: String,
      default: '副标题'
    },
    price: {
      type: Number,
      default: 99
    },
    baseData: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {

    }
  },
  computed: {

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
.buy-card-wrap{
  width: 100%;
  height: 100%;
}
.buy-card {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  background: #D8D8D8;
  border-radius: 18px;
  overflow: hidden;

  .img-wrap {
    flex: 1;
    height: 0;
  }
  .card-footer {
    // height: 50px;
    padding: 10px 12px 14px 10px;
    background-color: #fff;
    display: flex;
    justify-content: space-between;
    background: linear-gradient(270deg, #F9F9F9 0%, #FFFFFF 100%);
    .des {
      overflow: hidden;
    }
  }
  .title {
    font-weight: 600;
    color: #2F3A48;
    margin-bottom: 5px;
  }
  .des {
    color: #ABBDD2;
    font-size: 12px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    .subtitle-wrap{
      display: flex;
      align-items: center;
    }
    .sub-title{
      display: inline-block;
      width: 40px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .price{
      display: inline-block;
    }
  }
  .btns {
    width: 24px;
    height: 24px;
    border-radius: 50%;
    background: linear-gradient(180deg, #FF8AB2 0%, #FF4582 83%, #FC3D7D 100%);
    box-shadow: 0px 20px 40px 0px rgba(255, 0, 85, 0.2), 0px 10px 20px -4px rgba(255, 0, 85, 0.6);
  }
}
</style>
