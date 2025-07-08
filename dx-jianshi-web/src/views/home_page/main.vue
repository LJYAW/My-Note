<template>
  <div class="home-page">
    <bannerList />
    <ctardList />
    <homePage1 />
    <homeFooter />
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {},
  components: {
    bannerList: () => import('./banenr_list'),
    ctardList: () => import('./card_list'),
    homePage1: () => import('./page_1'),
    homeFooter: () => import('./footer'),
  },
  created() {},
  mounted() {},
}
</script>

<style scoped lang="scss">
@media screen and (max-width: 768px) {
  .home-page {
    max-width: 1300px;
    min-width: 1300px;
  }
}
@media screen and (max-width: 1200px) and (min-width: 768px) {
  .home-page {
    max-width: 1320px;
    min-width: 1320px;
  }
}
.home-page {
  color: #101010;
  .page-2 {
    margin-top: 150px;
  }
}
</style>
