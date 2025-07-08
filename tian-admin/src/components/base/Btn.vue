<!--
 * @Author: your name
 * @Date: 2021-01-15 14:31:14
 * @LastEditTime: 2021-02-21 21:15:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work_order_web/src/components/base/btn.vue
-->
<template>
  <el-button
    :type="type"
    :size="size"
    :round="round"
    :disabled="disabled"
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
    disabled: {
      type: Boolean,
      default: false
    },
    type: {
      type: String,
      default: 'primary'
    },
    round: {
      type: Boolean,
      default: false
    },
    size: {
      type: String,
      default: 'small'
    }
  }
}
</script>

<style scoped lang="scss">
.el-button{
  min-width: 70px;
  &.el-button--text{
    min-width: 40px;
  }
}
</style>
