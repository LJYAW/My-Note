<template>
  <div class="">
    <div class="home-page-1 page-wrap d-flex flex-wrap" style="padding-top:0px">
      <div class="container d-flex">
        <div class="left">
          <div>
            <div class="d-flex align-items-center">
              <img src="../../assets/images/homepage/shanchuang_black.png" height="64" />
              <div style="width: 42px;height:3px" class="bc-c ml-30px"></div>
            </div>
            <p class="fz-26px">有文本就能快速创作短视频</p>
          </div>

          <div class="mt-45px">
            <p class="fc-999 mb-24px" style="line-height: 24px">
              微剪与百度好看视频和百度人工智能实验室联合共研，运用先进的人工智能图文视频识别技术，结合微剪海量视频素材资源库，让短视频创作轻而易举。
            </p>
            <div class="d-flex flex-wrap">
              <span class="tag">图文链接一键转视频</span>
              <span class="tag">文本自动语言朗读</span>
              <span class="tag">智能文本识别</span>
              <span class="tag">本地素材上传</span>
              <span class="tag">文本自动摘要</span>
              <span class="tag">自定义片头片尾</span>
            </div>
          </div>

          <!-- <div class="btn cp">
            马上制作
            <img src="../../assets/images/homepage/right_icon.png">
          </div> -->
          <div class="d-flex">
            <img src="../../assets/images/homepage/footer_qr.png" style="width:192px;height:192px;" />
            <div class="wxcode">
              微信扫码 进入简视小程序 手机即可便捷操作
            </div>
          </div>
        </div>

        <div class="right d-flex flex-wrap">
          <div v-for="(img, index) in 9" :span="8" :key="index">
            <div class="text-center video-list-wrap"></div>
          </div>
        </div>
      </div>
    </div>

    <div class="home-page-2 page-wrap d-flex flex-wrap">
      <div class="page-2-bg"></div>
      <div class="container d-flex">
        <div class="left">
          <div>
            <div class="">
              <p class="fz-28px fc-white">海量素材资源库</p>
              <div style="width: 42px;height:3px" class="bc-c mt-15px"></div>
            </div>
          </div>

          <div class="mt-45px">
            <p class="fc-white mb-24px" style="line-height: 24px">
              全国300多家电视频道7X24小时闪编收录，第一时间获取企业相关资讯视频，包罗万象海量视频资源，人工智能精准搜索定位，助力企业短视频运营。
            </p>
            <div class="d-flex flex-wrap" style="padding:60px 0">
              <span class="tag tag_color">1.4亿条短视频资源</span>
              <span class="tag tag_color">1.3千万档视频节目资源</span>
              <span class="tag tag_color">按需定制推送推送</span>
              <span class="tag tag_color">在线剪辑下载</span>
            </div>
          </div>

          <!-- <div class="btn cp">
            搜索下载
            <img src="../../assets/images/homepage/right_icon.png">
          </div> -->
          <div class="contact">
            <div class="contactour">
              <img src="../../assets/images/homepage/phone.png" alt="" />
              <p>联系我们获取定制素材库</p>
            </div>
            <div class="telephone">
              010-12345678
            </div>
          </div>
          <!-- <div class="">
            <div class="btn-2 cp d-flex justify-content-center align-items-center">
              <img src="../../assets/images/homepage/dingzhi_icon.png"
                class="mr-15px"
                width="17"
                height="17">
              资源定制
            </div>
            <div class="btn-2 cp">
              <img src="../../assets/images/homepage/dingzhi_icon.png"
                class="mr-15px"
                width="17"
                height="17">
              资源上架
            </div>
          </div> -->
        </div>

        <div class="right">
          <div class="mb-40px d-flex">
            <img src="../../assets/images/homepage/hot_icon.png" style="vertical-align: bottom;" width="20" height="20" class="mr-10px" />
            <span class="fz-20px fc-white ">
              热门素材
            </span>
            <ul class="material_list">
              <li v-for="(material_item, material_index) in material" :key="material_index">{{ material_item.name }}</li>
            </ul>
          </div>
          <div class="d-flex flex-wrap">
            <div v-for="(img, index) in 6" :span="8" :key="index">
              <div class="text-center video-list-wrap1">
                <div class="duration">00:12:35</div>
                <img src="../../assets/images/is_empty.png" alt="" />
                <div class="title">视频标题视频标题</div>
                <div class="message d-flex w-100 justify-content-between">
                  <p>《车风尚》</p>
                  <p>安徽公共</p>
                </div>
                <div class="time message">2020-06-27 22:28:52</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- <div class="home-page-3 page-wrap d-flex flex-wrap">
      <div class="page-2-bg"></div>
      <div class="container d-flex">

        <div class="left">
          <div>
            <div class="">
              <p class="fz-28px">全网热门创作任务</p>
              <div style="width: 42px;height:3px"
                class="bc-c mt-15px"></div>
            </div>
          </div>

          <div class="mt-45px">
            <p class="mb-24px fc-999"
              style="line-height: 24px">全国300多家电视频道7X24小时闪编收录，第一时间获取企业相关资讯视频，包罗万象海量视频资源，人工智能精准搜索定位，助力企业短视频运营。</p>
            <div class="d-flex flex-wrap">
              <span class="tag">1.4亿条短视频资源</span>
              <span class="tag">1.3千万档视频节目资源</span>
              <span class="tag">按需定制推送推送</span>
              <span class="tag">在线剪辑下载</span>
            </div>
          </div>

          <div class="btn cp"
            style="width: 173px">
            做任务赚取佣金
            <img class="ml-10px"
              src="../../assets/images/homepage/right_icon.png">
          </div>

          <div class="btn cp mt-10px"
            style="margin-top: 10px">
            发布任务
            <img class="ml-10px"
              src="../../assets/images/homepage/right_icon.png">
          </div>

        </div>

        <div class="right">
          <div class="d-flex align-items-center title">
            <span class="recommend">推荐</span>
            <ul class="d-flex ml-10px fz-16px fc-999">
              <li class="mx-20px hove-c cp">微剪任务</li>
              <li class="mx-20px hove-c cp">百家号</li>
              <li class="mx-20px hove-c cp">头条号</li>
              <li class="mx-20px hove-c cp">企鹅号</li>
            </ul>

            <a class="fz-12px ml-auto">更多任务 <i class="iconfont iconicon align-center"></i> </a>
          </div>
          <div class="d-flex flex-wrap">
            <div v-for="(img,index) in 6"
              :span="8"
              :key="index">
              <div class="text-center video-list-wrap">
              </div>
            </div>
          </div>
        </div>

      </div>
    </div> -->
    <div class="strategy_partner">
      <div class="strategy_partnerbox">
        <div>战略合作伙伴</div>
        <div>
          <img src="../../assets/images/homepage/partner1.png" alt="" />
          <img src="../../assets/images/homepage/partner2.png" alt="" />
          <img src="../../assets/images/homepage/partner3.png" alt="" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      material: [
        {
          name: '孙俪'
        },
        {
          name: '张一山'
        },
        {
          name: '惠若琪'
        }
      ]
    }
  },
  computed: {},
  watch: {},
  methods: {},
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.strategy_partner {
  width: 100%;
  height: 100px;
  background-color: #ffffff;
  .strategy_partnerbox {
    display: flex;
    width: 55%;
    height: 100%;
    margin: 0 auto;
    line-height: 100px;
    img {
      width: 129px;
      height: 62px;
      margin-left: 20px;
    }
  }
}
.page-wrap {
  background-color: #fff;
  padding: 90px 102px 99px 107px;
  .left {
    flex: 1;
    margin-right: 20px;
    .wxcode {
      line-height: 24px;
      margin-top: 73px;
      font-size: 12px;
      margin-left: 10px;
    }
    .bc-c {
      background-color: $c;
    }
    .tag_color {
      background: #743042 !important;
    }
    .tag {
      background: #3a3a3a;
      border-radius: 15px;
      padding: 0px 18px;
      display: block;
      color: #fff;
      height: 30px;
      line-height: 30px;
      margin-bottom: 12px;
      margin-right: 18px;
    }
    .btn {
      background: $c;
      color: #fff;
      height: 43px;
      font-size: 16px;
      line-height: 43px;
      width: 140px;
      text-align: center;
      margin-top: 46px;
      img {
        width: 22px;
        height: 13px;
      }
    }
  }
  .right {
    .video-list-wrap {
      width: 226px;
      height: 136px;
      background: #ccc;
      margin: 10px;
    }
    .video-list-wrap1 {
      position: relative;
      width: 226px;
      // height: 136px;
      background: #ffffff;
      margin: 10px;
      padding: 15px;
      box-sizing: border-box;
      border-radius: 5px;
      img {
        width: 100%;
        height: 120px;
      }
      .title {
        color: #333333;
        font-size: 16px;
        font-weight: 400;
        text-align: left;
        margin-top: 10px;
        padding-bottom: 10px;
        border-bottom: 1px solid #dddddd;
      }
      .message {
        margin-top: 15px;
        text-align: left;
        color: #999999;
        font-size: 12px;
      }
      .duration {
        position: absolute;
        left: 20px;
        top: 96px;
        width: 105px;
        height: 26px;
        line-height: 26px;
        background: #333333;
        border-radius: 15px;
        color: #ffffff;
        opacity: 0.8;
      }
    }
    min-width: 810px;
    max-width: 810px;
  }
}
.home-page-2 {
  position: relative;
  background-image: url('../../assets/images/homepage/ziyuan_bg.png');
  background-repeat: no-repeat;
  background-size: cover;
  &.page-wrap {
    .left {
      .tag {
        background: rgba(58, 58, 58, 0.7);
      }
      .contact {
        color: #ffffff;
        margin-top: 50px;
        .telephone {
          font-size: 30px;
          font-weight: 700;
          margin-top: 10px;
        }
        .contactour {
          display: flex;
          font-size: 12px;
          p {
            margin-top: 10px;
            margin-left: 10px;
          }
          img {
            width: 33px;
            height: 33px;
          }
        }
      }
      .btn {
        margin-bottom: 46px;
      }
      .btn-2 {
        width: 348px;
        height: 55px;
        line-height: 55px;
        background: rgba(153, 153, 153, 0.65);
        border-radius: 4px;
        font-size: 15px;
        font-weight: 500;
        text-align: center;
        margin-bottom: 22px;
      }
    }
    .right {
      .material_list {
        display: flex;
        margin-left: 20px;
        li {
          margin-left: 10px;
          font-size: 12px;
          color: #ffffff;
          margin-top: 6px;
        }
      }
    }
  }
}
.home-page-3 {
  .title {
    margin-bottom: 20px;
    margin-left: 10px;
  }
  .recommend {
    font-weight: 500;
    font-size: 20px;
    position: relative;
    &::after {
      display: block;
      content: ' ';
      width: 40px;
      height: 3px;
      background: $c;
      position: absolute;
      bottom: -10px;
    }
  }
}
</style>
