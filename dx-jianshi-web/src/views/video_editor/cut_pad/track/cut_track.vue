<template>
  <div class="cut-pad"
       id="video-cut-pad"
       @click.stop.prevent>
    <div class="video-clip-box">
      <cutDragBox v-for="(item, index) in cut_list"
                  :is_active="parseInt(active_data.index) == index && active_data.type == 'video'"
                  :key="index"
                  :cp_key="index"
                  :w="item.width"
                  :left="item.left"
                  :cutMaxLeft="item.cutMaxLeft"
                  :stickMaxLeft="stickMaxLeft"
                  :stickMaxRight="stickMaxRight"
                  :maxWidth="item.maxWidth"
                  :minWidth="20"
                  :parentW="second_time_line_width"
                  @bodydown="bodydown"
                  @dragging="dragging"
                  @dragstop="dragstop"
                  @deselect="deselect"
                  @stickdown="stickdown(arguments)"
                  @stickmoveleft="stickmoveleft"
                  @stickmoveright="stickmoveright">
        <div class="content">
          <div class="track-item-preview"
               v-if="item.type == 'image'"
               :style="'background-image: url(' + item.img_url + ')'"></div>
          <div class="track-item-preview"
               v-else
               :style="'background-image: url(' + item.preview_img + ')'"></div>
        </div>
      </cutDragBox>
    </div>
  </div>
</template>

<script>
import cutDragBox from '../../compents/cut_list/index'

export default {
  props: [],
  data() {
    return {
      cut_list: [],
      clip_index: 0,
      sticks: ['mr', 'ml'],
      stickMaxLeft: 0, // 基于开始时间限制 选框大小
      stickMaxRight: 0,
      video_details_temp: {} // 用来存储点击时的片段数据
    }
  },
  components: { cutDragBox },

  computed: {
    clip_list() {
      return this.$store.state.videoM.cut_track_list
    },
    per_px_ms() {
      return this.$store.state.videoM.per_px_ms
    },
    time_line_width() {
      var dom = document.querySelector('.recycle-scroller-demo')
      return dom.clientWidth
    },
    active_data() {
      return this.$store.state.videoM.active_data
    },
    second_time_line_width() {
      return this.$store.state.videoM.second_time_line_width
    }
  },

  watch: {
    clip_list: {
      deep: true,
      handler: function(newV, oldV) {
        this.setCutList()
        // this.setTotalMs()
      }
    }
  },

  methods: {
    setCutList() {
      this.cut_list = this.clip_list
    },
    // setTotalMs() {
    //   var total_ms = 0
    //   this.cut_list.forEach(item => {
    //     total_ms += item.duration
    //     console.log('setTotalMs -> total_ms', total_ms)
    //   })

    //   this.$store.commit('setTotalMs', total_ms)
    // },
    bodydown(rect) {
      const index = rect.key
      this.clip_index = index
      this.$store.commit('setActiveData', {
        type: 'video',
        index: index
      })
      this.video_details_temp = this.$actions.clone(this.cut_list[index])

      this.$store.commit('setActiveData', {
        type: 'video',
        index: index
      })
    },
    stickdown(data) {
      // console.log("之前的dom 数据", data);
      this.video_details_temp = this.$actions.clone(this.cut_list[this.clip_index])

      this.dragging(data[1])
    },
    dragging(rect) {
      const rectDate = rect
      const index = rectDate.key
      const prveDom = this.cut_list[index - 1] // 上一个元素
      const naxtDom = this.cut_list[index + 1] // 下一个元素
      const currentDom = this.cut_list[index] // 复制当前元素信息

      // console.log(rectDate);

      const leftTemp = currentDom.left // 当前元素的 left
      const widthTem = currentDom.width // 当前元素的 width
      const maxWidth = Math.ceil(currentDom.maxWidth) // 当前元素 支持的最大宽度
      const startTime = currentDom.start_time // 当前元素 开始时间

      this.stickMaxRight = maxWidth + leftTemp + 1

      currentDom.left = rect.left
      currentDom.width = rect.width
      currentDom.is_active = true
      currentDom.cutMaxLeft = 0

      // 前面有元素
      if (prveDom) {
        const left = prveDom.left || 0
        currentDom.cutMaxLeft = prveDom.width + left + 1
      }

      // 后面有元素
      // if (naxtDom) {
      //   currentDom.parentW = naxtDom.left - 1
      //   this.stickMaxRight = Math.min(maxWidth + leftTemp, naxtDom.left + 1)
      // }

      this.cut_list[index] = currentDom
    },
    stickmoveleft(rect) {
      const pre_left = this.video_details_temp.left || 0 // 原始 left
      const pre_start_time = this.video_details_temp.start_time || 0 // 原始 开始时间
      const pre_width = this.video_details_temp.width // 原始 宽度
      const pre_end_time = this.video_details_temp.end_time // 原始 结束时间

      // 可以移动的像素
      const can_be_x = pre_start_time / this.per_px_ms
      this.stickMaxLeft = 0

      // 重新赋值开始时间
      const now_left = rect.left
      const now_width = rect.width

      const new_start_time = Math.floor((now_left - pre_left) * this.per_px_ms + pre_start_time)
      this.cut_list[this.clip_index].start_time = new_start_time
    },
    stickmoveright(rect) {
      // 计算开始时间
      const pre_left = this.video_details_temp.left || 0 // 原始 left
      const pre_start_time = this.video_details_temp.start_time || 0 // 原始 开始时间
      const pre_width = this.video_details_temp.width // 原始 宽度
      const pre_end_time = this.video_details_temp.end_time // 原始 结束时间

      // 可以移动的像素
      const can_be_x = pre_start_time / this.per_px_ms
      this.stickMaxLeft = Math.max(0, Math.floor(pre_left - can_be_x + 10))

      const now_left = rect.left
      const now_width = rect.width
      // 重新赋值结束时间
      const new_end_time = pre_start_time + Math.floor(now_width * this.per_px_ms)
      this.cut_list[this.clip_index].end_time = new_end_time
    },
    dragstop(rect) {
      // console.log("鼠标抬起", rect);
      // // 计算开始时间
      // let pre_left = this.video_details_temp.left || 0 // 原始 left
      // let pre_start_time = this.video_details_temp.start_time || 0 // 原始 开始时间
      // let pre_width = this.video_details_temp.width // 原始 宽度
      // let pre_end_time = this.video_details_temp.end_time// 原始 结束时间
      // let now_left = rect.left
      // let now_width = rect.width
      // // 重新赋值开始时间
      // let new_start_time = Math.floor((now_left - pre_left) * this.per_px_ms + pre_start_time)
      // this.cut_list[this.clip_index].start_time = new_start_time
      // // // 重新赋值结束时间
      // let new_end_time = new_start_time + Math.floor(now_width * this.per_px_ms)
      // this.cut_list[this.clip_index].end_time = new_end_time
    },
    deselect() {
      console.log('点击空白处')
    }
  },

  created() {
    this.setCutList()
    // this.setTotalMs()
  },

  mounted() {}
}
</script>

<style lang="scss">
.cut-pad {
  height: 60px;
  position: relative;
  display: flex;
  .video-clip-box {
    // width: 100%;
    flex-shrink: 0;
    .vdr {
      &.active::before {
        outline: 1px dashed transparent;
      }
      .content {
        width: 100%;
        height: 100%;
        .track-item-preview {
          width: 100%;
          height: 100%;
          background-size: auto 60px;
          background-repeat: repeat-x;
        }
      }
    }
  }
}
</style>
