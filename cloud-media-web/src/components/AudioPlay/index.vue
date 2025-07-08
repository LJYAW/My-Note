<!--
 * @Author: zzx
 * @Date: 2020-06-15 15:16:42
 * @LastEditTime: 2021-09-01 11:26:58
 * @FilePath: /zhijian_web/src/components/audio_play.vue
-->
<template>
  <div>
    <audio
      ref="audio"
      :src="url"
      @ended="end"
    />
  </div>
</template>

<script>
export default {
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
    audioPaly() {
      this.$nextTick(() => {
        const audio = this.$refs.audio
        const second = 10
        let playPromise

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
    },
    end() {
      this.$emit('end')
    }
  }
}
</script>

<style scoped lang="scss">
</style>
