<!--
 * @Author: your name
 * @Date: 2021-09-15 15:05:21
 * @LastEditTime: 2021-09-28 11:52:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/videoEffect/modular/voice/model/musicModel.vue
-->
<template>
  <div class="bg-music-wrap">
    <Upload
      id="Upload"
      btn-name="上传音乐"
      class="upload-position"
      file-type="audio/mpeg,audio/mpeg"
      @getProgress="getProgress"
      @getBosKey="getBosKey"
      @getFileData="getFileData"
    />
    <div v-infinite-scroll="load" class="list" infinite-scroll-immediate>
      <div class="transition-wrap">
        <div
          v-for="(item,index) in audioArr"
          :key="item.id"
          class="audio-item"
          :class="audioIndex===index?'activeClass':''"
          @click="chooseItem(item,index)"
        >
          <div class="audio-img">
            <svg-icon icon-class="bg-music" class="music-icon" />
            <svg-icon
              :icon-class="item.play_audio?'suspend':'play'"
              class="play-icon"
              @click.stop="audioPlay(item,index)"
            />
          </div>
          <div class="audio-msg">
            <p class="audio-name">{{ item.title }}</p>
            <p class="audio-duration">{{ item.duration|timesToHMS }}</p>
          </div>
          <div v-if="item.progress === '上传中'" class="progress-item">
            <el-progress :text-inside="true" :stroke-width="13" :percentage="complete" />
          </div>
        </div>
      </div>
    </div>
    <div v-if="demo_sound_url" class="audio-wrap cp">
      <base-audio-play :url="demo_sound_url" :play="play_audio" @end="playEnd" />
    </div>
    <div class="btns">
      <el-button type="primary" @click="determine()">确定</el-button>
    </div>
  </div>
</template>

<script>
import Upload from '../../../../components/upload.vue'
export default {
  components: {
    Upload
  },
  props: {
  },
  data() {
    return {
      complete: 0,
      play_audio: false,
      audioIndex: '',
      demo_sound_url: '',
      audioArr: [],
      itemData: {},
      // fileName: '',
      // duration: 0,
      count: 0,
      page: 1,

      fileData: {}
    }
  },
  computed: {
  },
  watch: {
  },
  created() {
    this.getinitData()
  },
  mounted() {

  },
  methods: {
    load() {
      if (this.audioArr.length < this.count) {
        this.page++
        this.getinitData()
      }
    },
    async getinitData() {
      const params = {
        query: 'media_type:audio,type:背景音乐',
        sortby: 'id',
        order: 'desc',
        limit: 20,
        page: this.page
      }
      const { err, res } = await this.$get('/materials', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      const { data } = res
      this.count = res.count
      this.audioArr = this.audioArr.concat(data)
    },
    // getDuration(data) {
    //   this.duration = data
    // },
    getProgress(ev) {
      this.complete = ev
    },
    // getFileName(name) {
    //   this.audioArr.unshift({
    //     progress: '上传中'
    //   })
    //   this.fileName = name
    // },
    getFileData(obj) {
      this.audioArr.unshift({
        progress: '上传中'
      })
      this.fileData = obj
    },
    determine() {
      this.$emit('saveData', this.itemData)
    },
    chooseItem(item, index) {
      this.audioIndex = index
      this.itemData = item
    },
    async getBosKey(file) {
      const { key } = file.fileData.body
      this.uploadData(key, this.fileName, this.duration)
    },
    async uploadData(key, name, duration) {
      let params = {
        file_key: key,
        media_type: 'audio',
        source: '个人',
        type: '背景音乐'
        // title: name,
        // duration: parseInt(duration)
      }
      params = Object.assign(params, this.fileData)

      const { err, res } = await this.$post('/materials', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.audioArr.splice(0, 1)
      this.$message({
        message: '上传成功!',
        type: 'success'
      })
      this.audioArr = []
      await this.getinitData()
      this.$emit('resetData')
      this.complete = 0
    },
    audioPlay(obj, index) {
      this.audioIndex = index
      this.playEnd()
      this.audioArr.forEach((item, audioIndex) => {
        if (index !== audioIndex) {
          this.$set(this.audioArr[audioIndex], 'play_audio', false)
        }
        if (item.play_audio === true) {
          this.play_audio = true
        }
      })
      this.demo_sound_url = obj.file_url
    },
    // 播放结束
    playEnd() {
      this.play_audio = false
      this.$set(this.audioArr[this.audioIndex], 'play_audio', !this.audioArr[this.audioIndex].play_audio)
    }
  }
}
</script>

<style scoped lang="scss">
@import "./index.scss"
</style>
