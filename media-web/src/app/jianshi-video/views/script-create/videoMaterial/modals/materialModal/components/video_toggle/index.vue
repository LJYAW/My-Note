<!--
 * @Author: zzx
 * @Date: 2020-07-24 15:01:39
 * @LastEditTime: 2021-08-30 16:03:47
 * @FilePath: /weijian_web/src/components/drop_down/src/drop_down.vue
-->
<template>
  <div class="toggle-wrap">
    <slot />
  </div>
</template>

<script>
export default {
  name: 'VideoToggle',
  componentName: 'videoToggle',
  components: {},
  props: {
    value: {
      type: [Array, String, Number],
      default() {
        return []
      }
    }
  },

  provide() {
    return {
      videoToggle: this
    }
  },

  data() {
    return {
      activeVideos: [].concat(this.value)
    }
  },
  computed: {},
  watch: {
    value(value) {
      this.activeVideos = [].concat(value)
    }
  },
  created() {
    this.$on('item-hover', this.handleItemHover)
  },
  mounted() {},
  methods: {
    setActiveVideos(activeVideos) {
      activeVideos = [].concat(activeVideos)
      const value = this.accordion ? activeVideos[0] : activeVideos
      this.activeVideos = activeVideos
      // this.$emit('input', value)
      // this.$emit('change', value)
    },
    handleItemHover(item) {
      this.setActiveVideos((this.activeVideos[0] || this.activeVideos[0] === 0) && this.activeVideos[0] === item.name ? '' : item.name)
    }
  }
}
</script>

<style scoped lang="scss">
</style>
