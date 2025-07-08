<!--
 * @Author: your name
 * @Date: 2021-07-23 15:06:45
 * @LastEditTime: 2021-09-27 10:53:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/frame/components/host.vue
-->
<template>
  <div class="host">
    <el-form-item class="host-box comment-box">
      <div class="host-message">
        <!-- 主持人 -->
        <div class="host-list">
          <el-switch v-model="form.hostSwitch" active-color="#13ce66" inactive-color="#DEDEDE" class="switch-btn" />
          <el-form-item label="虚拟主持人:" class="virtual-presenter">
            <div class="img-list">
              <div
                v-for="(item,index) in virtual_presenters"
                :key="item.id"
                class="item-list"
              >
                <div
                  class="img-box"
                  :class="tabIndex===index?'activeClass':''"
                  @click="tabClick(item,index)"
                >
                  <img :src="item.cover_url" alt="">
                </div>
                <div class="img-title">{{ item.title }}</div>
              </div>
            </div>
          </el-form-item>
        </div>
        <!-- 位置 -->
        <div class="host-position">
          <!-- <el-switch v-model="form.imgSwitch" active-color="#13ce66" inactive-color="#DEDEDE" class="switch-btn" :disabled="disabled" /> -->
          <el-form-item class="video-back" :label="presen_radio==='left'?'主持人居左:':'主持人居右:'">
            <div class="presen_postion">
              <img :src="presen_postion_img" width="54" height="50">
              <!-- <div class="positions-title">
                <span class="title-left fream-title">{{ presen_radio==="left"?'画面左侧':'画面右侧' }}</span>
              </div> -->
            </div>
          </el-form-item>
        </div>
      </div>
    </el-form-item>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { getMaterial } from '@/app/jianshi-video/api/source-material/index.js'
export default {
  components: {
  },
  props: {
  },
  data() {
    return {
      tabIndex: '',
      disabled: false,
      form: {
        hostSwitch: false
        // imgSwitch: false
      },
      presen_radio: 'left'
    }
  },
  computed: {
    ...mapState('jianshi', ['effectList', 'effectData', 'virtual_presenter', 'submitData']),
    // 画面图片位置
    presen_postion_img() {
      return require(`@/assets/images/createVideo/host_${this.presen_radio}.png`)
    },
    sex() {
      return this.submitData.update_audio_id.ext ? JSON.parse(this.submitData.update_audio_id.ext).sex : 1
    },
    virtual_presenters() {
      const arr = this.virtual_presenter || []

      const arr1 = arr.filter(item => {
        if (JSON.parse(item.ext).sex === this.sex) return item
      })
      return arr1
    }
  },
  watch: {
    // // 监听回显
    // 'effectData.virtual_presenter_detail': {
    //   handler(newName, oldName) {
    //     if (newName) {
    //       this.form.hostSwitch = true
    //       if (newName.positon) {
    //         this.form.imgSwitch = newName.positon === 'right'
    //       }
    //       this.update(newName.virtual_presenter_id)
    //     }
    //   },
    //   immediate: true
    // },
    'form.hostSwitch': {
      handler(newName, oldName) {
        if (!newName) {
          this.presen_radio = 'left'
          this.tabIndex = -1
          this.$store.commit('jianshi/SET_PERSONDEATIL', null)
        } else {
          this.tabClick(this.virtual_presenters[0], 0)
        }
      },
      immediate: true
    }
    // 'form.imgSwitch': {
    //   handler(newName, oldName) {
    //     if (newName) {
    //       this.presen_radio = 'right'
    //     } else {
    //       this.presen_radio = 'left'
    //     }
    //   },
    //   immediate: true
    // }
  },
  created() {
    this.getInitData()
  },
  mounted() {

  },
  methods: {
    // 回显数据
    // update(val) {
    //   const arr = JSON.parse(JSON.stringify(this.virtual_presenter))
    //   this.tabIndex = arr.findIndex(item => item.virtual_presenter_id === val)
    // },
    // 点击切换样式
    tabClick(item, index) {
      this.tabIndex = index
      this.form.hostSwitch = true
      const { position, sex } = JSON.parse(item.ext)
      // this.presen_radio = positions.length >= 2 ? 'left' : positions[0]
      // console.log(Object.keys(position))
      // const obj = {
      //   positon: this.presen_radio,
      //   virtual_presenter_id: item.id
      // }
      this.presen_radio = Object.keys(position)[0]

      this.$store.commit('jianshi/SET_PERSONDEATIL', item)
    },
    async getInitData() {
      const params = {
        query: 'media_type:video,source:系统,type:虚拟主持人',
        sortby: 'id',
        order: 'desc',
        limit: 9,
        page: 1
      }
      const { err, res } = await getMaterial(params)
      this.$store.commit('jianshi/SET_VIRTUAL_PRESENTER', res.data)
      if (err) {
        this.$message.error(err.msg)
        return
      }

      // await this.$store.dispatch('jianshi/getSubtitleData', params).then(res => {
      //   console.log('🚀 ~ file: host.vue ~ line 156 ~ awaitthis.$store.dispatch ~ res', res)
      //   // this.videoTemplate = res
      //   // this.videoTemplate.unshift({ label: '无', id: 0 })
      //   // this.setStoreData(this.videoTemplate[0], 0)
      // })
    }
  }
}
</script>

<style scoped lang="scss">
.host-list {
  display: flex;

  .virtual-presenter {
    display: flex;

    .img-box {
      border: 1px solid #ccc;
      border-radius: 4px;
      display: block;
      width: 96px;
      height: 54px;
    }

    .img-title {
      text-align: center;
      font-size: 12px;
      font-weight: 400;
      color: #454545;
    }
  }
}
</style>
