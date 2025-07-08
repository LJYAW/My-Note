<template>
  <div class="edit-table-wrap">
    <div style="position:absolute;right:20px;top:20px">
      <base-btn size="big" @click.native="edit()">进入编辑系统</base-btn>
    </div>
    <taskStatistics />
    <taskDeatils />

  </div>
</template>

<script>
import taskStatistics from './task_statistics'
import taskDeatils from './task_deatils'

export default {
  components: { taskStatistics, taskDeatils },
  data() {
    return {
      videoOffsetMs: {
        start_ms: 1000,
        end_ms: 3000
      },
      playerOptions: {
        width: '100%',
        autoplay: true,
        muted: true,
        language: 'en',
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [{
          type: 'application/x-mpegURL',
          src: 'http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8'
        }],
        src: 'http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8',
        poster: 'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-1.jpg',
        fluid: true
      }
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    edit() {
      this.$get('/ads-task/get-task').then((res) => {
        this.$router.push({
          path: '/advertisement/edit-system',
          query: {
            tid: res.data.id
          }
        })
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    }
  }
}
</script>

<style lang='scss'>
.edit-table-wrap{
  position: relative;
  background-color: #f3f3f3!important;
  .pagination-list{
    width: 100%;
    text-align: right;
    margin-top: 30px;
  }
  .edit-task-table{
    background: #fff;
    padding: 20px;
    .search-top{
      display: flex;
      .btns{
        margin-left: 10px;
      }
      .item-search{
        display: flex;
        margin-right: 10px;
        .item-label{
          min-width: 60px;
          font-size: 14px;
          margin-top: 10px;
          // text-align: center;
        }
      }
    }
  }
  .task-details{
    background: #fff;
    padding: 20px;
    margin-top: 20px;
    .top-title{
      font-size: 16px;
      font-weight: 700;
      color: #333333;
      padding-bottom: 15px;
      border-bottom: 1px solid #ddd;
    }
    .top-details{
      margin-top: 30px;
      .el-form-item{
        margin-right: 20px;
      }
    }
    .check-list{
      margin-top: 10px;
    }
    .item-search{
        display: flex;
        flex-grow: 1;
        margin-right: 10px;
        .item-label{
          min-width: 70px;
          font-size: 14px;
          margin-top: 10px;
          // text-align: center;
        }
    }
    .table-list{
      .pri-logo{
        background: #7D7D7D 10000% 10000%;
        color: #fff;
        padding: 2px;
        &.pub-logo{
          background: #167CFF 10000%;
          color: #fff;
          padding: 2px;
        }
      }
    }
    .details-table{
      .el-tabs__nav-wrap::after{
        position: static !important;
      }
    }
  }
}
</style>
