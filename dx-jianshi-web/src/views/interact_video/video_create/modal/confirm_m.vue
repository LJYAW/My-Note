<!--
 * @Author: zzx
 * @Date: 2020-09-29 17:18:54
 * @LastEditTime: 2020-10-13 11:54:19
 * @FilePath: /zhijian_web/src/views/interact_video/video_create/modal/confirm_m.vue
-->
<template>
  <div>
    <model :id="'confirmM'" class="model-sm" @close="close" ref="confirmM">
      <div slot="title"></div>
      <div slot="body">
        <div
          v-if="!submit"
          class="d-flex flex-column justify-content-center align-items-center pb-50px"
        >
          <p class="mb-70px mt-20px fz-16px">是否将素材提交，素材提交后将无法修改！</p>
          <div>
            <el-button round size="small" style="width: 90px; margin: 0 17px" @click="closeFrom">取消</el-button>
            <el-button
              round
              size="small"
              type="primary"
              style="width: 90px; margin: 0 17px"
              @click="yes"
            >确认</el-button>
          </div>
        </div>

        <div v-else class="d-flex flex-column justify-content-center align-items-center pb-50px">
          <img src="../../../../assets/images/yes.svg" />
          <p class="mb-30px mt-35px fz-16px">您的素材已提交，请到我的作品中查看生成进度。</p>

          <div>
            <el-button round size="small" style="width: 90px; margin: 0 17px" @click="closeFrom">确定</el-button>
            <!-- <el-button round size="small" type="primary" style="width: 90px; margin: 0 17px" @click="goToCheck">去查看</el-button> -->
          </div>
        </div>
      </div>
    </model>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      submit: true
    }
  },
  computed: {},
  watch: {},
  methods: {
    closeFrom() {
      this.$refs.confirmM.close()
    },
    close() {
      this.$emit('close')
    },
    yes() {
      this.submit = true
      this.$emit('submit')
    },
    goToCheck() {
      this.close()
      this.$refs.confirmM.close()
      this.$router.push('/my-videos')
    }
  }
}
</script>

<style scoped lang="scss">
</style>
