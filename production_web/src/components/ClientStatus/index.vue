<!--
 * @Author: your name
 * @Date: 2021-02-21 21:25:38
 * @LastEditTime: 2021-02-21 22:16:51
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/components/ClientStatus/index.vue
-->
<template>
  <div class="client-style">
    <p>客户推送状态：</p>
    <div v-for="(item,index) in list" :key="index">
      <span class="text" :style="item.style">{{ item.text }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ClientStatus',
  data() {
    return {
      list: [
        {
          // 待推送黄色；推送成功绿色；推送失败红色
          text: '推送成功',
          style: {
            color: '#67C23A'
          }
        },
        {
          text: '待推送',
          style: {
            color: '#E6A23C'
          }
        },
        {
          text: '推送失败',
          style: {
            color: '#F56C6C'
          }
        }
      ]
    }
  }
}
</script>

<style scoped lang="scss">
.client-style {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    margin-right: 30px;
    margin-bottom: 10px;
    .text {
        margin: 0 5px;
        font-weight: 600;
    }
}
</style>
