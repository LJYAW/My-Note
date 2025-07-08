<!--
 * @Author: zzx
 * @Date: 2020-09-27 14:56:58
 * @LastEditTime: 2021-07-19 21:56:06
 * @FilePath: /zhijian_web/src/views/interact_video/video_create/edit_pad.vue
-->
<template>
  <div class="edit-pad interaction">
    <div class="left-pad">
      <div class="title border-bottom bc-b-gray d-flex align-items-center mb-23px">
        <input type="text" v-model="title" maxlength="50" placeholder="输入标题" class="w-100 px-5px py-10px border-none fz-16px" />
        <span :class="['fz-12px fz-16px', title.length > 39 ? 'fc-c' : 'fc-999']">{{ title.length }}/40</span>
      </div>
      <div class="content-create d-flex align-items-center mb-30px">
        <p class="fz-16px mr-15px content-title">内容创作</p>
        <p class="fz-12px fc-999"></p>
        <!-- <el-select v-model="link_type" placeholder="请选择" class="link-type mr-15px" size="mini" style="width: 120px">
          <el-option :label="'资讯链接'" :value="1"></el-option>
          <el-option :label="'沪深股票代码'" :value="2"></el-option>
        </el-select>
        <div class="title border-bottom bc-b-gray d-flex align-items-center flex-1">
          <input
            type="text"
            v-model="created_url"
            :placeholder="link_type == '1' ? ' 输入文本开始创作，或直接粘贴资讯链接：目前支持百家号及微信公众号的文章链接' : '请输入股票代码'"
            class="w-100 px-5px py-10px border-none"
          />
          <el-button type="primary" size="mini" :disabled="ai_loading" round @click="generateByUrl">获取内容</el-button>
        </div>-->
      </div>
      <!-- 视频比例 -->
      <div class="d-flex video-ratio" style="margin-bottom: 26px">
        <p style="margin-right: 18px">视频比例:</p>
        <el-radio v-model="radio_event" label="1">
          <img src="./../../../assets/images/row-screen-icon.svg" />
          <span>横屏16：9</span>
        </el-radio>
        <el-radio v-model="radio_event" label="2">
          <img src="./../../../assets/images/col-screen-icon.svg" />
          <span>竖屏9：16</span>
        </el-radio>
      </div>
      <div class="d-flex video-txt-edite">
        <div class="video-img-list flex-1">
          <p style="margin-bottom: 26px">视频片段</p>

          <div class="img-ul">
            <ul>
              <li :class="['item d-flex', { img_error: item.imgError }]" v-for="(item, index) in edtor_img_list" :key="index">
                <span>{{ enLetter[index] }}:</span>
                <div>
                  <div class="img-wrap" v-if="item">
                    <img v-if="item.type.includes('image')" :src="item.resource_url" @error="imgError(item, index)" />

                    <videoCut
                      :key="item.resource_url"
                      v-if="item.type == 'video'"
                      :video_offset_ms="{
                        start_ms: item.start_ms,
                        end_ms: item.end_ms
                      }"
                      :video_options="{
                        width: '396',
                        height: '225',
                        src: item.resource_url
                      }"
                    />
                  </div>
                  <div :class="['time-ms', { 'bg-c': item.end_ms - item.start_ms < item.duration || item.duration < 3000 }]" v-if="item.duration">
                    时长:{{ (item.duration / 1000).toFixed(1) }}s
                  </div>
                  <div class="layer">
                    <a class="edit-material" @click="edtirMaterial(item, index)">
                      <!-- icondelect -->
                      <!-- <i class="iconfont iconbianjisucaiicon fc-999 fz-12px ml-13px"></i> -->
                      <vsvg class="iconfont fc-999 fz-12px ml-13px" icon="iconbianjisucaiicon"></vsvg>
                      <span class="ml-4px fz-12px">编辑素材</span>
                    </a>
                  </div>

                  <a class="icondelectbtn" v-if="edtor_img_list.length > 1 && edtor_text_list.length > 1" @click="deleteListItem(index, 'img')">
                    <i class="iconfont fc-999 iconclose hove-c fz-18px"></i>
                    <!-- <vsvg class="hove-c fz-12px" icon="iconguanbi"></vsvg> -->
                  </a>
                </div>
              </li>
            </ul>

            <!-- <div class="status-bar"></div> -->
          </div>

          <div class="text-center mt-20px" style="width: 270px">
            <el-button size="mini" @click="addItem()" round>添加片段</el-button>
          </div>
        </div>

        <div class="text-list flex-1 clearfix">
          <p class="mb-3px mb-26px text-sub-title">互动编辑</p>

          <div :class="['video-text-list', { edtor_text: edtor_text }]">
            <div
              :class="[
                'w-100 textarea-wrap d-flex',
                {
                  edtor_text: edtor_text,
                  'bg-danger': str_conunt > MAX_S,
                  'ml-25px': is_transform_video && edtor_text
                }
              ]"
              v-for="(item, j) in edtor_text_list"
              :key="j"
            >
              <!-- 互动效果信息 -->
              <ul class="interact-edit-info" v-if="edtor_text_list[interact_config_index]['content'].length" style="flex: 1">
                <li class="d-flex align-items-center" v-for="(opt, index) in item.content" :key="index">
                  <div class="edit-info-left fz-12px">{{ opt.text }}</div>
                  <template v-if="opt.eventFn == '跳转视频片段'">
                    <p class="edit-info-center">
                      <span>点击后跳转至</span>
                      <span class="ml-25px mr-25px">{{ opt.jumpTo }}</span>
                      <span>片段</span>
                    </p>
                  </template>
                  <template v-else-if="opt.eventFn == '跳转链接'">
                    <p class="edit-info-center">
                      点击后跳转至
                      <a :href="opt.eventParams" target="_blank" class="d-inline-block ml-25px">链接</a>
                    </p>
                  </template>
                  <template v-else-if="opt.eventFn == '拨打电话'">
                    <p class="edit-info-center">
                      点击后拨打
                      <span class="ml-25px">{{ opt.eventParams }}</span>
                    </p>
                  </template>
                </li>
              </ul>
              <div v-else style="display: flex; align-items: center; flex: 1">
                <vsvg class="iconfont fc-666 fz-60px" icon="iconhudong-"></vsvg>
              </div>
              <!-- <i class="iconfont iconhudong- fz-60px fc-bf"></i> -->
              <!-- 编辑互动效果按钮 -->
              <div v-if="edtor_img_list[j]['type']" class="w-100 d-flex align-items-center fz-12px py-8px" style="border-top: 1px solid #f0f0f0">
                <div class="ml-auto mr-10px">
                  <i class="iconfont iconcaidan- fz-12px fc-c"></i>
                  <el-button type="text" size="mini" @click="setTranfromVideoDateils(item, j)">编辑互动效果</el-button>
                </div>
              </div>
            </div>

            <!-- <quersTion video_title="文本编辑帮助" video_url="https://cdn-magic.weijian.video/storage/mnt/online/intelligent_writing/system/FinalVideo_1594183336.021173.mp4" /> -->
          </div>
        </div>
      </div>
    </div>

    <!-- 效果选择 -->
    <!-- <modelSet /> -->
    <div class="d-flex justify-content-center create-video mt-20px">
      <el-button size="small" @click="createdVideo()" style="width: 100px; margin: 0 20px">生成视频</el-button>
    </div>

    <!-- 确认弹框 -->
    <confirmM v-if="modalName == 'confirmM'" @submit="saveAll" @close="closeConfirm" />

    <!-- 确认删除弹框 -->
    <!-- <confirmDeleteText v-if="modalName == 'confirmDeleteText'" /> -->
    <!-- 编辑互动效果弹框
    material:对应左侧素材-->
    <videoInteration
      @videclose="videclose"
      @setInteractionData="handleSetInteractionData"
      @Modalclose="Modalclose"
      v-if="videoName == 'videoInteration'"
      :materials="edtor_img_list"
      :interact_config_index="interact_config_index"
      :btnListFromEdit_pad="edtor_text_list[interact_config_index]['content']"
    />
    <!-- 编辑素材弹框 -->
    <sucaiEditModel v-if="modalName == 'sucaiEdit'" @Modalclose="Modalclose" @addItem="addItemForModal" />
  </div>
</template>

<script>
import modelSet from './model_set.vue'
// import meterialM from './modal/material_m.vue'
import * as edit from './js/edit_pad.js'
import { setRedetDate } from './js/reset_edti'
import confirmM from './modal/confirm_m.vue'
// import quersTion from '@/components/question.vue'
import enLetter from './js/en_letter'
import sucaiEditModel from './modal/sucaiUploadModel/model'
import videoInteration from './modal/videoModels/videoInteration'

const TEXT_MS = 4.5 // 一秒6个字

export default {
  components: { sucaiEditModel, videoInteration, modelSet, confirmM },
  data() {
    return {
      enLetter: enLetter,
      text_select: [
        {
          name: '自由创作',
          size: TEXT_MS * 60 * 3,
          des: '视频时长5分钟以内'
        },
        {
          name: '通用1分钟',
          size: TEXT_MS * 60,
          des: '适应多家平台时间限制'
        },
        {
          name: '精悍2分钟',
          size: TEXT_MS * 60 * 2,
          des: '适合浏览，完播率高'
        }
      ],
      link_type: 1, // 智能链接类型
      MAX_SIZE: TEXT_MS * 60 * 3,
      text_select_index: 0,
      ai_loading: false,
      title: '',
      article_url: '',
      created_url: '',
      data_list: {},
      str_conunt: 0,
      img_conunt_ms: 0,
      edtor_text: false,
      sub_title_list: [],
      radio_event: '1',
      edtor_img_list: [{ type: '', duration: 0 }],
      edtor_text_list: [{ content: [] }],
      interact_config_cur: [], // 当前“编辑交互效果”信息
      interact_config_index: 0, // 当前“编辑交互效果”索引
      // interact_config:[{
      //   content:[{
      //     btn_type:"text", // "image"
      //     text:"按钮上的文字", // "按钮背景图url"
      //     type:"link_inner", //"link_outer","call"
      //     target:"B",//"http://baidu.com","电话号码"
      //   }],
      // }],
      src_list: [],
      edtor_img_index: -1,
      keyword_lsit: [],
      text_temp: '',
      modalName: '',
      resetData: {},
      video_key: 0, // 用于刷新video组件
      is_ai_created: false, // 说明是生成的文章 用于直接生成视频
      is_transform_video: false,
      videoName: '',
      video_modal_show: false
    }
  },
  // components: { modelSet, meterialM, confirmM, quersTion, videoInteration },

  computed: {
    restEdtiId() {
      return this.$route.query.reset_edti_id
    },
    resource_cut_video() {
      return this.$store.state.resource_cut_video
    },
    hot_link() {
      return this.$store.state.hot_link
    },
    MAX_S() {
      let MAX_S = this.MAX_SIZE / TEXT_MS
      if (MAX_S >= 180) {
        MAX_S = 300
      }
      return MAX_S
      // return 300
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
    },
    MAX_SIZE(val) {
      this.text_select_index = this.text_select.findIndex(item => {
        return item.size == val
      })
      let errMsg = edit.checkoutIsURL(this.created_url)
      if (!errMsg && this.edtor_text_list.length < 2) {
        this.generate()
      }
    },
    resource_cut_video: {
      deep: true,
      handler: function(newV, oldV) {
        // 监听剪辑事件
        console.log(newV)

        this.video_key++
        newV.duration = this.edtor_img_list[this.edtor_img_index].duration
        this.$set(this.edtor_img_list, this.edtor_img_index, newV)
        this.$store.commit('modalHidden', 'materialM')
        this.modalName = ''
      }
    },
    edtor_text_list() {
      edit.setTextDom()
    },
    edtor_img_index() {
      this.getKeywordList()
    },
    hot_link(link) {
      if (!link) {
        return
      }
      this.link_type = 1
      this.sub_title_list = []
      this.created_url = link
      this.generate()
    }
  },
  methods: {
    addCode(index, value) {
      let code = '#pau#'
      let el = this.$refs.textareaText[index].$el
      let dom = el.getElementsByTagName('textarea')[0]
      var restoreTop = dom.scrollTop
      edit.insertAtCursor(dom, code)
      let el_value = dom.value
      this.$set(this.edtor_text_list[index], 'text', el_value)
      this.changeText()
      this.$nextTick(() => {
        // 恢复滚动条位置
        dom.scrollTop = restoreTop
      })
    },
    cancelSubTitle(j) {
      this.sub_title_list.splice(j, 1)
    },
    videclose() {
      this.video_modal_show = false
      this.videoName = ''
    },
    setTranfromVideoDateils(item, index) {
      this.video_modal_show = true
      this.videoName = 'videoInteration'
      this.interact_config_cur = item
      this.interact_config_index = index
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'videoInteration')
      })
    },
    // 转为互动视频
    transformVideo() {
      this.$confirm('进入互动视频编辑后将不可返回当前视频编辑状态，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.is_transform_video = true
      })
    },
    // 添加段落标题
    addSubTitle(index) {
      this.$set(this.sub_title_list, index, '')
    },
    // 重新编辑
    resetEdtiVideo(id) {
      console.log('🚀 ~ file: edit_pad.vue ~ line 403 ~ resetEdtiVideo ~ id', id)
      this.ai_loading = true

      console.log('resetEdtiVideo -> resetEdtiVideo', id)
      this.$get('intelligent-creation/success-task-time-line', {
        params: { id: id }
      })
        .then(res => {
          if (res.data.code == '0000') {
            let data = res.data.data
            this.resetData = data
            this.edtor_img_list = setRedetDate(data).edtor_img_list
            this.edtor_text_list = setRedetDate(data).edtor_text_list
            this.title = data.title
          } else {
            this.$alertMsg(res.data.msg)
          }
        })
        .finally(() => {
          this.ai_loading = false
        })
    },
    // imgError
    imgError(item, index) {
      let img = event.srcElement
      let img_error_url = require('@/assets/images/img_error.svg')
      img.src = img_error_url
      img.onerror = null // 防止闪图
      this.$set(this.edtor_img_list[index], 'imgError', true)
    },
    // 设置弹框关键词
    getKeywordList() {
      let list = this.data_list.lexer || []

      let text_str = ''
      if (this.edtor_img_index > -1) {
        if (this.edtor_text_list.length < 2) {
          text_str = this.edtor_img_list[this.edtor_img_index].text_str
        } else {
          text_str = this.edtor_text_list[this.edtor_img_index].text
        }
      }

      let arr = []
      arr = list.filter(text => {
        return text_str.includes(text.item)
      })

      this.keyword_lsit = arr
    },
    // 弹框
    createdVideo() {
      // let data = edit.setBaseCaptionTracks(this.edtor_text_list, this.edtor_img_list, this.sub_title_list)
      // data.title = this.title

      // let err_msg = edit.checkImgListError(this.edtor_img_list) // 检查imglist 是否有加载失败的图片
      // let img_err_msg = edit.checkData(data, this.MAX_SIZE, TEXT_MS) // 检查生成数据 是否正确
      // if (err_msg) {
      //   this.$alertMsg(err_msg)
      //   return
      // }
      // if (img_err_msg) {
      //   this.$alertMsg(img_err_msg)
      //   return
      // }

      // this.modalName = 'confirmM'
      // this.$nextTick(() => {
      //   this.$store.commit('modalShow', 'confirmM')
      // })

      // this.$alertMsg('您的素材已提交，请到我的作品中查看生成进度。')
      this.$layer.iframe({
        content: {
          content: sucaiEditModel,
          parent: this,
          data: { iframeData: this.iframeData }
        },
        area: ['900px', '600px'],
        title: '这是一个标题',
        maxmin: true,
        shade: false,
        shadeClose: false,
        cancel: () => {
          // 关闭弹窗事件
          alert('关闭iframe')
        }
      })
    },
    closeConfirm() {
      this.modalName = ''
    },
    generateByUrl() {
      switch (this.link_type) {
        case 1:
          this.generate()
          break
        case 2:
          this.generateStock()
          break
      }
    },
    // 生成股票链接
    generateStock() {
      this.ai_loading = true
      this.edtor_text_list = [{ type: '', duration: 0 }]
      this.edtor_img_list = [{ type: '', duration: 0 }]

      this.$post('/intelligent-creation/gen-lushen-stock-information', {
        stock_code: this.created_url
      })
        .then(res => {
          console.log('generateStock -> res', res.data.data)
          if (res.data.code == '0000') {
            let data = res.data.data
            this.title = data.title
            this.edtor_text_list[0] = {
              text: data.content,
              duration: edit.getMs(data.content)
            }

            this.edtor_img_list[0] = {
              type: 'image',
              duration: edit.getMs(data.content)
            }

            // 预估时长
            this.str_conunt = edit.getDuration(this.edtor_text_list)
          } else {
            this.$alertMsg(res.data.msg)
          }
        })
        .finally(() => {
          this.ai_loading = false
        })
    },
    // 生成
    generate() {
      let errMsg = edit.checkoutIsURL(this.created_url)
      if (errMsg) {
        this.$alertMsg(errMsg)
        return
      }
      this.ai_loading = true
      this.edtor_text_list = []
      this.edtor_img_list = []

      this.$post('intelligent-creation/analysis-baijiahao-article-by-url', {
        url: this.created_url,
        text_len: this.MAX_SIZE
      }).then(res => {
        console.log(res)
        if (res.data.code == '0000') {
          this.src_list = JSON.parse(JSON.stringify(res.data.data))
          this.data_list = res.data.data
          this.title = this.data_list.title
          this.article_url = this.data_list.article_url

          let text = this.data_list.news_summary
          this.edtor_text_list.push({
            text: text,
            duration: edit.getMs(text)
          })
          // 没有图的情况
          if (res.data.data.image_list.length < 1) {
            this.data_list.image_list.push({})
          }
          // 预估时长
          this.str_conunt = edit.getDuration(this.edtor_text_list)

          this.data_list.image_list.forEach((item, index) => {
            let text_str = edit.getText(text, this.data_list.image_list.length, index)
            let duration = edit.getMs(text_str)

            this.edtor_img_list.push({
              type: 'image',
              resource_url: item.proxy_url,
              origin_url: item.origin_url,
              duration: duration,
              text_str: text_str
            })
          })
          this.resetImgListDuration()
        }
        this.is_ai_created = true
        this.ai_loading = false
        edit.setTextDom()
        this.getKeywordList()
        this.$store.commit('setHotLink', '')
      })
    },
    edtor() {
      this.edtor_text = true
      if (this.edtor_text_list.length < 1) {
        this.edtor_text_list.push({ text: '', duration: 0 })
      }
    },
    changeText(text, index) {
      this.$forceUpdate()
      let text_length = this.edtor_text_list.length
      this.str_conunt = edit.getDuration(this.edtor_text_list)

      if (text_length < 2) {
        this.resetImgListDuration()
      } else {
        let duration = edit.getMs(text)
        this.$set(this.edtor_img_list[index], 'duration', duration)
        this.$set(this.edtor_text_list[index], 'duration', duration)
      }
    },
    // 完成编辑
    submitEdtor() {
      this.edtor_text = false
      let str = ''
      this.edtor_text_list.forEach(item => {
        str += item.text
      })
    },
    // 换行
    keyupEnter(txt, index) {
      if (!this.edtor_text) return

      let text = edit.textToSlice(txt, index)

      let pre_str_ms = edit.getMs(text.pre_str)
      let next_str_ms = edit.getMs(text.naxt_str)
      console.log('keyupEnter -> next_str_ms', next_str_ms)

      // 重置 字数秒数
      this.edtor_text_list[index] = { text: text.pre_str, duration: pre_str_ms }
      this.edtor_text_list.splice(index + 1, 0, {
        text: text.naxt_str,
        duration: next_str_ms
      })
      console.log('keyupEnter -> this.edtor_text_list[index]', this.edtor_text_list[index])

      // 重置 素材秒数
      this.$set(this.edtor_img_list[index], 'duration', pre_str_ms)

      // 添加素材
      let source_img_item = {
        duration: next_str_ms,
        type: ''
      }
      if (this.data_list.image_list) {
        if (this.data_list.image_list[index + 1]) {
          source_img_item = this.data_list.image_list[index + 1] || {}
        }
      }
      // 这里的逻辑可能有问题

      let img_item = Object.assign(
        {
          duration: next_str_ms,
          resource_url: source_img_item.proxy_url || '',
          type: source_img_item.proxy_url ? 'image' : ''
        },
        source_img_item
      )
      console.log('keyupEnter -> img_item', img_item)
      this.edtor_img_list.splice(index + 1, 0, img_item)

      this.edtor_img_list.length = this.edtor_text_list.length
    },
    getFileUrl(url, file) {
      console.log(url)
    },
    // 编辑素材弹框
    edtirMaterial(item, index) {
      this.edtor_img_index = index
      this.modalName = 'sucaiEdit'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'sucaiEdit')
      })
    },
    handleSetInteractionData(params) {
      let { btnList, radio_event, playArry } = params
      this.$set(this.edtor_text_list, this.interact_config_index, { content: btnList })
    },
    Modalclose(modalName) {
      this[modalName] = ''
    },
    addItem() {
      let text_length = this.edtor_text_list.length
      let imng_length = this.edtor_img_list.length

      this.edtor_img_list.push({
        duration: 0,
        resource_url: '',
        type: ''
      })
      this.edtor_text_list.push({ content: [] })
      // 两种情况 一种是直接生成图文 图大于文本长度
      // let add_only_img = imng_length > text_length
      // let add_all = imng_length == text_length

      // if (add_only_img) {
      //   this.resetImgListDuration()
      //   let frast_duration = this.edtor_img_list[0].duration
      //   const MAX_SIZE = 3000

      //   if (frast_duration < MAX_SIZE) {
      //     this.$alertMsg('每个片段最低不能低于3秒')
      //     this.edtor_img_list.splice(imng_length - 1, 1)
      //   }
      // }

      // if (add_all) {
      //   this.edtor_text_list.push({ text: '', duration: 0 })
      // }
    },
    addItemForModal(item) {
      console.log(item)
      let duration = 0
      // if (this.edtor_text_list.length < 2) {
      //   duration = this.edtor_img_list[this.edtor_img_index].duration
      // } else {
      //   duration = this.edtor_text_list[this.edtor_img_index].duration
      // }
      // 如果是原文素材的话
      this.$set(this.edtor_img_list, this.edtor_img_index, {
        duration: duration,
        resource_url: item.resource_url,
        origin_url: item.origin_url,
        is_ai_image: item.is_ai_image,
        type: item.type,
        id: item.id
      })
    },
    saveAll() {
      let musicData = this.$refs.modelSet.$data

      let caption_tracks = []
      let tracks = []
      let tracks_obj = {}

      console.log('saveAll ->  this.sub_title_list', this.sub_title_list)
      tracks_obj = edit.setBaseCaptionTracks(this.edtor_text_list, this.edtor_img_list, this.sub_title_list)

      let virtual_presenter_detail = null
      if (musicData.presen_select.virtual_presenter_id != -1) {
        virtual_presenter_detail = {
          virtual_presenter_id: musicData.presen_select.virtual_presenter_id,
          positon: musicData.presen_radio
        }
      }

      var data = {
        title: this.title,
        tts_per_id: musicData.form.timbre, // 播放音色
        bg_music_id: musicData.bg_music_id, // 如果有，传背景音乐id
        set_caption_bg: musicData.form.set_caption_bg ? 1 : 2, // 底部遮挡
        caption_bg_id: musicData.caption_id, // 底部遮挡ID
        video_logo_type: musicData.video_logo_id ? 1 : 2, // 角标类型 2无角标 1有角标
        video_logo_user_res_id: musicData.video_logo_id, // 当角标type=2，角标用户资源id
        video_begin_type: musicData.video_begin_id ? 2 : 1, // 片头类型 1自动片头 2上传素材片头
        video_begin_user_res_id: musicData.video_begin_id, // 当片头type=2 片头用户素材id
        video_end_type: musicData.video_end_type, // 片尾类型 1无片尾 2上传素材片尾 3自定义片尾
        video_end_user_res_id: musicData.video_end_id, // 当片尾type=2 片尾用户素材id
        tracks: tracks_obj.tracks, // 音视频资源列表
        caption_tracks: tracks_obj.caption_tracks, // 字幕资源列表
        virtual_presenter_detail: virtual_presenter_detail // 虚拟主持人
      }

      this.$post('/intelligent-creation/create-timeline-task', data).then(res => {
        if (res.data.code == '0000') {
          this.$alertMsg('您的素材已提交，请到我的作品中查看生成进度。')
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    // 删除素材
    deleteListItem(index, type) {
      let showCancelButton = index > 0 && !type
      let msg_str = ''
      let confirmButtonText = ''

      if (type) {
        msg_str = '点击确定后将删除所选图片，您可在原文图库中找回'
        confirmButtonText = '确定'
      } else {
        if (this.edtor_text_list[index].duration <= 0) {
          this.edtor_img_list.splice(index, 1)
          if (this.edtor_text_list.length > 1) {
            this.edtor_text_list.splice(index, 1)
          }
          this.resetImgListDuration()
          return
        }
        msg_str = '删除段落后图片与文本将同时删除，是否需要将文本合并至上一段落（合并后视频素材需要重新编辑）'
        confirmButtonText = '直接删除'
      }

      this.$confirm(msg_str, '提示', {
        distinguishCancelAndClose: true,
        showCancelButton: showCancelButton,
        confirmButtonText: confirmButtonText,
        cancelButtonText: '合并'
      })
        .then(() => {
          this.edtor_img_list.splice(index, 1)
          if (this.edtor_text_list.length > 1) {
            this.edtor_text_list.splice(index, 1)
          }
          // this.resetImgListDuration()
        })
        .catch(action => {
          if (action == 'close') return

          this.edtor_img_list.splice(index, 1)
          // let cuurent_text = this.edtor_text_list[index].text
          // let pre_text = this.edtor_text_list[index - 1].text
          // let text_str = pre_text + cuurent_text
          this.edtor_text_list.splice(index, 1)
          // this.$set(this.edtor_text_list[index - 1], 'text', text_str)
          // this.resetImgListDuration()
        })

      // 记录删除历史
      // setDeleteHistory(this.edtor_img_list, this.edtor_text_list, index)
    },
    //  当只有一文本条数据时重新计算时长
    resetImgListDuration() {
      let text_length = this.edtor_text_list.length

      if (text_length == 1) {
        let text = this.edtor_text_list[0].text
        let img_length = this.edtor_img_list.length

        let duration = edit.getMs(text)
        let img_duration = Math.floor(duration / img_length)
        let last_duration = duration - img_duration * (img_length - 1) + 10

        for (let index = 0; index < this.edtor_img_list.length; index++) {
          if (index == img_length - 1) {
            img_duration = last_duration
          }

          this.$set(this.edtor_img_list[index], 'duration', img_duration)
        }
      }
    }
  },
  created() {},
  mounted() {
    edit.setTextDom()
  }
}
</script>

<style lang="scss" scoped>
@import './css/edit_pad.scss';
</style>
