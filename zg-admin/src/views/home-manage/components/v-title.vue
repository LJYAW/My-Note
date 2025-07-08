<!--
 * @Author: your name
 * @Date: 2021-10-21 11:54:13
 * @LastEditTime: 2021-11-05 11:26:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/components/v-title.vue
-->
<template>
  <div class="title-wrap">
    <h5 class="title">{{ title }}</h5>
    <span class="sub-title">{{ subTitle }}</span>
    <div class="btn" @click="more">more</div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    title: {
      type: String,
      default: '请传入 title'
    },
    subTitle: {
      type: String,
      default: '请传入 subnTitle'
    },
    btnLink: {
      type: String,
      default: ''
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
    more() {
      console.log(this.btnLink)
    }
  }
}
</script>

<style scoped lang="scss">
.title-wrap {
  display: flex;
  align-items: center;
  margin-bottom: 15px;

  .title {
    font-size: 18px;
    font-weight: 600;
    color: #2F3A48;
  }

  .sub-title {
    font-size: 12px;
    font-weight: 400;
    color: #ABBDD2;
    margin-left: 5px;
    align-self: flex-end;
    overflow:hidden;
    text-overflow:ellipsis;
    white-space:nowrap;
    margin-right: 20px;
  }

  .btn {
    margin-left: auto;
    width: 41px;
    height: 18px;
    border-radius: 9px;
    border: 1px solid #ABBDD2;
    text-align: center;
    color: #ABBDD2;
  }
}
</style>
