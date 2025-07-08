<!--
 * @Author: your name
 * @Date: 2021-04-13 17:57:53
 * @LastEditTime: 2021-05-20 10:40:35
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/business/timeDetailsM.vue
-->
<template>
  <transition name="fade-in-linear">
    <div v-if="showDetails" class="time-details-m" @click.stop="handleShow">
      <div ref="content" class="content" :style="positionStyle">
        <slot />
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: 'TimeDetailsM',
  components: {

  },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    position: {
      type: Object,
      default: () => {
        return {
          top: '50%',
          left: '50%'
        }
      }
    }
  },
  data() {
    return {
      showDetails: false,
      positionStyle: {

      }
    }
  },
  computed: {

  },
  watch: {
    show: {
      handler(val) {
        this.showDetails = val
      },
      immediate: true
    },
    position: {
      deep: true,
      handler(obj) {
        if (!this.show) return
        this.$nextTick(() => {
          const dom = this.$refs.content
          const windowWidth = document.body.clientWidth
          const height = dom.clientHeight
          const width = dom.clientWidth
          const maxLeft = windowWidth - parseInt(this.position.left)

          const y = parseInt(this.position.top) - height / 2 - 15
          let x = parseInt(this.position.left)
          const left = Math.min(x, parseInt(this.position.left))

          const manWidth = document.querySelector('.main-container').offsetLeft
          const manWClineWidth = document.querySelector('.main-container').clientWidth
          const totalWidth = (manWidth + manWClineWidth)
          const domWidth = (width + x)
          if (x < (width / 2 + manWidth)) {
            x = x + width / 2
          }
          if (domWidth > totalWidth) {
            x = x - ((domWidth - totalWidth) / 2 - 20)
          }
          this.positionStyle = {
            left: x + 'px',
            top: y + 'px'
          }
        })
      },
      immediate: true
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {
    handleShow() {
      this.$emit('update:show', false) // 触发 input 事件，并传入新值
      this.$emit('close') // 触发 input 事件，并传入新值
    }
  }
}
</script>

<style scoped lang="scss">
.time-details-m {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  right: 0;
  z-index: 300;
  .content {
    width: 600px !important;
    height: 320px !important;
    overflow-y: scroll;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    z-index: 2021;
    background: #fff;
    padding: 10px;
    border-radius: 5px;
    box-shadow: 0 2px 12px 0 rgb(0 0 0 / 20%);
    ::v-deep table{
      tr{
         td{
            word-break: break-all !important;
          }
      }
    }
  }
}
.fade-in-linear-enter-active,
.fade-in-linear-leave-active {
  transition: opacity 200ms linear;
}
.fade-in-linear-enter,
.fade-in-linear-leave,
.fade-in-linear-leave-active {
  opacity: 0;
}
</style>
