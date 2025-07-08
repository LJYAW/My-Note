<!--
 * @Author: your name
 * @Date: 2020-11-17 16:39:06
 * @LastEditTime: 2020-12-10 16:46:13
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/user_info/pages/my_library/my_library_music.vue
-->
<template>
  <div class="content">
    <loading v-if="status_loading" />
    <div v-else>
      <el-button round class='upload-btn mb-24px' @click.native='uploadBtn'>+上传素材</el-button>
      <cardList :list='musicList' @handerClick="handerClick" v-if='list.length'>
        <template slot='progress'>
          <el-progress :text-inside="true" :stroke-width="13" :percentage="complete"></el-progress>
        </template>
        <template #img="slotProps">
          <div class='text-center pt-50px'>
            <vsvg icon='iconyinfu' class='iconfont fz-40px w-100' />
          </div>
          <vsvg :icon='!slotProps.item.play_audio?"iconyinfukaishijian":"iconyinfuzantingjian"'
            class='fz-40px iconbofang' @click.native.stop="audioPaly(slotProps)" />
          <div class="icon-wrap d-flex justify-content-between w-100">
            <a @click.stop="deleteItem(slotProps.item)"
              class="align-self-end ml-auto">
              <i class="iconfont icondelect fc-white"></i>
            </a>
          </div>
        </template>
        <template #title="slotProps">
          <p class="title" @click="updateTitle(slotProps.item)">{{slotProps.item.title}}</p>
        </template>
        <template #details="slotProps">
          <div class='d-flex justify-content-between'>
            <p>{{slotProps.item.file_size}}M</p>
            <p>{{getDuration(slotProps.item.duration_ms)}}</p>
          </div>
        </template>
      </cardList>
      <div class='no-data' v-else>
        <img src="../../../../assets/images/user_info/no_content.png" alt="">
        <p class='text-center'>暂无内容</p>
      </div>
    </div>
    <div class="mb-50px d-flex justify-content-end" v-if='total>limit'>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="limit"
        layout="prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <div class="audio-wrap cp">
      <audioPlay :url="demo_sound_url" :play="play_audio" />
    </div>
    <updateTitle @Modalclose="Modalclose"
      v-if="modaName =='updateTitle'" :updateItem='update_item' type='music' />
  </div>
</template>

<script>
import cardList from '@/components/card_list/index'
import audioPlay from '@/components/audio_play'
import uploadM from '../../components/upload_m'
import updateTitle from '../../components/update_title'
export default {
  props: ['activeId'],
  data() {
    return {
      page: 1,
      limit: 12,
      file_type: '',
      total: 0,
      currentPage: 1,
      list: [],
      musicList: [],
      timeout: null,
      status_loading: false,
      finished_status: true,
      refresh_time: null,
      modaName: '',
      video_options: {},
      video_details: {},
      complete: 0,
      play_audio: false,
      demo_sound_url: ''
    }
  },
  computed: {},
  watch: {
    activeId(val) {
      this.getData()
    }
  },
  methods: {
    getData() {
      this.$get('/intelligent-creation/user-bg-music-list').then(res => {
        if (res.data.code == '0000') {
          let data = res.data.data
          this.list = data.list
          this.list.forEach(item => {
            this.$set(item, 'play_audio', false)
          })
          this.total = this.list.length
          this.musicList = this.getList()
          this.demo_sound_url = this.list[0].audio_url
        }
      })
    },
    getList() {
      let begin = (this.currentPage - 1) * this.limit
      let end = this.currentPage * this.limit
      return this.list.slice(begin, end)
    },
    handerClick() {
      return
    },
    handleCurrentChange(val) {
      this.page = val
      this.currentPage = val
      this.musicList = this.getList()
    },
    uploadMyFile(obj) {
      this.complete = 0
      var formData = new FormData()
      formData.append('title', obj.title)
      formData.append('resource', obj.file)
      this.musicList.unshift({
        type: '上传中'
      })
      this.uploadMusic(formData)
    },
    uploadBtn() {
      this.$layer.iframe({
        content: {
          content: uploadM,
          parent: this,
          data: {
            fileType: 'audio/mpeg'
          }
        },
        area: ['800px', '550px'],
        title: '上传素材',
        maxmin: true,
        shade: true, //是否显示遮罩
        shade: false,
        shadeClose: true,
        cancel: () => {
          // 关闭弹窗事件
        }
      })
    },
    uploadMusic(formData) {
      this.$axios
        .post('/intelligent-creation/upload-bg-music', formData, {
          onUploadProgress: progressEvent => {
            this.complete = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          }
        })
        .then(res => {
          if (res.data.code == '0000') {
            this.$alertMsg('上传成功')
            this.getData()
          } else {
            this.$alertMsg('上传失败')
          }
        })
    },
    // 删除素材
    deleteItem(item) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // 删除本地素材
          this.$deleteRun('/intelligent-creation/delete-bg-music', {
            data: { id: item.id }
          }).then(res => {
            if (res.data.code == '0000') {
              this.$alertMsg('删除成功')
              this.getData()
            } else {
              this.$alertMsg(res.data.msg)
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    getDuration(t) {
      var NowtimeValue = t
      var nowH = parseInt(NowtimeValue / 3600)
      var nowM = parseInt((NowtimeValue % 3600) / 60)
      var nowS = parseInt(NowtimeValue % 60)
      nowH < 10 ? (nowH = '0' + nowH) : (nowH = nowH)
      nowM < 10 ? (nowM = '0' + nowM) : (nowM = nowM)
      nowS < 10 ? (nowS = '0' + nowS) : (nowS = nowS)
      return nowH + ':' + nowM + ':' + nowS
    },
    //播报音色
    audioPaly(val) {
      this.play_audio = false
      this.$set(this.list[val.index], 'play_audio', !this.list[val.index].play_audio)
      this.list.forEach((item, index) => {
        if (index != val.index) {
          this.$set(this.list[index], 'play_audio', false)
        }
        if (item.play_audio == true) {
          this.play_audio = true
        }
      })
      this.demo_sound_url = val.item.audio_url
    },
    updateTitle(item) {
      this.modaName = 'updateTitle'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'updateTitle')
      })
      this.update_item = item
    },
    Modalclose() {
      this.modaName = ''
    }
  },
  components: { cardList, audioPlay, updateTitle },
  created() {
    this.getData()
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
</style>

