<!--
 * @Author: your name
 * @Date: 2021-08-19 17:27:56
 * @LastEditTime: 2021-08-19 17:52:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/components/vueScroll/index.vue
-->
<template>
  <div ref="wrapper">
    <slot />
  </div>
</template>

<script>

export default {
  name: 'Scroll',
  props: {
    /**
     * 是否是 横向滚动
     */
    scrollX: {
      type: Boolean,
      default: () => {
        return false
      }
    }
  },
  data() {
    return {
      dom: null
    }
  },
  watch: {},
  mounted() {
    // 保证在DOM渲染完毕后初始化better-scroll
    this.dom = this.$refs.wrapper
    this.dom.addEventListener('scroll', this._initScroll, true)
    // this._initScroll()
  },
  methods: {
    _initScroll(e) {
      // 变量scrollTop是滚动条滚动时，距离顶部的距离
      if (!this.scrollX) {
        const scrollTop = e.target.scrollTop
        // 变量windowHeight是可视区的高度
        const windowHeight = e.target.clientHeight

        // 变量scrollHeight是滚动条的总高度
        const scrollHeight = e.target.scrollHeight

        // 滚动条到底部的条件
        if (scrollTop + windowHeight === scrollHeight) {
          // 写后台加载数据的函数
          this.$emit('scrollDown')
          console.log('滚动到底部了')
        }
      } else {
        const scrollLeft = e.target.scrollLeft
        const clientWidth = e.target.clientWidth
        const scrollWidth = e.target.scrollWidth

        // 滚动条到底部的条件
        if (scrollLeft + clientWidth === scrollWidth) {
          // 写后台加载数据的函数
          this.$emit('scrollXDown')
          console.log('滚动到底部了')
        }
      }
    },
    scrollTop() {
      this.$refs.wrapper.scrollTop = 0
      console.log('滚动到顶部了')
    }
  }
}
</script>

<style scoped lang="scss">
</style>
