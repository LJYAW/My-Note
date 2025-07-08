<!--
 * @Author: your name
 * @Date: 2021-02-23 11:38:19
 * @LastEditTime: 2021-03-04 15:51:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/EditTask.vue
-->
<template>
  <div class="nums-list">
    <div v-for="(item,index) in numData" :key="index" class="item-nums">
      <span>{{ item.label }}:</span>
      <div>{{ item.value }}</div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'NumsList',
  components: {},
  props: {
    numData: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  computed: {},
  watch: {},
  mounted() {},
  created() {},
  methods: {}
}
</script>

<style lang="scss" scoped>
.nums-list{
    display: flex;
    margin-top: 20px;
    .item-nums{
      width: 200px;
      display: flex;
      margin-left: 20px;
      font-weight: bold;
      font-size: 14px;
      span{
        color:#409eff ;
      }
      div{
        margin-left: 5px;
        color: red;
      }
    }
  }
</style>
