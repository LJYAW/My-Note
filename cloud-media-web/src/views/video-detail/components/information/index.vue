<!--
 * @Author: your name
 * @Date: 2021-08-03 09:48:08
 * @LastEditTime: 2021-09-23 15:52:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/video-detail/components/information/index.vue
-->
<template>
  <div class="information">
    <div v-for="item in informationData" :key="item.title" class="information-item">
      <div class="title">{{ item.title }}</div>
      <div class="cont">
        <img v-if="item.title ==='视频封面'" :src="item.cont" alt="">
        <span v-else>{{ item.cont }}</span>
      </div>
    </div>

    <div class="item">
      <div v-if="tags.length" class="title">智能标签</div>
      <div v-for="(i,index) in tags" :key="i+index" class="tag">{{ i }}</div>
    </div>
    <div class="item">
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
    </el-tooltip>
  </div>
</template>

<script>
import { initSize, timesToHMS } from '@/utils/filter.js'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      informationData: [
        { title: '视频封面', cont: '' },
        { title: '视频名称', cont: '' },
        { title: '视频时长', cont: '' },
        { title: '视频来源', cont: '' },
        { title: '上传时间', cont: '' },
        { title: '视频大小', cont: '' },
        { title: '视频目录', cont: '' },
        { title: '分辨率', cont: '' }
      ],
      tag: ''
    }
  },
  computed: {
    videoData() {
      return this.$store.state.videoStatus.videoData
    },
    labels() {
      let arr = []
      if (this.videoData.labels) {
        arr = this.videoData.labels.split(',').filter(function(s) {
          return s && s.trim()
        })
      }
      return arr
    },
    tags() {
      let arr = []
      if (this.videoData.tags) {
        arr = this.videoData.tags.split(',').filter(function(s) {
          return s && s.trim()
        })
      }
      return arr
    },
    uuid() {
      return this.videoData.uuid
    }
  },
  watch: {
    videoData: {
      handler: function(data) {
        this.informationData[0].cont = data.cover_url
        this.informationData[1].cont = data.titles
        this.informationData[2].cont = timesToHMS(data.video_duration)
        this.informationData[3].cont = data.source
        this.informationData[4].cont = data.create_time
        this.informationData[5].cont = initSize(data.video_size)
        this.informationData[6].cont = data.dir_name
        this.informationData[7].cont = `${data.video_width}*${data.video_height}`
      }
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    addTag() {
      if (!this.tag.trim()) {
        this.$message.warning('请输入标签内容')
        return
      }
      if (this.labels.includes(this.tag)) {
        this.$message.error('不能添加重复标签')
        return
      }
      this.labels.push(this.tag)
      this.tag = ''
      this.setData()
    },
    async setData() {
      const params = {
        labels: this.labels.join(',')
      }
      const { err, res } = await this.$put(`/videos/${this.uuid}`, params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.$message.success('添加成功')
    }
  }
}
</script>

<style scoped lang="scss">
.information {
  max-height: 75vh;
  overflow: scroll;

  .information-item {
    display: flex;
    margin-bottom: 20px;

    .title {
      width: 60px;
      flex-shrink: 0;
      font-size: 14px;
      color: #a0a0a0;
      margin-right: 20px;
      word-break: keep-all;
    }

    .cont {
      flex: 1;
      padding-right: 50px;

      img {
        width: 160px;
        height: 90px;
      }

      span {
        font-size: 14px;
        color: #404040;
        line-height: 20px;
        word-break: break-all;//只对英文起作用，以字母作为换行依据
        word-wrap: break-word; //只对英文起作用，以单词作为换行依据
        white-space: pre-wrap; //只对中文起作用，强制换行
      }
    }
  }

  .item {
    display: flex;
    flex-wrap: wrap;

    .title {
      width: 100%;
      margin-bottom: 22px;
      font-weight: 600;
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
