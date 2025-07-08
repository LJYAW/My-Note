<!--
 * @Author: zzx
 * @Date: 2020-08-04 15:05:39
 * @LastEditTime: 2020-12-22 17:54:33
 * @FilePath: /video_edit/src/pages/edit_main/tracks/decorate-track/decorate_tracks.vue
-->
<template>
  <drag-box
    :class="{'is_avtive': data.id == decorate_active_id}"
    :width="data.trackWidth"
    :left="data.trackLeft"
    :maxWidth="parentWidth"
    @onDragStop="onDragStop"
    @onResizeStop="onResizeStop"
    ref="dragBox"
  >
    <span class="text">{{data.type}}</span>
  </drag-box>
</template>

<script>
import dragBox from '@/components/draggable_box/drag_box'
import trackData from '@/utils/setTrackData'
import trackBtn from '../track_btn/index'

export default {
  name: 'decorateTrack',
  props: {
    data: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  inject: ['track'],
  mixins: [trackData],
  data() {
    return {}
  },
  computed: {
    decorate_active_id() {
      return this.$store.state.decorate_active_id
    },
    PER_PX_MS() {
      return this.$store.state.PER_PX_MS
    },
    left() {
      return this.data.start_ms / this.$store.state.PER_PX_MS
    },
    width() {
      return (this.data.end_ms - this.data.start_ms) / this.$store.state.PER_PX_MS
    },
    parentWidth() {
      return parseInt(this.track.style.width)
    }
  },
  watch: {},
  methods: {
    onDragStop(x) {
      console.log('onDragStop -> x', x)
      this.setTrackData({ trackLeft: x }, this.data.id)
      console.log('onDragStop -> this.data.id', this.data.id)
      this.$store.commit('setdecorateActiveId', this.data.id)
    },
    onResizeStop(x, w) {
      this.setTrackData({ trackLeft: x, trackWidth: w }, this.data.id)
    }
  },
  components: {
    dragBox,
    trackBtn
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss">
.draggable {
  .track-btn-wrap {
    display: block;
    width: 30px;
    height: 10px;
    line-height: 0px;
    text-align: left;
    border: 1px solid chocolate;
    border-radius: 30px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }
}
</style>