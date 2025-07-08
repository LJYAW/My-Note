<!--
 * @Author: your name
 * @Date: 2021-09-22 18:24:35
 * @LastEditTime: 2021-09-29 15:25:01
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
          <div v-if="item.status=='已创建'||item.status=='生成中'" class="image-build">
            <img src="@/assets/images/jianshi/created_ing.png" alt="">
          </div>
          <div v-if="item.status=='生成失败'" class="image-build">
            <base-image v-if="item.status=='生成失败'" src="" />
          </div>
          <VideoCard v-if="item.status=='生成成功'" :cover-url="item.cover_url" :video-url="item.video_url" />
          <div v-if="item.status=='生成成功'" class="down-btn" @click.stop="downloadVideo(item)">
            <base-btn size="mini">立即下载</base-btn>
          </div>
          <div class="btn-wrap">
            <p><span v-if="item.duration">视频时长 {{ item.duration|timesToHMS }}</span></p>
            <svg-icon icon-class="delete" @click.stop.native="deleteVideo(item.id)" />
          </div>
        </div>
        <div slot="des">
          <p class="desc">{{ item.titles }}</p>
          <div class="video-status">
            <p class="date">{{ setTimes(item.create_time) }}</p>
            <p :class="setStyleClass(item.status)">{{ item.status }}</p>
          </div>
        </div>
      </base-card>
    </ul>
    <svg-icon v-else icon-class="empty-img" class="empty-icon" />
  </div>
</template>

<script>
import { GetAudioCreation, DeleteAudioCreation, DownPhonetiCreate } from '@/app/jianshi-video/api/audio-creation/index.js'
import { downloadVideo } from '@/app/jianshi-video/utils/download.js'
import setQueryParams from '@/utils/setQueryParams.js'
import VideoCard from '@/app/jianshi-video/components/VideoCard'
import { dateChangeFormat } from '../../js/time'
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
    setTimes(times) {
      return dateChangeFormat('YYYY-mm-dd HH:MM:SS', times)
    },
    routeTo(obj) {
      if (obj.status !== 2) return
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
        case '生成中':
          return 'is-building'
        case '生成失败':
          return 'build-fail'
        case '生成成功':
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
        titles__contains: this.keyword,
        source: this.source
      }
      const params = {
        page: this.page,
        limit: this.limit,
        query: setQueryParams(query)
      }
      const { err, res } = await GetAudioCreation(params)
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
      const { err, data } = await DeleteAudioCreation(params)
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
        id: obj.id
      }
      const { err, res } = await DownPhonetiCreate(params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      var a = document.createElement('a')
      a.download = (obj.title || '视频') + '.mp4'
      a.href = res.data.dl_url
      document.body.appendChild(a)
      a.click()
      document.body.removeChild(a)
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
