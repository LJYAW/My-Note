<template>
  <div id="dragResize">
    <VueDragResize v-if="parseInt(text_active_index) >= 0"
                   :isActive="isActive"
                   @dblclick.native="dblclick()"
                   :x="left"
                   :y="top"
                   :w="width"
                   :h="height"
                   :parentW="576"
                   :parentH="324"
                   :isDraggable="isDraggable"
                   :sticks="sticks"
                   @resizing="resize"
                   @dragging="dragging"
                   @deactivated="onDeactivated"
                   @changeover="changeover"
                   :parentLimitation="true">
      <div class="w-100 h-100"
           v-show="isDraggable">
        <p :style="[fontStyle]">
          <font>
            <font class="font">{{ text }}</font>
          </font>
        </p>
      </div>
    </VueDragResize>

    <!-- 修改文字 -->
    <div v-show="parseInt(text_active_index) >= 0 && !isDraggable"
         :style="[fontStyle, position]"
         id="dragText">
      <p class="border_none preview-text"
         contenteditable="true">
        <font class="font">{{ text }}</font>
      </p>
    </div>

    <!-- 文本轴 -->
    <div v-if="!isActive"
         class="text-track-list-wrap">
      <div class="text-track-list"
           v-for="(item, index) in text_arr_temp"
           :key="index"
           @click="textTrackHandel(index, item)">
        <p :style="[item.style, item.position]"
           class="video-text"
           v-show="text_show_index === index">
          <font>
            <font class="font">{{ item.content }}</font>
          </font>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import VueDragResize from '../../../../compents/vue-drag-resize/src/index'

export default {
  name: 'dragResize',

  components: {
    VueDragResize
  },

  data() {
    return {
      isActive: false,
      isDraggable: true,
      sticks: ['tl', 'tm', 'tr', 'mr', 'br', 'bm', 'bl', 'ml'],
      img_src: '',
      width: 150,
      height: 30,
      top: 150,
      left: 190,
      text: '双击文本编辑',
      position: {
        position: 'absolute',
        top: 0,
        left: 0
      },
      fontStyle: {},
      text_arr_temp: [],
      textInnerHTML: null,
      dragText: null,
      text_show_index: -1
    }
  },

  computed: {
    styleObject() {
      return this.$store.state.videoM.track_text_style
    },
    text_arr() {
      return this.$store.state.videoM.track_text_list
    },
    text_active_index() {
      return this.$store.state.videoM.text_active_index
    },
    progrees_x() {
      return this.$store.state.videoM.progrees_x
    }
  },

  watch: {
    styleObject: {
      handler(val) {
        this.setFontStyle(val)
      },
      deep: true
    },
    text_arr: {
      handler(val) {
        this.text_arr_temp = this.$actions.clone(this.text_arr)
      },
      deep: true
    },
    progrees_x(val) {
      this.findTextIndex(val)
    },
    text_active_index(val) {
      if (parseInt(val) >= 0) {
      }
    }
  },

  methods: {
    textTrackHandel(index, item) {
      console.log('textTrackHandel')

      this.width = parseInt(item.style.width)
      this.height = parseInt(item.style.height)
      this.left = parseInt(item.position.left)
      this.top = parseInt(item.position.top)
      this.position = item.position
      this.fontStyle = item.style
      this.isActive = true
      this.text = item.content

      this.$store.commit('setTextActiveIndex', index)
      this.$store.commit('setActiveData', {
        type: 'text',
        index: index
      })
    },
    // 选框大小改变
    resize(rect) {
      console.log('选框大小改变')
      this.setDomDetails(rect)
    },
    // 选框位置改变
    dragging(rect) {
      console.log('选框位置改变')
      this.setDomDetails(rect)
    },
    getDomInnerText() {
      console.log('getDomInnerText')
      const pNode = Array.from(document.querySelectorAll('.preview-text'))
      var text = ''
      var height = 30
      pNode.forEach(item => {
        text += item.innerText
        height += item.offsetHeight
      })
      this.text_arr_temp[this.text_active_index].content = text
      this.text_arr_temp[this.text_active_index].style.height = height + 'px'
      this.height = height
    },
    setDomDetails(rect) {
      this.width = rect.width
      this.height = rect.height
      this.left = rect.left
      this.top = rect.top
      this.fontStyle.width = rect.width + 'px'
      this.fontStyle.height = rect.height + 'px'
    },
    changeover(rect) {
      console.log('changeover')
      this.position.top = rect.top + 'px'
      this.position.left = rect.left + 'px'

      var item = this.text_arr_temp[this.text_show_index]
      item.position = this.position

      this.$set(this.text_arr_temp, this.text_show_index, item)
      this.$store.commit('setTrackTextList', this.text_arr_temp)
    },
    setFontStyle(item) {
      var styleObject = this.$actions.clone(this.text_arr_temp[this.text_show_index])

      if (styleObject) {
        var height = styleObject.style.height
        var width = styleObject.style.width
        styleObject.style = item
        styleObject.style.width = width
        styleObject.style.height = height

        this.$set(this.text_arr_temp, this.text_show_index, styleObject)
        this.$store.commit('setTrackTextList', this.text_arr_temp)
      }
    },
    clicked() {
      console.log('单击事件')
      this.isActive = true
    },
    dblclick() {
      if (this.isDraggable) {
        console.log('双击事件')
        this.isDraggable = false
        this.sticks = ['mr', 'ml']

        var self = this
        this.$nextTick(() => {
          self.dragText = document.getElementById('dragText')
          console.log(self.dragText)

          self.dragText.oninput = function() {
            self.getDomInnerText()
          }
        })
      }
    },
    onDeactivated() {
      console.log('点击组件外部')
      this.isDraggable = true
      this.isActive = false
      this.sticks = ['tl', 'tm', 'tr', 'mr', 'br', 'bm', 'bl', 'ml']
      this.$store.commit('setTextActiveIndex', -1)
      this.$store.commit('setTrackTextList', this.text_arr_temp)

      // this.$store.commit('setTrackTextList', this.text_arr_temp)
    },

    findTextIndex(left) {
      this.text_show_index = this.text_arr.findIndex(item => {
        var itemLeft = item.left || 0
        return left >= itemLeft && left <= itemLeft + item.width
      })
    }
  },

  created() {
    this.text_arr_temp = this.$actions.clone(this.text_arr)
  },
  mounted() {}
}
</script>

<style lang="scss">
#dragResize {
  position: absolute;
  top: 0;
  left: 0;
  .vdr {
    pointer-events: auto;

    .vdr-stick {
      display: none;
    }
    &.active {
      .vdr-stick {
        display: block;
      }
      outline: 1px dashed #d6d6d6;
    }

    &.active::before {
      outline: 1px dashed transparent;
    }
  }

  p {
    letter-spacing: unset;
    outline: none;
  }
}
#dragText {
  .boder_none {
    outline: none;
  }
}
</style>
