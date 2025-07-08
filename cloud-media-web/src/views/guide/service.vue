<template>
  <div class="wrapper">
    <div class="banner">
      <div class="inner">
        <div class="list_flex align_items">
          <div class="box_flex_1 info">
            <div class="title">视迅云专业企业服务</div>
            <div class="desc">助力企业高效管理媒资，输出价值内容</div>
            <div class="btn whiter" @click="askfor()">立即申请</div>
          </div>
          <div style="width: 364px;margin-right: 100px;">
            <img src="../../assets/images/guide/service_head.png">
          </div>
        </div>
      </div>
    </div>

    <div v-for="(item, index) in datalist" :key="index" class="content_a">
      <div class="inner">
        <div class="box list_flex align_items">
          <div class="box_flex_1 info">
            <div class="title">{{ item.title }}</div>
            <div class="desc" v-html="item.desc" />
          </div>
          <div class="imgbox">
            <img :src="item.pic">
          </div>
        </div>
      </div>
    </div>

    <div ref="applybox">
      <Apply :bg="'bg'" />
    </div>
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
      datalist: [
        {
          ico: '',
          title: '企业视频应用Saas部署',
          subtitle: '',
          desc: '视迅云的视频管理系统及各视频应用工具，可为企业做定制化私有部署，打通企业内部的管理系统、内容编辑发布系统、用户系统等，助力企业视频内容运营。',
          pic: require('../../assets/images/guide/service_h_1.png')
        },
        {
          ico: '',
          title: '视频云企业版',
          subtitle: '',
          desc: '升级至企业版，可实现多用户管理，企业可自行添加多个子账户，每个账户可设置不同的管理权限。方便企业多级内容管理和视频数据安全。',
          pic: require('../../assets/images/guide/service_h_2.png')
        },
        {
          ico: '',
          title: '企业视频内容定制服务',
          subtitle: '',
          desc: '视迅云专业的内容创作团队，可为企业提供内容批量化图文内容视频化服务，也可针对企业特点提供从策划、创意、拍摄、到制作、运营分发的全流程服务。',
          pic: require('../../assets/images/guide/service_h_3.png')
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
    askfor() {
      const _top = this.getPoint(this.$refs.applybox)
      document.body.scrollTop = _top - 70
      document.documentElement.scrollTop = _top - 70
    },
    getPoint(obj) {
      let t = obj.offsetTop
      let l = obj.offsetLeft
      while (obj.offsetParent) {
        obj = obj.offsetParent
        t += obj.offsetTop
        l += obj.offsetLeft
      }
      return t
    },
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
  background: #333ed0;
  padding: 18px 0;
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
      font-size: 48px;
      margin-top: 10px;
    }

    .subtitle {
      font-size: 18px;
      margin-top: 30px;
    }

    .desc {
      line-height: 1.4;
      font-size: 22px;
      margin-top: 30px;
      color: #404040;
    }
  }
}

</style>
