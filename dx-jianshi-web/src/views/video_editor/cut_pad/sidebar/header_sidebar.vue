<template>
  <div class="header-sidebar-wrap d-flex justify-content-between align-items-center">
    <ul class="tool-bar-list">
      <!-- <li>
        <a class="hove-c"
           @click.stop.prevent>
          <i class="iconfont iconchexiao"></i>
        </a>
      </li>
      <li>
        <a class="hove-c"
           style="transform:rotate(180deg);"
           @click.stop.prevent>
          <i class="iconfont iconchexiao"></i>
        </a>
      </li> -->
      <li>
        <a class="hove-c"
           @click.stop.prevent="setTrackLeft(1)">
          <i class="iconfont iconrudian"></i>
        </a>
        <span class="ml-5px fz-12px fc-999">入点标记</span>
      </li>
      <!-- <li class="pr-35px">
        <a class="hove-c"
           @click.stop.prevent="setTrackLeft(2)">
          <i class="iconfont iconrudian1"></i>
        </a>
      </li> -->
      <!-- <li class="pl-8px"
          @click.stop.prevent="cutClip()">
        <a class="hove-c">
          <i class="iconfont iconcut fz-16px"></i>
        </a>
      </li>
      <li @click.stop.prevent="deleteClip()">
        <a class="hove-c">
          <i class="iconfont iconshanchu fz-16px"></i>
        </a>
      </li>
      <li>
        <a class="hove-c">
          <i class="iconfont iconziyuan fz-16px"></i>
        </a>
      </li> -->

    </ul>
    <div class="d-flex align-items-center h-100">
      <p v-if="selectTotaoMs < duration"
         class="fz-12px fc-c mr-10px">选取时长小于素材所需时长，将会自动重复播放</p>
      <div class="ml-auto">
        <span class="mr-15px">选取时长：{{selectTotaoMs |msToTime }}</span>
        <el-button round
                   type="primary"
                   size="mini"
                   v-if="is_ready"
                   @click="cutSelect">剪选</el-button>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  props: [],
  data() {
    return {
      is_ready: false,
      selectTotaoMs: 0
    }
  },
  components: {},

  computed: {
    active_data() {
      return this.$store.state.videoM.active_data
    },
    clip_list() {
      return this.$store.state.videoM.cut_track_list
    },
    per_px_ms() {
      return this.$store.state.videoM.per_px_ms
    },
    duration() {
      return Math.floor((this.clip_list[0].width * this.per_px_ms * 10) / 10)
    },
    progrees_x() {
      return this.$store.state.videoM.progrees_x
    },
    currentTimeForProgress() {
      return this.$store.state.videoM.currentTimeForprogress
    },
    video() {
      let video = document.getElementById('myVideo')
      return video
    }
  },

  watch: {
    clip_list: {
      deep: true, // 深度监听设置为 true
      handler: function(newV, oldV) {
        this.getSelectTotaoMs()
      }
    }
  },

  methods: {
    getSelectTotaoMs() {
      this.$nextTick(() => {
        let currentDuration = Math.floor((this.clip_list[0].width * this.per_px_ms * 10) / 10)
        this.selectTotaoMs = Math.min(currentDuration, this.duration)
      })
    },
    deleteClip() {
      console.log('删除')
      console.log(this.active_data)

      if (this.active_data.type == 'video') {
        let list = this.$actions.clone(this.clip_list)
        list.splice(this.active_data.index, 1)
        this.$store.commit('setCutTrackList', list)
      }

      if (this.active_data.type == 'image') {
        let list = this.$store.state.videoM.image_track_list
        list.splice(this.active_data.index, 1)
        this.$store.commit('setImageTrackList', list)
      }

      if (this.active_data.type == 'text') {
        let list = this.$store.state.videoM.track_text_list
        list.splice(this.active_data.index, 1)
        this.$store.commit('setTrackTextList', list)
      }
    },
    cutClip() {
      console.log('剪辑')

      if (this.active_data.type == 'video') {
        const progrees_x = this.progrees_x
        const per_px_ms = this.$store.state.videoM.per_px_ms

        const index = this.active_data.index
        const list = this.clip_list
        const item = list[index]
        const cutNewItem = Object.assign({}, item)
        console.log(cutNewItem)

        console.log(list, 'list[iex]')

        const left = item.left || 0
        const width = item.width

        if (progrees_x >= left + 1 && progrees_x <= width + left - 1) {
          console.log('说明在局域内')

          // 计算剪辑的宽度
          item.width = progrees_x - left
          item.end_time = Math.floor(item.width * per_px_ms)

          cutNewItem.width = cutNewItem.width - item.width
          cutNewItem.left = item.left + item.width + 1
          cutNewItem.start_time = Math.floor(progrees_x * per_px_ms)

          list[index] = item
          list.splice(index + 1, 0, cutNewItem)

          this.$store.commit('setCutTrackList', list)
        }
      }
    },
    cutSelect() {
      console.log('cutSelect -> cutSelect')
      if (this.selectTotaoMs < 3000) {
        this.$alertMsg('选取视频时长不能小于3秒')
        return
      }
      var item = this.clip_list[0]
      console.log('cutSelect -> item', item)

      var data = {
        uuid: item.uuid,
        video_origin: item.video_origin,
        video_hd_str: item.video_hd_str,
        resource_url: item.video_url,
        start_ms: Math.floor(item.left * this.per_px_ms),
        end_ms: Math.floor((item.left + item.width) * this.per_px_ms),
        type: 'video'
      }
      console.log('cutSelect -> data', data)
      this.$store.commit('setResourceCutVideo', data)
    },
    setTrackLeft(type) {
      var data = this.clip_list[0]
      let objTemp = {}

      if (type == 1) {
        let left = this.currentTimeForProgress / this.per_px_ms
        console.log('setTrackLeft -> left', left)
        objTemp = Object.assign(data, {
          left: this.progrees_x
        })
      }

      if (type == 2) {
        let MaxWidth = this.$store.state.edtor_total_ms / this.per_px_ms
        let currentWidth = this.currentTimeForProgress / this.per_px_ms - data.left
        let minWidth = Math.ceil(1000 / this.per_px_ms)

        let width = Math.min(MaxWidth, currentWidth)
        console.log('setTrackLeft -> width', width)
        objTemp = Object.assign(data, {
          width: Math.max(width, minWidth)
        })
      }

      var arr = [data]
      this.$store.commit('setCutTrackList', arr)
    },
    dsiable() {
      // 等待视频健在完成后 可以选择剪辑区域
      this.$nextTick(() => {
        let video = document.getElementById('myVideo')
        let self = this
        video.addEventListener('canplaythrough', function() {
          // SomeJavaScriptCode
          console.log('可以播放')
          self.is_ready = true
        })
      })
    }
  },

  created() {},

  mounted() {
    this.dsiable()
  }
}
</script>

<style scoped lang="scss">
.header-sidebar-wrap {
  position: relative;
  z-index: 12;
  padding: 8px 14px;
  box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(255, 255, 255, 0);
  // height: 50px;
  // background: white;
  // box-shadow: 0 3px 10px 0 #d9d9d9;
  .tool-bar-list {
    height: 100%;
    display: flex;
    align-items: center;
    margin-left: -20px;
    li {
      margin: 0 16px;
      font-size: 12px;
      height: 50%;
      display: flex;
      align-items: center;
      .iconfont {
        font-size: 14px;
      }
    }
  }
}
</style>
