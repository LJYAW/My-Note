<!--
 * @Author: your name
 * @Date: 2021-07-27 18:13:46
 * @LastEditTime: 2021-09-07 14:51:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/layout/home/nav.vue
-->
<template>
  <div class="header-nav">
    <ul class="nav-list">
      <li v-for="item in headerNav" :key="item.path" class="item">
        <router-link :to="item.path" @click.native="handler(item)">
          {{ item.title }}
          <svg-icon v-if="item.title==='视频工具箱'" icon-class="导航New" class="svg" />
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>

export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      navList: [
        {
          path: '/home',
          roles: ['视频库'],
          title: '我的视频库'
        },
        {
          path: '/app-tools',
          roles: ['视频工具箱'],
          title: '视频工具箱'
        }
      ]
    }
  },
  computed: {
    headerNav() {
      const rolesList = this.$store.state.user.roles || []
      return this.navList.filter(item => rolesList.some(role => item.roles.includes(role)))
    }
  },
  watch: {

  },
  created() {
  },
  mounted() {

  },
  methods: {
    handler() {
      this.$bus.$emit('srearchOnkeyword', '')
    },
    filterRoutes(routes) {
      const res = []
      const route = routes[0].children
      route.forEach(element => {
        if (element.meta && element.meta.type === 'befor_menu') {
          res.push(element)
        }
      })
      return res
    }

  }
}
</script>

<style scoped lang="scss">
.header-nav {
  height: 100%;
  margin-left: 35px;

  .nav-list {
    height: 100%;
    display: flex;
    align-items: center;

    .item {
      width: 120px;
      font-size: 14px;
      color: #fff;
      position: relative;

      .svg {
        position: absolute;
        top: 0px;
        margin-left: 2px;
      }
    }

  }
}
</style>
