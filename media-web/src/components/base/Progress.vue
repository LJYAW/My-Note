<!--
 * @Author: your name
 * @Date: 2021-08-12 15:51:02
 * @LastEditTime: 2021-08-24 17:14:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/components/base/Progress.vue
-->
<template>
  <el-progress
    v-if="!isNaN(percentage)"
    :text-inside="textInside"
    :stroke-width="strokeWidth"
    :percentage="percentage"
    :show-text="showText"
    :format="format"
  />

</template>

<script>
export default {
  name: 'BaseProgress',
  components: {

  },
  props: {
    // 进度条显示文字内置在进度条内
    textInside: {
      type: Boolean,
      default() {
        return true
      }
    },
    // 进度条的宽度
    strokeWidth: {
      type: Number,
      default() {
        return 20
      }
    },
    percentage: {
      type: Number,
      default: 0
    },
    // 是否显示进度条文字内容
    showText: {
      type: Boolean,
      default() {
        return false
      }
    },
    progressText: {
      type: String,
      default() {
        return ''
      }
    }
  },
  data() {
    return {

    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    format(percentage) {
      return `${this.progressText} ${percentage}%`
    }
  }
}
</script>

<style scoped lang="scss">

</style>
