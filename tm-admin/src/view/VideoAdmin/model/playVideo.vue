<!--
 * @Author: your name
 * @Date: 2021-05-27 17:00:45
 * @LastEditTime: 2021-07-12 17:40:50
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/videoAdmin/model/playVideo.vue
-->
<!--  -->
<template>
  <div class="video-warp">
    <video-player
      ref="videoPlayer"
      class="vjs-custom-skin"
      :options="playerOptions"
      :playsinline="true"
    />
    <div class="content-box">
      <div class="deatils-box">
        <div class="item-message">
          <div>视频名称:</div>
          <div>{{ videoData.videotitle }}</div>
        </div>
        <div class="item-message">
          <div>开播时间:</div>
          <div>{{ videoData.playtime }}</div>
        </div>
        <div class="item-message">
          <div>集/期数:</div>
          <div>{{ videoData.nums }}</div>
        </div>
        <div class="item-message">
          <div>视频介绍:</div>
          <div>{{ videoData.descs }}</div>
        </div>
      </div>
      <div class="cover">
        <img :src="videoData.videopic" alt="">
      </div>
    </div>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';

export default {
// import引入的组件需要注入到对象中才能使用
  components: {},
  props: {
    videoData: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    // 这里存放数据
    return {
      playerOptions: {
        height: 100,
        autoplay: true,
        muted: false,
        language: 'en',
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [
          {
            type: 'video/mp4',
            // mp4
            src: 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4'
          }
        ],
        poster:
          'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-1.jpg'
      }
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {

  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {
    this.playerOptions.sources[0].src = this.$parent.title === '播放bos'
      ? 'http://play.tmsx.net' + this.videoData.bos_keyv
      : this.videoData.videourl
  },
  beforeCreate() {}, // 生命周期 - 创建之前
  beforeMount() {}, // 生命周期 - 挂载之前
  beforeUpdate() {}, // 生命周期 - 更新之前
  updated() {}, // 生命周期 - 更新之后
  beforeDestroy() {}, // 生命周期 - 销毁之前
  destroyed() {}, // 生命周期 - 销毁完成
  activated() {},
  // 方法集合
  methods: {

  } // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style lang='scss' scoped>
//@import url(); 引入公共css类
.video-warp{
  height: 500px;
  overflow-y: scroll;
    .vjs-custom-skin{
        width: 100%;
        height:360px;
        ::v-deep .video-js{
            width: 100%;
            height: 100%;
            padding: 0;
        }
    }
    .content-box{
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
      .cover{
        width: 180px;
        height: 90px;
        margin-left: 10px;
        img{
          width: 100%;
          height: 100%;
        }
      }
      .deatils-box{
        flex: 1;
        .item-message{
          display: flex;
          margin-top: 10px;
          div:nth-child(1){
            width: 80px;
          }
          div:nth-child(2){
            flex: 1;
          }
        }
      }
    }
}
</style>
