<!--
 * @Author: your name
 * @Date: 2021-08-11 14:38:46
 * @LastEditTime: 2021-09-01 10:51:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/collection/index.vue
-->
<template>
  <div class="container collection-wrap">
    <SelectBar :count="count" @onChange="selectChange" />
    <VideoList v-loading="loading" :video-data="videoData" />
  </div>
</template>

<script>
import SelectBar from './components/select/index.vue'
import VideoList from './components/videoList'
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
      selectData: {
        dir_id: {}, // 目录
        vca_status: {} // 分析状态
      },
      videoData: [],
      count: 0,
      loading: true,
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
    selectChange(obj) {
      // 合并修改select的参数
      this.selectData = Object.assign(this.selectData, obj)
      this.page = 1
      this.getVideoList()
    },
    // 获取视频列表
    async getVideoList() {
      const query = {
        dir_id: this.selectData.dir_id.id,
        vca_status: this.selectData.vca_status.id
      }
      const params = {
        query: setQueryParams(query),
        page: this.page,
        limit: 20
      }

      // 整理参数发送求
      this.loading = true
      const { err, res } = await this.$get('/favorites', params)
      this.loading = false
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      // 如果不是第一页 push到数组内
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
.collection-wrap {
  padding-top: 6px;
}
</style>
