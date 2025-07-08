<!--
 * @Author: your name
 * @Date: 2021-08-11 10:24:30
 * @LastEditTime: 2021-09-15 20:28:44
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/edit-mian/DecorateTracks/decorateBox.vue
-->
<!--
 * @Author: zzx
 * @Date: 2020-08-04 15:00:28
 * @LastEditTime: 2021-08-11 09:54:30
 * @FilePath: /video_edit/src/components/draggable_box/drag_box.vue
-->
<template>
  <div class="decorate-dragegable-wrap">
    <vue-draggable-resizable
      v-for="item in decorateListTemp"
      :key="item.id"
      :w="item.w"
      :h="item.h"
      :x="item.x"
      :y="item.y"
      :active="decorateActiveId == item.id"
      :parent="true"
      :resizable="item.resizable"
      @dragging="onDrag"
      @resizing="onResize"
      @update:active="activated(arguments,item)"
      @dragstop="onDragStop"
      @resizestop="onResizeStop"
    >
      <img v-if="item.type === 'image'" :src="item.imgUrl" class="dragegable-image">
    </vue-draggable-resizable>
  </div>
</template>

<script>
import VueDraggableResizable from '../components/VueDraggableResizable/src/vue-draggable-resizable'
import { mapState } from 'vuex'

export default {
  components: {
    VueDraggableResizable
  },
  props: {
    active: {
      type: Boolean,
      default: false
    },
    text: {
      type: String,
      default: ''
    },
    deactivation: {
      type: Boolean,
      default: true
    },
    width: {
      type: Number,
      default: 100
    },
    left: {
      type: Number,
      default: 0
    },
    maxWidth: {
      type: Number,
      default: 1000
    },
    minWidth: {
      type: Number,
      default: 1
    },
    height: {
      type: Number,
      default: 30
    },
    axis: {
      type: String,
      default: 'x'
    },
    parentW: {
      type: Number,
      default: 0
    },
    handles: {
      type: Array,
      default() {
        return ['tl', 'tm', 'tr', 'mr', 'br', 'bm', 'bl', 'ml']
      }
    }
  },
  data() {
    return {
      decorateListTemp: []
    }
  },
  computed: {
    ...mapState('video', ['progressX', 'decorateList', 'decorateActiveId'])
  },
  watch: {
    // decorateListTemp: {
    //   handler() {
    //     const arr = this.decorateListTemp.filter(item => item.type === 'gaussBlur')
    //     this.$bus.$emit('drawCanvasMosaic', arr)
    //   },
    //   immediate: true
    // },
    decorateList: {
      handler() {
        this.setDecorateListTemp()
      },
      immediate: true
    },
    progressX: {
      handler() {
        this.setDecorateListTemp()
      },
      immediate: true
    }
  },
  created() {},
  mounted() {
    // addChangePosition
    this.$bus.$on('addChangePosition', () => {
      this.setDecorateListTemp()
      this.$bus.$emit('onDragStopChangePosition')
    })
  },
  methods: {
    setDecorateListTemp() {
      const left = this.progressX
      this.decorateListTemp = this.decorateList.filter(item => {
        if (left >= item.trackLeft && left <= item.trackLeft + item.trackWidth) {
          return item
        }
      })
      // this.$bus.$emit('onDragStopChangePosition')
      this.$store.commit('video/SET_DECORATE_LIST_TEMP', this.decorateListTemp)
    },
    activated(status, item) {
      const active = status[0]
      active
        ? this.$store.commit('video/SET_DECORATE_ACTIVE_ID', item.id)
        : this.$store.commit('video/SET_DECORATE_ACTIVE_ID', null)
    },
    onDragStartCallback(ev) {
      ev.stopPropagation() //  阻止事件冒泡
    },
    onDrag(x, y, event) {
      this.dragging = true
      this.x = x
      // console.log('拖拽中', x)
      // this.$emit('onDrag', { x, y })
      this.upDataDecorate(x, y)
    },
    onDragStop(x, y, width, height) {
      // console.log('拖拽结束', x, y)
      this.dragging = false
      this.$bus.$emit('onDragStopChangePosition')
      // this.$emit('onDragStop', { x })
    },
    onResize(x, y, w, h) {
      // console.log('改变大小')
      this.resizing = true
      // this.$emit('onResize', { x, y, w })
      this.upDataDecorate(x, y, w, h)
    },
    onResizeStop(x, y, w, height) {
      // console.log('改变大小结束')
      this.resizing = false
      this.x = x
      this.w = w
      // this.$emit('onResizeStop', { x, w })
      this.$bus.$emit('onDragStopChangePosition')
    },
    upDataDecorate(x, y, w, h) {
      const item = this.decorateList.find(item => item.id === this.decorateActiveId)
      const newData = Object.assign(item, {
        x: x || item.x,
        y: y || item.y,
        w: w || item.w,
        h: h || item.h
      })
      this.$store.commit('video/UPLOAD_DECORATE_LIST', newData)
    }

  }
}
</script>

<style lang="scss">
.decorate-dragegable-wrap {

  .draggable {
    background-color: transparent;
    border: 2px solid transparent;
    position: absolute;

    .dragegable-image {
      object-fit: fill;
    }

    .handle {
      box-sizing: border-box;
      position: absolute;
      width: 10px;
      height: 10px;
      background: #eee;
      border: 1px solid #333;
    }

    &.active {
      border: 2px solid #5675e8;
    }
  }
}
</style>
