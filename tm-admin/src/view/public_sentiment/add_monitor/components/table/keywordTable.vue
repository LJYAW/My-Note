<!--
 * @Author: your name
 * @Date: 2021-04-16 09:54:25
 * @LastEditTime: 2021-04-27 16:58:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/public_sentiment/add_monitor/components/table/selfTable.vue
-->

<template>
  <div class="table">
    <div class="item">
      <div class="item-header">行业关键词</div>
      <div class="item-content">
        <!-- <div class="edit" @click="edit('品牌', 'brandEditDialog')">编辑</div> -->
        <el-popover
          v-for="(items, i) in list"
          :key="i"
          placement="bottom-start"
          width="10"
          trigger="hover"
        >
          <div class="btn" @click="popClick(item)">添加特殊提报</div>
          <div slot="reference">{{ items }}</div>
        </el-popover>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: {
    list: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {}
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    popClick(item) {
      this.$emit('popClick', item)
    },
    edit(title, name) {
      this.$emit('showDialog', title, name)
    }
  }
}
</script>

<style scoped lang="scss">
.table {
  width: 100%;
  display: flex;
  .item {
    flex: 1;
    .item-header {
      background: #ebebeb;
      width: 100%;
      height: 45px;
      line-height: 45px;
      text-align: center;
    }
    .item-content {
      display: flex;
      justify-content: flex-start;
      align-content: flex-start;
      flex-wrap: wrap;
      border: 1px solid #bbbbbb;
      box-sizing: border-box;
      padding: 8px 16px;
      min-height: 282px;
      max-height: 282px;
      overflow: hidden;
      overflow-y: auto;
      span {
        flex-shrink: 0;
        display: block;
        color: rgba(16, 16, 16, 100);
        font-size: 14px;
        line-height: 20px;
        // height: 20px;
        margin-right: 14px;
        cursor: pointer;
      }

      .el-image {
        max-width: 110px;
      }
    }
  }
}
.btn {
  text-align: center;
  cursor: pointer;
}
.edit {
  display: block;
  width: 100%;
  color: rgba(41, 113, 193, 100);
  font-size: 12px;
  cursor: pointer;
  margin-bottom: 11px;
}
</style>
