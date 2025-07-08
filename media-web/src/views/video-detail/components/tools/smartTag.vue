<!--
 * @Author: your name
 * @Date: 2021-07-29 15:12:32
 * @LastEditTime: 2021-09-02 11:02:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/video-detail/components/tools/smartTag.vue
-->
<template>
  <div class="smart-tag">
    <div class="item">
      <div
        v-for="(i,index) in cont"
        :key="i.word"
        class="tag ai"
        @click="wordToSecond(index, i.timestamps)"
      >
        <span class="text">{{ i.word }}</span>
        <span class="num">{{ i.timestamps.length }}</span>
      </div>
    </div>

    <!-- <div class="item">
      <div class="title">人工标签</div>
      <div v-for="(i,index) in labels" :key="i+index" class="tag">{{ i }}</div>
    </div>
    <el-tooltip class="item" effect="dark" content="请输入便于搜索识别的关键词" placement="bottom-start">

      <div class="input">
        <el-input
          v-model="tag"
          placeholder=""
          class=""
          @keyup.enter.native="addTag"
        >
          <el-button slot="append" class="button" @click="addTag">增加标签</el-button>
        </el-input>
      </div>
    </el-tooltip> -->
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    cont: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      uuid: 0,
      tag: '',
      labels: [],

      word_index: 0,
      word_time_num: 0

    }
  },
  computed: {
    videoData() {
      return this.$store.state.videoStatus.videoData
    }
  },
  watch: {

  },
  created() {
    this.labels = this.videoData.labels.split(',')
    this.uuid = this.$route.query.uuid
  },
  mounted() {

  },
  methods: {

    wordToSecond(index, timeList) {
      if (index !== this.word_index) {
        this.word_time_num = 0
      }
      this.word_index = index
      if (this.word_time_num > timeList.length - 1) {
        this.word_time_num = 0
      }
      var second_time = timeList[this.word_time_num]
      this.$store.commit('videoStatus/TIME_LINE_CHANGE', second_time / 1000)
      // this.video_info.startOffsetX = second_time
      // if (this.tiems_block_arr.length === 2) {
      //   this.video_info.cut_block_is_ready = true
      // }
      // this.$store.commit('setVideoInfo', this.video_info)
      // this.touchstart(second_time)
      // this.$store.commit('setVideoInfo', this.video_info)
      // this.$emit("touchstart", timeList[this.word_time_num]*1000);
      this.word_time_num++
    }

  }
}
</script>

<style scoped lang="scss">
.smart-tag {
  max-height: 70vh;
  overflow: scroll;

  .item {
    display: flex;
    flex-wrap: wrap;

    .title {
      width: 100%;
      margin-bottom: 22px;
    }

    .tag {
      margin-right: 20px;
      margin-bottom: 20px;
      font-size: 14px;
      color: #404040;
      line-height: 14px;
      background-clip: text;
      cursor: pointer;

      &.ai {
        border-radius: 4px;
        border: 1px solid #f2f2f2;

        span {
          display: inline-block;
          padding: 8px;
          text-align: center;

          &.num {
            background: rgba(239,239,239,.8);
            max-width: 30px;
            min-width: 30px;
          }
        }
      }
    }

  }

  .input {
    width: 50%;
  }

  .button {
    font-size: 12px;
    font-weight: 400;
    color: #404040;
  }
}

</style>
