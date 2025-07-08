<!--
 * @Author: your name
 * @Date: 2021-09-08 15:06:01
 * @LastEditTime: 2021-10-27 16:01:24
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/script-create/index.vue
-->
<template>
  <div class="intellect-create">
    <Header />
    <videoMaterial />
    <videoEffect />
  </div>
</template>

<script>
import videoEffect from './videoEffect/index'
import videoMaterial from './videoMaterial/index'
import Header from './components/header.vue'
import * as duration from './videoMaterial/materialList/js/set-duration'
// import { setRedetDate } from './components/videoMaterial/materialList/js/reset-edti'
import { mapState } from 'vuex'
export default {
  components: {
    videoMaterial,
    videoEffect,
    Header
  },
  props: {

  },
  data() {
    return {
      show: true
    }
  },
  computed: {
    ...mapState('jianshi', ['effectData', 'target_ratio', 'material_tabname'])
  },
  watch: {

  },
  created() {
    if (this.$route.query.reset_edti_id) this.getData()
    if (this.$route.query.pptId) this.getMaterialData()
  },
  mounted() {

  },
  methods: {
    // 重新编辑
    getData() {
      this.show = false
      this.$get('/intelligent-creation/success-task-time-line', {
        params: { id: 18921 }
      })
        .then(res => {
          if (res.data.code === '0000') {
            const data = res.data.data
            this.$store.commit('jianshi/SET_MATERIAL_DATA', data)
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .finally(() => { this.show = true })
    },
    getFileKey(url) {
      const keyData = url.split('//')[1].split('/')
      keyData.splice(0, 1)
      return keyData.join('/')
    },
    async getMaterialData() {
      const { err, res } = await this.$get(`/transppt/${this.$route.query.pptId}`)
      if (err) {
        this.$message.error(err.msg)
      }
      const pptData = res.data.pic_bos_key.split(',')
      const imgData = []
      const dataImgList = []
      const textData = []
      const subtitle = []
      if (pptData.length) {
        pptData.forEach((item) => {
          const src = item.split('|||')[0] || ''
          const text = item.split('|||')[1] || ''
          imgData.push({
            resource_url: src,
            file_key: this.getFileKey(src),
            type: 'image',
            source: '本地素材',
            duration: duration.getMs(text)
          })
          dataImgList.push({
            origin_url: src,
            proxy_url: src
          })
          textData.push({
            text: text
          })
          subtitle.push({
            title: '',
            show: false
          })
        })
      }
      const dataList = {
        image_list: dataImgList
      }
      this.$store.commit('jianshi/SET_TITLE', res.data.titles.split('.')[0])
      this.$store.commit('jianshi/SET_DATA_LIST', dataList)
      this.$store.commit('jianshi/SET_IMG_LIST', imgData)
      this.$store.commit('jianshi/SET_TEXT_LIST', textData)
      this.$store.commit('jianshi/SET_SUBTITLEDATA', subtitle)
    }
  }
}
</script>

<style scoped lang="scss">
.intellect-create {
  flex : 1;
  //   margin-right: 20px;
  padding : 20px 10%;
  height : 100%;
}

</style>
