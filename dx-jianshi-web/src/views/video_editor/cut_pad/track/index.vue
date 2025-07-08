<template>
  <div class="track-wrap">

    <!-- <textTrack /> -->

    <cutTrack />

    <!-- <imageTrack /> -->

  </div>
</template>

<script>
import cutTrack from './cut_track'
import textTrack from './text_track'
import imageTrack from './image_track'

export default {
  props: [],
  data() {
    return {}
  },
  components: {
    cutTrack,
    textTrack,
    imageTrack
  },

  computed: {},

  watch: {},

  methods: {},

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss">
</style>
