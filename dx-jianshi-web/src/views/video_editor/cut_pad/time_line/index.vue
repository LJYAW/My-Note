<template>
  <div class="time-line-wrap bg-white">

    <!-- 时间线 -->
    <MyTimeLine :list="this.blocck_line_list"
                :item_size="this.block_number_left"
                :second_time_line_width="second_time_line_width" />

  </div>
</template>

<script>
import rangeChange from './range'
import { getScrollContainer } from '@/utils/dom'
import MyTimeLine from './time_line'

export default {
  name: 'timeLineWrap',
  props: {},
  data() {
    return {
      BODY_WITH_TOTAL_MS: 0,
      PER_BLOCK_TOTAL_MS: 0,
      second_time_line_width: 0,
      per_px_ms: 0,
      block_number: 0,
      block_number_left: 0,
      block_number_list: [],
      blocck_line_list: [],
      scrollLeft_scale: 0
    }
  },
  components: {
    MyTimeLine
  },

  computed: {
    body_with() {
      return 940
    },
    total_ms() {
      console.log(this.$store.state.videoM.total_ms)

      var total_ms = Math.max(this.$store.state.videoM.total_ms, 0)
      return total_ms
    }
  },

  watch: {
    total_ms(val) {
      console.log('total_ms', val)
      this.setTimeLineDetails()
    }
  },

  methods: {
    setTimeLineDetails() {
      // 一屏总毫秒数
      this.BODY_WITH_TOTAL_MS = rangeChange(3).body_width_total_ms

      // 每格子毫秒数
      this.PER_BLOCK_TOTAL_MS = rangeChange(3).base_block_times
      console.log('setTimeLineDetails -> this.PER_BLOCK_TOTAL_MS', this.PER_BLOCK_TOTAL_MS)

      // 每像素毫秒数
      this.per_px_ms = this.BODY_WITH_TOTAL_MS / this.body_with
      this.$store.commit('setPerPxMs', this.per_px_ms)

      // 一屏有多少个格
      this.block_number = Math.ceil(this.BODY_WITH_TOTAL_MS / this.PER_BLOCK_TOTAL_MS)

      // 时间轴总长度
      this.second_time_line_width = this.total_ms / this.per_px_ms
      this.$store.commit('setSecondTimeLineWidth', this.second_time_line_width)

      // 计算时间浮标的左偏移
      this.block_number_left = this.body_with / this.block_number

      this.setBlockNumberList()
    },
    handleScroll() {
      var scrollLeft = Math.abs(this.$refs.secondLine.getBoundingClientRect().left)
      this.scrollLeft_scale = Math.floor(scrollLeft / Math.floor(this.block_number_left))
    },
    // 设置初始值
    setBlockNumberList() {
      var num = Math.ceil(this.second_time_line_width / this.block_number_left)
      let ling_num = Math.max(num, 5)
      this.blocck_line_list = []

      for (let i = 0; i < ling_num; i++) {
        this.blocck_line_list.push({
          id: i,
          time: this.PER_BLOCK_TOTAL_MS * i,
          size: this.block_number_left
        })
      }
    },
    // 设置
    videoEditorShow() {
      var track_obj = this.$store.state.videoM.cut_track_list[0]
      var edtortotalms = this.$store.state.edtor_total_ms
      var video_duration = parseInt(track_obj.duration)

      edtortotalms = Math.min(edtortotalms, video_duration)

      let left = track_obj.start_time * (1000 / this.per_px_ms)

      track_obj.width = edtortotalms / this.per_px_ms
      track_obj.maxWidth = Math.ceil(edtortotalms / this.per_px_ms)
      track_obj.left = left

      var arr = [track_obj]
      this.$store.commit('setCutTrackList', arr)
    }
  },

  created() {},

  mounted() {
    this.setTimeLineDetails()
    this.videoEditorShow()
    var timeline = document.querySelector('.time-line-wrap')
  }
}
</script>

<style scoped lang="scss">
</style>