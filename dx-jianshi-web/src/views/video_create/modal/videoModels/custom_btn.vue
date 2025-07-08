<!--
 * @Author: zzx
 * @Date: 2020-09-15 10:52:44
 * @LastEditTime: 2020-09-15 11:02:42
 * @FilePath: /zhijian_web/src/views/video_create/modal/videoModels/custom_btn.vue
-->
<template>
  <div :class="['custom-btn', size]">
    <span>{{ text }}</span>
    <img v-if="img" class="btn-img" :src="img" />
    <slot></slot>
  </div>
</template>
<script>
export default {
  name: 'cBtn',
  props: {
    text: {
      type: String,
      default: ''
    },
    img: {
      type: String,
      default: ''
    },
    size: {
      type: String,
      default: ''
    }
  },
  data() {
    return {}
  },
  components: {},

  computed: {},

  watch: {},

  methods: {},

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss">
.custom-btn {
  width: 120px;
  height: 36px;
  background: #c51a1a;
  color: #ffffff;
  text-align: center;
  line-height: 36px;
  font-size: 12px;
  &.mini {
    height: 26px;
    line-height: 26px;
  }
}
</style>
