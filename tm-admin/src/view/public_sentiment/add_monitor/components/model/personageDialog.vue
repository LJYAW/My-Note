<!--
 * @Author: your name
 * @Date: 2021-04-15 19:17:51
 * @LastEditTime: 2021-05-10 10:46:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/public_sentiment/add_monitor/components/model/personageDialog.vue
-->
<template>
  <div>
    <div class="d-flex align-items-center justify-content-between">
      <div>
        <el-select
          v-model="personageDialogData.dialogSelectVal"
          filterable
          placeholder="关键词搜索&下拉选择公司"
          clearable
          @change="selectChange"
        >
          <el-option
            v-for="item in personageDialogData.dialogOption"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
        <el-input
          v-model="personageDialogData.dialogSearch"
          placeholder="搜索人物名称"
          suffix-icon="el-icon-search"
          @input="searchChange"
        />
      </div>
    </div>

    <div class="d-flex align-items-center justify-content-between" style="margin-top: 20px">
      <div>
        <el-input v-model="personageDialogData.dialogCompany" placeholder="添加公司名称" />
        <el-input v-model="personageDialogData.dialigPersonage" placeholder="添加人物名称" />
      </div>
      <div class="add-btn" @click="addItem">添加新一条</div>
    </div>

    <el-table :data="personageDialogData.dialogTableData" style="width: 100%">
      <el-table-column prop="company" label="公司" />
      <el-table-column prop="contents" label="人物" />
      <el-table-column fixed="right" label="操作" style="position: relative">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="goImgDialog(scope)">图片库</el-button>
          <el-popconfirm
            title="确定删除吗？"
            @confirm="deleteItem(scope)"
          >
            <el-button slot="reference" type="text" size="small">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page.sync="currentPage"
      hide-on-single-page
      :page-size="10"
      layout="prev, pager, next, jumper"
      :total="count"
      @current-change="currentChange"
    />

  </div>
</template>

<script>
import { validatorFn } from '@/utils/validate.js'
import rules from '../../js/rules'

export default {
  components: {},
  props: {},
  data() {
    return {
      // 监测人物
      personageDialogData: {
        dialogOption: [],
        dialogSelectVal: '',
        dialogSearch: '',
        dialogCompany: '',
        dialigPersonage: '',
        dialigUrl: '',
        dialogTableData: []
      },
      currentPage: 1,
      count: 0
    }
  },
  computed: {},
  watch: {},
  created() {
    // this.dialogOption = res.images;
  },
  mounted() {
    this.initTopics()
  },
  methods: {
    async initTopics(params) {
      this.getSelect()
      const { res } = await this.$get('/api/topics', { ...params, query: `types:2,company__contains:${this.personageDialogData.dialogSelectVal},contents__contains:${this.personageDialogData.dialogSearch}` })
      this.personageDialogData.dialogTableData = res.data
      this.count = res.count
    },
    async getSelect() {
      const { res } = await this.$get('/api/topics', { limit: 10000, query: `types:2,company__contains:${this.personageDialogData.dialogSelectVal},contents__contains:${this.personageDialogData.dialogSearch}` })
      this.personageDialogData.dialogOption = Array.from(new Set(res.data.map(item => item.company)))
    },
    currentChange(val) {
      this.initTopics({ page: val })
    },
    selectChange(e) {
      this.initTopics()
    },
    searchChange(e) {
      this.initTopics()
    },
    goImgDialog(scope) {
      this.$emit('goImgDialog', scope.row.contents, 'personage')
    },
    addItem() {
      const value = {
        name: this.personageDialogData.dialogCompany,
        personage: this.personageDialogData.dialigPersonage
      }
      validatorFn(rules['addMointorPersonage'], value, () => {
        const parmas = {
          company: this.personageDialogData.dialogCompany,
          contents: this.personageDialogData.dialigPersonage,
          types: 2
        }
        this.$post('/api/topics', parmas).then((data) => {
          console.log(data)
          const { err, res } = data
          if (err) {
            this.$message.error(err.msg)
          } else {
            this.personageDialogData.dialogCompany = ''
            this.personageDialogData.dialigPersonage = ''
            this.$message.success('添加成功')
            this.initTopics()
          }
        })
      })
    },
    deleteItem(scope) {
      this.$deleteRun('/api/topics/' + scope.row.id).then((res) => {
        this.$message.success('删除成功')
        this.$emit('replacePersonage', scope.row.contents)
        this.initTopics()
      })
      // this.personageDialogData.dialogTableData.splice($index, 1);
    }
  }
}
</script>

<style scoped lang="scss">
.dialog {
  ::v-deep.el-dialog {
    min-height: 50vh;
  }
  // .el-select {
  //   // width: 250px;
  // }
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
  }
  .el-pagination {
    margin-top: 10px;
    ::v-deep.el-input{
      width: 50px!important;
    }
  }
}
</style>
