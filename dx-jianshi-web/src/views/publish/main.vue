<!--
 * @Author: zzx
 * @Date: 2020-06-19 10:37:59
 * @LastEditTime: 2020-07-21 15:51:32
 * @FilePath: /zhijian_web/src/views/publish/main.vue
-->
<template>
  <div>
    <div class="publish-form d-flex container mt-20px">

      <videoForm ref="videoForm" />

      <div class="flex-1">
        <rightForm ref="rightForm" />

        <div class="text-center mt-25px">
          <el-button type="primary"
                     round
                     :disabled="publish_status"
                     size="small"
                     :loading="publish_status"
                     style="minWidth: 100px"
                     @click="publish">发布</el-button>
        </div>
      </div>

    </div>

  </div>

</template>

<script>
import videoForm from './video_form'
import rightForm from './form'
import { checkData } from '../video_create/edit_pad'
export default {
  props: {},
  data() {
    return {
      publish_status: false
    }
  },
  computed: {},
  watch: {},
  methods: {
    publish() {
      this.$refs.rightForm.submitForm()
      if (!this.$refs.rightForm.passed) {
        return
      }
      this.publish_status = true

      let videoFormData = this.$refs.videoForm
      let rightFormData = this.$refs.rightForm.ruleForm

      this.checkData(videoFormData, rightFormData)

      var form = new FormData()
      form.append('intelligent_id', this.$route.query.intelligent_id)
      form.append('category_id', rightFormData.category_id)
      form.append('title', rightFormData.title)
      form.append('intro', rightFormData.intro)
      form.append('is_original', rightFormData.is_original ? '是' : '否')

      rightFormData.tag_list.forEach(item => {
        form.append('tags[]', item)
      })
      rightFormData.third_account_info_ids.forEach(item => {
        form.append('third_account_info_ids[]', item)
      })

      if (videoFormData.photo_file) {
        form.append('photo', videoFormData.photo_file)
      }

      this.$post('/short-video/publish-from-intelligent', form).then(res => {
        if (res.data.code == '0000') {
          this.$alertMsg('发布成功')
          this.$router.push('my-videos')
        } else {
          this.$alertMsg(res.data.msg)
        }

        this.publish_status = false
      })
    },
    checkData(videoFormData, rightFormData) {}
  },
  components: { videoForm, rightForm },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
</style>
