<!--
 * @Author: your name
 * @Date: 2021-07-28 10:19:49
 * @LastEditTime: 2021-08-23 15:44:44
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/videoList/index.vue
-->
<template>
  <div class="video-list">
    <transition-group name="list" tag="div" class="transition-wrap">
      <VideoCard v-for="data in videoData" :key="data.id" :video-data="data.video" class="item" :collection-video-data="data" @goDetail="goDetail" />
    </transition-group>
  </div>
</template>

<script>
import VideoCard from '@/components/VideoCard'

export default {

  components: {
    VideoCard
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
  methods: {
    goDetail(uuid) {
      this.$router.push({
        path: '/video-detail',
        query: {
          uuid: uuid
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.video-list {
  // display: flex;
  width: 100%;
  // margin-right: - 24px;
  // flex-wrap: wrap;
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
