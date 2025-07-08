<template>
  <div class="content_video">
    <div class="inner">
      <div class="videolist">
        <div class="list">
          <div v-for="(item, index) in videolist" :key="index" class="item" @click="playvideo(item.url)">
            <div class="videobox">
              <video
                crossOrigin="anonymous"
                x-webkit-airplay="true"
                webkit-playsinline="true"
                playsinline="true"
                x5-video-player-type="h5"
              >
                <source :src="item.url">
              </video>
            </div>
            <div class="title">{{ item.name }}</div>
          </div>
        </div>
      </div>
    </div>

    <base-dialog :show.sync="videodialog.show" :show-close="false" width="100%" class="videodialogbox">
      <div class="videodialog">
        <div class="box">
          <div class="close" @click="videoclose()" />
          <video
            crossOrigin="anonymous"
            x-webkit-airplay="true"
            webkit-playsinline="true"
            playsinline="true"
            x5-video-player-type="h5"
            controls
            autoplay
          >
            <source :src="videodialog.url">
          </video>
        </div>
      </div>
    </base-dialog>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      videodialog: {
        show: false,
        url: ''
      },
      videolist: [
        {
          url: 'https://cdn-yun.tm.video/biz_media/system/gushibobao.mp4',
          name: '股市播报'
        },
        {
          url: 'https://cdn-yun.tm.video/biz_media/system/miaodongbaike.mp4',
          name: '秒懂百科'
        },
        {
          url: 'https://cdn-yun.tm.video/biz_media/system/xinwenkuaixun.mp4',
          name: '新闻快讯'
        },
        {
          url: 'https://cdn-yun.tm.video/biz_media/system/yingshijiangjie.mp4',
          name: '影视讲解'
        },
        {
          url: 'https://cdn-yun.tm.video/biz_media/system/youshenghuiben.mp4',
          name: '有声绘本'
        },
        {
          url: 'https://cdn-yun.tm.video/biz_media/system/zhuanjigushi.mp4',
          name: '传记故事'
        }
      ]
    }
  },
  computed: {
  },
  watch: {

  },
  beforeDestroy() {

  },
  created() {
  },
  mounted() {
  },
  methods: {
    playvideo(url) {
      this.videodialog.show = true
      this.videodialog.url = url
    },
    videoclose() {
      this.videodialog.show = false
      this.videodialog.url = ''
    }
  }
}
</script>
<style scoped lang="scss">
@import "../css/index.scss";

.content_video {

}

.videolist {
  margin-top: 30px;

  .list {
    margin-left: -30px;
    overflow: hidden;
  }

  .item {
    margin-top: 30px;
    padding-left: 30px;
    float: left;
    width: 33.33333%;
    transition: all .3s ease;

    &:hover {
      color: #5675e8;
    }

    .title {
      font-size: 18px;
      margin-top: 20px;
      text-align: center;
      cursor: pointer;
    }

    .videobox {
      position: relative;
      width: 100%;
      padding-top: 56.25%;
      background-color: #999;
      cursor: pointer;

      video {
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        object-fit: cover;
      }
    }
  }
}

.videodialog {
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  z-index: 3000;

  .box {
    position: absolute;
    width: 80%;
    max-width: 800px;
    top: 50%;
    left: 50%;
    transform: translateX(-50%) translateY(-50%);
  }

  .close {
    position: absolute;
    width: 48px;
    height: 48px;
    top: -6px;
    right: -60px;
    cursor: pointer;
    text-indent: -999em;

    &:before,
    &:after {
      position: absolute;
      content: " ";
      width: 80%;
      height: 2px;
      background-color: #fff;
      top: 50%;
      left: 50%;
      transform: translateX(-50%) translateY(-50%) rotate(45deg);
    }

    &:after {
      transform: translateX(-50%) translateY(-50%) rotate(-45deg);
    }

    &:hover {

      &:before,
      &:after {
        background-color: #ccc;
      }
    }
  }

  video {
    width: 100%;
    height: auto;
  }
}

.videodialogbox {

  ::v-deep .el-dialog__header {
    display: none;
  }

  ::v-deep .el-dialog__body {
    padding: 0;
  }
}
</style>
