<!--
 * @Author: your name
 * @Date: 2021-09-08 17:36:22
 * @LastEditTime: 2021-09-22 17:35:16
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/index.vue
-->
<template>
  <Layout />
</template>

<script>
import Layout from '../layout/index.vue'
import myvuex from '../store/index'
import { mapState } from 'vuex'
export default {
  components: {
    Layout
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
  beforeCreate() {
    this.$store.registerModule('jianshi', myvuex)
  },
  created() {

  },
  destroyed() {
    // resetState
    this.$store.commit('jianshi/resetState')
    this.$store.unregisterModule('jianshi')
  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">

</style>
