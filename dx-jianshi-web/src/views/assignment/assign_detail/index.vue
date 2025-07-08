<!--
 * @Author: your name
 * @Date: 2020-11-10 19:42:49
 * @LastEditTime: 2020-11-16 16:59:42
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/assignment/assign_detail/index.vue
-->
<template>
  <div class="assign-detail-wrap">
    <detail-platform v-if="type == 'platform'" />
    <detail-my v-else />
  </div>
</template>

<script>
import DetailMy from './detail_my.vue'
import DetailPlatform from './detail_platform.vue'
export default {
  provide() {
    return {
      assignDetail: this
    }
  },
  props: {},
  data() {
    return {
      assignInfo: {}
    }
  },
  computed: {
    type() {
      return this.$route.query.type
    }
  },
  watch: {},
  methods: {},
  components: { DetailMy, DetailPlatform },
  created() {
    let assignInfo = window.sessionStorage.getItem('assignDetail')
    this.assignInfo = JSON.parse(assignInfo)
  },
  mounted() {
    // console.log(this.type)
  }
}
</script>

<style scoped lang="scss">
.assign-detail-wrap {
  width: 100%;
  height: 100%;
  padding: 36px 220px 0;
  background-color: #fff;
}
</style>
