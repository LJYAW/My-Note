<template>
  <div class="effectBox">
    <div class="router_list">
      <router-link :to="item.url" v-for="(item,index) in tabList" :key="index">{{item.name}}</router-link>
    </div>
    <div class="content">
      <router-view />
    </div>
  </div>
</template>
<script>
export default {
  name: 'works',
  data() {
    return {
      tabList: [
        {
          name: '虚拟主持人',
          url: '/effect/host',
        },
        {
          name: '背景音乐',
          url: '/effect/back_music',
        },
        {
          name: '画面修饰',
          url: '/effect/frame',
        },
        {
          name: '默认片头',
          url: '/effect/title',
        },
        {
          name: '默认片尾',
          url: '/effect/film',
        },
      ],
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {},
}
</script>

<style lang="scss" scoped>
.effectBox {
  width: 100%;
  height: 100%;
  .router_list {
    height: 50px;
    line-height: 50px;
    display: flex;
    a {
      display: inline-block;
      position: relative;
      margin-left: 36px;
      font-size: 16px;
      min-width: 70px;
      text-align: center;
    }
    .router-link-active {
      font-weight: bold;
    }
    .router-link-active::after {
      /* 下划线伪元素 */
      display: block;
      content: '';
      /* 控制下划线动画起始位置 */
      position: absolute;
      left: 0;
      right: 0;
      margin: 0 auto;
      bottom: 5px;
      /* 下划线样式 */
      width: auto;
      border-bottom: 3px solid #2a79df;
      transition: width 0.6s cubic-bezier(0, 0.83, 0.72, 1.03);
      font-weight: bold;
    }
  }
  .content {
    margin-top: 15px;
    padding: 15px;
    height: calc(100% - 75px);
    background: #ffffff;
    overflow: auto;
  }
}
</style>
