<!--
 * @Author: your name
 * @Date: 2021-08-16 17:45:38
 * @LastEditTime: 2021-08-24 18:27:44
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/videos-manage/components/videoList/index.vue
-->

<template>
  <div class="video-list">
    <!-- <transition-group v-if="videoData.length" name="list" tag="div" class="transition-wrap"> -->
    <div class="transition-wrap">
      <VideoCard v-for="data in videoData" :key="data.id" :video-data="data" class="item" @goDetail="goDetail" />
    </div>
    <!-- </transition-group> -->
    <Normal v-if="videoData.length<=0" />

  </div>
</template>

<script>
import VideoCard from '@/components/VideoCard'
import Normal from '@/components/Normal'
export default {
  components: {
    VideoCard,
    Normal
  },
  props: {
    videoData: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      activeVideoItem: {}
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
  destroyed() {
    const arr = this.$store.state.videoStatus.batchVideo
    arr.splice(0)
    this.$store.commit('videoStatus/BATCH_VIDEO_CHANGE', arr)
  },
  methods: {
    goDetail(uuid, dir_id) {
      this.$router.push({
        path: '/manage-center/videos-manage/videos-detail',
        query: {
          uuid: uuid,
          DirId: dir_id
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.video-list {
  width: 100%;
  min-height: 300px;

  .transition-wrap {
    display: grid;
    grid-template-columns: repeat(4, 25%);  //同上
    grid-row-gap: 30px;
    margin: 0 -12px;

    .video-item {
      margin: 0 12px;
    }
  }

  .item {
    transition: all 1s;
  }

  .list-enter-active,
  .list-leave-active {
    transition: all 1s;
  }

  .list-enter,
  .list-leave-to
/* .list-leave-active for below version 2.1.8 */ {
    opacity: 0;
    transform: translateY(40px);
  }
}
</style>
