<!--
 * @Author: your name
 * @Date: 2021-07-28 10:19:59
 * @LastEditTime: 2021-09-30 15:36:51
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/videoList/videoItem.vue
-->
<template>
  <div class="video-item">
    <div class="video-poster" @mouseleave.self="hideVideo()" @mouseenter.self="showVideo()">
      <slot name="cutStatus" />
      <slot name="download" />
      <!-- 视频播放 -->
      <div v-if="videoShow && !showCheckbox" class="video">
        <Vloading v-if="videoLoading" />
        <video
          ref="video"
          :src="videoData.video_url"
          :preload="false"
          autoplay
          muted
        />
      </div>
      <!-- 缩略图 -->
      <div class="poster">
        <!-- <ImgSkeleton :img-src="videoData.cover_url" alt="" /> -->
        <base-image v-show="!videoShow||videoLoading" :src="videoData.cover_url" alt="" />
      </div>

      <!-- 分享选择 -->
      <div v-if="showCheckbox&&isvca" class="share-wrap">
        <el-checkbox v-model="videoSelectFlag" label="" :indeterminate="false" @change="handlerChange" />
      </div>

      <!-- hover展示的视频信息 -->
      <div v-if="!showCheckbox" class="video-msg" @click="goVideoDetail()">
        <div class="vdieo-msg-top">
          <div class="status">{{ videoData.vca_status|vcaStatus }}</div>
          <div class="date">{{ videoData.create_time }}</div>
          <!-- <div class="soure">本地上传</div> -->
          <div class="projectName">{{ videoData.dir_name }}</div>
        </div>
        <!-- 收藏 -->
        <div v-if="collection" :class="['fav',vdata.fav_id?'act':'']" @click.stop="fav">
          <svg-icon icon-class="形状" class="svg" />
        </div>
        <div class="video-msg-bottom">
          <div class="tags">
            <span
              v-for="(tag,index) in cardTag"
              :key="tag+index"
              :class="[{'search':tag === searchWord}]"
            >{{ tag }}</span>
          </div>
          <div class="time">{{ videoData.video_duration | timesToHMS }}</div>
        </div>
      </div>

    </div>
    <!-- 视频标题 -->
    <div class="video-desc">
      <span v-html="highLight(videoData.titles)" />
      <slot name="videoMsg" />
    </div>
  </div>
</template>

<script>
import { highLightMsg } from '@/utils/hightLight.js'
import Vloading from '@/components/VLoading'
import ImgSkeleton from '@/components/ImageSkeleton'

export default {
  components: {
    Vloading
    // ImgSkeleton
  },
  props: {
    videoData: {
      type: Object,
      default: () => {}
    },
    cutVideoUrl: {
      type: String,
      default() {
        return ''
      }
    },
    collectionVideoData: {
      type: Object,
      default: () => {}
    },
    collection: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      vdata: JSON.parse(JSON.stringify(this.videoData)),
      // 封面图显示
      // posterShow: false,
      // 是否显示播放当前视频
      videoShow: false,
      // 视频相关配置
      videoSrc: '',
      // 是否分享状态
      showCheckbox: false,
      // 是否已选择当前视频
      videoSelectFlag: false,
      videoLoading: false,
      vcaFlag: false,
      setTimeOut: null
    }
  },
  computed: {
    tags() {
      return this.videoData.tags.split(',').filter(function(s) {
        return s && s.trim()
      })
    },
    labels() {
      const arr = this.videoData.labels.split(',').filter(function(s) {
        return s && s.trim()
      })
      return arr.splice(0, 5)
    },

    cardTag() {
      var arr = []
      arr = this.tags.concat(this.labels)
      return arr.splice(0, 3)
    },
    searchWord() {
      return this.$store.state.videoStatus.searchWord
    },
    isvca() {
      let flag = true
      if (this.vcaFlag) {
        flag = this.videoData.vca_status === 2
      }
      return flag
    }
  },
  watch: {
    '$store.state.videoStatus.batchVideo': {
      handler: function(val) {
        this.videoSelectFlag = this.$route.fullPath === '/my-collection'
          ? val.includes(this.collectionVideoData.id) : val.includes(this.videoData.uuid)
      },
      deep: true
    }
  },
  created() {
    this.$bus.$on('batchFlag', (type, vcaFlag) => {
      this.showCheckbox = type
      this.vcaFlag = vcaFlag
    })
  },
  mounted() {

  },
  methods: {
    loadImg() {
      this.posterShow = true
    },
    async fav() {
      const { uuid, fav_id } = this.vdata
      const { err, res } = fav_id ? await this.$deleteRun(`/favorites/${fav_id}`) : await this.$post('/favorites/', { video_uuid: `${uuid}` })
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.vdata.fav_id = res.data ? res.data.id : 0
    },
    highLight(item) {
      return highLightMsg(item, this.searchWord)
    },
    hideVideo() {
      this.videoShow = false
      clearTimeout(this.setTimeOut)
    },
    showVideo() {
      this.setTimeOut = setTimeout(() => {
        if (this.isCutVideo && !this.cutVideoUrl) return
        this.videoShow = true
        this.videoLoading = true
        this.$nextTick(() => {
          const dom = this.$refs.video
          if (!dom) return
          dom.addEventListener('canplaythrough', () => {
            this.videoLoading = false
          })
        })
      }, 1000)
    },
    handlerChange(e) {
      if (this.$route.fullPath === '/my-collection') {
        this.handlerCollectionChange(e)
        return
      }
      const arr = this.$store.state.videoStatus.batchVideo
      if (e) {
        !arr.includes(this.videoData.uuid) && arr.push(this.videoData.uuid)
      } else {
        var index = arr.indexOf(this.videoData.uuid)
        index > -1 && arr.splice(index, 1)
      }
      this.$store.commit('videoStatus/BATCH_VIDEO_CHANGE', arr)
    },
    handlerCollectionChange(e) {
      const arr = this.$store.state.videoStatus.batchVideo
      if (e) {
        !arr.includes(this.collectionVideoData.id) && arr.push(this.collectionVideoData.id)
      } else {
        var index = arr.indexOf(this.collectionVideoData.id)
        index > -1 && arr.splice(index, 1)
      }
      this.$store.commit('videoStatus/BATCH_VIDEO_CHANGE', arr)
    },
    goVideoDetail() {
      this.$emit('goDetail', this.videoData.uuid, this.videoData.dir_id)
    }
  }
}
</script>

<style scoped lang="scss">
.video-item {
  position: relative;
  // width: 342px;
  // max-width: 23.5%;
  // margin-right: 24px;
  // margin-bottom: 30px;
  flex-shrink: 0;

  // &:nth-child(4n+4) {
  //   margin-right: 0;
  // }

  .fav {
    position: absolute;
    right: 20px;
    top: 15px;
    cursor: pointer;
    color: #fff;

    &.act {
      color: red;
    }
  }

  &:hover {

    .video-poster {
      transform: translateY(-5px);
      box-shadow: 0px 10px 20px 0px rgba(0, 0, 0, .2);

      .video-msg {
        opacity: 1;
      }
    }
  }

  .video-poster {
    position: relative;
    width: 100%;
    height: 0;
    padding-bottom: 56.25%;
    background: #000;
    border-radius: 4px;
    overflow: hidden;
    transition: all .4s;

    .video {
      position: absolute;
      left: 0;
      top: 0;
      z-index: 2;
      width: 100%;
      height: 100%;

      video {
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        // object-fit: cover;
      }
    }

    .poster {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: #c7c2c2;

      :v-deep .el-img {
        position: static;
        width: 100%;
        height: 100%;
        top: 0;
        // object-fit: cover;
      }
    }

    .share-wrap {
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0;
      left: 0;
      background: rgba(0,0,0,.4);
      display: flex;
      justify-content: flex-end;
      box-sizing: border-box;
      padding: 10px;

      .el-checkbox {
        width: 100%;
        height: 100%;

        ::v-deep.el-checkbox__input {
          position: absolute;
          right: 0;
        }

        ::v-deep.el-checkbox__inner {
          border-radius: 50%;
          overflow: hidden;
        }
      }

    }

    .video-msg {
      opacity: 0;
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0;
      left: 0;
      z-index: 3;
      // background: rgba(0,0,0,.4);
      // box-shadow: inset 0px 30px 30px -20px #000,
      //   inset 0px -30px 30px -20px #000;

      transition: all 1s;
      display: flex;
      flex-direction: column;
      justify-content: space-between;

      .vdieo-msg-top {
        height: 40px;
        padding-top: 10px;
        padding-left: 20px;
        padding-right: 40px;
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        background: linear-gradient(180deg, #000 0%, rgba(0, 0, 0, .6) 50%, rgba(0, 0, 0, 0) 100%);

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
          // width: 70%;
          font-size: 12px;
          color: #fff;
          line-height: 12px;
        }

        .soure {
          // margin-top: 10px;
          font-size: 10px;
          color: #fff;
          line-height: 10px;
          margin-right: 5px;
        }

        .projectName {
          margin-left: 10px;
          // margin-bottom: 10px;
          width: 60px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
          font-size: 10px;
          color: #fff;
          line-height: 20px;
        }
      }

      .video-msg-bottom {
        height: 40px;
        padding-left: 20px;
        padding-right: 20px;
        // padding-bottom: 10px;
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        justify-content: space-between;
        background: linear-gradient(360deg, #000 0%, rgba(0, 0, 0, .6) 50%, rgba(0, 0, 0, 0) 100%);

        .tags {
          display: flex;
          align-items: center;
          // flex-wrap: wrap;
          width: 70%;
          height: 100%;
          overflow: hidden;

          span {
            max-width: 30%;
            flex-shrink: 0;
            margin-bottom: 3px;
            font-size: 10px;
            color: #fff;
            line-height: 10px;
            padding: 4px;
            border-radius: 2px;
            background: rgba(0,0,0,.4);
            margin-right: 10px;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;

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

  .video-desc {
    margin-top: 10px;
    font-size: 14px;
    font-weight: 600;
    color: #404040;
    line-height: 18px;
    word-break: break-all;//只对英文起作用，以字母作为换行依据
    word-wrap: break-word; //只对英文起作用，以单词作为换行依据
    white-space: pre-wrap; //只对中文起作用，强制换行
  }
}

</style>
