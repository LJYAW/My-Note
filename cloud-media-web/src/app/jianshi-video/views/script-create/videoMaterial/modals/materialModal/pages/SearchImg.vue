<!--
 * @Author: zzx
 * @Date: 2020-10-27 14:51:33
 * @LastEditTime: 2021-09-23 16:25:56
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/src/search_img.vue
-->
<template>
  <div class="origin-img-wrap">
    <div class="origin-img-content">
      <hander-title :type-arr="typeArr" class="hander-title" @showTypeHandler="showTypeHandler" />
      <!-- 原文图片 -->
      <materialArticle v-if="showType=='原文图库'&&material_url" @selectImg="selectArticleImg" />
      <!-- 本地素材 -->
      <MaterialMy
        v-if="showType=='本地素材'"
        @selectImg="selectLocalImg"
      />
    </div>
    <div class="origin-img-bottom">
      <base-btn type="primary" round size="small" @click="submit()">完成</base-btn>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import handerTitle from '../components/ModalHandelTitle.vue'
import MaterialMy from './MaterialMy.vue'
import MaterialArticle from './MaterialArticle.vue'
export default {
  components: {
    handerTitle,
    MaterialMy,
    MaterialArticle
  },
  props: {},
  data() {
    return {
      active_index: null,
      select_item: {},
      showType: '原文图库'
    }
  },
  computed: {
    ...mapState('jianshi', ['src_data', 'material_url']),
    original_img_list() {
      return this.src_data.image_list
    },
    typeArr() {
      const baseArr = ['本地素材']
      if (this.material_url) baseArr.push('原文图库')
      return baseArr
    }
  },
  watch: {
    showType: {
      handler(val) {
        this.$emit('showTypeHandler', val)
      },
      // 一进页面就执行
      immediate: true
    }
  },
  created() {},
  mounted() {

  },
  methods: {
    selectImg(index, item, type) {
      this.active_index = index
      // 原文图片才搜索
      if (type === 1) {
        this.select_item = {
          resource_url: item.proxy_url,
          origin_url: item.origin_url,
          type: 'image'
        }
      } else if (type === 3) {
        this.select_item = {
          resource_url: item,
          is_ai_image: true,
          type: 'image'
        }
      }
    },
    // 选择本地素材
    selectLocalImg(item) {
      this.select_item = item
      this.select_item.type = item.media_type === 'video' ? 'video' : 'image'
      this.select_item.source = '本地素材'
    },
    selectArticleImg(item) {
      this.select_item = item
      this.select_item.type = 'image'
      this.select_item.source = '原文素材'
    },
    submit() {
      this.$emit('submit', this.select_item)
    },
    showTypeHandler(type) {
      this.showType = type
    }
  }
}
</script>

<style scoped lang="scss">
.origin-img-wrap {
  flex: 1;
  display: flex;
  flex-direction: column;

  .origin-img-content {
    flex: 1;
    overflow: auto;

    .list {
      height: 320px;
      overflow-y: auto;

      .img-list {
        display: flex;
        flex-wrap: wrap;
      }
    }
  }

  .origin-img-bottom {
    height: 32px;
    text-align: center;
    margin: 30px auto;
  }
}
</style>
