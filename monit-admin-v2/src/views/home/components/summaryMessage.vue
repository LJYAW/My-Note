<!--
 * @Author: your name
 * @Date: 2021-07-02 17:05:36
 * @LastEditTime: 2021-07-05 15:12:09
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/home/components/summaryMessage.vue
-->
<template>
  <div class="summary-message">
    <div class="card">
      <div v-for="(item,index) in cardList" :key="index" class="item-list">
        <div class="num-list">
          <div class="title">{{ item.title }}</div>
          <div class="num">{{ item.num }}</div>
        </div>
        <div class="message">
          <div class="h1">信息概述</div>
          <ul>
            <li v-for="(label,i) in item.totalList" :key="i">
              <div class="total-list">
                <div class="border-div" :style="{'background':label.color}" />
                <div class="name">{{ label.name }}</div>
              </div>
              <div class="total-num">{{ label.num }}</div>
            </li>
          </ul>
        </div>
      </div>
    </div>
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
      cardList: [
        {
          title: '当前用户总数',
          num: '700,991',
          totalList: [
            {
              name: '运行用户总数',
              num: 301,
              color: 'rgba(75, 205, 211, 1)'
            },
            {
              name: '停用用户总数',
              num: 1039,
              color: 'rgba(252, 68, 68, 1)'
            },
            {
              name: '试用用户总数',
              num: 703934,
              color: 'rgba(211, 155, 75, 1)'
            }
          ]
        },
        {
          title: '当前频道总数',
          num: '106',
          totalList: [
            {
              name: '当前监测频道总数',
              num: 117,
              color: 'rgba(75, 205, 211, 1)'
            },
            {
              name: '当前拆条频道总数',
              num: 98,
              color: 'rgba(252, 68, 68, 1)'
            },
            {
              name: '本月增加频道总数',
              num: 703934,
              color: 'rgba(211, 155, 75, 1)'
            }
          ]
        },
        {
          title: '当前任务总数',
          num: '789,033',
          totalList: [
            {
              name: '当前正在运作的任务',
              num: 39824,
              color: 'rgba(75, 205, 211, 1)'
            },
            {
              name: '历史任务数',
              num: 28043,
              color: 'rgba(252, 68, 68, 1)'
            }
          ]
        }
      ]
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

</style>
