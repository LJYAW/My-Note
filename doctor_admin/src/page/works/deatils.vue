<!--
 * @Author: zll
 * @Date: 2020-12-16 11:49:55
 * @LastEditTime: 2020-12-16 11:56:07
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /medical_cms_web/src/page/works/deatils.vue
-->
<template>
  <div class="work_wrap">
    <el-breadcrumb>
      <el-breadcrumb-item to="/works">作业管理</el-breadcrumb-item>
      <el-breadcrumb-item>详情</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="d-flex mt-30px">
      <div class="videoBox">
        <video :src="data.video_url" class="videoStyle" controls="controls"></video>
      </div>
      <div class="messageBox">
        <div class="title">
          {{data.title}}
        </div>
        <div class="messageList">
          <span>作者:</span>
          <p>{{data.author_name}}</p>
        </div>
        <div class="messageList">
          <span>时长:</span>
          <p>{{data.duration}} 分钟</p>
        </div>
        <div class="messageList">
          <span>生成时间:</span>
          <p>{{data.finished_time}}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: '',
  props: {
    type: String,
  },
  data() {
    return {
      data: JSON.parse(this.$route.query.params),
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {},
}
</script>

<style lang="scss" scoped>
.work_wrap {
  width: 100%;
  background: #ffffff;
  padding: 36px;
  box-sizing: border-box;
  .videoBox {
    video {
      width: 660px;
      height: 370px;
    }
  }
  .messageBox {
    margin-left: 15px;
    .title {
      color: #202020;
      font-size: 20px;
      text-align: left;
      line-height: 26px;
      word-wrap: break-word;
    }
    .messageList {
      display: flex;
      margin-top: 15px;
      span {
        color: #999999;
        font-size: 14px;
      }
      p {
        color: #333333;
        font-size: 14px;
        margin-left: 10px;
      }
    }
  }
}
</style>
