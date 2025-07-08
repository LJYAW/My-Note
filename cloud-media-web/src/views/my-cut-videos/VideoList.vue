<!--
 * @Author: your name
 * @Date: 2021-08-12 14:59:34
 * @LastEditTime: 2021-08-24 16:28:37
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/my-cut-videos/VideoList.vue
-->
<template>
  <div class="video-list">
    <transition-group name="list" tag="div" class="transition-wrap">
      <base-card
        v-for="data in videoData"
        :key="data.id"
        class="item"
        :show-mark="false"
        @click.native="showVideo(data.cut_video_url)"
      >
        <div slot="img">
          <base-image :src="data.video.cover_url" />
          <div v-if="data.status!==2" class="mark-status">
            <div slot="cutStatus" :class="[data.status===1&&'is-cutting','cutStatus']">
              {{ data.status|clipStatus }}
            </div>
          </div>

          <div
            v-if="data.cut_video_url"
            class="download-btn"
            @click.stop="download(data)"
          >
            <img src="@/assets/images/download.png">
            立即下载
          </div>
        </div>
        <!-- <div slot="mark" class="mark-wrap">
          <div class="vdieo-msg-top">
            <div class="status">{{ data.video.vca_status|vcaStatus }}</div>
            <div class="date">{{ data.create_time }}</div>
            <div class="projectName">{{ data.video.dir_name }}</div>
          </div>
          <div class="video-msg-bottom">
            <div class="tags">
              <span
                v-for="(tag,index) in setLabels(data)"
                :key="`${tag}${index}`"
              >{{ tag }}</span>
            </div>
            <div class="time">{{ data.duration | timesToHMS }}</div>
          </div>
        </div> -->
        <div slot="des" class="video-msg">
          <div class="video-title">{{ data.video_title }}</div>
          <div class="desc">
            <p>作者：{{ data.user_name }}</p>
            <p>创建时间：{{ data.create_time }}1</p>
          </div>

        </div>
      </base-card>
    </transition-group>
    <!-- 播放视频弹窗 -->
    <base-dialog
      :show.sync="dialogVisible"
      width="800px"
    >
      <video-player
        ref="videoPlayer"
        :options="playerOptions"
        class="videoPlayer"
      />
    </base-dialog>
  </div>
</template>

<script>
export default {
  components: {
  },
  props: {
    videoData: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      dialogVisible: false,
      playerOptions: {
        height: '360',
        autoplay: false,
        muted: false,
        language: 'en',
        fluid: true,
        sources: [
          {
            type: 'video/mp4',
            src: ''
          }
        ],
        poster: '',
        aspectRatio: '16:9'
      }
    }
  },
  computed: {
    labels() {
      return this.videoData.labels.split(',').filter(function(s) {
        return s && s.trim()
      })
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    async download(obj) {
      const params = {
        uuid: obj.uuid,
        media_type: 'clip'
      }
      const { res, err } = await this.$get('/videos/dl-url', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      window.open(res.data.dl_url, '_blank')
    },
    setLabels(item) {
      return item.video.labels.split(',').filter(function(s) {
        return s && s.trim()
      })
    },
    showVideo(url) {
      if (!url) return
      this.dialogVisible = true
      this.playerOptions.sources[0].src = url
    }
  }
}
</script>

<style scoped lang="scss">
.video-list {
  width: 100%;
  min-height: 300px;

  .transition-wrap {
    display: grid;
    grid-template-columns: repeat(4, 25%);  //同上
    grid-row-gap: 30px;
    margin: 0 -12px;
  }

  .item {
    transition: all 1s;
    margin: 0 12px;

    ::v-deep .image {
      position: relative;
    }

    .mark-status {
      position: absolute;
      width: 100%;
      height: 100%;
      top: 0;
      background: rgba(0, 0, 0, .4);
    }

    .cutStatus {
      background: #530a0a;
      border-radius: 4px;
      color: #fff;
      position: absolute;
      z-index: 9;
      padding: 9px 16px;
      top: 50%;
      left: 50%;
      transform: translate(-50%,-50%);
    }

    .is-cutting {
      opacity: 0;
      animation-timing-function: ease-in-out;
      animation-name: breathe;
      animation-duration: 1250ms;
      animation-iteration-count: infinite;
      animation-direction: alternate;
    }

    .download-btn {
      background: rgba(0,0,0,.6);
      box-shadow: 0px 10px 20px 0px rgba(0, 0, 0, .2);
      border-radius: 4px;
      color: #fff;
      position: absolute;
      right: 10px;
      bottom: 10px;
      z-index: 9;
      font-size: 12px;
      padding: 6px 11px;
      cursor: pointer;
      display: flex;
      align-items: center;

      img {
        display: inline-block;
        width: 14px;
        background: rgba(255, 255, 255, .01);
        box-shadow: 0px 10px 20px 0px rgba(0, 0, 0, .2);
        margin-right: 4px;
      }
    }

    .video-msg {

      .video-title {
        font-size: 14px;
        color: #404040;
        word-break: break-all;
      }

      .desc {
        display: flex;
        font-size: 12px;
        color: #bababa;
        margin-top: 10px;
        flex-wrap: wrap;

        p {
          margin-right: 10px;
          margin-bottom: 10px;
        }
      }

    }

    .mark-wrap {
      display: flex;
      flex-direction: column;
      height: 100%;
      justify-content: space-between;
      padding: 10px;

      .vdieo-msg-top {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        align-items: center;

        .status {
          font-size: 8px;
          color: #fff;
          line-height: 8px;
          border-radius: 2px;
          padding: 4px;
          background: #0a532b;
          margin-right: 10px;
        }

        .date {
          width: 70%;
          font-size: 12px;
          color: #fff;
          line-height: 12px;
        }

        .soure {
          margin-top: 10px;
          font-size: 10px;
          color: #fff;
          line-height: 10px;
          margin-right: 5px;
        }

        .projectName {
          margin-top: 10px;
          font-size: 10px;
          color: #fff;
          line-height: 10px;
        }
      }

      .video-msg-bottom {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        justify-content: space-between;

        .tags {
          display: flex;
          align-items: center;
          flex-wrap: wrap;

          span {
            margin-bottom: 3px;
            font-size: 10px;
            color: #fff;
            line-height: 10px;
            padding: 4px;
            border-radius: 2px;
            background: rgba(0,0,0,.4);
            margin-right: 10px;

            &.search {
              background: #5675e8;
            }
          }
        }

        .time {
          font-size: 12px;
          color: #fff;
          line-height: 12px;
        }
      }
    }

  }

  .list-enter-active,
  .list-leave-active {
    transition: all 1s;
  }

  .list-enter,
  .list-leave-to
/* .list-leave-active for below version 2.1.8 */ {
    opacity: 0;
    transform: translateY(40px);
  }
}
@keyframes breathe {

  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
</style>
