<!--
 * @Author: your name
 * @Date: 2021-08-03 14:25:12
 * @LastEditTime: 2021-10-09 19:24:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/tracks/cutTrack/index.vue
-->
<template>
  <div v-if="showDecorateTrack">
    <div v-for="item in decorateList" :key="item.id" class="cut-track" :style="style">
      <DragBox
        :key-id="item.id"
        :active="decorateActiveId === item.id"
        :parent-w="CUT_TRACK_WIDTH"
        :max-width="CUT_TRACK_WIDTH"
        :width="item.trackWidth"
        :left="item.trackLeft"
        @onDragStop="onDragStop"
        @onDrag="onDrag"
        @onResize="onResize"
        @onResizeStop="onResizeStop"
      >
        <!-- <span class="durantion">
          {{ item.endMs - item.startMs | msToSecond }}
        </span> -->
        <img v-if="item.type === 'image'" :src="item.imgUrl" alt="">
        <span v-if="item.type === 'gaussBlur' && item.trackWidth > 20" class="durantion">
          马赛克
        </span>
      </DragBox>
    </div>
  </div>

</template>

<script>
import DragBox from './dragBox.vue'
import { mapState } from 'vuex'

export default {
  name: 'DecorateTracks',
  components: {
    DragBox
  },
  props: {},
  data() {
    return {

    }
  },
  computed: {
    ...mapState('video', ['CUT_TRACK_WIDTH', 'PER_PX_MS', 'trackSetting', 'decorateList', 'VIDEO_DURANTION_MS', 'decorateActiveId']),
    style() {
      return {
        width: this.CUT_TRACK_WIDTH + 'px',
        height: 30 + 'px'
      }
    },
    showDecorateTrack() {
      return this.trackSetting.decorateTrack
    }
  },
  watch: {
    PER_PX_MS(val) {
      // const { startMs, endMs } = this.cutTrackData
      // const data = Object.assign(this.cutTrackData, {
      //   left: startMs / val,
      //   width: (endMs - startMs) / val
      // })

      // this.setCutTrackData(data)
    }
  },
  created() {
    // this.init()
  },
  mounted() {

  },
  methods: {
    onDragStop(data) {
      const { x, y, w, height } = data
    },
    onDrag(data) {
      const { x } = data
      const item = this.decorateList.find(item => item.id === this.decorateActiveId)

      const obj = Object.assign(item, {
        trackLeft: x,
        id: this.decorateActiveId
      })

      this.$store.commit('video/UPLOAD_DECORATE_LIST', obj)
    },
    onResize(data) {
      const { x, w } = data
      const item = this.decorateList.find(item => item.id === this.decorateActiveId)

      const obj = Object.assign(item, {
        trackWidth: w,
        trackLeft: x
      })

      this.$store.commit('video/UPLOAD_DECORATE_LIST', obj)
    },
    onResizeStop(data) {
      const { x, y, w, height } = data
    }
  }
}
</script>

<style scoped lang="scss">
.cut-track {
  margin-bottom: 10px;
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
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
  }
}
</style>
