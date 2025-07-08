<template>
  <div class="text-track-wrap d-flex"
       @click.stop.prevent>
    <!-- 文字轨道 -->
    <cutDragBox v-for="(item, index) in text_arr"
                class="mini"
                :is_active="active_data.index == index && active_data.type =='text'"
                :key="index"
                :cp_key="index"
                :w="item.width"
                :h="30"
                :cutMaxLeft="0"
                :left="item.left"
                :maxWidth="time_line_width"
                :parentW="time_line_width"
                @bodydown="bodydown(index)"
                @dragging="dragging"
                @dragstop="dragstop"
                @deselect="deselect"
                @stickdown="stickdown"
                @stickmoveleft="stickmoveleft"
                @stickmoveright="stickmoveright">

      <p class="fz-12px text-center mt-7px fc-gray-54 text-ellipsis">{{item.content}}</p>

    </cutDragBox>

  </div>
</template>

<script>
import cutDragBox from '../../compents/cut_list/index'

export default {
  props: [],
  data() {
    return {}
  },
  components: {
    cutDragBox
  },

  computed: {
    text_arr() {
      return this.$store.state.videoM.track_text_list
    },
    time_line_width() {
      var dom = document.querySelector('.recycle-scroller-demo')
      return dom.clientWidth
    },
    active_data() {
      return this.$store.state.videoM.active_data
    }
  },

  watch: {},

  methods: {
    bodydown(index) {
      this.$store.commit('setActiveData', {
        index: index,
        type: 'text'
      })
    },
    dragging() { },
    dragstop(rect) {
      console.log(rect)

      const text_arr = this.text_arr
      const item = text_arr[this.active_data.index]
      console.log(rect)

      item.left = rect.left
      item.width = rect.width

      text_arr[this.active_data.index] = item

      this.$store.commit('setTrackTextList', text_arr)
    },
    deselect() { },
    stickdown() { },
    stickmoveleft() { },
    stickmoveright() { }

  },

  created() { },

  mounted() {

  }
}
</script>

<style lang="scss">
.text-track-wrap {
  height: 30px;
  background: #dbe0e2;
  margin-bottom: 15px;
  .text-clip-box {
    height: 100%;
    position: relative;
  }
}
</style>
