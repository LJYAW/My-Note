<!--
 * @Author: zzx
 * @Date: 2020-10-27 14:46:27
 * @LastEditTime: 2020-11-11 15:15:09
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/index01.vue
-->
<template>
  <div class="materialM" id="material-m">
    <go-back-btn v-if="aiListName" />

    <div v-show="!videoEditVisible" class="material-m-wrap">
      <search-header />

      <search-img v-show="!aiListName" />

      <component :is="aiListName"></component>
    </div>
    <div v-if="videoEditVisible" style="height:100%;">
      <video-edit />
    </div>
  </div>
</template>

<script>
import SearchImg from './pages/search_img'
import GoBackBtn from './pages/go_back_btn'

export default {
  name: 'modalM',
  props: ['src_list', 'keyword_list', 'layerid'],
  provide() {
    return {
      modalM: this
    }
  },
  components: {
    SearchImg,
    GoBackBtn,
    SearchHeader: () => import('./pages/search_header'),
    AiImgList: () => import('./pages/ai_img_list'),
    AiVideoList: () => import('./pages/ai_video_list'),
    VideoEdit: () => import('./pages/video_edit')
  },
  data() {
    return {
      aiListName: '',
      videoEditVisible: false,
      search_img_list: [],
      search_key: '',
      search_list: [],
      is_done: false,
      video_item: null,
      videoOps: null
    }
  },
  computed: {},
  watch: {},
  methods: {
    submit(select_item) {
      // console.log(select_item)
      if (select_item.type) {
        this.$parent.addItemForModal(select_item)
      }
      this.$layer.close(this.layerid)
    }
  },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.materialM {
  width: 100%;
  height: 100%;
  position: relative;
  padding: 0 26px;
  .material-m-wrap {
    display: flex;
    flex-direction: column;
    height: 100%;
  }
  .active {
    color: $c;
  }
}
</style>
