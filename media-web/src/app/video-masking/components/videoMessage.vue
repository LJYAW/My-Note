<!--
 * @Author: your name
 * @Date: 2021-09-08 18:54:49
 * @LastEditTime: 2021-10-08 14:53:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-masking/components/videoMessage.vue
-->
<template>
  <div>
    <div class="tool-pad">
      <el-tabs v-model="activeName">
        <el-tab-pane name="first">
          <span slot="label"><i class="el-icon-tickets" /> 视频信息</span>
          <div class="information">
            <div v-for="item in informationData" :key="item.title" class="information-item">
              <div class="title">{{ item.title }}</div>
              <div class="cont">
                <!-- <div v-if="item.title==='项目名称'&&isEdit">
                  <el-input
                    v-model="item.cont"
                    style="max-width: 300px;margin-right: 10px;"
                    placeholder=""
                    size="small"
                    clearable
                    :maxlength="50"
                    @keydown.enter.native="submit"
                  />
                  <el-button type="primary" size="mini" @click="submit">确定</el-button>
                </div> -->

                <span>{{ item.cont }}</span>
                <!-- <span
                  v-if="item.title==='项目名称'&&!isEdit"
                  style="color: #5675e8;margin-left: 10px;cursor: pointer"
                  @click="isEdit = true"
                >修改</span> -->
              </div>
            </div>
          </div>

        </el-tab-pane>
      </el-tabs>
    </div>
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
      activeName: 'first',
      isEdit: false,
      informationData: [
        // { title: '项目名称', cont: '' },
        { title: '视频时长', cont: '' },
        { title: '上传时间', cont: '' },
        { title: '视频大小', cont: '' },
        { title: '分辨率', cont: '' }
      ]
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {
    const videoMarkDetails = JSON.parse(sessionStorage.getItem('videoMarkDetails'))
    console.log(videoMarkDetails)
    // this.informationData[0].cont = videoMarkDetails.titles
    this.informationData[0].cont = timesToHMS(videoMarkDetails.video_duration)
    this.informationData[1].cont = videoMarkDetails.create_time
    this.informationData[2].cont = initSize(videoMarkDetails.video_size)
    this.informationData[3].cont = `${videoMarkDetails.video_width}*${videoMarkDetails.video_height}`
  },
  methods: {
    submit() {
      if (!this.informationData[0].cont.trim()) {
        this.$message.error('项目名称不能为空')
        return
      }
      const videoMarkDetails = JSON.parse(sessionStorage.getItem('videoMarkDetails'))
      videoMarkDetails.titles = this.informationData[0].cont
      sessionStorage.setItem('videoMarkDetails', JSON.stringify(videoMarkDetails))
      this.isEdit = false
    }
  }
}
</script>

<style scoped lang="scss">
.tool-pad {
  flex-shrink: 0;
  width: 100%;
  max-height: 88vh;
  height: 950px;
  background: #fff;
  padding: 20px;
}

.information {
  margin-top: 30px;
}

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
</style>
