<!--
 * @Author: your name
 * @Date: 2021-02-22 19:58:59
 * @LastEditTime: 2021-07-09 14:42:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-admin-template/src/layout/components/Sidebar/Logo.vue
-->
<template>
  <div class="sidebar-logo-container" :class="{'collapse':collapse}">
    <transition name="sidebarLogoFade">
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <i class="el-icon-document" />
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <!-- <img v-if="logo" :src="logo" class="sidebar-logo"> -->
        <i class="el-icon-document" />
        <h1 class="sidebar-title">{{ title }} </h1>
      </router-link>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'SidebarLogo',
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      title: '舆情监测系统',
      logo: ''
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 120px;
  line-height: 120px;
  background: menuBg;
  text-align: center;
  overflow: hidden;
  background: #474e75;
  box-shadow: 0px 2px 12px 0px rgb(189 177 177 / 50%);

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;
    display: flex;
    align-items: center;

    & .sidebar-logo {
      width: 60%;
      height: 100%;
      vertical-align: middle;
      object-fit: contain;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      color: #fff;
      font-weight: 600;
      line-height: 50px;
      font-size: 36px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      vertical-align: middle;
    }

    .el-icon-document {
      font-size: 40px;
      color: #fff;
      line-height: 50px;
      margin-right: 20px;
      vertical-align: sub;
    }
  }

  &.collapse {

    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
