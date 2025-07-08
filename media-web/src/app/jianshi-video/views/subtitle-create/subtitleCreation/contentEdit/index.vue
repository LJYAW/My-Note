<!--
 * @Author: your name
 * @Date: 2021-09-03 15:19:52
 * @LastEditTime: 2021-10-13 18:42:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/create-center/intellectCreate/components/audioSubtitle/contentEdit/index.vue
-->
<template>
  <div v-loading="loading" class="content-edit">
    <div class="content-title">内容编辑:</div>
    <div v-if="!loading" class="content-box" :style="{'max-height':subtitleVideoSale==='16:9'?'374px':'504px'}">
      <contentLeft />
      <RightPad />
      <!-- <contentRight /> -->
    </div>
  </div>
</template>

<script>
import contentLeft from './contentLeft'
import RightPad from './RightPad.vue'
import { mapState } from 'vuex'
import { GetVideos } from '../../../../api/videos/index'
import { addEvent, removeEvent } from '../../../../utils/dom.js'

// import contentRight from './contentRight'
export default {
  components: {
    contentLeft,
    RightPad
    // contentRight
  },
  props: {

  },
  data() {
    return {
      loading: false
    }
  },
  computed: {
    ...mapState('jianshi', ['subtitleVideoSale'])
  },
  watch: {

  },
  created() {
    this.getInitData()
  },
  mounted() {
    addEvent(document.documentElement, 'mousedown', (e) => {
      this.$bus.$emit('contentEditDown', e)
    })
  },
  beforeDestroy: function() {
    this.$bus.$off('contentEditDown')
    removeEvent(document.documentElement, 'mousedown')
  },
  methods: {
    // 初始化数据
    async getInitData() {
      this.loading = true
      const { res, err } = await GetVideos({ id: this.$route.query.id })
      if (err) {
        this.loading = false
        return
      }
      const data = res.data
      // 设置store数据
      this.$store.commit('jianshi/SET_TITLESINGLE', data)
      // 设置翻译语言
      this.$store.commit('jianshi/SET_TANGSLATELANGUAGE', data.translate_language)
      this.$store.commit('jianshi/SET_LANGUAGE', data.language)
      this.$store.commit('jianshi/SET_DISPLAYLANGUAGE', data.display_language)

      this.loading = false
    }
  }
}
</script>

<style scoped lang="scss">
.content-edit {
  min-height: 344px;

  .content-box {
    display: flex;
  }

  .content-title {
    font-size: 18px;
    font-weight: 600;
    color: #404040;
    margin-bottom: 20px;
  }
}
</style>
