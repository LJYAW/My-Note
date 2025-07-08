<!--
 * @Author: your name
 * @Date: 2021-07-21 19:08:35
 * @LastEditTime: 2021-09-23 10:15:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoMaterial.vue
-->
<template>
  <div class="material-wrap">
    <MaterialForm @loading="setLoadingData" />
    <MaterialList v-loading="loading" />
  </div>
</template>

<script>
import MaterialForm from './materialForm/index.vue'
import MaterialList from './materialList/index.vue'
export default {
  components: {
    MaterialForm,
    MaterialList
  },
  props: {

  },
  data() {
    return {
      loading: false
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
    setLoadingData(val) {
      this.loading = val
    }
  }
}
</script>

<style scoped lang="scss">
.material-wrap {
  margin-top: 15px;
  padding: 20px;
  background: #fff;
  margin-bottom: 20px;
  border-radius: 4px;
  min-width: 1220px;
}
</style>
