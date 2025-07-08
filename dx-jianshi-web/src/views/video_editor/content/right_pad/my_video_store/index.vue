<template>
  <div class="my-video-store d-flex">
    <ul class="my-video-list-nav">
      <li v-for="(item, index) in my_video_store_nav"
          :key="index"
          :class="{ active: store_index == index }">
        <a @click="checkoutStore(index)">
          {{ item }}
        </a>
      </li>
    </ul>

    <ul class="my-state-video-list flex-1">
      <li v-for="(item, index) in video_list"
          :class="['item', { 'is-hover': video_show_index == index }]"
          @mouseenter="focus(item, index)"
          @mouseleave="blur()"
          @mousedown.stop.prevent="drag(item)"
          :key="index">
        <video v-if="video_show_index == index && !video_show_loading"
               autoplay
               muted
               width="100%"
               height="100%"
               :src="item.video_url"></video>

        <div v-else
             class="w-100 h-100">
          <img v-if="item.image"
               :src="item.image" />

          <img v-else-if="item.photo"
               :src="item.photo" />
        </div>

        <i class="iconfont iconsolid_add"
           @click.stop.prevent="addVideo(item)"></i>
      </li>
      <li style="width: 100%"
          class="d-flex justify-content-center">
        <a class="fz-12px fc-whiten mt-5px hove-c"
           @click="getMoreData()">加载更多</a>
        <loading v-if="status_loading" />
      </li>
    </ul>

    <div id="drag-dom"></div>

  </div>
</template>

<script>
import { dragEvent } from '../../../drag.js'
import { getMouseXY } from '@/utils/mouse.js'

export default {
  props: [],
  data() {
    return {
      my_video_store_nav: ['购买的视频'],
      store_index: 0,
      interface_name: '',
      video_list: [],
      cut_track_list: [],
      video_show_loading: true,
      video_show_index: -1,
      video_show_details: {},
      page: 1,
      limit: 9,
      status_loading: false,
      dragItem: {}
    }
  },
  components: {},

  computed: {
    progrees_x() {
      return this.$store.state.videoM.progrees_x
    },
    per_px_ms() {
      return this.$store.state.videoM.per_px_ms
    },
    track_list() {
      return this.$store.state.videoM.cut_track_list
    }
  },

  watch: {},

  methods: {
    checkoutStore(index) {
      if (index == 0) {
        this.getMyPurchasedVideo()
      }
      // this.getVideoList(index)
    },
    getMyPurchasedVideo() {
      var params = {
        status: 1,
        item_id: '',
        platform_id: '',
        keywords: '',
        orderby: 'time',
        orderby_type: 'desc',
        page: this.page,
        limit: this.limit,
        type: 1
      }
      this.$get('/short-video-resource/search', { params: params }).then(res => {
        if (res.data.code == '0000') {
          this.video_list = this.video_list.concat(res.data.data.list)
        } else {
          this.$emit('alertMsg', res.data.msg)
        }
      })
    },
    getMoreData() {
      this.page++
      this.getMyPurchasedVideo()
    },
    focus(item, index) {
      if (!this.video_show_loading) {
        return
      }

      this.video_show_index = index
      this.video_show_url = item.video_url
      this.video_show_loading = false
    },
    blur() {
      this.video_show_index = -1
      this.video_show_loading = true
      this.video_show_details = {}
    },
    addVideo(item, mouseX) {
      // video 所需数据

      console.log(item)
      var duration = item.duration_ms
      const list = this.track_list

      console.log(mouseX)

      // var left = this.setTrackLeft(item, list)

      const left = this.setClipDetails(item, list, mouseX).left
      const width = this.setClipDetails(item, list, mouseX).width
      const pre_index = this.setClipDetails(item, list, mouseX).index
      console.log(this.setClipDetails(item, list, mouseX))

      const video_item = {
        type: 'video',
        video_url: item.video_url,
        duration: duration,
        uuid: item.uuid,
        start_time: 0,
        end_time: duration,
        preview_img: item.photo,
        width: width,
        maxWidth: Math.ceil(duration / this.per_px_ms),
        left: left
      }

      list.splice(pre_index + 1, 0, video_item)

      console.log(list)

      // list.push(video_item)
      this.$store.commit('setCutTrackList', list)
    },
    setTrackLeft(item, list) {
      let progrees_x = this.progrees_x

      if (list.length > 0) {
        const index = list.findIndex(video => {
          var left = video.left || 0
          return progrees_x >= left && progrees_x <= left + video.width
        })

        if (index > -1) {
          var preDom = list[index]
          var nextDom = list[index + 1]
          var lastDom = list[list.length - 1]

          if (!nextDom) {
            progrees_x = preDom.left + preDom.width + 1
          } else {
            progrees_x = lastDom.left + lastDom.width + 1
          }
        }
      }
      return progrees_x
    },
    setClipDetails(item, list, mouseX) {
      const duration = item.duration_ms
      let width = duration / this.per_px_ms
      let pre_index = -1
      let progrees_x = this.progrees_x
      //  说明是拖拽的元素
      if (mouseX) {
        progrees_x = mouseX
      }

      if (list.length > 0) {
        // 有没有在 某个元素上面
        const index = list.findIndex(video => {
          var video_left = video.left || 0
          return progrees_x >= video_left && progrees_x <= video_left + video.width
        })

        console.log(`在第 ${index} 元素上`)

        var clip_arr = []
        for (let i = 0; i < list.length; i++) {
          var left = list[i].left || 0
          const video_left = left
          const video_right = left + list[i].width
          clip_arr.push([video_left, video_right])
        }

        console.log('所有元素的 时间数组', clip_arr)

        // 如果 时间线 在DOM 上
        if (index > -1) {
          const end_left = clip_arr[index][1] // 当前元素的结束位置
          const next_start_left = clip_arr[index + 1] // 下一个元素的开始位置
          pre_index = index

          console.log('next_start_left', next_start_left)
          console.log('end_left', end_left)

          progrees_x = end_left + 1

          // 如果下一个有元素的话
          if (next_start_left) {
            // 中间的间隙
            var block_width = next_start_left[0] - end_left
            console.log('block_width', block_width)

            if (block_width > 2) {
              width = Math.min(width, block_width) - 2
              console.log('width', width)
              console.log('progrees_x', progrees_x)

              pre_index = index
            } else {
              pre_index = list.length - 1
              console.log('pre_index', pre_index)
              progrees_x = list[pre_index].left + list[pre_index].width + 1
              console.log('progrees_x', progrees_x)
            }
          }
        } else {
          for (let i = 0; i < clip_arr.length; i++) {
            const number = clip_arr[i]
            if (progrees_x > number[1]) {
              pre_index = i
            }
          }

          console.log(pre_index)

          const next_start_left = clip_arr[pre_index + 1]
          console.log('next_start_left', next_start_left)

          if (next_start_left) {
            var max_width = next_start_left[0] - progrees_x
            width = Math.min(width, max_width) - 2
          }
        }
      }

      return {
        left: progrees_x,
        width: width,
        index: pre_index
      }
    },
    setStartTime() {
      var start_time = 0

      if (this.cut_track_list.length == 0) {
        start_time = this.$store.state.videoM.progrees_x * this.$store.state.videoM.per_px_ms
      } else {
        start_time = this.cut_track_list[this.cut_track_list.length - 1].end_time
      }
      return start_time
    },
    getVideoList(index) {
      this.$get(this.interface_name, { params: this.interface_params }).then(res => {
        if (res.data.code == '0000') {
          var list = []
          switch (index) {
            case 0:
              list = res.data.data.items
              break
            case 1:
              list = res.data.data.list
              break
            case 2:
              list = res.data.data.list
              break
            case 3:
              list = res.data.data.items
              break
          }
          this.video_list = list
        }
      })
    },
    drag(item) {
      const duration = item.duration_ms
      const progrees_x = this.progrees_x
      const width = duration / this.per_px_ms

      this.dragItem = item

      const ele = document.getElementById('drag-dom')
      ele.style.width = width + 'px'
      ele.style.height = 54 + 'px'
      ele.style.backgroundColor = '#ccc'
      ele.style.backgroundImage = `url(${item.photo})`
      ele.style.zIndex = 100

      dragEvent('drag-dom', 'video-cut-pad', this.previewDom, this.callBack)
    },
    previewDom() {
      console.log('previewDom')
      // this.addVideo(this.dragItem)
    },
    callBack() {
      const scrollerDom = document.querySelector('.vue-recycle-scroller')
      var x = scrollerDom.getBoundingClientRect().x - 60 - document.body.getBoundingClientRect().x
      const mouse = getMouseXY().x - x - 60

      this.addVideo(this.dragItem, mouse)
      this.dragItem = {}
    }
  },

  created() {
    this.checkoutStore(0)
  },

  mounted() {}
}
</script>

<style scoped lang="scss">
.my-video-store {
  height: 100%;
  // overflow: hidden;
  // overflow-y: auto;
  .my-video-list-nav {
    height: 100%;
    background: #232323;

    li {
      // height: 32px;
      padding: 12px 15px;
      border-left: 5px solid transparent;
      transition: all 0.3s;
      display: flex;
      a {
        color: #999999;
        font-size: 12px;
      }
      &.active {
        background: #313131;
        border-left: 5px solid $c;
        a {
          color: #fff;
        }
      }
    }
  }
  .my-state-video-list {
    padding: 10px;
    display: flex;
    flex-wrap: wrap;
    align-content: baseline;
    overflow: hidden;
    overflow-y: auto;
    height: 316px;
    .item {
      position: relative;
      width: 130px;
      height: 90px;
      background: black;
      border: 2px solid #232323;
      border-radius: 2px;
      margin: 5px 10px;

      .iconfont {
        color: white;
        font-weight: 400;
        font-size: 16px;
        position: absolute;
        width: 20px;
        height: 20px;
        line-height: 20px;
        cursor: pointer;
        top: 3px;
        right: 0.1875rem;
        opacity: 0;
        transition: all 0.3s;
      }
      &.is-hover {
        &:hover {
          border: 2px solid $c;
          .iconfont {
            opacity: 1;
          }
        }
      }

      img {
        width: 100%;
        height: 100%;
        display: block;
        object-fit: contain;
      }
    }
  }
  #drag-dom {
    background: #232323;
    border-radius: 2px;
    background-size: auto 60px;
    background-repeat: repeat-x;
    background-color: #4f8ae0;
    display: none;
  }
}
</style>
