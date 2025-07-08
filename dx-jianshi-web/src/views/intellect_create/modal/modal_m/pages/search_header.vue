<!--
 * @Author: zzx
 * @Date: 2020-10-27 14:48:28
 * @LastEditTime: 2020-12-15 15:35:07
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/src/search_header.vue
-->
<template>
  <div class="search-header">
    <!-- <upload
      file_type=" image/jpeg"
      class="upload"
      ref="upload"
      button_name
      @getFileUrl="getFileImg">
      <i class="iconfont iconcamera fz-28px cp hove-c"></i>
    </upload> -->

    <el-input v-model="search_key" @keyup.enter.native="searchKeyWord" placeholder="输入关键词查找素材">

      <el-button
        slot="append"
        @click="searchKeyWord"
        type="primary"
        icon="iconfont iconsearch fz-16px fc-white"></el-button>
    </el-input>

    <div class="fz-12px mt-10px d-flex mb-20px">
      <div v-if="keyWrods.length > 0">
        <div class="d-flex flex-wrap">
          <span style="min-width: 80px">推荐关键字：</span>
          <a
            v-for="(key,j) in keyWrods"
            @click="keyWordClick(key.item)"
            class="mr-10px mb-6px hove-c fz-12px"
            :key="j">{{key.item}}</a>
        </div>
      </div>
    </div>
    <!-- <div class="key-list">
      <span v-for="(item,index) in keyWrods" :key="index">{{item.item}}</span>
    </div> -->
  </div>
</template>

<script>
import { EventBus } from '../js/eventBus'
import { searchImgAi } from '../js/search_img_ai.js'
import { searchVideoAi } from '../js/search_video_ai.js'

export default {
  props: {},
  mixins: [searchImgAi, searchVideoAi],
  inject: ['modalM'],
  data() {
    return {
      search_key: '',
      // 搜索图片列表
      search_img_list: [],
      // 搜索视频列表
      search_list: []
    }
  },
  computed: {
    keyWrods() {
      return this.modalM.keyword_list
    }
  },
  watch: {},
  methods: {
    // 上传图片并搜索
    async getFileImg(url, file) {
      this.search_key = ''
      this.modalM.is_done = false

      this.modalM.aiListName = 'AiImgList'
      this.search_img_list = []
      await this.asyncGetImgList(file)
      this.$refs.upload.clearFile()

      this.$set(this.modalM, 'search_img_list', this.search_img_list)
      this.modalM.is_done = true
    },
    // 关键字搜索
    keyWordClick(text) {
      this.search_key = text
      this.searchKeyWord()
    },
    // 输入关键字搜索
    async searchKeyWord() {
      if (!this.search_key) {
        this.$alertMsg('请输入关键词')
        return
      } else {
        this.modalM.is_done = false
        this.modalM.aiListName = 'AiVideoList'

        await this.searchAll(this.search_key)

        this.$set(this.modalM, 'search_key', this.search_key)
        this.$set(this.modalM, 'search_list', this.search_list)
        this.modalM.is_done = true
      }
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.search-header /deep/ {
  .el-input-group__append {
    background-color: #c51a1a;
    height: 35px;
    border: none;
    border-radius: 0 3px 3px 0;

    .el-button {
      height: 34px;
      padding: 0 22px;
      text-align: center;
    }
  }
  .el-input-group {
    .el-input__inner {
      border-radius: 3px 0 0 3px;
    }
  }
}
.search-header {
  width: 50%;
  // height: 132px;
  padding-top: 26px;
  margin: 0 auto;
  position: relative;

  .iconcamera {
    position: absolute;
    right: 62px;
    top: 29px;
    z-index: 11;
  }
}
</style>
