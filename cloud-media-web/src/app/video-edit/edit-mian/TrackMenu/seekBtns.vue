<!--
 * @Author: your name
 * @Date: 2021-08-06 15:24:54
 * @LastEditTime: 2021-09-27 15:40:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/edit-mian/trackBar/seekBtn.vue
-->
<template>
  <div class="seek-btn-wrap">
    <!-- <base-upload
      button-name=""
      @getFileUrl="getFileUrl"
    >
      <el-button type="primary" size="mini">添加水印</el-button>
    </base-upload> -->

    <el-button type="primary" size="mini" @click="seek('start')">
      <svg-icon icon-class="seek-start" />
      定位开始
    </el-button>
    <el-button type="primary" size="mini" @click="seek('end')">
      <svg-icon icon-class="seek-end" />
      定位结束
    </el-button>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import getImgSize from '../../utils/get-image-size'
import sdkUploadFile from '@/utils/sdk-upload-file.js'
import guid from '../../utils/guid'

export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      activeId: null
    }
  },
  computed: {
    ...mapState('video', ['CUT_TRACK_WIDTH', 'canvasOptions', 'PER_PX_MS', 'cutTrackData', 'VIDEO_DURANTION_MS', 'currentTimeMs', 'decorateActiveId', 'decorateList', 'videoPlay'])

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

      const imgSize = await getImgSize(imgUrl)
      const videoWidth = this.videoPlay.videoWidth
      const videoHeight = this.videoPlay.videoHeight
      const canvasW = this.canvasOptions.canvasW
      const canvasH = this.canvasOptions.canvasH

      const roteW = (canvasW / videoWidth).toFixed(2)
      const roteH = (canvasH / videoHeight).toFixed(2)

      if (!imgSize.h) {
        this.btnDisabled = true
        return
      }

      const id = 'activeBtn' + guid()

      const list = [{
        x: 0,
        y: 0,
        w: imgSize.w * roteW,
        h: imgSize.h * roteH,
        trackLeft: 0,
        trackWidth: Infinity,
        id: id,
        imgUrl: imgUrl,
        resizable: false,
        active: true,
        type: 'image' // 装饰类型
      }]

      this.$store.commit('video/SET_DECORATE_LIST', list)
      this.$store.commit('video/SET_DECORATE_ACTIVE_ID', id)
    },
    getTrackLeft() {
      const baseWidth = 100
      const maxLeft = this.CUT_TRACK_WIDTH - baseWidth + 1 // 为了 兼容最后一像素误差 后期可能会改
      const left = Math.min(maxLeft, this.progressX)
      return left
    },
    seek(type) {
      if (!this.activeId) {
        this.setCutTrackData(type)
      } else {
        this.setDecorateData(type)
      }
    },
    setCutTrackData(type) {
      let data = {}
      const { left, width, startMs, endMs } = this.cutTrackData

      const maxLeft = this.CUT_TRACK_WIDTH - width + 1 // 为了 兼容最后一像素误差 后期可能会改
      const progreesX = this.currentTimeMs / this.PER_PX_MS
      const x = Math.min(maxLeft, progreesX)

      if (type === 'start') {
        data = Object.assign(this.cutTrackData, {
          startMs: x * this.PER_PX_MS,
          endMs: (x + width) * this.PER_PX_MS,
          left: x
        })

        this.$store.commit('video/SET_CUT_TRACK_DATA', data)
      }

      if (type === 'end') {
        if (progreesX <= left) return

        const w = progreesX - left
        data = Object.assign(this.cutTrackData, {
          width: w,
          endMs: (left + w) * this.PER_PX_MS
        })

        this.$store.commit('video/SET_CUT_TRACK_DATA', data)
      }
    },
    setDecorateData(type) {
      const data = this.decorateList.find(item => item.id === this.activeId)
      let obj = {}
      const { trackLeft, trackWidth } = data

      const maxLeft = this.CUT_TRACK_WIDTH - trackWidth + 1 // 为了 兼容最后一像素误差 后期可能会改
      const progreesX = this.currentTimeMs / this.PER_PX_MS
      const x = Math.min(maxLeft, progreesX)

      if (type === 'start') {
        obj = Object.assign(data, {
          trackLeft: x
        })

        this.$store.commit('video/SET_CUT_TRACK_DATA', data)
      }

      if (type === 'end') {
        if (progreesX <= trackLeft) return

        const w = progreesX - trackLeft
        obj = Object.assign(data, {
          trackWidth: w
        })
      }
      this.$store.commit('video/UPLOAD_DECORATE_LIST', obj)
      this.$store.commit('video/SET_DECORATE_ACTIVE_ID', this.activeId)
    }
  }
}
</script>

<style scoped lang="scss">
.seek-btn-wrap {
  display: flex;

  .upload {
    margin-right: 8px;
  }

  .svg-icon {
    margin-right: 8px;
  }

  .el-button--mini {
    padding: 8px 10px;
  }
}
</style>
