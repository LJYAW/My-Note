<!--
 * @Author: your name
 * @Date: 2020-12-13 13:11:17
 * @LastEditTime: 2020-12-16 11:55:08
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /medical_cms_web/src/page/works/index.vue
-->
<template>
  <div class="wroks_wrap">
    <eacharts></eacharts>
    <tables></tables>
  </div>
</template>
<script>
import eacharts from './wokrs_componets/eacharts'
import tables from './wokrs_componets/tables'
export default {
  name: 'works',
  data() {
    return {
    }
  },
  components: {
    eacharts,
    tables,
  },
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {},
}
</script>

<style lang="scss" scoped>
.wroks_wrap {
  width: 100%;
  height: 100%;
  padding: 15px;
  box-sizing: border-box;
  .router_list {
    display: flex;
    a {
      display: inline-block;
      position: relative;
      margin-left: 36px;
      font-size: 16px;
      // min-width: 105px;
      // text-align: center;
    }
    .router-link-active {
      font-weight: bold;
    }
    .router-link-active::after {
      /* 下划线伪元素 */
      display: block;
      content: '';
      /* 控制下划线动画起始位置 */
      position: absolute;
      left: 0;
      right: 0;
      margin: 0 auto;
      padding-bottom: 6px;
      /* 下划线样式 */
      width: auto;
      border-bottom: 3px solid #2a79df;
      transition: width 0.6s cubic-bezier(0, 0.83, 0.72, 1.03);
    }
  }
  .content {
    margin-top: 20px;
  }
}
</style>
