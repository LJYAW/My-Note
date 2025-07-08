<!--
 * @Author: zzx
 * @Date: 2020-09-27 15:09:04
 * @LastEditTime: 2020-11-20 15:36:11
 * @FilePath: /zhijian_web/src/views/interact_video/video_create/model_set.vue
-->
<template>
  <div class="interat-model-set model-set">
    <p class="fz-16px mb-30px content-title">效果选择</p>
    <el-form ref="form" :model="form" label-width="120px" label-position="left" class="text-left">
      <div class="wrap">
        <span class="sub-title">附加</span>
        <el-switch v-model="form.timbreSwitch" active-color="#13ce66" inactive-color="#DEDEDE"></el-switch>
        <el-form-item label="自定义角标：" label-width="105px">
          <div class="d-flex flex-wrap">
            <div class="fz-12px d-flex video_end_id-wrap flex-wrap">
              <div :class="['box text-center add-bg-box', { active: !video_logo_id }]" @click="video_logo_id = null">
                <span class="fz-12px">无角标</span>
              </div>
              <div :class="['box text-center add-bg-box', { active: video_logo_id == item.id }]" v-for="(item, i) in video_logo" :key="i" @click="video_logo_id = item.id">
                <img :src="item.resource_url" />
                <vsvg icon="iconshanchu" @click.native="deleteItem(item, 1)" class="iconshanchu"></vsvg>
              </div>
              <a class="fz-12px fc-c play-video" style="text-decoration: underline;" @click="showImg('images/corner_marke.jpeg')">
                <el-button round size="mini" class="ml-20px">示例</el-button>
              </a>
            </div>

            <div class="d-flex justify-content-center">
              <upload button_name="上传本地图标" :disabled="video_logo.length > 4" @click.native="uploadFile('video_logo', video_logo.length)" @getFileUrl="getFileUrl" />
              <p class="fz-12px fc-999 mt-5px" style="line-height: 1">建议上传高度为50PX的透明背景PNG图</p>
            </div>
          </div>
        </el-form-item>

        <el-switch v-model="form.videoBeginSwitch" active-color="#13ce66" inactive-color="#DEDEDE"></el-switch>
        <el-form-item label="自定义片头：" label-width="105px">
          <div class="d-flex flex-wrap flex-column">
            <div class="fz-12px d-flex video_end_id-wrap">
              <div :class="['box text-center add-bg-box', { active: !video_begin_id }]" @click="video_begin_id = null">
                <span class="fz-12px">自动片头</span>
              </div>
              <div class="box fz-12px" v-if="file_loading && file_type == 'video_begin'">{{ this.complete }} %</div>
              <div :class="['box text-center add-bg-box', { active: video_begin_id == item.id }]" @click="video_begin_id = item.id" v-for="(item, i) in video_begin" :key="i">
                <img :src="item.cover_pic" />
                <vsvg icon="iconshanchu" @click.native="deleteItem(item, 2)" class="iconshanchu"></vsvg>
                <div class="iconfont">
                  <vsvg icon="iconbofanganniu" @click.native="playVideo(item)" class="fz-30px"></vsvg>
                </div>
              </div>
            </div>
            <div class="d-flex">
              <upload button_name="上传片头" file_type=" video/mp4" :disabled="video_begin.length > 4" @click.native="uploadFile('video_begin', video_begin.length)" @getFileUrl="getFileUrl" />
              <p class="fz-12px fc-999 mt-5px" style="line-height: 1">建议上传16:9的视频，格式为MP4</p>
            </div>
          </div>
        </el-form-item>
        <el-switch v-model="form.videoEndSwitch" active-color="#13ce66" inactive-color="#DEDEDE"></el-switch>
        <el-form-item label="自定义片尾：" label-width="105px">
          <div class="d-flex flex-wrap">
            <div class="fz-12px d-flex video_end_id-wrap flex-wrap">
              <div
                :class="['box text-center add-bg-box', { active: !video_end_id && video_end_type == 3 }]"
                @click="
                  video_end_id = null
                  video_end_type = 3
                ">
                <span class="fz-12px">默认片尾</span>
              </div>
              <div class="box fz-12px" v-if="file_loading && file_type == 'video_end'">{{ this.complete }} %</div>
              <div
                :class="['box text-center add-bg-box', { active: video_end_id == item.id }]"
                @click="
                  video_end_id = item.id
                  video_end_type = 2
                "
                v-for="(item, i) in video_end"
                :key="i">
                <img :src="item.cover_pic" />
                <vsvg icon="iconshanchu" @click.native="deleteItem(item, 3)" class="iconshanchu"></vsvg>
                <div class="iconfont">
                  <vsvg icon="iconbofanganniu" @click.native="playVideo(item)" class="fz-30px"></vsvg>
                </div>
              </div>
            </div>
            <div class="d-flex">
              <upload button_name="上传片尾" file_type=" video/mp4" class="ml-auto" :disabled="video_end.length > 4" @click.native="uploadFile('video_end', video_end.length)" @getFileUrl="getFileUrl" />
              <p class="fz-12px fc-999 mt-5px" style="line-height: 1">建议上传16:9的视频，格式为MP4</p>
            </div>
          </div>
        </el-form-item>
      </div>
    </el-form>
    <!-- 图片 -->
    <showImgM v-if="modal_show_name == 'showImgM'" @Modalclose="Modalclose" :img_url="modal_img_url" />
    <!-- 播放片头片尾 -->
    <showVideoM v-if="modal_show_name == 'showVideoM'" @Modalclose="Modalclose" :video_details="video_details" :video_options="video_options" />
  </div>
</template>
<script>
import showImgM from '@/components/show_img_m'
import showVideoM from '@/components/show_video_m'
export default {
  data() {
    return {
      form: {
        videoBeginSwitch: true,
        timbreSwitch: true,
        videoEndSwitch: true
      },
      file_type: '',
      file_loading: false,
      show_video_logo: true,
      show_video_begin: true,
      show_video_end: true,
      video_logo_id: null,
      video_begin_id: null,
      video_end_id: null,
      video_end_type: 3,
      video_logo: [], // 角标
      video_begin: [], // 片头
      video_details: {},
      video_options: {},
      video_end: [], // 片尾
      modal_show_name: ''
    }
  },
  components: {
    showImgM,
    showVideoM
  },
  created() {
    this.getMyFileList()
  },
  methods: {
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
    // 示例
    showImg(url) {
      this.modal_show_name = 'showImgM'
      this.modal_img_url = url
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showVideoM')
      })
    },
    // 弹窗关闭
    Modalclose() {
      this.modal_show_name = ''
    },
    // 上传文件
    uploadFile(type, arr_length) {
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
    // 播放视频
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
    }
  }
}
</script>
<style lang="scss">
.interat-model-set {
  .wrap {
    border: 1px solid #e8f0ff;
    border-radius: 0px;
    padding-left: 26px;
    position: relative;
    margin-bottom: 30px;
    .el-button {
      padding: 10px 24px;
      color: #165dd9;
      background: #e8f0ff;
      border: none;
    }
    .sub-title {
      position: absolute;
      top: -8px;
      padding: 0 5px;
      font-size: 16px;
      background-color: #fff;
    }
    .el-switch {
      height: 16px;
      line-height: 16px;
      margin-right: 10px;
      vertical-align: 3px;
      .el-switch__core {
        width: 28px !important;
        height: 16px;
      }
      .el-switch__core:after {
        width: 12px;
        height: 12px;
      }
      &.is-checked .el-switch__core::after {
        margin-left: -13px;
      }
    }
    .el-form-item {
      display: inline-block;
      width: 94%;
      margin-bottom: 25px !important;
      .box.add-bg-box {
        width: 100px;
        height: 56px;
        background: #f7f7f7;
        margin-right: 10px;
        line-height: 56px;
        margin-bottom: 20px;
        position: relative;
        & > img {
          height: 100%;
        }
        & > img + span {
          display: block;
          width: 20px;
          height: 20px;
          color: #fff;
          background: #fd5352;
          line-height: 20px;
          border-radius: 100%;
          text-align: center;
          font-size: 14px;
          font-weight: 600;
          position: absolute;
          right: -6px;
          top: -6px;
          cursor: pointer;
        }
        .iconshanchu {
          display: block;
          color: #fd5352;
          position: absolute;
          top: -5px;
          right: -5px;
          z-index: 3;
        }
        div {
          background: rgba(0, 0, 0, 0.2);
          position: absolute;
          width: 100%;
          height: 100%;
          top: 0;
          svg {
            position: absolute;
            color: #fff;
            left: 50%;
            top: 50%;
            margin-left: -15px;
            margin-top: -15px;
            width: 30px;
            height: 30px;
            cursor: pointer;
          }
        }
      }
      label.el-form-item__label {
        font-size: 16px;
        padding-right: 0;
      }
      .video_end_id-wrap {
        height: auto;
        .el-button {
          margin-top: 30px;
        }
        a.fz-12px.fc-c.play-video {
          position: absolute;
          right: 0;
        }
      }
    }
    .upload {
      padding: 0 24px;
      background: #e8f0ff;
      border-radius: 20px;
      height: 34px;
      .btn-style {
        color: #165dd9 !important;
        border: none;
        padding: 0;
      }
      & + p {
        line-height: 26px !important;
        margin-left: 10px;
      }
    }
  }
}
</style>
