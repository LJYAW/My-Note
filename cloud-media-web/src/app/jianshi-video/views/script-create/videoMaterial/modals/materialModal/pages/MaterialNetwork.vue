<!--
 * @Author: your name
 * @Date: 2021-09-09 17:05:21
 * @LastEditTime: 2021-09-16 12:09:37
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/script-create/videoMaterial/modals/materialModal/pages/MaterialNetwork.vue
-->
<template>
  <div v-loading="status_loading" class="ai-video-list-wrap">
    <scroll ref="scroll" class="wrapper search-list" @scrollDown="scrollDown">
      <div class="content">
        <div v-if="!search_list.length" class="no-content">
          <div>
            <img src="@/assets/images/createVideo/is_empty.png">
            <p>未找到您所搜索的内容，换个词试试？</p>
          </div>
        </div>
        <!-- <el-row :gutter="20" style="margin-left: 10px">
          <el-col
            v-for="(item, index) in search_list"
            :key="index"
            class="video-box"
            :span="5"
            style="padding: 0;"
            @click.native="videoEditorShow(item)"
            @mouseleave.self.native="rmVideo($event)"
            @mouseenter.native="getVideo($event, item, index)"
          >
            <el-tooltip v-if="video_show_index !== index" :content="item.title" :visible-arrow="true" placement="bottom-end" :offset="100">
              <div class="img-wrap">
                <img :src="item.cover_pic_url">
              </div>
            </el-tooltip>

            <videoPlayer
              v-else-if="video_show_index == index"
              :video-options="video_options"
              :start-time-s="start_time_s"
              :controls-show="false"
              :fluid="false"
              :cross-origin="false"
            />

            <div class="time-box">
              <span v-if="item.time_start">{{ (item.time_start * 1000) | msToTime }} /</span>
              <span v-if="item.duration_str">{{ item.duration_str }}</span>
            </div>
          </el-col>
        </el-row> -->
      </div>
      <div class="transition-wrap">
        <base-card
          v-for="item in search_list"
          :key="item.id"
          :show-mark="false"
          class="video-item"
        >
          <div slot="img">
            <VideoCard :cover-url="item.cover_url" :video-url="item.file_url" />
            <div class="btn-wrap">
              <p>{{ item.duration|timesToHMS }}</p>
            </div>
          </div>
          <div slot="des">
            <p class="desc">{{ item.title }}</p>
            <div class="tag-wrap">
              <span v-for="tag in setTags(item.tags)" :key="tag">{{ tag }}</span>
            </div>
          </div>
        </base-card>
      </div>
    </scroll>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import setQueryParams from '@/utils/setQueryParams'
import VideoCard from '@/app/jianshi-video/components/VideoCard'
export default {
  name: 'AiVideoList',
  components: {
    VideoCard
  },
  props: {},
  data() {
    return {
      search_page: 1,
      search_limit: 20,
      status_loading: false,
      search_url: 'http://tjzm.tmsx.net/v1/materials',
      video_show_index: null,
      video_options: {
        width: '175',
        height: '95',
        poster: '',
        autoplay: true,
        src: ''
      },
      search_list: []
    }
  },
  computed: {
    ...mapState('jianshi', ['searchKey'])
  },
  watch: {
    searchKey: {
      handler(val, oldVal) {
        this.searchAll(val)
      },
      immediate: true
    }
  },
  beforeDestroy() {
    this.$store.commit('jianshi/SET_SEARCH_KEY', '')
  },
  created() {},
  mounted() {},
  methods: {
    searchTab() {
      this.search_page = 1
      this.$refs.scroll.scrollTop()
    },
    // 显示剪辑页面
    videoEditorShow(item, e) {
      console.log(item)
      // const video_item = {
      //   type: 'video',
      //   resource_url: item.resource_url,
      //   uuid: item.uuid,
      //   start_ms: 0,
      //   preview_img: item.image,
      //   video_hd_str: item.resource_hd_str,
      //   video_origin: item.video_origin
      // }
      // this.$emit('setVideoItem', video_item)
    },
    // 显示视频
    getVideo(e, item, index) {
      this.time = setTimeout(() => {
        this.video_show = true
        this.video_show_index = index

        this.video_options.poster = item.cover_pic_url
        this.video_options.src = item.resource_url

        this.start_time_s = item.time_start || 0
        this.onelyUUID = item.uuid
      }, 2000)
    },
    // 显示图片
    rmVideo(e) {
      this.onelyUUID = ''
      this.video_options.src = ''
      this.start_time_s = 0
      this.video_show = false
      this.video_show_index = null
      clearTimeout(this.time)
      this.time = null
    },
    // 滚动加载
    scrollDown() {
      // const url = this.search_tab.url
      this.search_page++
      this.getSearchList(this.search_url).then(res => {
        this.search_list = this.search_list.concat(res)
      })
    },
    async searchAll() {
      this.search_page = 1
      this.getSearchList(this.search_url)
    },
    async getSearchList(url) {
      this.status_loading = true
      var query = {
        title: this.searchKey,
        source: '网络素材'
      }
      var params = {
        page: this.search_page,
        limit: this.search_limit,
        query: setQueryParams(query)
      }
      const { err, res } = await this.$get(url, params)
      this.status_loading = false
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.search_list = res.data
    },
    setTags(tag) {
      return tag.split(',')
    }
  }
}
</script>

<style scoped lang="scss">
.ai-video-list-wrap {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.search-list {
  // margin: 0 -15px;
  padding-bottom: 50px;
  // height: 600px;
  max-height: 600px;
  // overflow: hidden;
  overflow-y: auto;

  .content {

    .no-content {
      padding: 100px 0;
      text-align: center;

      img {
        height: 205px;
        margin-bottom: 25px;
      }
    }

  }

  .transition-wrap {
    display: grid;
    grid-template-columns: repeat(4, 25%);
    grid-row-gap: 30px;
    margin: 0 -12px;

    .video-item {
      margin: 0 12px;

      ::v-deep .image {
        border-radius: 4px;
        height: auto;
        position: relative;
        background: none;

        .btn-wrap {
          position: absolute;
          bottom: 13px;
          right: 10px;
          z-index: 9;
          color: #fff;
          font-size: 12px;
          line-height: 12px;
        }

      }

      .desc {
        font-size: 14px;
        font-weight: 600;
        color: #404040;
        line-height: 18px;
        margin-bottom: 10px;
      }

      .tag-wrap {
        display: flex;
        flex-wrap: wrap;
        height: 30px;
        overflow: hidden;

        span {
          background: #efefef;
          border-radius: 4px;
          opacity: .8;
          font-size: 12px;
          line-height: 12px;
          color: #404040;
          padding: 6px 8px;
          margin-right: 10px;
          margin-bottom: 10px;
        }

      }
    }
  }
}
</style>
