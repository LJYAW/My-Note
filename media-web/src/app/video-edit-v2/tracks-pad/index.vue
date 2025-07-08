<!--
 * @Author: your name
 * @Date: 2021-08-31 17:40:15
 * @LastEditTime: 2021-09-01 14:59:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit-v2/tracks-pad/index.vue
-->
<template>
  <div class="tracks-pad">

    <SeekLine />

    <TimeLineCanvas />

    <div class="tracks-content" />

  </div>
</template>

<script>
import TimeLineCanvas from '../components/TimeLineCanvas'
import SeekLine from './SeekLine/index.vue'

export default {
  components: {
    TimeLineCanvas,
    SeekLine
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
.tracks-pad {
  margin: 20px;
  position: relative;
  overflow: hidden;
  height: 100%;
  display: flex;
  flex-direction: column;

  .tracks-content {
    flex: 1;
    margin-top: 20px;
  }

  .seek-line {
    top: 28px;
  }
}
</style>
