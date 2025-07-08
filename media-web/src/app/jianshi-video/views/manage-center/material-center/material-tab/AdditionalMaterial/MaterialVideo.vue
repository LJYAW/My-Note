<!--
 * @Author: your name
 * @Date: 2021-09-01 15:57:57
 * @LastEditTime: 2021-09-17 16:27:19
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/material-center/material-tab/AdditionalMaterial/MaterialVideo.vue
-->
<template>
  <base-card :show-mark="false" class="video-item">
    <div slot="img">
      <VideoCard :cover-url="materialData.cover_url" :video-url="materialData.file_url" />
      <div class="btn-wrap">
        <p>视频时长 {{ materialData.duration|timesToHMS }}</p>
        <svg-icon icon-class="delete" @click.native="deleteMaterial" />
      </div>
    </div>
  </base-card>
</template>

<script>
import VideoCard from '@/app/jianshi-video/components/VideoCard'
export default {
  components: {
    VideoCard
  },
  props: {
    materialData: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {

    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    deleteMaterial() {
      this.$emit('delete', this.materialData.uuid)
    }
  }
}
</script>

<style scoped lang="scss">
.video-item {
  margin: 0 12px;

  ::v-deep .image {
    border-radius: 4px;
    height: auto;
    position: relative;

    .btn-wrap {
      position: absolute;
      width: 100%;
      padding: 9px;
      bottom: 0;
      z-index: 9;
      color: #fff;
      font-size: 12px;
      line-height: 12px;
      background: rgba(0,0,0,.4);
      display: flex;
      justify-content: space-between;
    }

  }
}
</style>
