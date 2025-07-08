<template>
  <div :class="['cut-list-box-wrap', { is_active: is_active }]"
       ref="cutListBoxWrap"
       @mousedown.stop.prevent="bodyDown($event)"
       :id="id"
       :style="style">
    <span class="left-touch"
          @mousedown.stop.prevent="stickDown($event, 'l')"></span>

    <div class="cut-container"></div>

    <span class="right-touch"
          @mousedown.stop.prevent="stickDown($event, 'r')"></span>

    <slot></slot>
  </div>
</template>

<script>
import { getMouseXY } from '@/utils/mouse.js'
import { longStackSupport } from 'q'
export default {
  props: {
    h: {
      type: Number,
      default: 26
    },
    w: {
      type: Number,
      default: 100
    },
    left: {
      type: Number,
      default: 0
    },
    cutMaxLeft: {
      type: Number,
      default: 0
    },
    stickMaxLeft: {
      type: Number,
      default: 0
    },
    stickMaxRight: {
      type: Number,
      default: 99999
    },
    cp_key: {
      type: Number,
      default: null
    },
    parentW: {
      type: Number,
      default: 99999
    },
    maxWidth: {
      type: Number,
      default: 99999
    },
    minWidth: {
      type: Number,
      default: 10
    },
    is_active: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      id: 'cutListBoxWrap',
      x: this.left,
      width: this.w,
      bodyDrag: false,
      stickDrag: false,
      stickStartPos: {},
      active_index: this.cp_key,
      stickAxis: '' // 标记方向
    }
  },
  components: {},

  computed: {
    style() {
      return {
        left: this.x + 'px',
        width: this.width + 'px',
        height: this.h + 'px',
        zIndex: 10,
        domScrollLeft: 0
      }
    },
    Drag() {
      return this.$refs.cutListBoxWrap
    },
    rect() {
      return {
        left: Math.round(this.x),
        width: Math.round(this.width),
        key: this.active_index
      }
    }
  },

  watch: {
    left() {
      this.x = this.left
    },
    w() {
      this.width = this.w
    }
  },

  methods: {
    bodyDown(ev) {
      console.log('bodyDown')
      this.bodyDrag = true
      this.domScrollLeft = document.querySelector('.direction-horizontal').getBoundingClientRect().left - 60
      this.stickStartPos.mouseX = ev.pageX || ev.touches[0].pageX
      this.stickStartPos.x = this.left
      this.stickStartPos.w = this.width

      this.$emit('bodydown', this.rect)
    },
    move(ev) {
      if (!this.stickDrag && !this.bodyDrag) {
        return
      }

      ev.stopPropagation()
      if (this.stickDrag) {
        this.stickMove(ev)
      }
      if (this.bodyDrag) {
        this.bodyMove(ev)
      }
    },
    bodyMove(ev) {
      const stickStartPos = this.stickStartPos
      this.zIndex = 200

      const delta = {
        x: stickStartPos.mouseX - (ev.pageX || ev.touches[0].pageX)
      }
      // 获取当前 移动像素
      var left = stickStartPos.x - delta.x

      // 限制移动位置
      var max = this.parentW - stickStartPos.w

      this.x = Math.min(max, Math.max(this.cutMaxLeft, left))
      this.$emit('dragging', this.rect)
    },
    stickDown(ev, stickAxis) {
      this.stickDrag = true
      this.stickAxis = stickAxis
      this.stickStartPos.mouseX = ev.pageX || ev.touches[0].pageX
      this.stickStartPos.x = this.left
      this.stickStartPos.w = this.width
      this.$emit('stickdown', this.stickStartPos, this.rect)
      this.$emit('dragging', this.rect)
    },
    stickMove(ev) {
      const stickStartPos = this.stickStartPos
      this.zIndex = 200

      const delta = {
        x: stickStartPos.mouseX - (ev.pageX || ev.touches[0].pageX)
      }
      // 获取当前 移动像素
      if (this.stickAxis == 'r') {
        let width = Math.max(stickStartPos.w - delta.x, this.minWidth)
        const maxRight = width + stickStartPos.x

        if (maxRight >= this.stickMaxRight) {
          return
        }

        this.width = Math.min(width, this.maxWidth)
        this.$emit('stickmoveright', this.rect)
      }

      if (this.stickAxis == 'l') {
        console.log('delta.x', delta.x)
        console.log('this.stickMaxLeft.x', this.stickMaxLeft)
        console.log('this.stickStartPos.x', stickStartPos)

        let width = Math.max(stickStartPos.w + delta.x, this.minWidth)

        if (stickStartPos.x - delta.x <= this.stickMaxLeft || width <= this.minWidth) {
          return
        }

        this.width = Math.min(width, this.maxWidth)
        this.x = Math.max(this.stickMaxLeft, stickStartPos.x - delta.x)
        // 用来计算视频结束时间
        this.$emit('stickmoveleft', this.rect)
      }
      this.$emit('dragging', this.rect)
    },
    up(ev) {
      if (this.stickDrag) {
        this.stickUp(ev)
      }
      if (this.bodyDrag) {
        this.bodyUp(ev)
      }
    },
    bodyUp() {
      this.$emit('dragstop', this.rect)
      this.bodyDrag = false
      this.stickStartPos = { mouseX: 0, x: 0, w: 0 }
    },
    stickUp() {
      this.$emit('dragstop', this.rect)
      this.stickDrag = false
      this.stickStartPos = { mouseX: 0, x: 0, w: 0 }
      this.stickAxis = ''
    },
    deselect() {
      this.$emit('deselect')
    }
  },

  created() {
    this.id = this.id + this._uid
    this.bodyDrag = false
    this.stickStartPos = { mouseX: 0, x: 0, w: 0 }
  },

  mounted() {
    document.documentElement.addEventListener('mousemove', this.move)
    document.documentElement.addEventListener('mouseup', this.up)
    document.documentElement.addEventListener('mouseleave', this.up)
    document.documentElement.addEventListener('mousedown', this.deselect)
    document.documentElement.addEventListener('touchmove', this.move, true)
  },
  beforeDestroy() {}
}
</script>

<style scoped lang="scss">
.cut-list-box-wrap {
  position: absolute;
  top: 0;
  border-radius: 4px;
  overflow: hidden;
  cursor: pointer;
  box-sizing: border-box;
  // background-color: transparent;
  // border: 3px solid transparent;
  &.is_active {
    border: 3px solid $c;
    .left-touch,
    .right-touch {
      display: block;
    }
  }
  &.mini {
    border: 2px solid #4f8ae0;
    height: 20px;
    .left-touch,
    .right-touch {
      background-color: #4f8ae0;
      &::after {
        top: 7px;
      }
      &::before {
        top: 7px;
      }
    }
    .left-touch {
      left: -2px;
      &::after {
        // right: -3px;
      }
    }
    .right-touch {
      right: -2px;
      &::before {
        right: -3px;
      }
    }
  }

  .left-touch,
  .right-touch {
    width: 9px;
    cursor: col-resize;
    position: absolute;
    height: 100%;
    background-color: $c;
    -webkit-transition: all 0.3s ease;
    transition: all 0.3s ease;
    display: none;

    z-index: 12;
    &::before {
      left: 3px;
      position: absolute;
      content: ' ';
      width: 1px;
      height: 10px;
      top: 4px;
      background-color: #fff;
      display: inline-block;
    }
    &::after {
      right: 3px;
      position: absolute;
      content: ' ';
      width: 1px;
      height: 10px;
      top: 22px;
      background-color: #fff;
      display: inline-block;
    }
  }
  .left-touch {
    left: -3px;
    &::after {
      // right: -3px;
    }
  }
  .right-touch {
    right: -3px;
    &::before {
      right: -3px;
    }
  }
  .content {
    .track-item-preview {
      position: absolute;
      top: 0;
      width: 100%;
      height: 100%;
      overflow: hidden;
    }
  }

  .content {
    width: 100%;
    height: 60px;
    .track-item-preview {
      width: 100%;
      height: 100%;
      background-size: auto 60px;
      background-repeat: repeat-x;
      background-color: #4f8ae0;
    }
  }
}
</style>
