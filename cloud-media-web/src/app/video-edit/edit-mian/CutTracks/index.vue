<!--
 * @Author: your name
 * @Date: 2021-08-03 14:25:12
 * @LastEditTime: 2021-09-02 11:13:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/tracks/cutTrack/index.vue
-->
<template>
  <div v-if="showCutTrack" class="cut-track" :style="style">
    <DragBox
      :parent-w="CUT_TRACK_WIDTH"
      :max-width="CUT_TRACK_WIDTH"
      :width="cutTrackData.width"
      :left="cutTrackData.left"
      @onDragStop="onDragStop"
      @onDrag="onDrag"
      @onResize="onResize"
      @onResizeStop="onResizeStop"
    >
      <span class="durantion">
        {{ cutTrackData.endMs - cutTrackData.startMs | msToSecond }}
      </span>
    </DragBox>
  </div>
</template>

<script>
import DragBox from './dragBox.vue'
import { mapState } from 'vuex'

export default {
  name: 'CutTrack',
  components: {
    DragBox
  },
  props: {},
  data() {
    return {

    }
  },
  computed: {
    ...mapState('video', ['CUT_TRACK_WIDTH', 'PER_PX_MS', 'trackSetting', 'cutTrackData', 'VIDEO_DURANTION_MS']),
    style() {
      return {
        width: this.CUT_TRACK_WIDTH + 'px',
        height: 30 + 'px'
      }
    },
    showCutTrack() {
      return this.trackSetting.cutTrack
    }
  },
  watch: {
    PER_PX_MS(val) {
      const { startMs, endMs } = this.cutTrackData
      const data = Object.assign(this.cutTrackData, {
        left: startMs / val,
        width: (endMs - startMs) / val
      })

      this.setCutTrackData(data)
    }
  },
  created() {
    this.init()
  },
  mounted() {

  },
  methods: {
    init() {
      const baseCutData = {}
      baseCutData.startMs = 0
      baseCutData.left = 0
      baseCutData.endMs = Math.min(10000, this.VIDEO_DURANTION_MS)
      baseCutData.width = (baseCutData.endMs - baseCutData.startMs) / this.PER_PX_MS

      this.setCutTrackData(baseCutData)
    },
    onDragStop(data) {
      const { x, y, w, height } = data
      // console.log('🚀 ~ file: index.vue ~ line 68 ~ onDragStop ~ x,y,w,height', x, y, w, height)
    },
    onDrag(data) {
      const { x } = data

      const startMs = x * this.PER_PX_MS
      const endMs = (x + this.cutTrackData.width) * this.PER_PX_MS

      const obj = Object.assign(this.cutTrackData, {
        left: x,
        startMs: startMs,
        endMs: endMs
      })

      this.setCutTrackData(obj)
    },
    onResize(data) {
      const { x, w } = data
      const startMs = x * this.PER_PX_MS
      const endMs = (x + w) * this.PER_PX_MS

      const obj = Object.assign(this.cutTrackData, {
        left: x,
        width: w,
        startMs: startMs,
        endMs: endMs
      })
      this.setCutTrackData(obj)
    },
    onResizeStop(data) {
      const { x, y, w, height } = data
      // console.log('🚀 ~ file: index.vue ~ line 83 ~ onResizeStop ~ x,y,w,height', x, y, w, height)
    },
    setCutTrackData(data) {
      this.$store.commit('video/SET_CUT_TRACK_DATA', data)
    }
  }
}
</script>

<style scoped lang="scss">
.cut-track {
  background-color: #f0f0f0;

  .draggable {

    .durantion {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      font-size: 12px;
      color: white;
      pointer-events: none;
    }
  }
}
</style>
