<!--
 * @Author: your name
 * @Date: 2021-05-10 17:17:09
 * @LastEditTime: 2021-10-12 17:35:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/components/UpdateSample.vue
-->
<template>
  <base-dialog :show="show" title="编辑样本" width="1100px" height="200px" :close-on-click-modal="false" @close="close">
    <div class="updateSampleModel">
      <div class="sample-video">
        <iframe id="videoEditiframe" ref="iframe" class="iframe" src="/video-edit/index.html" frameborder="0" @load="inIframe" />
        <!-- <video-player
          ref="videoPlayer"
          :options="playerOptions"
          :playsinline="true"
        /> -->
      </div>

      <div class="sample-form">
        <slot />
      </div>
    </div>
  </base-dialog>
</template>

<script>
import { videoToImg } from '@/utils/canvas.js'
export default {
  components: {
  },
  props: {
    show: {
      type: Boolean,
      default() {
        return false
      }
    },
    videoUrl: {
      type: String,
      default() {
        return ''
      }
    }
  },
  data() {
    return {
      iframe: null,
      // cutData: {
      //   start_ms: 0, // 开始时间 必传
      //   end_ms: 5000, // 结束时间 必传
      //   max_ms: 5000
      // },
      start_ms: '',
      end_ms: '',
      playerOptions: {
        height: '360',
        autoplay: true,
        muted: false,
        language: 'en',
        fluid: true,
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [
          {
            type: 'video/mp4',
            // mp4
            src: ''
          }
        ],
        src: '',
        poster: ''
      }
    }
  },
  computed: {

  },
  watch: {

  },
  beforeDestroy() {
    this.$bus.$off('setCut')
    this.$bus.$off('cutVideoImage')
    this.$bus.$off('setImageData')
    window.removeEventListener('message', this.iframeMsg, false)
  },
  created() {
    this.playerOptions.sources[0].src = this.videoUrl
    this.playerOptions.src = this.videoUrl

    // this.playerOptions.src = 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4'
    // this.playerOptions.sources[0].src = 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4'
  },
  mounted() {
    this.$bus.$on('setCut', s => {
      // this.cutData = s
      this.iframe.postMessage({ cmd: 'setCutTrackDetails', data: s }, '*')
    })
    // 监听截图事件
    this.$bus.$on('cutVideoImage', () => {
      // this.cutData = s
      this.iframe.postMessage({ cmd: 'getVideoEditAllData' }, '*')
    })
  },
  methods: {
    inIframe() {
      const iframe = this.$refs.iframe.contentWindow
      iframe.focus()
      this.iframe = this.$refs.iframe.contentWindow
      iframe.postMessage({ cmd: 'setVideoOptions', data: this.playerOptions }, '*')

      // hls 设置 视频播放时间
      iframe.postMessage({ cmd: 'setVideoDurantion', data: 6000 }, '*')

      // 获取 组件内所有数据 store
      // this.$nextTick(function() {
      //   iframe.postMessage({ cmd: 'getVideoEditAllData' }, '*')
      // })

      window.addEventListener('message', this.iframeMsg, false)
    },
    iframeMsg(event) {
      const cmd = event.data.cmd
      // console.log('🚀 ~ file: index.html ~ line 72 ~ iframeMsg ~ cmd', cmd)
      const data = event.data.data
      // console.log("videoLoad -> event", event)
      if (event.data.cmd === 'playerLoadeddata') {
        // console.log('playerLoadeddata');
        // 轨道生成生命周期
        // 发送剪辑选框数据
        const cutData = {
          start_ms: 10000, // 开始时间 必传
          end_ms: 20000, // 结束时间 必传
          max_ms: 30000 // 选框最大时间 可不填 不填默认的就是 视频总时长的宽度
        }
        // this.iframe.postMessage({ cmd: 'setCutTrackDetails', data: cutData }, '*')
        // iframe.postMessage({ cmd: "setActiveBtnList", data: activeBtnList }, "*");
      }
      // 点击剪辑按钮的返回
      if (event.data.cmd === 'exportcutDetails') {
        console.log(event.data.data)
        const { start_ms, end_ms } = event.data.data
        this.start_ms = start_ms
        this.end_ms = end_ms
        const time = {
          start_ms: start_ms,
          end_ms: end_ms
        }
        this.$bus.$emit('setTime', time)
      }
      // 拖拽的返回
      if (event.data.cmd === 'onDrag') {
        // console.log(event.data.data)
      }
      // 改变大小的返回
      if (event.data.cmd === 'onResize') {
        // console.log(event.data.data)
      }
      // 获取当前 每像素多少毫秒
      if (event.data.cmd === 'setPerPxMs') {
        // console.log(event.data.data)
      }
      // 获取当前 cut 数据
      if (event.data.cmd === 'getCutData') {
        // console.log(event.data.data)
      }
      // 获取当前 装饰数据
      if (event.data.cmd === 'exportArr') {
        // console.log(event.data.data)
      }

      // 获取当前 组件内所有数据
      if (event.data.cmd === 'exportVideoEditAllData') {
        this.getImageSrc(event.data.data.videoPlay.current_time_ms)
      }
    },

    close() {
      this.$emit('close')
    },
    async getImageSrc(time) {
      const url = await videoToImg(this.videoUrl, time)
      this.$bus.$emit('setImageData', url)
    }
  }
}
</script>

<style lang="scss">
.el-autocomplete-suggestion{
    width: auto !important;
}
.updateSampleModel{
    display: flex;

    .sample-video{
        flex: 1;
        .iframe{
          height: 600px;
          width: 100%;
        }
    }
    .sample-form{
        width: 400px;
        margin-left: 20px;
        form{
          height: 500px;
          overflow-y: auto;
        }
        .el-form-item{
            width: 100%;
            margin-right: 0;
            .el-form-item__content{
                width: 75%;
                .el-autocomplete{
                    width: 100%;
                }
            }
        }
        .btn-wrap{
            margin-top: 50px;
        }
    }
}
</style>
