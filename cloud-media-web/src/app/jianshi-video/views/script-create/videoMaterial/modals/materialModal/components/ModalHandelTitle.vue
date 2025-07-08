<!--
 * @Author: your name
 * @Date: 2020-12-22 11:39:08
 * @LastEditTime: 2021-09-09 17:45:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/modal_handel_title.vue
-->
<template>
  <div class="handel-title">
    <el-tabs v-model="showType">
      <el-tab-pane
        v-for="(item,index) in typeArr"
        :key="index"
        :label="item"
        :name="item"
      />
    </el-tabs>
  </div>
</template>

<script>
export default {
  components: {},
  props: {
    typeArr: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      showType: ''
    }
  },
  computed: {},
  watch: {
    showType: {
      handler(val) {
        this.$emit('showTypeHandler', val)
      },
      // 一进页面就执行
      immediate: true
    }
  },
  created() {
    this.showType = this.typeArr[0]
  },
  mounted() {},
  methods: {}
}
</script>

<style scoped lang="scss">
.handel-title {
  display: flex;

  ::v-deep .el-tabs__header {
    margin-bottom: 20px;

    .el-tabs__nav-wrap {

      .el-tabs__item {
        height: auto;
        font-size: 18px;
        line-height: 18px;
        color: #404040;
        opacity: .4;
        padding: 0 10px 12px 10px;

        &:nth-child(2) {
          padding-left: 0;
        }

        &.is-active {
          opacity: 1;
        }
      }

      &::after {
        height: 0;
      }

    }
  }

}
</style>
