<!--
 * @Author: your name
 * @Date: 2021-02-22 19:58:59
 * @LastEditTime: 2021-07-09 14:50:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin-template/src/layout/components/AppMain.vue
-->
<template>
  <section class="app-main">
    <div class="main-wrap" :class="!isShow?'activeClass':''">
      <transition name="fade-transform" mode="out-in">
        <router-view :key="key" />
      </transition>
    </div>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  data() {
    return {
    }
  },
  computed: {
    key() {
      return this.$route.path
    },
    isShow() {
      const name = this.$route.name
      return name === 'Home'
    }
  }
}
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  display: flex;
  flex-direction: column;
  min-height: calc(100vh - 100px);
  width: 100%;
  position: relative;
  overflow: hidden;
}

.activeClass {
  background: #fff;
  box-shadow: 0px 10px 20px 0px rgba(93, 126, 235, .1);

}

.main-wrap {
  margin: 30px 48px;

  /* background: #fff; */
  border-radius: 4px;
  overflow: hidden;
  flex: 1;
}

.fixed-header+.app-main {
  padding-top: 100px;
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {

  .fixed-header {
    padding-right: 15px;
  }
}
</style>
