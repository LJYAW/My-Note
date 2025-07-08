<!--
 * @Author: zll
 * @Date: 2020-12-04 11:00:34
 * @LastEditTime: 2020-12-16 15:42:12
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/home_pages/page.vue
-->
<template>
  <div class="page_warp">
    <img src="../../assets/images/home_page/roght_top.png" alt="" class="right_top">
    <img src="../../assets/images/home_page/left_bottom.png" alt="" class="left_bottom">
    <div class="title">
      <p>百度AI全面赋能</p>
      <div>微剪与百度云联合打造，将人工智能技术应用于视频创作，提高创作效率</div>
    </div>
    <div class="page_list container">
      <ul>
        <li v-for="(item,index) in page_list" :key="index">
          <div class="message">
            <div class="arrow_right"></div>
            <div class="title">{{item.title}}</div>
            <div class="label">{{item.label}}</div>
          </div>
          <img :src="item.url" alt="">
        </li>
      </ul>
    </div>
    <div class="strategy_partner">
      <div class="strategy_partnerbox container">
        <div class="callurs">
          <div style="width:200px;color:#666666;font-size:20px;opacity:0.4;line-height:200px">战略合作伙伴</div>
          <div class="img_list">
            <img :src="img_item.url" alt="" v-for="(img_item,item_index) in strategy_partner" :key="item_index">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: '',
  props: {},
  data() {
    return {
      strategy_partner: [
        {
          url: require('../../assets/images/home_page/partner2.png')
        },
        {
          url: require('../../assets/images/homepage/partner1.png')
        },
        {
          url: require('../../assets/images/home_page/partner3.png')
        }
      ],
      page_list: [
        {
          title: '智能文章摘要',
          label: '人工智能文章内容识别分析，将长文章快速摘要成短文章，更适合视频化输出',
          url: require('../../assets/images/home_page/page1.png')
        },
        {
          title: '热点资讯推荐',
          label: '全网热门资讯推荐，热度话题第一时间视频转化',
          url: require('../../assets/images/home_page/page2.png')
        },
        {
          title: '智能关键词提取',
          label: '智能分析文章内容，提取关键词，匹配素材更便捷',
          url: require('../../assets/images/home_page/page3.png')
        },
        {
          title: '智能语音播报',
          label: '多种语音播报音色，自由选择适配不同类型的视频内容',
          url: require('../../assets/images/home_page/page4.png')
        }
      ]
    }
  },
  methods: {},
  mounted() {},
  created() {},
  components: {},
  watch: {}
}
</script>

<style lang='scss' scoped>
.page_warp {
  position: relative;
  background: #3163d2;
  margin-top: 40px;
  .right_top {
    position: absolute;
    top: 0;
    right: 210px;
    width: 161px;
    height: 156px;
    object-fit: cover;
  }
  .left_bottom {
    position: absolute;
    bottom: 189px;
    left: 220px;
    width: 215px;
    height: 74px;
    object-fit: cover;
  }
  .title {
    padding-top: 30px;
    p {
      font-size: 30px;
      font-weight: 500;
      margin-top: 30px;
      text-align: center;
      color: #ffffff;
      font-family: PingFang SC, PingFang SC-Bold;
    }
    div {
      text-align: center;
      color: #ffffff !important;
      font-size: 12px;
      margin-top: 20px;
    }
  }
  .page_list {
    ul {
      width: 75%;
      margin: 30px auto;
      margin-bottom: 50px;
      display: flex;
      flex-wrap: wrap;
      li {
        display: flex;
        width: calc(100% / 2 - 23px);
        height: 256px;
        margin-right: 23px;
        margin-top: 23px;
        background: #3b6cd9;
        padding-left: 10px;
        box-sizing: border-box;
        .message {
          width: 50%;
          color: #ffffff;
          position: relative;
          padding-left: 15px;
          .arrow_right {
            position: absolute;
            top: 30px;
            left: 0;
            width: 0;
            height: 0;
            border-bottom: 5px solid transparent; /* left arrow slant */
            border-top: 5px solid transparent; /* right arrow slant */
            border-left: 5px solid #51eaed; /* bottom, add background color here */
            font-size: 0;
            line-height: 0;
          }
          .title {
            font-size: 16px;
            font-weight: 300;
          }
          .label {
            font-size: 12px;
            margin-top: 10px;
            line-height: 17px;
            opacity: 0.6;
          }
        }
        img {
          width: 50%;
          height: 100%;
        }
      }
    }
  }
  .strategy_partner {
    width: 100%;
    background-color: #ffffff;
    .strategy_partnerbox {
      height: 200px;
      border-bottom: 1px solid #f0f0f0;
      line-height: 200px;
      .callurs {
        display: flex;
        width: 75%;
        height: 100%;
        margin: 0 auto;
        line-height: 100px;
        .img_list {
          display: flex;
          // justify-content: space-between;
          width: calc(100% - 200px);
          align-self: center;
          img {
            width: 220px;
            height: 70px;
            margin-left: 26px;
          }
          img:last-child {
            width: 150px;
            height: 70px;
          }
        }
      }
    }
  }
}
@media screen and (max-width: 1600px) {
  .right_top {
    right: 0 !important;
  }
  .left_bottom {
    left: 0 !important;
  }
}
@media screen and (min-width: 1600px) and (max-width: 1920px) {
  .right_top {
    right: 189px !important;
  }
  .left_bottom {
    left: 210px !important;
  }
}
@media screen and (min-width: 1920px) {
  .right_top {
    right: 300px !important;
  }
  .left_bottom {
    left: 340px !important;
  }
}
</style>