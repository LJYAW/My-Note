<!--
 * @Author: your name
 * @Date: 2021-08-02 19:07:25
 * @LastEditTime: 2021-09-06 10:34:49
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/components/Upload/index.vue
-->
<template>
  <div>
    <div class="upload-btn">
      <label for="file">
        <input
          id="file"
          type="file"
          name="file"
          :accept="fileType"
          style="display: none"
          @change="upload($event)"
        >
        <span>
          <svg-icon icon-class="upload" />
          <div class="upload-tips">点击上传视频</div>
        </span>
      </label>
    </div>
  </div>
</template>

<script>

export default {
  components: {

  },
  props: {
    type: {
      type: String,
      default: 'video'
    },
    fileType: {
      type: String,
      default: 'video/mp4'
    },
    maxSize: {
      type: Number,
      default: 100
    }
  },
  data() {
    return {
      file: null
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    uploadSuccess(msg) {
      console.log(msg)
    },
    upload(event) {
      this.file = event.target.files[0]

      if (this.type === 'video') {
        if (['video/mp4'].indexOf(this.file.type) === -1) {
          this.$message.error('请上传正确的视频格式')
          return false
        }
      } else if (this.type === 'image') {
        if ([
          'image/png',
          'image/jpg',
          'image/gif',
          'image/jpeg',
          'image/bmp',
          'image/svg+xml'
        ].indexOf(this.file.type) === -1) {
          this.$message.error('请上传正确的图片格式')
          return false
        }
      }
      if (this.file.size / 1024 / 1024 / 1000 > this.maxSize) {
        this.$message.error('视频文件过大')
        return false
      }

      this.$emit('getFile', this.file)
    }
  }
}
</script>

<style scoped lang="scss">
.upload-btn {
  width: 548px;
  height: 308px;
  margin: 0 auto;
  background: #f2f2f2;
  border-radius: 4px;

  label {

    width: 100%;
    height: 100%;
  }

  svg {
    font-size: 50px;
    color: #fff;
  }

  .upload-tips {
    color: #404040;
    width: 100%;
    text-align: center;
    font-weight: normal;
    margin-top: 10px;
  }

  span {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    cursor: pointer;
    text-align: center;

  }
}
</style>
