<!--
 * @Author: your name
 * @Date: 2021-06-10 12:11:12
 * @LastEditTime: 2021-09-22 21:13:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /opinion-monit-web/src/layout/index.vue
-->
<template>
  <div id="app-main">
    <HomeAppBar v-if="showHeader" />
    <HomeView />
  </div>
</template>

<script>
export default {
  components: {
    HomeAppBar: () => import('./home/AppBar_new.vue'),
    HomeView: () => import('./home/View.vue')
  },
  data() {
    return {
    }
  },
  computed: {
    showHeader() {
      return this.$store.state.settings.showHeader
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
#app-main {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>
