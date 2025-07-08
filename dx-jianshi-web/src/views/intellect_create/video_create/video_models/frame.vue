<!--
 * @Author: zzx
 * @Date: 2020-10-10 16:59:24
 * @LastEditTime: 2021-07-23 17:08:42
 * @FilePath: /zhijian_web/src/views/intellect_create/video_create/videoModels/frame.vue
-->
<template>
  <div v-loading="loading">
    <div class="wrap">
      <span class="sub-title fz-16px">声音</span>
      <el-switch v-model="form.timbreSwitch" active-color="#13ce66" inactive-color="#DEDEDE" class="mt-12px" disabled></el-switch>
      <el-form-item class="audio-select" label="播报音色选择：">
        <el-select v-model="form.timbre" placeholder="请选择音色" size="mini" style="width: 370px">
          <el-option class="pl-30px" v-for="(item, index) in timbre_list" :key="index" :label="item.name" :value="item.tts_per_id"></el-option>
        </el-select>
        <vsvg :icon="play_audio ? 'iconyinfuzantingjian' : 'iconyinfukaishijian'" @click.native.stop="audioPaly()" class="iconfont iconaudio fc-c"></vsvg>
        <el-checkbox label="设置默认音色" v-model="form.timbre_checkout" @change="changeCheck" name="type" class="ml-30px"></el-checkbox>
      </el-form-item>
      <el-switch v-model="form.bgmSwitch" active-color="#13ce66" inactive-color="#DEDEDE" class="mt-12px"></el-switch>
      <el-form-item label="视频背景音乐：" class="video-back-music">
        <!-- <el-radio-group v-model="form.bgm">
          <el-radio label="默认背景音乐"></el-radio>
          <el-radio label=""></el-radio>
        </el-radio-group> -->

        <span>自定义背景音乐</span>
        <span class="back-music-name" v-if="bg_name">{{ bg_name }}</span>
        <el-button size="mini" class="ml-20px" @click.native="setBgmusic">选择背景音乐</el-button>
      </el-form-item>
      <div class="audio-wrap cp">
        <audioPlay :key="form.timbre" :url="demo_sound_url" :play="play_audio" @playOver="changeAudioStatus" @audioError="audioError" />
      </div>
    </div>
    <div class="wrap" v-if="videoScale == '9:16'">
      <span class="sub-title">画面</span>
      <el-form-item label="字幕位置" label-width="90px" class="mb-0px subtitle-position">
        <div class="d-flex">
          <div class="fz-12px d-flex subtitle">
            <img src="../../../../assets/images/default_video.jpg" alt="" />
            <span>当前字幕距底部距离为{{ subtitle.margin_bottom * 100 }}%</span>
            <a @click="editSubtitle()">修改字幕位置</a>
          </div>
        </div>
      </el-form-item>
    </div>
    <div class="wrap" v-if="videoScale == '16:9'">
      <span class="sub-title">画面</span>
      <el-switch v-model="form.modelSwitch" active-color="#13ce66" inactive-color="#DEDEDE" class="mt-12px"></el-switch>
      <el-form-item label="视频模版：" label-width="130px" class="mb-0px">
        <div class="d-flex">
          <div class="fz-12px d-flex flex-wrap">
            <div
              :class="['box text-center caption-bg-wrap mr-8px mb-60px', { active: caption_id == item.id }]"
              v-for="(item, i) in caption_bg_temp"
              :key="i"
              @click="caption_id = item.id"
            >
              <img class="caption-bg-img" :src="item.image_url" @error="imgError(item, i)" />
              <span class="sub-name cp hove-c" @click="showImg(item.image_url)">{{ item.name }}</span>
            </div>
          </div>
          <!-- <el-button round class="ml-10px mt-30px add-btn" size="mini" @click.native="showCaptionM">添加</el-button> -->
        </div>
      </el-form-item>
      <el-switch v-model="form.persenterSwitch" active-color="#13ce66" inactive-color="#DEDEDE" class="mt-12px" @change="handelChangeSwitch"></el-switch>
      <el-form-item label="虚拟主持人：" label-width="130px" class="mb-15px">
        <div class="persenter-wrap d-flex flex-wrap">
          <div class="d-flex flex-wrap persenter-list">
            <div v-for="(img, index) in virtual_presenter_list" :key="img.virtual_presenter_id" class="persenter mb-20px" @click="selectPersenter(img)">
              <img
                v-if="img.cover_pic_url"
                :src="img.cover_pic_url"
                :class="['box', { active: img.virtual_presenter_id == presen_select.virtual_presenter_id }]"
                @error="imgError(img, index)"
              />
              <p class="sub-name fz-12px">{{ img.name }}</p>
            </div>
          </div>
          <div class="d-flex presen_postion">
            <img :src="presen_postion_img" width="54" height="50" />
            <div class="d-flex align-items-center" style="height: 20px">
              <span v-if="!presen_select.positions.some(s => s == 'left')">画面右侧</span>
              <span v-else>画面左侧</span>
            </div>

            <!-- <div class="d-flex flex-column ml-15px">
              <el-radio class="fz-12px my-10px" v-model="presen_radio" label="left" :disabled="!presen_select.positions.some(s => s == 'left')">左侧</el-radio>
              <el-radio class="fz-12px" v-model="presen_radio" label="right" :disabled="!presen_select.positions.some(s => s == 'right')">右侧</el-radio>
            </div> -->
          </div>
        </div>
      </el-form-item>
    </div>
    <!-- 图片 -->
    <showImgM v-if="modal_show_name == 'showImgM'" @Modalclose="Modalclose" :img_url="modal_img_url" />
    <!--背景音乐 -->
    <bgMusicM
      v-if="modal_show_name == 'musicm'"
      :bg_music="bg_music"
      @close="Modalclose"
      ref="bgMusicM"
      @upDataBgMusic="updataOptions"
      @getBgmusicId="getBgmusicId"
      :custom="custom"
    ></bgMusicM>
    <!-- 视频画面装饰： -->
    <showCaptionBg
      v-if="modal_show_name == 'showCaptionBg'"
      @setCaptionId="setCaptionId"
      @updataCaptionBgImg="updataOptions"
      :caption_bg="JSON.parse(JSON.stringify(caption_bg))"
    />
    <subtitleModels v-if="subtitle_show_name == 'subtitleModels'" @subtitleClose="subtitleClose" @subtitleFun="subtitleFun" />
  </div>
</template>
<script>
import showCaptionBg from '@/components/show_caption_bg'
import bgMusicM from './bg_music_m'
import audioPlay from '@/components/audio_play'
import showImgM from '@/components/show_img_m'
import subtitleModels from './subtitle_models'
export default {
  name: 'videoFrame',
  data() {
    return {
      subtitle: {
        margin_bottom: 0.3
      },
      loading: true,
      subtitle_show_name: '',
      caption_bg_temp: [],
      caption_bg: [],
      timbre_list: [], // 音色
      bg_music_id: null,
      bg_music: [], // 背景音乐
      bg_name: '', // 背景音乐名称
      demo_sound_url: '',
      play_audio: false,
      custom: [], // 上传的 音乐
      virtual_presenter_id: '',
      caption_id: null,
      virtual_presenter: [{ name: '无主持人', virtual_presenter_id: -1, positions: [], sex: 0 }],
      virtual_presenter_list: [],
      modal_show_name: '',
      modal_img_url: '',
      presen_radio: 'right',
      videoScale: '16:9',
      presen_select: {
        positions: [],
        virtual_presenter_id: -1
      },
      form: {
        modelSwitch: true,
        transitSwitch: true,
        persenterSwitch: false,
        timbre: '',
        timbre_checkout: false,
        timbreSwitch: true,
        bgmSwitch: false,
        bgm: ''
      }
    }
  },
  components: {
    showCaptionBg,
    bgMusicM,
    audioPlay,
    showImgM,
    subtitleModels
  },
  computed: {
    presen_postion_img() {
      return require(`../../../../assets/images/host_${this.presen_radio}.png`)
    }
  },
  created() {
    this.getListOfoptions()
  },
  mounted() {
    this.$bus.$on('video_scale', this.getVideoScale)
  },
  methods: {
    subtitleFun(value) {
      this.subtitle.margin_bottom = value / 100
    },
    // 修改字幕位置
    editSubtitle() {
      this.subtitle_show_name = 'subtitleModels'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'subtitleModels')
      })
    },
    subtitleClose() {
      this.subtitle_show_name = ''
      this.$store.commit('modalHidden', 'subtitleModels')
    },
    // 设置视频模板展示数据
    setCaptionBgTemp(arr) {
      let builtInList = arr.built_in
      let customList = arr.custom

      let newArr = []

      const cousTomlength = Math.min(customList.length, 4)
      for (let i = 0; i < cousTomlength; i++) {
        const item = customList[i]
        newArr.push(item)
      }

      for (let j = 0; j < newArr.length; j++) {
        const item = newArr[j]
        builtInList.splice(j + 1, 0, item)
      }
      this.caption_bg_temp = builtInList.splice(0, 5)
    },
    audioError(e) {
      this.$alertMsg('音频加载失败~~')
      this.play_audio = false
    },
    // 获取视频声音和画面素材列表
    getListOfoptions() {
      this.loading = true
      this.$get('/intelligent-creation/list-of-options').then(res => {
        this.loading = false
        if (res.data.code == '0000') {
          let data = res.data.data
          this.timbre_list = data.tts_per
          this.bg_music = data.bg_music.built_in
          this.bg_music.forEach(item => {
            if (item.id == this.bg_music_id) {
              this.bg_name = item.name
            }
          })
          this.custom.forEach(item => {
            if (item.id == this.bg_music_id) {
              this.bg_name = item.name
            }
          })
          this.form.bgm = this.bg_music_id == null ? '' : '自定义背景音乐'
          this.custom = data.bg_music.custom
          this.virtual_presenter.push(...data.virtual_presenter)
          this.caption_bg = JSON.parse(JSON.stringify(data.caption_bg))
          this.setCaptionBgTemp(data.caption_bg)
          if (!this.$route.query.reset_edti_id) {
            this.caption_id = this.caption_bg.built_in[0].id
            if (this.timbre_list.length > 0) {
              this.form.timbre = Number(localStorage.getItem('timbre')) || this.timbre_list[0].tts_per_id
              this.demo_sound_url = this.timbre_list[0].demo_sound_url
            }
            this.$nextTick(() => {
              this.form.timbre_checkout = Number(localStorage.getItem('timbre')) == this.form.timbre ? true : false
            })
          }
          this.virtual_presenter_list = this.getVirtualPresenter()
        }
      })
    },
    handelChangeSwitch(val) {
      if (val) {
        if (this.virtual_presenter_list[0]) {
          this.presen_radio = this.virtual_presenter_list[0].positions[0]
          this.presen_select = {
            positions: this.virtual_presenter_list[0].positions,
            virtual_presenter_id: this.virtual_presenter_list[0].virtual_presenter_id
          }
        }
      } else {
        this.presen_radio = 'right'
        this.presen_select = {
          positions: [],
          virtual_presenter_id: -1
        }
      }
    },
    getVirtualPresenter() {
      if (this.virtual_presenter.length && this.form.timbre) {
        let timbreItme = this.timbre_list.find(item => this.form.timbre == item.tts_per_id)
        if (timbreItme) {
          return this.virtual_presenter.filter(item => {
            return item.sex == timbreItme.sex
          })
        }
      }
    },
    selectPersenter(item) {
      this.presen_select = item
      !item.positions.some(s => s == 'left') ? (this.presen_radio = 'right') : (this.presen_radio = 'left')
    },
    // 添加视频模版
    showCaptionM() {
      this.modal_show_name = 'showCaptionBg'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showCaptionBg')
      })
    },
    //查看视频模版
    showImg(url) {
      this.modal_show_name = 'showImgM'
      this.modal_img_url = url
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showVideoM')
      })
    },
    // 选择背景音乐
    setBgmusic() {
      this.modal_show_name = 'musicm'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'bgMusicM')
      })
    },
    arrUnique(arr, key) {
      let returnArr = []
      if (key) {
        // 对象数组去重
        const obj = {}
        returnArr = arr.reduce((cur, next) => {
          obj[next[key]] ? '' : (obj[next[key]] = true && cur.push(next))
          return cur
        }, [])
        return returnArr
      }
    },
    setCaptionId(obj) {
      this.caption_id = obj.item.id
      this.setCaptionBgTemp(JSON.parse(JSON.stringify(this.caption_bg)))
      this.caption_bg_temp.splice(1, 0, obj.item)
      this.caption_bg_temp = this.arrUnique(this.caption_bg_temp, 'id').splice(0, 5)
    },
    updataOptions() {
      this.getListOfoptions()
    },
    getBgmusicId(id) {
      this.bg_music_id = id
    },
    Modalclose() {
      this.modal_show_name = ''
      this.$store.commit('modalHidden', subtitleModels)
    },
    //播报音色
    audioPaly() {
      let id = this.form.timbre
      let item = this.timbre_list.find(item => item.tts_per_id == id)

      if (this.demo_sound_url == item.demo_sound_url) {
        this.play_audio = !this.play_audio
      } else {
        this.demo_sound_url = item.demo_sound_url
        this.play_audio = true
      }
    },
    changeAudioStatus(val) {
      this.play_audio = !val
    },
    imgError(item, index) {
      let img = event.srcElement
      let img_error_url = require('@/assets/images/img_error.svg')
      img.src = img_error_url
      img.onerror = null // 防止闪图
    },
    changeCheck(val) {
      if (val) {
        localStorage.setItem('timbre', this.form.timbre)
      } else {
        localStorage.setItem('timbre', '')
      }
    },
    getVideoScale(data) {
      this.videoScale = data
    }
  },
  watch: {
    // 背景音乐
    'form.bgmSwitch'() {
      if (this.form.bgmSwitch) {
        if (this.bg_music[91]) {
          this.bg_music_id = this.bg_music[91].id
          this.bg_name = this.bg_music[91].name
        }
      } else {
        this.bg_music_id = null
        this.bg_name = ''
      }
    },
    // 视频模板
    'form.modelSwitch'() {
      if (this.form.modelSwitch) {
        if (this.caption_bg.built_in[0]) {
          this.caption_id = this.caption_bg.built_in[0].id
        }
      } else {
        this.caption_id = null
      }
    },
    // 主持人
    // 'form.persenterSwitch'() {
    //   if (this.form.persenterSwitch) {
    //     if (this.virtual_presenter_list[0]) {
    //       this.presen_radio = this.virtual_presenter_list[0].positions[0]
    //       this.presen_select = {
    //         positions: this.virtual_presenter_list[0].positions,
    //         virtual_presenter_id: this.virtual_presenter_list[0].virtual_presenter_id
    //       }
    //     }
    //   } else {
    //     this.presen_radio = 'right'
    //     this.presen_select = {
    //       positions: [],
    //       virtual_presenter_id: -1
    //     }
    //   }
    // },
    'form.timbre'() {
      if (this.timbre_list.length) this.virtual_presenter_list = this.getVirtualPresenter()
      let check = localStorage.getItem('timbre') || ''
      this.form.timbre_checkout = this.form.timbre == Number(check)
      this.play_audio = false
    },
    bg_music_id(val) {
      if (val) {
        this.form.bgmSwitch = true
      }
      this.bg_music.forEach(item => {
        if (item.id == val) {
          this.bg_name = item.name
        }
      })
      this.custom.forEach(item => {
        if (item.id == val) {
          this.bg_name = item.name
        }
      })
      this.form.bgm = this.bg_music_id == null ? '' : '自定义背景音乐'
    },
    caption_id(val) {
      if (val) {
        this.form.modelSwitch = true
      }
    },
    'presen_select.virtual_presenter_id'(val) {
      if (val > -1) {
        this.form.persenterSwitch = true
      }
    }
  }
}
</script>
<style lang="scss">
.el-select-dropdown__wrap {
  max-height: 400px;
}
</style>
