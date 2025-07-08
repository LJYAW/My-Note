<template>
  <div class="recycle-scroller-demo"
       @click.stop.prevent="handelClick($event)">

    <!-- 时间线面板 -->
    <RecycleScroller class="scroller"
                     ref="scroller"
                     :items="list"
                     :minItemSize="item_size"
                     direction="horizontal">

      <template v-slot="props">
        <div :style="'width:'+ item_size +'px'">
          {{props.item.time |msToSecond}}
          <div class="second-time w-100 d-flex justify-content-between align-items-end">
            <span v-for="(i,index) in 5"
                  :class="{'blob':index%5==0}"
                  :key="index">
            </span>
          </div>
        </div>
      </template>

      <template #before>

        <tracks ref="tracks" />

        <!-- 进度条 -->
        <progrees ref="progrees" />

      </template>

    </RecycleScroller>
  </div>

</template>

<script>
import tracks from '../track/index'
import progrees from './progrees'

export default {
  name: 'timeLine',
  props: {
    list: {
      type: Array,
      default: () => []
    },
    item_size: {
      type: Number,
      return() {
        0
      }
    },
    second_time_line_width: {
      type: Number,
      return() {
        0
      }
    }
  },

  data() {
    return {
      timelineProgrees: null,
      moveIs: false
    }
  },

  components: { tracks, progrees },

  computed: {},

  watch: {},

  methods: {
    handelClick() {
      this.$refs.progrees.pregressTranslation()
      this.$store.commit('setTextActiveIndex', -1)
    }
  },
  mounted() {}
}
</script>

<style lang="scss">
@import './index.scss';
</style>