<!--
 * @Author: your name
 * @Date: 2021-09-15 15:51:41
 * @LastEditTime: 2021-09-29 11:11:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/videoEffect/modular/frame/components/subtitleeVideoback.vue
-->
<template>
  <div class="subtitle-video-back template">
    <el-form-item class="template-box comment-box">
      <el-form-item label="视频背景:" class="video-back">
        <div class="img-list">
          <div
            v-for="(item,index) in tempsubtitleList"
            :key="item.id"
            class="item-list"
          >
            <div
              class="item-box"
              :class="tabIndex===index?'activeClass':''"
              @click="tabClick(item,index)"
            >
              <div v-if="item.label" class="item-label">{{ item.label }}</div>
              <div
                v-if="item.file_url"
                class="img-box"
              >
                <img :src="item.file_url" alt="">
              </div>
            </div>

          </div>
        </div>
        <!-- 上传 -->
        <div class="el-upload el-upload--picture-card uploadBtn" style="margin-top: 10px" @click="upload()">
          <i class="el-icon-plus" />
        </div>
      </el-form-item>
    </el-form-item>
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="700px" title="视频背景">
      <ImgShow
        type="视频背景"
        :target-ratio="target_ratio"
        @resetData="resetData"
        @saveData="saveData"
      />
    </base-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import ImgShow from '../model/imgShow'
export default {
  components: {
    ImgShow
  },
  props: {

  },
  data() {
    return {
      tabIndex: 0,
      dialogVisible: false,
      tempsubtitleList: []
    }
  },
  computed: {
    ...mapState('jianshi', ['subtitleVideoBackList', 'submitData', 'target_ratio'])
  },
  watch: {
    // 回显
    // submitData: {
    //   handler(newName, oldName) {
    //     if (newName.bg) {
    //       this.tabIndex = this.subtitleVideoBackList.some(item => item.id === newName.bg)
    //     }
    //   },
    //   immediate: true
    // }
    target_ratio: {
      handler: function() {
        this.getsubtitleVideoBackData()
      }
    }
  },
  created() {
    this.getsubtitleVideoBackData()
  },
  mounted() {

  },
  methods: {
    upload() {
      this.dialogVisible = true
    },
    resetData() {
      this.getsubtitleVideoBackData()
    },
    setStoreData(item, id) {
      this.$store.commit('jianshi/SET_BGID', item)
      this.$store.commit('jianshi/SET_SUBTITLEVIDEOSINGLEDATA', item)
    },
    saveData(obj) {
      this.dialogVisible = false
      const index = this.tempsubtitleList.findIndex(item => item.id === obj.id)
      let item; let id
      if (index > -1) {
        // 找到值
        item = this.tempsubtitleList[index]; id = item.id
        this.tabIndex = index
      } else {
        item = obj; id = obj.id
        this.tempsubtitleList.splice(1, 1, obj)
        this.tabIndex = 0
      }
      this.setStoreData(item, id)
    },
    tabClick(item, index) {
      this.tabIndex = index
      this.setStoreData(item, item.id)
    },
    async getsubtitleVideoBackData() {
      const params = {
        query: `media_type:pic,type:视频背景,sub_type:${this.target_ratio === '9:16' ? '竖版' : '横版'}`,
        sortby: 'id',
        order: 'desc',
        limit: 5,
        page: 1,
        types: '视频背景'
      }
      await this.$store.dispatch('jianshi/getSubtitleData', params).then(res => {
        this.tempsubtitleList = res
        this.tempsubtitleList.unshift({ label: '无', id: 0 })
        this.setStoreData(this.tempsubtitleList[0], 0)
      })
    }
  }
}
</script>

<style scoped lang="scss">

</style>
