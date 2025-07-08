<!--
 * @Author: zzx
 * @Date: 2020-09-02 18:19:06
 * @LastEditTime: 2020-12-22 15:20:40
 * @FilePath: /zhijian_web/src/views/video_create/modal/material/index.vue
-->
<template>
  <model :id="'materialM'" ref="materialM" class="model-ll" @close="close">
    <div slot="title">
      <p class="modal-title fz-14px">浏览视频内容，从中剪选所需镜头</p>
    </div>
    <div slot="body">
      <div class="material-header">
        <!-- 搜索框 -->
        <el-input v-model="search_key" @keyup.enter.native="searchAll" placeholder="输入关键词查找素材">
          <el-button
            slot="append"
            @click="searchAll"
            type="primary"
            icon="iconfont iconsearch fz-16px"></el-button>
        </el-input>
      </div>
      <div class="material-content">
        <originalImg />

        <aiImgList />

        <myMaterial />
      </div>
    </div>
  </model>
</template>

<script>
import originalImg from './src/original_img'
import aiImgList from './src/ai_img_list'
import myMaterial from './src/my_material'

export default {
  props: {},
  data() {
    return {
      search_key: ''
    }
  },
  computed: {},
  watch: {},
  methods: {
    searchAll() {},
    close() {
      this.$emit('Modalclose')
      this.$store.commit('setCutTrackList', [])
    }
  },
  components: {
    originalImg,
    aiImgList,
    myMaterial
  },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
</style>
