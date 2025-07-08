<!--
 * @Author: your name
 * @Date: 2021-09-08 15:42:33
 * @LastEditTime: 2021-09-17 15:40:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/manage-center/index.vue
-->
<template>
  <div class="jianshi-manage-center">
    <!-- <div class="title">
      <div v-if="!isCollapse">
        <span>简视频</span>
        <i class="el-icon-arrow-left icon" @click="isCollapse=true" />
      </div>
      <i v-else class="el-icon-arrow-right icon" @click="isCollapse=false" />
    </div> -->
    <div class="center-nav">
      <el-menu class="el-menu-vertical-demo" :collapse="isCollapse" :default-active="defaultActive" router>
        <el-menu-item v-for="(item) in menuRouters" :key="item.path" :index="item.path">
          <svg-icon :icon-class="item.meta.icon" />
          <p class="menu-name">{{ item.meta.title }}</p>
        </el-menu-item>
      </el-menu>
    </div>
    <div class="manage-main">
      <router-view />
    </div>

  </div>
</template>

<script>
import jianshiVideoRouter from '@/app/jianshi-video/router/index.js'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      isCollapse: false
    }
  },
  computed: {
    defaultActive() {
      const pathArr = this.$route.path.split('/')
      return pathArr[3]
    },
    menuRouters() {
      const filterRouter = jianshiVideoRouter.filter(item => {
        return item.path === 'manage-center'
      })
      return filterRouter[0].children
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.jianshi-manage-center {
  display: flex;

  .center-nav {
    padding-top: 0;
  }

  .manage-main {
    padding: 30px 10% 0 24px;
    background: #fff;
    flex: 1;
    width: 0;
    display: flex;
  }
}
</style>
