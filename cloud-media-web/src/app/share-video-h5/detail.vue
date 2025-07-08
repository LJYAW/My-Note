<!--
 * @Author: your name
 * @Date: 2021-08-17 10:25:43
 * @LastEditTime: 2021-08-24 17:55:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/share-video/detail.vue
-->
<template>

  <div class="video-detail">
    <Header />
    <div class="video-wrap">
      <video class="video" :src="src" controls :poster="poster" />
      <div class="video-desc">
        <div>{{ titles }}</div>
        <div class="time">视频时长：{{ duration | timesToHMS }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from './header.vue'

export default {
  components: {
    Header
  },
  props: {

  },
  data() {
    return {

    }
  },
  computed: {
    src() {
      return this.$route.query.video_url
    },
    poster() {
      return this.$route.query.cover_url
    },
    titles() {
      return this.$route.query.titles
    },
    duration() {
      return this.$route.query.video_duration
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

<style scoped lang="scss">
.video-detail {
  background: #0f0f1c;
  width: 100%;
  min-height: 100vh;

  .video-wrap {
    width: 100%;
    border-radius: 4px;
    padding: 15px 20px;

    .video {
      width: 100%;
      border-radius: 4px;
    }

    .video-desc {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      color: #fff;
      padding: 10px 0;

      .time {
        color: rgba(139,139,161,.4);
      }
    }
  }
}
</style>
