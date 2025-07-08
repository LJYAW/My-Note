<!--
 * @Author: your name
 * @Date: 2021-07-23 15:04:24
 * @LastEditTime: 2021-09-26 18:12:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/voice/components/timbre.vue
-->
<template>
  <div class="timbre">
    <base-audio-play :url="audio_url" :play="play" @playOver="playOver" />
    <el-form-item class="timbar-box comment-box">
      <el-switch v-model="form.timbreSwitch" active-color="#13ce66" inactive-color="#DEDEDE" disabled class="switch-btn" />
      <el-form-item label="替换原声选择:">
        <div class="audio-select">
          <el-select
            v-model="form.timbre"
            placeholder="请选择音色"
            size="mini"
            value-key="id"
          >
            <el-option
              v-for="item in audioTimbreList"
              :key="item.id"
              class="pl-30px"
              :label="item.title"
              :value="item"
            />
          </el-select>
          <svg-icon
            class="el-icon-video-play icon-box"
            :icon-class="play?'audioPause':'audioPlay'"
            @click="playAudio()"
          />
        </div>
      </el-form-item>
    </el-form-item>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      audio_url: '',
      play: false,
      form: {
        timbreSwitch: false,
        timbre: null
      }
    }
  },
  computed: {
    ...mapState('jianshi', ['audioTimbreList', 'submitData'])
  },
  watch: {
    // 回显
    // submitData: {
    //   handler(newName, oldName) {
    //     this.form.timbre = newName.update_audio_id
    //   },
    //   immediate: true
    // },

    audioTimbreList: {
      handler(newName, oldName) {
        this.form.timbre = newName[0]
      }
    },
    // 监听音色下拉值，切换音乐播放暂停
    'form.timbre': {
      handler(newName, oldName) {
        if (newName) {
          this.play = false
          this.setUrl(newName)
        }
      },
      immediate: true
    }
  },
  created() {
    this.getTimbreData()
  },
  mounted() {

  },
  methods: {
    // 音频播放结束
    playOver(val) {
      if (val) {
        this.play = false
      }
    },
    // 获取音色数据
    async getTimbreData() {
      const params = {
        query: 'media_type:audio,type:播报音色,source:系统',
        sortby: 'id',
        order: 'desc',
        limit: 10000,
        page: 1,
        types: '音色'
      }
      await this.$store.dispatch('jianshi/getSubtitleData', params)
    },
    // 播放暂停音乐切换
    playAudio() {
      this.play = !this.play
    },
    // 设置音乐url
    setUrl(val) {
      // const url = ''
      // if (this.audioTimbreList.length > 0) {
      //   url = this.audioTimbreList.find(item => item.id === val).file_url
      // }
      this.audio_url = val.file_url
      this.$store.commit('jianshi/SET_ADUIOID', val)
    }
  }
}
</script>

<style scoped lang="scss">

</style>
