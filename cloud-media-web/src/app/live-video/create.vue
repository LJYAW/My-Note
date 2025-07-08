<template>
  <div class="container list_flex">
    <div class="content livebox">
      <div class="header list_flex align_items justify_content">
        <div class="box_item">
          <div class="title-line">直播设置</div>
        </div>
      </div>
      <div class="list_flex align_items" style="margin-top: 20px;">
        <div style="margin-right: 8px;">直播时间段</div>
        <div class="box_item" style="margin-right: 8px;">
          <el-date-picker
            v-model="tablelist.range"
            style="width: 350px;"
            type="datetimerange"
            size="small"
            format="yyyy-MM-dd HH:mm"
            value-format="timestamp"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :disabled="tablelist.disabled"
            :picker-options="pickerOptions"
            @change="tablelist.disabled = true"
          />
        </div>
        <div>
          <el-checkbox v-model="tablelist.loop" size="small" label="重复播放" border />
        </div>
      </div>
      <div class="list">
        <el-table
          :data="tablelist.list"
          border
          style="width: 100%"
        >
          <el-table-column
            label="播出时间"
            width="286"
          >
            <template slot-scope="scope">
              <div class="title" v-html="formatVideotime(scope.row,scope.$index)" />
            </template>
          </el-table-column>
          <el-table-column
            label="视频名称"
            width=""
          >
            <template slot-scope="{row}">
              <div class="title">
                {{ row.titles }}
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
            label="操作"
            width=""
          >
            <template slot-scope="scope">
              <template v-if="scope.$index > 0">
                <el-button type="text" size="small" @click="editorAction(scope.row,scope.$index,'up')">上移</el-button>
              </template>
              <template v-if="scope.$index < tablelist.list.length - 1">
                <el-button type="text" size="small" @click="editorAction(scope.row,scope.$index,'down')">下移</el-button>
              </template>
              <el-button type="text" size="small" @click="editorAction(scope.row,scope.$index,'del')">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="list_flex align_items justify_content" style="margin-top: 20px;">
        <div class="box_item">
          <div v-if="tablelist.range.length">
            直播总时长：{{ Math.floor((tablelist.range[1] - tablelist.range[0]) / 60 / 1000) }}分钟
          </div>
          <template v-if="tablelist.list.length">
            <div style="margin-top: 10px;">
              视频总时长：{{ totalDuration(tablelist.list) }}分钟
            </div>
            <div style="margin-top: 10px;">
              视频数：{{ tablelist.list.length }}条
            </div>
          </template>
          <div v-if="tablelist.range.length">
            <div style="margin-top: 10px;">
              直播时间：{{ timesToYMD(tablelist.range[0]) }} - {{ timesToYMD(tablelist.range[1]) }}
            </div>
          </div>
        </div>
        <div class="">
          <el-button type="" size="small" @click="clearAll">清空</el-button>
          <el-button type="primary" size="small" :disabled="!(tablelist.range.length && tablelist.list.length)" @click="save">保存直播</el-button>
        </div>
      </div>
    </div>
    <div class="content livehistory">
      <div class="header list_flex align_items justify_content">
        <div class="box_item">
          <div class="title-line">视频选择</div>
        </div>
        <div class="">
          <template v-if="videolist.type === 'videos'">
            <el-button size="small" @click="changeVideoType('favorites')">我的收藏</el-button>
          </template>
          <template v-else-if="videolist.type === 'favorites'">
            <el-button size="small" @click="changeVideoType('videos')">视频库</el-button>
          </template>
        </div>
      </div>
      <div class="list">
        <div v-for="(item, index) in videolist.list" :key="index" class="item list_flex">
          <div class="vimgbox">
            <div class="vimg"><img alt="" :src="item.cover_url || item.video.cover_url"></div>
          </div>
          <div class="box_flex_1">
            <div class="title">{{ item.titles || item.video.titles }}</div>
            <div class="text">视频时长：{{ checkDuration(item.video_duration || item.video.video_duration) }}</div>
            <el-button type="primary" size="mini" @click="addvideo(item)">加入视频</el-button>
          </div>
        </div>
        <div v-if="videolist.list < 1 && !videolist.loading" style="padding: 20px; text-align: center;">
          暂无数据
        </div>
      </div>
      <pagination311
        :source="getvideolist"
        :page="videolist.page"
        :page-size="videolist.limit"
        :total-page="videolist.count"
      />
    </div>
  </div>
</template>

<script>
import { get } from '@/axios/http'
import { timesToHMS, timesToYMD } from '@/utils/filter'
import pagination311 from './components/pagination/index'

export default {
  components: {
    pagination311
  },
  props: {},
  data() {
    return {
      pickerOptions: {
        disabledDate: (time) => {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      videolist: {
        type: 'videos', // favorites videos
        count: 0,
        page: 1,
        limit: 10,
        loading: false,
        list: []
      },
      tablelist: {
        range: [],
        disabled: false,
        loop: false,
        outTime: 0,
        list: []
      }
    }
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() {
    this.getvideolist()
  },
  methods: {
    formatVideotime(obj, index) {
      const list = this.tablelist.list
      const range = this.tablelist.range
      let totaltime = 0
      for (var i = 0; i < list.length; i++) {
        const sub = list[i]
        if (i < index) {
          totaltime += sub.video_duration
        } else {
          break
        }
      }
      const starttime = totaltime + range[0]
      let endtime = starttime + obj.video_duration
      endtime = Math.min(endtime, range[1])
      return this.timesToYMD(starttime) + ' 至 ' + this.timesToYMD(endtime)
    },
    totalDuration(list) {
      let totaltime = 0
      for (var i = 0; i < list.length; i++) {
        const sub = list[i]
        totaltime += sub.video_duration
      }
      return Math.floor(totaltime / 60 / 1000)
    },
    timesToYMD(time) {
      return timesToYMD(time).substr(0, 17)
    },
    checkDuration(ts) {
      return timesToHMS(ts)
    },
    changeVideoType(str) {
      this.videolist.type = str
      this.getvideolist(1)
    },
    async getvideolist(page, pageSize) {
      if (page) {
        this.videolist.page = page
      }
      if (pageSize) {
        this.videolist.limit = pageSize
      }
      const params = {
        limit: this.videolist.limit,
        page: this.videolist.page
      }
      this.videolist.loading = true
      const url = '/' + this.videolist.type
      const { err, res } = await this.$get(url, params)
      this.videolist.list = res.data
      this.videolist.count = res.count
      this.videolist.loading = false
    },
    async save() {
      const list = this.tablelist.list
      const range = this.tablelist.range
      let totaltime = 0
      for (var i = 0; i < list.length; i++) {
        const sub = list[i]
        totaltime += sub.video_duration
      }
      const params = {
        contents: JSON.stringify(list),
        start_time: timesToYMD(range[0]),
        end_time: timesToYMD(range[1]),
        video_count: list.length,
        repeat: this.tablelist.loop ? 1 : -1,
        durations: totaltime
      }
      const { err, res } = await this.$post('/virtuallive', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
      } else {
        this.$router.push({ path: '/live-video' })
      }
    },
    clearAll() {
      // 清除列表
      this.tablelist.disabled = false
      this.tablelist.range = ''
      this.tablelist.list = []
      this.tablelist.outTime = 0
    },
    addvideo(item) {
      let obj = { ...item }
      if (this.videolist.type === 'favorites') {
        obj = obj['video']
      }
      // 加入视频
      const list = this.tablelist.list
      const range = this.tablelist.range
      if (range.length < 1) {
        this.$message({
          message: '请设置直播时间段',
          type: 'warning'
        })
        return
      }
      let totaltime = obj.video_duration
      for (var i = 0; i < list.length; i++) {
        const sub = list[i]
        totaltime += sub.video_duration
      }
      if (totaltime > range[1] - range[0]) {
        this.tablelist.outTime += 1
        if (this.tablelist.outTime > 1) {
          this.tablelist.outTime = 1
          this.$message({
            message: '已超出直播时间段，禁止加入',
            type: 'error'
          })
        } else {
          this.$message({
            message: '当前超出直播时间段',
            type: 'warning'
          })
          this.tablelist.list.push(obj)
        }
        return
      }
      this.tablelist.list.push(obj)
    },
    editorAction(obj, index, type) {
      this.tablelist.list.splice(index, 1)
      if (type === 'up') {
        this.tablelist.list.splice(index - 1, 0, obj)
      } else if (type === 'down') {
        this.tablelist.list.splice(index + 1, 0, obj)
      } else if (type === 'del') {
        // this.tablelist.list.splice(index, 1)
      }
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

  .livebox {
    width: 66%;

    .list {
      margin-top: 20px;
    }
  }

  .livehistory {
    width: calc(34% - 24px);
    margin-left: 24px;

    .list {
      padding-top: 10px;

      & > .list_flex {
        color: #404040;
        margin-top: 10px;
        transition: all .3s ease;
        color: #404040;

        &:hover {
          background-color: #f8f9fa;
        }

        .vimgbox {
          width: 34%;

          .vimg {
            position: relative;
            padding-top: 56.25%;
            margin-right: 10px;

            img {
              position: absolute;
              width: 100%;
              height: 100%;
              top: 0;
              left: 0;
              object-fit: cover;
              border-radius: 4px;
            }
          }
        }

        .title {
          margin-bottom: 6px;
          -webkit-line-clamp: 2;
          box-orient: vertical;
          overflow: hidden;
          white-space: normal;
          line-height: 1.5;
          word-break: break-all;
        }

        .text {
          margin-bottom: 10px;
          font-size: 12px;
        }
      }
    }
  }
</style>
