<!--
 * @Author: your name
 * @Date: 2021-07-28 10:19:49
 * @LastEditTime: 2021-08-30 17:19:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/videoList/index.vue
-->
<template>
  <div class="video-list">

    <RecycleScroller
      v-slot="{ item }"
      class="scroller"
      :items="tablistTemp"
      :item-size="250"
      key-field="id"
    >
      <div class="transition-wrap">
        <VideoCard v-for="(data) in item.arr" :key="data.id" collection :video-data="data" class="item observe" @goDetail="goDetail" />
      </div>
    </RecycleScroller>
    <Normal v-if="videoData.length<=0" />
  </div>
</template>

<script>
const LIMIT = 10

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
    tablistTemp() {
      const num = Math.ceil(this.videoData.length / 4)
      const temp = new Array(num)
      for (let i = 0; i < num; i++) {
        temp[i] = {
          id: i,
          arr: this.videoData.slice(i * 4, i * 4 + 4)
        }
      }
      return temp
    }
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
    },
    showVideo(item) {
      console.log(item)
      this.activeVideoItem = item
    },
    vidoeHiden() {
      this.activeVideoItem = {}
    }
  }
}
</script>

<style scoped lang="scss">
.video-list {
  width: 100%;
  min-height: 300px;

  .transition-wrap {
    padding-top: 5px;
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
