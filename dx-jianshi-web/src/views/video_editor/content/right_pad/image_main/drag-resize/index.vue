<template>
  <div class="image-track-list-wrap">
    <VueDragResize v-if="parseInt(image_active_index) >= 0 && isActive"
                   :isActive="isActive"
                   @dblclick.native="dblclick()"
                   :x="left"
                   :y="top"
                   :w="width"
                   :h="height"
                   :minw="10"
                   :minh="10"
                   :parentW="576"
                   :parentH="324"
                   :isDraggable="isDraggable"
                   @clicked="clicked"
                   @resizing="resize"
                   @dragging="dragging"
                   @deactivated="onDeactivated"
                   @changeover="changeover"
                   :parentLimitation="true">

      <!-- <img class="h-100 w-100"
           v-show="isDraggable"
           :src="img_src"> -->

    </VueDragResize>

    <div class="image-track-list"
         v-if="!isActive">
      <div v-for="(item,index) in image_track_list"
           :key="index"
           @click="getImage(item,index)">
        <img v-if="image_show_index == index && item.src"
             :style="[item.position,item.style]"
             :src="item.src">
        <div v-else
             :style="[item.position,item.style]"></div>
      </div>

    </div>

  </div>
</template>

<script>
import VueDragResize from '../../../../compents/vue-drag-resize/src/index'
import * as canvasFunction from '@/utils/canvas.js'

export default {
  props: [],
  data() {
    return {
      width: 100,
      height: 100,
      top: 150,
      left: 190,
      style: {},
      position: {},
      img_src: '',
      isActive: false,
      isDraggable: true,
      image_show_index: -1,
      canvas: null
    }
  },
  components: {
    VueDragResize
  },

  computed: {
    image_active_index() {
      return this.$store.state.videoM.image_active_index
    },
    image_track_list() {
      return this.$store.state.videoM.image_track_list
    },
    progrees_x() {
      return this.$store.state.videoM.progrees_x
    }
  },

  watch: {
    progrees_x(val) {
      this.findIndex(val)
    },
    image_show_index(index) {
      console.log(index)

      this.$emit('computeFrameStart', index)
    }
  },

  methods: {
    clicked() {
      // this.$store.commit('setVideoPaused', false)
    },
    getImage(item, index) {
      this.width = parseInt(item.style.width)
      this.height = parseInt(item.style.height)
      this.left = parseInt(item.position.left)
      this.top = parseInt(item.position.top)
      this.position = item.position
      this.style = item.style
      this.img_src = item.src
      this.isActive = true

      this.$store.commit('setImageActiveIndex', index)
      this.$store.commit('setActiveData', {
        type: 'image',
        index: index
      })
    },
    dblclick(rect) {},
    resize(rect) {
      this.setImageDetails(rect)
    },
    dragging(rect) {
      this.setImageDetails(rect)
    },
    setImageDetails(rect) {
      this.width = rect.width
      this.height = rect.height
      this.left = rect.left
      this.top = rect.top

      this.style.width = rect.width
      this.style.height = rect.height
    },
    onDeactivated() {
      console.log('点击组件外部')
      this.isDraggable = true
      this.isActive = false

      var list = this.image_track_list
      var index = this.image_active_index
      var item = this.image_track_list[index]
      item.style.width = this.width + 'px'
      item.style.height = this.height + 'px'
      item.position.left = this.left + 'px'
      item.position.top = this.top + 'px'

      list[index] = item

      this.$store.commit('setImageTrackList', list)
    },
    changeover() {
      // this.isActive = false
    },
    findIndex(left) {
      this.image_show_index = this.image_track_list.findIndex(item => {
        var itemLeft = item.left || 0
        return left >= itemLeft && left <= itemLeft + item.width
      })
    }
  },

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss">
.image-list-wrap {
  position: absolute;
  top: 0;
}
</style>
