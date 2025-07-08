<!--
 * @Author: zll
 * @Date: 2020-12-07 15:56:20
 * @LastEditTime: 2020-12-15 15:51:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/ material_library/index.vue
-->
<template>
  <div class="meaterial_wrap">
    <!-- <banner /> -->
    <contant />
  </div>
</template>

<script>
import banner from './banner'
import contant from './contant'
export default {
  props: {},
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {},
  components: {
    banner,
    contant
  },
  mounted() {},
  created() {}
}
</script>

<style lang='scss' scoped>
@media screen and (max-width: 768px) {
  .meaterial_wrap {
    max-width: 1000px;
    min-width: 1000px;
  }
}
@media screen and (max-width: 1200px) and (min-width: 768px) {
  .meaterial_wrap {
    max-width: 1200px;
    min-width: 1200px;
  }
}
</style>
