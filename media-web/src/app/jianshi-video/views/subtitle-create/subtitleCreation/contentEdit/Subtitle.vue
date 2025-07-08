<!--
 * @Author: your name
 * @Date: 2021-09-10 15:38:56
 * @LastEditTime: 2021-09-15 11:19:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/subtitleCreation/contentEdit/Subtitle.vue
-->
<template>
  <div class="subtitle-wrap">
    <!-- handles是否缩放功能 handles=""-->
    <vue-draggable-resizable
      v-for="(item,index) in subtitle"
      :key="index"
      :parent="true"
      :w="item.w"
      :h="item.h"
      :x="item.x"
      :y="item.y"
      :handles="item.handles"
      :class="[item.type==='text'&&'resizable-text',(item.type==='text'&&!item.text)&&'hide-resizable']"
      @dragstop="onDragStop(arguments,index)"
    >
      <img v-if="item.type === 'image'" :src="item.src" class="dragegable-image">
      <span v-else :class="[index!='titleText'&&'subtitle-text','draggable-text']">{{ item.text }}</span>
    </vue-draggable-resizable>

  </div>
</template>

<script>
import VueDraggableResizable from './components/VueDraggableResizable/src/vue-draggable-resizable.vue'
import { mapState } from 'vuex'
import getImgSize from '../../../../utils/get-image-size'
const singleHeight = 20; const singleCnWidth = 15; const singleEnWidth = 6.7
export default {
  components: {
    VueDraggableResizable
  },
  props: {
    videoSize: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      // subtitle: [],
      titleData: [],
      contentList: [],
      testX: 0,
      testY: 0,
      built_in: [{
        id: 1,
        image_url: 'https://static-magic.tmsx.net/storage/mnt/zhijian/intelligent_writing/caption_background/moren.png'
      }, {
        id: 2,
        image_url: 'https://static-magic.tmsx.net/storage/mnt/zhijian/intelligent_writing/caption_background/huanqiucaijing.png'
      }, {
        id: 3,
        image_url: 'https://static-magic.tmsx.net/storage/mnt/zhijian/intelligent_writing/caption_background/kejichuangyi.png'
      }],
      decorateActiveId: 1,
      activeSubtitle: {}
    }
  },
  computed: {
    ...mapState('jianshi', ['effectData', 'subTitleSingle', 'target_ratio',
      'subTilteList', 'videoCurrentTimes', 'title', 'subTilteList']),
    subtitle() {
      return this.titleData.concat(this.contentList)
    }
  },
  watch: {
    'effectData.caption_bg_id'(val) {
      // this.setSubtitleBg(val)
    },
    videoCurrentTimes: {
      handler(curMs) {
        this.activeSubtitle = this.subTilteList.find(item => {
          return curMs * 1000 >= item.timestamp && curMs * 1000 < item.endTimestamp
        })
      },
      immediate: true
    },
    activeSubtitle: {
      handler(obj) {
        if (!obj) return
        this.setContentList(obj)
      },
      immediate: true
    },
    title: {
      handler(title) {
        if (!title) return
        const baseData = {
          type: 'titleText',
          w: 'auto',
          h: 'auto',
          handles: [],
          dragX: this.titleData.length ? this.titleData[0].dragX : null,
          dragY: this.titleData.length ? this.titleData[0].dragY : null
        }
        this.titleData = [{
          text: title,
          ...baseData
        }]
        this.$nextTick(() => {
          const width = document.getElementsByClassName('draggable-text')[0].offsetWidth
          const height = document.getElementsByClassName('draggable-text')[0].offsetHeight
          this.setTitleData(width, height)
        })
      },
      immediate: true
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {
    async setSubtitleBg(caption_bg_id) {
      const index = this.built_in.findIndex(item => item.id === caption_bg_id)
      const url = this.built_in[index].image_url
      const imgSize = await getImgSize(url)
      this.subtitle[2].src = this.built_in[index].image_url
      this.subtitle[2].w = imgSize.w
      this.subtitle[2].h = imgSize.h
    },
    setPositionData() {
      this.subtitle.forEach((item, index) => {
        if (item.type === 'text') {
          const singleWidth = index ? singleEnWidth : singleCnWidth
          item.w = Math.min(item.text.length * singleWidth, this.parentW * 0.9)
          item.h = Math.ceil((item.text.length * singleWidth) / (this.parentW * 0.9)) * singleHeight
          item.x = (this.parentW - item.w) / 2
          item.y = this.parentH
        }
      })
    },
    // 重新编辑初始化
    resetInit() {

    },
    // 设置字幕信息
    setSubtitleText() {
      const obj = JSON.parse(this.subTitleSingle.save_subtitles)[this.subTitleIndex]
      const textArr = obj.word.split('\n')
      this.subtitle[0].text = textArr[0]
      this.subtitle[1].text = textArr[1] || ''
    },
    getWidth(text) {
      // const dom = document.getElementsByClassName('test')[0]
      // console.log('🚀 ~ file: Subtitle.vue ~ line 172 ~ getWidth ~ dom', dom)
      // if (dom) {
      //   console.log(dom.offsetWidth)
      // }
      // console.log(document.getElementsByClassName('draggable-text')[0].offsetWidth)
      // console.log(this.titleData)

      // console.log(this.titleData.width)

      // const width = subType ? singleEnWidth : singleCnWidth
      // return Math.min(text.length * width, this.videoSize.width * 0.9)
    },
    onDragStop(args, index) {
      switch (index) {
        case 0:
          this.setTitleDrag(args)
          return
        case 3:
          this.setSubtitleBgDrag(args)
          return
        default:
          this.setContentDrag(args)
      }
    },
    setTitleData(width, height) {
      const x = this.titleData[0].dragX ? this.titleData[0].dragX : (this.videoSize.width - width) / 2
      const y = this.titleData[0].dragY ? this.titleData[0].dragY : 0
      this.$set(this.titleData[0], 'w', width + 10)
      this.$set(this.titleData[0], 'h', height + 10)
      this.$set(this.titleData[0], 'x', x)
      this.$set(this.titleData[0], 'y', y)
    },
    setTitleDrag(args) {
      this.titleData[0].dragX = args[0]
      this.titleData[0].dragY = args[1]
    },
    setContentList(obj) {
      const textArr = obj.word.split('\n')
      textArr.forEach((item, index) => {
        const obj = {
          type: 'subTitleText',
          text: item,
          w: 'auto',
          h: 'auto'
        }
        this.$set(this.contentList, index, obj)
        this.$nextTick(() => {
          const width = document.getElementsByClassName('subtitle-text')[index].offsetWidth
          const height = document.getElementsByClassName('subtitle-text')[index].offsetHeight
          this.setContentPosition(width, height, index)
        })
      })
    },
    setSubtitleBgDrag() {

    },
    setContentPosition(width, height, index) {
      const x = this.testX ? this.testX : (this.videoSize.width - width) / 2
      const y = this.testY ? this.testY : this.videoSize.height - 30 - height
      this.$set(this.contentList[index], 'x', x)
      this.$set(this.contentList[index], 'y', y)
    },
    setContentDrag() {

    }
  }
}
</script>

<style scoped lang="scss">
.subtitle-wrap {
  position: absolute;
  width: 100%;
  height: calc(100% - 30px);
  top: 0;
  left: 0;
  color: #fff;
  font-size: 14px;
  pointer-events: none;

  .draggable {
    pointer-events: auto;
  }

  .draggable.active {
    border: 2px solid #5675e8;
  }

  .resizable-text {
    z-index: 8!important;
    cursor: pointer;
  }

  .hide-resizable {
    display: none;
  }
}
</style>
