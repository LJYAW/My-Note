<template>
  <div class="upload-img-list d-flex">
    <div class="box"
         v-for="(item,index) in ad_url_list"
         :key="index">
      <img :src="item.url"
           v-if="item.type =='image'">
      <video v-if="item.type =='video'"
             :src="item.url"></video>
    </div>
    <el-upload class="avatar-uploader"
               ref="upload"
               action="#"
               :show-file-list="false"
               :http-request="uploadSectionFile">
      <span class="font-14">选择图片或视频</span>
      <div slot="tip"
           class="el-upload__tip">尺寸750*1125px，大小2M以内，视频支持MP4</div>

      <i class="iconfont iconadd fz-20px"></i>
    </el-upload>
    <!-- <el-dialog :visible.sync="dialogVisible">
      <img width="100%"
           :src="dialogImageUrl"
           alt="">
    </el-dialog> -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      upload_url: 'api/v1/intelligent-creation/upload-user-resource', // 上传URL
      upload_name: '', // 图片或视频名称
      ad_url: '', // 上传后的图片或视频URL
      ad_url_list: [] // 预览列表
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    handleRemove(res, file) {},
    uploadSectionFile(params) {
      var self = this
      var file = params.file
      var fileType = file.type
      var type = ''
      if (fileType.indexOf('image') != -1) {
        type = 'image'
      }
      if (fileType.indexOf('video') != -1) {
        type = 'video'
      }
      var file_url = self.$refs.upload.uploadFiles[0].url
      var obj = {
        url: file_url,
        type: type
      }

      var isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$alertMsg('上传图片或视频大小不能超过 2MB!')
        self.$refs.upload.uploadFiles = []
        return
      }

      var isMP4 = file.type === 'video/mp4'

      if (!isMP4) {
        this.$alertMsg('上传视频只支持 mp4 格式')
        return
      }
      self.ad_url_list.unshift(obj)
      self.uploadFile(file, obj)
    },
    uploadFile(file, obj) {
      var self = this
      var formData = new FormData()
      formData.append(self.type, obj.type)
      formData.append(self.resource, file)
      formData.append(self.duration, '123')

      this.$post('/intelligent-creation/upload-user-resource', formData, { headers: this.headers })
        .then(function(res) {
          if (res.code === '0000') {
            return
          }
          this.$alertMsg('上传失败，请重新上传')
        })
        .catch(function(err) {
          console.error(err)
        })
    }
  },
  created() {},
  mounted() {}
}
</script>

<style lang='scss' scoped>
</style>