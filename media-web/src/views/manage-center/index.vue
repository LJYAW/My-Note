<!--
 * @Author: your name
 * @Date: 2021-08-03 14:33:43
 * @LastEditTime: 2021-10-19 12:14:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/index.vue
-->
<template>
  <div class="manage-center">

    <div class="center-nav">
      <div class="title">
        <div v-if="!isCollapse">
          <span>管理中心</span>
          <i class="el-icon-arrow-left icon" @click="isCollapse=true" />
        </div>
        <i v-else class="el-icon-arrow-right icon" @click="isCollapse=false" />
      </div>
      <el-menu class="el-menu-vertical-demo" :collapse="isCollapse" :default-active="defaultActive">
        <el-menu-item v-for="(item) in menuRouters" :key="item.path" :index="item.path" @click.native="goToPath(item.path)">
          <svg-icon :icon-class="item.meta.icon" />
          <p class="menu-name">{{ item.meta.title }}</p>
        </el-menu-item>
      </el-menu>
    </div>
    <section class="sections-wrap">
      <keep-alive>
        <router-view v-if="$route.meta.keepAlive" />
      </keep-alive>
      <router-view v-if="!$route.meta.keepAlive" /></section>
  </div>
</template>

<script>
import routers from '@/router/manage-center.js'
import { mapGetters } from 'vuex'
import manageCenterRouter from '@/router/manage-center.js'

export default {
  name: 'ManageCenter',
  components: {

  },
  props: {

  },
  data() {
    return {
      isCollapse: false,
      defaultActive: '',
      menuRouters: []
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  watch: {
    $route() {
      this.setDefaultActiceRouter()
    }
  },
  created() {
    this.filterRoutes()
    this.setDefaultActiceRouter()
  },
  mounted() {

  },
  methods: {
    goToPath(path) {
      this.$router.push({
        path: `/manage-center/${path}`
      })
    },
    filterRoutes() {
      let arr = []
      const filterArr = manageCenterRouter.filter(item => !item.meta.hidden)
      if (this.roles.includes('superman')) {
        arr = filterArr
      } else {
        arr = filterArr.filter(item => {
          if (!item.meta.roles || item.meta.roles.some(role => this.roles.includes(role))) {
            return item
          }
        })
      }
      this.menuRouters = arr
    },
    setDefaultActiceRouter() {
      const firsetPath = this.menuRouters[0].path
      const currentPath = this.$route.path
      const currentRouter = this.menuRouters.find(item => currentPath.includes(item.path))
      let path = ''

      currentPath === '/manage-center'
        ? (path = firsetPath && this.goToPath(firsetPath))
        : (path = currentRouter.path)

      this.defaultActive = path
    }
  }
}
</script>

<style scoped lang="scss">
.manage-center {
  display: flex;
  min-height: calc(100vh - 70px);
  max-height: calc(100vh - 70px);
  overflow: hidden;

  .sections-wrap {
    padding: 30px 48px 0 24px;
    flex: 1;
    width: 0;
    background-color: #fff;
    overflow: auto;
  }
}
</style>
