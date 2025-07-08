<!--
 * @Author: your name
 * @Date: 2021-08-17 10:25:32
 * @LastEditTime: 2021-08-26 19:02:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/share-video/list.vue
-->
<template>
  <div class="video-list">
    <Header />

    <div v-for="item in list" :key="item.id" class="video-item" @click="goDetail(item)">
      <img :src="item.cover_url" alt="">
      <div class="video-desc">
        <div class="title">{{ item.titles }}</div>
        <div class="time">视频时长：{{ item.video_duration | timesToHMS }}</div>
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
      list: []
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getList()
  },
  mounted() {

  },
  methods: {
    async getList() {
      const { err, res } = await this.$get('/videos/share/' + this.$route.query.id)
      if (err) {
        this.$message.warning(err.msg)
        return
      }
      this.list = res.data.videos
    },
    goDetail(item) {
      this.$router.push({
        path: '/share-detail',
        query: {
          video_url: item.video_url,
          cover_url: item.cover_url,
          titles: item.titles,
          video_duration: item.video_duration
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.video-list {
  background: #0f0f1c;
  width: 100%;
  min-height: 100vh;
  padding-bottom: 30px;

  .video-item {
    display: flex;
    justify-content: center;
    margin: 10px 0;
    padding: 0 20px ;

    img {
      width: 50%;
      // height: 180px;
      border-radius: 4px;
    }

    .video-desc {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      color: #fff;
      padding: 10px 0;
      margin-left: 10px;

      .title {
        word-wrap: break-word;
        white-space: normal;
        word-break: break-all;
      }

      .time {
        color: rgba(139,139,161,.4);
      }
    }
  }
}
</style>
