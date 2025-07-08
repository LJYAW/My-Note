<template>
  <div class="cut-content">

    <div class="video-pad-wrap">
      <video-pad />
    </div>

    <!-- <div class="cut-editor-pad"> -->
    <!-- <rightPad /> -->
    <!-- </div> -->

  </div>
</template>

<script>
import videoPad from './video_play/video_pad'
import rightPad from './right_pad/index'

export default {
  props: [],
  data() {
    return {}
  },
  components: {
    videoPad,
    rightPad
  },

  computed: {},

  watch: {},

  methods: {},

  created() {
    // let item = {
    //   ai_type: 'per',
    //   cp_name: '广西卫视',
    //   duration_ms: 3663944,
    //   duration_str: '01:01:03',
    //   image: 'http://vd-drm.mtq.tvm.cn/video/guangxigaoqing77/2020/02/12/GuangXiHD_1000000_1581476017_20200212105337867_3_2_6_ocr-bin.jpg?45311706_32523_1316',
    //   item_name: '歌海元宵',
    //   person: '吴亦凡',
    //   publish_time: '2020-02-08 22:03:02',
    //   time_start: 1316,
    //   time_start_str: '00:21:56',
    //   title: '20200208《歌海元宵》：2020广西文学艺术界联合会春节联欢晚会',
    //   uuid: 'b676aa71-a78c-426e-9a40-d90701452121',
    //   video_url:
    //     'http://vd-drm.mtq.tvm.cn/access/OGdXKxstUWUVeFZvVgk_YFINPmFWYFhvXG9aaltpWGpaalhoWgUyAzMFMgY3DzoLVGRUZFRkUWM8eDBZAWYIaRxbdEZ3WGpadUV3R3VabVo9UzpLJEUiSzNUOlsuSWYJbAhhFw'
    // }
    // var duration = item.duration_ms
    // let left = item.time_start * (1000 / this.per_px_ms)
    // let video_item = {
    //   type: 'video',
    //   video_url: item.video_url,
    //   duration: duration,
    //   uuid: item.uuid,
    //   start_time: item.time_start,
    //   end_time: duration,
    //   preview_img: item.photo,
    //   width: this.edtortotalms / this.per_px_ms,
    //   maxWidth: this.edtortotalms / this.per_px_ms,
    //   left: left
    // }
    // var arr = [video_item]
    // this.$store.commit('setTotalMs', duration)
    // this.$store.commit('setCutTrackList', arr)
  },

  mounted() {}
}
</script>

<style lang="scss">
.cut-content {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  .video-pad-wrap {
    margin: 0 auto;
    // background: #232323;
  }
  .cut-editor-pad {
    flex: 1;
    overflow-x: auto;
    height: 366px;
  }
  .toolbar {
    // height: 44px;
    // min-height: 44px;
    background: #232323;
    border-bottom: 1px solid #000;
    display: flex;
    flex-wrap: wrap;
    .mini-nav {
      display: flex;
      justify-content: flex-start;
      height: 44px;
      // margin-top: 15px;
      li {
        padding-top: 15px;
        margin: 0 10px;
        a {
          color: #999;
          padding-bottom: 14px;
          &.action {
            color: white;
          }
        }
      }
      // li:last-child {
      //   // margin-left: auto;
      //   padding-left: 20px;
      //   border-left: 1px solid #2e2e2e;
      // }
    }
  }
}
</style>
