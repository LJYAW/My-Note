<!--
 * @Author: zzx
 * @Date: 2020-10-29 14:48:25
 * @LastEditTime: 2021-08-30 18:38:46
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/src/video_edit.vue
-->
<template>
  <iframe
    id="videoEditIfame"
    :src="`${publicPath}`"
    frameborder="0"
    width="100%"
    height="100%"
    @load="loaded"
  />

</template>

<script>
import { getVideoDuantion } from '../js/check-video-size'

export default {
  name: 'VideoEdit',
  inject: ['modalM'],
  components: {},
  props: {},
  data() {
    return {
      duration: 0,
      iframe: null,
      exportRange: '00:00:00',
      tipHiddenFlag: true, // 选择的视频时长是否小于素材时长
      setPerPxMs: null // 每像素多少毫秒
    }
  },
  computed: {
    publicPath() {
      return location.origin + '/video-edit/index.html?' + new Date().getTime()
    },
    video_item() {
      return this.modalM.video_item
    },
    select_item() {
      const materialListVm = this.modalM.$parent.$parent.$parent
      return materialListVm.edtor_img_list[materialListVm.edtor_img_index]
    },
    materialListVm() {
      return this.modalM.$parent.$parent.$parent
    },
    msToTime() {
      return this.$root.$options.filters.msToTime
    },
    msToSecond() {
      return this.$root.$options.filters.msToSecond
    }

    // exportRange() {
    //   let materialListVm = this.modalM.$parent
    //   let select_item = materialListVm.edtor_img_list[materialListVm.edtor_img_index]
    //   let duration = select_item.duration < duration ? select_item.duration : duration
    //   return this.msToTime(duration)
    // }
  },
  watch: {},
  created() {},
  mounted() {
    // 获取 剪辑的 开始时间 结束时间数据
    window.addEventListener('message', this.receiveMessage, false)
  },
  beforeDestroy() {
    window.removeEventListener('message', this.receiveMessage, false)
  },
  methods: {
    async loaded() {
      this.iframe = document.getElementById('videoEditIfame')
      const track_H = this.iframe.contentWindow.document.getElementsByClassName('trackHeader')[0]
      const ul = track_H.getElementsByTagName('ul')[0]
      const liExport = ul.getElementsByClassName('export-btn')[0]
      liExport.style.display = 'none'

      this.duration = (await getVideoDuantion(this.video_item.resource_url)) * 1000
      this.appendFlag()
      const playerOptions = {
        src: this.video_item.resource_url
      }
      console.log('loaded -> playerOptions', playerOptions)
      this.iframe.contentWindow.postMessage({ cmd: 'setVideoOptions', data: playerOptions }, '*')
    },
    appendFlag() {
      this.iframe = document.getElementById('videoEditIfame')
      const track_H = this.iframe.contentWindow.document.getElementsByClassName('trackHeader')[0]
      const ul = track_H.getElementsByTagName('ul')[0]

      const liFlag = ul.getElementsByTagName('li')[0]
      const liExport = ul.getElementsByClassName('export-btn')[0]
      liExport.style.display = 'block'

      const exportBtn = liExport.getElementsByTagName('div')[0]
      liExport.style.marginLeft = '0'
      // 剪选按钮颜色替换
      exportBtn.style.backgroundColor = '#165DD9'
      exportBtn.style.borderRadius = '11px'
      // 入点标记
      const flag = document.createElement('span')
      flag.innerText = '裁剪9:16'
      flag.style.fontSize = '12px'
      flag.style.color = '#999'
      flag.style.marginLeft = '5px'
      liFlag.appendChild(flag)
      // 选取时长
      this.exportRangeDom = document.createElement('li')
      this.tipText = document.createElement('li')
      this.tipText.className = 'tipText'
      this.tipText.innerText = '选取时长小于素材所需时长，将会自动重复播放'
      this.tipText.style.display = 'none'
      this.tipText.style.margin = '0 10px 0 auto'
      this.tipText.style.color = '#c51a1a'
      this.tipText.style.fontSize = '12px'
      this.exportRangeDom.innerText = `选取时长：${this.exportRange}`
      this.exportRangeDom.style.fontSize = '14px'
      this.exportRangeDom.style.margin = '0 10px 0 auto'
      ul.insertBefore(this.exportRangeDom, liExport)
      ul.insertBefore(this.tipText, this.exportRangeDom)
    },

    receiveMessage(event) {
      // 点击剪选
      // if (event.data.cmd == 'exportcutDetails') {
      //   let data = event.data.data
      //   let video_item = Object.assign(this.video_item, data)
      //   this.modalM.submit(video_item)
      // }
      // 获取当前 装饰数据和剪辑数据
      if (event.data.cmd === 'exportAllData') {
        const data = event.data.data
        console.log('receiveMessage -> data', data)
        const video_item = Object.assign(this.video_item, data.cutDetails, { decorateList: data.decorateList[0] }, { videoSize: data.videoSize })
        this.modalM.submit(video_item)
      }

      if (event.data.cmd === 'playerLoadeddata') {
        // 轨道loaded
        this.playerLoadeddata(event)
      }

      // 改变大小的返回
      if (event.data.cmd === 'onResize') {
        this.onResize(event)
      }

      // 获取当前 每像素多少毫秒
      if (event.data.cmd === 'setPerPxMs') {
        // console.log(event.data.data)
        this.setPerPxMs = event.data.data
      }
    },
    async playerLoadeddata() {
      console.log('playerLoadeddata -> playerLoadeddata')
      let duration = 0
      if (this.select_item.duration < this.duration) {
        this.tipHiddenFlag = false
        duration = this.select_item.duration
      } else {
        this.tipText.style.display = 'block'
        this.exportRangeDom.style.marginLeft = '0'
        duration = this.duration
      }

      this.exportRange = this.msToTime(duration)
      this.exportRangeDom.innerText = `选取时长：${this.exportRange}`
      let startMs = 0
      let left
      // 帧搜索
      if (this.video_item.time_start_str) {
        startMs = this.video_item.start_ms * 1000

        left = startMs / this.setPerPxMs

        const scroll = this.iframe.contentWindow.document.getElementsByClassName('edit-pad')[0]

        scroll.scrollTo({ left, behavior: 'smooth' })
      }
      // 发送剪辑选框数据
      const cutData = {
        start_ms: startMs, // 开始时间 必传
        end_ms: startMs + duration, // 默认选中时间 必传
        max_ms: duration // 选框最大时间 可不填 不填默认的就是 视频总时长的宽度
      }
      console.log('playerLoadeddata -> cutData', cutData)
      // 轨道生成生命周期
      this.iframe.contentWindow.postMessage({ cmd: 'setCutTrackDetails', data: cutData }, '*')
    },
    onResize() {
      const { start_ms, end_ms } = event.data.data
      const duration = Math.floor((end_ms - start_ms) / 1000)
      const originDuration = Math.floor(this.select_item.duration / 1000)
      this.exportRange = this.msToTime(end_ms - start_ms)
      // 选取时长小于素材所需时长，将会自动重复播放
      this.exportRangeDom.innerText = `选取时长：${this.exportRange}`
      if (!this.tipHiddenFlag) {
        if (duration < originDuration) {
          this.exportRangeDom.style.marginLeft = '0'
          this.tipText.style.display = 'block'
        } else {
          this.exportRangeDom.style.marginLeft = 'auto'
          this.tipText.style.display = 'none'
        }
      }
    }
  }
}
</script>

<style scoped lang="scss">
#videoEditIfame {
  margin-top: 20px;
}
// .tipText {
//   display: none;
// }
</style>
