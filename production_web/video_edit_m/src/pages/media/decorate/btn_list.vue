<!--
 * @Author: zzx
 * @Date: 2020-08-04 15:05:39
 * @LastEditTime: 2021-06-21 19:01:24
 * @FilePath: /video_edit/src/pages/media/decorate/decorate.vue
-->
<template>
  <div class="dragWrap" v-if="btnShow">
    <vue-draggable-resizable
      :key="index"
      v-for="(item,index) in decorate_list"
      :w="item.w"
      :h="item.h"
      :x="item.x"
      :y="item.y"
      @update:active="activated(arguments,index)"
      @dragstop="onDragStop"
      @resizestop="onResizeStop"
      :parent="true">
      <img :src="item.imgUrl">
    </vue-draggable-resizable>
  </div>
</template>

<script>
import dragBox from '@/components/draggable_box/drag_box'
import { mosaic } from '@/utils/canvas.js'
import trackData from '@/utils/setTrackData'
import guid from '@/utils/guid.js'

export default {
  name: 'decorateTrack',
  props: {},
  mixins: [trackData],
  data() {
    return {
      decorate_list: [],
      active_index: false,
      btnShow: false
    }
  },
  computed: {
    progrees_x() {
      return this.$store.state.progrees_x
    },
    cutData() {
      return this.$store.state.cut_details
    }
  },
  watch: {
    progrees_x(left) {
      if (
        left >= this.cutData.trackLeft &&
        left < this.cutData.trackLeft + this.cutData.trackWidth
      ) {
        this.btnShow = true
      } else {
        this.btnShow = false
      }
    },
    decorate_list: {
      handler() {
      //  window.parent.postMessage({ cmd: 'setTrackBtnlist', data: this.decorate_list })
      },
      deep: true
    }
  },
  methods: {
    activeBtnList(event) {
      if (event.data.cmd == 'setActiveBtnList') {
        let btnList = event.data.data
        let current_time_ms = parseInt(
          this.$store.state.videoPlay.current_time_ms
        )
        let PER_PX_MS = parseInt(this.$store.state.PER_PX_MS)

        this.decorate_list = []

        let myCanvas = document.getElementById('videoCanvas')
        let myCanvas_rect = myCanvas.getBoundingClientRect()
        let widths = myCanvas_rect.width
        let heights = myCanvas_rect.height

        // 临时计算方法
        for (let i = 0; i < btnList.length; i++) {
          const item = btnList[i]
          // const pre = btnList[i - 1]

          let x = item.x
          let y = item.y
          // if (pre) {
          //   y = this.decorate_list[i - 1].y + pre.h
          //   if (y > 200) {
          //    x = pre.w + 10
          //   }
          // }

          this.decorate_list.push({
            x: x,
            y: y,
            w: parseInt(item.w),
            h: parseInt(item.h),
            id: 'activeBtn' + guid(),
            imgUrl: item.imgUrl,
            active: false,
            type: 'image' // 装饰类型
          })
        }
      }
      window.parent.postMessage({ cmd: 'setTrackBtnlist', data: this.decorate_list })
    },
    activated(status, index) {
      this.active_index = index
    },
    onDragStartCallback(ev) {
      ev.stopPropagation() //  阻止事件冒泡
    },
    onDragStop(x, y) {
      console.log('移动结束 -> x, y', x, y)
       this.decorate_list[this.active_index].x = x
       this.decorate_list[this.active_index].y = y
      window.parent.postMessage({ cmd: 'setTrackBtnlist', data: this.decorate_list })
    },
    onResizeStop(x, y, w, h) {
      console.log('改变大小结束', x, y, w, h)
      this.decorate_list[this.active_index].x = x
      this.decorate_list[this.active_index].y = y
      this.decorate_list[this.active_index].h = h
      this.decorate_list[this.active_index].w = w
      window.parent.postMessage({ cmd: 'setTrackBtnlist', data: this.decorate_list })
    }
  },
  components: {
    dragBox
  },
  created() {},
  mounted() {
    // 设置互动视频按钮
    window.addEventListener('message', this.activeBtnList, false)
  }
}
</script>

<style lang="scss">
.dragWrap {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;

  .draggable {
    &.vdr {
      border: 3px dashed red;
    }
    &.active {
      &.vdr {
        border: 3px dashed #c51a1a;
      }
    }
    img {
      width: 100%;
      height: 100%;
    }
  }

  .drag-item {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
}
</style>
