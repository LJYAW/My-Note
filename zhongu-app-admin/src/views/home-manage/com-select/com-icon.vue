<!--
 * @Author: your name
 * @Date: 2021-10-20 15:56:04
 * @LastEditTime: 2021-10-20 16:13:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/com-select/com-icon.vue
-->
<template>
  <div class="com-icon">
    <div class="item">
      <div v-for="(a,j) in layout[0]" :key="j" class="block block-1" />
    </div>
    <div class="item">
      <div v-for="(i,k) in layout[1]" :key="k" class="block block-2" />
    </div>
  </div>
</template>

<script>
export default {
  props: {
    layout: {
      type: Array,
      default: () => {
        return [1, 3]
      }
    }
  }
}
</script>

<style scoped lang="scss">
.com-icon {
  width: 40px;
  height: 40px;
  display: flex;
  flex-direction: column;

  .item {

    display: flex;
    margin-bottom: 2px;
    width: 100%;
    height: 100%;

    .block {
      flex: 1;
      background-color: #C8C8C8;
      margin: 0 1px;
      border-radius: 2px;
    }
  }

}
</style>
