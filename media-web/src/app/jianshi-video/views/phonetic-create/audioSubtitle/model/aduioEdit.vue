<!--
 * @Author: your name
 * @Date: 2021-09-07 15:37:42
 * @LastEditTime: 2021-09-24 16:35:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/create-center/intellectCreate/components/audioSubtitle/model/aduioEdit.vue
-->
<template>
  <div class="aduio-edit">
    <!-- {{ setTimes(aduioData.start_time) }} - {{ setTimes(aduioData.end_time) }} -->
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item class="item-list svg-box">
        <div class="play-box">
          <svg-icon :icon-class="play?'video-play':'video-pluse'" class="video-icon" @click="playClick()" />
          <base-audio-play ref="aduioPlayer" :url="audioUrl" :play="play" @timeupdate="timeupdate" />
          <span>{{ curentTimes }}</span>
        </div>
        <span class="times">{{ setTimes(aduioData.end_time-aduioData.start_time) }}</span>
      </el-form-item>
      <el-form-item class="item-list">
        <el-input v-model="aduioData.txt" />
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" size="mini" @click="save">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import msToSecond from '../js/ms-to-second'
export default {
  components: {

  },
  props: {
    audioUrl: {
      type: String,
      default: ''
    },
    itemData: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      play: false,
      form: {
      },
      aduioData: {},
      curentTimes: ''
    }
  },
  computed: {

  },
  watch: {
    itemData: {
      handler(newName, oldName) {
        this.aduioData = JSON.parse(JSON.stringify(newName))
        this.curentTimes = msToSecond(newName.start_time)
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    timeupdate(e) {
      const timeStamp = e.target.currentTime
      this.curentTimes = msToSecond(timeStamp * 1000)
      if (timeStamp >= parseInt(this.aduioData.end_time / 1000)) {
        this.play = false
      }
    },
    setTimes(times) {
      return msToSecond(times)
    },
    save() {
      this.$emit('resetData', this.aduioData)
    },
    playClick() {
      this.play = !this.play
      const times = parseInt(this.aduioData.start_time / 1000)
      this.$refs.aduioPlayer.$refs.audio.currentTime = times
      this.curentTimes = msToSecond(this.aduioData.start_time)
    }
  }
}
</script>

<style scoped lang="scss">
.aduio-edit {

  ::v-deep  .el-form-item__content {
    margin-left: 0 !important;
    display: flex;
  }

  .btns {
    width: 100%;

    button {
      width: 100%;
    }
  }

  .item-list {
    width: 100%;
    height: 40px;
    background: #f8f8f8;
    border-radius: 4px;
    padding: 0 20px 0 20px;

    ::v-deep input {
      border-color: transparent;
      background: transparent;
    }
  }

  .svg-box {

    .el-form-item__content {
      display: flex;
      justify-content: space-between;
      width: 100%;

      .play-box {
        flex: 1;
        display: flex;
        align-items: center;
        padding-left: 15px;

        .video-icon {
          margin-right: 6px;
        }
      }

      .times {
        min-width: 60px;
        text-align: right;
      }
    }
  }
}
</style>
