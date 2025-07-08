<!--
 * @Author: zzx
 * @Date: 2020-08-04 15:05:39
 * @LastEditTime: 2020-11-20 12:16:20
 * @FilePath: /video_edit_cut/src/pages/edit_main/tracks/cut_track/index.vue
-->
<template>
  <div class="track">
    <drag-box
      v-if="cutData.trackWidth"
      :class="{'is_avtive': true}"
      :width="cutData.trackWidth"
      :left="cutData.trackLeft"
      :maxWidth="cutData.maxWidth || parentWidth"
      :parentW="parentWidth"
      @onDragStop="onDragStop"
      @onDrag="onDrag"
      @onResize="onResize"
      @onResizeStop="onResizeStop"
      ref="dragBox"></drag-box>
  </div>
</template>

<script>
import dragBox from '@/components/draggable_box/drag_box'
import trackData from '@/utils/setTrackData'
import trackBtn from '../track_btn/index'

export default {
  name: 'decorateTrack',
  props: {},
  inject: ['track'],
  mixins: [trackData],
  data() {
    return {}
  },
  computed: {
    PER_PX_MS() {
      return this.$store.state.PER_PX_MS
    },
    parentWidth() {
      return parseInt(this.track.style.width)
    },
    videoDuration() {
      return this.$store.state.videoPlay.duration
    },
    cutData() {
      return this.$store.state.cut_details
    }
  },
  watch: {
    cutData: {
      handler() {
        // 发送消息窗口的源（协议+主机+端口号） [也可以设置'*']指向当前的父窗口
          let start_ms = this.cutData.trackLeft * this.PER_PX_MS
          let end_ms = (this.cutData.trackLeft + this.cutData.trackWidth) * this.PER_PX_MS

          let obj = {
            start_ms: start_ms < 500 ? 0 : start_ms,
            end_ms: this.duration - end_ms < 500 ? this.duration : end_ms
          }
          window.parent.postMessage({ cmd: 'getCutData', data: this.cutData }, '*')
      },
      deep: true
      // immediate: true
    }
  },
  methods: {
    onDrag(data) {
      let start_ms = data.x * this.PER_PX_MS
      let end_ms = (data.x + this.cutData.trackWidth) * this.PER_PX_MS + start_ms

      let obj = {
        start_ms: start_ms < 500 ? 0 : start_ms,
        end_ms: this.videoDuration - end_ms < 500 ? this.videoDuration : end_ms
      }
      // window.parent.postMessage({ cmd: 'onDrag', data: obj }, '*')
    },
    onResize(data) {
      let start_ms = data.x * this.PER_PX_MS
      let end_ms = (data.x + data.w) * this.PER_PX_MS

      let obj = {
        start_ms: start_ms < 500 ? 0 : start_ms,
        end_ms: this.videoDuration - end_ms < 500 ? this.videoDuration : end_ms
      }
      window.parent.postMessage({ cmd: 'onResize', data: obj }, '*')
    },
    onDragStop(x) {
      let start_ms = x * this.PER_PX_MS
      let end_ms = (x + this.cutData.trackWidth) * this.PER_PX_MS

      let cutData = {
        startMs: start_ms < 500 ? 0 : start_ms,
        endMs: this.videoDuration - end_ms < 500 ? this.videoDuration : end_ms,
        trackLeft: x
      }
      this.$store.commit('setCutTrackDetails', cutData)
    },
    onResizeStop(x, w) {
      let start_ms = x * this.PER_PX_MS
      let end_ms = (x + w) * this.PER_PX_MS

      let cutData = {
        startMs: start_ms < 500 ? 0 : start_ms,
        endMs: this.videoDuration - end_ms < 500 ? this.videoDuration : end_ms,
        trackLeft: x,
        trackWidth: w
      }

      this.$store.commit('setCutTrackDetails', cutData)
    },
    // 设置剪辑框的默认 开始和结束时间
    setCutDatas(event) {
      if (event && event.data.cmd == 'setCutTrackDetails') {
        // 传入的数据
        let data = Object.assign({}, event.data.data)
        let cutData = {
          startMs: Math.max(data.start_ms, 0),
          endMs: Math.min(data.end_ms, this.videoDuration),
          trackLeft: Math.max(data.start_ms / this.PER_PX_MS, 0),
          trackWidth: Math.max((data.end_ms - data.start_ms) / this.PER_PX_MS, 1),
          maxWidth: data.max_ms ? Math.min(this.parentWidth, data.max_ms / this.PER_PX_MS) : ''
        }
        this.$store.commit('setCutTrackDetails', cutData)
        this.$bus.$emit('setStartTimeMs', cutData.startMs)
      }
    }
  },
  components: {
    dragBox,
    trackBtn
  },
  created() {
  },
  mounted() {
    window.addEventListener('message', this.setCutDatas, false)
  }
}
</script>

<style lang="scss">
.draggable {
  .track-btn-wrap {
    display: block;
    width: 30px;
    height: 10px;
    line-height: 0px;
    text-align: center;
    border: 1px solid chocolate;
    border-radius: 30px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }
}
</style>