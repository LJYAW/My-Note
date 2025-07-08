<!--
 * @Author: zzx
 * @Date: 2020-06-15 15:16:42
 * @LastEditTime: 2021-09-24 11:43:43
 * @FilePath: /zhijian_web/src/components/audio_play.vue
-->
<template>
  <div>
    <audio
      ref="audio"
      :src="url"
      @timeupdate="timeupdate"
    />
  </div>
</template>

<script>
export default {
  naem: 'BaseAudioPlay',
  components: {},
  props: {
    url: {
      type: String,
      default() {
        return ''
      }
    },
    play: {
      type: Boolean,
      default() {
        return false
      }
    }
  },
  data() {
    return {
      tiemr: null
    }
  },
  computed: {},
  watch: {
    url(val) {
      this.$nextTick(() => {
        this.audioPaly()
      })
    },
    play(val) {
      const audio = this.$refs.audio
      val ? this.audioPaly() : audio.pause()
    }
  },
  created() {},
  mounted() {
    const audio = this.$refs.audio
    const _this = this
    audio.addEventListener(
      'ended',
      function() {
        _this.$emit('playOver', true)
      },
      false
    )
  },
  methods: {
    timeupdate(e) {
      this.$emit('timeupdate', e)
    },
    audioPaly() {
      this.$nextTick(() => {
        const audio = this.$refs.audio
        var playPromise
        const second = 10
        var that = this
        // 加载音频
        playPromise = audio.play()
        if (playPromise) {
          playPromise
            .then(() => {
              // 音频加载成功
              if (this.play) {
                audio.play()
              } else {
                audio.pause()
              }
            })
            .catch(e => {
              that.$emit('audioError', e)
              console.log('音频加载失败' + e)
            })
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
</style>
