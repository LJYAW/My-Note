<!--
 * @Author: your name
 * @Date: 2021-01-14 14:34:46
 * @LastEditTime: 2021-03-31 18:38:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/components/Dialog/index.vue
-->
<template>
  <el-dialog
    :title="title"
    :visible.sync="SelfDialogVisible"
    :width="width"
    :height="height"
    :append-to-body="appendToBody"
    :center="center"
    @close="close"
  >
    <slot />
  </el-dialog>

</template>

<script>
export default {
  name: 'BaseDialog',
  components: {},
  props: {
    width: {
      type: String,
      default: '500px'
    },
    height: {
      type: String,
      default: '300px'
    },
    show: {
      type: Boolean,
      default: false
    },
    appendToBody: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    center: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      SelfDialogVisible: false
    }
  },
  computed: {},
  watch: {
    show(val) {
      this.SelfDialogVisible = val
    }
  },
  mounted() {
  },
  created() {},
  methods: {
    close() {
      this.$emit('close')
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
