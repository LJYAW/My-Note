<template>
  <div>
    <div v-if="showHeader" :class="headersticky" class="header">
      <div class="header-main list_flex align_items">
        <div class="list_flex align_items">
          <router-link to="/" class="logo">视迅云</router-link>
          <div ref="nav" class="nav" @click="navclick" @mouseover="showSubNav = true" @mouseleave="showSubNav = false">
            <div class="item">
              <div class="text">视频工具<img class="ico" src="../../assets/images/guide/ico_n.png"></div>

              <transition name="el-zoom-in-top">
                <div v-show="showSubNav" class="subnav">

                  <div class="list_flex sub-link-list">
                    <div v-for="item in subLinkList" :key="item.path" class="sitem" @click.stop="navHandel(item)">
                      <div class="title">
                        {{ item.title }}
                        <img v-if="item.title === '字幕创作'" class="ico" src="../../assets/images/guide/ico_hot.png">
                      </div>
                      <div class="desc" v-html="item.subTitle" />
                    </div>
                  </div>
                </div>
              </transition>

            </div>

            <!-- 顶部 nav -->
            <div v-for="item in navList" :key="item.path" class="item">
              <div class="text" @click="navHandel(item)">
                {{ item.title }}
              </div>
            </div>

          </div>
        </div>
        <HeaderUser />
      </div>
      <div class="headerbg">head</div>
    </div>
  </div>
</template>

<script>
const debounce = function(fn, delay) {
  var timer = null
  return function() {
    var context = this
    var args = arguments
    clearTimeout(timer)
    timer = setTimeout(function() {
      fn.apply(context, args)
    }, delay)
  }
}
const scrollLoad = function(callback, box) {
  const isWindow = box == null
  box = box || document.body
  const scrollFn = debounce(trigger, 10)
  if (isWindow) {
    window.addEventListener('scroll', scrollFn)
    return () => window.removeEventListener('scroll', scrollFn)
  } else {
    box.addEventListener('scroll', scrollFn)
    return () => box.removeEventListener('scroll', scrollFn)
  }
  function trigger() {
    const height = isWindow ? window.innerHeight : box.clientHeight
    const scrollTop = box.scrollTop || document.documentElement.scrollTop
    const scrollHeight = box.scrollHeight
    callback.forEach(cb => cb(scrollTop, height, scrollHeight))
  }
}

import { mapGetters } from 'vuex'
import HeaderUser from '@/components/HeaderUser/index.vue'

export default {
  components: {
    HeaderUser
  },
  props: {

  },
  data() {
    return {
      subLinkList: [
        {
          path: '/guide/subtitle-make',
          loginPath: '/jianshi-video/add-videos',
          title: '字幕创作',
          subTitle: '识别视频中的字幕<br>支持多语言翻译',
          openNewTab: true
        },
        // {
        //   path: '/guide/phonetic-make',
        //   loginPath: '/jianshi-video/add-videos-phonetic',
        //   title: '语音创作',
        //   subTitle: '上传一段音频文件<br>转成字幕与视频',
        //   openNewTab: true
        // },
        {
          path: '/guide/script-creation',
          loginPath: '/jianshi-video/quick-script-create',
          title: '快速创作',
          subTitle: '图文一键转换视频<br>可搭配任意素材',
          openNewTab: true
        },
        {
          path: '/guide/video-masking',
          loginPath: '/video-masking',
          title: '视频水印',
          subTitle: '一键增加视频水印<br>多款贴图样式选择',
          openNewTab: true
        }
      ],
      navList: [
        {
          path: '/guide/video-home',
          loginPath: '/home',
          roles: ['视频管家'],
          title: '视频管家',
          openNewTab: true
        },
        {
          path: '/guide/service',
          loginPath: '/guide/service',
          title: '企业服务',
          openNewTab: true
        },
        {
          path: '/guide/hot-qa',
          loginPath: '/guide/hot-qa',
          title: '使用帮助',
          openNewTab: false
        }
      ],
      showSubNav: false,
      headerfix: false,
      headersticky: ''
    }
  },
  computed: {
    ...mapGetters([
      'roles',
      'userInfo',
      'showHeader'
    ]),
    hasVideoLibrary() {
      return this.roles.some(item => item === '视频管家')
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.setheader()
      },
      immediate: true
    }
  },
  beforeDestroy() {
    this.destroyScroll()
  },
  created() {
  },
  mounted() {
    this.setheader()
    this.destroyScroll = scrollLoad([this.onscroll], null)
  },
  methods: {
    navHandel(item) {
      this.showSubNav = false

      const { path, loginPath } = item

      // 是否有 roles
      const hasRoles = (item.roles && item.roles.length > -1 && this.roles.includes(...item.roles)) || !item.roles

      // 未登录
      if (!this.userInfo.mobile) {
        this.$router.push({
          path: path
        })
      } else {
        // 已经登录
        if (hasRoles && item.openNewTab) {
          window.open(loginPath)
        } else {
          this.$router.push({
            path: path
          })
        }
      }
    },
    setheader() {
      if (this.$route.name !== 'Login') {
        this.headerfix = true
        this.headersticky = 'headersticky'
      } else {
        this.headerfix = false
        this.headersticky = ''
      }
    },
    onscroll(scrollTop, height, scrollHeight) {
      if (this.headerfix) {
        return
      }
      if (scrollTop > 30) {
        this.headersticky = 'headersticky'
      } else {
        this.headersticky = ''
      }
    },
    navclick() {
      this.$refs.nav.style.pointerEvents = 'none'
      setTimeout(() => {
        this.$refs.nav.style.pointerEvents = 'inherit'
      }, 500)
    },
    // -------------------------
    openblank(path) {
      const { href } = this.$router.resolve(path)
      window.open(href, '_blank')
    },
    gotoPage(path) {
      if (this.roles && this.roles.length > 0) {
        // this.$router.push(path)
        this.openblank({
          path: path,
          query: {}
        })
      } else {
        this.$bus.$emit('openLogin', path)
      }
    }
  }

}
</script>

<style scoped lang="scss">
.list_flex {
  display : flex;
  flex-wrap : wrap;

  .box_item {
    position : relative;
    display : flex;
    flex-direction : column;
    justify-content : flex-start;
    align-items : stretch;
    align-content : stretch;
  }
}

.sub-link-list {
  margin-left : 200px;
}

.box_flex_1 {
  flex : 1;
}

.align_items {
  align-items : center;
}

.justify_content {
  justify-content : space-between;
}

.inner {
  max-width : 1240px;
  margin : 0 auto;
}

.header {
  position : fixed;
  width : 100%;
  top : 0;
  left : 0;
  z-index : 302;
  color : #FFFFFF;
  transition : background .5s;
  border-bottom : 1px solid transparent;

  ::v-deep .el-dropdown {
    color : #FFFFFF;
  }

  .headerbg {
    position : absolute;
    z-index : -1;
    height : 70px;
    width : 100%;
    top : 0;
    left : 0;
    background : rgba(10, 3, 45, .6);
    backdrop-filter : blur(5px);
    text-indent : -99999em;
    transition : all .5s;
  }

  &.headersticky {
    color : #333333;
    border-bottom : 1px solid #F0F0F0;

    ::v-deep .el-dropdown {
      color : #333333;
    }

    .logo {
      background-image : url('../../assets/images/guide/g_logo_c.png');
    }

    .headerbg {
      background : rgba(255, 255, 255, 1);
    }

    .subnav {
      border-top-color : #F0F0F0;
      background : rgba(255, 255, 255, 1);

      .sitem {
        border-color : #F0F0F0;
      }
    }
  }
}

.header-main {
  position : relative;
  display : flex;
  flex-direction : row;
  align-items : center;
  justify-content : space-between;
  height : 70px;
  font-size : 14px;
  padding : 0 20px;

  .logo {
    width : 114px;
    height : 32px;
    background : url('../../assets/images/guide/g_logo.png') no-repeat;
    background-size : 114px 32px;
    text-indent : -99em;
  }

  .nav {
    display : flex;
    margin-left : 50px;

    // &:hover {

    //   .subnav {
    //     transform: scaleY(1);
    //   }
    // }

    .item {
      position : relative;

      .text {
        height : 70px;
        line-height : 70px;
        padding : 0 20px;
        cursor : pointer;

        .ico {
          width : 12px;
          height : auto;
          margin-left : 5px;
        }

        &:hover {
          opacity : .8;
        }
      }
    }
  }

  .headlogin {
    cursor : pointer;
    display : flex;
    flex-wrap : wrap;
  }
}

.subnav {
  position : fixed;
  left : 0;
  top : 70px;
  width : 100%;
  padding : 34px 0;
  background : rgba(10, 3, 45, .6);
  backdrop-filter : blur(5px);
  line-height : 1.5;
  border-top : 1px solid rgba(10, 3, 45, .5);
  // transform: scaleY(0);
  // transition: transform .3s ease-out;
  // transform-origin: center top;

  .sitem {
    text-align : center;
    border-right : 1px solid rgba(255, 255, 255, .5);
    padding : 0 50px;
    cursor : pointer;

    &:first-child {
      border-left : 1px solid rgba(255, 255, 255, .5);
    }

    .title {
      font-size : 14px;

      .ico {
        width : 22px;
        height : auto;
        margin-left : 5px;
      }
    }

    .desc {
      margin-top : 6px;
      font-size : 12px;
      opacity : .6;
    }
  }
}

</style>
