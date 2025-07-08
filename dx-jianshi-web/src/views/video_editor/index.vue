<template>
  <div class="video-editor-container" @click="handerl()">
    <!-- <editorHeader /> -->

    <!-- <div class="cut-header-wrapper"> -->
    <!-- <cut-header /> -->
    <!-- </div> -->

    <div class="cut-content-wrapper">
      <cut-content />
    </div>

    <div class="cut-pad-wrapper">
      <cut-pad />
    </div>
  </div>
</template>

<script>
// import cutHeader from './toolbar/main.vue'
// import editorHeader from './editor_header/index.vue'
import cutContent from './content/main.vue'
import cutPad from './cut_pad/main.vue'

export default {
  props: [],
  data() {
    return {}
  },
  components: {
    // cutHeader,
    cutContent,
    cutPad
    // editorHeader
  },

  computed: {},

  watch: {},

  methods: {
    handerl() {
      this.$store.commit('setActiveData', {
        type: '',
        index: -1
      })
    }
  },

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss">
.video-editor-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  .cut-pad-wrapper {
    flex: 1;
    margin: 0 30px;
    background-color: #f0f0f0;
  }
}
</style>
