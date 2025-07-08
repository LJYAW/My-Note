<!--
 * @Author: your name
 * @Date: 2021-12-13 16:19:33
 * @LastEditTime: 2021-12-23 15:35:23
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /production_web/src/views/EPG/new-system-pad/right-pad.vue
-->
<template>
  <div class="left-pad-wrap">

    <video-player
      ref="videoPlayer"
      :key="videoUrl"
      class="vjs-custom-skin"
      :options="playerOptions"
      :playsinline="true"
      style="display: none"
      @play="play($event)"
      @pause="onPlayerPause($event)"
      @canplay="onPlayerCanplay($event)"
      @timeupdate="onPlayerTimeupdate($event)"
      @ended="onPlayerEnded($event)"
    />

    <div class="btn-lsit">
      <el-tooltip class="item" effect="dark" content="快捷建为空格" placement="top">
        <i v-if="videoStatus != 'play'" class="icon el-icon-video-play" @click="playVideo" />
        <i v-if="videoStatus === 'play'" class="icon el-icon-video-pause" @click="playPause" />
      </el-tooltip>

      <div class="start-btn btn">
        <el-tooltip class="item" effect="dark" content="快捷建为F1" placement="top">
          <el-button type="primary" plain size="mini" icon="el-icon-house" @click="setTimeByBtn('start', 1)">切正片开始</el-button>
        </el-tooltip>
      </div>

      <div class="end-btn btn">
        <el-tooltip class="item" effect="dark" content="快捷建为F2" placement="top">
          <el-button type="warning" plain size="mini" icon="el-icon-house" @click="setTimeByBtn('end', 1)">切正片结束</el-button>
        </el-tooltip>
      </div>

      <div class="start-btn btn">
        <el-tooltip class="item" effect="dark" content="快捷建为F3" placement="top">
          <el-button type="danger" title="广告开始" plain size="mini" icon="el-icon-house" @click="setTimeByBtn('start', 2)">切广告开始</el-button>
        </el-tooltip>
      </div>

      <div class="end-btn btn">
        <el-tooltip class="item" effect="dark" content="快捷建为F4" placement="top">
          <el-button type="danger" title="广告结束" plain size="mini" icon="el-icon-house" @click="setTimeByBtn('end', 2)">切广告结束</el-button>
        </el-tooltip>
      </div>
    </div>

    <div ref="imgContent" class="img-content">
      <div class="wrap">
        <RecycleScroller
          v-slot="{ item }"
          ref="scrollerWrap"
          class="scroller"
          :items="videoImg"
          :item-size="124"
        >
          <div class="video-img-list-content">
            <div
              v-for="(obj) in item.list"
              :key="`currentTime_${obj.currentTime - 1}`"
              :class="['img-card', isActive(obj.currentTime - 1)]"
            >
              <base-image
                :src="obj.imgUrl"
                :style="imgStyle"
                :class="obj.currentTime - 1 === currentTime && 'active-img'"
                @click.native="changeCurrentTime(obj.currentTime - 1)"
              />
              <!-- @dblclick.native="setTime(obj.currentTime - 1)" -->

              <i
                class="el-icon-zoom-in scale"
                @click.stop="dialogImage(obj.imgUrl)"
              />

              <!-- <div
                v-show="isActive(obj.currentTime - 1)"
                class="mask"
              /> -->
            </div>
          </div>
        </RecycleScroller>
      </div>
    </div>

    <el-dialog :visible.sync="dialogVisible">
      <img style="width:100%;height:auto;" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import { RecycleScroller } from 'vue-virtual-scroller'
import 'vue-virtual-scroller/dist/vue-virtual-scroller.css'
import chunk from 'lodash/chunk'
import dateFun from '@/utils/time.js'
export default {
  components: {
    RecycleScroller
  },
  props: {
    epgData: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      videoUrl: '',
      dialogVisible: false,
      imgSize: 0,
      playerOptions: {
        height: '360',
        autoplay: false,
        muted: false,
        language: 'en',
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [
          {
            type: 'video/mp4',
            src: ''
          }
        ],
        poster:
          'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-1.jpg'
      },
      dialogImageUrl: '',
      curVideoDuration: 0,
      videoImg: [],
      time: null,
      currentTime: 0,
      videoStatus: '',
      imgStyle: {
        width: '210px',
        height: '120px'
      }
    }
  },
  computed: {
    cutTimeList() {
      return this.$store.state.newEpgEdit.cutTimeList
    },
    player() {
      return this.$refs.videoPlayer.player
    }
  },
  watch: {
  },
  created() {
    this.toggleSideBar()
    this.setBaseData()
    this.keydowonFn()
  },
  mounted() {
    this.$bus.$on('rowClick', this.setCurrentTime)
    window.addEventListener('resize', this.resizeHandler)
  },
  methods: {
    setCurrentTime(index) {
      console.log('🚀 ~ file: left-pad.vue ~ line 169 ~ setCurrentTime ~ index', index)
      const time = this.cutTimeList[index].time
      this.currentTime = time
      this.videoStatus === 'play' && this.player.currentTime(time)
      // this.$refs.scrollerWrap.scrollTop = 200
      const itemIndex = Math.max(Math.floor(time / this.imgSize), 0)
      this.$refs.scrollerWrap.scrollToItem(itemIndex)

      console.log('🚀 ~ file: left-pad.vue ~ line 175 ~ setCurrentTime ~ this.$refs.scrollerWrap', this.$refs.scrollerWrap)
    },
    resizeHandler: window.lodash.throttle(function() {
      this.setBaseData()
    }, 1000),
    async setBaseData() {
      this.curVideoDuration = (new Date(this.epgData.end_time).getTime() - new Date(this.epgData.start_time).getTime()) / 1000

      const list = await this.setImgList(
        'videoImg',
        this.epgData.task_generate_files.image_url,
        this.curVideoDuration
      )
      this.videoImg = list
      this.playerOptions.sources[0].src = this.epgData.task_generate_files.audio_url
      this.videoUrl = this.epgData.task_generate_files.audio_url
    },
    // 生成图片，渲染页面
    setImgList(id, img_url, duration) {
      return new Promise((resolve, reject) => {
        this.$nextTick(() => {
          const imgWrapDom = this.$refs.imgContent
          const wrapWidth = imgWrapDom.clientWidth

          const imgSize = Math.floor(wrapWidth / parseInt(this.imgStyle.width))
          this.imgSize = imgSize
          let arr = []

          if (img_url) {
            for (let i = 1; i < duration + 1; i++) {
              const imgUrl = img_url.replace(/%d/g, i)
              const obj = {
                currentTime: i,
                imgUrl: imgUrl,
                id: i + 'img' + id
              }
              arr.push(obj)
            }

            arr = chunk(arr, imgSize)
            const chunkArr = []
            arr.forEach((item, index) => {
              chunkArr.push({
                id: `img_${index}`,
                list: item
              })
            })
            resolve(chunkArr)
          }
        })
      })
    },
    // 单击，改变当前播放时间
    changeCurrentTime(val) {
      clearTimeout(this.time)

      // this.time = setTimeout(() => {
      // 写单击事件执行的逻辑代码
      this.currentTime = val
      this.player.currentTime(val)
      this.player.pause()
      // }, 300)
    },
    dialogImage(url) {
      this.dialogImageUrl = url
      this.dialogVisible = true
    },
    playVideo() {
      this.player.play()
    },
    playPause() {
      this.player.pause()
    },
    setTimeByBtn(key, type) {
      // type 1 为正片 2位广告
      const isAready = this.cutTimeList.some(item => item.time === this.currentTime)
      if (isAready) {
        this.$message({
          message: '不能重复打点',
          type: 'success'
        })
        return
      }
      const obj = {
        type: type,
        time: this.currentTime,
        key: key,
        timeStr: '',
        dateTimeMs: ''
      }

      const dateStar = new Date(this.epgData.start_time).getTime() + this.currentTime * 1000
      obj.timeStr = dateFun.convert(dateStar, 'y-m-d h:m:s')
      obj.dateTimeMs = dateStar

      // this.cutTimeList.push(obj)
      this.$store.commit('newEpgEdit/PUSH_CUT_TIME_LIST', obj)
    },
    isActive(curTime) {
      let active = ''

      for (var i = 0; i < this.cutTimeList.length; i++) {
        const { time, key, type } = this.cutTimeList[i]
        if (curTime === time) {
          active = key + '_' + type
          break
        }
      }
      return active
    },
    toggleSideBar() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: true })
    },
    play() {
      this.videoStatus = 'play'
    },
    onPlayerPause() {
      this.videoStatus = 'pause'
    },
    onPlayerCanplay() {},
    onPlayerTimeupdate(player) {
      this.currentTime = Math.floor(player.currentTime())
    },
    onPlayerEnded() {
      // this.videoStatus[this.currentVideo] = false
    },
    keydowonFn() {
      document.onkeydown = (e) => {
        var keycode = window.event.keyCode
        const keyArr = [112, 113, 114, 115, 32]

        if (keyArr.includes(keycode)) {
          if (e.preventDefault) {
            e.preventDefault()//
          } else {
            window.event.returnValue = false
          }
        }

        switch (keycode) {
          //  F1
          case 112:
            this.setTimeByBtn('start', 1)
            break
            //  F2
          case 113:
            this.setTimeByBtn('end', 1)
            break
            //  F3
          case 114:
            this.setTimeByBtn('start', 2)
            break
            //  F4
          case 115:
            this.setTimeByBtn('end', 2)
            break
            // 空格
          case 32:
            this.videoStatus !== 'play' ? this.playVideo() : this.playPause()
            break
        }
      }
    }
  }
}
</script>

<style scoped lang="scss">
.left-pad-wrap {
  width: 100%;
  height: 100%;
  background: #E4EBF5;

  .btn-lsit {
    font-size: 25px;
    color: #232323;
    display: flex;
    justify-content: center;
    padding-top: 10px;

    .btn {
      display: flex;
      justify-content: center;
      align-items: center;
      margin: 0 5px;
      border-radius: 5px;
      cursor: pointer;
      ::v-deep .el-icon-house {
      transform: rotate(180deg);
    }

      .icon {
        font-size: 14px;
      }
    }
    .text {
      font-size: 12px;
    }
    .icon {
      cursor: pointer;
      transition: all 0.3;
      margin-right: 2px;
    }
  }
}
.img-content {
  position: relative;
  width: 100%;
  height: calc(100% - 50px);

  .wrap {
    padding: 5px;
    overflow: hidden;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    .scroller {
      width: 100%;
      height: 100%;
    }
    .video-img-list-content {
      display: flex;
      .el-image {
        transition: all 0.1s;
        background-color: #409EFF;
      }
      .active-img {
        border: 4px solid #409EFF !important;
        transform: scale(0.95);
      }
      .img-card {
        position: relative;
        .el-icon-zoom-in {
          display: none;
          position: absolute;
          right: 10px;
          top: 10px;
          font-size: 16px;
          color: #fff;
          font-weight: 500;
          cursor: pointer;
        }
        &.start_1 {
          &::after {
            content: '正片开始';
            color: cyan;
          }
          .el-image {
            border: 4px solid cyan;
            border-radius: 15px;
          }
        }
        &.end_1 {
          &::after {
            content: '正片结束';
            color: cyan;
          }
          .el-image {
            border: 4px solid cyan;
            border-radius: 15px;
          }
        }
        &.start_2 {
          &::after {
            content: '广告开始';
            color: #DC2537;
          }
          .el-image {
            border: 4px solid #DC2537;
            border-radius: 15px;
          }
        }
        &.end_2 {
          &::after {
            content: '广告结束';
            color: #DC2537;
          }
          .el-image {
            border: 4px solid #DC2537;
            border-radius: 15px;
          }
        }
        &::after {
            display: block;
            position: absolute;
            top: 10px;
            right: 10px;
            font-size: 14px;
            font-weight: 600;
        }
        &:hover {
          .el-icon-zoom-in {
            display: block;
            z-index: 2002;
          }
        }
      }

      .img-wrap {
        position: relative;
        .mask {
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          background: rgba(0, 0, 0, 0.6);
          border: 1px solid cyan;
          pointer-events: none;
        }
      }

    }
    .img-wrap {
      position: relative;
      border-radius: 4px;
      overflow: hidden;
      &.test {
        z-index: 99;
      }
      .scale {
        cursor: pointer;
        display: none;
        color: #fff;
        position: absolute;
        top: 6px;
        right: 20px;
        font-size: 16px;
        font-weight: 600;
      }
      &:hover {
        .scale {
          display: block;
          z-index: 12;
        }
      }
      .mask {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        box-sizing: border-box;
        background: rgba(0, 0, 0, 0.5);
        border: 2px solid rgb(231, 9, 9);
        pointer-events: none;
      }
    }
  }
}
</style>
