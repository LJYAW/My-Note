<!--
 * @Author: your name
 * @Date: 2021-08-12 11:34:53
 * @LastEditTime: 2021-08-19 16:19:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/my-cut-videos/index.vue
-->
<template>
  <div class="container my-cut-videos">
    <SelectBar :count="count" @search="handelSearch" />
    <VideoList v-loading="loading" :video-data="videoData" />
  </div>
</template>

<script>
import SelectBar from './SelectBar.vue'
import VideoList from './VideoList.vue'
import throttle from '@/utils/debounce.js'
import setQueryParams from '@/utils/setQueryParams'

export default {
  components: {
    SelectBar,
    VideoList
  },
  props: {

  },
  data() {
    return {
      videoData: [],
      count: 0,
      keywords: '',
      loading: false,
      page: 1,
      pagesize: 1
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.$nextTick(() => {
      const better_scroll = throttle(() => {
        var scrollTop = document.documentElement.scrollTop || document.body.scrollTop
        // 变量windowHeight是可视区的高度
        var windowHeight = document.documentElement.clientHeight || document.body.clientHeight
        // 变量scrollHeight是滚动条的总高度
        var scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
        // 把距离顶部的距离加上可视区域的高度 等于或者大于滚动条的总高度就是到达底部
        if (scrollTop + windowHeight === scrollHeight) {
          this.pagesize > this.page && this.getVideoList(this.page++)
        }
      }, 500)
      document.addEventListener('scroll', better_scroll)
    })
    this.getVideoList()
  },
  mounted() {

  },
  methods: {
    handelSearch(val) {
      this.page = 1
      this.keywords = val
      this.getVideoList()
    },
    // 获取视频列表
    async getVideoList() {
      const query = {
        video_title__contains: this.keywords
      }
      const params = {
        query: setQueryParams(query),
        page: this.page,
        limit: 20
      }

      // 整理参数发送求
      this.loading = true
      const { err, res } = await this.$get('/clips', params)
      this.loading = false
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      //   // 如果不是第一页 push到数组内
      this.page === 1
        ? this.videoData = res.data
        : this.videoData = this.videoData.concat(res.data)
      this.count = res.count
      this.pagesize = res.pagesize
    }
  }
}
</script>

<style scoped lang="scss">
</style>
