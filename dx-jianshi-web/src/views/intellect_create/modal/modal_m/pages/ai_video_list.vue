<!--
 * @Author: zzx
 * @Date: 2020-10-27 19:22:24
 * @LastEditTime: 2021-08-09 15:57:49
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/src/ai_video_list.vue
-->
<template>
  <div class="ai-video-list-wrap">
    <div class="d-flex search-nav">
      <ul v-if="search_list.length" class="mini-nav d-flex justify-content-start">
        <li v-for="(link, j) in search_tab" :key="j" :class="[{ active: j == search_tab_index }]" @click="searchTab(link, j)">
          <a v-if="search_list[j] ? search_list[j].length > 0 : false">{{ link.name }}</a>
        </li>
      </ul>
      <div class="text-loading" v-if="!is_done">
        <img width="50" height="35" src="@/assets/images/text_loading.svg" />
      </div>
    </div>

    <scroll class="wrapper search-list" ref="scroll" @scrollDown="scrollDown">
      <div style="min-height: 600px" v-if="!is_done"></div>
      <div class="content" v-else>
        <div class="text-center w-100 py-100px" v-if="search_list[search_tab_index] ? search_list[search_tab_index].length < 1 : true">
          <div>
            <img src="@/assets/images/is_empty.png" style="264" height="205" />
            <p class="fz-12px fc-999 mt-25px">未找到您所搜索的内容，换个词试试？</p>
          </div>
        </div>
        <el-row :gutter="20" style="margin-left: 10px">
          <!-- <keep-alive> -->
          <el-col
            class="video-box"
            v-for="(video, v) in search_list[search_tab_index]"
            :key="v"
            :span="5"
            style="padding: 0;"
            @click.native="videoEditorShow(video)"
            @mouseleave.self.native="rmVideo($event)"
            @mouseenter.native="getVideo($event, video, v)">
            <el-tooltip v-if="video_show_index !== v" :content="video.title" :visible-arrow="true" placement="bottom-end" :offset="100">
              <div class="img-wrap">
                <img :src="video.cover_pic_url" />
              </div>
            </el-tooltip>

            <videoPalyer
              v-else-if="video_show_index == v"
              :video_options="video_options"
              :start_time_s="start_time_s"
              :controls_show="false"
              :fluid="false"
              :crossOrigin="false" />

            <div class="time-box">
              <span v-if="video.time_start">{{ (video.time_start * 1000) | msToTime }} /</span>
              <span v-if="video.duration_str">{{ video.duration_str }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- </keep-alive> -->
      </div>
    </scroll>
  </div>
</template>

<script>
import { searchVideoAi } from '../js/search_video_ai.js'
import { EventBus } from '../js/eventBus'
export default {
  name: 'AiVideoList',
  props: {},
  inject: ['modalM'],
  mixins: [searchVideoAi],
  data() {
    return {
      search_page: 1,
      status_loading: false,
      // 有排序 人物 物体 帧搜索
      search_tab: [
        {
          name: '素材库',
          url: '/material-library/material-list'
        },
        {
          name: '实时资讯',
          url: '/intelligent-creation/cut-video-search'
        },
        {
          name: '人物搜索',
          url: '/intelligent-creation/video-search-person'
        },
        {
          name: '物体搜索',
          url: '/intelligent-creation/video-search-object'
        },
        {
          name: '帧搜索',
          url: '/intelligent-creation/video-search'
        }
      ],
      video_item: null,
      video_show_index: null,
      video_options: {
        width: '175',
        height: '95',
        poster: '',
        autoplay: true,
        src: ''
      }
    }
  },
  computed: {
    search_key() {
      return this.modalM['search_key']
    },
    search_list() {
      return this.modalM['search_list']
    },
    is_done() {
      if (this.modalM['is_done']) {
        // this.setTabIndex(this.search_list)
      }
      return this.modalM['is_done']
    },
    video_ops() {
      return this.modalM['videoOps']
    }
  },
  watch: {},
  methods: {
    searchTab(item, index) {
      this.search_page = 1
      this.search_tab_index = index
      this.$refs.scroll.scrollTop()
    },
    videoEditorShow(item, e) {
      // console.log(item)
      this.modalM.videoUrl = item.resource_url
      this.modalM.videoEditVisible = true
      // let duration = item.duration_ms

      this.modalM.video_item = {
        type: 'video',
        resource_url: item.resource_url,
        // duration: duration,
        uuid: item.uuid,
        start_ms: 0,
        // time_start_str: item.time_start_str,
        // end_ms: duration,
        preview_img: item.image,
        video_hd_str: item.resource_hd_str,
        video_origin: item.video_origin
      }
      // console.log(this.modalM.video_item)
    },
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
      if (this.search_tab_index != -1) {
        let index = this.search_tab_index
        let url = this.search_tab[index].url
        this.search_page++
        this.getSearchList(url, index).then(res => {
          let arr = this.search_list[index].concat(res)
          this.$set(this.search_list, index, arr)
        })
      }
    },
    setTabIndex(list) {
      for (let i = 0; i < list.length; i++) {
        let arr = list[i]
        if (arr.length > 0) {
          this.search_tab_index = i
          break
        }
      }
    }
  },
  components: {},
  created() {
    this.search_tab.forEach(() => {
      this.search_list.push([])
    })
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.ai-video-list-wrap {
  height: 0;
  flex: 1;
  display: flex;
  flex-direction: column;
}
.search-nav {
  position: relative;
  width: fit-content;
  .mini-nav {
    border: none;
    margin-bottom: 10px;

    li {
      margin: 0;

      &.active {
        a {
          border-bottom: 2px solid $c;
        }
      }

      a {
        margin-right: 10px;
        padding: 0 2px;
        padding-bottom: 10px;
        border-bottom: 2px solid transparent;
      }
    }
  }
  .text-loading {
    position: absolute;
    right: -45px;
    top: -10px;
  }
}
.search-list {
  margin: 0 -15px;
  padding-bottom: 50px;
  max-height: 600px;
  overflow: hidden;
  overflow-y: auto;

  &.img-list {
    max-height: 300px;

    .card-img {
      width: 100%;
      height: 100%;
      position: relative;
      overflow: hidden;

      .el-upload-list__item-status-label {
        position: absolute;
        right: -17px;
        top: -7px;
        width: 46px;
        height: 26px;
        line-height: 33px;
        background: #c51a1a;
        text-align: center;
        transform: rotate(45deg);
        box-shadow: 0 1px 1px #ccc;
        color: white;

        i {
          display: block;
          transform: rotate(-45deg);
          font-size: 12px;
        }
      }

      &:hover {
        .el-upload-list__item-status-label {
          display: block;
        }
      }
    }
  }

  .video-box {
    border: 2px solid transparent;
    position: relative;
    width: 180px;
    height: 100px;
    background-color: #e5e5e5;
    border-radius: 3px;
    margin: 9px 6px;

    &:hover {
      border: 2px solid $c;
    }

    &.isactive {
      border: 2px solid $c;

      .el-upload-list__item-status-label {
        display: block;
      }
    }

    #videoWrap {
      height: 100%;
      width: 100%;
    }
  }
  .img-wrap {
    width: 100%;
    height: 100%;
    text-align: center;

    img {
      height: 100%;
      object-fit: contain;
    }
  }
  .time-box {
    background-color: rgba($color: #000000, $alpha: 0.7);
    color: #fff;
    font-size: 12px;
    height: 18px;
    border-radius: 20px;
    padding: 0 5px;
    position: absolute;
    left: 10px;
    bottom: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}

.video-wrap {
  margin: 0 -26px;
}
</style>
