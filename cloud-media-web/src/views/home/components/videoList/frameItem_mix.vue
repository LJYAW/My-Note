<!--
 * @Author: your name
 * @Date: 2021-08-16 16:36:47
 * @LastEditTime: 2021-09-27 12:24:05
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/videoList/frameItem.vue
-->
<template>
  <div :class="showCheckbox?'active':''" class="video-item" @click="goVideoDetail">
    <div class="video-cont video-cont311" @mouseenter.self="playVideo()" @mouseleave.self="pauseVideo()" @click="pauseVideo">
      <!-- 缩略图 -->
      <div class="poster">
        <!-- <video-player
          v-if="!loading"
          ref="videoPlayer"
          :options="playerOptions"
          class="videoPlayer"
          @timeupdate="onTimeupdate"
        /> -->
        <video
          ref="videoPlayer"
          :src="videoSrc"
          :poster="videoPoster"
          style="width: 100%;height: 100%"
          @timeupdate="onTimeupdate"
        />
      </div>
    </div>

    <!-- 智能识别信息 -->
    <div class="video-ai">
      <!-- 视频标题 -->
      <div class="video-tit" @click="goVideoDetail" v-html="highLight(videoData.titles)" />
      <div class="video-desc">
        <div>
          <span>{{ videoData.create_time }}</span>
          <span>视频时长 {{ videoData.video_duration | timesToHMS }}</span>
          <span :class="videoData.fav_id?'act':''" class="actbtn" @click.stop="fav">{{ videoData.fav_id?'已收藏':'收藏' }}</span>
        </div>
        <!--<div>{{ videoData.dir_name }}</div>-->
      </div>
      <div style="margin-top: 10px;">
        <div v-if="videoData.tags" class="tags-box">
          <div
            v-for="(item, index) in videoData.tags.split(',')"
            :key="index"
            class="c-item"
            :class="checkTags(item)"
          >
            {{ item }}
          </div>
        </div>

        <template v-if="videoData.vca.ocr || videoData.vca.asr">
          <div class="timeline-box">
            <VideoSubtitles
              :updown="timelineData.length > 10"
              :time-line-data="timelineData"
              :time="time"
              :search-word="searchWord"
              @timeLineHandel="timeLineHandel"
              @click.stop.native
            >
              <template slot="type" slot-scope="props">
                <!-- props保存的是子组件传递过来的数据 -->
                <span class="type">{{ props.type }}</span>
              </template>
            </VideoSubtitles>
          </div>
        </template>
        <template v-else>
          <div class="infos-box">
            <span>分辨率 {{ videoData.video_width }}*{{ videoData.video_height }}</span>
            <span>视频目录 {{ videoData.dir_name }}</span>
            <span>视频大小 {{ Math.floor(videoData.video_size / 1024 / 1024) }}M</span>
          </div>
        </template>

      </div>
    </div>

    <!-- 分享选择 -->
    <div v-if="showCheckbox" class="share-wrap" @click.stop>
      <el-checkbox v-model="videoSelectFlag" label="" :indeterminate="false" @change="handlerChange" />
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
      videoSrc: '',
      videoPoster: '',
      // timeLineData: [
      //   { time: 10, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 20, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
      //   { time: 50, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' }
      // ],
      // playerOptions: {
      //   height: '124',
      //   autoplay: false,
      //   muted: false,
      //   language: 'en',
      //   fluid: true,
      //   preload: false,
      //   sources: [
      //     {
      //       type: 'video/mp4',
      //       src: ''
      //     }
      //   ],
      //   poster: '',
      //   aspectRatio: '16:9'
      // },

      // // 是否分享状态
      showCheckbox: false,
      // // 是否已选择当前视频
      videoSelectFlag: false
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
        this.videoSrc = data.video_url
        this.videoPoster = data.cover_url
      },
      deep: true,
      immediate: true
    },
    '$store.state.videoStatus.batchVideo': {
      handler: function(val) {
        this.videoSelectFlag = this.$route.fullPath === '/my-collection'
          ? val.includes(this.collectionVideoData.id) : val.includes(this.videoData.uuid)
      },
      deep: true
    }
  },
  created() {
    this.$bus.$on('batchFlag', (type, vcaFlag) => {
      this.showCheckbox = type
    })
  },
  mounted() {

  },
  methods: {
    playVideo() {
      this.$refs.videoPlayer.play()
    },
    pauseVideo() {
      this.$refs.videoPlayer.pause()
    },
    checkTags(item) {
      return item === this.searchWord ? 'acitve' : ''
    },
    highLight(item) {
      return highLightMsg(item, this.searchWord)
    },
    goVideoDetail() {
      this.$emit('goDetail', this.videoData.uuid, this.videoData.dir_id)
    },
    timeLineHandel(time) {
      // this.$refs.videoPlayer.player.play()
      this.$refs.videoPlayer.currentTime = time
      // this.$refs.videoPlayer.player.psause()
    },
    onTimeupdate() {
      this.time = this.$refs.videoPlayer.currentTime
    },
    handlerChange(e) {
      const arr = this.$store.state.videoStatus.batchVideo
      if (e) {
        !arr.includes(this.videoData.uuid) && arr.push(this.videoData.uuid)
      } else {
        var index = arr.indexOf(this.videoData.uuid)
        index > -1 && arr.splice(index, 1)
      }
      this.$store.commit('videoStatus/BATCH_VIDEO_CHANGE', arr)
    },
    async fav() {
      const { uuid, fav_id } = this.videoData
      const { err, res } = fav_id ? await this.$deleteRun(`/favorites/${fav_id}`) : await this.$post('/favorites/', { video_uuid: `${uuid}` })
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.videoData.fav_id = res.data ? res.data.id : 0
    }
  }
}
</script>

<style lang="scss">
.video-cont311 {

  .vjs-theme-fantasy {

    .vjs-big-play-button {
      font-size: 40px;
    }
  }
}
</style>
<style scoped lang="scss">
.video-item {
  position: relative;
  width: calc(83% + 10px);
  margin-left: -10px;
  padding: 10px;
  margin-bottom: 30px;
  flex-shrink: 0;
  display: flex;
  justify-content: space-between;

  &:hover,
  &.active {
    background: #fff;
  }

  .video-cont {
    width: 342px;

    .poster {
      width: 100%;
      height: 192px;
      border-radius: 4px;
      overflow: hidden;
      background: #efefef;
    }
  }

  .video-tit {
    font-size: 18px;
    font-weight: 600;
    color: #404040;
    line-height: 24px;
    white-space: nowrap;
    text-overflow: ellipsis;
    cursor: pointer;

    &:hover {
      color: #5675e8;
    }
  }

  .video-desc {
    margin-top: 10px;
    font-size: 12px;
    color: #a0a0a0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;

    span {
      margin-right: 12px;

      &.actbtn {
        cursor: pointer;
        color: #666;

        &.act {
          color: #5675e8;
        }
      }
    }
  }

  .timeline-box {
    margin-top: 10px;
    background-color: #f4f4f5;
    padding: 14px 20px;
  }

  .video-ai {
    margin-left: 24px;
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

  .tags-box {
    margin-top: 5px;
    display: flex;
    flex-wrap: wrap;
    font-size: 12px;

    .c-item {
      height: 24px;
      line-height: 24px;
      background: #efefef;
      border-radius: 4px;
      padding: 0 10px;
      margin-top: 5px;
      margin-right: 10px;
      opacity: .8;

      &.acitve {
        color: #fff;
        background: #5675e8;
      }
    }
  }

  .infos-box {
    margin-top: 10px;
    font-size: 12px;
    color: #a0a0a0;
    display: flex;
    align-items: center;

    span {
      padding-right: 20px;
      margin-right: 20px;
      border-right: 1px solid rgba(151, 151, 151, .2);

      &:last-child {
        margin-right: 0;
        padding-right: 0;
        border: 0;
      }
    }
  }

  .share-wrap {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    display: flex;
    justify-content: flex-end;
    box-sizing: border-box;
    padding: 10px;

    .el-checkbox {
      width: 100%;
      height: 100%;

      ::v-deep.el-checkbox__input {
        position: absolute;
        right: 0;
      }

      ::v-deep.el-checkbox__inner {
        border-color: transparent;
        border-radius: 50%;
        overflow: hidden;
      }
    }

  }
}

</style>
