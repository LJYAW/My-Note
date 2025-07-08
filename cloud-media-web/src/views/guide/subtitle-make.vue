<template>
  <div class="wrapper">
    <div class="banner">
      <div class="inner">
        <div class="list_flex align_items">
          <div class="box_flex_1 info">
            <div class="title">智能字幕识别创作系统</div>
            <div class="desc">视频语音识别生成字幕，多国语言识别翻译，让视频创作无国界。</div>
            <div class="btn" @click="gotoPage('/jianshi-video/add-videos')">立即使用</div>
          </div>
          <div style="width: 600px;padding: 0 130px;">
            <img src="../../assets/images/guide/sm_hb.png">
          </div>
        </div>
      </div>
    </div>

    <div v-for="(item, index) in datalist" :key="index" :class="item.rowReverse?'row-reverse':''" class="content_a">
      <div class="inner">
        <div class="box list_flex align_items">
          <div class="box_flex_1 info">
            <img class="ico" :src="item['ico']">
            <div class="title">{{ item.title }}</div>
            <div class="subtitle">{{ item.subtitle }}</div>
            <div class="desc" v-html="item.desc" />
          </div>
          <div class="imgbox">
            <img :src="item.pic">
          </div>
        </div>
      </div>
    </div>

    <!-- <div style="padding: 20px 0 100px;">
      <div class="inner">
        <div class="all_title">
          <div class="title"><span>更多视频工具 让你的创作再无拘束</span></div>
          <div class="desc" style="font-size: 18px;margin-top: 30px;">多场景适用，智能创作随时待命</div>
        </div>
      </div>
      <videoCase />
    </div> -->

    <Footer />

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import videoCase from './components/videoCase'
import Footer from './components/footer'

export default {
  components: {
    // videoCase,
    Footer
  },
  props: {

  },
  data() {
    return {
      redirect: '',
      tabslistactive: 0,
      datalist: [
        {
          ico: require('../../assets/images/guide/ico_sc02.png'),
          title: '视频字幕快速提取功能',
          subtitle: '导入视频文件 识别语音',
          desc: '无论是没有字幕的视频文件，或是带有内嵌硬字幕的视频文件，都可以通过技术分析后精准识别出字幕内容，并支持在线编辑和导出',
          pic: require('../../assets/images/guide/sm_1.png'),
          rowReverse: true
        },
        {
          ico: require('../../assets/images/guide/ico_sc02.png'),
          title: '字幕多语言实时翻译',
          subtitle: '实时翻译 拒绝繁琐',
          desc: '提取视频字幕过程中，可以选择将原字语言幕实时翻译成中，英，法，日，俄等多种语言，并支持双语展示',
          pic: require('../../assets/images/guide/sm_2.png'),
          rowReverse: true
        },
        {
          ico: require('../../assets/images/guide/ico_sc03.png'),
          title: '视频内容二次创作',
          subtitle: '不同素材自由搭配 助力快速创作',
          desc: '可以将已加入字幕的视频进行再创作，加入背景音乐，片头片尾，角标水印等素材，包括替换视频原语音为新的个性化音色',
          pic: require('../../assets/images/guide/sm_3.png')
        }
      ]
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
  color: #1d1d1d;
  background-color: rgba(238, 242, 254, 1);
  padding: 50px 0;

  .info {
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

  &.row-reverse {

    .box {
      flex-flow: row-reverse;
    }

    .info {
      padding-left: 150px;
      padding-right: 0px;
    }
  }

  &:nth-child(2n+1) {
    background: #f6f7f9;
  }

  .imgbox {
    width: 710px;
  }

  .info {
    padding-right: 150px;

    .ico {
      width: 40px;
      height: 40px;
    }

    .title {
      font-weight: bold;
      font-size: 30px;
      margin-top: 10px;
    }

    .subtitle {
      font-size: 18px;
      margin-top: 30px;
    }

    .desc {
      line-height: 1.5;
      font-size: 14px;
      margin-top: 30px;
      color: #939393;
    }
  }
}
</style>
