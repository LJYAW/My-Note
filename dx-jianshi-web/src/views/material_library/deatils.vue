<!--
 * @Author: zll
 * @Date: 2020-12-07 15:56:36
 * @LastEditTime: 2021-01-06 15:48:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/ material_library/deatils.vue
-->
<template>
  <div class="details_wrap container">
    <el-breadcrumb>
      <el-breadcrumb-item to="/material-library">素材库</el-breadcrumb-item>
      <el-breadcrumb-item>视频详情</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="d-flex mt-30px">
      <div class="videoBox">
        <videoPalyer :video_options="video_options" v-if="falg" :fluid='false' />
        <!-- <video :src="data.resource_url" class="videoStyle" controls="controls"></video> -->
      </div>
      <div class="messageBox">
        <div class="title">
          {{data.title}}
        </div>
        <div class="messageList">
          <span>视频大小:</span>
          <p>{{data.file_size}}</p>
        </div>
        <div class="messageList">
          <span>视频分辨率:</span>
          <p>{{data.resolution}}</p>
        </div>
        <div class="messageList">
          <span>时长:</span>
          <p>{{data.duration_str}}</p>
        </div>
      </div>
    </div>
    <div style="font-size:14px;color:#000000" class="mt-20px">智能标签</div>
    <div class="d-flex tags">
      <p v-for="(tags,tagsindex) in data.tags" :key="tagsindex">
        {{tags}}
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: '',
  props: {},
  data() {
    return {
      tags: [],
      data: {},
      video_options: {
        width: '660',
        height: '370',
        src: '',
        autoplay: false,
        poster: ''
      },
      falg: false
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    getData() {
      let params = {
        uuid: this.$route.query.uuid
      }
      this.$get('/material-library/material-info', { params: params }).then(res => {
        if (res.data.code == '0000') {
          console.log(res)
          this.falg = true
          this.data = res.data.data
          this.video_options.src = this.data.resource_url
          this.video_options.poster = this.data.cover_pic_url
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    }
  },
  created() {
    this.getData()
  },
  mounted() {}
}
</script>

<style lang="scss" scoped>
.details_wrap {
  width: 100%;
  background: #ffffff;
  padding: 36px;
  box-sizing: border-box;
  .tags {
    margin-top: 10px;
    padding-bottom: 20px;
    flex-wrap: wrap;
    p {
      color: #333333;
      background: #f7f7f7;
      padding: 5px;
      font-size: 12px;
      margin-top: 10px;
      line-height: 17px;
      -webkit-line-clamp: 2;
      line-clamp: 2;
      border-radius: 8px;
      margin-right: 10px;
    }
  }
  .videoBox {
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
