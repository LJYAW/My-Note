<!--
 * @Author: your name
 * @Date: 2021-10-29 16:09:11
 * @LastEditTime: 2021-11-21 18:25:45
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/components/group-info.vue
-->
<template>
  <div class="group-info">
    <div class="list">
      <span v-if="calssData && calssData.name">{{ calssData.name }} |</span>
      <div v-if="group && group.name" class="group">
        <span class="name">{{ group.name }} </span> |
        <span>ID: {{ group.category_id }} | </span>
        <span>库存: {{ group.inventory }} | </span>
        <span>售价： {{ group.price }}</span>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    calssData: {
      type: Object,
      default: () => {}
    },
    group: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {

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
.group-info {
  display : flex;
  flex-wrap : wrap;

  .group {
    display : flex;
  }

  .list {
    display : flex;
    flex-wrap : wrap;
    align-items : center;
    overflow : hidden;
    text-overflow : ellipsis;
    white-space : nowrap;
    height : 40px;

    span {
      margin : 0 6px;
    }
    .name {
      color : #5675E8;
      font-weight : 500;
    }
  }
}

</style>
