<!--
 * @Author: your name
 * @Date: 2021-10-20 11:19:06
 * @LastEditTime: 2021-10-29 10:55:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/layout/index.vue
-->
<template>
  <div :class="classObj" class="app-wrapper">
    <!-- <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" /> -->
    <div :class="{'fixed-header':fixedHeader}">
      <navbar />
    </div>
    <div class="main-container">
      <sidebar class="sidebar-container" />
      <app-main />
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, AppMain } from './components'
import ResizeMixin from './mixin/ResizeHandler'

export default {
  name: 'Layout',
  components: {
    Navbar,
    Sidebar,
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
        // hideSidebar: !this.sidebar.opened,
        // openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
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
  @import '~@/styles/mixin.scss';
  @import '~@/styles/variables.scss';

  .app-wrapper {
    @include clearfix;
    position : relative;
    height : 100%;
    width : 100%;
    &.mobile.openSidebar {
      position : fixed;
      top : 0;
    }
  }
  .drawer-bg {
    background : #000000;
    opacity : .3;
    width : 100%;
    top : 0;
    height : 100%;
    position : absolute;
    z-index : 999;
  }

  .fixed-header {
    position : fixed;
    top : 0;
    right : 0;
    z-index : 9;
    width : calc(100% - #{$sideBarWidth});
    transition : width .28s;
  }
  .fixed-header + .main-container {
    padding-top : 70px;
  }

  .hideSidebar .fixed-header {
    width : calc(100% - 54px);
  }

  .mobile .fixed-header {
    width : 100%;
  }

</style>
