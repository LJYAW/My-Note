<!--
 * @Author: your name
 * @Date: 2020-12-08 15:00:58
 * @LastEditTime: 2020-12-09 12:23:13
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/user_info/components/upload_m.vue
-->
<template>
  <div class='upload-model w-100'>
    <div class='pt-26px'>
      <div class='upload-file mb-11px'>
        <videoPalyer :video_options="video_options" v-if='fileType == "video/mp4"&&src' ref='videoPalyer' />
        <div v-else-if='fileType == "audio/mpeg"&&src' class='h-100'>
          <div class='text-center pt-100px'>
            <vsvg icon='iconyinfu' class='iconfont fz-60px w-100' />
          </div>
          <div class="audio-wrap cp">
            <audioPlay :url="src" :play="play_audio" />
          </div>
          <vsvg :icon='!play_audio?"iconyinfukaishijian":"iconyinfuzantingjian"'
            class='fz-60px iconbofang' @click.native.stop="audioPaly" />
        </div>
        <div v-else class='d-flex justify-content-center align-items-center h-100'>
          <img :src=this.src alt="" class='h-100'>
        </div>
      </div>
      <upload button_name="上传素材" :file_type="fileType"
        class="ml-auto mb-30px upload-btn"
        @getFileUrl="getFileUrl" />
    </div>
    <span class='mr-20px ml-40px'>素材名称:</span>
    <el-input placeholder="请输入素材标题" v-model='title'></el-input>
    <div class='sure-btn text-center pt-26px'>
      <el-button size='small' @click.native='sure'>确定</el-button>
    </div>
  </div>
</template>

<script>
import audioPlay from '@/components/audio_play'
export default {
  props: {
    fileType: {
      //传递的数据
      type: String,
      default: () => {
        return ''
      }
    },
    layerid: {
      //自动注入的layerid
      type: String,
      default: ''
    }
  },
  data() {
    return {
      src: '',
      video_options: {},
      title: '',
      file_obj: {},
      //   demo_sound_url: '',
      play_audio: false
    }
  },
  computed: {},
  watch: {},
  methods: {
    getFileUrl(url, file) {
      this.src = url
      if (this.fileType == 'video/mp4') {
        this.video_options = {
          width: ' 463',
          height: '260',
          autoplay: false,
          src: url,
          poster: url
        }
        this.$nextTick(() => {
          this.$refs['videoPalyer'].videoInit()
          this.$refs['videoPalyer'].videoOffset()
        })
      }
      var fileType = file.type
      var type = fileType.split('/')[0]
      this.file_obj = {
        type: type,
        url: url,
        file: file
      }
      if (type == 'video') {
        this.file_obj.duration = this.getVideoDuration(url)
      }
    },
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
    //播报音色
    audioPaly(val) {
      this.play_audio = !this.play_audio
    },
    sure() {
      if (!this.src) {
        this.$alertMsg('请上传素材')
        return
      }
      if (!this.title) {
        this.$alertMsg('请输入素材名称')
        return
      }
      this.file_obj.title = this.title
      this.$parent.uploadMyFile(this.file_obj)
      this.$layer.close(this.layerid)
    }
  },
  components: { audioPlay },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.upload-model {
  & > div {
    width: 600px;
    margin: 0 auto;
    position: relative;
    .iconbofang {
      position: absolute;
      right: 30px;
      bottom: 40px;
    }
  }
  /deep/#videoWrap {
    height: 300px;
    .vjs-fluid {
      padding: 0;
      height: 300px;
    }
  }
  .upload-file {
    width: 600px;
    height: 300px;
    background: #eee;

    video {
      width: 100%;
    }
  }
  /deep/.upload-btn {
    .btn-style {
      color: #c51b18;
    }
  }
  .sure-btn {
    button {
      background: #c51b18;
      color: #fff;
      padding: 7px 29px;
      border-radius: 100px;
    }
  }
}
</style>
