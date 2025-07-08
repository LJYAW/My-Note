<template>
  <div class="wrapper">
    <div class="banner">
      <img src="../../assets/images/guide/g_bg.png">
    </div>

    <div class="content_a">
      <div class="inner">
        <div class="all_title">
          <div class="title"><span>创作工具</span>&nbsp;&nbsp;简单易用高效智能创作</div>
          <div class="desc">人工智能技术赋能视频创作及视频管理，简单</div>
        </div>
        <div class="tabs list_flex">
          <div
            v-for="(item,index) in tabslist"
            :key="index"
            :class="index === tabslistactive?'active':''"
            class="list_flex align_items item"
            @click="tabslistactive = index"
          >
            <img v-if="index === tabslistactive" class="ico" :src="item.ico_o">
            <img v-else class="ico" :src="item.ico">
            <div>{{ item.name }}</div>
          </div>
        </div>
        <div class="tabscon list_flex">
          <div class="box_flex_1 limg">
            <img :src="tabslist[tabslistactive]['img']">
          </div>
          <div class="box_flex_1 info">
            <div class="title">{{ tabslist[tabslistactive]['title'] }}</div>
            <div class="subtitle">{{ tabslist[tabslistactive]['subtitle'] }}</div>
            <div class="desc">{{ tabslist[tabslistactive]['desc'] }}</div>
            <div class="btn" @click="gotoPage('/phonetic-transcription/view')">立即体验</div>
          </div>
        </div>
      </div>
    </div>

    <div class="content_a mbg">
      <div class="inner">
        <div class="all_title">
          <div class="title"><span>视频管家</span>&nbsp;&nbsp;安全强大智能化视频资源管理</div>
          <div class="desc">人工智能技术赋能视频创作及视频管理，简单</div>
        </div>
        <div class="gridcontent">
          <div class="list_flex">
            <div
              v-for="(item,index) in intrlist"
              :key="index"
              class="item"
            >
              <div class="box_item">
                <div class="title list_flex align_items">
                  <img class="ico" :src="item.ico">{{ item.title }}
                </div>
                <div class="desc">{{ item.desc }}</div>
              </div>
            </div>
          </div>
          <div class="list_flex align_items btnwrap">
            <div class="btn" @click="gotoPage('/home')">立即体验</div>
          </div>
        </div>
      </div>
    </div>

    <Apply />
    <Footer />

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Footer from './components/footer'
import Apply from './components/apply'

export default {
  components: {
    Footer,
    Apply
  },
  props: {

  },
  data() {
    return {
      redirect: '',
      tabslistactive: 0,
      tabslist: [
        {
          name: '智能创作',
          ico: require('../../assets/images/guide/ico_t01.png'),
          ico_o: require('../../assets/images/guide/ico_t01_o.png'),
          img: require('../../assets/images/guide/01.png'),
          title: '图文资讯一键转视频',
          subtitle: '文案配素材即可自动生成视频',
          desc: '0基础快速上手，像写word一样做视频。'
        },
        {
          name: '智能字幕',
          ico: require('../../assets/images/guide/ico_t03.png'),
          ico_o: require('../../assets/images/guide/ico_t03_o.png'),
          img: require('../../assets/images/guide/02.png'),
          title: '智能字幕',
          subtitle: '导入视频即可自动识别提取视频中的字幕或语',
          desc: '多国语言识别翻译，匹配字幕，视频创作无国'
        },
        {
          name: '智能遮码',
          ico: require('../../assets/images/guide/ico_t02.png'),
          ico_o: require('../../assets/images/guide/ico_t02_o.png'),
          img: require('../../assets/images/guide/04.png'),
          title: '智能遮码',
          subtitle: '视频在线智能编辑',
          desc: '在线对视频内容进行打码、加水印处理，区域位置灵活定义，在线合成高清输出'
        },
        {
          name: '智能提取',
          ico: require('../../assets/images/guide/ico_t04.png'),
          ico_o: require('../../assets/images/guide/ico_t04_o.png'),
          img: require('../../assets/images/guide/03.png'),
          title: '智能提取',
          subtitle: '智能识别提取视频或音频内容',
          desc: '文案角本轻松获得，激发更多创作灵感'
        }
      ],
      intrlist: [
        {
          title: 'AI智能帧搜索',
          ico: require('../../assets/images/guide/i_1.png'),
          desc: '通过对视频内容的智能分析识别，实现视频内容的帧级搜索：关键词搜索，人物搜索，动物搜索，物体搜索，场景搜索。强大的搜索引擎技术以及高效云计算能力，让内容创作快速找到所需内容，实现视频媒资的高效利用。'
        },
        {
          title: '视频内容智能分析',
          ico: require('../../assets/images/guide/i_2.png'),
          desc: '视频资源经过AI数据分析，通过声音识别、字幕识别、图像识别，即可将每段视频变为可精准标签定位的视频信息数据，输出内容的泛标签。\n'
        },
        {
          title: '企业级安全存储',
          ico: require('../../assets/images/guide/i_3.png'),
          desc: '专为视频媒资应用管理优化的云服务器，基于大规模分布式集群下智能调度，高速读写，自由扩容。视频资料上云集中管理，稳定、安全、高效，将数据的传输、存储、处理和发布四个环节有机融为一体。'
        },
        {
          title: '企业多用户管理',
          ico: require('../../assets/images/guide/i_4.png'),
          desc: '分级分权限内容管理，使用纪录清晰可见。人员变动快速资产文件交接，企业文化资产永不丢失。'
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
  margin-top: -70px;
}

.banner {

  img {
    width: 100%;
    height: 470px;
    object-fit: cover;
  }
}

.content_a {
  padding: 100px 0;

  &.mbg {
    background: url("../../assets/images/guide/bg.png");
    background-size: cover;

    .all_title {
      color: #fff;

      .desc {
        opacity: .8;
      }
    }
  }
}

.tabs {
  margin-top: 50px;
  justify-content: space-around;
  border-bottom: 1px solid rgba(240, 240, 240, 1);

  .item {
    width: calc(25% - 20px);
    padding: 10px 0;
    font-size: 24px;
    color: #a8a8a8;
    cursor: pointer;
    border-bottom: 1px solid transparent;
    margin-bottom: -1px;

    &:hover {
      color: #404040;
    }

    &.active {
      color: #404040;
      border-bottom-color: rgba(86, 117, 232, 1);
    }

    .ico {
      width: 62px;
      height: auto;
      margin-right: 8px;
    }
  }
}

.tabscon {
  margin-top: 50px;

  .info {
    padding-left: 30px;
    font-size: 14px;
    color: #404040;
    line-height: 1.5;

    .title {
      font-size: 30px;
    }

    .subtitle {
      font-size: 18px;
      margin-top: 10px;
    }

    .desc {
      margin-top: 30px;
      color: #a8a8a8;
    }
  }
}

.gridcontent {
  margin-top: 30px;

  .item {
    width: 25%;
    color: #fff;
    padding: 15px 30px;
    border-left: 1px solid rgba(255, 255, 255, .2);
    line-height: 1.5;

    &:last-child {
      border-right: 1px solid rgba(255, 255, 255, .2);
    }

    .title {
      font-size: 18px;
      justify-content: center;

      .ico {
        width: 24px;
        height: auto;
        margin-right: 8px;
      }
    }

    .desc {
      margin-top: 40px;
    }
  }

  .btnwrap {
    justify-content: center;
    margin-top: 50px;

    .btn {
      line-height: 40px;
      border: 1px solid rgba(151, 151, 151, 1);
      color: #fff;
      padding: 0 72px;
      cursor: pointer;
      background: none;

      &:hover {
        background-color: rgba(151, 151, 151, .6);
      }
    }
  }
}
</style>
