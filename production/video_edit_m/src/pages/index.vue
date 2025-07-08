<!--
 * @Author: zzx
 * @Date: 2020-07-30 15:41:43
 * @LastEditTime: 2021-07-09 18:13:12
 * @FilePath: /video_edit/src/pages/index.vue
-->
<template>
  <div class="editBody">
    <meadia />

    <editMain />
  </div>
</template>

<script>
import editMain from './edit_main/index'
import meadia from './media/index'
export default {
  props: {},
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {},
  components: {
    editMain,
    meadia
  },
  created() {
  },
  mounted() {}
}
</script>

<style lang="scss">
@import '../assets/scss/index.scss';
</style>
