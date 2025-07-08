<!--
 * @Author: your name
 * @Date: 2021-07-23 15:06:31
 * @LastEditTime: 2021-09-29 11:11:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/frame/components/template.vue
-->
<template>
  <div class="template subtitle-back">
    <el-form-item class="template-box comment-box">
      <el-form-item label="字幕背景:" class="video-back">
        <div class="img-list">
          <div
            v-for="(item,index) in tempsubtitlrBacklist"
            :key="item.id"
            class="item-list"
          >
            <div class="item-box" :class="tabIndex===index?'activeClass':''" @click="tabClick(item,index)">
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
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="700px" title="字幕背景">
      <ImgShow
        type="字幕背景"
        @resetData="resetData"
        @saveData="saveData"
      />
    </base-dialog>
  </div>
</template>

<script>
import ImgShow from '../model/imgShow'
import { mapState } from 'vuex'

export default {
  components: {
    ImgShow
  },
  props: {

  },
  data() {
    return {
      dialogVisible: false,
      tabIndex: 0,
      tempsubtitlrBacklist: []
    }
  },
  computed: {
    ...mapState('jianshi', ['subtitleBackList', 'submitData'])
  },
  watch: {
    // 回显
    // submitData: {
    //   handler(newName, oldName) {
    //     if (newName.bg_zm_id) {
    //       this.tabIndex = this.subtitleBackList.some(item => item.id === newName.bg_zm_id)
    //     }
    //   },
    //   immediate: true
    // }
  },
  created() {
    this.getsubtitleBackData()
  },
  mounted() {

  },
  methods: {
    saveData(obj) {
      this.dialogVisible = false
      const index = this.tempsubtitlrBacklist.findIndex(item => item.id === obj.id)
      let item; let id
      if (index > -1) {
        // 找到值
        item = this.tempsubtitlrBacklist[index]; id = item.id
        this.tabIndex = index
      } else {
        item = obj; id = obj.id
        this.tempsubtitlrBacklist.splice(1, 1, obj)
        this.tabIndex = 1
      }
      this.setStoreData(item, id)
    },
    async getsubtitleBackData() {
      const params = {
        query: 'media_type:pic,type:字幕背景',
        sortby: 'id',
        order: 'desc',
        limit: 5,
        page: 1,
        types: '字幕背景'
      }
      await this.$store.dispatch('jianshi/getSubtitleData', params).then(res => {
        this.tempsubtitlrBacklist = res
        this.tempsubtitlrBacklist.unshift({ label: '无', id: 0 })
        this.setStoreData(this.tempsubtitlrBacklist[0], 0)
      })
    },
    setStoreData(item, id) {
      this.$store.commit('jianshi/SET_ZMBGID', item)
      this.$store.commit('jianshi/SET_SUBTITLEBACKSINGLEDATA', item)
    },
    // 回显数据
    update(val) {
      const arr = JSON.parse(JSON.stringify(this.built_in))
      this.tabIndex = arr.findIndex(item => item.id === val)
    },
    // 上传弹框
    upload() {
      this.dialogVisible = true
    },
    // 更新数据
    resetData() {
      this.getsubtitleBackData()
    },
    // 点击切换样式
    tabClick(item, index) {
      this.tabIndex = index
      this.$store.commit('jianshi/SET_ZMBGID', item)
      this.setStoreData(item, item.id)
    }
  }
}
</script>

<style scoped lang="scss">

</style>
