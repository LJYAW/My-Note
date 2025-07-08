<!--
 * @Author: zzx
 * @Date: 2020-08-04 15:00:28
 * @LastEditTime: 2021-09-09 10:19:58
 * @FilePath: /video_edit/src/components/draggable_box/drag_box.vue
-->
<template>
  <vue-draggable-resizable
    class-name="draggable-box"
    class-name-dragging="my-dragging-class"
    class-name-handle="my-handle-class"
    :on-drag-start="onDragStartCallback"
    :prevent-deactivation="deactivation"
    :w="width"
    :h="height"
    :x="left"
    :axis="axis"
    :active="active"
    :max-width="maxWidth"
    :min-width="minWidth"
    :parent="true"
    :parent-w="parentW"
    :handles="handles"
    @click.stop.prevent
    @update:active="activated(arguments)"
    @dragging="onDrag"
    @dragstop="onDragStop"
    @resizing="onResize"
    @resizestop="onResizeStop"
  >
    <slot />
  </vue-draggable-resizable>
</template>

<script>
import VueDraggableResizable from '../../components/VueDraggableResizable/src/vue-draggable-resizable'
export default {
  components: {
    VueDraggableResizable
  },
  props: {
    keyId: {
      type: String,
      default: ''
    },
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
      default: false
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
        return ['ml', 'mr']
      }
    }
  },
  data() {
    return {
      x: 0,
      w: 0,
      dragging: false,
      resizing: false
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    activated(status) {
      const active = status[0]
      console.log('🚀 ~ file: dragBox.vue ~ line 108 ~ activated ~ active', active)
      active
        ? this.$store.commit('video/SET_DECORATE_ACTIVE_ID', this.keyId)
        : this.$store.commit('video/SET_DECORATE_ACTIVE_ID', null)
    },
    onDragStartCallback(ev) {
      ev.stopPropagation() //  阻止事件冒泡
    },
    onDrag(x, y, event) {
      this.dragging = true
      this.x = x
      // console.log('拖拽中', x)
      this.$emit('onDrag', { x, y })
    },
    onDragStop(x, y, width, height) {
      // console.log('拖拽结束', x)
      this.dragging = false
      this.$emit('onDragStop', { x })
    },
    onResize(x, y, w, height) {
      // console.log('改变大小')
      this.resizing = true
      this.$emit('onResize', { x, y, w })
    },
    onResizeStop(x, y, w, height) {
      // console.log('改变大小结束')
      this.resizing = false
      this.x = x
      this.w = w
      this.$emit('onResizeStop', { x, w })
    }
  }
}
</script>

<style scoped lang="scss">
.draggable {
  // border: 2px solid #5675e8;
  background-color: #b2bfed;
  border-radius: 4px;

  &.active {
    border: 2px solid #5675e8;
  }
  // overflow: hidden;

  .my-handle-class {
    position: absolute;
    background-color: transparent;
    height: 30px;
    width: 10px;
    transition: all 300ms linear;
    top: -2px;
    background-color: #5675e8;

    &::after {
      display: block;
      content: "||";
      color: #fff;
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%,-50%);
    }
  }

  .my-handle-class-ml {
    left: -1px;
    cursor: w-resize;
    border-radius: 4px 0px 0px 4px;
  }

  .my-handle-class-mr {
    right: -1px;
    cursor: e-resize;
    border-radius: 0px 4px 4px 0px;

  }

}
</style>
