<!--
 * @Author: your name
 * @Date: 2021-09-03 15:20:27
 * @LastEditTime: 2021-09-28 11:40:07
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/create-center/intellectCreate/components/audioSubtitle/contentEdit/contentRight.vue
-->
<template>
  <div class="content-right">
    <div v-if="Object.keys(materialObj).length===0" class="upload-box" @click="uploadMatreial()">
      <div class="svg-box">
        <svg-icon icon-class="emptyvideo" class="video-icon" />
      </div>
      <div class="choosetitle">请点击选择视频</div>
    </div>
    <div v-else class="video-material">
      <VideoCard v-if="materialObj.media_type==='video'" :cover-url="materialObj.cover_url" :video-url="materialObj.file_url" />
      <img v-if="materialObj.media_type==='pic'" :src="materialObj.file_url" alt="">
      <div class="edit-material-label" @click="uploadMatreial()">点击更换视频素材</div>
    </div>
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="1000px" title="个人素材">
      <PersonMaterial
        @resetData="resetData"
        @close="close"
      />
    </base-dialog>
  </div>
</template>

<script>
import PersonMaterial from '../model/personMaterial'
import VideoCard from '@/app/jianshi-video/components/VideoCard'

export default {
  components: {
    PersonMaterial, VideoCard
  },
  props: {

  },
  data() {
    return {
      dialogVisible: false,
      materialObj: {}
    }
  },
  computed: {

  },
  watch: {
    materialObj(val) {
      if (Object.keys(val).length !== 0) {
        this.$emit('resetData', val)
      }
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    resetData(data) {
      this.dialogVisible = false
      this.materialObj = data
    },
    close() {
      this.dialogVisible = false
    },
    uploadMatreial() {
      this.dialogVisible = true
    }
  }
}
</script>

<style scoped lang="scss">
.content-right {
  width: 464px;
  height: 260px;
  background: #f7f8f9;
  border-radius: 4px 4px 0px 0px;
  margin-left: 20px;

  .upload-box {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    text-align: center;
    cursor: pointer;

    .svg-box {

      .video-icon {
        width: 40px;
        height: 30px;
      }
    }

    .choosetitle {
      font-size: 12px;
      font-weight: 400;
      color: #bababa;
      margin-top: 20px;
    }
  }

  .video-material {
    position: relative;
    width: 100%;
    height: 100%;

    .edit-material-label {
      height: 20px;
      background: rgba(0, 0, 0, .4);
      font-size: 12px;
      line-height: 12px;
      padding: 4px 10px;
      color: #fff;
      position: absolute;
      bottom: 0;
      width: 100%;
      z-index: 2;
      transition: all .3s;
      border-radius: 0 0 4px 4px;
      cursor: pointer;
      z-index: 1000;
    }
  }
}
</style>
