<template>
  <div class="wrapper">
    <div class="banner">
      <div class="inner">
        <div class="list_flex align_items">
          <div class="box_flex_1 info">
            <div class="title">智能语音创作</div>
            <div class="desc">通过音频文件精准识别语音中的内容，生成字幕并搭配素材快速创作视频</div>
            <div class="btn" style="margin-top: 70px;" @click="gotoPage('/jianshi-video/add-videos-phonetic')">立即使用</div>
          </div>
          <div style="width: 568px;">
            <img src="../../assets/images/guide/pm_h.png">
          </div>
        </div>
      </div>
    </div>

    <div class="content_a">
      <div class="inner">
        <div class="all_title">
          <div class="title"><span>智能语音快速转写 让您的创作先人一步</span></div>
          <div class="desc" style="font-size: 18px;margin-top: 30px;">支持多种文件格式，无需转码即时生成</div>
        </div>
        <div class="listcol3 intr">
          <div class="col">
            <div class="item list_flex">
              <div class="ico"><img src="../../assets/images/guide/pm_ico_1.png"></div>
              <div class="info">
                <div class="t">语音识别</div>
                <div class="d">先进的语音识别技术 精准识别信息</div>
              </div>
            </div>
          </div>
          <div class="col">
            <div class="item list_flex">
              <div class="ico"><img src="../../assets/images/guide/pm_ico_2.png"></div>
              <div class="info">
                <div class="t">精准切分字幕断句</div>
                <div class="d">自动识别语音内容 智能内容切分</div>
              </div>
            </div>
          </div>
          <div class="col">
            <div class="item list_flex">
              <div class="ico"><img src="../../assets/images/guide/pm_ico_3.png"></div>
              <div class="info">
                <div class="t">快速自由创作</div>
                <div class="d">快速对对识别结果进行二次编辑</div>
              </div>
            </div>
          </div>
        </div>
        <div class="all_title">
          <div class="title"><span>应用场景</span></div>
        </div>
        <div class="listcol3 scenario">
          <div class="list_flex">
            <div class="col">
              <div class="box_item">
                <div class="pic"><img src="../../assets/images/guide/pm_s_1.png"></div>
                <div class="title">通过采访录音创作视频</div>
              </div>
            </div>
            <div class="col">
              <div class="box_item">
                <div class="pic"><img src="../../assets/images/guide/pm_s_2.png"></div>
                <div class="title">会议录音生成视频</div>
              </div>
            </div>
            <div class="col">
              <div class="box_item">
                <div class="pic"><img src="../../assets/images/guide/pm_s_3.png"></div>
                <div class="title">音频文件替换原声再创作</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <Footer />

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Footer from './components/footer'

export default {
  components: {
    Footer
  },
  props: {

  },
  data() {
    return {
      redirect: '',
      tabslistactive: 0
    }
  },
  computed: {
    ...mapGetters([
      'roles',
      'userInfo'
    ])
  },
  watch: {

  },
  beforeDestroy() {
  },
  created() {
  },
  mounted() {
  },
  methods: {
    gotoPage(path) {
      if (this.roles && this.roles.length > 0) {
        // this.$router.push(path)
        const { href } = this.$router.resolve({
          path: path,
          query: {}
        })
        window.open(href, '_blank')
      } else {
        this.$bus.$emit('openLogin', path)
      }
    }
  }
}
</script>
<style scoped lang="scss">
@import "./css/index.scss";

.wrapper {
  background-color: #fff;
}

.banner {
  background: #eef2fe;
  padding: 48px 0;

  .info {
    padding-right: 170px;
  }

  .title {
    font-size: 48px;
    font-weight: 600;
  }

  .desc {
    font-size: 14px;
    margin-top: 30px;
    line-height: 1.5;
  }

  .btn {
    margin-top: 30px;
  }
}

.content_a {
  padding: 100px 0;
}

.intr {
  padding-bottom: 100px;

  .item {
    background: #fff;
    box-shadow: 0px 6px 20px 0px rgba(86, 117, 232, .1);
    border-radius: 4px;
    padding: 30px;

    .ico {
      width: 50px;
    }

    .info {
      padding-left: 20px;

      .t {
        font-weight: bold;
        font-size: 24px;
      }

      .d {
        margin-top: 10px;
        font-size: 14px;
        color: #a0a0a0;
      }
    }
  }
}

.scenario {

  .title {
    margin-top: 20px;
    font-size: 18px;
    text-align: center;
  }
}

</style>
