<!--
 * @Author: your name
 * @Date: 2021-10-26 20:42:12
 * @LastEditTime: 2021-11-05 17:28:37
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/product-manage/components/PreviewDialog.vue
-->
<template>
  <div class="preview-dialog-wrap">
    <base-image :src="previewData.main_img" class="main-img" />
    <div class="preview-main">
      <div class="preview-main-head">
        <!-- <div class="bg"> -->
        <div class="title-wrap">
          <p class="name">{{ previewData.name }}</p>
          <p class="intro">{{ previewData.intro }}</p>
        </div>
        <div class="price-wrap">
          <p class="price">￥{{ previewData.price }}<span v-if="previewData.type=='竞拍'">起</span></p>
        </div>
        <div class="buy-wrap">
          <div v-if="previewData.type!=='竞拍'" class="buy-icon">
            <svg-icon icon-class="shop-cart" />
          </div>
          <p class="buy-text">立即购买</p>
        </div>
        <!-- </div> -->
      </div>
      <div class="preview-main-content">
        <div v-if="previewData.type=='盲盒'" class="blind-wrap">
          <div v-for="(item,index) in previewData.blind_box" :key="index" class="blind-item">
            <base-image :src="item.img" />
            <p class="name">{{ item.name }}</p>
            <p class="desc">{{ item.des }}</p>
          </div>
        </div>
        <div class="desc-wrap">
          <p>{{ previewData.des }}</p>
        </div>
        <div class="detail-wrap">
          <div v-for="(item,index) in previewData.detail_img" :key="index" class="detail-item">
            <base-image :src="item" />
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    previewData: {
      type: Object,
      default() {
        return {}
      }
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
.preview-dialog-wrap {
  //   padding : 30px;
  .main-img {
    height : 510px;
  }
  .preview-main {
    border-radius : 33px 33px 0px 0px;
    overflow : hidden;
    margin-top : -84px;
    position : relative;
    z-index : 9;
    border : 1px solid #FFFFFF;
    background : rgba(255, 255, 255, .6);
    .preview-main-head {
      padding : 24px 22px;
      display : flex;
      align-items : center;
      position : relative;
      // background : rgba(255, 255, 255, .6);
      backdrop-filter : blur(10px);
      .title-wrap {
        flex : 1;
        width : 0;
        .name {
          font-size : 22px;
          font-weight : 600;
          color : #2F3A48;
          line-height : 22px;
          overflow : hidden;
          text-overflow : ellipsis;
          white-space : nowrap;
        }
        .intro {
          font-size : 14px;
          font-weight : 400;
          color : #ABBDD2;
          line-height : 14px;
          margin-top : 6px;
        }
      }
      .price-wrap {
        margin-left : 27px;
        .price {
          font-size : 29px;
          font-weight : 400;
          color : #FC3D7D;
          line-height : 29px;
          span {
            font-size : 14px;
            color : #404040;
            margin-left : 10px;
          }
        }
      }
      .buy-wrap {
        display : flex;
        background : linear-gradient(180deg, #FF8AB2 0%, #FF4582 83%, #FC3D7D 100%);
        box-shadow : 0px 12px 24px 0px rgba(255,0,85,.2), 0px 6px 12px -6px rgba(255,0,85,.6);
        border-radius : 60px;
        height : 34px;
        margin-left : 18px;
        .buy-icon {
          background : linear-gradient(180deg, #FE6C9E 0%, #ED0452 100%);
          border-radius : 60px 0px 0px 60px;
          padding : 10px 17px;
          .svg-icon {
            display : block;
            width : 14px;
            height : 14px;
          }
        }
        .buy-text {
          font-size : 17px;
          font-weight : 400;
          color : #FFFFFF;
          line-height : 17px;
          padding : 8px 12px;
        }
      }
    }
    .preview-main-content {
      margin-top : 12px;
      padding : 0 22px;
      .blind-wrap {
        padding-bottom : 24px;
        overflow-x : auto;
        display : flex;
        .blind-item {
          min-width : 163px;
          max-width : 163px;
          padding : 18px;
          background : linear-gradient(270deg, #F9F9F9 0%, #FFFFFF 100%);
          box-shadow : 0px 12px 24px 0px rgba(0,0,0,.02), 0px 12px 24px -18px rgba(0,0,0,.1);
          border-radius : 22px;
          margin-right : 11px;
          backdrop-filter : blur(10px);
          .el-image {
            height : 127px;
            margin-bottom : 12px;
          }
          .name {
            font-size : 17px;
            font-weight : 600;
            color : #2F3A48;
            line-height : 17px;
            margin-bottom : 8px;
            text-align : center;
            white-space : nowrap;
            overflow : hidden;
            text-overflow : ellipsis;
          }
          .desc {
            font-size : 11px;
            font-weight : 400;
            color : #2F3A48;
            line-height : 11px;
            text-align : center;
            white-space : nowrap;
            overflow : hidden;
            text-overflow : ellipsis;
          }
          //   filter : blur(10px);
        }
      }
      .desc-wrap {
        background : #F5F4F4;
        border-radius : 22px;
        padding : 18px;
        margin-bottom : 18px;
        p {
          font-size : 14px;
          font-weight : 400;
          color : #9F9F9F;
          line-height : 22px;
        }
      }
      .detail-wrap {
        .detail-item {
          margin-bottom : 18px;
        }
      }
    }
  }
}

</style>
