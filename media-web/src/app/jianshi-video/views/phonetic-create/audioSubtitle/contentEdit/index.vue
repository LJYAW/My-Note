<!--
 * @Author: your name
 * @Date: 2021-09-03 15:19:52
 * @LastEditTime: 2021-10-14 14:52:12
 * @LastEditTime: 2021-09-26 15:33:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/create-center/intellectCreate/components/audioSubtitle/contentEdit/index.vue
-->
<template>
  <div class="content-edit">
    <div class="content-title">内容编辑:</div>
    <div v-for="(item,index) in materialList" :key="'id'+index" class="content-box">
      <i
        v-if="index!==0"
        class="el-icon-remove del-btn"
        @click="deleteMetarial(item,index)"
      />
      <contentLeft :key="'left'+index" ref="contentLeft" :show-data="showData" @resetData="resetData" />
      <contentRight :key="'right'+index" ref="contentRight" @resetData="resetData" />
    </div>
    <div class="add-material" @click="addMaterial()">+  点击添加素材段落</div>
  </div>
</template>

<script>
import contentLeft from './contentLeft'
import contentRight from './contentRight'
import { setBaseCaptionTracks } from '../js/integration-data'
import { mapState } from 'vuex'

export default {
  components: {
    contentLeft,
    contentRight
  },
  props: {

  },
  data() {
    return {
      materialList: [{}],
      integrationData: [],
      showData: false
    }
  },
  computed: {
    ...mapState('jianshi', ['tracks', 'caption_tracks']),
    routerData() {
      return this.$route.query
    }
  },
  watch: {
    materialList: {
      handler(newName, oldName) {
        this.setMaterialData(newName)
      },
      immediate: true
    }
  },
  created() {
    if (this.routerData.taskId) {
      this.showData = true
    }
  },
  methods: {
    resetData(data) {
      this.setMaterialData(this.materialList)
    },
    setMaterialData(data) {
      this.$nextTick(() => {
        data.forEach((item, index) => {
          item.caption_txt_time_line = this.$refs.contentLeft[index].contentList || []
          item.tracks = this.$refs.contentRight[index].materialObj || null
          item.duration = this.$refs.contentLeft[index].duration || 0
          item.file_bos_key = this.$refs.contentLeft[index].audioFileKey
        })
        const obj = setBaseCaptionTracks(this.materialList)
        this.$store.commit('jianshi/set_caption_tracks', obj.caption_tracks)
        this.$store.commit('jianshi/set_resourcedetail', obj.tracks)
      })
    },
    deleteMetarial(item, index) {
      this.showData = false
      this.materialList.splice(index, 1)
    },
    addMaterial() {
      this.showData = false
      this.materialList.push({})
    }
  }
}
</script>

<style scoped lang="scss">
.content-edit {

  .content-box {
    position: relative;
    display: flex;
    margin-top: 20px;

    .del-btn {
      position: absolute;
      right: 5px;
      top: 5px;
      color: #fd5352;
      cursor: pointer;
      font-size: 16px;
      z-index: 1000;
    }
  }

  .content-title {
    font-size: 18px;
    font-weight: 600;
    color: #404040;
    margin-bottom: 20px;
  }

  .add-material {
    width: 100%;
    height: 40px;
    font-size: 14px;
    font-weight: 460;
    color: #5675e8;
    margin-top: 20px;
    border-radius: 4px;
    border: 1px solid #5675e8;
    text-align: center;
    line-height: 40px;
    cursor: pointer;

    &:hover {
      color: #5675e8;
      border-color: #ccd6f8;
      background-color: #eef1fd;
    }
  }
}
</style>
