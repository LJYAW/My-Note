<!--
 * @Author: zzx
 * @Date: 2020-08-04 15:05:39
 * @LastEditTime: 2021-06-22 11:22:56
 * @FilePath: /video_edit/src/pages/media/decorate/decorate.vue
-->
<template>
  <div class="dragWrap">
    <vue-draggable-resizable
      :key="index"
      v-for="(item,index) in decorate_list_temp"
      :w="item.w"
      :h="item.h"
      :x="item.x"
      :y="item.y"
      :active="decorate_active_id == item.id"
      @update:active="activated(arguments,item)"
      @dragstop="onDragStop"
      @resizestop="onResizeStop"
      :parent="true"
      :resizable="item.resizable"
    ></vue-draggable-resizable>
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
      resizable: true,
      decorate_list_temp: [],
      active: false
    }
  },
  computed: {
    videoPlayStatus() {
      return this.$store.state.videoPlay.video_play_status
    },
    decorate_list() {
      return this.$store.state.decorate_list
    },
    decorate_active_id() {
      return this.$store.state.decorate_active_id
    },
    progrees_x() {
      return this.$store.state.progrees_x
    }
  },
  watch: {
    decorate_list() {
      this.$emit('changeDecorateList')
      this.setDecorateListTemp()
    },
    progrees_x(left) {
      this.setDecorateListTemp()
    }
  },
  methods: {
    setDecorateListTemp() {
      let left = this.progrees_x
      this.decorate_list_temp = this.decorate_list.filter(item => {
        if (left >= item.trackLeft && left <= item.trackLeft + item.trackWidth) {
          return item
        }
      })
    },
    activated(status, item) {
      this.active = status[0]
      if (this.active) this.$store.commit('setdecorateActiveId', item.id)
    },
    onDragStartCallback(ev) {
      ev.stopPropagation() //  阻止事件冒泡
    },
    onDragStop(x, y) {
      // console.log('移动结束 -> x, y', x, y)
      this.setTrackData({ x, y }, this.decorate_active_id)
    },
    onResizeStop(x, y, w, h) {
      // console.log('改变大小结束')
      this.setTrackData({ x, y, w, h }, this.decorate_active_id)
    }
  },
  components: {
    dragBox
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss">
.dragWrap {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 324px;

  .draggable {
    &.vdr {
      border: 3px dashed red;
    }
    &.active {
      &.vdr {
        border: 3px dashed #c51a1a;
      }
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
