<!--
 * @Author: zzx
 * @Date: 2020-05-26 10:45:05
 * @LastEditTime: 2020-11-18 16:28:46
 * @FilePath: /zhijian_web/src/components/model.vue
-->
<template>
  <div class="modal" :id="id">
    <div class="modal-wrap">
      <div class="modal-content">
        <div class="modal-header" v-if="show_title">
          <slot name="title"></slot>
          <i @click="close()" class="iconfont iconclose hove-c fz-18px ml-auto cp"></i>
        </div>
        <div class="modal-body">
          <slot name="body"></slot>
        </div>
        <div class="modal-footer">
          <slot name="foot"></slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  // props: ['id', 'show_title'],
  props: {
    id: {
      type: String,
      default() {
        return 'modal'
      }
    },
    show_title: {
      type: Boolean,
      default() {
        return true
      }
    }
  },
  data() {
    return {}
  },
  components: {},

  computed: {},

  watch: {},

  methods: {
    close() {
      this.$store.commit('modalHidden', this.id)
      this.$emit('close')
    }
  },

  created() {},

  mounted() {
    document.addEventListener('keyup', e => {
      if (e.keyCode == 27) {
        if (this.id) {
          this.close() // 事件名
        }
      }
    })
  },
  beforeDestroy() {
    // this.$store.commit('modalHidden', this.id)
    document.body.classList.remove('model-open')
  }
}
</script>

<style scoped lang="scss"></style>
