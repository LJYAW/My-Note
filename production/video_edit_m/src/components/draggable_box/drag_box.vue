<!--
 * @Author: zzx
 * @Date: 2020-08-04 15:00:28
 * @LastEditTime: 2020-11-03 12:01:09
 * @FilePath: /video_edit/src/components/draggable_box/drag_box.vue
-->
<template>
  <vue-draggable-resizable
    class-name="draggable-box"
    class-name-dragging="my-dragging-class"
    class-name-handle="my-handle-class"
    @dragging="onDrag"
    @dragstop="onDragStop"
    @resizing="onResize"
    @resizestop="onResizeStop"
    :onDragStart="onDragStartCallback"
    :prevent-deactivation="deactivation"
    :w="width"
    :h="height"
    :x="left"
    :axis="axis"
    :max-width="maxWidth"
    :min-width="minWidth"
    :parent="true"
    :parentW="parentW"
    :handles="handles"
  >
    <slot></slot>
  </vue-draggable-resizable>
</template>

<script>
export default {
  props: {
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
      default: 0
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
  methods: {
    onDragStartCallback(ev) {
      ev.stopPropagation() //  阻止事件冒泡
    },
    onDrag: function(x, y, event) {
      this.dragging = true
      this.x = x
      // console.log('拖拽中', x)
      this.$emit('onDrag', { x, y })
    },
    onDragStop: function(x, y, width, height) {
      // console.log('拖拽结束', x)
      this.dragging = false
      this.$emit('onDragStop', x)
    },
    onResize(x, y, w, height) {
      // console.log('改变大小')
      this.resizing = true
      this.$emit('onResize', { x, y, w })
    },
    onResizeStop(x, y, width, height) {
      // console.log('改变大小结束')
      this.resizing = false
      this.x = x
      this.w = width
      this.$emit('onResizeStop', x, width)
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style lang="scss">
</style>
