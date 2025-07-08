<!--
 * @Author: your name
 * @Date: 2021-08-09 19:54:26
 * @LastEditTime: 2021-08-17 16:14:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/README.MD
-->
# 项目尺寸说明
```
    SET_CANVAS_SIZE(state) {
      const clientW = document.body.clientWidth
      const baseSize = 1920
      const baseCanvasW = 952
      const baseCanvasH = 535
      const dpr = baseCanvasW / baseCanvasH

      const dprH = clientW / baseSize
      const canvasW = dprH * baseCanvasW
      const canvasH = canvasW / dpr

      state.canvasOptions.canvasW = canvasW
      state.canvasOptions.canvasH = canvasH
    }
```

# 设置初始数据
```
<!-- 待补充参数 -->
this.$store.commit('video/SET_VIDEO_OPTIONS',{
  poster: 'https://overwatch.nosdn.127.net/2/media/artwork/dva-animated-short.jpg',
  src: 'https://blz-videos.nosdn.127.net/1/OverWatch/OVR_D.VA_SHOOTING_STAR_zhCN_YT_PC_3.mp4'
})
```

# 提交按钮的返回
```
this.$bus.$emit('videoSubmit')

this.$bus.$on('videoSubmit')

beforeDestroy() {
    this.$bus.$off('videoSubmit')
  },

```
