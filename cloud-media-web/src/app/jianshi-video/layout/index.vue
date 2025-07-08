<!--
 * @Author: your name
 * @Date: 2021-08-30 11:44:51
 * @LastEditTime: 2021-09-22 20:46:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi/layout/index.vue
-->
<template>
  <div class="jainshi-main">
    <!-- <AppBar /> -->
    <AppView />
  </div>
</template>

<script>
// import AppBar from '@/layout/home/AppBar_new'
import AppView from './home/View.vue'
export default {
  components: {
    AppView
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
.jainshi-main {
  display: flex;
  flex-direction: column;
}
</style>
