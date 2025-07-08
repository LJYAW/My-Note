<!--
 * @Author: zzx
 * @Date: 2020-09-24 17:32:26
 * @LastEditTime: 2021-07-07 19:17:07
 * @FilePath: /video_edit/src/pages/edit_main/track_header/index.vue
-->
<template>
  <div class="trackHeader">
    <!-- 入点标记 -->

    <ul class="btn-list" >
      <li>
        <a @click="entryPoint()">
          <i class="iconfont icon-rudian"></i>
        </a>
        <a @click="setCutWidth()">
          <i class="iconfont icon-rudian1"></i>
        </a>
        <!-- <a @click="addCutTrak()">
          <i class="iconfont icon-ziyuan"></i>
        </a> -->
      </li>

      <li class="export-btn">
        <div @click="exportVideo()">剪选</div>
      </li>
       <li class="export-btn">
        <div @click="preview()">预览</div>
      </li>
    </ul>
    <!-- <MasalikeTrack /> -->
  </div>
</template>

<script>
import setTrackData from '@/utils/setTrackData.js'
import guid from '@/utils/guid.js'

export default {
  props: {},
  data() {
    return {
      track_s: 0,
      time: {}
    }
  },
  mixins: [setTrackData],
  computed: {
    PER_PX_MS() {
      return this.$store.state.PER_PX_MS
    },
    duration() {
      // 视频总毫秒数
      return this.$store.state.videoPlay.duration
    },
    cutDetails() {
      return this.$store.state.cut_details
    }
  },
  watch: {},
  methods: {
    addCutTrak() {
      // 添加装饰轨道
      let current_time_ms = parseInt(this.$store.state.videoPlay.current_time_ms)
      let PER_PX_MS = parseInt(this.$store.state.PER_PX_MS)
      const canvas = document.getElementById('videoCanvas')

      // 添加裁剪轨道
      let list = this.$store.state.decorate_list
      let maxWidth = this.duration / this.PER_PX_MS
      if (list.some(item => item.type == '裁剪')) return

      let height = canvas.height
      let width = height * (9 / 16)
      let x = canvas.width / 2 - width / 2
      console.log('addCutTrak -> y', x)
      // list.some(item => item.type == '裁剪')
      // 添加装饰轨道 位于视频的位置信息 x轴y轴 选框的宽高 类型，轨道的left和width
      let data = {
        x: x,
        y: 0,
        w: width,
        h: height,
        id: 'decorate_' + guid(),
        active: true,
        type: '裁剪', // 装饰类型
        trackLeft: 0,
        trackWidth: maxWidth
      }

      this.addTrackData(data)
    },
    // 入点标记
    entryPoint() {
      let PROGREES_X = this.$store.state.progrees_x
      let maxLleft = this.duration / this.PER_PX_MS - this.cutDetails.trackWidth
      this.$store.commit('setCutTrackDetails', { trackLeft: Math.min(maxLleft, PROGREES_X) })
    },
    // 设置 剪辑宽度
    setCutWidth() {
        const { trackLeft, trackWidth } = this.cutDetails

        let PROGREES_X = this.$store.state.progrees_x
        const newWidth = PROGREES_X - trackLeft

        if (PROGREES_X > trackLeft + 1) {
          this.$store.commit('setCutTrackDetails', { trackWidth: newWidth })
        }
    },
    preview() {
      this.exportVideo()
      if (!this.time.start_ms && !this.time.end_ms) return
      this.$bus.$emit('setTime', this.time)
    },
    exportVideo() {
      /// 发送消息窗口的源（协议+主机+端口号） [也可以设置'*']指向当前的父窗口
      let start_ms = this.cutDetails.trackLeft * this.PER_PX_MS
      let end_ms = (this.cutDetails.trackLeft + this.cutDetails.trackWidth) * this.PER_PX_MS
      let obj = {
        start_ms: start_ms < 500 ? 0 : start_ms,
        end_ms: this.duration - end_ms < 500 ? this.duration : end_ms
      }
      this.time = obj
      window.parent.postMessage({ cmd: 'exportcutDetails', data: obj }, '*')
    }
  },
  components: {
    MasalikeTrack: () => import('./masaike_index')
  },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.trackHeader {
  height: 35px;
  padding-left: 75px;
  padding-right: 25px;
  position: relative;
  box-shadow: 0 5px 10px -5px rgba(0, 0, 0, 0.2);
  z-index: 99;
  .track-btn-ul {
    font-size: 12px;
    .text {
      margin: 0 10px;
      cursor: pointer;
    }
  }
  .btn-list {
    color: #000;
    height: 100%;
    margin: 0;
    display: flex;
    align-items: center;
    li {
      margin: 0 20px;
    }
    .iconfont {
      color: #232323;
      cursor: pointer;
    }
    .icon-rudian {
      margin-right: 20px;
    }
    .export-btn {
      width: 80px;
      height: 22px;
      line-height: 22px;
      border-radius: 100px;
      background-color: rgba(182, 47, 38, 100);
      color: rgba(255, 255, 255, 100);
      font-size: 12px;
      font-weight: 350;
      text-align: center;
      margin-left: auto;
      cursor: pointer;
      transition: all 0.3s;
      &:hover {
        background-color: mix(white, #b62f26, 20%);
      }
    }
  }
}
</style>
