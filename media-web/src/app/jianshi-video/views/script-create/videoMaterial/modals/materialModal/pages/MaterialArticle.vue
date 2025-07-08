<!--
 * @Author: your name
 * @Date: 2021-09-23 16:01:10
 * @LastEditTime: 2021-09-23 16:55:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/script-create/videoMaterial/modals/materialModal/pages/MaterialArticle.vue
-->
<template>
  <div>
    <ul v-if="materialData.length" class="article-img-list">
      <base-card
        v-for="(item,index) in materialData"
        :key="item.id"
        :show-mark="false"
        :class="['list-item',{'active': my_image_index == index}]"
        @click.native="addItem(item,index)"
      >
        <div slot="img">
          <base-image :src="item.proxy_url" />
          <div class="btn-wrap">
            <p>图片素材</p>
          </div>
        </div>
      </base-card>
    </ul>
    <svg-icon v-else icon-class="empty-img" class="empty-img" />
  </div>

</template>

<script>
import { mapState } from 'vuex'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      my_image_index: null
    }
  },
  computed: {
    ...mapState('jianshi', ['src_data']),
    materialData() {
      return this.src_data.image_list
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    addItem(item, index) {
      this.my_image_index = index
      this.$emit('selectImg', item)
    }
  }
}
</script>

<style scoped lang="scss">
.article-img-list {
  display: flex;
  flex-wrap: wrap;
  height: 480px;
  overflow-y: auto;

  ::v-deep .image {
    height: 100%;
    position: relative;
    background: #e5e5e5;

    .el-image {
      position: static;

      img {
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
      }
    }
  }

  .list-item {
    width: 252px;
    height: 142px;
    margin-bottom: 20px;
    position: relative;
    overflow: hidden;
    margin-right: 15px;
    background-color: #e5e5e5;

    &.active {
      border: 1px solid #5675e8;
    }

    .btn-wrap {
      position: absolute;
      width: 76px;
      height: 24px;
      line-height: 24px;
      font-size: 12px;
      background: rgba(0, 0, 0, .4);
      border-radius: 4px;
      bottom: 20px;
      right: 10px;
      color: #fff;
      text-align: center;
    }
  }
}

.empty-img {
  display: block;
  width: 140px;
  height: 174px;
  margin: 100px auto;
}
</style>
