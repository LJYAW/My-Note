<!--
 * @Author: zzx
 * @Date: 2020-07-22 20:26:53
 * @LastEditTime: 2020-09-15 15:59:03
 * @FilePath: /zhijian_web/src/views/video_create/index.vue
-->
<template>
  <editPad />
</template>

<script>
import editPad from './edit_pad.vue'
export default {
  data() {
    return {}
  },
  components: { editPad },
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
.content {
  .el-input {
    height: 30px;
  }
  .search-pad {
    padding-bottom: 27px;
    border-bottom: 1px solid rgba(247, 247, 247, 1);
  }
  .video-title {
    padding-bottom: 27px;
    border-bottom: 1px solid rgba(247, 247, 247, 1);
  }
}
</style>
