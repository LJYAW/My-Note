<!--
 * @Author: your name
 * @Date: 2021-09-22 18:10:36
 * @LastEditTime: 2021-09-26 21:22:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/manage-center/video-manage/subtitle/index.vue
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
          <VideoCard :cover-url="item.cover_url" :video-url="item.video_url" />
          <div v-if="item.status==1" class="down-btn" @click.stop="downloadVideo(item)">
            <base-btn size="mini">立即下载</base-btn>
          </div>
          <div class="btn-wrap">
            <p>视频时长 {{ item.video_duration|timesToHMS }}</p>
            <svg-icon icon-class="delete" @click.stop.native="deleteVideo(item.id)" />
          </div>
        </div>
        <div slot="des">
          <p class="desc">{{ item.titles }}</p>
          <div class="video-status">
            <p class="date">{{ item.create_time }}</p>
            <p :class="setStyleClass(item.status)">{{ videoStatus[item.status] }}</p>
          </div>
        </div>
      </base-card>
    </ul>
    <svg-icon v-else icon-class="empty-img" class="empty-icon" />
  </div>
</template>

<script>
import { GetVideos, DeleteVideos } from '@/app/jianshi-video/api/videos/index.js'
import { downloadVideo } from '@/app/jianshi-video/utils/download.js'
import setQueryParams from '@/utils/setQueryParams.js'
import VideoCard from '@/app/jianshi-video/components/VideoCard'
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
      videoStatus: {
        1: '生成成功',
        2: '分析成功',
        0: '未分析',
        '-1': '分析中',
        '-2': '分析失败',
        '-20': '待生成视频',
        '-21': '生成视频中',
        '-22': '生成失败'
      },
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
      if (obj.status !== 2 && obj.status !== 1) return
      const { href } = this.$router.resolve({
        path: '/jianshi-video/subtitle-create',
        query: {
          id: obj.id
        }
      })
      window.open(href, '_blank')
    },
    setStyleClass(status) {
      switch (status) {
        case -21: case -1:
          return 'is-building'
        case -22: case -2:
          return 'build-fail'
        case 1: case 2:
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
        titles__contains: this.keyword
      }
      const params = {
        page: this.page,
        limit: this.limit,
        query: setQueryParams(query)
      }
      const { err, res } = await GetVideos(params)
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
      const { err, data } = await DeleteVideos(params)
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
      const url = process.env.VUE_APP_BASE_API_JIANSHI + '/videos/downloadvideo/' + obj.id
      downloadVideo(url, obj.titles)
    }
  }
}
</script>

<style scoped lang="scss">

</style>
