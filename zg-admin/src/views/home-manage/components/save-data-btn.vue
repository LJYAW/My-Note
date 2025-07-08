<!--
 * @Author: your name
 * @Date: 2021-11-02 14:22:34
 * @LastEditTime: 2021-11-02 14:24:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/components/save-data-btn.vue
-->
<template>
  <div class="save-btn">
    <el-button type="primary" size="mini" @click="saveBtn">保存</el-button>
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
    saveBtn() {
      this.$store.dispatch('bdapp/PostZhongguAppData')
    }
  }
}
</script>

<style scoped lang="scss">

</style>
