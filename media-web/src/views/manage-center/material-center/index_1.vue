<!--
 * @Author: your name
 * @Date: 2021-08-03 14:48:06
 * @LastEditTime: 2021-09-24 18:54:49
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/material-center/index.vue
-->
<template>
  <div class="material-center">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane v-for="(item) in tabList" :key="item.name" :label="item.name" :name="item.name" />
    </el-tabs>

    <div class="content-list-wrap">
      <el-row :gutter="20">
        <el-col :span="6">
          123
        </el-col>
      </el-row>
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
      page: 1,
      limit: 20,
      activeName: '个人素材',
      tabList: [
        {
          name: '个人素材'
        },
        {
          name: '背景音乐'
        },
        {
          name: '附加素材'
        }
      ],
      dataList: [],
      activeIndex: 0
    }
  },
  computed: {

  },
  watch: {
    activeName: {
      handler() {
        const params = this.setParams()
        this.getDataList(params)
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    setParams() {
      return {
        page: this.page,
        limit: this.limit,
        source: this.activeName
      }
    },
    async getDataList(params) {
      const { res, err } = await this.$get('/materials/', params)
      this.dataList = res.data
      // if (!err) {

      // }
    },
    handleClick(item) {
      this.activeName = item.name
    }
  }
}
</script>

<style scoped lang="scss">

</style>
