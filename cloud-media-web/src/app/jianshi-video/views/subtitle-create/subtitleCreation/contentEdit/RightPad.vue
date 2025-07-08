<!--
 * @Author: your name
 * @Date: 2021-09-13 18:23:57
 * @LastEditTime: 2021-10-13 16:51:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/subtitleCreation/contentEdit/RightPad.vue
-->
<template>
  <div class="right-pad">
    <div class="media-content" :style="`width: ${videoSize.width}px;height: ${videoSize.height}px`">
      <video-player
        :key="target_ratio"
        ref="videoPlayer"
        :options="playerOptions"
        class="videoPlayer"
        @timeupdate="timeupdate"
        @loadeddata="loadeddata"
      />

      <!-- 拖拽 -->
      <div :class="['drag-wrap',target_ratio==='9:16'&&'shu-drag-wrap']">
        <vue-draggable-resizable
          v-for="item in contentListShow"
          :key="item.id"
          :w="item.w"
          h="auto"
          :x="item.x"
          :y="item.y"
          :z="item.zIndex"
          :handles="item.handles"
          :active="decorateActiveId == item.id"
          :parent="true"
          :resizable="item.resizable"
          @dragging="onDrag"
          @resizing="onResize"
          @update:active="activated(arguments,item)"
          @dragstop="onDragStop(arguments,item)"
          @resizestop="onResizeStop(arguments,item)"
        >

          <img v-if="item.type === 'bgImg'" :src="item.imgUrl" class="dragegable-image">
          <p
            v-if="item.type.includes('text')"
            :class="['dragegable-text',item.type==='text-cn'&&target_ratio==='9:16'&&'dragegable-en-text']"
            :style="item.style"
          >
            {{ item.word }}
          </p>
          <p v-if="item.type.includes('videoTitle')" class="dragegable-text" :style="item.style">
            {{ item.word }}
          </p>
        </vue-draggable-resizable>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import VueDraggableResizable from './components/VueDraggableResizable/src/vue-draggable-resizable.vue'
import guiId from '../../../../utils/guid'

export default {
  components: {
    VueDraggableResizable
  },
  props: {

  },
  data() {
    return {
      grid: [20, 20],

      videoDurantion: 0,
      currentTime: 0,
      decorateActiveId: null,
      playerOptions: {
        height: '360',
        autoplay: false,
        muted: false,
        fluid: true,
        sources: [
          {
            type: 'video/mp4',
            src: ''
          }
        ],
        poster: '',
        aspectRatio: this.$store.state.jianshi.target_ratio
      },
      contentList: [],
      contentListTemp: [],
      contentListShow: []
    }
  },
  computed: {
    ...mapState('jianshi', ['target_ratio', 'showTitle', 'subTitleSingle', 'subTilteList', 'subtitleVideoSale', 'showTitle', 'setVideoStartTimes', 'setVideoEndTimes', 'subtitleBackSingleData', 'title', 'subtitlevideoSingleData']),

    // 根据选择比例 计算视频宽高
    videoSize() {
      const value = this.target_ratio
      const videoSize = {}

      if (value === '16:9') {
        videoSize.height = 344
        videoSize.width = 610
      }
      if (value === '9:16') {
        videoSize.height = 474
        videoSize.width = 266
      }

      return videoSize
    }
  },
  watch: {
    contentListTemp: {
      handler() {
        this.$store.commit('jianshi/setContentListTemp', this.contentListTemp)
      },
      deep: true
    },
    title() {
      (this.showTitle !== -1) && this.setContentVideoTitle(this.title)
    },

    videoSize: {
      handler(val) {
        this.contentListTemp = []

        const { file_url } = this.subtitleBackSingleData
        this.setContentBgImg(file_url)

        const textList = this.contentList
        this.setContentListTemp(textList)

        const title = this.title
        this.target_ratio !== '16:9' && this.setContentVideoTitle(title)
      },
      deep: true,
      immediate: true
    },
    showTitle: {
      handler(val) {
        if (val === -1) {
          const index = this.contentListTemp.findIndex(item => item.type === 'videoTitle')
          this.contentListTemp.splice(index, 1)
        } else {
          this.setContentVideoTitle(this.title)
        }
        this.setContentListShow()
      },
      deep: true,
      immediate: true
    },
    // 视频背景
    subtitlevideoSingleData: {
      handler(val) {
        if (val) {
          const { file_url } = val
          this.setVideoBgImg(file_url)
        }
      },
      deep: true,
      immediate: true
    },
    // 字幕背景
    subtitleBackSingleData: {
      handler(val) {
        if (val) {
          if (val.label === '无') {
            console.log()
            const index = this.contentListTemp.findIndex(item => item.type === 'bgImg')
            this.contentListTemp.splice(index, 1)
            this.setContentListShow()
          } else {
            const { file_url } = val
            file_url && this.setContentBgImg(file_url)
          }
        }
      },
      deep: true,
      immediate: true
    },
    currentTime(ms) {
      this.setContentListShow(ms)
    },
    subTilteList: {
      handler(val) {
        // this.setContentListTemp(val)
      },
      deep: true
    },
    subTitleSingle: {
      handler(val) {
        this.playerOptions.sources[0].src = val.video_paths
      },
      deep: true,
      immediate: true
    },
    target_ratio() {
      this.playerOptions.aspectRatio = this.target_ratio
    },
    setVideoEndTimes: {
      handler: function(val, oldval) {
        this.$refs.videoPlayer.player.currentTime(this.setVideoStartTimes / 1000)
        this.$refs.videoPlayer.player.play()
        this.pauseFlag = true
      },
      deep: true
    }
  },
  created() {

  },
  beforeDestroy() {
    this.$bus.$off('setBaseContentList')
    this.$bus.$off('updateContentList')
    this.$bus.$off('addSubtitleList')
  },
  mounted() {
    this.$bus.$on('setBaseContentList', (list) => {
      this.contentList = list
      this.contentListTemp = []
      this.setContentListTemp(list)
    })

    this.$bus.$on('updateContentList', (data) => {
      if (data) {
        this.updateContentList(data)
      }
    })

    this.$bus.$on('addSubtitleList', (data) => {
      if (data) {
        this.addSubtitleList(data)
      }
    })
    // 字幕播放按钮
    this.$bus.$on('videoPlayHandel', (item) => {
      this.$refs.videoPlayer.player.currentTime(this.setVideoStartTimes / 1000)
      this.$refs.videoPlayer.player.play()
      this.pauseFlag = true
    })
  },
  methods: {
    loadeddata(player) {
      this.$store.commit('jianshi/SET_VIDEODURATION', player.duration())
      this.videoDurantion = Math.floor(player.duration() * 1000)
    },
    timeupdate(player) {
      this.currentTime = Math.floor(player.currentTime() * 1000)
      this.$store.commit('jianshi/SET_VIDEOCURRENTTIMES', player.currentTime())
      if (player.currentTime() >= parseInt(this.setVideoEndTimes / 1000) && this.pauseFlag) {
        this.$refs.videoPlayer.player.pause()
        this.pauseFlag = false
      }
    },
    setContentListShow(ms = 0) {
      this.contentListShow = this.contentListTemp.filter(item => {
        const isOther = (item.type === 'bgImg' || item.type === 'videoTitle')
        const isWidth = item.w > 0
        const inTime = (ms >= item.startMs && ms <= item.endMs)
        return (isOther || inTime) && isWidth
      })
    },
    // 设置 视频标题
    setContentVideoTitle(title) {
      const videoSize = this.videoSize
      const parentWidth = videoSize.width
      const parentHieght = videoSize.height

      const bottomRate = 0.1

      const text = this.setVideoTitlePositions(title)

      const enX = (parentWidth - text.width) / 2
      const enY = parentHieght * bottomRate

      const titleObj = {
        x: enX,
        y: enY,
        w: text.width,
        h: text.height,
        startMs: 0,
        endMs: this.videoDurantion,
        type: 'videoTitle',
        zIndex: 1,
        word: title,
        resizable: false,
        handles: [],
        style: text.style,
        id: guiId()
      }
      const index = this.contentListTemp.findIndex(item => item.type === 'videoTitle')
      if (index > -1) {
        this.$set(this.contentListTemp, index, titleObj)
      } else {
        this.contentListTemp.push(titleObj)
      }
      // const i = this.contentListTemp.find(item => item.type === 'videoTitle')
      this.setContentListShow()
    },

    // 设置 字幕背景基础数据
    setContentBgImg(file_url) {
      if (!file_url) return

      const { height, width } = this.videoSize
      let baseHeight = 80

      if (this.target_ratio === '16:9') {
        baseHeight = 80
      }
      if (this.target_ratio === '9:16') {
        baseHeight = 100
      }

      const imgObj = {
        x: 0,
        y: height - baseHeight,
        w: width,
        h: baseHeight,
        type: 'bgImg',
        startMs: 0,
        zIndex: 'auto',
        endMs: this.videoDurantion,
        imgUrl: file_url,
        handler: ['tl', 'tm', 'tr', 'mr', 'br', 'bm', 'bl', 'ml'],
        resizable: true,
        id: guiId()
      }

      const index = this.contentListTemp.findIndex(item => item.type === 'bgImg')
      if (index > -1) {
        this.$set(this.contentListTemp, index, imgObj)
      } else {
        this.contentListTemp.push(imgObj)
      }
      this.setContentListShow()
    },
    // 设置 字幕基础数据
    setContentListTemp(arr) {
      const videoSize = this.videoSize
      const parentWidth = videoSize.width
      const parentHieght = videoSize.height

      const bottomRate = 10

      for (let i = 0; i < arr.length; i++) {
        const item = arr[i]
        const en = this.setTextPositions(item.english)
        const cn = this.setTextPositions(item.chinese)

        let cnY = (parentHieght - bottomRate - en.height)

        if (en) {
          cnY = Math.max((cnY - cn.height - 5), 0)
        }

        const cnX = (parentWidth - cn.width) / 2

        const cnObj = {
          x: cnX,
          y: cnY,
          w: Math.max(cn.width, 1),
          h: cn.height + 2,
          startMs: item.timestamp,
          endMs: item.endTimestamp,
          type: 'text-cn',
          zIndex: 1,
          word: item.chinese,
          resizable: false,
          handles: [],
          style: cn.style,
          contentId: item.id,
          id: guiId()
        }
        this.contentListTemp.push(cnObj)

        const enX = (parentWidth - en.width) / 2
        const enY = (parentHieght - bottomRate - en.height)

        const enObj = {
          x: enX,
          y: enY,
          w: en.width,
          h: en.height,
          startMs: item.timestamp,
          endMs: item.endTimestamp,
          type: 'text-en',
          zIndex: 1,
          word: item.english,
          resizable: false,
          handles: [],
          style: en.style,
          contentId: item.id,
          id: guiId()
        }

        this.contentListTemp.push(enObj)
      }

      this.setContentListShow()
    },

    // 获取文字 样式
    setTextPositions(text, w = '100%') {
      const margin = 20
      const maxWidth = this.videoSize.width - margin

      const style = {
        maxWidth: maxWidth + 'px',
        width: w,
        opacity: 1,
        fontSize: this.target_ratio === '16:9' ? '15px' : '12px',
        fontFamily: 'Microsoft YaHei',
        color: 'white',
        margin: '0 auto'
      }

      const textNode = document.createElement('p')
      for (const key in style) {
        textNode.style[key] = style[key]
      }
      textNode.innerText = text
      document.body.appendChild(textNode)
      const { width, height } = textNode.getBoundingClientRect()
      document.body.removeChild(textNode)
      return {
        width, height: height + 2, textNode, maxWidth, style
      }
    },
    // 获取文字 样式
    setVideoTitlePositions(text, w = 'max-content') {
      const margin = 20
      const maxWidth = this.videoSize.width - margin

      const style = {
        maxWidth: maxWidth + 'px',
        width: w,
        opacity: 1,
        fontSize: '22px',
        fontWeight: 600,
        fontFamily: 'Microsoft YaHei',
        color: '#EEC405',
        margin: '0 auto',
        wordBreak: 'break-all'
      }

      const textNode = document.createElement('p')
      for (const key in style) {
        textNode.style[key] = style[key]
      }
      textNode.innerText = text
      document.body.appendChild(textNode)
      const { width, height } = textNode.getBoundingClientRect()
      document.body.removeChild(textNode)
      return {
        width, height: height + 2, textNode, maxWidth, style
      }
    },
    // 重置 文字位置
    resetContentListShow(x, y, data) {
      const index = this.contentListTemp.findIndex(item => item.id === data.id)
      const { w, type } = data

      const wArr = this.contentListTemp.map(item => item.w)
      const maxWidthForWarr = Math.max(...wArr, w)
      this.contentListTemp.forEach(item => {
        // 统一更改文本位置
        if (item.type.includes('text') && data.type.includes('text') && item.type === type) {
          const curW = w + 'px'
          const { height, width, maxWidth } = this.setTextPositions(item.word)
          const xt = x
          // if (x + width > maxWidth) {
          //   xt = 0
          // }

          const obj = Object.assign(item, {
            x: xt,
            w: w,
            y: y,
            h: height
          })
          item = obj
        }
      })
    },

    // 添加
    addSubtitleList(item) {
      const videoSize = this.videoSize
      const parentWidth = videoSize.width
      const parentHieght = videoSize.height

      const bottomRate = 10

      const en = this.setTextPositions(item.english)
      const cn = this.setTextPositions(item.chinese)

      let cnY = (parentHieght - bottomRate - en.height)

      if (en) {
        cnY = Math.max((cnY - cn.height - 5), 0)
      }

      const cnX = (parentWidth - cn.width) / 2

      const cnObj = {
        x: cnX,
        y: cnY,
        w: cn.width,
        h: cn.height + 2,
        startMs: item.timestamp,
        endMs: item.endTimestamp,
        type: 'text-cn',
        zIndex: 1,
        word: item.chinese,
        resizable: false,
        handles: [],
        contentId: item.id,
        id: guiId()
      }
      this.contentListTemp.push(cnObj)

      const enX = (parentWidth - en.width) / 2
      const enY = (parentHieght - bottomRate - en.height)

      const enObj = {
        x: enX,
        y: enY,
        w: en.width,
        h: en.height,
        startMs: item.timestamp,
        endMs: item.endTimestamp,
        type: 'text-en',
        zIndex: 1,
        word: item.english,
        resizable: false,
        handles: [],
        contentId: item.id,
        id: guiId()
      }

      this.contentListTemp.push(enObj)

      this.setContentListShow()
    },

    // 更新背景图片位置大小信息
    uploadBgimg({ x, y, w, h }) {
      const index = this.contentListTemp.findIndex(item => item.type === 'bgImg')
      const item = this.contentListTemp[index]
      const obj = Object.assign(item, {
        x: x || item.x,
        y: y || item.y,
        w: w || item.w,
        h: h || item.h
      })

      this.$set(this.contentListTemp, index, obj)
    },

    // 更新title 位置
    uploadVideoTitle(x, y) {
      const index = this.contentListTemp.findIndex(item => item.type === 'videoTitle')
      const item = this.contentListTemp[index]
      const obj = Object.assign(item, {
        x: Math.floor(x),
        y: Math.floor(y)
      })
      this.$set(this.contentListTemp, index, obj)
    },

    // 改变 基础数据文字和时间
    updateContentList(data, index) {
      // const { timestamp, endTimestamp, english, chinese } = data
      const curData = this.contentListTemp.filter(item => item.contentId === data.id)

      curData.forEach(item => {
        const index = this.contentListTemp.findIndex(arr => arr.id === item.id)
        if (item.type === 'text-en') {
          const en = this.setTextPositions(item.word)

          const obj = Object.assign(item, {
            startMs: data.timestamp,
            endMs: data.endTimestamp,
            word: data.english,
            w: en.width,
            h: en.height
          })
          this.$set(this.contentListTemp, index, obj)
        }
        if (item.type === 'text-cn') {
          const cn = this.setTextPositions(item.word)
          const obj = Object.assign(item, {
            startMs: data.timestamp,
            endMs: data.endTimestamp,
            word: data.chinese,
            w: cn.width,
            h: cn.height
          })
          this.$set(this.contentListTemp, index, obj)
        }
      })
    },
    // 设置 视频背景图片
    setVideoBgImg(fileUrl) {
      if (fileUrl) {
        this.$nextTick(() => {
          const videoDom = document.querySelector('.vjs-theme-fantasy')
          videoDom.style.backgroundImage = `url(${fileUrl})`
          videoDom.style.backgroundSize = 'cover'
        })
      } else {
        this.$nextTick(() => {
          const videoDom = document.querySelector('.vjs-theme-fantasy')
          videoDom.style.backgroundImage = ''
          videoDom.style.backgroundSize = 'cover'
        })
      }
    },
    setVideoSize(value) {
      const videoSize = {}

      if (value === '16:9') {
        videoSize.height = 610
        videoSize.width = 344
      }
      if (value === '9:16') {
        videoSize.height = 474
        videoSize.width = 266
      }
    },
    activated(data) {
      const active = data[0]
      this.decorateActiveId = active.id
    },
    onDrag() {},
    onResize() {

    },
    onDragStop(data, item) {
      const x = data[0]
      const y = data[1]

      if (item.type.includes('text')) {
        this.resetContentListShow(x, y, item)
      }
      if (item.type.includes('bgImg')) {
        this.uploadBgimg(x, y, item)
      }
      if (item.type.includes('videoTitle')) {
        this.uploadVideoTitle(x, y)
      }
    },

    onResizeStop(data) {
      const [x, y, w, h] = data
      this.uploadBgimg({ x, y, w, h })
    }
  }
}
</script>

<style scoped lang="scss">
.right-pad {
  margin-left: 20px;

  .media-content {
    background-color: black;
    position: relative;

    ::v-deep .vjs-control-bar {
      height: 30px;
      overflow: hidden;
      opacity: 1 !important;
      position: absolute;
      bottom: -30px;
      background-color: rgba($color: #000, $alpha: .8);
      display: flex;

      .vjs-time-control,
      .vjs-icon-placeholder:before {
        line-height: 30px;
        margin-top: 0;
      }
    }

    .drag-wrap {
      position: absolute;
      width: 100%;
      height: calc(100%);
      top: 0;
      left: 0;
      color: #fff;
      pointer-events: none;

      .draggable {
        pointer-events: auto;
      }

      .draggable {
        background-color: transparent;
        border: 2px solid transparent;
        position: absolute;
        text-align: center;

        .dragegable-en-text {
          opacity: .6!important;
        }

        .dragegable-image {
          object-fit: fill;
        }

        .handle {
          box-sizing: border-box;
          position: absolute;
          width: 10px;
          height: 10px;
          background: #eee;
          border: 1px solid #333;
        }

        &.active {
          border: 2px dotted #5675e8;
        }
      }
    }
  }
}
</style>
