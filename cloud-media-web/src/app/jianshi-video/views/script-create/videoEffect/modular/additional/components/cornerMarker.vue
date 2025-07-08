<!--
 * @Author: your name
 * @Date: 2021-07-23 15:07:37
 * @LastEditTime: 2021-09-28 10:22:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/additional/components/cornerMarker.vue
-->
<template>
  <div class="corner-marker">
    <el-form-item class="marker-box" label="自定义角标:">
      <div class="additional-list">
        <div
          v-for="(item,index) in tempMaskerList"
          :key="index"
          class="item-list"
          :class="tabIndex===index?'activeClass':''"
          @click="tabClick(item,index)"
        >
          <img v-if="item.file_url" :src="item.file_url" alt="" class="icon-img">
          <div v-if="item.label">{{ item.label }}</div>
        </div>
      </div>
      <!-- 上传 -->
      <div class="el-upload el-upload--picture-card uploadBtn" @click="upload()">
        <i class="el-icon-plus" />
      </div>
    </el-form-item>
    <base-dialog
      :show.sync="dialogVisible"
      :show-close="false"
      width="700px"
      title="自定义角标"
    >
      <AudioListDialog
        key="maker"
        title="上传角标"
        sub-type="角标"
        :tem-list="subTitleLogoList"
        :file-type="fileType"
        tag="video_logo"
        @resetData="resetData"
      />
    </base-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import AudioListDialog from '../model/audioListModel'
import minxi from '../js/minix'
export default {
  components: {
    AudioListDialog
  },
  mixins: [minxi],
  props: {

  },
  data() {
    return {
      fileType: 'image/png, image/jpeg, image/gif, image/jpg'
    }
  },
  computed: {
    ...mapState('jianshi', ['subTitleLogoList']),
    // 取前四个
    tempMarkerData() {
      const arr = this.subTitleLogoList.length > 0
        ? JSON.parse(JSON.stringify(this.subTitleLogoList)).splice(0, 4) : []
      return arr
    },
    // 数据转换
    tempMaskerList() {
      const arr = JSON.parse(JSON.stringify(this.tempMarkerData))
      arr.unshift({ label: '无角标' })
      return arr
    }
  },
  watch: {
  },
  created() {
    this.initData()
  },
  mounted() {

  },
  methods: {
    // 设置数据
    setData(item) {
      this.$store.commit('jianshi/SET_JBLOGOID', item.label ? null : item)
    },
    // 更新数据
    resetData(obj) {
      this.dialogVisible = false
      if (obj) {
        const index = this.tempMaskerList.findIndex(item => item.id === obj.id)
        let item; let id
        if (index > -1) {
        // 找到值
          item = this.tempMaskerList[index]; id = item.id
          this.tabIndex = index
        } else {
          item = obj; id = obj.id
          this.tempMaskerList.splice(1, 1, obj)
          this.tabIndex = 1
        }
        this.$store.commit('jianshi/SET_JBLOGOID', obj)
      } else {
        this.initData()
      }
    },
    async initData() {
      const params = {
        query: 'media_type:pic,type:角标',
        types: '角标',
        sortby: 'id',
        order: 'desc',
        limit: 10000,
        page: 1
      }
      await this.$store.dispatch('jianshi/getSubtitleData', params)
    }
  }
}
</script>

<style scoped lang="scss">

</style>
