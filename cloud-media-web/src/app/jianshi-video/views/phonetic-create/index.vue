<!--
 * @Author: your name
 * @Date: 2021-09-08 15:06:33
 * @LastEditTime: 2021-09-26 16:47:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/phonetic-create/index.vue
-->
<template>
  <div class="phonetic-create">
    <AudioSubtitle />
    <VideoOption />
    <VideoEffect />
  </div>
</template>

<script>
import AudioSubtitle from './audioSubtitle/index'
import VideoOption from './videoOption/index'
import VideoEffect from './videoEffect/index'
export default {
  components: {
    AudioSubtitle, VideoEffect, VideoOption
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
.phonetic-create {
  flex: 1;
  // padding: 20px 10%;
  height: 100%;
  width: 100%;
  min-width: 1220px;
  width: 1220px;
  margin: 0 auto;

}
</style>
