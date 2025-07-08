<!--
 * @Author: your name
 * @Date: 2021-10-20 15:34:05
 * @LastEditTime: 2021-10-26 19:53:19
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/app-details/index.vue
-->
<template>
  <div class="app-details-wrap">
    <component :is="comActive" class="hander-select" />

    <ComDialog />
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  components: {
    ComDialog: () => import('../components/com-list-dialog.vue'),
    EditHeaderBanner: () => import('./pages/edit-header-swiper.vue'),
    EditWeekUpdate: () => import('./pages/edit-week-update.vue'),
    EditSingle: () => import('./pages/edit-single.vue'),
    EditBuiltBtns: () => import('./pages/edit-built-btns.vue')
  },
  props: {

  },
  data() {
    return {
    }
  },
  computed: {
    ...mapState('bdapp', ['appData', 'activeComName']),

    comActive() {
      const keyMap = {
        HeaderBanner: 'EditHeaderBanner',
        BuiltBtns: 'EditBuiltBtns',
        WeekUpdate: 'EditWeekUpdate',
        Single: 'EditSingle'
      }

      return keyMap[this.activeComName]
    }
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
.app-details-wrap {
  flex: 1;
  border-radius: 4px;
  border: 1px solid #E9E9E9;
}
</style>
