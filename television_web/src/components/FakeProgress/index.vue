<!--
 * @Author: your name
 * @Date: 2021-04-19 19:45:34
 * @LastEditTime: 2021-04-19 20:10:08
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /opinion_monitor_web/src/components/Progress/index.vue
-->
<!-- 进度条 -->
<template>
  <div id="progress">
    <progress value="22" max="100" />
  </div>

</template>

<script>
import Progress from './progress.js'

export default {
  data() {
    return {

    }
  },

  computed: {},

  created() {
    Progress.start()
    console.log(Progress.value)
  },

  mounted() {
  },
  destroyed() {

  },
  methods: {
  }
}
</script>
<style lang='scss' scoped>
</style>
