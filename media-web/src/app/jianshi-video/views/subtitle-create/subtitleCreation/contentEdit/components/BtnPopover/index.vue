<!--
 * @Author: your name
 * @Date: 2021-09-22 18:03:45
 * @LastEditTime: 2021-09-22 20:59:56
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/subtitleCreation/contentEdit/components/BtnPopover/index.vue
-->
<template>
  <div class="btn-popover-wrap" :style="style">
    <slot />
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    position: {
      type: Object,
      default: () => {
        return {
          left: 0,
          top: 0
        }
      }
    }
  },
  data() {
    return {

    }
  },
  computed: {
    style() {
      const { left, top } = this.position
      return {
        left: left + 'px',
        top: top + 'px'
      }
    }
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
.btn-popover-wrap {
  position: fixed;
  z-index: 221;
}
</style>
