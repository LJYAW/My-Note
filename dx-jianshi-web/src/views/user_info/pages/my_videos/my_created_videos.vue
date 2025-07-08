<template>
  <div class="content create-video">
    <loading v-if="status_loading" />
    <div v-else>
      <div v-if="list.length > 0">
        <card-list :list="list" @handerClick="handerClick">
          <template #img="slotProps">
            <img v-if="slotProps.item.status == '生成中' || slotProps.item.status == '预处理中'" src="@/assets/images/created_ing.png" />
            <img v-else :src="slotProps.item.cover_pic" alt="" @error="imgError()" />
            <span>{{ getDuration(slotProps.item.duration) }}</span>
            <div class="icon-wrap d-flex justify-content-between w-100">
              <div class="d-flex" v-if="slotProps.item.status == '生成成功'">
                <a @click.stop="downloadVideo(slotProps.item)">
                  <i class="iconfont iconxiazai1 fc-white"></i>
                </a>

                <!-- <a @click.stop="publishVideo(slotProps.item)"
                class=" ml-10px">
                <i class="iconfont iconfabu fc-white"></i>
              </a> -->
              </div>

              <a @click.stop="resetEdit(slotProps.item)" class=" ml-10px">
                <i class="iconfont iconbianji fc-white"></i>
              </a>
              <a v-if="slotProps.item.status == '生成成功'" class=" ml-10px" @click.stop="shareVideo(slotProps.item)">
                <i class="iconfont iconshare fc-white"></i>
              </a>

              <a @click.stop="deleteVideo(slotProps.item)" class="align-self-end ml-auto">
                <i class="iconfont icondelect fc-white"></i>
              </a>
            </div>
            <div class="w-100 h-100 bofang d-flex justify-content-center align-items-center" v-if="slotProps.item.status == '生成成功'">
              <vsvg icon="iconbofanganniu" class="iconfont fz-40px" />
            </div>
          </template>

          <template #title="slotProps">
            <p class="title">{{ slotProps.item.title }}</p>
          </template>

          <template #details="slotProps">
            <div>
              <p>
                视频状态：
                <span :class="[slotProps.item.status == '生成成功' && 'success', slotProps.item.status == '生成失败' && 'failed']">{{ slotProps.item.status }}</span>
              </p>
              <p v-if="slotProps.item.status == '生成成功'">生成时间：{{ slotProps.item.finished_time }}</p>
            </div>
          </template>
        </card-list>
        <div class="mb-50px d-flex justify-content-end">
          <el-pagination @current-change="handleCurrentChange" :current-page="currentPage" :page-size="limit" layout="prev, pager, next, jumper" :total="total"></el-pagination>
        </div>
      </div>
      <div class="no-data" v-else>
        <img src="../../../../assets/images/user_info/no_videos.png" alt="" />
      </div>
    </div>
    <showVideoM @Modalclose="Modalclose" v-if="modaName == 'showVideoM'" :video_details="video_details" :video_options="video_options" />
    <shareVideoM :qr_code="qr_code" @Modalclose="Modalclose" v-if="modaName == 'shareVideoM'" />
  </div>
</template>

<script>
import cardList from '@/components/card_list/index'
import downloadUrlFile from '@/utils/download'
import showVideoM from '@/components/show_video_m'
import shareVideoM from '../../components/share_video'
import { imgError } from '../../js/imgError'
export default {
  props: ['activeId'],
  mixins: [imgError],
  data() {
    return {
      keyword: '',
      page: 1,
      limit: 12,
      total: 0,
      currentPage: 0,
      list: [],
      timeout: null,
      status_loading: true,
      finished_status: true,
      refresh_time: null,
      modaName: '',
      video_options: {},
      video_details: {},
      qr_code: 'XXX'
    }
  },
  components: { cardList, showVideoM, shareVideoM },
  computed: {},
  watch: {
    activeId(val) {
      if (val) {
        this.currentPage = 1
        this.page = 1
        this.getSearch()
      }
    },
    finished_status: {
      handler(val) {
        if (!val) {
          this.refresh_time = setTimeout(() => {
            this.getSearch()
          }, 60000)
        } else {
          clearTimeout(this.refresh_time)
          this.refresh_time = null
        }
      },
      immediate: true
    }
  },
  methods: {
    handerClick(item) {
      if (item.status == '生成成功') {
        this.video_options = {
          width: '800',
          height: '450',
          poster: item.cover_pic,
          autoplay: false,
          src: item.video_url
        }
        this.video_details = item
        this.modaName = 'showVideoM'
        this.$nextTick(() => {
          this.$store.commit('modalShow', 'showVideoM')
        })
      } else {
        this.$alertMsg('该视频' + item.status)
      }
    },
    Modalclose() {
      this.video_options.src = ''
      this.video_details = {}
      this.modaName = ''
    },
    getSearch() {
      let params = {
        keyword: this.keyword,
        page: this.page,
        limit: this.limit
      }
      if (this.timeout !== null) {
        clearTimeout(this.timeout)
      }
      this.status_loading = true
      this.finished_status = true

      this.$get('/intelligent-creation/intelligent-video-list', { params: params }).then(res => {
        if (res.data.code == '0000') {
          var data = res.data.data
          let list = data.list
          list.forEach(item => {
            if (item.finished_time) {
              item.finished_time = item.finished_time.substring(5)
            } else {
              this.finished_status = false
            }

            if (item.estimate_finish_time) {
              let current_date = new Date().getTime()
              let date = new Date(item.estimate_finish_time).getTime()
              let time_remaining = date - current_date
              item.finished_time_by_now = time_remaining
            }
          })
          this.list = data.list
          this.total = data.total
          this.currentPage = Number(data.page)
        } else {
          this.$alertMsg(res.data.msg)
        }
        this.status_loading = false
      })
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.page = val
      this.getSearch()
    },
    getDuration(t) {
      var NowtimeValue = t
      var nowH = parseInt(NowtimeValue / 3600)
      var nowM = parseInt((NowtimeValue % 3600) / 60)
      var nowS = parseInt(NowtimeValue % 60)
      nowH < 10 ? (nowH = '0' + nowH) : (nowH = nowH)
      nowM < 10 ? (nowM = '0' + nowM) : (nowM = nowM)
      nowS < 10 ? (nowS = '0' + nowS) : (nowS = nowS)
      return nowH + ':' + nowM + ':' + nowS
    },
    downloadVideo(item) {
      this.$post('/intelligent-creation/download', { id: item.id }).then(res => {
        if (res.data.code == '0000') {
          let url = res.data.data.download_url
          downloadUrlFile(url)
          this.$alertMsg('正在下载...')
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    resetEdit(item) {
      this.$router.push({
        path: '/intellect-create',
        query: {
          reset_edti_id: item.id
        }
      })
    },
    publishVideo(item) {
      // query传参，使用path跳转
      this.$router.push({
        path: '/publish_video',
        query: {
          title: item.title,
          intelligent_id: item.id,
          id: item.id,
          video_url: item.video_url,
          cover_pic: item.cover_pic
        }
      })
    },
    deleteVideo(item) {
      this.$confirm('是否确认删除该视频?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      })
        .then(() => {
          this.$deleteRun('/intelligent-creation/delete-intelligent', { data: { id: item.id } }).then(res => {
            if (res.data.code == '0000') {
              this.$alertMsg('删除成功')
              this.getSearch()
            } else {
              this.$alertMsg(res.data.msg)
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    shareVideo(item) {
      this.qr_code = 'https://magic.weijian.video/wx-share?id=' + item.id
      this.modaName = 'shareVideoM'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'shareVideoM')
      })
    }
  },

  created() {
    this.getSearch()
  },
  destroyed() {
    clearInterval(this.refresh_time)
    this.refresh_time = null
  },

  mounted() {}
}
</script>
<style lang="scss">
.content.create-video {
  min-height: 860px;
  position: relative;
  .card-content {
    .des {
      height: 40px;
      span {
        padding: 2px 12px;
        border-radius: 5px;
        background: #ffe9e8;
        color: $c;
      }
      span.success {
        color: #49bf5d;
        background: #effff4;
      }
      span.failed {
        background: #f7f7f7;
        color: #999;
      }
    }
  }
  .no-data {
    position: absolute;
    top: 80px;
    left: 50%;
    transform: translateX(-50%);
  }
}
</style>
