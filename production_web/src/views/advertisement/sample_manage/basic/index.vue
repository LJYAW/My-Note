<!--
 * @Author: your name
 * @Date: 2021-10-08 18:05:10
 * @LastEditTime: 2021-10-08 18:17:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/basic/index.vue
-->
<template>
  <div class="basic-wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane
        v-for="item in tabData"
        :key="item.name"
        :label="item.label"
        :name="item.name"
      />
    </el-tabs>
    <component :is="activeName" />
  </div>
</template>

<script>
import BasicImformation from './base_message/index.vue'
import IndustryClassify from './industry_classify/index.vue'
export default {
  components: {
    BasicImformation,
    IndustryClassify
  },
  props: {

  },
  data() {
    return {
      activeName: 'BasicImformation',
      tabData: [{
        name: 'BasicImformation',
        label: '基础信息'
      }, {
        name: 'IndustryClassify',
        label: '行业分类'
      }]
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
.basic-wrap{
    padding: 20px;
    ::v-deep .el-tabs__nav-wrap::after{
        height: 0;
    }
    ::v-deep .el-tabs__item{
        font-size: 16px;
    }
}
</style>
