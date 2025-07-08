<!--
 * @Author: your name
 * @Date: 2021-08-06 11:51:35
 * @LastEditTime: 2021-08-25 17:58:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/edit-mian/trackBar/timeBox.vue
-->
<template>
  <div class="time-box-wrap">
    <div v-for="(item) in boxList" :key="item.title" class="time-box">
      <div class="title">{{ item.title }}</div>
      <div class="time">{{ item.ms | msToSecond }}</div>
    </div>
  </div>
</template>

<script>
// 开始时间
// 结束时间
// 总计时长
import { mapState } from 'vuex'

export default {
  components: {

  },
  props: {
    title: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      time: 0
    }
  },
  computed: {
    ...mapState('video', ['cutTrackData']),
    boxList() {
      return [
        {
          title: '开始时间',
          ms: this.cutTrackData.startMs
        },
        {
          title: '结束时间',
          ms: this.cutTrackData.endMs
        },
        {
          title: '总计时长',
          ms: this.cutTrackData.endMs - this.cutTrackData.startMs
        }
      ]
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.time-box-wrap {
  display: flex;

  .time-box {
    display: flex;
    align-items: center;
    border: 1px solid #efefef;
    border-radius: 4px;
    font-size: 12px;
    height: 30px;
    margin-right: 10px;

    .title {
      background-color: #efefef;
      color: #404040;
      height: 100%;
      padding: 0 10px;
      display: flex;
      align-items: center;
      min-width: 68px;
      max-width: 68px;
      overflow: hidden;
      word-break: keep-all;
    }

    .time {
      color: #404040;
      padding: 0 25px;
      min-width: 100px;
      max-width: 100px;
    }
  }
}

</style>
