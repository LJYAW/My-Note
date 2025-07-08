<!--
 * @Author: your name
 * @Date: 2021-10-08 15:22:52
 * @LastEditTime: 2021-10-22 17:12:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/manage-center/video-manage/mask/index.vue
-->
<template>
  <div v-loading="loading" class="video-list">
    <ul v-if="videoData.length" v-infinite-scroll="load" class="infinite-list transition-wrap">
      <base-card
        v-for="data in videoData"
        :key="data.id"
        class="item"
        :show-mark="false"
        @click.native="showVideo(data)"
      >
        <div slot="img" class="image-wrap">
          <base-image :src="data.cover_url" />
          <div
            v-if="data.status===2"
            class="download-btn"
            @click.stop="download(data)"
          >
            立即下载
          </div>

          <div class="message">
            <div>视频时长 {{ data.video_duration|timesToHMS }}</div>
            <div class="delete" @click.stop="deleteItem(data.uuid)">
              <svg-icon icon-class="delete" />

            </div>
          </div>
        </div>
        <div slot="des" class="video-msg">
          <div class="video-title">{{ data.title }}</div>
          <div class="desc">
            <p>{{ data.create_time }}</p>
            <p :style="`color:${data.status===2?'#5675E8':'#FF6464'}`">{{ initStatus(data.status) }}</p>
          </div>

        </div>
      </base-card>
    </ul>
    <!-- 播放视频弹窗 -->
    <base-dialog
      :show.sync="dialogVisible"
      width="800px"
    >
      <video-player
        ref="videoPlayer"
        :options="playerOptions"
        class="videoPlayer"
      />
      <div class="title">{{ videoTit }}</div>
      <el-button
        style="width: 100%"
        type="primary"
        size="small"
        @click="download(videoDatas)"
      >立即下载</el-button>

    </base-dialog>
  </div>
</template>

<script>
import setQueryParams from '@/utils/setQueryParams'

export default {
  components: {
  },
  props: {
    keyword: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      videoData: [],
      page: 1,
      limit: 20,
      count: 0,
      dialogVisible: false,
      videoTit: '',
      videoDatas: {},
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
      },
      loading: false
    }
  },
  computed: {
    labels() {
      return this.videoData.labels.split(',').filter(function(s) {
        return s && s.trim()
      })
    }
  },
  watch: {

  },
  created() {
    this.initVideoList()
  },
  mounted() {

  },
  methods: {

    search() {
      this.page = 1
      this.videoData = []
      this.initVideoList()
    },

    async initVideoList() {
      this.loading = true
      const query = {
        title__contains: this.keyword
      }
      const params = {
        query: setQueryParams(query),
        page: this.page,
        limit: this.limit
      }
      const { err, res } = await this.$get('/masks/', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.loading = false
      this.videoData = this.videoData.concat(res.data)
      this.count = res.count
      this.pagesize = res.pagesize
    },
    load() {
      if (this.videoData.length < this.count) {
        this.page++
        this.initVideoList()
      }
    },
    async deleteVideo(id) {
      const { err, res } = await this.$deleteRun(`/masks/${id}`)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.$message.success('删除成功')
      this.videoData = []
      this.page = 1
      this.initVideoList()
    },

    async download(obj) {
      const params = {
        uuid: obj.uuid,
        media_type: 'mask'
      }
      const { res, err } = await this.$get('/videos/dl-url', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      window.open(res.data.dl_url, '_blank')
    },

    showVideo(data) {
      if (data.status !== 2) return
      this.videoDatas = data
      this.dialogVisible = true
      this.playerOptions.sources[0].src = data.video_url
      this.videoTit = data.title
    },
    initStatus(val) {
      let str = ''
      switch (val) {
        case 0:
          str = '提交数据'
          break
        case 1:
          str = '处理中'
          break
        case 2:
          str = '生成成功'
          break
        case 3:
          str = '生成失败'
          break
        default:
          str = '处理中'
          break
      }
      return str
    },
    deleteItem(id) {
      this.$confirm('是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteVideo(id)
      }).catch(() => {
      })
    }
  }
}
</script>

<style scoped lang="scss">
.video-list {
  width: 100%;
  min-height: 300px;

  .transition-wrap {
    display: grid;
    grid-template-columns: repeat(4, 25%);  //同上
    grid-row-gap: 30px;
    margin: 0 -12px;
  }

  .item {
    transition: all 1s;
    margin: 0 12px;

    ::v-deep .image {
      position: relative;
    }

    .image-wrap {
      width: 100%;
      height: 100%;

      ::v-deep.el-image__inner {
        object-fit: cover;
      }
    }

    .download-btn {
      background: #5675e8;
      border-radius: 4px;
      border-radius: 4px;
      color: #fff;
      position: absolute;
      right: 10px;
      top: 10px;
      z-index: 9;
      font-size: 12px;
      padding: 3px 8px;
      cursor: pointer;
      display: flex;
      align-items: center;

      img {
        display: inline-block;
        width: 14px;
        background: rgba(255, 255, 255, .01);
        box-shadow: 0px 10px 20px 0px rgba(0, 0, 0, .2);
        margin-right: 4px;
        object-fit: cover;
        border-radius: 4px;
        overflow: hidden;
      }
    }

    .message {
      position: absolute;
      bottom: 0;
      width: 100%;
      background: rgba(0,0,0,.4);
      height: 30px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      color: #fff;
      padding: 0 10px;

      .delete {
        color: #fff;

        svg {
          font-size: 18px;
        }
      }
    }

    .video-msg {

      .video-title {
        font-size: 14px;
        color: #404040;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;

      }

      .desc {
        display: flex;
        font-size: 12px;
        color: #bababa;
        margin-top: 10px;
        flex-wrap: wrap;
        align-items: center;
        justify-content: space-between;

        p {
          margin-bottom: 10px;
        }
      }

    }
  }

}

.title {
  font-size: 14px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: #404040;
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>
