<!--
 * @Author: your name
 * @Date: 2021-07-29 10:56:09
 * @LastEditTime: 2021-09-26 20:39:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/video-detail/components/videoDetail.vue
-->
<template>
  <div class="video-detail">
    <video-player
      v-if="!loading"
      ref="videoPlayer"
      :options="playerOptions"
      class="videoPlayer"
      @timeupdate="onTimeupdate"
    />

    <div class="video-desc">
      <div class="title">
        <span>{{ videoData.titles }}</span>
        <div class="tips">{{ videoData.vca_status|vcaStatus }}</div>
      </div>
      <div class="desc">{{ videoData.create_time }} 本地上传 {{ videoData.dir_name }}</div>
      <div class="btn-box">
        <el-button type="primary" size="small" @click="goToVideoCut">快速剪辑</el-button>
        <el-button type="primary" size="small" @click="downLoad">高清下载</el-button>
        <el-button :loading="btnLoading" type="info" size="small" @click="fav">{{ videoData.fav_id?'取消收藏':'收藏' }}</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      loading: true,
      btnLoading: false,
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
    timeLine() {
      return this.$store.state.videoStatus.timeLine
    },
    videoData() {
      return this.$store.state.videoStatus.videoData
    }
  },
  watch: {
    timeLine: {
      handler: function(s) {
        this.setVideoTime(s)
      }
    },
    videoData: {
      handler: function(data) {
        this.loading = false
        this.playerOptions.sources[0].src = data.video_url
        // this.playerOptions.poster = data.cover_url
      },
      deep: true
    }
  },
  created() {

  },
  mounted() {
    this.playerOptions.sources[0].src = this.$store.state.videoStatus.videoData
  },
  methods: {
    async downLoad() {
      const params = {
        uuid: this.$route.query.uuid,
        media_type: 'video'
      }
      const { res, err } = await this.$get('/videos/dl-url', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      window.open(res.data.dl_url, '_blank')
    },
    async fav() {
      this.btnLoading = true
      const uuid = this.$route.query.uuid
      const { fav_id } = this.videoData
      const { err, res } = await fav_id ? this.$deleteRun(`/favorites/${fav_id}`) : this.$post('/favorites/', { video_uuid: `${uuid}` })
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      setTimeout(() => {
        this.$emit('getDetail')
        this.btnLoading = false
      }, 500)
    },
    setVideoTime(s) {
      this.$refs.videoPlayer.player.currentTime(s)
    },
    onTimeupdate(player) {
      this.$store.commit('videoStatus/VIDEO_TIME_CHANGE', player.currentTime())
    },
    goToVideoCut() {
      sessionStorage.setItem('videoCutDetails', JSON.stringify(this.videoData))
      this.$router.push({
        path: '/video-edit'
      })
    }
  }
}
</script>

<style scoped lang="scss">
.video-detail {
  flex: 1;
  height: 535px;

  .videoPlayer {
    border-radius: 4px ;
    overflow: hidden;
    background: #fff;
  }

  .video-desc {
    margin-top: 20px;
    background: #fff;
    border-radius: 4px;
    box-sizing: border-box;
    padding: 20px;

    .title {
      font-size: 18px;
      font-weight: 600;
      color: #404040;
      display: flex;
      justify-content: space-between;

      .tips {
        font-size: 12px;
        color: #fff;
        border-radius: 2px;
        box-sizing: border-box;
        padding: 3px 5px;
        background: #25c86e;
      }
    }

    .desc {
      font-size: 12px;
      color: #404040;
      line-height: 12px;
      margin-top: 10px;
      margin-bottom: 20px;
      opacity: .6;
    }

    .btn-box {
      display: flex;

      div {
        background: #5675e8;
        border-radius: 4px;
        box-sizing: border-box;
        padding: 4px;
        color: #fff;
        margin-right: 10px;
        font-size: 12px;
      }

      .gray {
        background: #efefef;
        border-radius: 4px;
        opacity: .8;
        color: #404040;
      }
    }
  }
}
</style>
