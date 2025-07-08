<template>
  <div class="wrapper">
    <div class="banner">
      <div class="inner">
        <div class="list_flex align_items">
          <div class="box_flex_1 info">
            <div class="title">智能视频水印</div>
            <div class="desc">在视频任意位置添加高斯模糊水印，或用贴图遮住视频某一位置，所见即所得，满足视频遮码需求</div>
            <div class="btn" style="margin-top: 70px;" @click="gotoPage('/video-masking/view')">立即使用</div>
          </div>
          <div style="width: 568px;">
            <img src="../../assets/images/guide/vm_h.png">
          </div>
        </div>
      </div>
    </div>

    <div class="content_a">
      <div class="inner">
        <div class="all_title">
          <div class="title"><span>智能视频水印管理 让你的创作再无拘束</span></div>
          <div class="desc" style="font-size: 18px;margin-top: 30px;">多场景适用，为你的视频防盗添砖加瓦</div>
        </div>
        <div class="listcol3 intr">
          <div class="col">
            <div class="item list_flex">
              <div class="ico"><img src="../../assets/images/guide/vm_ico_1.png"></div>
              <div class="info">
                <div class="t">便捷添加</div>
                <div class="d">支持多重水印自定义添加 操作简单便捷</div>
              </div>
            </div>
          </div>
          <div class="col">
            <div class="item list_flex">
              <div class="ico"><img src="../../assets/images/guide/vm_ico_2.png"></div>
              <div class="info">
                <div class="t">自由操作</div>
                <div class="d">简单操作台 可定义添加时间长度</div>
              </div>
            </div>
          </div>
          <div class="col">
            <div class="item list_flex">
              <div class="ico"><img src="../../assets/images/guide/vm_ico_3.png"></div>
              <div class="info">
                <div class="t">所见即所得</div>
                <div class="d">视频实时预览 一键生成视频</div>
              </div>
            </div>
          </div>
        </div>
        <!--        <videoCase />-->
      </div>
    </div>

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
  padding-bottom: 30px;

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

</style>
