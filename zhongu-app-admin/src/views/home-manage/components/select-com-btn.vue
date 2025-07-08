<!--
 * @Author: your name
 * @Date: 2021-10-26 18:39:17
 * @LastEditTime: 2021-11-01 21:06:13
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/components/select-com-btn.vue
-->
<template>
  <div class="select-com-btn">
    <el-button type="text" @click="selectCom">选择单品</el-button>
  </div>
</template>

<script>
/**
 * @function getCategoryData
 */
export default {
  name: 'SelectBtn',
  components: {
  },
  props: {
    dataIndex: {
      type: Number,
      default: -1
    }
  },
  data() {
    return {

    }
  },
  computed: {
    showComDialog() {
      return this.$store.state.bdapp.showComDialog
    }
  },
  watch: {
  },
  methods: {
    selectCom() {
      this.$store.commit('bdapp/SHOW_COM_DIALOG', true)
    }
  }
}
</script>

<style scoped lang="scss">
</style>
