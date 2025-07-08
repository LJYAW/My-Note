<!--
 * @Author: your name
 * @Date: 2021-10-20 15:33:17
 * @LastEditTime: 2021-11-05 11:20:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/view-app-edit/index.vue
-->
<template>
  <div class="view-app-edit-wrap">
    <div class="edit-pad" :style="phoneStyle">
      <component
        :is="key"
        v-for="(value,key) in appData"
        :key="key"
        :class="{'hander-select': key !== 'Single', 'active': activeComName === key}"
        @click.native="selectCom(key)"
      />
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  components: {
    HeaderBanner: () => import('./pages/header-banner.vue'),
    BuiltBtns: () => import('./pages/built-btns.vue'),
    WeekUpdate: () => import('./pages/week-update.vue'),
    Single: () => import('./pages/single.vue')
  },
  props: {

  },
  data() {
    return {
      phoneStyle: {
        width: '375px',
        height: '812px'
      }
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
    selectCom(key) {
      this.$store.commit('bdapp/SET_ACTIVE_COM', key)
    }
  }
}
</script>

<style scoped lang="scss">

.edit-pad {
  background-color: #f8f8f8;
  padding: 107px 18px;
  overflow: hidden;
  overflow: auto;

  .single {
    margin-bottom: 20px;
  }

  .active {
    &.hander-select {
      &::after {
        opacity: 1;
      }
    }
  }

  .hander-select {
    position: relative;
    &::after {
      display: block;
      content: ' ';
      width: calc(100% + 12px);
      height: calc(100% + 12px);
      position: absolute;
      top: -6px;
      left: -6px;
      border: 1px solid #5675E8;
      border-radius: 4px;
      opacity: 0;
      transition: all 0.3s;
    }

    &:hover {
       &::after {
         opacity: 1;
       }
    }

  }
}
</style>
