<template>
  <div class="d-flex align-items-center upload">
    <input
      :id="'upload_flie' + _uid"
      ref="inputer"
      type="file"
      multiple
      :disabled="disabled"
      :accept="fileType"
      style="display:none"
      @change="addImg($event)"
    >

    <a
      class="fc-bb d-flex flex-column align-items-center"
      @click="updataImg()"
    >
      <!-- <i class="iconfont iconsolid_add fz-70px"></i> -->
      <div class="btn-style">{{ buttonName }}</div>
    </a>
    <div class="ml-10px">{{ size }}</div>
  </div>
</template>

<script>
// getFileUrl 获取 img 本地地址
export default {
  name: 'BaseUpload',
  components: {},
  props: {
    disabled: {
      type: Boolean,
      default: false
    },
    buttonName: {
      type: String,
      default: '点击上传'
    },
    fileType: {
      type: String,
      default: 'image/png, image/jpeg, image/gif, image/jpg'
    },
    singleElection: {
      type: Boolean,
      default: true
    },
    flieLength: {
      type: Number,
      default: 9
    },
    size: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      fil: null,
      progress: ''
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    addImg(el) {
      let inputDOM = null
      inputDOM = this.$refs.inputer

      this.fil = inputDOM.files[0]

      const size = this.fil.size / 1024
      if (!this.limitFileSize(size)) {
        return
      }

      // file 转成 base64
      var reader = new FileReader()
      reader.readAsDataURL(this.fil)
      var self = this
      reader.onload = function() {
        self.$emit('getImgToBase64', reader.result)
      }

      if (this.singleElection) {
        this.getObjectURL(this.fil)
      } else {
        this.fileList(el.target)
      }

      // inputDOM.value = ''
    },
    fileList(fileList) {
      const files = fileList.files
      const urlArr = []
      // 批量上传 限制数量
      if (files.length <= this.flieLength) {
        for (let i = 0; i < files.length; i++) {
          var url = null
          if (window.createObjectURL !== undefined) {
            // basic
            url = window.createObjectURL(files[i])
          } else if (window.URL !== undefined) {
            // mozilla(firefox)
            url = window.URL.createObjectURL(files[i])
          } else if (window.webkitURL !== undefined) {
            // webkit or chrome
            url = window.webkitURL.createObjectURL(files[i])
          }
          urlArr.push(url)
        }
        this.$emit('getFileUrl', urlArr, files)
      }
    },

    limitFileSize(size) {
      var MAX_SIZE = 1024 * 1000

      if (this.fileType.includes('image')) {
        MAX_SIZE = 5 * 1024
      }
      if (this.fileType.includes('video')) {
        MAX_SIZE = 100 * 1024
      }
      if (this.fileType.includes('audio')) {
        MAX_SIZE = 20 * 1024
      }
      if (size > MAX_SIZE) {
        this.$message.error(`请选择 ${MAX_SIZE / 1024}MB 以内的文件`)
        return false
      } else {
        return true
      }
    },
    getObjectURL(file) {
      var url = null
      if (window.createObjectURL !== undefined) {
        // basic
        url = window.createObjectURL(file)
      } else if (window.URL !== undefined) {
        // mozilla(firefox)
        url = window.URL.createObjectURL(file)
      } else if (window.webkitURL !== undefined) {
        // webkit or chrome
        url = window.webkitURL.createObjectURL(file)
      }
      this.$emit('getFileUrl', url, file)
    },
    // url 图片链接或者是blob对象
    // callback 回调函数
    getImgToBase64(url, callback) {
      var canvas = document.createElement('canvas')
      var ctx = canvas.getContext('2d')
      var img = new Image() // 通过构造函数来创建的 img 实例，在赋予 src 值后就会立刻下载图片，相比 createElement() 创建 <img> 省去了 append()，也就避免了文档冗余和污染
      img.crossOrigin = 'Anonymous'
      // 要先确保图片完整获取到，这是个异步事件

      img.onload = function() {
        canvas.height = img.height // 确保canvas的尺寸和图片一样
        canvas.width = img.width
        ctx.drawImage(img, 0, 0) // 将图片绘制到canvas中
        var dataURL = canvas.toDataURL('image/png') // 转换图片为dataURL,传第二个参数可压缩图片,前提是图片格式jpeg或者webp格式的
        // callback(dataURL) //调用回调函数
        this.$emit('getImgToBase64', dataURL)
        canvas = null
      }
    },
    updataImg() {
      this.$refs.inputer.click()
    }
  }
}
</script>

<style scoped lang="scss">
.upload {
  .btn-style {
    color: #3485e1;
    font-size: 12px;
  }
  .card {
    border: 1px solid rgba(213, 213, 213, 1);
    border-radius: 3px;
    color: #e7e7e7;
    text-align: center;
    p {
      height: 25px;
    }
  }
}
</style>
