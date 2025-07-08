<!--
 * @Author: zzx
 * @Date: 2020-09-24 17:32:26
 * @LastEditTime: 2021-06-21 18:21:35
 * @FilePath: /video_edit/src/pages/edit_main/track_header/masaike_index.vue
-->
<template>
  <div class="trackHeader">
    <ul class="btn-list">
      <li>
        <a @click="addCutTrak()">
          <i class="iconfont icon-ziyuan"></i>
        </a>
      </li>
      <!-- <li>
        <a @click="addCutTrakOfMobile()">
          <i class="iconfont icon-ziyuan"></i>
        </a>
      </li> -->
      <li>
        <a @click="addDecorate()">
          <i class="iconfont icon-masaike1"></i>
        </a>
      </li>
      <li>
        <a @click="deleteDecorate()">
          <i class="iconfont icon-shanchu"></i>
        </a>
      </li>
      <li class="track-btn-ul">
        <span class="text" @click.stop>设置秒数</span>
        <input type="number" v-model="track_s" min="0" />
        <a class="text" @click.stop="setMaxMs()">max</a>
        <a class="text" @click.stop="setMs()">确认</a>
      </li>
      <li class="export-btn">
        <div @click="exportVideo()">导出视频</div>
      </li>
    </ul>
  </div>
</template>

<script>
import setTrackData from '@/utils/setTrackData.js'
import guid from '@/utils/guid.js'

export default {
  props: {},
  data() {
    return {
      track_s: 0
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
    decorate_active_id() {
      return this.$store.state.decorate_active_id
    },
    active_track() {
      let list = this.$store.state.decorate_list
      return list.find(item => item.id == this.decorate_active_id)
    },
    cutDetails() {
      return this.$store.state.cut_details
    }
  },
  watch: {},
  methods: {
    // 设置9/16 裁剪框
    addCutTrakOfMobile() {
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
        resizable: false, // 是否可以 改变选框大小
        trackWidth: maxWidth
      }

      this.addTrackData(data)
    },
    // 计算马赛克位置
    activeBtnList(event) {
      if (event.data.cmd == 'setMosaicList') {
        let list = event.data.data

        let canvas = document.getElementById('videoCanvas')
        let canvasWidth = canvas.width
        let canvasHeight = canvas.height

        let player = document.getElementById('myVideo')
        let videoWidth = player.videoWidth
        let videoHeight = player.videoHeight

        list.forEach(item => {
          let top = (item.y / videoHeight) * canvasHeight
          let left = (item.x / videoWidth) * canvasWidth
          let width = (item.width / videoWidth) * canvasWidth
          let height = (item.height / videoHeight) * canvasHeight

          let obj = {
            active: false,
            w: width,
            x: left,
            y: top,
            h: height,
            id: 'decorate_' + guid(),
            trackLeft: item.start_ms / this.PER_PX_MS,
            trackWidth: (item.end_ms - item.start_ms) / this.PER_PX_MS,
            type: item.type || '马赛克'
          }
          this.addTrackData(obj)
        })
      }
    },
    setMs() {
      if (!this.active_track) return
      let maxWidth = this.duration / this.PER_PX_MS
      let widht = (this.track_s * 1000) / this.PER_PX_MS
      let w = Math.min(maxWidth, widht)
      this.setTrackData({ trackWidth: w }, this.decorate_active_id)
    },
    setMaxMs() {
      if (!this.active_track) return
      let maxWidth = this.duration / this.PER_PX_MS
      let w = maxWidth - this.active_track.trackLeft
      this.setTrackData({ trackWidth: w }, this.decorate_active_id)
    },
    addDecorate() {
      // 添加装饰轨道
      let current_time_ms = parseInt(this.$store.state.videoPlay.current_time_ms)
      let PER_PX_MS = parseInt(this.$store.state.PER_PX_MS)

      // 添加装饰轨道 位于视频的位置信息 x轴y轴 选框的宽高 类型，轨道的left和width
      let data = {
        x: 10,
        y: 10,
        w: 100,
        h: 50,
        id: 'decorate_' + guid(),
        active: false,
        type: '马赛克', // 装饰类型
        trackLeft: (current_time_ms - PER_PX_MS) / PER_PX_MS,
        trackWidth: 100
      }

      this.addTrackData(data)
    },
    addCutTrak() {
      // 添加裁剪轨道
      let list = this.$store.state.decorate_list
      let maxWidth = this.duration / this.PER_PX_MS
      if (list.some(item => item.type == '裁剪')) return
      // list.some(item => item.type == '裁剪')
      // 添加装饰轨道 位于视频的位置信息 x轴y轴 选框的宽高 类型，轨道的left和width
      let data = {
        x: 10,
        y: 10,
        w: 400,
        h: 300,
        id: 'decorate_' + guid(),
        active: true,
        type: '裁剪', // 装饰类型
        trackLeft: 0,
        trackWidth: maxWidth
      }

      this.addTrackData(data)
    },
    exportVideo() {
      /// 发送消息窗口的源（协议+主机+端口号） [也可以设置'*']指向当前的父窗口
      let list = this.$store.state.decorate_list
      let exportArr = []
      // 计算开始时间结束时间
      let PER_PX_MS = this.$store.state.PER_PX_MS
      let player = document.getElementById('myVideo')
      let videoWidth = player.videoWidth
      let videoHeight = player.videoHeight

      let canvas = document.getElementById('videoCanvas')
      let canvasWidth = canvas.width
      let canvasHeight = canvas.height

      list.forEach(item => {
        let top = (item.y / canvasHeight) * videoHeight
        let left = (item.x / canvasWidth) * videoWidth
        let width = (item.w / canvasWidth) * videoWidth
        let height = (item.h / canvasHeight) * videoHeight
        let start_ms = item.trackLeft * PER_PX_MS

        let obj = {
          start_ms: start_ms < 500 ? 0 : start_ms,
          end_ms: (item.trackLeft + item.trackWidth) * PER_PX_MS,
          y: top,
          x: left,
          width: width,
          height: height,
          type: item.type
        }
        exportArr.push(obj)
      })

      window.parent.postMessage({ cmd: 'exportArr', data: exportArr }, '*')
      let cutDetails = this.exportcutDetails()

      let videoSize = {
        width: videoWidth,
        height: videoHeight
      }

      // 传输 cut 数据以及 装饰数据
      window.parent.postMessage({
        cmd: 'exportAllData',
        data: {
          cutDetails: cutDetails,
          decorateList: exportArr,
          videoSize: videoSize
        }
      }, '*')
    },
    exportcutDetails() {
      /// 发送消息窗口的源（协议+主机+端口号） [也可以设置'*']指向当前的父窗口
      let start_ms = this.cutDetails.trackLeft * this.PER_PX_MS
      let end_ms = (this.cutDetails.trackLeft + this.cutDetails.trackWidth) * this.PER_PX_MS

      let obj = {
        start_ms: start_ms < 500 ? 0 : start_ms,
        end_ms: this.duration - end_ms < 500 ? this.duration : end_ms
      }
      return obj
    },
    deleteDecorate() {
      this.deleteTrackData(this.$store.state.decorate_active_id)
    }
  },
  components: {},
  created() {},
  mounted() {
    // 设置互动视频按钮
    window.addEventListener('message', this.activeBtnList, false)
  }
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
