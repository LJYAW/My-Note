<!--
 * @Author: your name
 * @Date: 2020-11-17 14:57:39
 * @LastEditTime: 2020-12-22 17:25:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_admin/src/components/mavon_editor/index.vue
-->
<template>
  <div id="editorElem" style="text-align:left;width:100%;z-index:100"></div>
</template>

<script>
import wangEditor from 'wangeditor'

export default {
  props: {
    defaultHtml: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      editor: null,
      editorData: ''
    }
  },
  computed: {},
  watch: {},
  components: {},
  created() {},
  mounted() {
    const editor = new wangEditor(`#editorElem`)

    // 配置 onchange 回调函数，将数据同步到 vue 中
    editor.config.onchange = newHtml => {
      this.editorData = newHtml
    }

    editor.config.menus = [
      'head',
      'bold',
      'fontSize',
      'fontName',
      'italic',
      'underline',
      'strikeThrough',
      'indent',
      'lineHeight',
      'foreColor',
      'backColor',
      'link',
      'list',
      'justify',
      'quote',
      'emoticon',
      'undo',
      'redo'
    ]
    editor.config.showFullScreen = false
    this.$nextTick(() => {
      // 创建编辑器
      editor.create()
      editor.txt.html(this.defaultHtml)
      this.editor = editor
    })
  },
  methods: {
    getEditorData() {
      // 通过代码获取编辑器内容
      let data = this.editor.txt.html()
      alert(data)
    }
  },
  beforeDestroy() {
    // 调用销毁 API 对当前编辑器实例进行销毁
    this.editor.destroy()
    this.editor = null
  }
}
</script>

<style scoped lang="scss">
</style>
