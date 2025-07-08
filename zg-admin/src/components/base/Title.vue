<!--
 * @Author: your name
 * @Date: 2021-10-20 15:13:29
 * @LastEditTime: 2021-10-20 15:31:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/components/base/Title.vue
-->
<template>
  <div class="title">
    {{ title }}
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
.title {
  font-size: 18px;
  position: relative;
  padding-left: 10px;
  line-height: 1;

  &::after {
    display: block;
    content: ' ';
    width: 2px;
    height: 100%;
    background-color: #5675E8;
    position: absolute;
    left: 0;
    top: 0;
  }
}
</style>
