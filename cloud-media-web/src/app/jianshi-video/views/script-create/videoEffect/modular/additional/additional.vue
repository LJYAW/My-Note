<!--
 * @Author: your name
 * @Date: 2021-07-23 14:43:18
 * @LastEditTime: 2021-07-30 16:46:55
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/additional.vue
-->
<template>
  <div class="additional wrap">
    <div class="sub-title">附加项</div>
    <CornerMarker />
    <VideoTitle />
    <VideoTail />
  </div>
</template>

<script>
import CornerMarker from './components/cornerMarker'
import VideoTail from './components/videoTail'
import VideoTitle from './components/videoTitle'
import axios from 'axios'
export default {
  components: {
    CornerMarker, VideoTitle, VideoTail
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
