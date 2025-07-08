<template>
  <div class="image-track-wrap"
       @click.stop.prevent>
    <cutDragBox class="mini"
                v-for="(item, index) in image_track_list"
                :is_active="active_data.index == index && active_data.type =='image'"
                :key="index"
                :cp_key="index"
                :w="item.width"
                :h="30"
                :cutMaxLeft="0"
                :left="item.left"
                @bodydown="bodydown(index)"
                @dragging="dragging"
                @dragstop="dragstop"
                @deselect="deselect"
                @stickdown="stickdown"
                @stickmoveleft="stickmoveleft"
                @stickmoveright="stickmoveright">

      <div class="content">
        {{item.type_name}}
      </div>
    </cutDragBox>
  </div>
</template>

<script>
import cutDragBox from '../../compents/cut_list/index'

export default {
  props: [],
  data() {
    return {

    }
  },
  components: {
    cutDragBox
  },

  computed: {
    image_track_list() {
      return this.$store.state.videoM.image_track_list
    },
    active_data() {
      return this.$store.state.videoM.active_data
    }
  },

  watch: {},

  methods: {
    bodydown(index) {
      this.$store.commit('setImageActiveIndex', index)
      this.$store.commit('setActiveData', {
        index: index,
        type: 'image'
      })
    },
    dragging() { },
    dragstop(rect) {
      var list = this.image_track_list
      var index = this.active_data.index
      var item = this.image_track_list[index]

      item.left = rect.left
      item.width = rect.width
      list[index] = item
      this.$store.commit('setImageTrackList', list)
    },
    deselect() { },
    stickdown() { },
    stickmoveleft() { },
    stickmoveright() { }
  },

  created() { },

  mounted() { }
}
</script>

<style scoped lang="scss">
.image-track-wrap {
  height: 30px;
  background: #dbe0e2;
  margin: 15px 0;
  position: relative;
  .content {
    text-align: center;
    font-size: 12px;
    line-height: 25px;
    color: rgb(85, 82, 82);
  }
}
</style>
