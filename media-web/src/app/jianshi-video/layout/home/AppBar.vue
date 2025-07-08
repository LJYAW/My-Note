<!--
 * @Author: your name
 * @Date: 2021-08-30 12:02:39
 * @LastEditTime: 2021-09-22 18:58:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/layout/home/AppBar.vue
-->
<template>
  <div class="app-nav">
    <Theader :name="$route.meta.appName">
      <div slot="tit">
        c gvwhgcvgh
      </div>
    </Theader>
  </div>
</template>

<script>
import Theader from '@/components/ToolsHeader'
export default {
  components: {
    Theader
  },
  props: {

  },
  data() {
    return {

    }
  },
  computed: {

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
.app-nav {
  background: #fff;
  position: fixed;
  z-index: 9;
  width: 100%;
  box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, .06);

  .nav-container {
    width: 100%;
    height: 70px;
    padding: 0 10%;
    display: flex;
    align-items: center;

    .nav-wrap {
      display: flex;
      align-items: center;

      svg {
        width: 110px;
        height: 30px;
        margin-right: 44px;
      }

      ul {
        display: flex;
        font-size: 18px;
        line-height: 18px;
        font-weight: 400;
        color: #404040;

        li {
          margin-right: 48px;

          a.router-link-active {
            color: #5675e8;
          }
        }
      }

    }
  }

}
</style>
