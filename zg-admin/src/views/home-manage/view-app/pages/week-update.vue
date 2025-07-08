<!--
 * @Author: your name
 * @Date: 2021-10-20 18:03:49
 * @LastEditTime: 2021-11-05 17:55:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/view-app-edit/components/week-update.vue
-->
<template>
  <div class="weekly-update">
    <Vtitle :title="weekUpdateData.title" :sub-title="weekUpdateData.subTitle" />

    <div class="com-list">

      <div v-for="(item,index) in weekUpdateData.list" :key="index" class="item">
        <BuyCard :base-data="item.group" />
      </div>

    </div>

    <div class="class-wrap">
      <div v-for="(item,j) in weekUpdateData.classification" :key="j" class="classification">
        <div class="class-card">
          <base-image style="width: 60px; height: 60px" :src="item.calssData.small_img" />
        </div>
      </div>

      <div class="class-card">
        <div class="all-btns">
          <span class="all">All</span>
          <span class="text">全部分类</span>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'WeekUpdate',
  components: {
    Vtitle: () => import('../../components/v-title.vue'),
    BuyCard: () => import('../../components/buy-card.vue')
  },
  props: {
  },
  data() {
    return {

    }
  },
  computed: {
    ...mapState('bdapp', ['upCom']),
    weekUpdateData() {
      return this.$store.state.bdapp.appData.WeekUpdate
    }
  },
  watch: {
    upCom() {
      this.$forceUpdate()
    }
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

.weekly-update {
  margin-top : 20px;
  .title-wrap {
    margin-bottom : 15px;
  }

  .com-list {
    display : flex;
    // width:300px;
    overflow : hidden;
    overflow-x : auto;
    position : relative;
    z-index : 9;

    .item {
      margin-right : 8px;
      min-width : 110px;
      max-width : 110px;
      height : 182px;
    }
  }

  .class-wrap {
    display : flex;
    margin-top : 20px;
    margin-bottom : 30px;
    overflow : hidden;
    overflow-x : auto;

    .class-card {
      min-width : 78px;
      height : 75px;
      background : linear-gradient(270deg, #F9F9F9 0%, #FFFFFF 100%);
      box-shadow : 0px 10px 20px 0px rgba(0, 0, 0, .02), 0px 10px 20px -15px rgba(0, 0, 0, .1);
      border-radius : 18px;
      margin-right : 9px;
      overflow : hidden;

      display: flex;
      justify-content: center;
      align-items: center;
    }
  }

  .all-btns {
    display : flex;
    flex-direction : column;
    justify-content : center;
    width : 100%;
    height : 100%;
    margin-left : 11px;

    .all {
      font-size : 12px;
      font-weight : 400;
      margin-bottom : 2px;
    }
  }
}

</style>
