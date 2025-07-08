<!--
 * @Author: your name
 * @Date: 2021-08-16 16:36:47
 * @LastEditTime: 2021-09-13 20:21:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/videoList/frameItem.vue
-->
<template>
  <div class="video-item" @click.self="goVideoDetail">
    <div class="video-cont">
      <!-- 缩略图 -->
      <div class="poster">
        <video-player
          v-if="!loading"
          ref="videoPlayer"
          :options="playerOptions"
          class="videoPlayer"
          @timeupdate="onTimeupdate"
        />
      </div>
      <!-- 视频标题 -->
      <div class="video-tit" v-html="highLight(videoData.titles)" />
      <div class="video-desc">
        <div>视频时长 {{ videoData.video_duration | timesToHMS }}</div>
        <div>{{ videoData.dir_name }}</div>
      </div>
    </div>

    <!-- 智能识别信息 -->
    <div class="video-ai">
      <VideoSubtitles
        :time-line-data="timelineData"
        :time="time"
        :search-word="searchWord"
        @timeLineHandel="timeLineHandel"
      >
        <template slot="type" slot-scope="props">
          <!-- props保存的是子组件传递过来的数据 -->
          <span class="type">{{ props.type }}</span>
        </template>
      </VideoSubtitles>
    </div>
  </div>
</template>

<script>
import { highLightMsg } from '@/utils/hightLight.js'
import VideoSubtitles from '@/components/VideoSubtitles'
export default {

  components: {
    VideoSubtitles
  },
  props: {
    videoData: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      loading: true,
      time: 0,
      // timeLineData: [
      //   { time: 10, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 20, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 50, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' }
      // ],
      playerOptions: {
        height: '360',
        autoplay: false,
        muted: false,
        language: 'en',
        fluid: true,
        sources: [
          {
            type: 'video/mp4',
            src: ''
          }
        ],
        poster: '',
        aspectRatio: '16:9'
      }
    }
  },
  computed: {
    searchWord() {
      return this.$store.state.videoStatus.searchWord
    },
    timelineData() {
      // 处理vca结果
      const ocr = this.videoData.vca.ocr ? this.videoData.vca.ocr.map(element => {
        element['type'] = '字幕'
        return element
      }) : []
      const asr = this.videoData.vca.asr ? this.videoData.vca.asr.map(element => {
        element['type'] = '语音'
        return element
      }) : []
      const arr = ocr.concat(asr)
      function sortTime(a, b) {
        return a.timestamp - b.timestamp
      }
      arr.sort(sortTime)
      return arr
    }
  },
  watch: {
    videoData: {
      handler: function(data) {
        this.loading = false
        this.playerOptions.sources[0].src = data.video_url
        // this.playerOptions.poster = data.cover_url
      },
      deep: true,
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    highLight(item) {
      return highLightMsg(item, this.searchWord)
    },
    goVideoDetail() {
      this.$emit('goDetail', this.videoData.uuid, this.videoData.dir_id)
    },
    timeLineHandel(time) {
      this.$refs.videoPlayer.pauseAllVideo()
      this.$refs.videoPlayer.player.currentTime(time)
    },
    onTimeupdate(player) {
      this.time = player.currentTime()
    }
  }
}
</script>

<style scoped lang="scss">
.video-item {
  width: 49%;
  height: 330px;
  padding: 20px;
  // margin-right: 24px;
  margin-bottom: 30px;
  border-radius: 4px;
  background: #fff;
  flex-shrink: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;

  &:hover {
    box-shadow: 0px 10px 20px 0px rgba(0, 0, 0, .2);
  }

  .video-cont {
    width: 394px;

    .poster {
      width: 100%;
      height: 222px;
      border-radius: 4px;
      overflow: hidden;
    }

    .video-tit {
      margin-top: 10px;
      font-size: 14px;
      font-weight: 600;
      color: #404040;
      line-height: 18px;
      word-break: break-all;//只对英文起作用，以字母作为换行依据
      word-wrap: break-word; //只对英文起作用，以单词作为换行依据
      white-space: pre-wrap; //只对中文起作用，强制换行
    }

    .video-desc {
      margin-top: 10px;
      font-size: 12px;
      color: #404040;
      display: flex;
      justify-content: space-between;
      align-items: center;
      width: 100%;
      opacity: .6;
    }
  }

  .video-ai {
    margin-left: 32px;
    flex: 1;
    height: 100%;
    overflow: auto;

    .type {
      display: inline-block;
      padding: 0px 4px;
      background: rgba(86,117,232,.2);
      border-radius: 2px;
      font-size: 6px;
      color: #404040;
      margin-left: 10px;

      &.active {
        background: rgba(8,153,73,.2);
      }
    }

    ::v-deep.content {
      font-size: 12px;
    }
  }
}
</style>
