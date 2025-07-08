<!--
 * @Author: zzx
 * @Date: 2020-10-10 16:33:20
 * @LastEditTime: 2021-07-14 17:58:14
 * @FilePath: /zhijian_web/src/views/intellect_create/video_create/videoModels/attach.vue
-->
<template>
  <div>
    <div class="wrap add-wrap">
      <span class="sub-title">附加</span>

      <!-- 自定义角标 -->
      <!-- <el-switch v-model="form.videoLogoSwitch" active-color="#13ce66" inactive-color="#DEDEDE" disabled></el-switch> -->
      <el-form-item label="自定义角标：" label-width="105px" class="mb-35px w-100">
        <div class="d-flex flex-wrap">
          <div class="fz-12px d-flex flex-wrap">
            <div :class="['box text-center add-bg-box', { active: !video_logo_id }]" @click="video_logo_id = null">
              <span class="fz-12px">无角标</span>
            </div>
            <div v-for="(item, i) in video_logo" :key="i" @click="video_logo_id = item.id" :class="['box text-center add-bg-box', { active: video_logo_id == item.id }]">
              <img :src="item.resource_url" :active="video_logo_id == item.id" />
            </div>
            <div class="play-video">
              <a class="fz-12px fc-c" style="text-decoration: underline;" @click="showImg('images/corner_marke.jpeg')">
                <!-- <span class="ml-20px">示例</span> -->
              </a>
              <div class="d-flex justify-content-center upload-btn">
                <!-- 上传角标 -->
                <el-button round size="mini" @click.native="openModal('video_logo')"></el-button>
              </div>
            </div>
          </div>
        </div>
      </el-form-item>

      <!-- 自定义片头 -->
      <!-- <el-switch v-model="form.videoBeginSwitch" active-color="#13ce66" inactive-color="#DEDEDE" disabled></el-switch> -->
      <el-form-item label="自定义片头：" label-width="105px" class="mb-35px w-100">
        <div class="d-flex flex-wrap flex-column">
          <div class="fz-12px d-flex flex-wrap">
            <div :class="['box text-center add-bg-box', { active: !video_begin_id }]" @click="video_begin_id = null">
              <span class="fz-12px">无片头</span>
            </div>
            <div :class="['box text-center add-bg-box', { active: video_begin_id == item.id }]" @click="video_begin_id = item.id" v-for="(item, i) in video_begin" :key="i">
              <div class="px-10px pt-20px" v-if="item.type == '上传中'">
                <el-progress :text-inside="true" :stroke-width="13" :percentage="complete"></el-progress>
              </div>
              <div v-else>
                <img :src="item.cover_pic" @error="imgError(img, index)" />
                <div class="w-100 h-100 play-video">
                  <vsvg icon="iconbofanganniu" class="fz-30px icon-play" @click.native="playVideo(item)"></vsvg>
                </div>
              </div>
            </div>
            <div class="d-flex play-video upload-btn">
              <!-- 上传片头 -->
              <el-button round size="mini" @click="openModal('video_begin')"></el-button>
            </div>
          </div>
        </div>
      </el-form-item>

      <!-- 自定义片尾 -->
      <!-- <el-switch v-model="form.videoEndSwitch" active-color="#13ce66" inactive-color="#DEDEDE" disabled></el-switch> -->
      <el-form-item label="自定义片尾：" label-width="105px" class="mb-35px w-100">
        <div class="d-flex flex-wrap flex-column">
          <div class="fz-12px d-flex flex-wrap">
            <div :class="['box text-center add-bg-box', { active: !video_end_id && video_end_type == 1 }]" @click=";(video_end_id = null), (video_end_type = 1)">
              <span class="fz-12px">无片尾</span>
            </div>
            <div :class="['box text-center add-bg-box', { active: !video_end_id && video_end_type == 3 }]" @click=";(video_end_id = null), (video_end_type = 3)">
              <span class="fz-12px">默认片尾</span>
            </div>
            <div
              v-for="(item, i) in video_end.slice(0, 3)"
              :key="i"
              :class="['box text-center add-bg-box', { active: video_end_id == item.id }]"
              @click=";(video_end_id = item.id), (video_end_type = 2)"
            >
              <div class="px-10px pt-20px" v-if="item.type == '上传中'">
                <el-progress :text-inside="true" :stroke-width="13" :percentage="complete"></el-progress>
              </div>
              <div>
                <img :src="item.cover_pic" @error="imgError(img, index)" />
                <div class="w-100 h-100 play-video">
                  <vsvg icon="iconbofanganniu" class="fz-30px icon-play" @click.native="playVideo(item)"></vsvg>
                </div>
              </div>
            </div>
            <div class="d-flex play-video upload-btn">
              <!-- 上传片尾 -->
              <el-button round size="mini" @click="openModal('video_end')"></el-button>
            </div>
          </div>
        </div>
      </el-form-item>
    </div>

    <!-- 图片 -->
    <showImgM v-if="modal_show_name == 'showImgM'" @Modalclose="Modalclose" :img_url="modal_img_url" />

    <!-- 播放片头片尾 -->
    <showVideoM v-if="modal_show_name == 'showVideoM'" @Modalclose="Modalclose" :video_details="video_details" :video_options="video_options" />

    <!-- 上传弹框 -->
    <showVideoWrap
      v-if="modal_show_name == 'showVideoWrap'"
      @Modalclose="Modalclose"
      @setActiveTtem="setActiveTtem"
      :modal_data_type="modal_data_type"
      :modal_avtive_id="modal_avtive_id"
    />
  </div>
</template>
<script>
import showImgM from '@/components/show_img_m'
import showVideoM from '@/components/show_video_m'
import showVideoWrap from '../video_models/modal_video_wrap'
import * as model from '../../js/model_set'

export default {
  data() {
    return {
      complete: 0,
      videoScale: '16:9',
      form: {
        videoLogoSwitch: true, // 角标开关
        videoBeginSwitch: true, // 自定义片头开关
        videoEndSwitch: true // 自定义片尾开关
      },
      testLoading: false,
      file_type: '',
      last_page: 1,
      video_logo: [], // 角标
      video_begin: [], // 片头
      video_end: [], // 片尾
      video_logo_filter: [], // 角标
      video_begin_filter: [], // 片头
      video_end_filter: [], // 片尾
      video_logo_temp: [], // 角标
      video_begin_temp: [], // 片头
      video_end_temp: [], // 片尾
      video_details: '',
      video_options: {},
      modal_show_name: '',
      video_end_type: 1,
      video_logo_id: null,
      video_begin_id: null,
      video_end_id: null,
      upload_id: null,
      video_data_temp: [],
      title: '',
      PAGE: 1,
      LIMIT: 4,
      modal_data_type: '',
      modal_avtive_id: null
    }
  },
  components: {
    showImgM,
    showVideoM,
    showVideoWrap
  },
  methods: {
    // 播放片头片尾
    playVideo(item) {
      this.modal_show_name = 'showVideoM'
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
    // 示例
    showImg(url) {
      this.modal_show_name = 'showImgM'
      this.modal_img_url = url
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showVideoM')
      })
    },
    // 请求附加三个数据
    getAllDataList(arr = ['video_logo', 'video_begin', 'video_end']) {
      arr.forEach(item => {
        this.getMyFileList(this.setParams(item)).then(data => {
          this[item] = data.list[item] || []
        })
      })
    },
    setParams(type, title = '', page = 0, limit = 4) {
      return {
        tag: type,
        title: title,
        page: page,
        limit: limit
      }
    },
    // 获取用户本地素材列表(附加模块角标，片头，片尾)
    getMyFileList(params) {
      return new Promise((resolve, reject) => {
        this.$get('/intelligent-creation/user-resource-list', { params, params }).then(res => {
          if (res.data.code === '0000') {
            // resolve(res.data.data.list[params.tag])
            resolve(res.data.data)
          } else {
            this.$alertMsg(res.data.msg)
          }
        })
      })
    },
    // 打开附加数据弹框
    openModal(type) {
      this.modal_data_type = type
      this.modal_avtive_id = this[type + '_id']
      this.modal_show_name = 'showVideoWrap'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showVideoWrap')
      })
    },
    // 设置视频模板展示数据
    setVideoWrap(type, arr) {
      const cousTomlength = Math.min(arr.length, 4)
      this[type] = arr.splice(0, cousTomlength)
    },
    uploadFile(type) {
      this.modal_show_name = 'showVideoWrap'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showVideoWrap')
      })
      this.file_type = type
      this.$data[type + '_filter'] = this.$data[type]
      this.upload_id = this.$data[type + '_id']
    },

    // 关闭弹窗
    Modalclose() {
      this.modal_show_name = ''
    },

    imgError(item, index) {
      let img = event.srcElement
      let img_error_url = require('@/assets/images/img_error.svg')
      img.src = img_error_url
      img.onerror = null // 防止闪图
    },
    getVideoScale(val) {
      this.videoScale = val
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
    //插入数据
    async insertData(val) {
      let arr = await this.getMyFileList(this.setParams(this.modal_data_type))
      arr = arr.list[this.modal_data_type]
      this.setVideoWrap(this.modal_data_type, JSON.parse(JSON.stringify(arr)))
      this[this.modal_data_type].splice(0, 0, val)
      this[this.modal_data_type + '_id'] = val.id
      this[this.modal_data_type] = this.arrUnique(this[this.modal_data_type], 'id').splice(0, 4)
    },
    setActiveTtem(val) {
      if (!val.id) return
      this.insertData(val)
    }
  },

  created() {
    this.getAllDataList()
  },
  mounted() {}
}
</script>
