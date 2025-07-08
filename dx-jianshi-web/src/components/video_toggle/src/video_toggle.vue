<!--
 * @Author: zzx
 * @Date: 2020-07-24 15:01:39
 * @LastEditTime: 2020-12-30 10:40:00
 * @FilePath: /weijian_web/src/components/drop_down/src/drop_down.vue
-->
<template>
  <div class="toggle-wrap">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'videoToggle',
  componentName: 'videoToggle',
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
  methods: {
    setActiveVideos(activeVideos) {
      activeVideos = [].concat(activeVideos)
      let value = this.accordion ? activeVideos[0] : activeVideos
      this.activeVideos = activeVideos
      // this.$emit('input', value)
      // this.$emit('change', value)
    },
    handleItemHover(item) {
      this.setActiveVideos((this.activeVideos[0] || this.activeVideos[0] === 0) && this.activeVideos[0] === item.name ? '' : item.name)
    }
  },
  components: {},
  created() {
    this.$on('item-hover', this.handleItemHover)
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
</style>
