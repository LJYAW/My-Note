<!--
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-04-15 00:20:49
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/layout/components/Sidebar/Logo.vue
-->
<template>
  <div class="sidebar-logo-container" :class="{'collapse':collapse}">
    <transition name="sidebarLogoFade">
      <div v-if="collapse" class="sidebar-logo-link">
        <img v-if="logo" :src="logo" class="sidebar-logo">
        <router-link v-else to="/">
          <h1 class="sidebar-title">{{ title }} </h1>
        </router-link>
      </div>
      <div v-else class="sidebar-logo-link">
        <router-link key="expand" to="/">
          <img :src="logo" class="sidebar-logo">
        </router-link>
        <h1 class="sidebar-title">{{ title }} </h1>
      </div>
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
      title: '',
      logo: require('@/assets/images/logo.png')
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
  text-align: center;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;

  .sidebar-logo-link {
    .sidebar-logo {
      width: 80%;
      max-width: 120px;
      vertical-align: middle;
      margin-right: 12px;
    }
  }
  &.collapse {
    height: 60px;
    line-height: 60px;
    .sidebar-logo {
      margin-right: 0px;
    }
  }
  .sidebar-title {
      display: inline-block;
      margin: 0;
      color: #fff;
      font-weight: 600;
      font-size: 14px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      vertical-align: middle;
    }
}
</style>
