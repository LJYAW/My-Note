<!--
 * @Author: your name
 * @Date: 2021-02-24 11:25:27
 * @LastEditTime: 2021-12-16 17:02:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/model/TaskKeyFrame.vue
-->
<template>
  <div class="video-content">
    <video-player
      ref="videoPlayer"
      :key="videoUrl"
      class="vjs-custom-skin"
      :options="playerOptions"
      :playsinline="true"
      style="display:none"
      @canplay="onPlayerCanplay($event)"
      @timeupdate="onPlayerTimeupdate($event)"
      @ended="onPlayerEnded($event)"
    />
    <!-- 前五秒 -->
    <div class="video-header">
      <div class="before-task-keyframe">
        <p class="play-btn">
          <i v-if="videoStatus.pre" class=" el-icon el-icon-video-pause" @click="setCurrentVideo('pre')" />
          <i v-else class="el-icon el-icon-video-play" @click="setCurrentVideo('pre')" />
        </p>

        <div
          v-for="(img, index) in firstFiveVideoImg"
          :key="index"
          class="before-img-list"
        >
          <div v-for="item in img.list" :key="item.id" class="before-img">
            <div v-show="isActive(item.currentTime - 6,'Start')" class="mask" />
            <base-image
              :style="imgMinStyle"
              :src="item.imgUrl"
              :class="item.currentTime - 1 === currentTime &&currentVideo==='pre'&& 'active-img'"
              @click.native="changeCurrentTime(item.currentTime-1)"
              @dblclick.native="setTime(item.currentTime - 6,'Start')"
            />
          </div>
        </div>
      </div>
    </div>

    <div class="task-keyframe">
      <div class="title">
        <span>任务开始</span>
        <hr>
      </div>

      <p class="play-btn">
        <i v-if="videoStatus.current" class="el-icon el-icon-video-pause" @click="setCurrentVideo('current')" />
        <i v-else class="el-icon el-icon-video-play" @click="setCurrentVideo('current')" />
      </p>
      <!-- 中间视频 -->
      <div ref="imgContent" class="img-content">
        <div class="wrap">
          <RecycleScroller
            v-slot="{ item }"
            class="scroller"
            :items="videoImg"
            :item-size="122"
          >
            <div class="video-img-list-content">
              <div
                v-for="(obj, index) in item.list"
                :key="index"
                :class="[isActive(obj.currentTime - 1,'Start'), 'img-wrap']"
              >
                <base-image
                  :src="obj.imgUrl"
                  :style="imgStyle"
                  :class="obj.currentTime - 1 === currentTime &&currentVideo==='current'&& 'active-img'"
                  @click.native="changeCurrentTime(obj.currentTime-1)"
                  @dblclick.native="setTime(obj.currentTime - 1,'Start')"
                />
                <i class="el-icon-zoom-in scale" @click.stop="dialogImage(obj.imgUrl)" />

                <div v-show="isActive(obj.currentTime - 1,'Start')" class="mask" />
              </div>
            </div>
          </RecycleScroller>
        </div>
      </div>
      <!-- 后五秒 -->
      <div v-if="lastFiveVideoImg.length">
        <div class="title">
          <hr>
        </div>
        <div class="before-task-keyframe">
          <p class="play-btn">
            <i v-if="videoStatus.suf" class="el-icon el-icon-video-pause" @click="setCurrentVideo('suf')" />
            <i v-else class="el-icon el-icon-video-play" @click="setCurrentVideo('suf')" />
          </p>
          <div
            v-for="(img, index) in lastFiveVideoImg"
            :key="index"
            class="before-img-list"
          >
            <div v-for="item in img.list" :key="item.id" class="before-img">
              <div v-show="isActive(item.currentTime,'End')" class="mask" />
              <base-image
                :style="imgMinStyle"
                :src="item.imgUrl"
                :class="item.currentTime - 1 === currentTime &&currentVideo==='suf'&& 'active-img'"
                @click.native="changeCurrentTime(item.currentTime-1)"
                @dblclick.native="setTime(item.currentTime,'End')"
              />
            </div>
          </div>
        </div>
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
import to from '@/utils/to-promise.js'
import chunk from 'lodash/chunk'
import { mapGetters } from 'vuex'
import { theEvent } from '@/utils/simulate.js'
// import { simulate } from '@/utils/simulate.js'
export default {
  components: {
    RecycleScroller
  },
  props: {
    taskMessage: {
      type: Object,
      default() {
        return {}
      }
    },
    startTime: {
      type: String,
      default() {
        return ''
      }
    },
    endTime: {
      type: String,
      default() {
        return ''
      }
    }
  },
  data() {
    return {
      videoImg: [],
      firstFiveVideoImg: [],
      lastFiveVideoImg: [],
      baseMs: 1000,
      baseStartTime: null,
      baseEndTime: null,
      videoUrl: '',
      fiveSecondsVideoUrl: '',
      videoDuration: 0,
      playerOptions: {
        height: '360',
        autoplay: true,
        muted: false,
        language: 'en',
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [
          {
            type: 'video/mp4',
            // mp4
            src: ''
          }
        ],
        poster:
          'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-1.jpg'
      },
      dialogVisible: false,
      dialogImageUrl: '',
      currentTime: 0,
      // pause: true,
      videoStatus: { pre: false, current: false, suf: false },
      loading: true,
      start: '',
      end: '',
      time: null,
      selectedImg: [],
      imgMinStyle: {
        width: '140px',
        height: '80px'
      },
      imgStyle: {
        width: '210px',
        height: '120px'
      },
      timeArray: [],
      curVideoDuration: null,
      currentVideo: 'current'
    }
  },
  computed: {
    ...mapGetters([
      'sidebar'
    ]),
    player() {
      return this.$refs.videoPlayer.player
    }
  },
  watch: {
    'sidebar.opened': {
      handler(val) {
        this.$nextTick(() => {
          this.setVideoList()
        })
      },
      deep: true
    },
    currentVideo(val) {
      Object.keys(this.videoStatus).forEach(item => {
        this.videoStatus[item] = false
      })
      this.currentTime = 0
      this.videoStatus[val] = true
    }
  },
  beforeDestroy() {
    window.removeEventListener('onresize', this.resizeHandler)
    // simulate()
  },
  async created() {
    // 更改开始时间或结束时间
    this.$bus.$on('timeSlot', (data) => {
      this.start = data.start
      this.end = data.end
    })
    // 重置表单
    this.$bus.$on('resetTime', () => {
      this.selectedImg = []
    })
    // 保存之前选择的开始时间和结束时间
    this.$bus.$on('saveTime', () => {
      this.timeArray.unshift({
        startTime: this.start,
        endTime: this.end
      })
    })
    // 删除之前的选择的开始时间和结束时间
    this.$bus.$on('delete', (index) => {
      this.timeArray.splice(index, 1)
    })
    this.baseStartTime = new Date(this.startTime).getTime()
    this.baseEndTime = new Date(this.endTime).getTime()
    this.curVideoDuration = (new Date(this.endTime).getTime() - new Date(this.startTime).getTime()) / 1000
    await this.setVideoList()
    this.playerOptions.sources[0].src = this.taskMessage.current_audio_url
    // const btns = document.getElementsByClassName('vjs-mute-control')[0]
    // console.log('🚀 ~ file: TaskKeyFrame.vue ~ line 263 ~ created ~ btns', btns)
    // btns.click()
  },
  async mounted() {
    window.addEventListener('resize', this.resizeHandler)
    // this.togglePlay()
    this.toggleSideBar()
    this.setPlayStatus()
  },
  methods: {
    setPlayStatus() {
      theEvent()
      this.player.play()
      const par = this.player.play()
      const btn = document.querySelectorAll('.vjs-big-play-button')[0]
      btn.click()
    },
    onPlayerCanplay(e) {
      console.log('🚀 ~ file: TaskKeyFrame.vue ~ line 274 ~ onPlayerCanplay ~ e', e)
    },
    toggleSideBar() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: true })
    },
    onPlayerTimeupdate(player) {
      this.currentTime = Math.floor(player.currentTime())
    },
    // 播放完成
    onPlayerEnded(player) {
      // 显示播放按钮
      this.videoStatus[this.currentVideo] = false
    },
    resizeHandler: window.lodash.throttle(function() {
      this.setVideoList()
    }, 1000),

    async setVideoList() {
      const {
        current_image_url,
        pre_image_url,
        suf_image_url
      } = this.taskMessage

      const [err, list] = await to(
        this.setImgList('videoImg', current_image_url, this.curVideoDuration)
      )
      this.videoImg = list
      if (err) alert(err)

      const [err2, firstFiveVideoImg] = await to(
        this.setImgList('firstVideoImg', pre_image_url, 5)
      )
      this.firstFiveVideoImg = firstFiveVideoImg
      if (err2) alert(err2)

      const [err3, lastFiveVideoImg] = await to(
        this.setImgList('lastVideoImg', suf_image_url, 5)
      )
      this.lastFiveVideoImg = lastFiveVideoImg
      if (err3) alert(err3)
    },
    // 生成图片，渲染页面
    setImgList(id, img_url, duration) {
      return new Promise((resolve, reject) => {
        this.$nextTick(() => {
          const imgWrapDom = this.$refs.imgContent
          const wrapWidth = imgWrapDom.clientWidth

          const imgSize = Math.floor(wrapWidth / parseInt(this.imgStyle.width))
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
    // 空格控制播放暂停
    togglePlay() {
      const _this = this
      document.onkeydown = function(e) {
        const key = window.event.keyCode
        const nodeName = e.target.nodeName
        if (key === 32 && nodeName !== 'INPUT') {
          e.preventDefault()
          // 进入页面时给video赋值
          if (!_this.currentVideo) {
            _this.currentVideo = 'current'
            _this.playerOptions.sources[0].src = _this.taskMessage.current_audio_url
            return
          }
          _this.$nextTick(() => {
            _this.videoStatus[_this.currentVideo] = !_this.videoStatus[_this.currentVideo]
            _this.videoStatus[_this.currentVideo] ? _this.player.play() : _this.player.pause()
          })
        }
      }
    },
    // 单击，改变当前播放时间
    changeCurrentTime(val) {
      clearTimeout(this.time)
      this.time = setTimeout(() => {
        // 写单击事件执行的逻辑代码
        this.currentTime = val
        this.player.currentTime(val)
      }, 300)
    },
    // 双击,选中给开始时间和结束时间赋值
    setTime(val, type) {
      clearTimeout(this.time)
      if (this.start && this.end) {
        return
      }
      const time = this.$data['base' + type + 'Time'] + val * 1000
      // 选择的时间不能在之前选择的时间范围内
      for (var i = 0; i < this.timeArray.length; i++) {
        const parseHistoryStartTime = new Date(this.timeArray[i].startTime).getTime()
        const parseHistoryEndTime = new Date(this.timeArray[i].endTime).getTime()
        if (time >= parseHistoryStartTime && time <= parseHistoryEndTime) {
          return
        }
      }
      this.selectedStatus(val)
      this.$bus.$emit('setTime', time)
    },
    // 图片选中状态
    selectedStatus(val) {
      this.selectedImg.length < 1 ? this.selectedImg[0] = val : this.selectedImg[1] = val
    },
    selectAcitve(val) {
      if (this.selectedImg.length === 2) {
        return val >= this.selectedImg[0] && val <= this.selectedImg[1]
      }
      if (this.selectedImg.length === 1) {
        return val === this.selectedImg[0]
      }
    },
    isActive(val, type) {
      const currrentTiem = val
      const parseStartTime = new Date(this.start).getTime()
      const parseEndTime = new Date(this.end).getTime()
      const time = this.$data['base' + type + 'Time'] + currrentTiem * 1000
      for (var i = 0; i < this.timeArray.length; i++) {
        const parseHistoryStartTime = new Date(this.timeArray[i].startTime).getTime()
        const parseHistoryEndTime = new Date(this.timeArray[i].endTime).getTime()
        if (time >= parseHistoryStartTime && time <= parseHistoryEndTime) {
          return true
        }
      }
      if (parseStartTime === time && !parseEndTime) {
        return true
      }
      if (parseEndTime && parseEndTime) {
        return time >= parseStartTime && time <= parseEndTime
      }
    },
    dialogImage(url) {
      this.dialogImageUrl = url
      this.dialogVisible = true
    },
    // 点击播放/暂停视频
    setCurrentVideo(name) {
      this.currentVideo = name
      this.videoStatus[name] = !this.videoStatus[name]
      this.playerOptions.sources[0].src = this.taskMessage[name + '_audio_url']
      this.videoUrl = this.taskMessage[name + '_audio_url']
      this.$nextTick(() => {
        this.videoStatus[name] ? this.player.play() : this.player.pause()
        // this.player.pause()
      })
    }
  }
}
</script>

<style scoped lang="scss">
.video-content {
  flex: 1;
  border: 1px solid #eee;
  display: flex;
  flex-direction: column;
  .active-img {
    border: 3px solid #40c42f;
  }
  img {
    display: inline-block;
    width: 110px;
    height: 60px;
    object-fit: fill;
    background-color: #eee;
    margin-right: 3px;
    &.active {
      border: 1px solid red;
    }
  }
  .play-btn {
    position: absolute;
    left: -22px;
  }
  .el-icon{
    font-size: 20px;
    margin-bottom: 5px;
    margin-left: 5px;
    vertical-align: -2px;
    cursor: pointer;
  }
  .before-task-keyframe {
    padding: 5px !important;
    .before-img-list {
      display: flex;
      .before-img {
        position: relative;
        .mask {
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          background: rgba(0, 0, 0, 0.5);
          border: 1px solid cyan;
          pointer-events: none;
        }
      }
    }
    padding: 10px;
  }
  .task-keyframe {
    flex: 1;
    display: flex;
    flex-direction: column;
    .img-content {
      position: relative;
      flex: 1;
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
        }
        .img-wrap {
          position: relative;
          &.test {
            z-index: 99;
          }
          .scale{
            cursor: pointer;
            display: none;
            color: #fff;
            position: absolute;
            top: 6px;
            right: 20px;
            font-size: 16px;
            font-weight: 600;
          }
          &:hover{
            .scale{
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
    .title {
      position: relative;
      text-align: center;
      hr {
        border: 1px dashed #eee;
        position: absolute;
        width: 100%;
        top: 50%;
        margin: 0;
      }
      span {
        position: relative;
        z-index: 9;
      }
    }
    .num {
      padding-left: 10px;
    }
    .task-keyframe-content {
      padding: 10px;
      border-bottom: 1px solid #eee;
      height: calc(100vh - 350px);
      overflow-y: auto;
      & > div {
        display: inline-block;
        position: relative;
        .videoImg {
          width: 110px;
          height: 60px;
        }
      }
    }
  }
  .video-footer {
    padding: 10px;
  }
}
</style>
