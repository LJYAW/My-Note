<!--
 * @Author: your name
 * @Date: 2021-07-23 15:04:39
 * @LastEditTime: 2021-09-26 17:17:39
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/voice/components/music.vue
-->
<template>
  <div class="music">
    <el-form-item class="music-box comment-box">
      <el-switch
        v-model="form.musicSwitch"
        active-color="#13ce66"
        inactive-color="#DEDEDE"
        class="switch-btn"
        @change="switchChange"
      />
      <el-form-item label="视频背景音乐:" class="video-back">
        <div class="music-title">自定义背景音乐</div>
        <span v-if="muasicName" class="back-music-name">{{ muasicName }}</span>
        <el-button size="small" @click.native="setBgmusic">选择背景音乐</el-button>
      </el-form-item>
    </el-form-item>
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="700px" title="背景音乐">
      <MusicModel @resetData="resetData" @saveData="saveData" />
    </base-dialog>
  </div>
</template>

<script>
import MusicModel from '../model/musicModel'
import { mapState } from 'vuex'
export default {
  components: {
    MusicModel
  },
  props: {

  },
  data() {
    return {
      dialogVisible: false,
      form: {
        musicSwitch: false
      },
      muasicName: ''
    }
  },
  computed: {
    ...mapState('jianshi', ['backMusicList', 'submitData'])
  },
  watch: {
    // 回显
    // submitData: {
    //   handler(newName, oldName) {
    //     if (newName.bg_musice) {
    //       this.muasicName = this.backMusicList.find(item => item.id === newName.bg_musice).title
    //     }
    //   },
    //   immediate: true
    // }
  },
  created() {
    this.getMusicData()
  },
  mounted() {

  },
  methods: {
    // 保存背景音乐id
    saveData(obj) {
      this.dialogVisible = false
      if (!obj.id) return
      this.$store.commit('jianshi/SET_BACKMUSICID', obj)
      this.muasicName = obj.title
      this.form.musicSwitch = true
    },
    // 获取背景音乐数据
    async getMusicData() {
      const params = {
        query: 'media_type:audio,type:背景音乐',
        sortby: 'id',
        order: 'desc',
        limit: 10000,
        page: 1
      }
      const { err, res } = await this.$get('/materials', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      const { data } = res
      this.$store.commit('jianshi/SET_BACKMUSICLIST', data)
    },
    // 回显数据
    upData(val) {
      if (this.bg_music.length > 0) {
        const arr = JSON.parse(JSON.stringify(this.bg_music))
        this.$store.commit('jianshi/SET_MUSICNAME', arr.find(item => item.id === val).category_name)
      }
    },
    // 切换switch开关
    switchChange(ev) {
      let name = ''; let id = ''; let obj = null
      if (ev && !this.muasicName) {
        obj = this.backMusicList[0]
        name = this.backMusicList[0].title; id = this.backMusicList[0].id
      } else {
        obj = null
        name = ''; id = ''
      }
      this.$store.commit('jianshi/SET_BACKMUSICID', obj)
      this.muasicName = name
    },
    // 更新数据
    resetData() {
      this.getMusicData()
    },
    // 点击展示背景音乐
    setBgmusic() {
      this.dialogVisible = true
    }
  }
}
</script>

<style  lang="scss">

</style>
