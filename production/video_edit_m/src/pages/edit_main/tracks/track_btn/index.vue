<!--
 * @Author: zzx
 * @Date: 2020-09-29 12:06:12
 * @LastEditTime: 2020-09-29 16:52:05
 * @FilePath: /video_edit/src/pages/edit_main/tracks/track_btn/index.vue
-->
<template>
  <div>
    <ul class="track-btn-ul" v-if="decorate_active_id" :style="style">
      <li>
        <span class="text">删除</span>
      </li>
      <li>
        <span class="text" @click.stop="show = !show">设置秒数</span>
        <div class="set-track-ms" v-if="show">
          <input type="number" v-model="track_s" />
          <a class="text" @click.stop="setMaxMs()">max</a>
          <a class="text" @click.stop="setMs()">确认</a>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import trackData from '@/utils/setTrackData'

export default {
  props: {},
  mixins: [trackData],
  data() {
    return {
      data: {},
      position: {
        left: '-100px',
        top: '-100px'
      },
      track_s: 0,
      show: false
    }
  },
  computed: {
    style() {
      return {
        left: this.position.x - 15 + 'px',
        top: this.position.y - 100 + 'px'
      }
    },
    decorate_active_id() {
      return this.$store.state.decorate_active_id
    },
    duration() {
      // 视频总毫秒数
      return this.$store.state.videoPlay.duration
    },
    PER_PX_MS() {
      return this.$store.state.PER_PX_MS
    }
  },
  watch: {},
  methods: {
    setMs() {
      let maxWidth = this.duration / this.PER_PX_MS
      let widht = (this.track_s * 1000) / this.PER_PX_MS
      let w = Math.min(maxWidth, widht)

      this.setTrackData({ trackWidth: w }, this.data.id)
    },
    setMaxMs() {
      let maxWidth = this.duration / this.PER_PX_MS
      let w = maxWidth - this.data.trackLeft
      this.setTrackData({ trackWidth: w }, this.data.id)
    },
    submit() {}
  },
  components: {},
  created() {
    this.$bus.$on('showTrackBtn', data => {
      this.data = data.data
      this.position = data.position
    })
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.track-btn-ul {
  position: fixed;
  z-index: 1010;
  background-color: white;
  color: #000;
  font-size: 12px;
  border-radius: 3px;
  box-shadow: 0 5px 10px -5px rgba(0, 0, 0, 0.2);
  padding: 5px 0;

  li {
    text-align: center;
    cursor: pointer;
    transition: all 0.3s;

    .text {
      display: block;
      padding: 8px;
      &:hover {
        color: coral;
      }
    }
  }
  .iconfont {
    font-size: 12px;
  }
  &::before {
    pointer-events: none;
    position: absolute;
    bottom: -12px;
    left: calc(50% - 6px);
    content: '';
    border-top: 6px solid #fff;
    border-left: 6px solid transparent;
    border-right: 6px solid transparent;
    width: 0;
    height: 6px;
  }

  .set-track-ms {
    display: flex;
    align-items: center;
    margin: 0 10px;
    input {
      width: 50px;
      margin-right: 10px;
    }
  }
}
</style>
