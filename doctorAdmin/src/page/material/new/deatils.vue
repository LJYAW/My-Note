<!--
 * @Author: zll
 * @Date: 2020-12-03 15:33:40
 * @LastEditTime: 2020-12-03 16:45:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /weijian_cms_web/src/page/material/new/models/adopt.vue
-->
<template>
  <div class="deatils_wrap">
    <el-breadcrumb>
      <el-breadcrumb-item to="/material">素材管理</el-breadcrumb-item>
      <el-breadcrumb-item>视频详情</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="d-flex mt-30px">
      <div class="videoBox">
        <video src="http://vd-drm.mtq.tvm.cn/video/DaTangLeiYinSi/2019/04/26/LiaoNingNorth_1500000_20190426_1556293984948_0.mp4" class="videoStyle" controls="controls"></video>
      </div>
      <div class="messageBox">
        <div class="title">
          {{data.title}}
        </div>
        <div class="messageList">
          <span>作者:</span>
          <p>{{data.author}}</p>
        </div>
        <div class="messageList">
          <span>时长:</span>
          <p>{{data.duration}} 分钟</p>
        </div>
        <div class="messageList">
          <span>视频标签:</span>
          <p style="line-height:22px">{{data.tags}}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: '',
  data() {
    return {
      data: JSON.parse(this.$route.query.params)
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
.deatils_wrap {
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
        min-width: 80px;
        color: #999999;
        font-size: 14px;
      }
      p {
        color: #333333;
        font-size: 14px;
        margin-left: 10px;
        margin-top: 10px;
      }
    }
  }
}
</style>
