<!--
 * @Author: your name
 * @Date: 2021-01-15 14:31:14
 * @LastEditTime: 2021-01-15 22:01:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work_order_web/src/components/base/btn.vue
-->
<template>
  <el-button
    :type="type"
    :size="size"
    :round="round"
    :style="`min-width: ${minWidth}px;`"
    v-bind="$attrs"
    v-on="$listeners"
  >
    <slot />
  </el-button>

</template>

<script>
export default {
  name: 'BaseBtn',

  props: {
    type: {
      type: String,
      default: 'primary'
    },
    round: {
      type: Boolean,
      default: true
    },
    size: {
      type: String,
      default: 'small'
    },
    minWidth: {
      type: Number,
      default: 70
    }
  }
}
</script>

<style scoped lang="scss">

</style>
