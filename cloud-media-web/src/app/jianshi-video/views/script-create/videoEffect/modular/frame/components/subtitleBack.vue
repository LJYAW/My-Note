<!--
 * @Author: your name
 * @Date: 2021-07-23 15:06:31
 * @LastEditTime: 2021-09-09 17:57:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/frame/components/template.vue
-->
<template>
  <div class="template">
    <el-form-item class="template-box comment-box">
      <el-form-item label="字幕背景:" class="video-back">
        <div class="img-list">
          <div
            v-for="(item,index) in built_in"
            :key="item.id"
            class="item-list"
          >
            <div
              class="img-box"
              :class="tabIndex===index?'activeClass':''"
              @click="tabClick(item,index)"
            >
              <img :src="item.image_url" alt="">
            </div>
            <div class="img-title" :class="titelIndex===index?'activeTitle':''" @click="ImgshowClick(item,index)">{{ item.name }}</div>
          </div>
        </div>
        <!-- 上传 -->
        <div class="el-upload el-upload--picture-card uploadBtn" @click="upload()">
          <i class="el-icon-plus" />
        </div>
      </el-form-item>
    </el-form-item>
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="700px">
      <ImgShow :img-src="imgSrc" @ImgshowFun="ImgshowFun" />
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
      titelIndex: '',
      imgSrc: '',
      dialogVisible: false,
      tabIndex: '',
      built_in: [{
        id: 1,
        image_url: 'https://static-magic.tmsx.net/storage/mnt/zhijian/intelligent_writing/caption_background/moren.png'
      }, {
        id: 2,
        image_url: 'https://static-magic.tmsx.net/storage/mnt/zhijian/intelligent_writing/caption_background/huanqiucaijing.png'
      }, {
        id: 3,
        image_url: 'https://static-magic.tmsx.net/storage/mnt/zhijian/intelligent_writing/caption_background/kejichuangyi.png'
      }]
    }
  },
  computed: {
    ...mapState('jianshi', ['effectData'])
    // 获取store数据(模板数据)
    // built_in() {
    //   const arr = this.$store.state.jianshi.effectList
    //   return arr > 0 ? this.$store.state.jianshi.effectList.caption_bg.built_in : []
    // }
  },
  watch: {
    // 监听回显
    'effectData.caption_bg_id': {
      handler(newName, oldName) {
        if (newName) {
          this.update(newName)
        }
      },
      immediate: true
    }
  },
  created() {
    this.$store.commit('jianshi/SET_TEMPLATEID', this.built_in[0].id)
  },
  mounted() {

  },
  methods: {
    // 回显数据
    update(val) {
      const arr = JSON.parse(JSON.stringify(this.built_in))
      this.tabIndex = arr.findIndex(item => item.id === val)
    },
    // 上传弹框
    upload() {

    },
    // 显示弹框图片展示
    ImgshowClick(item, index) {
      this.titelIndex = index
      this.dialogVisible = true
      this.imgSrc = item.image_url
    },
    // 更新数据
    ImgshowFun() {
      this.dialogVisible = false
      this.imgSrc = ''
    },
    // 点击切换样式
    tabClick(item, index) {
      this.tabIndex = index
      this.$store.commit('jianshi/SET_TEMPLATEID', item.id)
    }
  }
}
</script>

<style scoped lang="scss">

</style>
