<template>
  <div class="footer">
    首页 Copyright © {{ nowyear }} 天脉视迅（北京）科技有限公司  京ICP备2021010041号-3
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      nowyear: new Date().getFullYear()
    }
  },
  computed: {
  },
  watch: {

  },
  beforeDestroy() {

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
@import "../css/index.scss";

.footer {
  height: 200px;
  background: #1d2129;
  display: flex;
  align-items: center;
  justify-content: center;
  color: rgba(255, 255, 255, .4);
}
</style>
