<!--
 * @Author: zzx
 * @Date: 2020-09-18 20:07:38
 * @LastEditTime: 2021-06-22 16:28:11
 * @FilePath: /video_edit/src/pages/edit_main/tracks/decorate-track/decorate_group.vue
-->
<template>
  <div class="decorate-tracks">
    <div v-for="(item,index) in decorate_list" :key="index" class="track">
      <decorate-track :data="item"></decorate-track>
    </div>
  </div>
</template>

<script>
import decorateTrack from './decorate_tracks'
export default {
  name: 'decorateGroup',
  props: {},
  data() {
    return {}
  },
  computed: {
    decorate_list() {
      return this.$store.state.decorate_list
    }
  },
  watch: {},
  methods: {},
  components: {
    decorateTrack
  },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.decorate-tracks {
  margin-bottom: 100px;
}
</style>
