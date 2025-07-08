<!--
 * @Author: your name
 * @Date: 2021-08-30 18:13:31
 * @LastEditTime: 2021-09-18 17:00:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-masking/index.vue
-->
<template>
  <div class="container mask-wrap">
    <!-- <title-bar title="视频遮码" /> -->
    <div class="operation">
      <el-input
        v-model="searchWord"
        class="search"
        placeholder=""
        size="small"
        clearable
        suffix-icon="el-icon-search"
        @change="keyWordChange"
      />
      <el-button type="primary" size="small" @click="addProject">新建项目</el-button>
    </div>

    <div ref="list" class="card-list">
      <VideoList :video-data="videoData" @deleteItem="deleteVideo" />
    </div>

    <base-dialog
      :show.sync="chooseVisible"
      :close-on-click-modal="false"
      title="选择视频"
      width="588px"
      class="upload-dialog"
    >
      <AddProject upload-type="mask" @addProject="gotoVideoEdit" />
    </base-dialog>

  </div>
</template>

<script>
import VideoList from '../components/videoList.vue'
import AddProject from '@/components/AddProject'
import setQueryParams from '@/utils/setQueryParams'

export default {
  components: {
    VideoList,
    AddProject
  },
  props: {

  },
  data() {
    return {
      searchWord: '',
      chooseVisible: false,
      videoData: [],
      limit: 12,
      page: 1,
      pagesize: 1
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {
    this.initVideoList()
    const dom = this.$refs.list
    dom.addEventListener('scroll', this.betterScroll)
  },
  methods: {
    addProject() {
      this.chooseVisible = true
    },
    keyWordChange() {
      this.videoData = []
      this.page = 1
      this.initVideoList()
    },
    async initVideoList() {
      this.loading = true
      const query = {
        title__contains: this.searchWord
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
      this.videoData = this.videoData.concat(res.data)
      this.count = res.count
      this.pagesize = res.pagesize
    },
    // 滑动加载
    betterScroll(dom) {
      var scrollTop = dom.target.scrollTop
      // console.log('🚀 ~ file: index.vue ~ line 90 ~ constbetter_scroll=throttle ~ scrollTop', scrollTop)
      // 变量windowHeight是可视区的高度
      var windowHeight = dom.target.clientHeight || dom.target.clientHeight
      // console.log('🚀 ~ file: index.vue ~ line 93 ~ constbetter_scroll=throttle ~ windowHeight', windowHeight)
      // 变量scrollHeight是滚动条的总高度
      var scrollHeight = dom.target.scrollHeight || dom.target.scrollHeight
      // console.log('🚀 ~ file: index.vue ~ line 96 ~ constbetter_scroll=throttle ~ scrollHeight', scrollHeight)
      // 把距离顶部的距离加上可视区域的高度 等于或者大于滚动条的总高度就是到达底部
      if (scrollTop + windowHeight === scrollHeight) {
        this.pagesize > this.page && this.initVideoList(this.page++)
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
    gotoVideoEdit() {
      this.$router.push('/video-masking/video-edit-view')
    }
  }
}
</script>

<style scoped lang="scss">
.mask-wrap {
  padding-top: 30px;
  padding-bottom: 30px;

  .operation {
    display: flex;
    justify-content: space-between;
    align-items: center;
    // margin-top: 30px;

    .search {
      width: 365px;
    }

  }

  .card-list {
    margin-top: 30px;
    max-height: calc(100vh - 200px);
    overflow-y: auto;
    padding-bottom: 20px;

  }

}
</style>
