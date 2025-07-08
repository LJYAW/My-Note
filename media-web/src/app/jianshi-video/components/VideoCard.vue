<!--
 * @Author: your name
 * @Date: 2021-08-30 16:30:20
 * @LastEditTime: 2021-09-29 18:10:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/video-manage/component/video-card.vue
-->
<template>
  <div class="video-poster" @mouseleave.self="hideVideo()" @mouseenter.self="showVideo()">
    <div v-if="videoShow" class="video">
      <Vloading v-if="videoLoading" />
      <video
        ref="video"
        :src="videoUrl"
        autoplay
        muted
        :controls="controls"
      />
    </div>
    <base-image :src="coverUrl" />
  </div>
</template>

<script>
import Vloading from '@/components/VLoading'
export default {
  components: {
    Vloading
  },
  props: {
    coverUrl: {
      type: String,
      default() {
        return ''
      }
    },
    videoUrl: {
      type: String,
      default() {
        return ''
      }
    },
    controls: {
      type: Boolean,
      default() {
        return false
      }
    }
  },
  data() {
    return {
      // 是否显示播放当前视频
      videoShow: false,
      videoLoading: false
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    hideVideo() {
      this.videoShow = false
    },
    showVideo() {
      this.videoShow = true
      this.videoLoading = true
      this.$nextTick(() => {
        const dom = this.$refs.video
        if (!dom) return
        dom.addEventListener('canplaythrough', () => {
          this.videoLoading = false
        })
      })
    }
  }
}
</script>

<style scoped lang="scss">
.video-poster {
  height: 0;
  padding-bottom: 56.25%;
  position: relative;
  background: #eee;

  ::v-deep .el-image {
    position: static;

    .image-slot {
      position: absolute;
      width: 100%;
      height: 100%;
      top: 0;
      background: #eee;

      .svg-icon {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
      }
    }

    img {
      position: absolute;
      width: 100%;
      height: 100%;
      top: 0;
      // object-fit: cover;
    }
  }

  .video {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    z-index: 3;
    background: #232323;

    video {
      position: absolute;
      width: 100%;
      height: 100%;
      top: 0;
      // object-fit: cover;
    }
  }

}

</style>
