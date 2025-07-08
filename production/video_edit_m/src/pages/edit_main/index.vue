<!--
 * @Author: zzx
 * @Date: 2020-08-04 14:51:49
 * @LastEditTime: 2021-04-28 14:53:53
 * @FilePath: /video_edit_cut/src/pages/edit_main/index.vue
-->
<template>
  <div class="editMain" >
    <track-header v-if="player_is_ready"></track-header>

    <div class="track-container">
      <div class="track-main">
        <!-- <div class="track-sidebar">
          <track-sidbar></track-sidbar>
        </div>-->
        <div class="edit-pad" ref="trackMain" @mousedown.stop="editMainHanderl($event)">
          <timeProgress />

          <timeLine ref="timeLine" :range="range" :duration="duration" />

          <tracks v-if="player_loadeddata" />
        </div>
      </div>
    </div>

    <!-- <track-btn></track-btn> -->
  </div>
</template>

<script>
import timeLine from '@/components/time_line/src/index'
import tracks from './tracks/main'
import slider from '@/components/slider/main.vue'
import timeProgress from './progrees/progrees'
import { getMouseXY } from '@/utils/mouse.js'
import bus from '@/utils/bus.js'
import trackSidbar from './track_sidebar/index'
import trackHeader from './track_header/index'
// import trackBtn from './tracks/track_btn/index'
import { RecycleScroller } from 'vue-virtual-scroller'

export default {
  name: 'videoEdit',
  props: {},
  data() {
    return {}
  },
  computed: {
    PER_PX_MS() {
      // 每像素毫秒数
      return this.$store.state.PER_PX_MS
    },
    range() {
      // 当前 时间轴 毫秒数 比例
      return this.$store.state.range.range
    },
    duration() {
      // 视频总毫秒数
      return this.$store.state.videoPlay.duration
    },
    player_is_ready() {
      // 视频是否已经准备好
      return this.$store.state.videoPlay.player_is_ready
    },
    player_loadeddata() {
      // 视频是否在加载数据
      return this.$store.state.videoPlay.player_loadeddata
    }
  },
  watch: {
    range(val) {
      this.setTrackData()
    },
    player_loadeddata(status) {
      if (status) this.setTrackData()
    }
  },
  methods: {
    editMainHanderl(e) {
      // 点击 轨道 计算当前播放时间
      let parogres = this.getProgressX(e)
      let current_time_ms = parogres * this.PER_PX_MS
      this.$bus.$emit('setCurrentTimeMs', current_time_ms)
    },
    getProgressX(e) {
      // 根据当前视频播放毫秒数 计算指针位置
      let x = getMouseXY().x
      let screenX = e.screenX
      let editMainX = this.$refs.trackMain.getBoundingClientRect().x
      let scrollX = this.$refs.trackMain.scrollLeft
      let progrees = x - editMainX + scrollX
      return progrees
    },
    getRange(num) {
      this.$store.commit('setTimeLineRange', num)
    },
    setTrackData() {
      // 设置 轨道的基本数据
      // this.$nextTick(() => {
      let BLOCK_TOTAL_MS = this.$refs.timeLine.BLOCK_TOTAL_MS
      let BLOCK_WIDTH = this.$refs.timeLine.BLOCK_WIDTH
      let PER_PX_MS = BLOCK_TOTAL_MS / BLOCK_WIDTH

      this.$store.commit('setPerPxMs', PER_PX_MS)
      window.parent.postMessage({ cmd: 'setPerPxMs', data: PER_PX_MS }, '*')

      // })
    }
  },
  components: {
    timeLine,
    tracks,
    slider,
    timeProgress,
    trackSidbar,
    trackHeader,
    RecycleScroller
    // trackBtn
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss">
@import './edit_main.scss';
</style>
