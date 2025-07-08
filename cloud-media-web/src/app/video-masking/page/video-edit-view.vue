<!--
 * @Author: your name
 * @Date: 2021-09-01 16:26:12
 * @LastEditTime: 2021-10-09 19:22:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-masking/page/video-edit-view.vue
-->
<template>
  <div class="video-edit-view">

    <ToolsHeader name="视频去水印">
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
          @blur="setTitle"
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
      <div slot="rightPad">
        <VideoMessage />
      </div>
    </VideoEdit>

    <BaseDialog :show.sync="submitShow">
      <div class="submit-cut-wrap">
        <p class="text"> {{ submitText }}</p>

        <div v-if="!submmitSuccess">
          <el-button type="info" size="mini" @click="cancelCut">取消</el-button>
          <el-button :loading="btnLoading" type="primary" size="mini" @click="submit">确认生成</el-button>
        </div>

        <el-button v-else type="primary" size="mini" @click="gotoMyCutVideo">跳转到我的剪辑</el-button>
      </div>
    </BaseDialog>
  </div>
</template>

<script>
import VideoEdit from '@/app/video-edit/index.vue'
import VideoMessage from '../components/videoMessage.vue'
import { mapState } from 'vuex'
import ToolsHeader from '@/components/ToolsHeader/index'
export default {
  components: {
    VideoEdit,
    ToolsHeader,
    VideoMessage
  },
  props: {

  },
  data() {
    return {
      timeLineLoading: true,
      formTitle: '',
      showInput: false,
      submmitSuccess: false,
      submitText: '是否确认生成视频',
      btnLoading: false,
      submitShow: false
    }
  },
  computed: {
    ...mapState('video', ['cutTrackData', 'videoPlay', 'videoOptions', 'PER_PX_MS', 'currentTimeMs', 'canvasOptions', 'decorateList', 'videoSize', 'VIDEO_DURANTION_MS'])
  },
  watch: {

  },
  created() {

  },
  mounted() {
    this.$store.commit('video/SET_CANVAS_SIZE', 0.65)
    window.onresize = () => {
      this.$store.commit('video/SET_CANVAS_SIZE', 0.65)
    }

    const videoMarkDetails = JSON.parse(sessionStorage.getItem('videoMarkDetails'))
    this.formTitle = videoMarkDetails.titles

    this.$store.commit('video/SET_VIDEO_OPTIONS', {
      poster: videoMarkDetails.cover_url || '',
      src: videoMarkDetails.video_url
    })

    this.$store.commit('video/SET_TRACK_SETTING', {
      cutTrack: false,
      decorateTrack: true
    })

    // 监听 视频剪辑提交的按钮
    this.$bus.$on('videoSubmit', this.submitVideoCutData)
    this.$bus.$on('timeLineLoadingIsReady', () => {
      this.timeLineLoading = false
    })

    // 隐藏公共头部
    this.$store.dispatch('settings/changeSetting', { showHeader: false })
  },
  destroyed() {
    this.$store.commit('video/resetState')
    this.$store.unregisterModule('video')
  },
  methods: {
    // generateVideo() {
    //   this.$bus.$emit('videoSubmit')
    // },
    setTitle() {
      this.showInput = false
      this.$store.commit('jianshi/SET_TITLE', this.formTitle)
    },
    async submit() {
      this.btnLoading = true
      this.submmitSuccess = false
      const params = this.setVideoCutParams()
      const { err, res } = await this.$post('/masks/', params)

      if (err) {
        this.submitText = err.msg
        return
      } else {
        this.submmitSuccess = true
        this.submitText = '创建剪辑任务成功'
      }
      // history.go(-1)
      this.$router.push('/manage-center/creative-center?tab=视频水印')
      this.btnLoading = false
    },
    submitVideoCutData() {
      this.submitShow = true
    },
    cancelCut() {
      this.submitShow = false
    },
    setVideoCutParams() {
      const decorateList = this.decorateList
      const decorate = []

      decorateList.forEach(item => {
        console.log(item)

        const types = {
          gaussBlur: 'dlogo',
          image: 'logo'
        }
        // 上传百度的 image key
        const pic_key = !item.imgKey ? '' : item.imgKey
        const { top, left, width, height } = this.decoratePosition(item)
        const start_time = Math.floor(item.trackLeft * this.PER_PX_MS)
        const end_time = Math.floor((item.trackLeft + item.trackWidth) * this.PER_PX_MS)

        decorate.push({
          start_time: start_time,
          end_time: end_time,
          type: types[item.type],
          pic_key: pic_key,
          height: height,
          width: width,
          x: left,
          y: top
        })
      })

      const videoMarkDetails = JSON.parse(sessionStorage.getItem('videoMarkDetails'))
      const video_local_key = !videoMarkDetails.video_local_key ? '' : videoMarkDetails.video_local_key
      const video_uuid = videoMarkDetails.uuid
      const titles = this.formTitle

      const params = {
        params: decorate,
        video_duration: Math.floor(this.VIDEO_DURANTION_MS),
        video_local_key: video_local_key,
        video_url: videoMarkDetails.video_url,
        video_uuid: video_uuid,
        video_height: this.videoSize.h,
        video_width: this.videoSize.w,
        title: titles
      }
      return params
    },
    decoratePosition(data) {
      const { x, y, w, h } = data

      const videoW = this.videoPlay.videoWidth
      const videoH = this.videoPlay.videoHeight
      const canvasW = this.canvasOptions.canvasW
      const canvasH = this.canvasOptions.canvasH

      const top = Math.floor(y / canvasH * videoH)
      const left = Math.floor(x / canvasW * videoW)
      const width = Math.floor(w / canvasW * videoW)
      const height = Math.floor(h / canvasH * videoH)

      return {
        top,
        left,
        width,
        height
      }
    },
    gotoMyCutVideo() {
      this.$router.push('/video-masking/view')
    }
  }
}
</script>

<style lang="scss">
.video-edit-view {
  height: 100%;
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
