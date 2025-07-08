<!--
 * @Author: your name
 * @Date: 2020-12-30 11:52:38
 * @LastEditTime: 2020-12-30 11:52:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/material_library/recyc_scroll/index.vue
-->
<template>
  <RecycleScroller
    class="scroller"
    :items="list"
    :item-size="32"
    key-field="id"
    v-slot="{ item }">
    <div class="user">
      {{ item.name }}
    </div>
  </RecycleScroller>
</template>
 
<script>
export default {
  props: {
    list: Array
  }
}
</script> 
 
<style scoped>
.scroller {
  height: 100%;
}
</style> 