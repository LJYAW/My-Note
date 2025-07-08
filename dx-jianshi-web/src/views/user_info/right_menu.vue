<!--
 * @Author: zzx
 * @Date: 2020-07-22 18:47:21
 * @LastEditTime: 2020-12-10 14:52:12
 * @FilePath: /weijian_web/src/views/user_info/right_menu.vue
--> 
<template>
  <ul class="menu-list">
    <li class='menu-active'></li>
    <li v-for="(item,index) in menu_list"
      :key="index" @click='changeBg(index)'>
      <router-link class="item cp fz-16px"
        :to="item.router">
        <img :src="getSrc(item)" alt="" class='mr-10px'>
        <img :src="item.activeSrc" alt="" class='mr-10px active-img'>
        {{item.name}}
      </router-link>
    </li>
  </ul>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      menu_list: [
        {
          name: '我的作品',
          router: '/user-info/ai-created',
          src: require('../../assets/images/user_info/left_menu/my_videos.svg'),
          activeSrc: require('../../assets/images/user_info/left_menu/my_video_active.svg')
        },
        {
          name: '我的素材库',
          router: '/user-info/my-library',
          src: require('../../assets/images/user_info/left_menu/my_library.svg'),
          activeSrc: require('../../assets/images/user_info/left_menu/my_library_active.svg')
        },
        // {
        //   name: '收藏夹',
        //   router: '/user-info/my-collection',
        //   src: require('../../assets/images/user_info/left_menu/shoucang.svg'),
        //   activeSrc: require('../../assets/images/user_info/left_menu/shoucang_active.svg')
        // },
        // {
        //   name: '发布记录',
        //   router: '/user-info/release-record',
        //   src: require('../../assets/images/user_info/left_menu/fabu.svg'),
        //   activeSrc: require('../../assets/images/user_info/left_menu/fabu_active.svg')
        // },
        {
          name: '购买记录',
          router: '/user-info/purchase-records',
          src: require('../../assets/images/user_info/left_menu/buy.svg'),
          activeSrc: require('../../assets/images/user_info/left_menu/buy_active.svg')
        },
        // {
        //   name: '消息中心',
        //   router: '/user-info/message-center',
        //   src: require('../../assets/images/user_info/left_menu/message.svg'),
        //   activeSrc: require('../../assets/images/user_info/left_menu/message_active.svg')
        // },
        // {
        //   name: '发布者账号',
        //   router: '/user-info/publisher-account',
        //   src: require('../../assets/images/user_info/left_menu/zhanghao.svg'),
        //   activeSrc: require('../../assets/images/user_info/left_menu/zhanghao_active.svg')
        // },
        {
          name: '账号信息',
          router: '/user-info/account-info',
          src: require('../../assets/images/user_info/left_menu/account.svg'),
          activeSrc: require('../../assets/images/user_info/left_menu/account_active.svg')
        }
      ]
    }
  },
  computed: {},
  watch: {
    $route(val) {
      this.activeMenu()
    }
  },
  methods: {
    getSrc(item) {
      if (item.router == this.$route.path) {
        return item.activeSrc
      }
      return item.src
    },
    changeBg(index) {
      document.getElementsByClassName('menu-active')[0].style.top = 52 * index + 'px'
    },
    //路由切换
    activeMenu() {
      let activeIndex
      this.menu_list.forEach((item, index) => {
        if (item.name == this.$route.name) {
          activeIndex = index
        }
      })
      this.changeBg(activeIndex)
    }
  },
  components: {},
  created() {},
  mounted() {
    this.activeMenu()
  }
}
</script>

<style scoped lang="scss">
</style>
