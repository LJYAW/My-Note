/*
 * @Author: your name
 * @Date: 2021-02-23 16:40:51
 * @LastEditTime: 2021-06-16 20:06:15
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-zzx-ui/packages/VideoPlayer/index.js
 */
// import _videojs from 'video.js'
// import videoPlayer from './src/index.vue'

// const videojs = window.videojs || _videojs
// const install = function(Vue, config) {
//   if (config) {
//     if (config.options) {
//       videoPlayer.props.globalOptions.default = () => config.options
//     }
//     if (config.events) {
//       videoPlayer.props.globalEvents.default = () => config.events
//     }
//   }
//   Vue.component(videoPlayer.name, videoPlayer)
// }

// export default VueVideoPlayer

import VideoPlayer from './src/index.vue'

// 为组件提供 install 安装方法，供按需引入
VideoPlayer.install = function(Vue) {
  Vue.component(VideoPlayer.name, VideoPlayer)
}

// 默认导出组件
export default VideoPlayer
