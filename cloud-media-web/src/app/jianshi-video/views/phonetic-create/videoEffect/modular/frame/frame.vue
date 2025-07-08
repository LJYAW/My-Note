<!--
 * @Author: your name
 * @Date: 2021-07-23 14:42:52
 * @LastEditTime: 2021-09-26 16:57:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/frame.vue
-->
<template>
  <div class="frame wrap">
    <div class="sub-title">画面</div>
    <div v-if="phoneticVideoSale!=='9:16'">
      <Template />
      <Host />
    </div>
    <SubtitlePosition v-else />
  </div>
</template>

<script>
import Template from './components/template'
import Host from './components/host'
import SubtitlePosition from './components/subtitlePosition'
// import SubtitleBack from './components/subtitleBack'
import { mapState } from 'vuex'

export default {
  components: {
    Template, Host, SubtitlePosition
  },
  props: {

  },
  data() {
    return {

    }
  },
  computed: {
    ...mapState('jianshi', ['phoneticVideoSale']),
    router() {
      return this.$route.name
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

<style  lang="scss">

</style>
