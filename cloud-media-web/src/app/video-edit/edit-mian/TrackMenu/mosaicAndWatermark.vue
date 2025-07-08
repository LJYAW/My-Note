<!--
 * @Author: your name
 * @Date: 2021-09-01 15:27:30
 * @LastEditTime: 2021-09-27 15:51:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/edit-mian/TrackMenu/mosaic-and-watermark.vue
-->
<template>
  <div class="mosaic-and-watermark">
    <div class="track-btns-wrap">
      <el-button type="primary" size="mini" @click="addMosaic">添加马赛克</el-button>
      <base-upload
        button-name=""
        @getFileUrl="getFileUrl"
      >
        <el-button type="primary" size="mini">添加水印</el-button>
      </base-upload>

      <el-button type="danger" size="mini" @click="deleteTacks">删除</el-button>

    </div>
  </div>
</template>

<script>
import guid from '../../utils/guid'
import getImgSize from '../../utils/get-image-size'
import { mapState } from 'vuex'
import sdkUploadFile from '@/utils/sdk-upload-file.js'

export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      activeId: ''
    }
  },
  computed: {
    ...mapState('video', ['videoPlay', 'currentTimeMs', 'canvasOptions', 'decorateActiveId', 'decorateList', 'CUT_TRACK_WIDTH', 'progressX'])

  },
  watch: {
    decorateActiveId(id) {
      id && (this.activeId = id)
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {

    async getFileUrl(url, file) {
      const data = await sdkUploadFile(file, 'mask')
      const { location, key } = data.body
      const imgUrl = location
      const imgKey = key

      const imgSize = await getImgSize(url)
      const imgH = imgSize.h
      const imgW = imgSize.w
      const videoWidth = this.videoPlay.videoWidth
      const videoHeight = this.videoPlay.videoHeight

      const canvas = document.querySelector('.canvas-parent')
      console.log('🚀 ~ file: mosaicAndWatermark.vue ~ line 74 ~ getFileUrl ~ canvas', canvas)
      // const canvasW = this.canvasOptions.canvasW
      // const canvasH = this.canvasOptions.canvasH
      const canvasW = parseInt(canvas.style.width)
      const canvasH = parseInt(canvas.style.height)

      const roteW = (canvasW / videoWidth).toFixed(2)
      const roteH = (canvasH / videoHeight).toFixed(2)

      const id = 'img' + guid()

      const trackLeft = this.getTrackLeft()

      const list = {
        x: 0,
        y: 0,
        w: Math.min(imgW * roteW, canvasW),
        h: Math.min(imgH * roteH, canvasH),
        trackLeft: trackLeft,
        trackWidth: 100,
        trackHeight: 30,
        id: id,
        resizable: true,
        active: true,
        imgKey: imgKey,
        imgUrl: imgUrl,
        type: 'image' // 装饰类型
      }

      this.$store.commit('video/ADD_DECORATE_LIST', list)
      this.$store.commit('video/SET_DECORATE_ACTIVE_ID', id)
      this.$bus.$emit('addChangePosition')
    },
    addMosaic() {
      const id = 'mosaic' + guid()

      const trackLeft = this.getTrackLeft()

      const list = {
        x: 0,
        y: 0,
        w: 100,
        h: 100,
        trackLeft: trackLeft,
        trackWidth: 100,
        trackHeight: 30,
        id: id,
        resizable: true,
        active: true,
        type: 'gaussBlur' // 装饰类型
      }

      this.$store.commit('video/ADD_DECORATE_LIST', list)
      this.$store.commit('video/SET_DECORATE_ACTIVE_ID', id)
      this.$bus.$emit('addChangePosition')
    },
    addWaterMark() {

    },
    deleteTacks(e) {
      const index = this.decorateList.findIndex(item => item.id === this.activeId)
      index > -1 && this.$store.commit('video/DELETE_DECORATE_LIST', index)
    },
    getTrackLeft() {
      const baseWidth = 100
      const maxLeft = this.CUT_TRACK_WIDTH - baseWidth + 1 // 为了 兼容最后一像素误差 后期可能会改
      const left = Math.min(maxLeft, this.progressX)
      return left
    }
  }
}
</script>

<style scoped lang="scss">

.track-btns-wrap {
  display: flex;

  .el-button {
    margin-right: 10px;
  }
}
</style>
