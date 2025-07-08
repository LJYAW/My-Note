<!--
 * @Author: your name
 * @Date: 2021-07-23 11:29:31
 * @LastEditTime: 2021-10-22 16:41:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoMaterial/components/MaterialText.vue
-->
<template>
  <div class="material-text-wrap">
    <transition-group name="list" tag="div" class="material-list">
      <!-- :class="[MAX_SIZE != '810' && estimate_duration.toFixed(0) > MAX_S && 'over-time']" -->
      <div
        v-for="(item, index) in edtor_text_list"
        :key="`edit${index}`"
        :class="[img_list.length!=text_list.length&&'auto-wrap','material-item']"
      >
        <!-- 添加子标题和朗读停顿 -->
        <RichTextEdit
          ref="richText"
          :inner-text="item.text"
          :index="index"
          @inputText="inputText(index,$event)"
          @changeTextList="changeStateTextList(index)"
          @keyupEnter="keyupEnter(index,$event)"
        >
          <!--  -->

          <div class="material-btn">
            <div class="btn-wrap">
              <div class="btn-item">
                <div @click="showSubTitle(index)">
                  <svg-icon icon-class="sub-title" />添加子标题
                  <span v-if="!subTitle[index].show && subTitle[index].title">:{{ subTitle[index].title }}</span>
                </div>
                <el-input
                  v-if="subTitle[index].show"
                  v-model="subTitle[index].title"
                  placeholder="请输入子标题"
                  @keyup.enter.native="subTitle[index].show = false"
                  @blur="setSubTitle(index)"
                />
              </div>
              <span class="btn-wrap" @click="addCode(index)">
                <svg-icon icon-class="audio" />添加朗读停顿
              </span>
            </div>
            <p v-if="item.duration" class="material-duration">
              <span v-if="!(subTitle[index].show&&showLength)">共计{{ item.length }}个汉字</span>
              朗读时长{{ (item.duration / 1000).toFixed(1) }}s
            </p>
          </div>

        </RichTextEdit>
      </div>
    </transition-group>
  </div>

</template>

<script>
import RichTextEdit from './RichTextEdit.vue'
import { mapState } from 'vuex'
import * as edit from '../js/set-duration'
import { setRedetDate } from '../../materialList/js/reset-edti'
export default {
  components: {
    RichTextEdit
  },
  props: {

  },
  data() {
    return {
      edtor_text_list: [{ text: '', duration: 0 }], // 文本列表
      subTitle: [],
      showLength: true
    }
  },
  computed: {
    ...mapState('jianshi', ['range', 'material_data', 'text_list', 'img_list', 'subTitle_list', 'dataList'])
  },
  watch: {
    // 重新编辑回显数据
    material_data: {
      handler(val, oldVal) {
        if (this.$route.query.reset_edti_id) {
          this.$store.commit('jianshi/SET_TEXT_LIST', setRedetDate(val).edtor_text_list)
          // 子标题回显，并存放到state
          this.$store.commit('jianshi/SET_SUBTITLEDATA', setRedetDate(val).subTitle)
          this.subTitle = JSON.parse(JSON.stringify(this.subTitle_list))
        }
      },
      immediate: true
    },
    text_list: {
      handler(val, oldVal) {
        if (val && val.length) this.init(val)
      },
      immediate: true
    },
    subTitle_list: {
      handler(val, oldVal) {
        if (val && val.length) this.subTitle = JSON.parse(JSON.stringify(this.subTitle_list))
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {
    this.showLength = document.body.clientWidth < 1330
    window.onresize = () => {
      this.showLength = document.body.clientWidth < 1330
      // 调用methods中的事件
      // _this.pageResize()
    }
  },
  methods: {
    init(val) {
      this.edtor_text_list = JSON.parse(JSON.stringify(val))
      if (!this.edtor_text_list.length) return
      this.edtor_text_list.forEach(item => {
        item.length = edit.getLength(item.text)
        item.duration = edit.getMs(item.text)
        item.text = item.text.replace(/#pau#/g, "<img class='code' src=''>")
      })
    },
    // 更新edtor_text_list，素材列表展示数据
    setEdtorTextList(index, text) {
      this.edtor_text_list[index].length = edit.getLength(text)
      this.edtor_text_list[index].duration = edit.getMs(text)
    },
    // edtor_text_list_temp临时存放innerText值，用来更改state=>text_list
    changeStateTextList(index) {
      const edtor_text_list_temp = JSON.parse(JSON.stringify(this.text_list))
      const innerHTML = this.$refs.richText[index].$refs.textareaText.innerHTML
      const innerText = innerHTML.replace(/<img[^>]*>/g, '#pau#')
        .replace(/<\/?(?!img\b)\w+\b[^<>]*>/g, '').replace(/&nbsp;/ig, ' ')
      this.$set(edtor_text_list_temp[index], 'text', innerText)
      this.$store.commit('jianshi/SET_TEXT_LIST', edtor_text_list_temp)
      this.setImgDuration(index, innerText)
    },
    // 更新素材时间
    setImgDuration(index, text) {
      if (this.text_list.length !== this.img_list.length) {
        this.resetImgDuration()
        return
      }
      const duration = edit.getMs(text)
      const img_list = JSON.parse(JSON.stringify(this.img_list))
      this.$set(img_list[index], 'duration', duration)
      this.$store.commit('jianshi/SET_IMG_LIST', img_list)
    },
    // 只有一条文本计算时长
    resetImgDuration() {
      const img_list = JSON.parse(JSON.stringify(this.img_list))
      const text = this.text_list[0].text
      const img_length = this.img_list.length
      const duration = edit.getMs(text)
      let img_duration = Math.floor(duration / img_length)
      const last_duration = duration - img_duration * (img_length - 1) + 10

      for (let index = 0; index < img_length; index++) {
        if (index === img_length - 1) {
          img_duration = last_duration
        }
        this.$set(img_list[index], 'duration', img_duration)
      }
      this.$store.commit('jianshi/SET_IMG_LIST', img_list)
    },
    // 失去焦点更改state=>subTitle_list
    setSubTitle(index) {
      this.subTitle[index].show = false
      this.$store.commit('jianshi/SET_SUBTITLEDATA', this.subTitle)
    },
    showSubTitle(index) {
      this.$set(this.subTitle[index], 'show', !this.subTitle[index].show)
    },
    // 添加朗读停顿
    addCode(index) {
      if (!this.range) return
      const img = document.createElement('img')
      img.className = 'code'
      img.src = ''
      this.range.insertNode(img)
      this.changeStateTextList(index)
    },
    // 输入文本计算时长
    inputText(index, text) {
      this.setEdtorTextList(index, text)
      this.setImgDuration(index, text)
    },
    // 回车换行
    keyupEnter(index, htmlArr) {
      // 去掉
      htmlArr.forEach((item, htmlIndex) => {
        htmlArr[htmlIndex] = item.replace(/<img[^>]*>/g, '#pau#')
          .replace(/<\/?(?!img\b)\w+\b[^<>]*>/g, '').replace(/&nbsp;/ig, ' ')
      })
      const innerHTML = this.$refs['richText'][index].$refs.textareaText.innerHTML
      this.$refs['richText'][index].$refs.textareaText.innerHTML = innerHTML.replace(/<\/?(?!img\b)\w+\b[^<>]*>/g, '')
      this.changeEnterText(index, htmlArr)
      this.changeEnterImg(index, htmlArr)
      this.subTitle.splice(index + 1, 0, { title: '', show: false })
      this.$store.commit('jianshi/SET_SUBTITLEDATA', this.subTitle)
    },

    // 回车之后更改state=>text_list
    changeEnterText(index, htmlArr) {
      if (htmlArr.length < 2) return
      const edtor_text_list_temp = JSON.parse(JSON.stringify(this.text_list))
      this.$set(edtor_text_list_temp[index], 'text', htmlArr[0])
      const obj = {
        text: htmlArr[1]
        // duration: edit.getMs(htmlArr[1])
      }
      edtor_text_list_temp.splice(index + 1, 0, obj)
      this.$store.commit('jianshi/SET_TEXT_LIST', edtor_text_list_temp)
    },
    // 回车之后更改state=>img_list
    changeEnterImg(index, htmlArr) {
      if (htmlArr.length < 2) return
      const img_list = JSON.parse(JSON.stringify(this.img_list))
      this.$set(img_list[index], 'duration', edit.getMs(htmlArr[0]))
      // 添加下一个素材
      const source_img_item = (this.dataList.image_list && this.dataList.image_list[index + 1]) || {}
      const img_item = {
        id: null,
        resource_url: source_img_item.proxy_url || '',
        type: source_img_item.proxy_url ? 'image' : '',
        source: source_img_item.proxy_url ? '原文图库' : '',
        imgError: img_list[index + 1] ? img_list[index + 1].imgError : false,
        duration: edit.getMs(htmlArr[1])
      }
      img_list.splice(index + 1, 0, img_item)
      img_list.length = this.text_list.length
      this.$store.commit('jianshi/SET_IMG_LIST', img_list)
    }
  }
}
</script>

<style lang="scss">
.material-text-wrap {
  flex : 1;

  .material-list {
    .material-item {
      background : #F7F8F9;
      padding : 20px;
      height : 192px;
      border-radius : 4px;
      margin-bottom : 20px;

      .material-btn {
        display : flex;
        justify-content : space-between;
        align-items : center;

        .btn-wrap {
          display : flex;
          align-items : center;
          font-size : 12px;
          line-height : 12px;
          color : #5675E8;
          cursor : pointer;

          .btn-item {
            margin-right : 40px;
            display : flex;
            align-items : center;
          }

          svg {
            width : 12px;
            height : 12px;
            margin-right : 10px;
          }

          .el-input {
            width : 216px;
            font-size : 12px;
            // transform: scale(.8);
            margin-left : 10px;
            height : 24px;
            border-radius : 4px;

            input {
              height : 24px;
              line-height : 24px;
              background : #FFFFFF;
              border : 1px solid #5675E8;
              padding : 0 10px;
            }
          }
        }

        .material-duration {
          font-weight : 400;
          font-size : 12px;
          color : #BABABA;
        }
      }

      &.auto-wrap {
        height : auto;

        .textareaText {
          height : auto;
        }
      }

      .textareaText {
        height : 115px;
        min-height : 115px;
        outline : none;
        overflow-y : scroll;
        word-break : break-all;
        color : #404040;
        font-size : 14px;
        margin-bottom : 20px;

        .code {
          display : inline-block;
          width : 57px;
          height : 15px;
          vertical-align : middle;
          margin : 0 10px;
          background : url('~@/assets/images/jianshi/tingdun.svg');
          background-repeat : no-repeat;
          background-size : 100% 100%;
        }

        & *:not(img) {
          background : none!important;
          color : #404040!important;
          font-size : 14px!important;
          font-family : Microsoft YaHei, Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Arial, sans-serif!important;
          font-style : normal!important;
          line-height : 20px!important;
          white-space : normal!important;
          font-weight : 400!important;
          letter-spacing : 0!important;
        }
      }
    }
  }
}

</style>
