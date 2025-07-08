<!--
 * @Author: your name
 * @Date: 2021-08-06 14:39:27
 * @LastEditTime: 2021-09-09 10:30:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/edit-mian/trackBar/watermarkBtn.vue
 特殊组件 需要引入接口
-->
<template>
  <div class="watermark-btn">
    <el-checkbox v-model="checked" :disabled="btnDisabled" border size="mini" @click.native="setChecked()">添加水印</el-checkbox>
  </div>
</template>

<script>
import guid from '../../utils/guid'
import getImgSize from '../../utils/get-image-size'
import { mapState } from 'vuex'

export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      btnDisabled: false,
      checked: false,
      watemarkImg: ''
    }
  },
  computed: {
    ...mapState('video', ['videoPlay', 'canvasOptions'])
  },
  watch: {
    checked: {
      handler(val) {
        if (val) {
          this.addWatermaek()
        } else {
          this.deleteWaterMark()
        }
      },
      immediate: true
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {
    async getCompanies() {
      this.btnDisabled = true
      const { err, res } = await this.$get('companies')
      if (res) {
        this.watemarkImg = res.data.shuiyin_url
        this.watemarkImg && (this.btnDisabled = false)
      }
    },
    setChecked() {
      this.checked = !this.checked
    },
    deleteWaterMark() {
      this.$store.commit('video/DELETE_DECORATE', 0)
    },
    async addWatermaek() {
      await this.getCompanies()
      const imgSize = await getImgSize(this.watemarkImg)
      const videoWidth = this.videoPlay.videoWidth
      const videoHeight = this.videoPlay.videoHeight
      const canvasW = this.canvasOptions.canvasW
      const canvasH = this.canvasOptions.canvasH

      const roteW = (canvasW / videoWidth).toFixed(2)
      const roteH = (canvasH / videoHeight).toFixed(2)

      if (!imgSize.h) {
        this.btnDisabled = true
        return
      }

      const id = 'activeBtn' + guid()

      const list = [{
        x: 0,
        y: 0,
        w: imgSize.w * roteW,
        h: imgSize.h * roteH,
        trackLeft: 0,
        trackWidth: Infinity,
        id: id,
        imgUrl: this.watemarkImg,
        resizable: false,
        active: true,
        type: 'image' // 装饰类型
      }]

      this.$store.commit('video/SET_DECORATE_LIST', list)
      this.$store.commit('video/SET_DECORATE_ACTIVE_ID', id)
    }
  }
}
</script>

<style scoped lang="scss">
.watermark-btn {
  height: 30px;
  margin-right: 30px;

  .el-checkbox.is-bordered.el-checkbox--mini {
    height: 30px;
    padding: 5px 15px 3px 10px;
  }
}
</style>
