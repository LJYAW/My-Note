<!-- 上传 -->
<template>
  <div>
    <div v-if="this.type=='MBxlsx'">
      <a class="cp fc-white"
         @click="choose()">模版导入</a>
    </div>
    <div class="cp fz-12px fc-white bg-blue"
         style="width:77px;height:23px;border-radius:40px;text-align: center;line-height:23px"
         @click="choose()"
         v-else>点击上传</div>
    <input type="file"
           id="upload_file"
           multiple
           style="display:none"
           :accept="fileAccept"
           @change="fileChange($event)">
  </div>
</template>

<script>
const imgFileType = 'image/png, image/jpeg, image/gif, image/jpg, image/bmp'
const videoFileType = 'video/mp4, video/rmvb, video/wma'
export default {
  props: {
    type: {
      type: String
    },
    size: {
      type: Number
    },
    nameShuju: {
      type: String
    }
  },
  data() {
    return {
      fileAccept: ''
    }
  },

  mounted() {
    if (this.type == 'image') {
      this.fileAccept = imgFileType
    } else if (this.type == 'video') {
      this.fileAccept = videoFileType
    }
  },

  computed: {},

  created() { },

  methods: {
    choose() {
      document.getElementById('upload_file').value = null
      document.getElementById('upload_file').click()
    },
    fileChange(e) {
      let target = e.target
      this.fileValid(target, this.size, this.type)
    },

    /*
     * @param {e.target} value_ [获取input对象，一般为this]
     * @param {[number]} size_ [文件限制的大小，单位为kb]
     * @param {[string]} type_ [文件类型，当前支持image和office]
     */

    fileValid(target_, size_, type_) {
      let file = target_.files[0]
      let fileSize = (file.size / 1024).toFixed(0)
      let fileType = target_.value.substring(target_.value.lastIndexOf('.'))
      let that = this
      if (fileSize > size_) {
        let sizeNum = size_ / 1024
        this.$message({
          showClose: true,
          message: '请上传小于' + sizeNum + 'M的文件',
          type: 'error'
        })
        return false
      }
      switch (type_) {
        case 'image':
          if (!fileType.match(/.jpg|.jpeg|.gif|.png|.bmp/i)) {
            this.$message({
              showClose: true,
              message: '请上传图片',
              type: 'error'
            })
            return false
          } else {
            // 生成base64
            let reader = new FileReader()
            reader.readAsDataURL(file)
            reader.onloadend = function () {
              // base64传给store
              that.$store.commit('getBaseURL', reader.result)
            }
            this.$store.commit('getUploadFile', file)

            // 清空input的值
            target_.value = ''
            // 清空store中的值
            this.$store.commit('getBaseURL', '')
          }
          break
        case 'office':
          if (!fileType.match(/.doc|.docx|.xls|.xlsx|.pdf|.ppt|.pptx/i)) {
            this.$message({
              showClose: true,
              message: '请上传word、excel、pdf或ppt文件',
              type: 'error'
            })
            return false
          } else {
            this.$store.commit('getUploadFile', file)
          }
          break
        case 'video':
          if (!fileType.match(/.mp4|.rmvb|.wma/i)) {
            this.$message({
              showClose: true,
              message: '请上传.mp4、.rmvb、.wma文件',
              type: 'error'
            })
            return false
          } else {
            this.$store.commit('getUploadFile', file)
          }
          break
        case 'pdf':
          if (!fileType.match(/.pdf/i)) {
            this.$message({
              showClose: true,
              message: '请上传pdf文件',
              type: 'error'
            })
            return false
          } else {
            this.$store.commit('getUploadFile', file)
          }
          break
        case 'MBxlsx':
          if (!fileType.match(/.xlsx/i)) {
            this.$message({
              showClose: true,
              message: '请上传xlsx文件',
              type: 'error'
            })
            return false
          } else {
            this.$store.commit('getUploadFile', file)
          }
          break
        default:
          console.error('type_参数设置不正确！')
          return false
          break
      }
    }
  }
}
</script>
<style lang='scss' scoped>
</style>