  <template>
  <div ref="wrapper">
    <slot></slot>
  </div>
</template>

<script>
import BScroll from 'better-scroll'

export default {
  name: 'scroll',
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
  mounted() {
    // 保证在DOM渲染完毕后初始化better-scroll
    this.dom = this.$refs.wrapper
    this.dom.addEventListener('scroll', this._initScroll, true)
  },
  methods: {
    _initScroll(e) {
      // 变量scrollTop是滚动条滚动时，距离顶部的距离
      if (!this.scrollX) {
        let scrollTop = e.target.scrollTop
        // 变量windowHeight是可视区的高度
        let windowHeight = e.target.clientHeight

        // 变量scrollHeight是滚动条的总高度
        let scrollHeight = e.target.scrollHeight

        // 滚动条到底部的条件
        if (scrollTop + windowHeight == scrollHeight) {
          // 写后台加载数据的函数
          this.$emit('scrollDown')
          console.log('滚动到底部了')
        }
      } else {
        let scrollLeft = e.target.scrollLeft
        let clientWidth = e.target.clientWidth
        let scrollWidth = e.target.scrollWidth

        // 滚动条到底部的条件
        if (scrollLeft + clientWidth == scrollWidth) {
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
  },
  watch: {}
}
</script>

<style scoped lang="scss">
</style>
