<!--
 * @Author: your name
 * @Date: 2021-08-09 15:44:55
 * @LastEditTime: 2021-08-09 15:45:09
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/live-video/index.vue
-->
<template>
  <div class="container list_flex">
    <div class="content livebox">
      <div class="header list_flex align_items justify_content">
        <div class="box_item">
          <div class="title-line">当前直播</div>
        </div>
        <div class="">
          <el-button v-if="liveinfo.id" type="primary" size="small" @click="liveshare">分享直播</el-button>
          <template v-if="liveinfo.status === 1">
            <el-button v-if="liveinfo.status === 1" type="danger" size="small" @click="updateLivestatus(-1)">停止直播</el-button>
            <el-button v-else type="primary" size="small" @click="updateLivestatus(1)">开启直播</el-button>
          </template>
          <el-button type="primary" size="small" @click="createLive">创建直播</el-button>
        </div>
      </div>
      <div class="videobox">
        <video
          ref="video"
          controls
        />
      </div>
      <div class="list">
        <el-table
          :data="tablelist"
          border
          style="width: 100%"
        >
          <el-table-column
            label="播出时间"
            width="286"
          >
            <template slot-scope="scope">
              <div class="title" v-html="formatVideotime(scope.row, scope.$index)" />
            </template>
          </el-table-column>
          <el-table-column
            label="视频名称"
            width=""
          >
            <template slot-scope="{row}">
              <div class="title">
                <div style="display: box;-webkit-line-clamp: 1;box-orient: vertical;overflow: hidden;white-space: normal;">
                  {{ row.titles }}
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="视频时长"
            width=""
          >
            <template slot-scope="{row}">
              <div class="title">
                {{ checkDuration(row.video_duration) }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="状态"
            width=""
          >
            <template slot-scope="scope">
              <div class="title" v-html="checkStatusLive(scope.row, scope.$index)" />
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div class="content livehistory">
      <div class="header list_flex align_items justify_content">
        <div class="box_item">
          <div class="title-line">直播历史</div>
        </div>
        <div class="">
          <el-checkbox v-model="zhiboEnd" size="small" label="不显示已结束直播" border @change="zhiboEndChange" />
        </div>
      </div>
      <div class="list">
        <div v-for="(item, index) in livelist.list" :key="index" class="list_flex align_items">
          <div class="timerange">
            {{ item.start_time.substr(0, 16) }} - {{ item.end_time.substr(0, 16) }}
          </div>
          <div class="nums">{{ item.video_count }}条</div>
          <div class="times">{{ Math.floor(item.durations / 60 / 1000) }}分钟</div>
          <div class="status">{{ checkStatus(item) }}</div>
          <!--                    <div @click="delHistoryvideo(item)">删除</div>-->
        </div>
      </div>
      <pagination311
        :source="getlivelist"
        :page="livelist.page"
        :page-size="livelist.limit"
        :total-page="livelist.count"
      />
    </div>
    <el-dialog
      title="分享直播"
      :visible.sync="shareDialog.visible"
      center
      width="660px"
    >
      <div class="list_flex align_items" style="margin-bottom: 20px;">
        <div class="box_flex_1">{{ shareDialog.liveurl }}</div>
        <div style="margin-left: 20px;">
          <el-button v-clipboard:copy="shareDialog.liveurl" v-clipboard:success="onCopy" type="primary" size="small">复制直播地址</el-button>
        </div>
      </div>
      <div style="padding: 0 30%">
        <vue-qr :text="shareDialog.url" :size="600" />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="shareDialog.visible = false">取 消</el-button>
        <el-button type="primary" @click="shareDialog.visible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { timesToHMS, timesToYMD } from '@/utils/filter'
import pagination311 from './components/pagination/index'
import vueQr from 'vue-qr'
export default {
  components: {
    pagination311,
    vueQr
  },
  props: {},
  data() {
    return {
      nowts: new Date().getTime(),
      zhiboEnd: false,
      liveinfo: {
        /* 'id': 2,
        'uuid': '84380446-8d8f-0825-15c2-1e47fbb1e4fe',
        'start_time': '2021-08-11 18:14:00',
        'end_time': '2021-08-11 20:00:00',
        'status': 1,
        'video_count': 4,
        'durations': 1889556,
        'create_time': '2021-08-11 15:06:41',
        'update_time': '2021-08-11 15:06:41',
        'company_id': 8,
        'user_id': 5,
        'contents': '[{"id":33,"titles":"放烟花","tags":"","create_time":"2021-08-11 14:11:50","update_time":"2021-08-11 14:11:50","uuid":"5923951c-6ab4-c4b4-e00f-988fbbb12552","user_id":5,"company_id":8,"dir_id":0,"vca_status":2,"status":1,"video_bos_key":"biz_media/video/8/2021-08-11/k8ZKAxJxP5FC5ZecdBjHnR5tpd8MtPhs.mp4","video_size":7177062,"video_duration":66116,"cover_bos_key":"biz_media/pic/8/2021-08-11/H4he7Y4T27d5McxjQNk3y5dnW5RTK5r6.png","labels":"烟花","video_width":0,"video_height":0,"cover_width":0,"cover_height":0,"cover_url":"https://bj.bcebos.com/v1/tm-test/biz_media/pic/8/2021-08-11/H4he7Y4T27d5McxjQNk3y5dnW5RTK5r6.png","video_url":"https://bj.bcebos.com/v1/tm-test/biz_media/video/8/2021-08-11/k8ZKAxJxP5FC5ZecdBjHnR5tpd8MtPhs.mp4"},{"id":32,"titles":"mda-mg0z626fe9xy1dn7","tags":"","create_time":"2021-08-10 16:46:18","update_time":"2021-08-10 16:46:18","uuid":"ed30a8db-65b7-6ffc-7f51-754f86708c8f","user_id":5,"company_id":8,"dir_id":0,"vca_status":3,"status":1,"video_bos_key":"biz_media/video/8/2021-08-10/6twPi7bHifwNBBe5AC8TXyzCnsXK2yFX.mp4","video_size":7177062,"video_duration":66116,"cover_bos_key":"biz_media/pic/8/2021-08-10/WKb83D4YfpTdFKnseZbJmMD2yetd6ZeK.png","labels":"烟花","video_width":0,"video_height":0,"cover_width":0,"cover_height":0,"cover_url":"https://bj.bcebos.com/v1/tm-test/biz_media/pic/8/2021-08-10/WKb83D4YfpTdFKnseZbJmMD2yetd6ZeK.png","video_url":"https://bj.bcebos.com/v1/tm-test/biz_media/video/8/2021-08-10/6twPi7bHifwNBBe5AC8TXyzCnsXK2yFX.mp4"}]',
        'repeat': 1*/
      },
      tablelist: [],
      livelist: {
        count: 0,
        page: 1,
        limit: 10,
        loading: false,
        list: []
      },
      video: null,
      controls: {
        playing: false
      },
      shareDialog: {
        visible: false,
        url: ''
      },
      nowtsTimeout: null,
      timeInterval: null,
      timeInterval_autostop: null
    }
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() {
    this.getlivelist()
    // 当前直播
    this.getlive()
    this.updatenowts()
  },
  beforeDestroy() {
    this.nowtsTimeout && clearTimeout(this.nowtsTimeout)
    this.timeInterval && clearInterval(this.timeInterval)
    this.timeInterval_autostop && clearInterval(this.timeInterval_autostop)
  },
  methods: {
    timesToYMD(time) {
      return timesToYMD(time).substr(0, 17)
    },
    checkDuration(ts) {
      return timesToHMS(ts)
    },
    checkStatusLive(obj, index) {
      const nowts = this.nowts
      const list = this.tablelist
      let totaltime = 0
      for (var i = 0; i < list.length; i++) {
        const sub = list[i]
        if (i < index) {
          totaltime += sub.video_duration
        } else {
          break
        }
      }
      const starttime = totaltime + new Date(this.liveinfo.start_time).getTime()
      let endtime = starttime + obj.video_duration
      endtime = Math.min(endtime, new Date(this.liveinfo.end_time).getTime())
      let status_str = ''
      if (nowts < endtime) {
        status_str = '<div style="color: #5675e8;">正在直播</div>'
        if (nowts < starttime) {
          status_str = '未播放'
        }
      } else {
        status_str = '已播放'
      }
      return status_str
    },
    formatVideotime(obj, index) {
      const list = this.tablelist
      let totaltime = 0
      for (var i = 0; i < list.length; i++) {
        const sub = list[i]
        if (i < index) {
          totaltime += sub.video_duration
        } else {
          break
        }
      }
      const starttime = totaltime + new Date(this.liveinfo.start_time).getTime()
      let endtime = starttime + obj.video_duration
      endtime = Math.min(endtime, new Date(this.liveinfo.end_time).getTime())
      return this.timesToYMD(starttime) + ' 至 ' + this.timesToYMD(endtime)
    },
    checkStatus(obj) {
      let status_str = ''
      const nowts = this.nowts
      const start_time = new Date(obj.start_time).getTime()
      const end_time = new Date(obj.end_time).getTime()
      // `status` int(11) DEFAULT NULL COMMENT '直播状态 1正常 -1停止-2待转码-3转码中-4转码失败',
      if (obj.status === 1) {
        if (nowts < start_time) {
          status_str = '尚未开始'
        } else if (nowts >= start_time && nowts < end_time) {
          status_str = '正在直播'
          // 视频长度小于直播时间范围 && repeat非循环
          // 强制停止
          const range = end_time - start_time
          if (range > obj.durations && obj.repeat === -1) {
            status_str = '直播结束'
          }
        } else {
          status_str = '直播结束'
        }
      } else if (obj.status === -1) {
        status_str = '直播停止'
      } else if (obj.status === -2) {
        status_str = '待转码'
      } else if (obj.status === -3) {
        status_str = '转码中'
      } else if (obj.status === -4) {
        status_str = '转码失败'
      }
      return status_str
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
    updatenowts() {
      this.nowts = new Date().getTime()
      clearTimeout(this.nowtsTimeout)
      this.nowtsTimeout = setTimeout(() => {
        this.updatenowts()
      }, 1000)
    },
    async updateLivestatus(status) {
      const { err, res } = await this.$put('/virtuallive/' + this.liveinfo.id, { status: status })
      if (res) {
        this.liveinfo.status = status
      }
    },
    async delHistoryvideo(obj) {
      const { err, res } = await this.$deleteRun('/virtuallive/' + obj.id)
    },
    async getlive() {
      const { err, res } = await this.$get('/virtuallive/now')
      if (res) {
        // todo
        // const _ssn = new Date().getTime()
        // res.data.start_time = timesToYMD(_ssn)
        // res.data.end_time = timesToYMD(_ssn + 6000)
        this.liveinfo = res.data
        // 视频长度小于直播时间范围 && repeat非循环
        // 强制停止
        const range = new Date(this.liveinfo.end_time).getTime() - new Date(this.liveinfo.start_time).getTime()
        if (range > this.liveinfo.durations && this.liveinfo.repeat === -1 && this.nowts > new Date(this.liveinfo.start_time).getTime() + this.liveinfo.durations) {
          this.liveinfo.status = -1
        }

        const contents = await this.checkContents()
        this.tablelist = contents
        this.$nextTick(() => {
          this.video = this.$refs.video
          this.setvideo()
          this.videoEvent()
        })
      }
    },
    async getlivelist(page, pageSize) {
      if (page) {
        this.livelist.page = page
      }
      if (pageSize) {
        this.livelist.limit = pageSize
      }
      this.livelist.loading = true
      const params = {
        limit: this.livelist.limit,
        page: this.livelist.page,
        sortby: 'start_time',
        order: 'desc'
      }
      if (this.zhiboEnd) {
        let query = 'end_time__gt:' + timesToYMD(new Date().getTime())
        query += ',repeat:1'
        params['query'] = query
      }
      const { err, res } = await this.$get('/virtuallive', params)
      this.livelist.list = res.data
      this.livelist.count = res.count
      this.livelist.loading = false
    },
    zhiboEndChange() {
      this.getlivelist(1)
    },
    createLive() {
      this.$router.push({ path: '/live-video/create' })
    },
    setvideo() {
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
        if (this.video.currentSrc !== obj.video_url) {
          this.video.setAttribute('src', obj.video_url)
        }
        this.video.currentTime = currenttime > 0 ? currenttime / 1000 : 0
      } else {
        this.video.setAttribute('src', '')
      }
      return obj
    },
    videoEvent() {
      const self = this
      this.timeInterval = setInterval(() => {
        if (this.video.paused) {
          const obj = this.setvideo()
          if (!obj) {
            clearInterval(this.timeInterval)
          }
        }
      }, 3000)
      this.timeInterval_autostop = setInterval(() => {
        if (new Date().getTime() > new Date(this.liveinfo.end_time).getTime()) {
          clearInterval(this.timeInterval_autostop)
          this.video.setAttribute('src', '')
        }
      }, 1000)
      self.video.addEventListener('playing', _playing, false)
      self.video.addEventListener('waiting', _waiting, false)
      self.video.addEventListener('pause', _pause, false)
      self.video.addEventListener('ended', _ended, false)
      self.video.addEventListener('timeupdate', _timeupdate, false)

      // error pause
      function _playing() {
        // console.log('_playing')
      }
      function _waiting() {
        // console.log('_waiting')
      }
      function _pause() {
        // console.log('_pause')
      }
      function _timeupdate() {
        // console.log('_timeupdate')
      }
      function _ended() {
        const obj = self.setvideo()
        if (obj) {
          self.video.play()
        }
      }
    },
    liveshare() {
      if (!this.liveinfo.uuid) {
        return
      }
      let host = process.env.VUE_APP_BASE_API
      host = host.substring(0, host.indexOf('.video') + 6)
      this.shareDialog.liveurl = host + '/open/virtual/live/' + this.liveinfo.uuid
      this.shareDialog.url = location.origin + '/live-video/share' + '?uuid=' + this.liveinfo.uuid
      this.shareDialog.visible = true
    },
    onCopy() {
      this.$message({
        message: '地址复制成功',
        type: 'success'
      })
    }
  }
}
</script>

<style scoped lang="scss">
  @import "./css/index.scss";

  .container {
    font-size: 14px;
    box-sizing: border-box;
    line-height: 1.2;
    padding: 20px 0;
  }

  .content {
    background-color: #fff;
    padding: 20px;
  }

  .title-line {
    font-size: 18px;
    line-height: 1;
    padding-bottom: 10px;
    border-bottom: 2px solid #5675e8;
  }

  .videobox {
    margin-top: 20px;
    position: relative;
    padding-top: 56.25%;

    &:hover {

      .controls {
        display: block;
      }
    }

    video {
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      object-fit: contain;
      vertical-align: bottom;

      &::-webkit-media-controls-timeline {
        display: none;
      }

      &::-webkit-media-controls-current-time-display {
        display: none;
      }

      &::-webkit-media-controls-time-remaining-display {
        display: none;
      }
    }
  }

  .livebox {
    width: 57%;

    .list {
      margin-top: 20px;
    }
  }

  .livehistory {
    width: calc(43% - 24px);
    margin-left: 24px;

    .list {
      padding-top: 10px;

      & > .list_flex {
        background: rgba(216, 216, 216, .2);
        color: #404040;
        margin-top: 10px;
        padding: 20px 15px;
        cursor: pointer;
        transition: all .3s ease;

        &:hover {
          background-color: rgba(86, 117, 232, 1);
          color: #fff;
        }

        .timerange {
          flex: 1;
        }

        .nums {
          width: 80px;
          text-align: center;
          border-left: 1px solid rgba(216, 216, 216, 1);
        }

        .times {
          width: 100px;
          text-align: center;
          border-left: 1px solid rgba(216, 216, 216, 1);
        }

        .status {
          width: 70px;
          text-align: center;
          border-left: 1px solid rgba(216, 216, 216, 1);
        }
      }
    }
  }
</style>
