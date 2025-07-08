<!--
 * @Author: your name
 * @Date: 2021-04-21 16:00:17
 * @LastEditTime: 2021-04-22 13:23:55
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/business/showDateilsModel/PlayVideo.vue
-->
<template>
  <base-dialog
    :show="show"
    :title="dialogsObj.title"
    :width="dialogsObj.width"
    :center="dialogsObj.center"
    @close="close"
  >
    <div class="video-message">
      <div>
        <span>频道名称:</span>
        <span>{{ videoData.channel_name }}</span>
      </div>
      <div>
        <span>任务时间段:</span>
        <span>
          {{ videoData.start_time }}
          ~
          {{ videoData.end_time }}
        </span>
      </div>
    </div>
    <div class="play-video">
      <VideoHls
        ref="videoPlay"
        class="videoPaly"
        :player-options="playerOptions"
      />
    </div>
  </base-dialog>
</template>

<script>
import VideoHls from '@/components/VideoHlsCut/VideoHls.vue'
export default {
  naem: 'playVide',
  components: { VideoHls },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    videoData: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      playerOptions: {
        playbackRates: [0.5, 1, 1.5, 2],
        src: ''
      },
      dialogsObj: {
        width: '500px',
        title: 'EPG流监控',
        center: true
      }
    }
  },
  computed: {
  },
  watch: {
    videoData: {
      handler() {
        this.playerOptions.src = this.videoData.url
      },
      immediate: true
    }
  },
  mounted() {
  },
  created() {
  },
  methods: {
    close() {
      this.$emit('playFolwShow')
    }
  }
}
</script>

<style lang='scss' scoped>
 .video-message{
    display: flex;
    margin-bottom: 20px;
    div{
      margin-right: 20px;
    }
  }
.play-video{
    width: 100%;
    height: 100%;

    .vjs-custom-skin{
        width: 100%;
        height: 100%;
    }
    ::v-deep .vjs-control-bar{
      .vjs-fullscreen-control{
        display: none !important;
      }
    }
}
</style>
