<template>
  <div class="wrapper">
    <div class="banner">
      <div class="inner">
        <div class="list_flex align_items">
          <div class="box_flex_1 info">
            <div class="title">短视频智能创作系统</div>
            <div class="desc">运用人工智能技术和视频云端处理技术，提供自动创作和辅助创作的能力，全面提升内容创作效率。通过简单的在线编辑，即可创作出多种形式的短视频，让内容运营人员和短视频创作者0基础快速上手，高效创作生产短视频。</div>
            <div class="btn" @click="gotoPage('/jianshi-video/quick-script-create')">立即使用</div>
          </div>
          <div style="width: 600px;padding: 0 130px;">
            <img src="../../assets/images/guide/sc_hb.png">
          </div>
        </div>
      </div>
    </div>

    <autoSlider v-for="(item, index) in datalist" :key="index" :infos="item" />

    <div style="padding: 20px 0 100px;">
      <div class="inner">
        <div class="all_title">
          <div class="title"><span>更多视频工具 让你的创作再无拘束</span></div>
          <div class="desc" style="font-size: 18px;margin-top: 30px;">多场景适用，智能创作随时待命</div>
        </div>
      </div>
      <videoCase />
    </div>

    <Footer />

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import autoSlider from './components/autoSlider'
import videoCase from './components/videoCase'
import Footer from './components/footer'

export default {
  components: {
    autoSlider,
    videoCase,
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
          ico: require('../../assets/images/guide/ico_sc01.png'),
          title: '图文资讯 一键转视频',
          desc: '公众号文章秒变视频号',
          tabs: [
            {
              title: '获取图文链接',
              pic: require('../../assets/images/guide/sc_1_1.png')
            },
            {
              title: '系统识别内容',
              pic: require('../../assets/images/guide/sc_1_2.png')
            },
            {
              title: '生成视频',
              pic: require('../../assets/images/guide/sc_1_3.png')
            }
          ]
        },
        {
          contentStyle: {
            'background-color': '#f6f7f9'
          },
          boxStyle: {
            'flex-flow': 'row-reverse'
          },
          ico: require('../../assets/images/guide/ico_sc02.png'),
          title: '文本式 智能视频创作',
          desc: '编辑文本内容 自由添加素材',
          tabs: [
            {
              title: '编撰文本内容',
              pic: require('../../assets/images/guide/sc_2_1.png')
            },
            {
              title: '自由添加素材',
              pic: require('../../assets/images/guide/sc_2_2.png')
            },
            {
              title: '生成视频',
              pic: require('../../assets/images/guide/sc_2_3.png')
            }
          ]
        },
        {
          ico: require('../../assets/images/guide/ico_sc03.png'),
          title: '丰富的辅助创作功能',
          desc: '视频效果可根据用户需求自定义',
          tabs: [
            {
              title: '智能配音',
              pic: require('../../assets/images/guide/sc_3_1.png')
            },
            {
              title: '多样视频模板',
              pic: require('../../assets/images/guide/sc_3_2.png')
            },
            {
              title: '背景音乐',
              pic: require('../../assets/images/guide/sc_3_3.png')
            },
            {
              title: '视频素材',
              pic: require('../../assets/images/guide/sc_3_4.png')
            },
            {
              title: '灵活自定义',
              pic: require('../../assets/images/guide/sc_3_5.png')
            }
          ]
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
@import './css/index.scss';

.wrapper {
  background-color : #FFFFFF;
}

.banner {
  color : #1D1D1D;
  background-color : rgba(238, 242, 254, 1);
  padding : 50px 0;

  .title {
    font-size : 48px;
    font-weight : 600;
  }

  .desc {
    font-size : 14px;
    margin-top : 30px;
    line-height : 1.5;
  }

  .btn {
    margin-top : 30px;
  }
}

.content_a {
  padding : 0 0 100px;
}

</style>
