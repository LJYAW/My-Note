<!--
 * @Author: your name
 * @Date: 2021-07-27 14:45:40
 * @LastEditTime: 2021-09-23 15:36:42
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/track/index.vue
-->
<template>
  <div class="edit-main">

    <div ref="trackContent" class="track-content" @click.stop="trackPadHandel($event)">

      <div class="canvas-scale">
        <TimeLineCanvas
          v-if="videoIsReady"
          ref="timeLine"
          :scroll-left="scrollLeft"
          :range="range"
          :duration="VIDEO_DURANTION_MS"
          @getRangeArr="getRangeArr"
          @drawEnd="timeLineDrawEnd"
        />
      </div>

      <div v-if="!timeLineLoading" class="tracks">

        <CutTrack />

        <DecorateTracks />

      </div>

      <SeekLine :scroll-left="scrollLeft" />

      <Scroller ref="scroller" :parent-width="CUT_TRACK_WIDTH" :scroll-left="scrollLeft" @onscroll="onscroll(arguments)" />

    </div>

    <div class="track-footer-wrap">
      <TrackMenu v-if="!timeLineLoading" />
    </div>

  </div>
</template>

<script>
import TimeLineCanvas from '../components/TimeLineCanvas/index.vue'
import { mapState } from 'vuex'
import CutTrack from './CutTracks/index.vue'
import SeekLine from './SeekLine/index.vue'
import { getMouseXY } from '../utils/mouse'
import TrackMenu from './TrackMenu/index'
import Scroller from '../components/Scrollthumb/index.vue'
import DecorateTracks from './DecorateTracks/index.vue'

export default {
  components: {
    TimeLineCanvas,
    CutTrack,
    SeekLine,
    Scroller,
    TrackMenu,
    DecorateTracks
  },
  props: {

  },
  data() {
    return {
      scrollLeft: 0,
      timeLineLoading: true // 等待时间轴绘制完成
    }
  },
  computed: {
    ...mapState('video', ['VIDEO_DURANTION_MS', 'PER_PX_MS', 'currentTimeMs', 'CUT_TRACK_WIDTH', 'range']),
    videoIsReady() {
      return this.VIDEO_DURANTION_MS > 0
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    trackPadHandel(e) {
      const dom = this.$refs.trackContent

      const mouseX = getMouseXY().x
      const editMainX = dom.getBoundingClientRect().x
      const scrollX = dom.scrollLeft
      const bodyX = document.body.getBoundingClientRect().x
      const progreesX = mouseX - editMainX + scrollX + bodyX + this.scrollLeft
      const currentTimeMs = Math.min(progreesX * this.PER_PX_MS, this.VIDEO_DURANTION_MS)

      this.$store.commit('video/VIDEO_PAUSE')
      this.$store.commit('video/SET_VIDEO_CURRENTTIME_MS', currentTimeMs)
      this.$store.commit('video/SEEK_CURRENT_TIME_MS', currentTimeMs)
    },

    timeLineDrawEnd() {
      this.timeLineLoading = true
      const timeLine = this.$refs.timeLine
      const PER_PX_MS = timeLine.PER_PX_MS
      this.$store.commit('video/SET_PER_PX_MS', PER_PX_MS)
      this.$store.commit('video/SET_CUT_TRACK_WIDTH', this.VIDEO_DURANTION_MS / this.PER_PX_MS)

      this.timeLineLoading = false

      this.$bus.$emit('timeLineLoadingIsReady')

      this.$nextTick(() => {
        const parent = document.querySelector('.tracks')
        parent.onscroll = (e) => {
          this.scrollLeft = e.target.scrollLeft
        }
      })
    },
    getRangeArr(arr) {
      this.$store.commit('video/SET_MAX_RANGE', arr.length - 1)
    },
    onscroll(value) {
      const clientWidth = value[1]
      const x = value[0]
      const parent = document.querySelector('.tracks')
      parent.scrollLeft = Math.floor(x * (this.CUT_TRACK_WIDTH / clientWidth))
    }
  }
}
</script>

<style scoped lang="scss">
.edit-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  width: 100%;

  .track-content {
    overflow: hidden;
    position: relative;
    flex: 1;
    width: 100%;
    margin-bottom: 20px;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;

    .tracks {
      margin-top: 12px;
      width: 100%;
      overflow: scroll;
      position: relative;
      box-sizing: border-box;
      flex: 1;
    }

    .seek-line {
      position: absolute;
      top: 20px;
    }

    .scroller-wrap {
      margin-top: auto;
    }

  }

}

</style>
