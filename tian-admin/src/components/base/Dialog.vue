<!--
 * @Author: your name
 * @Date: 2021-01-14 14:34:46
 * @LastEditTime: 2021-05-27 19:27:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/components/Dialog/index.vue
-->
<template>
  <el-dialog
    v-if="show"
    :title="title"
    :visible="true"
    :width="width"
    :height="height"
    :append-to-body="appendToBody"
    :close-on-click-modal="closeOnClickModal"
    :center="center"
    :show-close="showClose"
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
    },
    closeOnClickModal: {
      type: Boolean,
      default: true
    },
    showClose: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
    }
  },
  computed: {},
  watch: {

  },
  mounted() {},
  created() {},
  methods: {
    close() {
      this.$emit('update:show', false)
      this.$emit('close')
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep .el-dialog__header {
  padding-right: 50px;
}
</style>
