<!--
 * @Author: your name
 * @Date: 2021-07-23 15:06:31
 * @LastEditTime: 2021-10-09 17:15:33
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/frame/components/template.vue
-->
<template>
  <div class="template">
    <el-form-item class="template-box comment-box">
      <el-switch v-model="form.templateSwitch" active-color="#13ce66" inactive-color="#DEDEDE" class="switch-btn" />
      <el-form-item label="视频模板:" class="video-back">
        <div class="img-list">
          <div
            v-for="(item,index) in videoTemplate"
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
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="700px" title="视频模板">
      <!-- <ImgShow :img-src="imgSrc" @ImgshowFun="ImgshowFun" /> -->
      <ImgShow
        type="视频模板"
        @resetData="resetData"
        @saveData="saveData"
      />
    </base-dialog>
  </div>
</template>

<script>
import ImgShow from '../model/imgShow'
import { mapState } from 'vuex'
import minxi from '../js/minix'
import { getMaterial } from '@/app/jianshi-video/api/source-material/index.js'
export default {
  components: {
    ImgShow
  },
  mixins: [minxi],
  props: {

  },

  data() {
    return {
      titelIndex: '',
      imgSrc: '',
      dialogVisible: false,
      tabIndex: 0,
      form: {
        templateSwitch: false
      },
      videoTemplate: []
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
    // 'effectData.caption_bg_id': {
    //   handler(newName, oldName) {
    //     if (newName) {
    //       this.form.templateSwitch = true
    //       this.update(newName)
    //     }
    //   },
    //   immediate: true
    // },

    tabIndex(val) {
      if (val) {
        this.form.templateSwitch = true
      } else {
        this.form.templateSwitch = false
      }
    },
    // 监听滑轮开关
    'form.templateSwitch'(val) {
      if (!val) {
        this.tabIndex = 0
        this.$store.commit('jianshi/SET_TEMPLATE', null)
      }
    }
  },
  created() {
    this.getVideoTemplateData()
  },
  mounted() {

  },
  methods: {
    // 回显数据
    // update(val) {
    //   const arr = JSON.parse(JSON.stringify(this.videoTemplate))
    //   this.tabIndex = arr.findIndex(item => item.id === val)
    //   this.form.templateSwitch = true
    // },
    // 上传弹框

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
      this.$store.commit('jianshi/SET_TEMPLATE', item)
    },
    async getVideoTemplateData() {
      const params = {
        query: 'media_type:pic,type:视频模板,sub_type:横屏',
        sortby: 'id',
        order: 'desc',
        limit: 4,
        page: 1
      }
      const { err, res } = await getMaterial(params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      // await this.$store.dispatch('jianshi/getSubtitleData', params).then(res => {
      this.videoTemplate = res.data
      this.videoTemplate.unshift({ label: '无', id: 0 })
      this.setStoreData(this.videoTemplate[0], 0)
      // })
    },
    resetData() {
      this.getVideoTemplateData()
    },
    saveData(obj) {
      this.dialogVisible = false
      const index = this.videoTemplate.findIndex(item => item.id === obj.id)
      let item; let id
      if (index > -1) {
        // 找到值
        item = this.videoTemplate[index]; id = item.id
        this.tabIndex = index
      } else {
        item = obj; id = obj.id
        this.videoTemplate.splice(1, 1, obj)
        this.tabIndex = 1
      }
      this.setStoreData(item, id)
    },
    setStoreData(item, id) {
      this.$store.commit('jianshi/SET_BGID', item)
      // this.$store.commit('jianshi/SET_SUBTITLEVIDEOSINGLEDATA', item)
    }
  }
}
</script>

<style scoped lang="scss">

</style>
