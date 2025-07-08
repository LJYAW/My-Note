<!--
 * @Author: your name
 * @Date: 2020-11-11 19:16:35
 * @LastEditTime: 2020-11-12 10:41:53
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/assignment/components/assign-demand-list.vue
-->
<template>
  <div class="assign-d-demand">
    <h6>{{data.title}}</h6>
    <ol>
      <li v-for="(txt,index) in data.list" :key="data.title + index">
        {{(index + 1) + '、' + txt}}
      </li>
    </ol>
  </div>
</template>

<script>
export default {
  props: { data: Object },
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {},
  components: {},
  created() {
    // console.log(this.data)
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.assign-d-demand {
  h6 {
    font-size: 18px;
    color: #101010;
    font-weight: normal;
  }
  margin-top: 32px;
  ol {
    margin-top: 12px;
    font-size: 18px;
  }
  li {
    line-height: 28px;
    font-size: 14px;
  }
}
</style>
