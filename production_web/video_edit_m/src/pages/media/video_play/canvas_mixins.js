/*
 * @Author: zzx
 * @Date: 2020-09-16 15:01:20
 * @LastEditTime: 2021-07-06 17:18:58
 * @FilePath: /video_edit/src/pages/media/video_play/canvas_mixins.js
 */
import { gaussBlur } from '@/utils/canvas.js'

export default {
  data() {
    return {
      canvas: '',
      context: '',
      video: '',
      animation: null
    }
  },
  computed: {
    decorate_list_temp() {
      return this.$refs.decorateBox.decorate_list_temp
    },
    video_play_status() {
      return this.$store.state.videoPlay.video_play_status
    }
  },
  watch: {
    video_play_status(val) {
      if (val === 'pause') {
        window.cancelAnimationFrame(this.animation)
        this.switchToCanvas()
      }
    }
  },
  methods: {
    setCanvas() {
      //  设置canvas
      this.canvas = document.getElementById('videoCanvas')
      this.context = this.canvas.getContext('2d')
      this.context.fillStyle = '#000'
      this.context.fillRect(0, 0, this.canvas.width, this.canvas.height)
      this.video = document.getElementById('myVideo')
      this.video.load()
      let playPromise = this.video.play()
      if (playPromise !== undefined) {
        playPromise
          .then(() => {
            this.video.play()
          })
          .catch((err) => {
            console.error(err)
          })
      }

      // this.video.play()
    },
    switchToCanvas() {
      // 绘制 视屏
      this.context.drawImage(
        this.video,
        0,
        0,
        this.canvas.width,
        this.canvas.height
      )
      this.setCanvasDecorate()
      // if (this.video_play_status === 'play') {
        // console.log('🚀 ~ file: canvas_mixins.js ~ line 67 ~ switchToCanvas ~ setCanvasDecorate')
        this.animation = window.requestAnimationFrame(this.switchToCanvas)
      // }
    },
    setCanvasDecorate() {
      this.decorate_list_temp.forEach((item) => {
        if (item.type == '马赛克') {
          let { x, y, w, h } = item
          // gaussBlur('videoCanvas', x, y, w, h)
        }
      })
    }
  },
  mounted() {
    this.setCanvas()
  }
}
