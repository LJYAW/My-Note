<!--
 * @Author: zzx
 * @Date: 2020-07-22 20:26:52
 * @LastEditTime: 2020-12-08 11:56:40
 * @FilePath: /zhijian_web/src/views/vhome.vue
-->
<template>
  <div>
    <Vheader />
    <router-view />
  </div>
</template>

<script>
import Vheader from './vheader'
import Vmain from './main'
export default {
  components: { Vheader, Vmain },
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped></style>
