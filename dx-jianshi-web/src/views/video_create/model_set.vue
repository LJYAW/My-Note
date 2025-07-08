<template>
  <div class="model-set">
    <p class="fz-16px mb-30px">效果选择</p>
    <!-- <loading  /> -->
    <el-form v-if="!status_loading" ref="form" :model="form" label-width="120px" label-position="left" class="text-left">
      <div class="wrap">
        <span class="sub-title">声音</span>

        <el-form-item class="audio-select" label="播报音色选择：">
          <el-select v-model="form.timbre" placeholder="请选择音色" @change="changeAudio" size="mini" style="width: 370px">
            <el-option class="pl-30px" v-for="(item, index) in tts_per" :key="index" :label="item.name" :value="item.tts_per_id"></el-option>
          </el-select>

          <i :class="['iconfont iconaudio fc-c fz-20px', [play_audio ? 'iconzanting' : 'iconbofang1']]" @click.stop="audioPaly()"></i>

          <el-checkbox label="设置默认音色" v-model="form.timbre_checkout" name="type" class="ml-30px"></el-checkbox>
        </el-form-item>
        <el-form-item label="视频背景音乐：">
          <el-radio-group v-model="form.bgm">
            <el-radio label="默认背景音乐"></el-radio>
            <el-radio label="自定义背景音乐"></el-radio>
          </el-radio-group>
          <el-button round size="mini" class="ml-20px" @click.native="setBgmusic">选择背景音乐</el-button>
        </el-form-item>
        <div class="audio-wrap cp">
          <audioPlay :url="demo_sound_url" :play="play_audio" />
        </div>
      </div>

      <div class="wrap">
        <span class="sub-title">画面</span>

        <table class="muban-table w-100">
          <tr>
            <td>视频模板：</td>
            <td>
              <div class="d-flex align-items-center">
                <div class="fz-12px d-flex flex-wrap video_end_id-wrap box-content">
                  <div
                    :class="['box text-center caption-bg-wrap ', { active: caption_id == item.id }]"
                    @click="caption_id = item.id"
                    v-for="(item, i) in caption_bg.built_in"
                    :key="i"
                  >
                    <img class="caption-bg-img" :src="item.image_url" />

                    <span class="sub-name cp hove-c" @click="showImg(item.image_url)">{{ item.name }}</span>
                  </div>
                </div>

                <div class="addicon cp ml-5px">
                  <i @click="showCaptionM" class="iconfont iconsolid_add"></i>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td>虚拟主持人：</td>
            <td>
              <div class="persenter-wrap d-flex flex-wrap align-items-center">
                <div v-for="img in virtual_presenter" :key="img.virtual_presenter_id">
                  <div
                    v-if="img.virtual_presenter_id == -1 || img.sex == tts_per.find(item => form.timbre == item.tts_per_id).sex"
                    :class="[
                      'persenter',
                      {
                        active: img.virtual_presenter_id == presen_select.virtual_presenter_id
                      }
                    ]"
                    @click="selectPersenter(img)"
                  >
                    <img v-if="img.cover_pic_url" :src="img.cover_pic_url" />
                    <p class="sub-name">{{ img.name }}</p>
                    <i v-if="img.virtual_presenter_id == presen_select.virtual_presenter_id" class="iconfont iconxuanzhong fc-c"></i>
                  </div>
                </div>

                <div class="d-flex align-items-center mb-30px">
                  <img :src="presen_postion_img" width="107" height="60" />
                  <div class="d-flex flex-column ml-15px">
                    <el-radio class="fz-12px my-10px" v-model="presen_radio" label="left" :disabled="!presen_select.positions.some(s => s == 'left')">位于画面左侧</el-radio>
                    <el-radio class="fz-12px" v-model="presen_radio" label="right" :disabled="!presen_select.positions.some(s => s == 'right')">位于画面右侧</el-radio>
                  </div>
                </div>
              </div>
            </td>
          </tr>
        </table>
      </div>

      <div class="wrap">
        <span class="sub-title">附加</span>

        <el-form-item label="自定义角标：" label-width="105px">
          <div class="d-flex flex-wrap">
            <div class="fz-12px d-flex video_end_id-wrap">
              <div :class="['box text-center', { active: !video_logo_id }]" @click="video_logo_id = null">
                <span class="fz-12px">无角标</span>
              </div>

              <div :class="['box text-center', { active: video_logo_id == item.id }]" @click="video_logo_id = item.id" v-for="(item, i) in video_logo" :key="i">
                <img :src="item.resource_url" />
                <i class="iconfont icondelect hove-c" @click.stop="deleteItem(item, 1)"></i>
              </div>

              <a class="fz-12px fc-c play-video mt-5px" style="text-decoration: underline;" @click="showImg('images/corner_marke.jpeg')">示例</a>
            </div>

            <!-- <a @click="example()"
             class="fc-c"
             style="text-decoration:underline">
            示例
            </a>-->
            <div class="ml-auto text-right d-flex flex-column justify-content-center">
              <upload
                button_name="上传图标"
                class="ml-auto"
                :disabled="video_logo.length > 4"
                @click.native="uploadFile('video_logo', video_logo.length)"
                @getFileUrl="getFileUrl"
              />
              <p class="fz-12px fc-999 mt-5px" style="line-height: 1">50px高度的png图</p>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="自定义片头：" label-width="105px">
          <div class="d-flex flex-wrap">
            <div class="fz-12px d-flex video_end_id-wrap">
              <div :class="['box text-center', { active: !video_begin_id }]" @click="video_begin_id = null">
                <span class="fz-12px">自动片头</span>
              </div>

              <div class="box fz-12px" v-if="file_loading && file_type == 'video_begin'">{{ this.complete }} %</div>

              <div :class="['box text-center', { active: video_begin_id == item.id }]" @click="video_begin_id = item.id" v-for="(item, i) in video_begin" :key="i">
                <img :src="item.cover_pic" />
                <i class="iconfont icondelect hove-c" @click="deleteItem(item, 2)"></i>

                <a class="fz-12px fc-c play-video" @click="playVideo(item)">播放</a>
              </div>
            </div>
            <div class="ml-auto text-right d-flex flex-column justify-content-center">
              <upload
                button_name="上传片头"
                class="ml-auto"
                file_type=" video/mp4"
                :disabled="video_begin.length > 4"
                @click.native="uploadFile('video_begin', video_begin.length)"
                @getFileUrl="getFileUrl"
              />
              <p class="fz-12px fc-999 mt-5px" style="line-height: 1">16:9 mp4格式的视频</p>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="自定义片尾：" label-width="105px">
          <div class="d-flex flex-wrap">
            <div class="fz-12px d-flex video_end_id-wrap">
              <div :class="['box text-center', { active: !video_end_id && video_end_type == 3 }]" @click="video_end_type = 3">
                <span class="fz-12px">默认片尾</span>
              </div>
              <div
                :class="['box text-center', { active: !video_end_id && video_end_type == 1 }]"
                @click="
                  video_end_id = null
                  video_end_type = 1
                "
              >
                <span class="fz-12px">无片尾</span>
              </div>

              <div class="box fz-12px" v-if="file_loading && file_type == 'video_end'">{{ this.complete }} %</div>

              <div
                :class="['box text-center', { active: video_end_id == item.id }]"
                @click="
                  video_end_id = item.id
                  video_end_type = 2
                "
                v-for="(item, i) in video_end"
                :key="i"
              >
                <img :src="item.cover_pic" />
                <i class="iconfont icondelect hove-c" @click="deleteItem(item, 3)"></i>

                <a class="fz-12px fc-c play-video" @click="playVideo(item)">播放</a>
              </div>
            </div>

            <div class="ml-auto text-right d-flex flex-column justify-content-center">
              <upload
                button_name="上传片尾"
                file_type=" video/mp4"
                class="ml-auto"
                :disabled="video_end.length > 4"
                @click.native="uploadFile('video_end', video_end.length)"
                @getFileUrl="getFileUrl"
              />
              <p class="fz-12px fc-999 mt-5px" style="line-height: 1">16:9 mp4格式的视频</p>
            </div>
          </div>
        </el-form-item>
      </div>
    </el-form>

    <!--背景音乐 -->
    <bgMusicM
      v-if="modal_show_name == 'musicm'"
      :bg_music="bg_music"
      @close="Modalclose"
      ref="bgMusicM"
      @upDataBgMusic="upDataBgMusic"
      @getBgmusicId="getBgmusicId"
      :custom="custom"
    ></bgMusicM>
    <!-- 播放片头片尾 -->
    <showVideoM v-if="modal_show_name == 'showVideoM'" @Modalclose="Modalclose" :video_details="video_details" :video_options="video_options" />

    <!-- 图片 -->
    <showImgM v-if="modal_show_name == 'showImgM'" @Modalclose="Modalclose" :img_url="modal_img_url" />

    <!-- 视频画面装饰： -->
    <showCaptionBg v-if="modal_show_name == 'showCaptionBg'" @setCaptionId="setCaptionId" @updataCaptionBgImg="updataCaptionBgImg" :caption_bg="caption_bg_temp" />
  </div>
</template>
<script>
import bgMusicM from './modal/bg_music_m'
import audioPlay from '@/components/audio_play'
import showVideoM from '@/components/show_video_m'
import showImgM from '@/components/show_img_m'
import showCaptionBg from '@/components/show_caption_bg'

export default {
  name: 'ModelSet',
  props: ['resetData'],
  data() {
    return {
      complete: 0,
      status_loading: false,
      file_loading: false,
      video_end_type: 3,
      form: {
        timbre: '1',
        set_caption_bg: true,
        timbre_checkout: true,
        bgm: '默认背景音乐',
        video_picture: ['去底部遮挡', '去台标']
      },
      file_type: '',
      video_logo_id: null,
      video_begin_id: null,
      video_end_id: null,
      bg_music_id: null,
      demo_sound_url: '',
      play_audio: false,
      tts_per: [], // 音色列表
      bg_music: [], // 背景音乐
      custom: [], // 上传的 音乐
      video_logo: [], // 角标
      video_begin: [], // 片头
      video_end: [], // 片尾
      caption_bg: [],
      caption_id: null,
      modal_show_name: '',
      video_details: {},
      video_options: {},
      modal_img_url: '',
      caption_bg_temp: [],
      virtual_presenter: [{ name: '无主持人', virtual_presenter_id: -1, positions: [] }],
      presen_radio: 'right',
      presen_select: {
        positions: [],
        virtual_presenter_id: -1
      }
    }
  },
  components: {
    bgMusicM,
    audioPlay,
    showVideoM,
    showImgM,
    showCaptionBg
  },
  computed: {
    presen_postion_img() {
      return require(`../../assets/images/host_${this.presen_radio}.png`)
    }
  },
  watch: {
    resetData(obj) {
      console.log('resetData -> obj', obj)
      // tts_per_id: this.form.timbre, // 播放音色
      //   bg_music_id: this.bg_music_id, // 如果有，传背景音乐id
      //   set_caption_bg: this.form.set_caption_bg ? 1 : 2, // 底部遮挡
      //   caption_bg_id: this.caption_id, // 底部遮挡ID
      //   video_logo_type: this.video_logo_id ? 1 : 2, // 角标类型 2无角标 1有角标
      //   video_logo_user_res_id: this.video_logo_id, // 当角标type=2，角标用户资源id
      //   video_begin_type: this.video_begin_id ? 2 : 1, // 片头类型 1自动片头 2上传素材片头
      //   video_begin_user_res_id: this.video_begin_id, // 当片头type=2 片头用户素材id
      //   video_end_type: this.video_end_type, // 片尾类型 1无片尾 2上传素材片尾 3自定义片尾
      //   video_end_user_res_id: this.video_end_id, // 当片尾type=2 片尾用户素材id

      obj.bg_music_id ? (this.form.bgm = '自定义背景音乐') : (this.form.bgm = '默认背景音乐')
      this.form.timbre = obj.tts_per_id
      this.bg_music_id = obj.bg_music_id
      this.form.set_caption_bg = obj.set_caption_bg
      this.caption_id = obj.caption_bg_id
      this.video_logo_id = obj.video_logo_type
      this.video_logo_id = obj.video_logo_user_res_id
      this.video_begin_id = obj.video_begin_type
      this.video_begin_id = obj.video_begin_user_res_id
      this.video_end_type = obj.video_end_type
      this.video_end_id = obj.video_end_user_res_id
    }
  },
  methods: {
    selectPersenter(item) {
      console.log('🚀 ~ file: model_set.vue ~ line 316 ~ selectPersenter ~ item', item)
      if (this.presen_select.virtual_presenter_id == item.virtual_presenter_id) {
        this.presen_select = {
          positions: [],
          virtual_presenter_id: -1
        }
      } else {
        this.presen_select = item
      }
      !item.positions.some(s => s == 'left') ? (this.presen_radio = 'right') : (this.presen_radio = 'left')
    },
    setCaptionId(obj) {
      console.log('setCaptionId -> id', obj)
      this.caption_id = obj.item.id

      if (obj.custom) {
        let built_in = JSON.parse(JSON.stringify(this.caption_bg.built_in))
        built_in[1] = obj.item
        this.$set(this.caption_bg, 'built_in', built_in)
      } else {
        let built_in = JSON.parse(JSON.stringify(this.caption_bg_temp.built_in))
        this.$set(this.caption_bg, 'built_in', built_in)
      }
    },
    updataCaptionBgImg() {
      this.getListOfoptions()
    },
    showCaptionM() {
      this.modal_show_name = 'showCaptionBg'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showCaptionBg')
      })
    },
    showImg(url) {
      this.modal_show_name = 'showImgM'
      this.modal_img_url = url

      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showVideoM')
      })
    },
    upDataBgMusic() {
      this.getListOfoptions()
    },
    Modalclose() {
      this.modal_show_name = ''
    },
    playVideo(item) {
      this.modal_show_name = 'showVideoM'
      this.video_details.title = ''
      this.video_options = {
        width: '800',
        height: '450',
        poster: item.cover_pic,
        autoplay: false,
        src: item.resource_url
      }

      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showVideoM')
      })
    },
    close() {
      this.modal_show_name = ''
    },
    audioPaly() {
      let id = this.form.timbre
      let item = this.tts_per.find(item => {
        return item.tts_per_id == id
      })

      if (this.demo_sound_url == item.demo_sound_url) {
        this.play_audio = !this.play_audio
      } else {
        this.demo_sound_url = item.demo_sound_url
        this.play_audio = true
      }
    },
    changeAudio() {
      this.play_audio = false
    },
    // 删除素材
    deleteItem(item, type) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // 删除本地素材
          this.$deleteRun('/intelligent-creation/delete-user-resource', {
            data: { id: item.id }
          }).then(res => {
            if (res.data.code == '0000') {
              this.$alertMsg('删除成功')
              this.getMyFileList()
            } else {
              this.$alertMsg(res.data.msg)
            }
            this.setDeleteAfterData(type)
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    setDeleteAfterData(type) {
      switch (type) {
        case 1:
          this.video_logo_id = null
          break
        case 2:
          this.video_begin_id = null
          break
        case 3:
          this.video_end_id = null
          this.video_end_type = 3
          break
      }
    },
    // 获取视频时长
    getVideoDuration(url) {
      return new Promise((resolve, reject) => {
        let videoDom = document.createElement('video')
        videoDom.src = url
        let duration = 0
        videoDom.addEventListener('durationchange', function(e) {
          duration = parseInt(videoDom.duration * 1000)
          resolve(duration)
        })
      })
    },
    uploadFile(type, arr_length) {
      console.log('uploadFile -> arr_length', arr_length)

      if (arr_length > 4) {
        this.$alertMsg('最多上传 5 个素材')
        return
      }

      this.file_type = type
    },
    async getFileUrl(url, file) {
      console.log('getFileUrl -> getFileUrl')
      var fileType = file.type

      var type = fileType.split('/')[0]

      var obj = {
        type: type,
        url: url
      }

      if (type == 'video') {
        obj.duration = await this.getVideoDuration(url)
      }

      this.uploadMyFile(obj, file)
    },
    uploadMyFile(obj, file) {
      this.complete = 0
      this.file_loading = true
      var formData = new FormData()
      formData.append('type', obj.type)
      formData.append('resource', file)
      formData.append('tag', this.file_type)

      if (obj.duration) {
        formData.append('duration', obj.duration)
      }

      this.$axios
        .post('/intelligent-creation/upload-user-resource', formData, {
          onUploadProgress: progressEvent => {
            this.complete = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          }
        })
        .then(res => {
          if (res.data.code === '0000') {
            this.getMyFileList()
          } else {
            this.$alertMsg('上传失败，请重新上传')
          }
          this.file_loading = false
        })
        .catch(function(err) {
          console.error(err)
        })
    },
    // 获取用户本地素材列表
    getMyFileList() {
      this.$get('/intelligent-creation/user-resource-list?tag=video_logo,video_begin,video_end', { params: { page: 1, limit: 100 } }).then(res => {
        if (res.data.code === '0000') {
          this.video_logo = res.data.data.list.video_logo || []
          this.video_begin = res.data.data.list.video_begin || []
          this.video_end = res.data.data.list.video_end || []
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    // 选择背景音乐
    setBgmusic() {
      this.modal_show_name = 'musicm'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'bgMusicM')
      })
    },
    // 查看示例
    example() {
      this.$store.commit('modalShow', 'exampleM')
    },
    getListOfoptions() {
      // this.status_loading = true
      this.virtual_presenter = []
      this.$get('/intelligent-creation/list-of-options').then(res => {
        if (res.data.code == '0000') {
          let data = res.data.data
          this.tts_per = data.tts_per
          this.bg_music = data.bg_music.built_in
          this.virtual_presenter.push(...data.virtual_presenter)
          this.custom = data.bg_music.custom
          this.caption_bg = data.caption_bg
          this.caption_bg_temp = JSON.parse(JSON.stringify(this.caption_bg))
          this.caption_id = this.caption_bg.built_in[0].id

          if (this.tts_per.length > 0) {
            this.form.timbre = this.tts_per[0].tts_per_id
            this.demo_sound_url = this.tts_per[0].demo_sound_url
          }
        }
        // this.status_loading = false
      })
    },
    getBgmusicId(id) {
      this.bg_music_id = id
      this.form.bgm = this.bg_music_id == null ? '默认背景音乐' : '自定义背景音乐'
    }
  },
  created() {
    this.getListOfoptions()
    this.getMyFileList()
  },
  mounted() {}
}
</script>

<style lang="scss">
.model-set {
  margin-top: 20px;
  padding: 25px 30px;
  background-color: #fff;
  border-radius: 2px;
  .wrap {
    border: 1px solid #e8e8e8;
    border-radius: 5px;
    padding: 24px 14px 4px 30px;
    position: relative;
    margin-bottom: 30px;
    .sub-title {
      position: absolute;
      top: -8px;
      padding: 0 5px;
      background-color: #fff;
    }
    .iconsolid_add {
      color: #979797;
    }
  }
  .el-form-item {
    position: relative;
    &:nth-child(n + 3) {
      margin-bottom: 15px !important;
    }
    .audio-wrap {
      position: absolute;
      top: 1px;
      left: 8px;
    }
  }
  .audio-select {
    position: relative;
    .iconaudio {
      position: absolute;
      left: 10px;
      top: 2px;
      font-size: 12px;
    }
  }
  .upload {
    .btn-style {
      border: 1px solid $c;
      border-color: #c51a1a;
      color: #c51a1a !important;
      font-weight: 400;
      border-radius: 20px;
      height: 26px;
      line-height: 26px;
      padding: 0 10px;
    }
  }

  .box {
    width: 80px;
    height: 50px;
    border-radius: 3px;
    margin-right: 10px;
    background-color: #e5e5e5;
    border: 1px solid transparent;
    text-align: center;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    .sub-name {
      position: absolute;
      bottom: -20px;
    }
    .iconfont {
      height: 12px;
      width: 12px;
      position: absolute;
      right: 0px;
      top: -12px;
      font-size: 12px;
      display: none;
    }
    .play-video {
      position: absolute;
      bottom: -36px;
      text-decoration: underline;
    }
    &:hover {
      border: 1px solid $c;
      .iconfont {
        display: block;
      }
    }
    &.active {
      border: 1px solid $c;
    }
    img {
      height: 100%;
    }
    &.caption-bg-wrap {
      background-image: url('../../assets/images/caption_bg/default.png');
      background-size: cover;
      width: 120px;
      height: 70px;
      img {
        object-fit: fill;
      }
    }
  }

  .el-input__inner {
    padding-left: 40px;
  }
  .video_end_id-wrap {
    height: 80px;
    margin-bottom: 10px;
  }
  .persenter-wrap {
    cursor: pointer;
    margin-bottom: 20px;
    /deep/ .el-radio__label {
      font-size: 12px;
    }
    /deep/ .el-switch {
      margin: 0 !important;
    }
    .persenter {
      position: relative;
      border-radius: 50%;
      margin-right: 10px;
      border: 1px solid #ccc;
      margin-bottom: 30px;
      width: 70px;
      height: 70px;
      &.active {
        border: 1px solid $c;
      }

      &:hover {
        border: 1px solid $c;
      }

      .sub-name {
        position: absolute;
        top: 74px;
        font-size: 12px;
        width: 70px;
        text-align: center;
      }
      .iconxuanzhong {
        position: absolute;
        top: 50px;
        right: 0px;
        font-size: 18px;
      }

      img {
        width: 100%;
        height: 100%;
        border-radius: 50%;
        object-fit: cover;
      }
    }
  }
}
</style>
