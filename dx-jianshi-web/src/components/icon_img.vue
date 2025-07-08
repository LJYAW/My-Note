<!--
 * @Author: zzx
 * @Date: 2020-10-16 15:00:09
 * @LastEditTime: 2020-10-16 17:29:34
 * @FilePath: /zhijian_web/src/components/icon_img.vue
-->
<template>
  <img :src="src" :width="width" :height="height" class="icon-img" />
</template>

<script>
export default {
  props: {
    src: {
      type: String,
      default: ''
    },
    width: {
      type: String,
      default: '18'
    },
    height: {
      type: String,
      default: '18'
    }
  },
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {},
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
</style>
