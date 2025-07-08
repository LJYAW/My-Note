<!--
 * @Author: zzx
 * @Date: 2020-09-27 10:21:24
 * @LastEditTime: 2021-07-30 10:26:48
 * @FilePath: /zhijian_web/src/views/intellect_create/video_create/material_list.vue
-->
<template>
  <div class="d-flex flex-column material-list pt-12px px-20px pb-20px" ref="materialList" v-loading="loading">
    <!-- 切换素材时长 -->
    <div class="select-pad">
      <el-form class="">
        <el-form-item label="视频比例：" label-width="90px" class="video-scale mb-0">
          <el-radio-group v-model="videoScale" class="mb-10px pt-7px">
            <el-radio label="16:9">
              <vsvg icon="iconhengping" class="fz-25px mr-11px iconhengping"></vsvg>
              <span>横屏16：9</span>
            </el-radio>
            <el-radio label="9:16">
              <vsvg icon="iconshuping" class="fz-25px mr-11px iconshuping"></vsvg>
              <span>竖屏9：16</span>
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>

      <div v-if="this.paneName == 'linkAccess'" class="estimate_duration ml-30px">
        <span class="pl-4px">视频时长：</span>
        <el-radio-group v-model="MAX_SIZE">
          <el-radio class="mr-20px fz-12px" v-for="(ms, t) in text_select" :key="t" :label="ms.size">{{ ms.name }}</el-radio>
        </el-radio-group>
      </div>
    </div>
    <!-- 素材列表 -->
    <div class="mt-20px material-item d-flex">
      <!-- 视频文本列表 -->
      <transition-group
        name="list"
        tag="div"
        :class="['material-msg flex-1', (create_url || this.$route.query.reset_edti_id) && edtor_text_list.length == 1 && edtor_img_list.length > 1 && 'content-msg']"
      >
        <div v-for="(text, index) in edtor_text_list" :key="'edtor-text-' + index" :class="[MAX_SIZE != '810' && estimate_duration.toFixed(0) > MAX_S && 'over-time', 'mb-20px']">
          <div
            contenteditable="true"
            ref="textareaText"
            :key="index"
            class="textareaText fz-14px"
            @keydown="divKeyDown($event)"
            @keyup.enter="keyupEnter(text.text, index, $event)"
            @click="getRange(index)"
            @keyup="changeRange(index, $event)"
            v-html="text.text"
            @input="changeText(index)"
          ></div>
          <div class="d-flex fz-12px mt-12px operate-msg">
            <span class="mr-8px" @click="addSubTitle(index)">
              <vsvg icon="iconzibiaoti-weixuanzhong" class="fz-14px mr-4px"></vsvg>
              子标题
              <a v-if="subTitle[index] && !subTitle[index].show && subTitle[index].title">{{ '：' + subTitle[index].title }}</a>
            </span>
            <el-input
              v-model="subTitle[index].title"
              v-if="subTitle[index] && subTitle[index].show"
              @keyup.enter.native="subTitle[index].show = false"
              @blur="subTitle[index].show = false"
              placeholder="请输入子标题"
            ></el-input>
            <span class="mr-25px" @click="addCode(index, text.text, $event)">
              <vsvg icon="iconlangdutingdunweixuanzhong" class="fz-14px mr-4px ml-32px"></vsvg>
              添加朗读停顿
            </span>
          </div>
        </div>
      </transition-group>
      <!-- 视频素材列表 -->
      <transition-group name="list" tag="div" class="material-img">
        <div class="d-flex edit-material-box flex-column-reverse mb-20px" v-for="(item, index) in edtor_img_list" :key="' edtor-img-' + index">
          <img v-if="item.type == 'image'" :src="item.resource_url" @error="imgError(item, index)" />
          <videoCut
            :key="item.resource_url"
            v-if="item.type == 'video'"
            :video_offset_ms="{ start_ms: item.start_ms, end_ms: item.end_ms }"
            :video_options="{
              width: '250',
              height: '140',
              src: item.resource_url
            }"
          />

          <div class="d-flex justify-content-between edit-material-label" @click="editMaterial(item, index)">
            <p class="fz-12px pt-3px w-100 text-center cp">
              <!-- <i class="iconfont iconbianji mr-10px fz-13px"></i> -->
              点击更换视频素材
            </p>
            <div class="fz-20px" v-if="item.duration">{{ (item.duration / 1000).toFixed(1) }}s</div>
          </div>
          <vsvg
            v-if="edtor_img_list.length != edtor_text_list.length || (edtor_img_list.length == edtor_text_list.length && index != 0)"
            icon="iconshanchu"
            @click.native="deleteMaterail(index, item.duration)"
          ></vsvg>
        </div>
      </transition-group>
    </div>
    <!-- 添加素材 -->
    <div class="edit-pad-footer">
      <div class="d-flex justify-content-end w-100">
        <el-button @click.native="addMaterail">+添加素材</el-button>
      </div>

      <p :class="[MAX_SIZE != '810' && estimate_duration.toFixed(0) > MAX_S && 'active', 'all-time text-center']">
        预计视频总时长：
        <span>{{ estimate_duration ? estimate_duration.toFixed(0) : '0' }}s</span>
        <span v-if="MAX_SIZE != '810' && estimate_duration.toFixed(0) > MAX_S">（视频已超长）</span>
      </p>
    </div>
  </div>
</template>
<script>
import * as edit from '../js/edit_pad.js'
import editMaterialContent from '../modal/modal_m/index'
import * as common from '../js/common.js'
import editText from '../js/edii_text'

const TEXT_MS = 4.5 // 一秒6个字
export default {
  mixins: [editText],
  props: ['paneName'],
  data() {
    return {
      MAX_SIZE: TEXT_MS * 60 * 3,
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
      create_url: '',
      estimate_duration: 0, // 预计时长
      edtor_text_list: [{ text: '', duration: 0 }], // 文本列表
      copy_edtor_text_list: [{ text: '', duration: 0 }], // 深复制文本列表
      edtor_img_list: [{ type: '', duration: 0 }], // 素材列表
      data_list: {},
      loading: false,
      subTitle: [{ show: false, title: '' }],
      range: {},
      isEnter: false,
      videoScale: '16:9',
      // 编辑素材使用
      edtor_img_index: -1,
      src_list: [],
      keyword_list: [],
      rangeOffset: 0,
      resetData: {}
    }
  },
  computed: {
    hot_link() {
      return this.$store.state.hot_link
    },
    MAX_S() {
      let MAX_S = this.MAX_SIZE / TEXT_MS
      if (MAX_S >= 180) {
        MAX_S = 300
      }
      return MAX_S
    }
  },
  watch: {
    hot_link(val) {
      if (!val) {
        return
      }
      this.create_url = val
      this.getMaterilaList(val)
      this.subTitle = [{ show: false, title: '' }]
    },
    MAX_SIZE(val) {
      if (val && this.edtor_text_list.length < 2) {
        if (this.paneName == 'scriptCreation' && !this.create_url) return
        this.getMaterilaList(this.create_url)
      }
    },
    edtor_img_index() {
      this.getKeywordList()
    },
    edtor_text_list(val) {
      if (this.$route.query.reset_edti_id) {
        this.edtor_img_list.forEach(item => {
          this.estimate_duration += item.duration
        })
        this.estimate_duration = this.estimate_duration / 1000
        this.copy_edtor_text_list = JSON.parse(JSON.stringify(this.edtor_text_list))
        this.copy_edtor_text_list.forEach(item => {
          item.text = item.text.replace(/<img[^>]*>/g, '#pau#')
        })
      }
    },
    videoScale(val) {
      this.$bus.$emit('video_scale', val)
    }
  },
  methods: {
    // 替换code
    transformCode(text) {
      return text.replace(/#pau#/g, '<img class="code" />')
    },
    changeToImg(val) {
      this.edtor_text_list = JSON.parse(JSON.stringify(this.copy_edtor_text_list))
      this.edtor_text_list.forEach(item => {
        item.text = item.text.replace(/#pau#/g, '<img class="code">')
      })
    },
    // 添加下一个文本和素材
    resetEdtorList(index, text) {
      // 重置当前文本，添加下一个文本
      let pre_str_ms = edit.getMs(text.pre_str)
      let next_str_ms = edit.getMs(text.naxt_str)
      this.copy_edtor_text_list[index].text = text.pre_str
      this.copy_edtor_text_list.splice(index + 1, 0, {
        text: text.naxt_str
      })
      this.changeToImg()
      // 添加下一个素材
      this.$set(this.edtor_img_list[index], 'duration', pre_str_ms)
      let source_img_item = (this.data_list.image_list && this.data_list.image_list[index + 1]) || {}
      let img_item = {
        resource_url: source_img_item.proxy_url || '',
        type: source_img_item.proxy_url ? 'image' : '',
        origin_url: source_img_item.origin_url || '',
        text_str: text.naxt_str,
        duration: next_str_ms
      }
      this.edtor_img_list.splice(index + 1, 0, img_item)
      this.edtor_img_list.length = this.edtor_text_list.length
    },
    //只有一条文本重新计算时长
    resetImgListDuration() {
      let text_length = this.copy_edtor_text_list.length
      if (text_length == 1) {
        let text = this.copy_edtor_text_list[0].text
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
    },
    // 插入标识span标签
    removeSpan(range, index) {
      var parent = document.getElementsByClassName('textareaText')[index]
      var obj = parent.getElementsByClassName('testSpan')
      for (var i = 0; i < obj.length; i++) {
        parent.removeChild(obj[i])
      }
      var span = document.createElement('span')
      span.className = 'testSpan'
      span.style = ''
      range.insertNode(span) // 在焦点插入节点
    },
    // 标识是否是回车事件
    divKeyDown(e) {
      this.isEnter = e.keyCode == '13'
    },
    //文本变化
    changeText(index) {
      if (!this.isEnter) {
        let text_length = this.copy_edtor_text_list.length
        let innerHTML = this.$refs.textareaText[index].innerHTML.replace(/<img[^>]*>/g, '#pau#')
        .replace(/<\/?(?!img\b)\w+\b[^<>]*>/g, '').replace(/&nbsp;/ig, ' ')
        this.$set(this.copy_edtor_text_list[index], 'text', innerHTML)
        this.estimate_duration = edit.getDuration(this.copy_edtor_text_list)
        if (text_length < 2) {
          this.resetImgListDuration()
        } else {
          let duration = edit.getMs(innerHTML)
          this.$set(this.edtor_img_list[index], 'duration', duration)
          // this.$set(this.edtor_text_list[index], 'duration', duration)
        }
        this.getRange(index)
      }
    },
    // 重新定位光标位置
    moveRange(node) {
      let selection = window.getSelection()
      selection.removeAllRanges()
      let newRange = document.createRange()
      newRange.setStart(node, 0)
      newRange.setEnd(node, 0)
      selection.addRange(newRange)
    },
    // 获取光标位置
    getRange(index) {
      this.range = window.getSelection().getRangeAt(0)
      console.log("🚀 ~ file: material_list.vue ~ line 311 ~ getRange ~ this.range", this.range)
      // let element = this.$refs.textareaText[index]
      // this.rangeOffset = common.getCursortPosition(element)
    },
    // 光标位置改变
    changeRange(index, e) {
      if (e.keyCode == '37' || e.keyCode == '38' || e.keyCode == '39' || e.keyCode == '40') {
        this.getRange(index)
      }
    },
    // 添加素材，比较素材和文本的length值，相同就一起添加，不同就只添加素材
    addMaterail() {
      if (this.edtor_img_list.length == this.edtor_text_list.length) {
        this.copy_edtor_text_list.push({ text: '', duration: '' })
        this.subTitle.push({
          title: '',
          show: false
        })
        this.changeToImg()
      }
      this.edtor_img_list.push({ type: '', duration: '' })
    },
    //编辑素材
    editMaterial(item, index) {
      if (this.edtor_text_list.length == this.edtor_img_list.length && item.duration < 3000) {
        this.$alertMsg('时长必须大于3秒')
        return
      }
      this.edtor_img_index = index
      this.$nextTick(() => {
        this.$layer.iframe({
          content: {
            content: editMaterialContent,
            parent: this,
            data: {
              src_list: this.src_list,
              keyword_list: this.keyword_list,
              resetData: this.resetData
            }
          },
          area: ['990px', '660px'],
          title: '浏览视频内容，从中剪选所需镜头',
          maxmin: true,
          shade: true,
          shadeClose: true,
          cancel: () => {
            // 关闭弹窗事件
            // alert('关闭iframe')
          }
        })
      })
    },
    // 点击完成编辑
    addItemForModal(item) {
      let data = {},
        duration = this.edtor_img_list[this.edtor_img_index].duration
      // 如果是原文素材的话
      if (item.type == 'image') {
        let { resource_url, origin_url, is_ai_image, type, id } = item
        data = {
          duration,
          resource_url,
          origin_url,
          is_ai_image,
          type,
          id
        }
      } else if (item.type == 'video') {
        //视频
        let { id, end_ms, resource_url, start_ms, type, uuid, video_hd_str, video_origin, decorateList, videoSize } = item
        data = { id, duration, end_ms, resource_url, start_ms, type, uuid, video_hd_str, video_origin, decorateList, videoSize }
      }
      this.$set(this.edtor_img_list, this.edtor_img_index, data)
    },
    // 删除素材
    deleteMaterail(index, duration) {
      if (!duration) {
        this.copy_edtor_text_list.splice(index, 1)
        this.edtor_img_list.splice(index, 1)
        this.subTitle.splice(index, 1)
        this.changeToImg()
        return
      }
      if (this.edtor_text_list.length == '1') {
        this.edtor_img_list.splice(index, 1)
        this.resetImgListDuration()
        return
      }
      let msg = '删除段落后图片与文本将同时删除，是否需要将文本合并至上一段落（合并后视频素材需要重新编辑）'
      this.$confirm(msg, '提示', {
        distinguishCancelAndClose: true,
        showCancelButton: true,
        confirmButtonText: '直接删除',
        cancelButtonText: '合并'
      })
        .then(() => {
          this.edtor_img_list.splice(index, 1)
          if (this.copy_edtor_text_list.length > 1) {
            this.copy_edtor_text_list.splice(index, 1)
            this.subTitle.splice(index, 1)
          }
          this.changeToImg()
          this.estimate_duration = edit.getDuration(this.copy_edtor_text_list)
          this.resetImgListDuration()
        })
        .catch(action => {
          if (action == 'close') return
          this.edtor_img_list.splice(index, 1)
          let text_str = this.copy_edtor_text_list[index - 1].text + this.copy_edtor_text_list[index].text
          this.copy_edtor_text_list.splice(index, 1)
          this.subTitle.splice(index, 1)
          this.copy_edtor_text_list[index - 1].text = text_str
          this.$set(this.edtor_img_list[index - 1], 'duration', edit.getMs(text_str.replace(/<\/?.+?\/?>/g, '')))
          this.changeToImg()
          this.resetImgListDuration()
        })
    },
    // 添加子标题
    addSubTitle(index) {
      let title = this.subTitle[index] ? this.subTitle[index].title : ''
      let item = {
        title: title,
        show: true
      }
      this.$set(this.subTitle, index, item)
    },
    // 获取素材
    getMaterilaList(url) {
      let errMsg = edit.checkoutIsURL(url)
      if (errMsg) {
        this.$alertMsg(errMsg)
        return
      }
      this.loading = true
      this.$post('intelligent-creation/analysis-baijiahao-article-by-url', {
        url: url,
        text_len: this.MAX_SIZE
      }).then(res => {
        this.loading = false
        if (res.data.code == '0000') {
          this.src_list = JSON.parse(JSON.stringify(res.data.data))
          this.data_list = res.data.data
          let article_msg = {
            title: this.data_list.title,
            url: this.data_list.article_url
          }
          this.$emit('article_msg', article_msg)
          this.edtor_text_list = []
          this.edtor_img_list = []
          let text = this.data_list.news_summary
          this.edtor_text_list.push({
            text: text
          })
          this.$refs.textareaText[0].innerHTML = text
          // 没有图的情况
          if (res.data.data.image_list.length < 1) {
            this.data_list.image_list.push({})
          }
          this.data_list.image_list.forEach((item, index) => {
            let text_str = edit.getText(text, this.data_list.image_list.length, index)
            let duration = edit.getMs(text_str, 0)
            this.edtor_img_list.push({
              type: 'image',
              resource_url: item.proxy_url,
              origin_url: item.origin_url,
              duration: duration,
              text_str: text_str
            })
          })
          this.copy_edtor_text_list = JSON.parse(JSON.stringify(this.edtor_text_list))
          // 预估时长
          this.estimate_duration = edit.getDuration(this.edtor_text_list)
          this.$store.commit('setHotLink', this.material_url)
        }
        this.getKeywordList()
      })
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
      this.keyword_list = arr
    },
    imgError(item, index) {
      let img = event.srcElement
      let img_error_url = require('@/assets/images/img_error.svg')
      img.src = img_error_url
      img.onerror = null // 防止闪图
      this.$set(this.edtor_img_list[index], 'imgError', true)
    }
  },
  mounted() {}
}
</script>
<style lang="scss">
@import '../css/material_list.scss';

.edit-pad-footer {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  .el-button {
    width: 100%;
    border-radius: 4px;
  }
}
.select-pad {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
</style>
