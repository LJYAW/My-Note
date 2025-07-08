<!--
 * @Author: your name
 * @Date: 2021-08-10 10:14:27
 * @LastEditTime: 2021-09-01 14:28:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/videos-manage/video-detail/component/editProject.vue
-->
<template>
  <div class="">
    <div class="title">是否确认开始分析视频？</div>
    <div class="cont">视频时长： {{ videoData.video_duration|timesToHMS }}</div>
    <div class="cont">剩余分析时长： {{ surplusTime|timesToHMS }}</div>

    <el-button type="primary" size="small" class="btn" @click="save">分析</el-button>

  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    videoData: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      surplusTime: 0
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getSurplusTime()
  },
  mounted() {

  },
  methods: {
    async getSurplusTime() {
      // 获取剩余空间
      const { res, err } = await this.$get('/companies/')
      this.surplusTime = res.data.time_size
    },
    save() {
      this.$emit('analysis')
    }
  }
}
</script>

<style scoped lang="scss">
.title {
  font-size: 14px;
  color: #404040;
  margin-top: 20px;
}

.cont {
  margin-top: 10px;
  font-size: 12px;
  color: #404040;
  opacity: .6;
}

.btn {
  margin-top: 35px;
  width: 100%;
}
</style>
