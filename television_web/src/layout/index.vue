<!--
 * @Author: your name
 * @Date: 2021-01-18 18:05:41
 * @LastEditTime: 2021-04-26 17:26:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/layout/index.vue
-->
<template>
  <div :class="classObj" class="app-wrapper">
    <div class="main-container">
      <div :class="{'fixed-header':fixedHeader}">
        <Navbar />
      </div>
      <app-main />
    </div>
  </div>
</template>

<script>
import { Navbar, AppMain } from './components'
import ResizeMixin from './mixin/ResizeHandler'
export default {
  name: 'Layout',
  components: {
    Navbar,
    AppMain
  },
  mixins: [ResizeMixin],
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar
    },
    device() {
      return this.$store.state.app.device
    },
    fixedHeader() {
      return this.$store.state.settings.fixedHeader
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        // mobile: this.device === 'mobile'
        mobile: false
      }
    }
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixin.scss";
  @import "~@/styles/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
    &.mobile.openSidebar{
      position: fixed;
      top: 0;
    }
    .main-container{
      background: #F3F3F3;
      height: 100%;
    }
  }
  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: 100%!important;
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 54px)
  }

  .mobile .fixed-header {
    width: 100%;
  }
</style>
