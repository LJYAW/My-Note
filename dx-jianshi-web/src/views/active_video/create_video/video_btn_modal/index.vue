<!--
 * @Author: your name
 * @Date: 2020-11-04 17:20:31
 * @LastEditTime: 2020-11-12 11:56:07
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/active_video/video_btn/index.vue
-->
<template>
  <div class="video-btn-modal">
    <div class="content-wrap">

      <div class="iframe-wrap">
        <iframe
          :src="`${publicPath}`"
          frameborder="0"
          width="100%"
          height="100%"
          @load="loaded"
          id="videoEditIframe"></iframe>

      </div>
      <btn-set-pad v-if="iframeLoaded" class="btn-pad" ref="btnSetPad" @setBtnList="setBtnList"></btn-set-pad>
    </div>

    <div class="submit-btn-wrap">
      <el-button style="width: 100px" type="primary" round size="default" @click="submit">确认</el-button>
    </div>

  </div>
</template>

<script>
import btnSetPad from './btn_pad'

export default {
  name: 'VideoBtnModal',
  props: {
    data: {
      type: Object
    },
    DATA_LENTH: {
      type: Number
    },
    DATA_INDEX: {
      type: Number
    },
    layerid: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      iframeLoaded: false,
      iframe: null
    }
  },
  computed: {
    publicPath() {
      return location.origin + '/video-edit/index.html'
    }
  },
  watch: {},
  methods: {
    receiveMessage(event) {
      // video 加载完成事件
      let cmd = event.data.cmd

      if (cmd == 'playerLoadeddata') {
        this.setTrackCut()
      }
      // 获取 剪辑框数据
      if (cmd == 'getCutData') {
        let data = event.data.data
        this.$bus.$emit('set_material_time', { index: this.DATA_INDEX, ...data })
      }
    },
    setTrackCut() {
      let material = this.data.material
      let start_ms = 0
      let end_ms = 30000

      if (material.start_ms > -1) {
        start_ms = material.start_ms
        end_ms = material.end_ms
      }

      let cutData = {
        start_ms: start_ms, // 开始时间 必传
        end_ms: end_ms
      }
      this.iframe.postMessage({ cmd: 'setCutTrackDetails', data: cutData }, '*')
    },
    submit() {
      let btnPadData = this.$refs.btnSetPad._data
      this.setActiveVideolist(btnPadData)
      this.cancel()
    },
    loaded() {
      // 发送视频数据  src 是必选字段
      let playerOptions = {
        src: this.data.material.resource_url
      }

      this.iframe = document.getElementById('videoEditIframe').contentWindow
      this.iframe.postMessage({ cmd: 'setVideoOptions', data: playerOptions }, '*')
      this.iframeLoaded = true
    },
    setBtnList(list) {
      let activeBtnList = []

      // 传入必要数据
      for (let i = 0; i < list.length; i++) {
        let item = list[i]
        let obj = {
          w: item.w,
          h: item.h,
          x: item.x,
          y: item.y,
          imgUrl: item.selectBtn ? item.selectBtn.image_url : ''
        }
        activeBtnList.push(obj)
      }

      this.iframe.postMessage({ cmd: 'setActiveBtnList', data: activeBtnList }, '*')
    },
    // 存储 面板数据
    setActiveVideolist(data) {
      this.$bus.$emit('active_video_list', data)
    },
    cancel() {
      this.$layer.close(this.layerid)
    }
  },
  components: {
    btnSetPad
  },
  created() {},
  mounted() {
    window.addEventListener('message', this.receiveMessage, false)
  },
  beforeDestroy() {
    window.removeEventListener('message', this.receiveMessage, false)
  }
}
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
