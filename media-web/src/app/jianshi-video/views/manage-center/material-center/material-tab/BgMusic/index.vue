<!--
 * @Author: your name
 * @Date: 2021-08-31 15:09:24
 * @LastEditTime: 2021-10-11 16:48:53
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/material-center/material-tab/BgMusic/index.vue
-->
<template>
  <div class="bg-music-wrap">
    <ul v-infinite-scroll="load" class="infinite-list">
      <div v-for="(item,index) in materialAudioData" :key="item.id" class="audio-item infinite-list-item">
        <div class="audio-img">
          <svg-icon icon-class="bg-music" class="music-icon" />
          <svg-icon
            :icon-class="item.play_audio?'suspend':'play'"
            class="play-icon"
            @click.native="audioPlay(item,index)"
          />
        </div>
        <div class="audio-msg">
          <p class="audio-name">{{ item.title }}</p>
          <p class="audio-duration">{{ item.duration*1000|timesToHMS }}</p>
        </div>
        <div class="delete-btn">
          <span @click="deleteMaterial(item.uuid)">
            <svg-icon icon-class="delete-red" />
          </span>
        </div>
      </div>
    </ul>
    <div v-if="demo_sound_url" class="audio-wrap cp">
      <audioPlay :url="demo_sound_url" :play="play_audio" @end="playEnd" />
    </div>
  </div>
</template>

<script>
import audioPlay from '@/components/AudioPlay/index'
import setQueryParams from '@/utils/setQueryParams.js'
export default {
  components: {
    audioPlay
  },
  props: {

  },
  data() {
    return {
      materialAudioData: [],
      play_audio: false,
      audioIndex: 0,
      demo_sound_url: '',
      page: 1,
      limit: 20,
      count: 0,
      loading: false
    }
  },
  computed: {

  },
  watch: {

  },
  async created() {
    await this.getInitData()
    this.demo_sound_url = this.materialAudioData[0].file_url
  },
  mounted() {

  },
  methods: {
    load() {
      if (this.materialAudioData.length < this.count) {
        this.page++
        this.getInitData()
      }
    },
    audioPlay(obj, index) {
      this.audioIndex = index
      this.playEnd()
      this.materialAudioData.forEach((item, audioIndex) => {
        if (index !== audioIndex) {
          this.$set(this.materialAudioData[audioIndex], 'play_audio', false)
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
      this.$set(this.materialAudioData[this.audioIndex], 'play_audio', !this.materialAudioData[this.audioIndex].play_audio)
    },
    async getInitData() {
      this.loading = true
      const query = {
        type: '背景音乐',
        source: '个人'
      }
      const params = {
        page: this.page,
        limit: this.limit,
        query: setQueryParams(query)
      }
      const { err, res } = await this.$get('/materials', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.loading = false
      this.count = res.count
      this.materialAudioData = this.materialAudioData.concat(res.data)
    },
    async deleteMaterial(id) {
      const { err, data } = await this.$deleteRun(`/materials/${id}`)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        this.page = 1
        this.materialAudioData = []
        this.getInitData()
      }
    }
  }
}
</script>

<style scoped lang="scss">
.bg-music-wrap {
  margin-top: 10px;
  width: calc(100% + 25px);

  .infinite-list {
    height: calc(100vh - 230px);
    overflow-y: auto;

    .audio-item {
      width: 236px;
      padding: 10px;
      margin-right: 24px;
      margin-bottom: 30px;
      display: inline-flex;

      //   &:hover {
      background: #f7f8f9;
      border-radius: 4px;
      //   }
      .audio-img {
        position: relative;

        &:hover {

          .play-icon {
            opacity: 1;
            transition: all .4s;
          }
        }

        .music-icon {
          width: 36px;
          height: 42px;
          margin-right: 10px;
        }

        .play-icon {
          width: 36px;
          height: 42px;
          position: absolute;
          top: 0;
          left: 0;
          opacity: 0;
          cursor: pointer;
        }

      }

      .audio-msg {
        color: #404040;
        font-size: 14px;
        flex: 1;
        width: 0;

        .audio-name {
          margin-bottom: 10px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }

        .audio-duration {
          line-height: 14px;
          opacity: .6;
        }
      }

      .delete-btn {
        margin-left: 40px;
        display: flex;
        align-items: center;

        span {
          background: #fff;
          padding: 3px;
          display: block;
          border-radius: 4px;

          svg {
            display: block;
          }
        }
      }
    }
  }
}
</style>
