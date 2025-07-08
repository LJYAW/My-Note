<!--
 * @Author: your name
 * @Date: 2021-09-08 15:07:17
 * @LastEditTime: 2021-10-09 16:06:55
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/subtitle-create/index.vue
-->
<template>
  <div class="subtitle-create">
    <SubtitleHeader />
    <SubtitleCreation />
    <VideoOption />
    <VideoEffect />
  </div>
</template>

<script>
import SubtitleHeader from './subtitleHeader/index.vue'
import SubtitleCreation from './subtitleCreation/index'
import VideoOption from './videoOption/index'
import VideoEffect from './videoEffect/index'
export default {
  components: {
    SubtitleCreation,
    VideoEffect,
    VideoOption,
    SubtitleHeader
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
.subtitle-create {
  flex: 1;
  padding: 20px 10%;
  padding-bottom: 80px;
  height: 100%;
  min-width: 1220px;
}
</style>
