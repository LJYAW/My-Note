<!--
 * @Author: your name
 * @Date: 2021-07-23 15:09:56
 * @LastEditTime: 2021-09-27 19:29:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/additional/components/videoTail.vue
-->
<template>
  <div class="video-tail">
    <el-form-item class="videotail-box" label="自定义片尾:">
      <div class="additional-list">
        <div
          v-for="(item,index) in tempVideoTail"
          :key="index"
          class="item-list"
          :class="tabIndex===index?'activeClass':''"
          @click="tabClick(item,index)"
        >
          <img v-if="item.cover_url" :src="item.cover_url" alt="" class="icon-img">
          <div v-if="item.label">{{ item.label }}</div>
          <div v-show="!item.label" class="icon-play el-icon-video-play" @click.stop="playVideo(item)" />
        </div>
      </div>
      <!-- 上传 -->
      <div class="el-upload el-upload--picture-card uploadBtn" @click="upload()">
        <i class="el-icon-plus" />
      </div>
    </el-form-item>
    <base-dialog
      :show.sync="dialogVisible"
      :show-close="false"
      width="700px"
      title="自定义片尾"
    >
      <AudioListDialog
        key="tail"
        title="上传片尾"
        sub-type="片尾"
        :tem-list="subTitleTailList"
        :file-type="fileType"
        tag="video_end"
        @resetData="resetData"
      />
    </base-dialog>
    <base-dialog :show.sync="dialogPlayShow" :show-close="false" width="700px">
      <PlayVideo :url="url" @closeFun="closeFun" />
    </base-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import AudioListDialog from '../model/audioListModel'
import minxi from '../js/minix'
import PlayVideo from '../model/playVideo'
export default {
  components: {
    AudioListDialog,
    PlayVideo
  },
  mixins: [minxi],
  props: {

  },
  data() {
    return {
      url: '',
      dialogPlayShow: false,
      fileType: 'video/mp4',
      videoTailList: [],
      videoTail: []
    }
  },
  computed: {
    ...mapState('jianshi', ['subTitleTailList']),
    tempVideoTail() {
      const arr = JSON.parse(JSON.stringify(this.subTitleTailList)).splice(0, 4)
      arr.unshift({ label: '无片尾' })
      return arr
    }
  },
  watch: {
  },
  created() {
    this.initData()
  },
  mounted() {
  },
  methods: {

    playVideo(item) {
      this.dialogPlayShow = true
      this.url = item.file_url
    },
    // 播放视频回调函数
    closeFun() {
      this.dialogPlayShow = false
    },
    // 设置数据
    setData(item) {
      this.$store.commit('jianshi/SET_TAILID', item.label ? null : item)
    },

    // 更新数据
    resetData(obj) {
      this.dialogVisible = false
      if (obj) {
        const index = this.tempVideoTail.findIndex(item => item.id === obj.id)
        let item; let id
        if (index > -1) {
        // 找到值
          item = this.tempVideoTail[index]; id = item.id
          this.tabIndex = index
        } else {
          item = obj; id = obj.id
          this.tempVideoTail.splice(1, 1, obj)
          this.tabIndex = 1
        }
        this.$store.commit('jianshi/SET_TAILID', obj)
      } else {
        this.initData()
      }
    },
    async initData() {
      const params = {
        query: 'media_type:video,type:片尾',
        types: '片尾',
        sortby: 'id',
        order: 'desc',
        limit: 10000,
        page: 1
      }
      await this.$store.dispatch('jianshi/getSubtitleData', params)
    }

  }
}
</script>

<style scoped lang="scss">

</style>
