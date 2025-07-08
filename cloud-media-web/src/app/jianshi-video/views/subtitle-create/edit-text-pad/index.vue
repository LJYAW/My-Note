<!--
 * @Author: your name
 * @Date: 2021-10-08 14:57:56
 * @LastEditTime: 2021-10-08 15:10:55
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/edit-text-pad/index.vue
-->
<template>
  <div class="edit-text-pad">

    <RightPad />

    <LeftPad />

  </div>
</template>

<script>
import RightPad from './right-pad.vue'
import LeftPad from './left-pad.vue'
export default {
  components: {
    RightPad,
    LeftPad
  },
  props: {

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
.edit-text-pad {
  width: 100%;
  max-width: 1440px;
  min-height: 350px;
}
</style>
