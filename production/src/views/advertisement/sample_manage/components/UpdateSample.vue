<!--
 * @Author: your name
 * @Date: 2021-05-10 17:17:09
 * @LastEditTime: 2021-10-11 18:19:08
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/components/UpdateSample.vue
-->
<template>
  <base-dialog :show="show" title="编辑样本" width="1100px" :close-on-click-modal="false" @close="close">
    <div class="updateSampleModel">
      <div class="sample-video">
        <video-player
          ref="videoPlayer"
          :options="playerOptions"
          :playsinline="true"
        />
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
        poster:
          'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-1.jpg'
      }
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.playerOptions.sources[0].src = this.videoUrl
  },
  mounted() {
    // 监听截图事件
    this.$bus.$on('cutVideoImage', () => {
      if (this.$refs.videoPlayer) this.getImageSrc(this.$refs.videoPlayer.player.currentTime() * 1000)
    })
  },
  methods: {
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
                width: 74%;
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
