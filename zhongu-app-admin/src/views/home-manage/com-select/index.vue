<!--
 * @Author: your name
 * @Date: 2021-10-20 15:32:35
 * @LastEditTime: 2021-11-04 18:13:12
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/com-select/index.vue
-->
<template>
  <div class="com-select-wrap">
    <BaseTitle class="base-title" title="组件管理" />

    <div class="com-list-wrap">

      <div v-for="(item,index) in comList" :key="index" class="com-list" @click="add(item.layout)">
        <ComIcon :layout="item.layout" />
        <div class="name">{{ item.name }}</div>
        <i class="drap">添加</i>
      </div>

    </div>

  </div>
</template>

<script>
import ComIcon from './com-icon.vue'
import { baseSingle } from '../base-com-data'
import { mapState } from 'vuex'

export default {
  components: {
    ComIcon
  },
  props: {

  },
  data() {
    return {
      comList: [
        {
          name: '1x4分类组件',
          layout: [1, 4]
        },
        {
          name: '1x3分类组件',
          layout: [1, 3]
        },
        {
          name: '1x2分类组件',
          layout: [1, 2]
        }
      ]
    }
  },
  computed: {
    ...mapState('bdapp', ['appData', 'activeComName'])
  },
  watch: {

  },
  created() {
  },
  mounted() {

  },
  methods: {
    add(item) {
      const total = item[1]
      const data = baseSingle(total)
      this.$store.commit('bdapp/ADD_SINGLE', data)

      // const appData = this.appData
      //   appData.Single.push()
      //   this.$store.commit('bdapp/SET_ZHONGGU_APP_DATA', data)
    }
  }
}
</script>

<style scoped lang="scss">
.com-select-wrap {
  border-radius: 4px;
  border: 1px solid #E9E9E9;
  padding: 20px;

  .base-title {
    margin-bottom: 25px;
  }

  .com-list-wrap {
    margin-top: 25px;
  }
}

.com-list {
  display: flex;
  align-items: center;
  padding: 8px 16px;
  border: 1px solid transparent;
  cursor: pointer;
  transition: all 0.25s;
  margin-bottom: 20px;
  user-select: none;

  &:hover {
    background: linear-gradient(270deg, #F9F9F9 0%, #FFFFFF 100%);
    box-shadow: 0px 10px 20px 0px rgba(0, 0, 0, 0.02), 0px 10px 20px -15px rgba(0, 0, 0, 0.4);
    border-radius: 4px;
    border: 1px solid #FFFFFF;
    // filter: blur(5px);
  }

  .name {
    margin-left: 10px;
    font-size: 18px;
    font-weight: 400;
    color: #404040;
  }
  .drap {
    cursor: pointer;
    margin-left: auto;
  }
}
</style>
