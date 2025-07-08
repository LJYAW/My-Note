<!--
 * @Author: your name
 * @Date: 2021-10-28 21:00:51
 * @LastEditTime: 2021-11-02 12:24:39
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/components/base/upImg.vue
-->
<template>
  <div class="base-up-image">
    <BaseUpload :button-name="buttonName" @getFileUrl="getFileUrl" />
  </div>
</template>

<script>
import getImgSize from '@/utils/img-size'

export default {
  name: 'UpImg',
  components: {
  },
  props: {
    size: {
      type: Object,
      default: () => ({ width: 0, height: 0 })
    },
    buttonName: {
      type: String,
      default: '点击上传'
    }
  },
  data() {
    return {
      fileName: ''
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
    async getFileUrl(url, file) {
      const { w, h } = await getImgSize(url)
      const { width, height } = this.size

      if (width < 1) {
        this.upload(file)
      } else {
        if (w !== width || h !== height) {
          this.$message({
            message: `请上传尺寸为 ${width}*${height} 的图片`,
            type: 'warning'
          })
        } else {
          this.upload(file)
        }
      }
    },

    async upload(file) {
      const fromData = new FormData()
      fromData.append('photo', file)

      const { err, res } = await this.$post('/admin/util/upload-img', fromData)
      if (err) {
        this.$message.error(err.msg)
      } else {
        const { data } = res
        const obj = {
          fileName: file.name,
          filePath: data.file_path,
          fileUrl: data.url
        }
        this.fileName = file.name
        this.$emit('getFileDetails', obj)
      }
    }
  }
}
</script>

<style scoped lang="scss">
</style>
