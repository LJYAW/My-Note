<!--
 * @Author: your name
 * @Date: 2021-07-26 20:40:09
 * @LastEditTime: 2021-09-22 21:11:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/index.vue
-->
<template>
  <div :class="['home-page', {'is-on-search': isOnSearch}]">
    <div class="banner">
      <Banner />

      <SearchBar />
    </div>

    <div class="container">
      <!--吸顶头部-->
      <Fheader v-if="fheaderFlag" @onChange="selectChange" @onSearchType="onSearchType" />

      <SelectBar :count="count" @onChange="selectChange" @onSearchType="onSearchType" />

      <!-- 帧搜索视频列表  -->
      <FrameList v-if="searchType === 1 && isOnSearch" v-loading="loading" :video-data="videoData" />
      <!-- 视频列表 -->
      <VideoList v-else v-loading="loading" :video-data="videoData" />

      <div v-if="page>=pagesize&&page>1" class="noMore">
        {{ page>=pagesize?' 没有更多数据了~':'加载中...' }}
      </div>
    </div>

    <el-backtop target="" :bottom="100">
      <div class="back-top">
        <svg-icon icon-class="top" class="svg" />

      </div>
    </el-backtop>
  </div>
</template>

<script>
import Banner from './components/banner.vue'
import SearchBar from './components/seach.vue'
import SelectBar from './components/select'
import VideoList from './components/videoList'
import FrameList from './components/videoList/frame.vue'
import throttle from '@/utils/debounce.js'
import Fheader from './components/fixedHeader'

import setQueryParams from '@/utils/setQueryParams'

export default {
  components: {
    Banner,
    SearchBar,
    SelectBar,
    VideoList,
    FrameList,
    Fheader
  },
  props: {

  },
  data() {
    return {
      isOnSearch: false, // 搜索状态
      fheaderFlag: false, // 吸顶状态

      searchKeyWord: '', // 搜索关键字

      selectData: {
        dir: { name: '全部目录', id: 0 }, // 目录
        vca_status: { name: '全部状态', id: 0 } // 分析状态
      },
      batchFlag: false, // 分享状态
      searchType: 1, // 搜索方式 1标体标签 2帧搜索 3人物搜索 4物体搜索

      videoData: [], // 视频列表数据
      count: 0, // 视频总数
      loading: true,
      page: 1,
      limit: 24,
      pagesize: 1
    }
  },
  computed: {

  },
  watch: {
    fheaderFlag: {
      handler: function(val) {
        this.$store.dispatch('settings/changeSetting', { showHeader: !val })
      }
    }
  },
  beforeDestroy() {
    this.$bus.$off('batchFlag')
    this.$bus.$off('srearchOnkeyword')
    document.removeEventListener('scroll', this.betterScroll)
    this.$store.commit('videoStatus/SEARCH_WORD', '')
  },
  created() {
    document.addEventListener('scroll', this.betterScroll)
  },
  mounted() {
    this.getVideoList()
    this.$bus.$on('srearchOnkeyword', (searchWord, searchType) => {
      this.isOnSearch = Boolean(searchWord)
      this.searchKeyWord = searchWord
      this.$store.commit('videoStatus/SEARCH_WORD', searchWord)
      this.batchFlag = false
      this.build()
    })
    this.$bus.$on('batchFlag', (type) => {
      this.batchFlag = type
    })
  },
  methods: {
    onSearchType(type) {
      this.searchType = type
      this.page = 1
      this.pagesize = 1
      this.videoData = []
      this.getVideoList()
    },
    // select变化
    selectChange(obj) {
      // 合并修改select的参数
      this.selectData = Object.assign(this.selectData, obj)
      this.page = 1
      this.pagesize = 1
      this.videoData = []
      this.getVideoList()
    },
    // 滑动加载
    betterScroll() {
      var scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      // console.log('🚀 ~ file: index.vue ~ line 90 ~ constbetter_scroll=throttle ~ scrollTop', scrollTop)
      // 变量windowHeight是可视区的高度
      var windowHeight = document.documentElement.clientHeight || document.body.clientHeight
      // console.log('🚀 ~ file: index.vue ~ line 93 ~ constbetter_scroll=throttle ~ windowHeight', windowHeight)
      // 变量scrollHeight是滚动条的总高度
      var scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
      // console.log('🚀 ~ file: index.vue ~ line 96 ~ constbetter_scroll=throttle ~ scrollHeight', scrollHeight)
      // 把距离顶部的距离加上可视区域的高度 等于或者大于滚动条的总高度就是到达底部
      this.fheaderFlag = scrollTop >= 380
      this.$bus.$emit('params', this)
      if (scrollTop + windowHeight === scrollHeight) {
        this.pagesize > this.page && this.getVideoList(this.page++)
      }
    },
    // 获取视频列表
    async getVideoList() {
      const query = {
        q: this.searchKeyWord,
        dir_id: this.selectData.dir.id,
        vca_status: this.selectData.vca_status.id
      }
      const params = {
        query: setQueryParams(query),
        page: this.page,
        limit: this.limit,
        search_type: this.searchType
      }

      this.loading = true
      // 有关键字走搜索 否则 视频列表
      const url = this.searchKeyWord || this.isOnSearch ? '/search/video' : '/videos'

      const { err, res } = await this.$get(url, params)
      this.loading = false
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      // 如果不是第一页 push到数组内
      this.videoData = this.videoData.concat(res.data)
      this.count = res.count
      this.pagesize = res.pagesize
    },
    async checklist(params) {
      const { err, res } = await this.$get('/search/video', params)
      if (res && res.data.length) {
        return res
      }
    },
    async build() {
      this.loading = true
      const tabs = []
      this.page = 1
      this.pagesize = 1
      this.videoData = []

      if (this.searchKeyWord) {
        const query = {
          q: this.searchKeyWord,
          dir_id: this.selectData.dir.id,
          vca_status: this.selectData.vca_status.id
        }
        const params = {
          query: setQueryParams(query),
          page: this.page,
          limit: this.limit,
          search_type: 1
        }

        const obj = await this.$get('/search/video', params)
        if (obj.res && obj.res.data.length) {
          tabs.push({ name: '综合搜索', type: 1 })
        }
        params.search_type = 3
        const obj3 = await this.$get('/search/video', params)
        if (obj3.res && obj3.res.data.length) {
          tabs.push({ name: '物体搜索', type: 3 })
        }
        params.search_type = 4
        const obj4 = await this.$get('/search/video', params)
        if (obj4.res && obj4.res.data.length) {
          tabs.push({ name: '人物搜索', type: 4 })
        }

        if (tabs.length) {
          this.searchType = tabs[0]['type']
        }
        this.tabList = tabs
      }

      this.getVideoList()
      this.$bus.$emit('params', this)
    }
  }
}
</script>

<style scoped lang="scss">

.home-page {
  position: relative;
  transition: all .5s;
  margin-top: -70px;

  .banner {
    height: 360px;

  }

  &.is-on-search {

    .banner {
      height: 210px;

    }

    .search {
      top: 110px;

      ::v-deep.tag {
        display: none;
      }
    }

    .f-header {

      ::v-deep.tab-bar {
        display: flex;
      }
    }

    .screen-bar {

      ::v-deep.tab-bar {
        display: flex;
      }
    }
  }

  .container {
    padding-bottom: 30px;

    .noMore {
      margin-top: 30px;
      font-size: 10px;
      text-align: center;
    }
  }

  .search {
    width: 100%;
    min-width: 800px;
    position: absolute;
    top: 175px;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  .back-top {
    height: 100%;
    width: 100%;
    background-color: #fff;
    border-radius: 4px;
    box-shadow: 0 0 6px rgba(0,0,0, .12);
    text-align: center;
    line-height: 40px;
    color: #5675e8;
  }

  .content_view {
    display: flex;
    flex-wrap: wrap;

    .leftpane {
      flex: 1;
    }

    .rightpane {
      width: 488px;
    }
  }
}
</style>
