<!--
 * @Author: your name
 * @Date: 2021-09-22 18:24:35
 * @LastEditTime: 2021-10-22 17:44:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/manage-center/video-manage/script-create/index.vue
-->
<template>
  <div v-loading="loading">
    <ul v-if="videoData.length" v-infinite-scroll="load" class="infinite-list transition-wrap">
      <base-card
        v-for="item in videoData"
        :key="item.id"
        :show-mark="false"
        class="video-item infinite-list-item"
        @click.native="routeTo(item)"
      >
        <div slot="img">
          <div v-if="item.status=='待处理'||item.status=='处理中'" class="image-build">
            <img src="@/assets/images/jianshi/created_ing.png" alt="">
          </div>
          <div v-if="item.status=='失败'" class="image-build">
            <base-image v-if="item.status=='失败'" src="" />
          </div>
          <VideoCard v-if="item.status=='完成'" :cover-url="item.cover_url" :video-url="item.video_url" />
          <div v-if="item.status=='完成'" class="down-btn" @click.stop="downloadVideo(item)">
            <base-btn size="mini">立即下载</base-btn>
          </div>
          <div class="btn-wrap">
            <p><span v-if="item.duration">视频时长 {{ item.duration|timesToHMS }}</span></p>
            <svg-icon icon-class="delete" @click.stop.native="deleteVideo(item.uuid)" />
          </div>
        </div>
        <div slot="des">
          <p class="desc">{{ item.title }}</p>
          <div class="video-status">
            <p class="date">{{ item.create_time }}</p>
            <p :class="setStyleClass(item.status)">{{ item.status }}</p>
          </div>
        </div>
      </base-card>
    </ul>
    <svg-icon v-else icon-class="empty-img" class="empty-icon" />
  </div>
</template>

<script>
import { GetScriptCreate, DeleteScriptCreate, DownScriptCreate } from '@/app/jianshi-video/api/script_creation/index.js'
import setQueryParams from '@/utils/setQueryParams.js'
import VideoCard from '@/app/jianshi-video/components/VideoCard'
import { downloadVideo } from '@/app/jianshi-video/utils/download.js'
export default {
  components: {
    VideoCard
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
      loading: false
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getInitData()
  },
  mounted() {

  },
  methods: {
    routeTo(obj) {
      return
    //   if (obj.status !== 2) return
    //   const { href } = this.$router.resolve({
    //     path: '/jianshi-video/subtitle-create',
    //     query: {
    //       id: obj.id
    //     }
    //   })
    //   window.open(href, '_blank')
    },
    setStyleClass(status) {
      switch (status) {
        case '处理中':
          return 'is-building'
        case '失败':
          return 'build-fail'
        case '完成':
          return 'build-success'
        default:
          return 'to-build'
      }
    },
    load() {
      if (this.videoData.length < this.count) {
        this.page++
        this.getInitData()
      }
    },
    search() {
      this.page = 1
      this.videoData = []
      this.getInitData()
    },
    async getInitData() {
      this.loading = true
      const query = {
        title__contains: this.keyword,
        source: this.source
      }
      const params = {
        page: this.page,
        limit: this.limit,
        query: setQueryParams(query)
      }
      const { err, res } = await GetScriptCreate(params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.loading = false
      this.count = res.count
      this.videoData = this.videoData.concat(res.data)
    },
    deleteVideo(id) {
      this.$confirm('是否确认删除视频?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.confirmDeleteVideo(id)
      }).catch(() => {

      })
    },
    async confirmDeleteVideo(id) {
      const params = {
        id: id
      }
      const { err, data } = await DeleteScriptCreate(params)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        this.page = 1
        this.videoData = []
        this.getInitData()
      }
    },
    async downloadVideo(obj) {
      const params = {
        id: obj.uuid
      }
      const { err, res } = await DownScriptCreate(params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      downloadVideo(res.data.dl_url, obj.title)
    }
  }
}
</script>

<style scoped lang="scss">
.infinite-list {

  ::v-deep .image {
    background: none;

    .image-build {
      height: 0;
      padding-bottom: 56.25%;
      background: #eee;

      .el-image {
        position: static;

        svg {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
        }
      }

      img {
        position: absolute;
      }
    }
  }
}
</style>
