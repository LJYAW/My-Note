<!--
 * @Author: zzx
 * @Date: 2020-06-15 15:16:42
 * @LastEditTime: 2020-12-07 20:09:17
 * @FilePath: /zhijian_web/src/components/audio_play.vue
-->
<template>
  <div>
    <audio ref="audio"
      :src="url"></audio>
  </div>
</template>

<script>
export default {
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
      let audio = this.$refs.audio
      val ? this.audioPaly() : audio.pause()
    }
  },
  methods: {
    audioPaly() {
      this.$nextTick(() => {
        let audio = this.$refs.audio
        let playPromise
        let second = 10
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
  },
  components: {},
  created() {},
  mounted() {
    let audio = this.$refs.audio
    let _this = this
    audio.addEventListener(
      'ended',
      function() {
        _this.$emit('playOver', true)
      },
      false
    )
  }
}
</script>

<style scoped lang="scss">
</style>
