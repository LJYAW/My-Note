<!--
 * @Author: your name
 * @Date: 2021-07-23 11:22:48
 * @LastEditTime: 2021-07-26 18:33:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoMaterial/components/MaterialList.vue
-->
<template>
  <div class="material-main">
    <!-- 素材列表 -->
    <div class="material-content">
      <!-- 视频文本列表 -->
      <MaterialText />
      <!-- 视频素材列表 -->
      <MaterialImg />
    </div>
    <!-- 添加素材 -->
    <AddMaterial />
  </div>
</template>

<script>
import MaterialText from './materialText/index.vue'
import MaterialImg from './materialImg/index.vue'
import AddMaterial from './addMaterial/index.vue'
export default {
  components: {
    MaterialText,
    MaterialImg,
    AddMaterial
  },
  props: {

  },
  data() {
    return {
    }
  },
  computed: {

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
.material-main {

  .material-content {
    display: flex;
  }
}
</style>
