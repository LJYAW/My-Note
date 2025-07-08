<template>
  <div class="wrapper">
    <div class="banner">
      <div class="inner">
        <div class="list_flex align_items">
          <div class="box_flex_1 info">
            <div class="title">视频管家</div>
            <div class="desc">新一代数字化媒资管理工作平台，专注为企业提供以视频资源为主的媒体资产云存储、云管理，运用人工智能技术实现媒资的智能分类、智能标签、智能搜索、在线智能剪辑，多账号分权限协同使用，多种应用工具助力企业高效视频内容运营。</div>
            <div class="btn whiter" @click="gotoPage('/home')">立即使用</div>
          </div>
          <div style="width: 460px;">
            <img src="../../assets/images/guide/vh_img.png">
          </div>
        </div>
      </div>
    </div>

    <div v-for="(item, index) in datalist" :key="index" class="content_a">
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
      tabslistactive: 0,
      datalist: [
        {
          ico: require('../../assets/images/guide/vh_ico_1.png'),
          title: 'AI视频搜索',
          subtitle: '文字与图像智能识别',
          desc: '通过对视频内容的逐帧图像分析识别，可快速搜索找到视频库中的人物、动物、物体、场景、品牌等出现在镜头。<br><br>通过规范的存储格式，智能的审核、编目技术，灵活自定义的编目模板、强大的搜索引擎技术以及高效云计算能力，实现媒体资料的集中管理和高效利用。媒资上云后即时自动内容分析，精确到帧的视频搜索技术，让内容创作快速找到所需内容。支持素材的整段下载和在线片段提取下载。',
          pic: require('../../assets/images/guide/vh_1.png')
        },
        {
          ico: require('../../assets/images/guide/vh_ico_2.png'),
          title: '人工智能视频内容分析',
          subtitle: 'AI数据分析 解放大脑',
          desc: '专为视频应用管理优化的云服务器，基于大规模分布式集群下智能调度，高速读写，自由扩容。企业视频资料上云集中管理，通过编目、分类、智能化标签、索引，将视频资料转化为企业数字资产。',
          pic: require('../../assets/images/guide/vh_2.png')
        },
        {
          ico: require('../../assets/images/guide/vh_ico_3.png'),
          title: '视频安全管理',
          subtitle: '海量数据云端集中存储 安全无忧',
          desc: '海量数据云端集中存储，统一管理，稳定、安全、高效，将数据的传输、存储、处理和发布四个环节有机融为一体。分级分权限内容管理，使用纪录清晰可见。人员变动快速资产文件交接，企业文化资产永不丢失。',
          pic: require('../../assets/images/guide/vh_3.png')
        },
        {
          ico: require('../../assets/images/guide/vh_ico_4.png'),
          title: '企业专属视频云',
          subtitle: '专为企业定制',
          desc: '专为视频媒资应用管理优化的云服务器，基于大规模分布式集群下智能调度，高速读写，自由扩容。企业视频资料上云集中管理，通过编目、分类、智能化标签、索引，将视频资料转化为企业数字资产。',
          pic: require('../../assets/images/guide/vh_4.png')
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
      const routerPath = this.roles.includes('视频管家') ? path : '/pay-qr'
      if (!this.userInfo.mobile) {
        this.$bus.$emit('openLogin', path)
      } else {
        this.$router.push({
          path: routerPath
        })
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
  background: url("../../assets/images/guide/vh_hb.png") center;
  background-size: cover;
  padding: 90px 0;
  color: #fff;

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

  &:nth-child(2n+1) {
    background: #f6f7f9;

    .box {
      flex-flow: row-reverse;
    }

    .info {
      padding-left: 200px;
      padding-right: 0px;
    }
  }

  .imgbox {
    width: 400px;
  }

  .info {
    padding-right: 200px;

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
      line-height: 1.4;
      font-size: 14px;
      margin-top: 30px;
      color: #939393;
    }
  }
}

</style>
