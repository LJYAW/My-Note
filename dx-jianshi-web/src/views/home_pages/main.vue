<!--
 * @Author: zll
 * @Date: 2020-12-04 10:58:11
 * @LastEditTime: 2020-12-05 15:34:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/home_pages/main.vue
-->
<template>
  <div class="main_warp">
    <banner />
    <card />
    <page />
    <foot />
  </div>
</template>

<script>
export default {
  name: '',
  props: {},
  data() {
    return {}
  },
  methods: {},
  mounted() {},
  created() {},
  components: {
    banner: () => import('./banner'),
    card: () => import('./card_list'),
    page: () => import('./page'),
    foot: () => import('./footer')
  },
  watch: {}
}
</script>

<style lang='scss' scoped>
@media screen and (max-width: 768px) {
  .main_warp {
    max-width: 1000px;
    min-width: 1000px;
  }
}
@media screen and (max-width: 1200px) and (min-width: 768px) {
  .main_warp {
    max-width: 1200px;
    min-width: 1200px;
  }
}
</style>