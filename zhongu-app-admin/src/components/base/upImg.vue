<!--
 * @Author: your name
 * @Date: 2021-10-28 21:00:51
 * @LastEditTime: 2021-10-29 15:37:31
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
import BaseUpload from './upload.vue'
import getImgSize from '@/utils/img-size'

export default {
  components: {
    BaseUpload
  },
  props: {
    size: {
      type: Object,
      default: () => ({ w: 0, h: 0 })
    },
    buttonName: {
      type: String,
      default: '点击上传'
    }
  },
  data() {
    return {

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
      console.log('🚀 ~ file: upImg.vue ~ line 52 ~ getFileUrl ~ url', url)
      const { w, h } = await getImgSize(url)

      if (this.size.w < 1) {
        this.upload(file)
      } else {
        if (w !== this.size.w || h !== this.size.h) {
          this.$message({
            message: `请上传尺寸为 ${this.size.w}*${this.size.h} 的图片`,
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
        this.$emit('getImage', res, file)
      }
    }
  }
}
</script>

<style scoped lang="scss">

</style>
