<!--
 * @Author: your name
 * @Date: 2021-01-22 16:42:30
 * @LastEditTime: 2021-01-22 16:51:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work_order_web/src/components/base/Tag.vue
-->
<template>
  <el-tag
    :closable="closable"
    :type="type"
    :size="size"
    v-bind="$attrs"
    v-on="$listeners"
  >
    <slot />
  </el-tag>
</template>

<script>
export default {
  name: 'BaseTag',
  components: {

  },
  props: {
    closable: {
      type: Boolean,
      default: false
    },
    type: {
      type: String,
      default: ''
    },
    size: {
      type: String,
      default: ''
    },
    effect: {
      type: String,
      default: 'light'
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

  }
}
</script>

<style scoped lang="scss">
.el-tag{
    margin-right: 10px;
}
</style>
