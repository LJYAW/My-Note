<template>
  <div>
    <div class="video-box border">
      <videoPalyer :video_options="video_options"
                   @getVideoId="getVideoId" />
    </div>

    <div class="mt-15px">
      <el-button type="primary"
                 round
                 style="minWidth: 100px;margin-right: 10px"
                 size="mini"
                 @click="cutVideoProto">截取封面</el-button>
      <span class="fc-999 fz-12px">拖动进度条，点击按钮截取封面</span>
    </div>

    <div class="cover-content mt-30px d-flex">

      <div>
        <p class="mb-8px">视频封面：</p>
        <div class="cover-box">
          <i v-if="!photo"
             class="iconfont icontupian-tianchong fz-35px"></i>
          <div v-else
               class="cover-img">
            <img :src="photo">
          </div>
        </div>
      </div>

      <div class="upload-wrap">
        <span class="icontadd">+</span>
        <upload button_name="上传封面"
                :MAX_SIZE="5"
                @getFileUrl="getFileUrl" />
        <p class="des">建议比例16：9，画面清晰，最大5M，660X370像素</p>
      </div>

    </div>
  </div>
</template>

<script>
import * as canvasFunction from '@/utils/canvas.js'

export default {
  props: {},
  data() {
    return {
      video_options: {
        width: '',
        height: '',
        src: ''
      },
      photo: this.$route.query.cover_pic,
      photo_file: null,
      video_play_id: ''
    }
  },
  computed: {},
  watch: {},
  methods: {
    cutVideoProto() {
      let id = this.video_play_id
      let src = canvasFunction.canvasImgSrc(this.video_play_id)
      this.photo = src
      this.photo_file = canvasFunction.dataURLtoFile(src)
    },
    setVideoDetails() {
      let item = this.$route.query
      this.video_options = {
        width: '600',
        height: '336',
        src: item.video_url
      }
    },
    getFileUrl(url, file) {
      const MIN_WIDTH = 660
      const MIN_HEIGHT = 370
      this.$actions.imgLoad(url, (width, height) => {
        console.log('getFileUrl -> height', height)
        console.log('getFileUrl -> width', width)
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
          this.$alertMsg(`图片尺寸不能小于 ${MIN_WIDTH} * ${MIN_HEIGHT}`)
        } else {
          this.photo = url
          this.photo_file = file
        }
      })
    },
    getVideoId(id) {
      this.$nextTick(() => {
        this.video_play_id = id
      })
    }
  },
  components: {},
  created() {
    this.setVideoDetails()
  },
  mounted() {}
}
</script>

<style lang="scss">
.video-box {
  width: 600px;
  height: 335px;
}
.cover-content {
  .cover-box {
    width: 200px;
    height: 112px;
    background-color: #e5e5e5;
    position: relative;
    .icontupian-tianchong {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      color: #8d9ea7;
    }
    .cover-img {
      width: 100%;
      height: 100%;
      text-align: center;
      img {
        object-fit: contain;
        height: 100%;
      }
    }
  }
  .upload-wrap {
    width: 200px;
    height: 112px;
    margin-top: 22px;
    margin-left: 20px;
    position: relative;
    border: 1px dashed #bbbbbb;
    color: #8e8e8e;
    .icontadd {
      font-weight: 100;
      position: absolute;
      top: 35%;
      left: 50%;
      font-size: 28px;
      transform: translate(-50%, -50%);
      color: #8d9ea7;
    }
    .upload {
      position: absolute;
      top: 58%;
      left: 50%;
      transform: translate(-50%, -50%);
      .btn-style {
        color: #8d9ea7;
      }
    }
    .des {
      position: absolute;
      bottom: -20px;
      font-size: 12px;
      width: max-content;
    }
  }
}
</style>
