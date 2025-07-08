<!--
 * @Author: your name
 * @Date: 2021-05-10 16:40:02
 * @LastEditTime: 2021-05-10 17:07:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/components/PlaySample.vue
-->
<template>
  <base-dialog :show="show" title="播放样本" width="600px" class="" :close-on-click-modal="false" @close="close">
    <video-player
      ref="videoPlayer"
      :options="playerOptions"
      :playsinline="true"
    />

    <slot />
  </base-dialog>
</template>

<script>
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

  },
  methods: {
    close() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped lang="scss">

</style>
