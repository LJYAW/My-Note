<!--
 * @Author: your name
 * @Date: 2021-08-06 11:50:31
 * @LastEditTime: 2021-09-23 16:54:08
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/edit-mian/trackBar/index.vue
-->
<template>
  <div class="track-footer-bar" @click.stop>
    <CutTimeBox v-if="!videoMasking" />

    <!-- <WatermarkBtn v-if="!videoMasking" /> -->

    <SeekBtns />

    <MosaicAndWaterMark v-if="videoMasking" />

    <Range />

    <Btns />

  </div>
</template>

<script>
// import WatermarkBtn from './watermarkBtn'
import SeekBtns from './seekBtns'
import CutTimeBox from './cutTimeBox'
import Range from './range'
import Btns from './btns.vue'
import MosaicAndWaterMark from './mosaicAndWatermark.vue'

export default {
  components: {
    Range,
    Btns,
    CutTimeBox,
    SeekBtns,
    // WatermarkBtn,
    MosaicAndWaterMark
  },
  props: {

  },
  data() {
    return {

    }
  },
  computed: {
    videoMasking() {
      return this.$route.path.indexOf('video-masking') > -1
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {
  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.track-footer-bar {
  display: flex;
  // flex-wrap: wrap;

  .time-box {
    margin-right: 10px;
  }

  .add-water {
    height: 30px;
    line-height: 30px;
    background-color: #ccc;
  }

  .seek-btn-wrap {
    margin-right: 30px;
  }

  .rang-change-wrap {
    margin-left: auto;
  }

  .track-btns-wrap {
    margin-left: auto;
  }

  .mosaic-and-watermark {
    margin-left: 30px;
  }
}
</style>
