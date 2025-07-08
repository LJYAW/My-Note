<!--
 * @Author: your name
 * @Date: 2021-07-23 14:41:10
 * @LastEditTime: 2021-07-23 17:11:19
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/voice.vue
-->
<template>
  <div class="voice wrap">
    <div class="sub-title">声音</div>
    <Timbre />
    <Music />
  </div>
</template>

<script>
import Timbre from './components/timbre'
import Music from './components/music'
export default {
  components: {
    Timbre, Music
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

<style  lang="scss">

</style>
