<!--
 * @Author: your name
 * @Date: 2021-07-29 15:11:20
 * @LastEditTime: 2021-08-27 14:55:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/video-detail/components/tools/subtitle.vue
-->
<template>
  <div class="subtitle-wrap">
    <el-input
      v-model="subtitleWord"
      placeholder=""
      size="mini"
      suffix-icon="el-icon-search"
    />
    <div class="title">识别出的字幕文字：</div>

    <VideoSubtitles
      :time-line-data="cont"
      :search-word="subtitleWord"
      @timeLineHandel="timeLineHandel"
    />

  </div>
</template>

<script>
import VideoSubtitles from '@/components/VideoSubtitles'

export default {
  components: {
    VideoSubtitles
  },
  props: {
    cont: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      subtitleWord: ''
      // timeLineData: [
      //   { time: 10, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 12, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 14, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 15, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 16, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 18, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 20, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 22, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 26, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 28, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 50, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' }
      // ]
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
    timeLineHandel(time) {
      this.$store.commit('videoStatus/TIME_LINE_CHANGE', time)
    }
  }
}
</script>

<style scoped lang="scss">
.title {
  font-size: 12px;
  color: #404040;
  margin-top: 10px;
  margin-bottom: 20px;
  opacity: .6;
}
</style>
