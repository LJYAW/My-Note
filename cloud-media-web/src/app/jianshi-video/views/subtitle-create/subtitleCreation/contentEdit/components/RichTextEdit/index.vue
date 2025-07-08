<!--
 * @Author: your name
 * @Date: 2021-07-23 14:26:18
 * @LastEditTime: 2021-10-08 16:43:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoMaterial/components/materialList/RichTextEdit.vue
-->
<template>
  <div>
    <div
      ref="textareaText"
      contenteditable="true"
      class="textareaText"
      placeholder="请输入字幕"
      @click="getRange()"
      @blur="setTextList()"
      @keyup.enter="keyupEnter()"
      @input="changeText()"
      v-html="innerText"
    />
    <slot />
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  components: {

  },
  props: {
    innerText: {
      type: String,
      default() {
        return ''
      }
    },
    index: {
      type: Number,
      default() {
        return 0
      }
    }
  },
  data() {
    return {
      innerTextData: ''
    }
  },
  computed: {
    ...mapState('jianshi', ['range'])
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    keyupEnter() {
      const innerHTML = this.$refs.textareaText.innerHTML
      let htmlArr = []
      if (innerHTML.indexOf('<div style') !== -1) {
        htmlArr = innerHTML.split('</div>')
      } else {
        htmlArr = innerHTML.split('<div>')
      }
      this.$emit('keyupEnter', htmlArr)
    },
    // 获取光标位置
    getRange() {
      const range = window.getSelection().getRangeAt(0)
      this.$store.commit('jianshi/SET_RANGE', range)
    },
    // 更新文本素材
    setTextList() {
      this.$emit('changeTextList')
    },
    getInnerHtml() {
      const innerHTML = this.$refs.textareaText.innerHTML
      this.innerTextData = innerHTML.replace(/<img[^>]*>/g, '#pau#')
        .replace(/<\/?(?!img\b)\w+\b[^<>]*>/g, '').replace(/&nbsp;/ig, ' ')
    },
    // 文本变化
    changeText() {
      this.getInnerHtml()
      this.$emit('inputText', this.innerTextData)
      this.getRange()
    }
  }
}
</script>

<style scoped lang="scss">
*[contenteditable="true"]:empty:before {
  content: attr(placeholder);
  color: #bababa;
}

.textareaText {

}
</style>
