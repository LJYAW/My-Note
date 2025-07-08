<!--
 * @Author: your name
 * @Date: 2021-04-15 19:05:48
 * @LastEditTime: 2021-05-10 10:22:33
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/public_sentiment/add_monitor/components/model/beandDialog.vue
-->
<template>
  <div>
    <div class="d-flex align-items-center justify-content-between">
      <div>
        <el-select
          v-model="dialogSelectVal"
          filterable
          placeholder="关键词搜索&下拉选择品牌"
        >
          <el-option
            v-for="item in dialogOption"
            :key="item.id"
            :label="item.contents"
            :value="item.contents"
          />
        </el-select>
      </div>
      <div class="add-btn" @click="addItem">添加</div>
    </div>

    <el-table :data="dialogTableData" style="width: 100%; margin-bottom: 20px">
      <el-table-column prop="name" label="公司" />
      <el-table-column prop="personage" label="品牌" />
      <el-table-column fixed="right" label="操作" style="position: relative">
        <template slot-scope="scope">
          <el-button type="text" size="default" @click="goImgDialog(scope)">图片库</el-button>
          <el-checkbox
            v-model="scope.row.status"
            label=""
            :indeterminate="false"
            @change="monitor(scope)"
          >监测图片</el-checkbox>

          <el-popconfirm
            title="确定删除吗？"
            @confirm="deleteItem(scope)"
          >
            <el-button slot="reference" type="text" size="small">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div class="d-flex align-items-center justify-content-center">
      <el-button type="primary" size="default" @click="submit">确定</el-button>
    </div>
  </div>
</template>

<script>

export default {
  components: {},
  props: {
    dialogTableData: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      dialogOption: [],
      dialogSelectVal: ''
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {
    this.initTopics()
  },
  methods: {
    async initTopics() {
      const { res } = await this.$get('/api/topics', { limit: 10000, query: `types:1` })
      this.dialogOption = res.data
    },
    goImgDialog(scope) {
      this.$emit('goImgDialog', scope.row.personage)
    },
    addItem() {
      this.$emit('addList', this.dialogSelectVal, 'brand')
    },
    deleteItem(scope) {
      this.$emit('deleteList', scope.$index, 'brand')
    },
    monitor(item) {
      console.log(item.row)
    },
    submit() {
      this.$emit('submit', 'brand')
    }
  }
}
</script>

<style scoped lang="scss">
.dialog {
  ::v-deep.el-dialog {
    min-height: 50vh;
  }
  .el-select {
    width: 250px;
  }
  .el-input {
    width: 300px;
  }
  .add-btn {
    color: rgba(41, 113, 193, 100);
    font-size: 14px;
    cursor: pointer;
  }
  .el-table {
    margin-top: 20px;
    img {
      width: 124px;
      height: 70px;
      object-fit: cover;
    }
  }
}
</style>
