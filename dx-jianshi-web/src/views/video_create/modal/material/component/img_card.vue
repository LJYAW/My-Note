<!--
 * @Author: zzx
 * @Date: 2020-09-02 18:23:01
 * @LastEditTime: 2020-09-02 18:24:15
 * @FilePath: /zhijian_web/src/views/video_create/modal/material/img_card.vue
-->
<template>
  <div class="img-card">
    <slot></slot>
    <label class="el-upload-list__item-status-label">
      <i class="iconfont iconqueding d-block"></i>
    </label>
  </div>
</template>

<script>
export default {
  name: 'sImgCard',
  props: {},
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
