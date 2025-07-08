<!--
 * @Author: your name
 * @Date: 2020-08-28 18:23:33
 * @LastEditTime: 2020-12-21 17:44:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /weijian_cms_web/src/page/vmain.vue
-->
<template>
  <div class="main-wrap" id="main_wrap">
    <div class="leftBox">
      <div class="routerList">
        <router-link :to="item.url" v-for="(item,index) in meauList" :key="index">{{item.name}}</router-link>
      </div>
    </div>
    <div class="rightBox">
      <router-view />
    </div>
  </div>
</template>
<script>
export default {
  name: 'vmain',
  data() {
    return {
      meauList: [
        {
          name: '用户管理',
          url: '/user'
        },
        {
          name: '作品管理',
          url: '/works'
        },
        // {
        //   name: '作品广场',
        //   url: '/works_square'
        // },
        // {
        //   name: '默认效果设置',
        //   url: '/effect'
        // },
        {
          name: '任务管理',
          url: '/task'
        }
        // {
        //   name: '素材管理',
        //   url: '/material'
        // }
      ]
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
.main-wrap {
  width: 100%;
  display: flex;
  height: calc(100% - 63px);
  margin-top: 3px;
  .leftBox {
    width: 200px;
    height: 100%;
    background: #ffffff;
    .routerList {
      margin-top: 48px;
      a {
        display: block;
        width: 100%;
        height: 40px;
        text-align: left;
        padding-left: 32px;
        line-height: 40px;
      }
      .router-link-active {
        background-color: rgba(225, 225, 225, 100);
      }
    }
  }
  .rightBox {
    width: calc(100% - 220px);
    margin: 20px;
    // background: #ffffff;
    // padding: 30px;
    box-sizing: border-box;
    overflow: auto;
  }
}
</style>
