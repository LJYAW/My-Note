<!--
 * @Author: zzx
 * @Date: 2020-07-24 17:14:11
 * @LastEditTime: 2020-12-30 11:15:24
 * @FilePath: /weijian_web/src/components/drop_down/src/drop_down_item.vue
-->
<template>
  <div class="toggle-content" :class="{'is-active': isActive}">
    <slot name="progress" />
    <div class="toggle-item">
      <div v-if="!isActive" class="h-100" @mouseenter="handleHover">
        <slot name="item" />
      </div>

      <transition name="fade">
        <div v-if="isActive" @mouseleave.self="handleHover(null)">
          <slot name="toggle-item" />
        </div>
      </transition>
    </div>
    <slot name="toggle-body" />
  </div>
</template>

<script>
export default {
  name: 'VideoItem',
  componentName: 'videoItem',
  inject: ['videoToggle'],
  components: {},
  props: {
    // active_item: Object,
    name: {
      type: [String, Number],
      default() {
        return this._uid
      }
    }
  },
  data() {
    return {}
  },
  computed: {
    isActive() {
      return this.videoToggle.activeVideos.indexOf(this.name) > -1
    }
  },
  watch: {},
  created() {},
  mounted() {},
  methods: {
    dispatch(componentName, eventName, params) {
      var parent = this.$parent || this.$root
      var name = parent.$options.componentName

      while (parent && (!name || name !== componentName)) {
        parent = parent.$parent

        if (parent) {
          name = parent.$options.componentName
        }
      }
      if (parent) {
        parent.$emit.apply(parent, [eventName].concat(params))
      }
    },
    handleHover(val) {
      this.dispatch('videoToggle', 'item-hover', this)
    }
  }
}
</script>

<style scoped lang="scss">
</style>
