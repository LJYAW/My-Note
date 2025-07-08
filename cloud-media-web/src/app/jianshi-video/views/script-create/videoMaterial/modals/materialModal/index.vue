<!--
 * @Author: your name
 * @Date: 2021-07-30 17:55:04
 * @LastEditTime: 2021-09-23 10:43:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoMaterial/modals/materialModal/index.vue
-->
<template>
  <div class="material-modal">
    <div class="material-m-wrap">
      <search-img @submit="submit" @showTypeHandler="showTypeHandler" />
    </div>
  </div>
</template>

<script>
import SearchImg from './pages/SearchImg.vue'
export default {
  name: 'ModalM',
  components: {
    SearchImg
  },
  data() {
    return {
      componentName: '',
      videoEditVisible: false,
      video_item: null,
      showType: ''
    }
  },
  provide() {
    return {
      modalM: this
    }
  },
  computed: {
  },
  watch: {},
  created() {},
  mounted() {},
  methods: {
    submit(obj) {
      this.$emit('submit', obj)
    },
    showTypeHandler(val) {
      this.showType = val
    }
  }
}
</script>

<style scoped lang="scss">
.material-modal {
  width: 100%;
  height: 600px;
  position: relative;
  display: flex;
  flex-direction: column;

  .material-m-wrap {
    display: flex;
    flex-direction: column;
    height: 100%;
  }

  .active {
    color: #5675e8;
  }
}
</style>

