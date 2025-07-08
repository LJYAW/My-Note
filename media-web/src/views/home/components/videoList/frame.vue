<!--
 * @Author: your name
 * @Date: 2021-08-16 16:35:34
 * @LastEditTime: 2021-09-16 10:36:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/videoList/frame.vue
-->
<template>
  <div class="video-list">
    <!-- <transition-group name="list" tag="div" class="transition-wrap"> -->
    <VideoItem v-for="data in videoData" :key="data.id" :video-data="data" class="item" @goDetail="goDetail" />
    <!-- </transition-group> -->

    <Normal v-if="videoData.length<=0" style="width: 100%" />

  </div>
</template>

<script>
import VideoItem from './frameItem_mix.vue'
import Normal from '@/components/Normal'

export default {
  components: {
    VideoItem,
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
      /*
      this.$router.push({
        path: '/video-detail',
        query: {
          uuid: uuid,
          DirId: dir_id
        }
      })
      */
      const { href } = this.$router.resolve({
        path: '/video-detail',
        query: {
          uuid: uuid,
          DirId: dir_id
        }
      })
      window.open(href, '_blank')
    }
  }
}
</script>

<style scoped lang="scss">
.video-list {
  display: flex;
  // width: calc(100% + 24px);
  // margin-right: - 24px;
  flex-wrap: wrap;
  min-height: 300px;

  .transition-wrap {
    display: flex;
    width: 100%;
    flex-wrap: wrap;
    justify-content: space-between;

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
