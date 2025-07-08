<!--
 * @Author: zzx
 * @Date: 2020-09-25 19:01:49
 * @LastEditTime: 2021-07-28 14:23:03
 * @FilePath: /zhijian_web/src/views/intellect_create/video_create/material.vue
-->
<template>
  <div class="edit-material" ref="aaa">
    <div class="new-nav-wrap">
      <div class="item cp" @click="checkoutTab('linkAccess')">
        <img v-if="activeName !== 'linkAccess'" src="@/assets/images/new_svg/link.png" />
        <img v-else src="@/assets/images/new_svg/link-active.png" />
        <!-- <svg-icon icon-class="link" /> -->
        <p>链接获取</p>
      </div>
      <div class="item cp" @click="checkoutTab('scriptCreation')">
        <img v-if="activeName !== 'scriptCreation'" src="@/assets/images/new_svg/code.png" />
        <img v-else src="@/assets/images/new_svg/code-active.png" />
        <!-- <svg-icon icon-class="code" /> -->
        <p>脚本创作</p>
      </div>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">
      <!-- <div class="tab-icon">
        <i :class="['iconlianjie iconfont', activeName == 'linkAccess' && 'activeTab']"></i>
        <i :class="['iconshipin3 iconfont', activeName == 'scriptCreation' && 'activeTab']"></i>
      </div> -->

      <el-tab-pane v-for="(item,index) in tabList" :key="item.name" :label="item.label" :name="item.name">
        <div v-if="item.name == 'linkAccess'" class="px-20px pt-33px">
          <!-- <p class="title fz-16px mb-24px" v-if="title">{{ title }}</p> -->
          <el-input v-model="title" maxlength="40" type="text" show-word-limit placeholder="输入标题" class="title mb-24px fz-16px"></el-input>

          <div class="search-material">
            <el-input v-model="material_url" clearable placeholder="输入文本开始创作，或直接粘贴资讯链接：目前支持百家号及微信公众号的文章链接"></el-input>
            <div class="ml-10px">
              <!-- <span>查看原文</span> -->
              <el-button @click.native="getMaterial()">获取内容</el-button>
            </div>
          </div>
        </div>

        <div class="title d-flex align-items-center pt-33px px-20px" v-else>
          <el-input type="text" v-model="title" maxlength="40" placeholder="输入标题" class="w-100 border-none fz-16px" show-word-limit />
        </div>

        <div v-show="item.name == activeName">
          <materailList @article_msg="getArticleMsg" ref="materialList" :paneName="activeName" />

          <modelSet v-if="!reset_loading" ref="modelSet" />

          <div class="d-flex justify-content-between align-items-center create-video pl-20px">
            <div class="protocol">
              <el-checkbox v-model="checked">勾选即表示同意</el-checkbox>
              <p class="ml-5px fc-999"><span class="fc-c" @click="protocol">《“简视”用户服务协议》</span></p>
            </div>
            <el-button :disabled="!checked" size="small" type="primary" @click="createdVideo(index)" round style="width: 100px;margin: 0 20px;">
              生成视频
            </el-button>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 确认弹框 -->
    <confirmM v-if="modalName == 'confirmM'" @submit="saveAll" @close="closeConfirm" />
    <showProtocol v-if="modalName == 'showProtocol'" @close="closeConfirm" />
  </div>
</template>
<script>
import * as edit from '../js/edit_pad'
import { setRedetDate } from '../js/reset_edti'
import materailList from './material_list'
import modelSet from './video_models/model_set'
import confirmM from '../modal/confirm_m'
import showProtocol from '../modal/show_protocol'
const ting = require('@/assets/images/materail_icon/ting.png')
const TEXT_MS = 4.5 // 一秒6个字
export default {
  data() {
    return {
      reset_loading: false,
      material_url: '',
      activeName: 'linkAccess',
      tabList: [
        {
          name: 'linkAccess',
          label: '链接获取'
        },
        {
          name: 'scriptCreation',
          label: '脚本创作'
        }
      ],
      title: '',
      modalName: '',
      index: 1,
      materailData: {},
      checked: false
    }
  },
  components: {
    materailList,
    modelSet,
    confirmM,
    showProtocol
  },
  computed: {
    restEdtiId() {
      return this.$route.query.reset_edti_id
    }
  },
  watch: {
    restEdtiId: {
      handler(id) {
        if (id) {
          this.resetEdtiVideo(id)
        }
      },
      immediate: true
    }
  },
  methods: {
    checkoutTab(item) {
      this.activeName = item
    },
    handleClick(tab, event) {},
    getMaterial() {
      if (this.material_url) {
        this.$store.commit('setHotLink', this.material_url)
      } else {
        this.$alertMsg('没有获取到链接')
      }
    },
    getArticleMsg(val) {
      this.material_url = val.url
      this.title = val.title
    },
    closeConfirm() {
      this.modalName = ''
    },
    //点击生成视频
    createdVideo(index) {
      let user_details = this.$store.state.user_details
      let energy
      if (user_details) {
        energy = user_details.energy + user_details.temp_energy
      }
      if (!this.checked) {
        this.$alertMsg('请阅读并同意《“闪创”用户服务协议》')
        return
      }
      if (!energy) {
        this.$alertMsg('您的能量值不足')
        return
      }
      this.index = index
      let materialData = this.$refs.materialList[index].$data
      let length = materialData.copy_edtor_text_list[0].text.split('<img').length
      let data = edit.setBaseCaptionTracks(materialData.copy_edtor_text_list, materialData.edtor_img_list, materialData.subTitle, length)

      data.title = this.title
      let err_msg = edit.checkImgListError(materialData.edtor_img_list) // 检查imglist 是否有加载失败的图片
      let img_err_msg = edit.checkData(data, materialData.MAX_SIZE, TEXT_MS) // 检查生成数据 是否正确
      if (err_msg) {
        this.$alertMsg(err_msg)
        return
      }
      if (img_err_msg) {
        this.$alertMsg(img_err_msg)
        return
      }

      this.modalName = 'confirmM'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'confirmM')
      })
    },
    //生成视频
    saveAll() {
      let frameData = this.$refs.modelSet[this.index].$refs.frame.$data
      let attachData = this.$refs.modelSet[this.index].$refs.attach.$data
      let materialData = this.$refs.materialList[this.index].$data
      let length = materialData.copy_edtor_text_list[0].text.split('<img').length
      let caption_tracks = []
      let tracks = []
      let tracks_obj = {}
      tracks_obj = edit.setBaseCaptionTracks(materialData.copy_edtor_text_list, materialData.edtor_img_list, materialData.subTitle, length)
      let virtual_presenter_detail = null
      if (frameData.presen_select.virtual_presenter_id != -1) {
        virtual_presenter_detail = {
          virtual_presenter_id: frameData.presen_select.virtual_presenter_id,
          positon: frameData.presen_radio
        }
      }
      var data = {
        title: this.title,
        subtitle: frameData.subtitle,
        target_ratio: materialData.videoScale,
        tts_per_id: frameData.form.timbre, // 播放音色
        bg_music_id: frameData.bg_music_id, // 如果有，传背景音乐id
        set_caption_bg: 1, // 底部遮挡
        caption_bg_id: frameData.caption_id, // 底部遮挡ID
        video_logo_type: attachData.video_logo_id ? 1 : 2, // 角标类型 2无角标 1有角标
        video_logo_user_res_id: attachData.video_logo_id, // 当角标type=2，角标用户资源id
        video_begin_type: attachData.video_begin_id ? 2 : 1, // 片头类型 1自动片头 2上传素材片头
        video_begin_user_res_id: attachData.video_begin_id, // 当片头type=2 片头用户素材id
        video_end_type: attachData.video_end_type, // 片尾类型 1无片尾 2上传素材片尾 3自定义片尾
        video_end_user_res_id: attachData.video_end_id, // 当片尾type=2 片尾用户素材id
        tracks: tracks_obj.tracks, // 音视频资源列表
        caption_tracks: tracks_obj.caption_tracks, // 字幕资源列表
        virtual_presenter_detail: virtual_presenter_detail // 虚拟主持人
      }
      this.$post('/intelligent-creation/create-timeline-task', data).then(res => {
        if (res.data.code == '0000') {
          this.$alertMsg('您的素材已提交，请到我的作品中查看生成进度。')
          this.$store.dispatch('setUserDetails')
        } else {
          this.closeConfirm()
          this.$alertMsg(res.data.msg)
        }
      })
    },
    // 重新编辑
    resetEdtiVideo(id) {
      this.activeName = 'scriptCreation'
      this.$get('intelligent-creation/success-task-time-line', {
        params: { id: id }
      })
        .then(res => {
          if (res.data.code == '0000') {
            let data = res.data.data
            this.tabList.forEach((item, index) => {
              //重新编辑回显数据
              this.$refs.materialList[index].$data.resetData = data
              this.$refs.materialList[index].$data.edtor_img_list = setRedetDate(data).edtor_img_list
              this.$refs.materialList[index].$data.data_list.image_list = setRedetDate(data).edtor_img_list
              this.$refs.materialList[index].$data.videoScale = data.target_ratio
              this.$refs.materialList[index].$data.edtor_text_list = setRedetDate(data).edtor_text_list
              this.$refs.materialList[index].$data.edtor_text_list.forEach((item, index) => {
                item.text = item.text.replace(/#pau#/g, '<img class="code">')
              })
              this.$refs.materialList[index].$data.subTitle = setRedetDate(data).subTitle //子标题
              let frameData = this.$refs.modelSet[index].$refs.frame.$data
              let attachData = this.$refs.modelSet[index].$refs.attach.$data
              frameData.bg_music_id = data.bg_music_id //背景音乐
              frameData.form.timbre = data.tts_per_id //音色
              frameData.caption_id = data.caption_bg_id //视频模版
              attachData.video_begin_id = data.video_begin_user_res_id //片头
              attachData.video_end_id = data.video_end_user_res_id //片尾
              attachData.video_logo_id = data.video_logo_user_res_id //角标
              if (data.virtual_presenter_detail) {
                frameData.presen_select.positions.push(data.virtual_presenter_detail.positon)
                frameData.presen_select.virtual_presenter_id = data.virtual_presenter_detail.virtual_presenter_id
              }
            })
            this.title = data.title
          } else {
            this.$alertMsg(res.data.msg)
          }
        })
        .finally(() => {})
    },
    protocol() {
      this.modalName = 'showProtocol'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showProtocol')
      })
    }
  }
}
</script>
<style lang="scss">
.edit-material {
  // position: relative;
  display: flex;
  .title {
    color: #404040;
    input {
      padding-right: 85px;
      height: 56px;
    }
  }
  .new-nav-wrap {
    // position: absolute;
    box-shadow: 0px 10px 20px 0px rgba(93, 126, 235, 0.1);
    border-radius: 4px;
    background-color: white;
    height: 190px;
    margin-right: 20px;
    display: flex;
    flex-direction: column;
    font-size: 14px;
    .item {
      width: 100px;
      height: 100px;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      &.active {
        filter: grayscale(100%);
      }
      p {
        margin-top: 10px;
      }
      &:nth-child(1) {
        border-bottom: 1px solid #f5f5f5;
      }
    }
  }
  .el-tabs {
    .el-tabs__header {
      display: none;
      .el-tabs__nav {
        padding-bottom: 14px;
        .el-tabs__active-bar {
          background: $c;
          width: 32px !important;
          height: 4px;
          left: 10px;
        }
      }
      .el-tabs__nav-wrap::after {
        height: 0;
      }
    }
    .el-tabs__item {
      font-size: 20px;
      color: #404040;
      font-weight: normal;
      padding-left: 40px !important;
      padding-right: 72px;
    }
    .el-tabs__item.is-top.is-active {
      border: none;
      color: $c;
    }
    .el-tabs__content {
      overflow: visible;
      .pt-33px {
        background: #fff;
        padding-top: 33px;
      }
      & > i.active {
        color: $c;
      }
      .tab-icon i {
        font-size: 20px;
        position: absolute;
        top: -43px;
        z-index: 3;
      }
      .activeTab {
        color: $c;
      }
      .tab-icon i:first-child {
        left: 8px;
      }
      .tab-icon i:nth-child(2) {
        left: 200px;
      }
      .script-input {
        input {
          padding-right: 75px;
        }
      }
      .el-input {
        input {
          background: #f7f7f7;
          border: none;
        }
        .el-input__count {
          font-size: 16px;
          .el-input__count-inner {
            background: none;
            padding-right: 12px;
          }
        }
      }
    }
  }

  .search-material {
    position: relative;
    display: flex;
    .el-input {
      input {
        border-radius: 0;
        font-size: 12px;
        background-color: #f7f7f7;
        color: #404040;
        border: none;
      }
    }
    & > div:last-child {
      // position: absolute;
      // top: 0;
      // right: 0;
      & > span {
        font-size: 12px;
        color: $c;
        margin-right: 16px;
      }
      .el-button {
        background-color: $c;
        color: #fff;
        border: none;
        // border-radius: 0;
        height: 40px;
      }
    }
  }
  .create-video {
    margin-top: -110px;
    position: relative;
    .protocol {
      // position: absolute;
      // left: 20px;
      // top: 8px;
      p {
        display: inline-block;
        cursor: pointer;
      }
    }
    .create-btn {
      border: 1px solid $c;
      color: $c;
    }
  }
}
</style>
