<!--
 * @Author: your name
 * @Date: 2021-01-21 15:33:49
 * @LastEditTime: 2021-04-15 20:18:13
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/full_business/task_mgt/models/play_video.vue
-->
<template>
  <base-dialog
    :show="show"
    :title="videoData.title"
    :width="dialogsObj.width"
    :center="dialogsObj.center"
    @close="close"
  >
    <div class="videoModel">
      <!-- <div class="title">
        00
      </div> -->
      <div class="videoBox">
        <video v-if="videoData.video_url" ref="video" :src="videoData.video_url" controls :disablePictureInPicture="true" controlsList="nodownload noremoteplayback" />
        <div v-else>糟糕，视频跑路了，换个视频试试 ~~~~</div>
      </div>
      <div class="message-box">
        <div class="message">
          <div class="item-message">
            <span>分类:</span>
            <div>{{ videoData.first_category_name }}</div>
            <div style="margin-left:10px">{{ videoData.second_category_name }}</div>
          </div>
          <div class="item-message" style="display:flex">
            <span>关键词:</span>
            <div>
              <div v-for="(item ,index) in videoData.keyword_detail" :key="index" class="item-keywords" @click="toCurrentTime(item)">
                <div>{{ item.keyword }}</div>
              </div>
            </div>
          </div>
          <div class="item-message">
            <span>播出时间:</span>
            <div>{{ videoData.broadcast_time }}</div>
          </div>
          <div class="item-message">
            <span>编辑提交时间:</span>
            <div>{{ created_at }}</div>
          </div>
          <div class="item-message" style="flex-wrap:wrap;">
            <span>推送成功时间:</span>
            <div>
              <div v-for="(item,index) in push_customers" :key="index" class="push-time">
                <div v-if="item.push_status==='推送成功'">
                  {{ item.customer_name }}:{{ item.push_success_at }}
                </div>
              </div>
            </div>
          </div>
          <div class="item-message">
            <span>来源:</span>
            <div>
              {{ videoData.channel_name }}

              {{ videoData.item_name }}
            </div>
          </div>
        </div>
        <div class="cover-box">
          <img v-if="videoData.cover_url" :src="videoData.cover_url" alt="">
        </div>
      </div>

    </div>
  </base-dialog>
</template>
<script>
export default {
  name: '',
  components: {},
  data() {
    return {
      show: false,
      videoData: {},
      created_at: '',
      push_customers: [],
      dialogsObj: {
        width: '800px',
        title: '',
        center: true
      }
    }
  },
  computed: {
  },
  watch: {},
  created() {
  },
  mounted() {
  },
  methods: {
    toCurrentTime(item) {
      this.$refs.video.currentTime = item.start_time / 1000
    },
    getData(id) {
      this.$get('/production/play-video?video_id=' + id).then((res) => {
        console.log(res)
        this.videoData = res.data
      }).catch((res) => {
        this.$message({
          showClose: true,
          message: '操作失败',
          type: 'error'
        })
      })
    },
    open(id) {
      this.show = true
      this.getData(id)
    },
    clear() {
      this.$refs.video.pause()
      this.created_at = ''
      this.push_customers = []
      this.videoData = {}
      this.$emit('close')
    },
    close() {
      this.show = false
      this.clear()
    }
  }
}
</script>

<style lang='scss' scoped>
.videoModel{
  height: 560px;
  overflow: auto;
  .title{
    font-size: 20px;
    font-weight: bold;
    color: #333333;
  }
  .videoBox{
    margin-top: 15px;
    video{
      width: 100%;
      height: 360px;
    }
    div{
      width: 100%;
      height: 360px;
      text-align: center;
      color: #fff;
      opacity: 0.8;
      background: #000;
      line-height: 360px;
    }
  }
  .message-box{
    display: flex;
    justify-content: space-between;
    .message{
      width: 70%;
     .item-message{
        display: flex;
        font-size: 16px;
        margin-top: 15px;
        .push-time{
          margin-left: 10px;
          border-radius: 2px;
          // margin-bottom: 10px;
        }
        .item-keywords{
          display: flex;
          flex-wrap: wrap;
          div{
            cursor: pointer;
            color: #409EFF;
            margin-right: 10px;
            line-height: 20px;
          }
        }
        span{
          min-width: 100px;
          text-align: center;
        }
        div{
          word-break: break-all;
        }
      }
    }
    .cover-box{
      width: 200px;
      img{
        width: 100%;
        height: 100%;
        object-fit: contain;
      }
    }
  }
}
</style>
