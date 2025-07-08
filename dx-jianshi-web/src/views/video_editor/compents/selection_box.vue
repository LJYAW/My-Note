<template>
  <!-- 剪辑面板 -->
  <div :id="id"
       class="cut-box"
       ref="cutBox"
       :style="'width:' + cutBoxWidth +'px; transform: translateX('+cutBoxX+'px);'">
    <span class="left-touch"
          @mousedown="touchChangeSize('left')"></span>

    <div class="cut-container"
         @mousedown="translation($event)"></div>

    <span class="right-touch"
          @mousedown="touchChangeSize('right')"></span>

    <slot></slot>

  </div>

</template>

<script>
import { translation, touchChangeSize } from '@/utils/mouse.js'
import { getTranslateX } from '@/utils/dom.js'
/**
 *
 * 返回 dom 数据
 * @function domDetailsChange
 * 获取当前 组件key
 * @function getCpKey
 *
 */
export default {
  props: {
    cutBoxWidth: {
      type: Number,
      default: 0
    },
    // 选择框的 左偏移量
    cutBoxX: {
      type: Number,
      default: 0
    },
    cp_key: {
      type: Number,
      default: 0
    },
    MAXWIDTH: {
      type: Number,
      default: 100000
    }
  },
  data() {
    return {
      id: 'cut-box',
      domDetails: {}
    }
  },
  components: {},

  computed: {},

  watch: {},

  methods: {
    // 平移
    translation() {
      translation(this.id, null, this.MAXWIDTH, this.postionChanse)
      this.$emit('getCpKey', this.cp_key)
    },
    touchChangeSize(xy) {
      touchChangeSize(xy, this.id, this.MAXWIDTH, this.changeSizeCallBack)
      this.$emit('getCpKey', this.cp_key)
    },
    changeSizeCallBack() {
      this.DomDetails()
    },
    postionChanse() {
      console.log('postionChanse')
      this.DomDetails()
    },
    DomDetails() {
      var box = this.$refs.cutBox
      if (box) {
        var width = box.offsetWidth
        console.log('DomDetails -> width', width)
        var translateX = parseInt(getTranslateX(box))

        this.domDetails = {
          width: width,
          translateX: translateX
        }
        this.$emit('domDetailsChange', this.domDetails)
      }
    }
  },

  created() {
    this.id = this.id + this._uid
  },

  mounted() {}
}
</script>

<style lang="scss">
.cut-box {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  border-radius: 8px;
  .cut-container {
    width: 100%;
    height: 100%;
    cursor: pointer;
  }
  .left-touch,
  .right-touch {
    width: 3px;
    height: 100%;
    position: absolute;
    top: 0;
    background: #4f8ae0;
    cursor: col-resize;
  }
  .left-touch {
    left: 0;
  }
  .right-touch {
    right: 0;
  }
}
</style>
