<template>
  <div class="home-wrap">
    <vheader></vheader>
    <vmain></vmain>
  </div>
</template>

<script>
import vmain from './vmain'
import vheader from './vheader'
export default {
  name: 'vhome',
  data() {
    return {}
  },
  components: {
    vmain,
    vheader,
  },
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {},
}
</script>

<style lang="scss" scoped>
.home-wrap {
  width: 100%;
  height: 100%;
}
</style>
