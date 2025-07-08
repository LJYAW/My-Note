<template>

  <div class="cut-pad-time-line">

    <headerSidebar />

    <div class="cut-pad-content-wrap d-flex h-100">

      <rightSidebar />

      <timeLine ref="timeLine" />
    </div>

  </div>
</template>

<script>
import timeLine from './time_line/index'
import rightSidebar from './sidebar/right_sidebar'
import headerSidebar from './sidebar/header_sidebar'

export default {
  props: [],
  data() {
    return {}
  },
  components: {
    timeLine,
    rightSidebar,
    headerSidebar
  },

  computed: {
    // clip_list() {
    //   return this.$store.state.videoM.cut_track_list
    // }
  },

  watch: {},

  methods: {},

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss">
.cut-pad-time-line {
  height: 100%;
  background-color: #f0f0f0;
}
</style>
