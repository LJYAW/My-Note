<!--
 * @Author: zzx
 * @Date: 2020-10-16 11:43:23
 * @LastEditTime: 2020-11-10 11:42:12
 * @FilePath: /zhijian_web/src/views/active_video/create_video/index.vue
-->
<template>
  <div>
    <header-bar></header-bar>
    <edit-pad></edit-pad>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {},
  components: {
    HeaderBar: () => import('./edit_header/index.vue'),
    EditPad: () => import('./edit_pad/index.vue')
  },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
</style>
