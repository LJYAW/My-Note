<!--
 * @Author: your name
 * @Date: 2021-08-09 19:48:27
 * @LastEditTime: 2021-09-23 16:50:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/sections/video-edit.vue
-->
<template>
  <div class="video-edit-main-wrap">

    <ToolsHeader name="视频剪辑">
      <div slot="tit" class="title-wrap">
        <el-input
          v-if="showInput"
          v-model="formTitle"
          size="mini"
          placeholder="请输入标题信息"
          maxlength="40"
          show-word-limit
          class="subtitletitel"
          type="text"
          @blur.stop="setTitle"
        />
        <p v-else>{{ formTitle }}</p>
        <svg-icon icon-class="edit" @click="showInput = !showInput" />
      </div>
      <div slot="btn">
        <!-- <base-btn @click="exportSubtitle">导出字幕</base-btn> -->
        <base-btn v-if="!timeLineLoading" @click="submitVideoCutData">生成视频</base-btn>
      </div>

    </ToolsHeader>

    <VideoEdit>

      <AiVideoDetails slot="rightPad" />

    </VideoEdit>

    <BaseDialog :show.sync="submitShow">
      <div class="submit-cut-wrap">
        <p class="text">{{ submitText }}</p>

        <div v-if="!submmitSuccess">
          <el-button type="info" size="mini" @click="cancelCut">取消</el-button>
          <el-button :loading="btnLoading" type="primary" size="mini" @click="submit">确认剪辑</el-button>
        </div>

        <el-button v-else type="primary" size="mini" @click="gotoMyCutVideo">跳转到我的剪辑</el-button>
      </div>
    </BaseDialog>
  </div>
</template>

<script>
// import VideoEdit from '@/app/video-edit-v2/main.vue'
import VideoEdit from '@/app/video-edit/index.vue'
import { mapState } from 'vuex'
import AiVideoDetails from '../video-detail/components/toolPad.vue'
import ToolsHeader from '@/components/ToolsHeader/index'

export default {
  name: 'VideoEditWrap',
  components: {
    VideoEdit,
    AiVideoDetails,
    ToolsHeader
  },
  props: {

  },
  data() {
    return {
      formTitle: '',
      showInput: false,
      timeLineLoading: true,
      submitShow: false,
      submitText: '',
      submmitSuccess: false,
      btnLoading: false,

      videoCutDetails: {}
    }
  },
  computed: {
    ...mapState('video', ['cutTrackData', 'videoPlay', 'videoOptions', 'PER_PX_MS', 'currentTimeMs', 'canvasOptions', 'decorateList']),
    ...mapState('videoStatus', ['timeLine'])

  },
  watch: {
    currentTimeMs(ms) {
      this.$store.commit('videoStatus/VIDEO_TIME_CHANGE', ms / 1000)
    },
    timeLine(s) {
      this.$store.commit('video/VIDEO_PAUSE')
      this.$store.commit('video/SEEK_CURRENT_TIME_MS', s * 1000)
      this.$store.commit('video/SET_VIDEO_CURRENTTIME_MS', s * 1000)
    }
  },
  created() {
    this.videoCutDetails = JSON.parse(sessionStorage.getItem('videoCutDetails'))
  },
  mounted() {
    this.$store.commit('video/SET_CANVAS_SIZE')
    window.onresize = () => {
      this.$store.commit('video/SET_CANVAS_SIZE')
    }
    this.initVideoOptions()
    // 视频分析所需数据
    this.$store.commit('videoStatus/VIDEO_DATA_CHANGE', this.videoCutDetails)
    // 监听 视频剪辑提交的按钮
    // this.$bus.$on('videoSubmit', this.submitVideoCutData)

    this.$bus.$on('timeLineLoadingIsReady', () => {
      this.timeLineLoading = false
    })
  },
  beforeDestroy() {
    this.$bus.$off('videoSubmit')
  },
  methods: {
    setTitle() {
      this.showInput = false
    },
    initVideoOptions() {
      const { cover_url, video_url, titles } = this.videoCutDetails
      console.log('🚀 ~ file: video-edit.vue ~ line 123 ~ initVideoOptions ~ this.videoCutDetails', this.videoCutDetails)
      this.formTitle = titles

      // 视频剪辑所需信息
      this.$store.commit('video/SET_VIDEO_OPTIONS', {
        poster: cover_url,
        src: video_url
      })
    },
    async submit() {
      this.btnLoading = true
      this.submmitSuccess = false
      const params = this.setVideoCutParams()
      const { err, res } = await this.$post('/clips/', params)

      if (err) {
        this.submitText = err.msg
        return
      } else {
        this.submmitSuccess = true
        this.submitText = '创建剪辑任务成功'
      }

      this.btnLoading = false
    },
    gotoMyCutVideo() {
      this.$router.push({
        path: '/my-cut-videos'
      })
    },
    cancelCut() {
      this.submitShow = false
    },
    submitVideoCutData() {
      this.submitShow = true
      const { startMs, endMs } = this.cutTrackData
      this.submitText = `当前剪辑秒数：${Math.floor((endMs - startMs) / 1000)} 秒`
    },
    setVideoCutParams() {
      const { startMs, endMs } = this.cutTrackData
      const { id } = this.videoCutDetails
      const { logo_x, logo_y, logo_show } = this.decoratePosition()

      const params = {
        cut_start: Math.floor(startMs),
        cut_end: Math.floor(endMs),
        video_id: id,
        logo_x: logo_x,
        logo_y: logo_y,
        logo_show: logo_show
      }
      return params
    },
    decoratePosition() {
      let logo_x = 0
      let logo_y = 0
      let logo_show = 0

      const data = this.decorateList[0]

      if (data) {
        const videoW = this.videoPlay.videoWidth
        const videoH = this.videoPlay.videoHeight
        const canvasW = this.canvasOptions.canvasW
        const canvasH = this.canvasOptions.canvasH
        const { x, y } = data

        logo_x = Math.floor(x / canvasW * videoW)
        logo_y = Math.floor(y / canvasH * videoH)
        logo_show = 1
      }

      return {
        logo_x: logo_x,
        logo_y: logo_y,
        logo_show: logo_show
      }
    }
  }
}
</script>

<style scoped lang="scss">
.video-edit-main-wrap {
  height: calc(100vh - 80px);
  width: min-content;
  margin: 0 auto;

  .right {
    display: flex;

    .tool-pad {
      height: 100%;
      width: 100%;
      flex: 1;
      margin-left: 0;
      padding-top: 10px;

      ::v-deep .el-tabs {
        height: 100%;
        display: flex;
        flex-direction: column;

        .el-tabs__header {
          height: 39px;
        }

        .el-tabs__content {
          height: 100%;

          .el-tab-pane {
            height: 100%;
            overflow: hidden;
            overflow-y: auto;
          }
        }
      }
    }
  }
}

.submit-cut-wrap {
  text-align: center;

  .text {
    margin-bottom: 30px;
  }
}

.title-wrap {
  display: flex;
  justify-content: center;
  align-items: center;

  p {
    margin-right: 10px;
  }

  .subtitletitel {
    width: 280px;
    margin-right: 10px;
  }
}
</style>
