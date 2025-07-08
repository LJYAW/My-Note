<template>
  <div class="home311">
    <video-player
      v-if="playerShow"
      ref="videoPlayer"
      :options="playerOptions"
      class="videoPlayer"
      @ended="onEnded"
      @pause="onPause"
      @play="onPlay"
    />
    <div v-if="liveError" class="errortext">
      当前没有直播
    </div>
  </div>
</template>

<script>
export default {
  components: {
  },
  data: function() {
    return {
      uuid: this.$route.query['uuid'] || '',
      nowts: new Date().getTime(),
      tablelist: [],
      liveinfo: {},

      liveError: false,

      player: null,
      playerOptions: {
        muted: false,
        language: 'en',
        fluid: true,
        sources: [
          {
            type: 'application/x-mpegURL',
            src: ''
          }
        ],
        poster: '',
        controlBar: {
          currentTimeDisplay: false, // 当前时间
          timeDivider: false, // 时间分割线
          durationDisplay: false, // 总时间
          progressControl: false // 进度条
        }
      },
      playerShow: false,
      paused: true
    }
  },
  computed: {

  },
  mounted: function() {
    this.init()
  },
  methods: {
    async init() {
      const { err, res } = await this.$get('/virtuallive/uuid/' + this.uuid)
      if (res) {
        this.liveinfo = res.data
        const nowtimestamp = new Date().getTime()
        if (nowtimestamp > new Date(this.liveinfo.start_time).getTime() && nowtimestamp < new Date(this.liveinfo.end_time).getTime()) {
          this.checkInfo()
          this.updatenowts()
        } else {
          this.liveError = true
        }
      }
      if (err) {
        this.liveError = true
      }
    },
    updatenowts() {
      this.nowts = new Date().getTime()
      setTimeout(() => {
        this.updatenowts()
      }, 1000)
    },
    async checkInfo() {
      // 视频长度小于直播时间范围 && repeat非循环
      // 强制停止
      const range = new Date(this.liveinfo.end_time).getTime() - new Date(this.liveinfo.start_time).getTime()
      if (range > this.liveinfo.durations && this.liveinfo.repeat === -1 && this.nowts > new Date(this.liveinfo.start_time).getTime() + this.liveinfo.durations) {
        this.liveinfo.status = -1
        this.liveError = true
        return
      }

      const contents = await this.checkContents()
      this.tablelist = contents
      this.playerShow = true
      this.$nextTick(() => {
        this.setvideo()
        // this.videoEvent()
      })
    },
    checkContents() {
      return new Promise(resolve => {
        const contents = JSON.parse(this.liveinfo.contents)
        const contents_stamp = [...contents]
        let index_stamp = 0
        const range = new Date(this.liveinfo.end_time).getTime() - new Date(this.liveinfo.start_time).getTime()
        if (range > this.liveinfo.durations && this.liveinfo.repeat === 1) {
          let durations = this.liveinfo.durations
          const _add = () => {
            durations += contents_stamp[index_stamp]['video_duration']
            if (durations < range) {
              contents.push(contents_stamp[index_stamp])
              index_stamp++
              if (index_stamp > contents_stamp.length - 1) {
                index_stamp = 0
              }
              _add()
            } else {
              resolve(contents)
            }
          }
          _add()
        } else {
          resolve(contents)
        }
      })
    },
    setvideo() {
      let host = process.env.VUE_APP_BASE_API
      host = host.substring(0, host.indexOf('.video') + 6)
      this.playerOptions.sources[0]['src'] = host + '/open/virtual/live/' + this.uuid
      // this.playerOptions.sources[0]['src'] = 'http://tapi-yun.tm.video/open/virtual/live/f7354b37-2def-0d1c-aae3-b604b1ce6f7c'
    },
    setvideo2() {
      const list = this.tablelist
      const nowts = this.nowts
      let totaltime = 0
      let obj = null
      let currenttime = 0 // 毫秒
      const starttime = new Date(this.liveinfo.start_time).getTime()
      for (var i = 0; i < list.length; i++) {
        const sub = list[i]
        if (nowts > starttime + totaltime && nowts < starttime + totaltime + sub.video_duration) {
          obj = sub
          currenttime = nowts - (starttime + totaltime)
          break
        }
        totaltime += sub.video_duration
      }
      if (obj) {
        if (this.playerOptions.sources[0]['src'] !== obj.video_url) {
          this.playerOptions.sources[0]['src'] = obj.video_url
        }
        setTimeout(() => {
          this.$refs.videoPlayer.player.currentTime(currenttime > 0 ? currenttime / 1000 : 0)
        }, 300)
      } else {
        this.playerOptions.sources[0]['src'] = ''
      }
      return obj
    },
    onEnded() {
      /*
      const obj = this.setvideo()
      if (obj) {
        this.$refs.videoPlayer.player.play()
      }
      */
    },
    onPause() {
      this.paused = true
    },
    onPlay() {
      this.paused = false
    },
    videoEvent() {
      const timeInterval = setInterval(() => {
        if (this.paused) {
          const obj = this.setvideo()
          if (!obj) {
            clearInterval(timeInterval)
          }
        }
      }, 3500)
      const timeInterval_autostop = setInterval(() => {
        if (new Date().getTime() > new Date(this.liveinfo.end_time).getTime()) {
          clearInterval(timeInterval_autostop)
          this.playerOptions.sources[0]['src'] = ''
          this.playerShow = false
          this.liveError = true
        }
      }, 1000)
    },

    loadScript: function(_src, _reffunc) {
      var localvaljs = document.createElement('script')
      localvaljs.type = 'text/javascript'
      localvaljs.async = true
      localvaljs.onload = function() {
        _reffunc && _reffunc()
      }
      localvaljs.src = _src
      var s = document.getElementsByTagName('script')[0]
      s.parentNode.insertBefore(localvaljs, s)
    },
    setwxshare: function() {
      var self = this
      const wx = window.wx
      function _wxready(_shareInfo) {
        wx.ready(function() {
          wx.onMenuShareAppMessage(_shareInfo)
          wx.onMenuShareTimeline(_shareInfo)
          wx.onMenuShareQQ(_shareInfo) // 分享到QQ
          wx.onMenuShareQZone(_shareInfo) // 分享到QQ空间
          wx.onMenuShareWeibo(_shareInfo) // 分享到腾讯微博
        })
      }

      var getWX = function($data) {
        self.loadScript('//res.wx.qq.com/open/js/jweixin-1.0.0.js', function() {
          wx.config({
            debug: false,
            appId: $data.AppId,
            timestamp: $data.Timestamp,
            nonceStr: $data.NonceStr,
            signature: $data.Signature,
            jsApiList: [
              'checkJsApi',
              'onMenuShareTimeline',
              'onMenuShareAppMessage',
              'onMenuShareQQ',
              'onMenuShareWeibo',
              'onMenuShareQZone',
              'hideMenuItems',
              'showMenuItems',
              'hideAllNonBaseMenuItem',
              'showAllNonBaseMenuItem',
              'translateVoice',
              'startRecord',
              'stopRecord',
              'onVoiceRecordEnd',
              'playVoice',
              'onVoicePlayEnd',
              'pauseVoice',
              'stopVoice',
              'uploadVoice',
              'downloadVoice',
              'chooseImage',
              'previewImage',
              'uploadImage',
              'downloadImage',
              'getNetworkType',
              'openLocation',
              'getLocation',
              'hideOptionMenu',
              'showOptionMenu',
              'closeWindow',
              'scanQRCode',
              'chooseWXPay',
              'openProductSpecificView',
              'addCard',
              'chooseCard',
              'openCard'
            ]
          })
          const shareInfo = {}
          _wxready(shareInfo)
        })
      }
      /*
      service.getSignPackage().then(function (res) {
        getWX(res.data)
      })
      */
    }
  }
}
</script>
<style type="text/scss" lang="scss">
  .home311 {

    .videoPlayer {
      width: 100%;
      height: 100%;
    }

    .vjs-fluid {
      padding-top: 0!important;
      height: 100% !important;
    }

    .video-js {

      .vjs-big-play-button {
        top: 50%;
        left: 50%;
        transform: translateX(-50%) translateY(-50%);
      }
    }
  }
</style>
<style type="text/scss" scoped lang="scss">
  .home311 {
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: #000;

    .errortext {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translateX(-50%) translateY(-50%);
      color: #fff;
    }
  }
</style>
