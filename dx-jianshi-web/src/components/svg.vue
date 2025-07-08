<!--
 * @Author: zzx
 * @Date: 2020-09-29 11:12:36
 * @LastEditTime: 2020-09-29 11:44:30
 * @FilePath: /zhijian_web/src/components/svg.vue
-->
<template>
  <svg class="icon" aria-hidden="true">
    <use :xlink:href="`#${icon}`" />
  </svg>
</template>

<script>
export default {
  name: 'vsvg',
  props: {
    icon: {
      type: String,
      default: ''
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
.icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
</style>
