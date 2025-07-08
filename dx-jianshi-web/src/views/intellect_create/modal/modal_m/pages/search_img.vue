<!--
 * @Author: zzx
 * @Date: 2020-10-27 14:51:33
 * @LastEditTime: 2021-01-04 10:44:45
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/src/search_img.vue
-->
<template>
  <div class="origin-img-wrap">
    <div class="origin-img-content">
      <hander-title @showTypeHandler="showTypeHandler" :typeArr="['原文图库','本地素材']" class='mb-20px'></hander-title>
      <!-- 原文图片 -->
      <div class="mb-25px list" v-if='showType=="原文图库"'>
        <!-- <p class="my-12px">原文图片</p> -->
        <div class="img-list flex-wrap">
          <img-wrap class='mb-10px'
            :active="active_index == `origin_${index}`"
            :img_url="item.proxy_url"
            v-for="(item,index) in original_img_list"
            :key="index" @selectImg="selectImg(`origin_${index}`,item,1)" />
        </div>
      </div>

      <!-- 相似图片 -->
      <!-- <div class="mb-25px" v-if="original_img_list.length > 0">
        <div class="d-flex justify-content-between">
          <p class="mb-12px mr-auto">相似图片</p>
          <el-button type="text" class="more-btn" @click="getMoreAiImg">更多</el-button>
        </div>
        <div class="text-loading" v-if="search_img_loading" style="min-height: 100px;">
          <img width="50" height="35" src="@/assets/images/text_loading.svg" />
        </div>
        <div class="img-list">
          <p
            v-if="!search_img_loading && !similar_img_list.length"
            class="fc-999"
            style="min-height: 100px">未找到相似图片</p>
          <img-wrap
            :active="active_index == `like_${index}`"
            :img_url="url"
            v-for="(url,index) in similar_img_list"
            :key="index"
            @selectImg="selectImg(`like_${index}`,url,3)" />
        </div>
      </div> -->

      <!-- 本地素材 -->
      <material-my
        ref="materialMy"
        @selectImg="selectLocalImg" v-if='showType=="本地素材"' />
    </div>

    <div class="origin-img-bottom">
      <el-button
        type="primary"
        round
        style="width: 100px"
        size="small"
        @click="submit()">完成</el-button>
    </div>
  </div>
</template>

<script>
import { searchImgAi } from '../js/search_img_ai.js'
import { EventBus } from '../js/eventBus'
import handerTitle from '@/components/modal_handel_title'
export default {
  props: {},
  inject: ['modalM'],
  mixins: [searchImgAi],
  data() {
    return {
      active_index: null,
      select_item: null,
      search_img_loading: true,
      search_img_list: [],
      showType: '原文图库'
    }
  },
  computed: {
    original_img_list() {
      return this.modalM.src_list.image_list
    }
  },
  watch: {},
  methods: {
    selectImg(index, item, type) {
      this.active_index = index
      // 原文图片才搜索
      if (item.proxy_url) {
        this.urlByfile(item.proxy_url)
      }
      if (type == 1)
        this.select_item = {
          resource_url: item.proxy_url,
          origin_url: item.origin_url,
          type: 'image'
        }
      else if (type == 3)
        this.select_item = {
          resource_url: item,
          is_ai_image: true,
          type: 'image'
        }
    },
    // 选择本地素材
    selectLocalImg(index, item) {
      // console.log(item)
      this.active_index = null
      this.select_item = item
    },
    submit() {
      this.modalM.submit(this.select_item)
    },
    getMoreAiImg() {
      this.modalM.aiListName = 'AiImgList'
      this.modalM.is_done = false
      this.$set(this.modalM, 'search_img_list', this.search_img_list)
      this.modalM.is_done = true
    },
    showTypeHandler(type) {
      this.showType = type
    }
  },
  components: {
    ImgWrap: () => import('../../../components/img_wrap'),
    MaterialMy: () => import('./material_my'),
    handerTitle
  },
  created() {},
  mounted() {
    if (this.original_img_list && this.original_img_list.length > 0) {
      this.urlByfile(this.original_img_list[0].proxy_url)

      this.$set(this.modalM, 'search_img_list', this.search_img_list)
    }
  }
}
</script>

<style scoped lang="scss">
.list {
  height: 320px;
  overflow-y: auto;
}
.img-list {
  display: flex;
}
.origin-img-wrap {
  height: 0;
  flex: 1;
  display: flex;
  flex-direction: column;
}
.origin-img-content {
  flex: 1;
  overflow: auto;
}
.origin-img-bottom {
  height: 32px;
  text-align: center;
  margin: 30px auto;
}
</style>
