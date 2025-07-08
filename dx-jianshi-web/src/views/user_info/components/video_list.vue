<!--
 * @Author: your name
 * @Date: 2020-11-12 14:35:19
 * @LastEditTime: 2020-11-12 15:51:08
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/user_info/components/video_list.vue
-->
<template>
  <div class="video-container">
    <div class="text-center mt-100px" v-if="list.length < 1">
      <img src="@/assets/images/user_info/no_videos.png" height="240" width="240" />
      <p class="fc-999">视频库暂无视频，请前去智能创作页面制作！</p>
    </div>
    <div v-else>
      <div class="mt-20px video-list">
        <el-row :gutter="20">
          <el-col :span="4" class="card pr-5px" v-for="(item, index) in list" :key="index">
            <div class="item">
              <div class="img-wrap" @click="videoMshow(item)" style="height:135px">
                <div v-if="item.status == '生成中' || item.status == '预处理中'" class="is_ing h-100 border-none">
                  <img class="ob-cover h-100" src="@/assets/images/created_ing.png" />
                </div>
                <div v-else class="h-100">
                  <i class="iconfont icontupian-tianchong fz-30px" v-if="!item.cover_pic && item.status !== '生成中'"></i>

                  <img v-else :src="item.cover_pic" class="w-100 h-100" />
                </div>
              </div>
              <div class="w-100 fc-999 text-wrap" style="background:white;padding:16px">
                <div style="color:#202020;line-height:20px;min-height: 40px" class="text-ellipsis-2">
                  {{ item.title }}
                </div>
                <div class="mt-18px">
                  <p class="fz-12px mt-7px">
                    视频状态：
                    <span :class="[item.status == '生成中' && 'create', item.status == '生成成功' && 'create-success', item.status == '生成失败' && 'create-fail', 'video-status']">
                      {{ item.status }}
                    </span>
                  </p>
                  <p class=" fz-12px mt-7px w-100 flex-start text-ellipsis" v-if="item.status !== '生成中'">
                    <span>生成时间：</span>
                    <span style="transform: scale(1); word-break: break-all;">{{ item.finished_time }}</span>
                  </p>
                  <p v-else-if="item.finished_time_by_now" class="fz-12px mt-7px text-ellipsis">
                    预计生成用时：约5分钟
                  </p>
                </div>

                <!-- <div class="fz-12px">生成时间：</div> -->
              </div>

              <div class="icon-wrap d-flex justify-content-between w-100">
                <div class="d-flex" v-if="item.status == '生成成功'">
                  <a @click.stop="downloadVideo(item)">
                    <i class="iconfont iconxiazai1 fc-white"></i>
                  </a>

                  <a @click.stop="publishVideo(item)" class=" ml-10px">
                    <i class="iconfont iconfabu fc-white"></i>
                  </a>
                </div>

                <a @click.stop="resetEdit(item)" class=" ml-10px">
                  <i class="iconfont iconbianji fc-white"></i>
                </a>

                <a @click.stop="deleteVideo(item)" class="align-self-end ml-auto">
                  <i class="iconfont iconshanchu fc-white"></i>
                </a>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="mb-50px d-flex justify-content-end">
        <el-pagination @current-change="handleCurrentChange" :current-page="currentPage" :page-size="limit" layout="prev, pager, next, jumper" :total="total"></el-pagination>
      </div>
    </div>
    <showVideoM @Modalclose="Modalclose" v-if="modaName == 'showVideoM'" :video_details="video_details" :video_options="video_options" />
  </div>
</template>
<script>
import showVideoM from '@/components/show_video_m'
import downloadUrlFile from '@/utils/download'

export default {
  name: 'videoList',
  props: ['list', 'total', 'currentPage'],
  data() {
    return {
      modaName: '',
      keyword: '',
      page: 1,
      limit: 12,
      activeIndex: 0,
      timeout: null,
      status_loading: true,
      video_modal_show: false,
      video_options_title: '',
      finished_status: true,
      video_options: {
        width: '180',
        height: '100',
        poster: '',
        autoplay: true,
        src: ''
      },
      refresh_time: null
    }
  },
  components: { showVideoM },

  computed: {},

  watch: {
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
    resetEdit(item) {
      console.log(item.id)
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
    downloadVideo(item) {
      this.$post('/intelligent-creation/download', { id: item.id }).then(res => {
        if (res.data.code == '0000') {
          let url = res.data.data.download_url
          console.log('downloadVideo -> url', url)
          downloadUrlFile(url)
          this.$alertMsg('正在下载...')
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    handleCurrentChange(val) {
      this.$emit('page', val)
    },
    videoMshow(item) {
      if (item.status == '生成成功') {
        this.video_modal_show = true
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
      this.video_modal_show = false
      this.video_options.src = ''
      this.video_details = {}
      this.modaName = ''
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
    }
  },

  created() {},

  destroyed() {
    clearInterval(this.refresh_time)
    this.refresh_time = null
  },

  mounted() {}
}
</script>
<style scoped lang="scss">
.active {
  color: #c51b19;
  border-bottom: 2px solid #c51b19;
}

.el-col {
  border-radius: 4px;
  width: 25%;
  margin-bottom: 20px;
  padding: 0 5px !important;
}
.video-container {
  min-height: 800px;
}
.video-list {
  .img-wrap {
    position: relative;
    background-color: rgb(229, 229, 229);
    .icontupian-tianchong {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      color: rgb(144, 157, 166);
    }
  }
  .card {
    .item {
      position: relative;
      min-height: 258px;
      max-height: 258px;
      border-radius: 8px;
      overflow: hidden;
      .text-wrap {
        min-height: 121px;
      }
      &:hover {
        .icon-wrap {
          opacity: 1;
          .iconfont {
            transform: scale(1);
          }
        }
      }
      .icon-wrap {
        text-align: center;
        position: absolute;
        top: 8px;
        // right: 8px;
        padding: 0 10px;
        opacity: 0;
        transition: all 0.3s;
        border-radius: 5px;
        .icondelect {
          background-color: rgba($color: #fff, $alpha: 0.8);
        }
        .iconfont {
          // transition: all 0.2s;
          // transform: scale(0.5);
          background: $c;
          border-radius: 50%;
          width: 35px;
          height: 35px;
          line-height: 33px;
          display: block;
          text-indent: 2px;
        }
        .iconshanchu {
          font-size: 16px;
          line-height: 35px;
        }
      }
    }
    .text-wrap {
      .video-status {
        padding: 2px 12px;
        border-radius: 5px;
      }
      .create-success {
        background: #effff4;
        color: #49bf5d;
      }
      .create-fail {
        background: #f7f7f7;
        color: #999;
      }
      .create {
        background: #ffe9e8;
        color: $c;
      }
    }
  }
}
</style>
